package com.skt.tsop.building;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * - Supports both HTTP and HTTPS
 * - Uses a connection pool to re-use connections and save overhead of creating connections.
 * - Has a custom connection keep-alive strategy (to apply a default keep-alive if one isn't specified)
 * - Starts an idle connection monitor to continuously clean up stale connections.
 * - source : https://howtodoinjava.com/spring-restful/resttemplate-httpclient-java-config/
 */
@Configuration
@EnableScheduling
public class HttpClientConfig {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientConfig.class);

	/**
	 * Determines the timeout in milliseconds until a connection is established.
	 */
	@Value("${config.restTemplate.timeout}")
	private int CONNECT_TIMEOUT;

	/**
	 * The timeout when requesting a connection from the connection manager.
	 */
	@Value("${config.restTemplate.timeout}")
	private int REQUEST_TIMEOUT;

	/**
	 * The timeout for waiting for data.
	 */
	@Value("${config.restTemplate.timeout}")
	private int SOCKET_TIMEOUT;

	/**
	 * max total connections.
	 */
	@Value("${config.restTemplate.maxConnTotal}")
	private int MAX_TOTAL_CONNECTIONS;

	/**
	 * max per route.
	 */
	@Value("${config.restTemplate.maxConnPerRoute}")
	private int MAX_PER_ROUTE;

	/**
	 * default keep alive time millis.
	 */
	private static final int DEFAULT_KEEP_ALIVE_TIME_MILLIS = 60 * 60 * 1000;

	/**
	 * close idle connection wait time secs.
	 */
	private static final int CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS = 60 * 60;

	/**
	 * Pooling Http Client Connection Manager.
	 * @return PoolingHttpClientConnectionManager
	 */
	@Bean
	public PoolingHttpClientConnectionManager poolingConnectionManager() {
		SSLContextBuilder builder = new SSLContextBuilder();
		try {
			builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
		} catch (NoSuchAlgorithmException | KeyStoreException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		SSLConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(builder.build());
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			LOGGER.error("Pooling Connection Manager Initialisation failure because of " + e.getMessage(), e);
		}

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory>create().register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory())
				.build();

		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolingConnectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
		poolingConnectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE);
		return poolingConnectionManager;
	}

	/**
	 * Connection Keep Alive Strategy.
	 * @return ConnectionKeepAliveStrategy
	 */
	@Bean
	public ConnectionKeepAliveStrategy connectionKeepAliveStrategy() {
		return (response, context) -> {
			HeaderElementIterator it = new BasicHeaderElementIterator
					(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
			while (it.hasNext()) {
				HeaderElement he = it.nextElement();
				String param = he.getName();
				String value = he.getValue();

				if (value != null && param.equalsIgnoreCase("timeout")) {
					return Long.parseLong(value) * 1000;
				}
			}
			return DEFAULT_KEEP_ALIVE_TIME_MILLIS;
		};
	}

	/**
	 * closeable http client.
	 * @return CloseableHttpClient
	 */
	@Bean
	public CloseableHttpClient httpClient() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectionRequestTimeout(REQUEST_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT)
				.setSocketTimeout(SOCKET_TIMEOUT).build();

		return HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setConnectionManager(poolingConnectionManager())
				.setKeepAliveStrategy(connectionKeepAliveStrategy())
				.build();
	}

	/**
	 * idle connection monitor.
	 * @param connectionManager connectionManager
	 * @return Runnable
	 */
	@Bean
	public Runnable idleConnectionMonitor(final PoolingHttpClientConnectionManager connectionManager) {
		return new Runnable() {
			@Override
			@Scheduled(fixedDelay = 60000)
			public void run() {
				try {
					if (connectionManager != null) {
						LOGGER.trace("run IdleConnectionMonitor - Closing expired and idle connections...");
						connectionManager.closeExpiredConnections();
						connectionManager.closeIdleConnections(CLOSE_IDLE_CONNECTION_WAIT_TIME_SECS, TimeUnit.SECONDS);
					} else {
						LOGGER.debug("run IdleConnectionMonitor - Http Client Connection manager is not initialised");
					}
				} catch (Exception e) {
					LOGGER.error("run IdleConnectionMonitor - Exception occurred. msg={}, e={}", e.getMessage(), e);
				}
			}
		};
	}
}