package com.skt.tsop.robot.service.cctv;

import com.skt.tsop.robot.model.URIQueryString;
import com.skt.tsop.robot.model.cctv.Video;
import com.skt.tsop.robot.util.RestTemplateByteUtil;
import com.skt.tsop.robot.util.RestTemplateMapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * VideoServiceImpl.
 * @author yjkim@ntels.com
 */
@Service
public class VideoServiceImpl implements VideoService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);
    /**
     * RestTemplateMapUtil.
     */
    @Autowired
    RestTemplateMapUtil restTemplateMapUtil;
    /**
     * RestTemplateByteUtil.
     */
    @Autowired
    RestTemplateByteUtil restTemplateByteUtil;
    /**
     * AICCTV IP 주소.
     */
    @Value("${config.cctv.ip}")
    private String cctvIp;
    /**
     * AICCTV 포트번호.
     */
    @Value("${config.cctv.port}")
    private String cctvPort;

    @Override
    public Map<String, Object> getVideoData(Video video, URIQueryString uriQueryString) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = uriQueryString.getUri();
        if(uriQueryString.getQueryString() != null) {
            path += "?" + uriQueryString.getQueryString();
        }
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", video.getAuth_token());
        headers.add("x-api-serial", video.getApi_serial().toString());
        return restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }

    @Override
    public byte[] getThumbnail(Video video, URIQueryString uriQueryString) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = uriQueryString.getUri();
        if(uriQueryString.getQueryString() != null) {
            path += "?" + uriQueryString.getQueryString();
        }
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", video.getAuth_token());
        headers.add("x-api-serial", video.getApi_serial().toString());
        return restTemplateByteUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }

    @Override
    public Map<String, Object> getRtspUrl(Video video, URIQueryString uriQueryString) {
        String baseUrl = "http://" + cctvIp + ":" + cctvPort;
        String path = uriQueryString.getUri();
        if(uriQueryString.getQueryString() != null) {
            path += "?" + uriQueryString.getQueryString();
        }
        String fullUrl = baseUrl + path;
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-auth-token", video.getAuth_token());
        headers.add("x-api-serial", video.getApi_serial().toString());
        return restTemplateMapUtil.restTemplate(fullUrl, HttpMethod.GET, headers, null);
    }

    @Override
    public void testImage() {
        String fullUrl = "http://localhost:8001/image";
        byte[] bytes = restTemplateByteUtil.restTemplate(fullUrl, HttpMethod.GET, null, null);
        try {
            InputStream inputStream = new ByteArrayInputStream(bytes);
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            ImageIO.write(bufferedImage, "jpg", new File("C:\\Users\\dondaykiz\\Desktop\\suwon1.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
