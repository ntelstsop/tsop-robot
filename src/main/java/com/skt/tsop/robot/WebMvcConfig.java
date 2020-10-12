package com.skt.tsop.robot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC 설정.
 *
 * @author yjkim@ntels.com
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 메시지 파일 경로.
     */
    @Value("${spring.messages.basename}")
    private String messagesBasename;

    /**
     * 메시지 인코딩.
     */
    @Value("${spring.messages.encoding}")
    private String messagesEncoding;

    /**
     * 메시지 소스.
     *
     * @return ReloadableResourceBundleMessageSource
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(messagesBasename);
        messageSource.setDefaultEncoding(messagesEncoding);
        return messageSource;
    }

    /**
     * 메시지 소스 억세서.
     *
     * @return MessageSourceAccessor
     */
    @Bean
    public MessageSourceAccessor getMessageSourceAccessor() {
        ReloadableResourceBundleMessageSource m = messageSource();
        return new MessageSourceAccessor(m);
    }
}
