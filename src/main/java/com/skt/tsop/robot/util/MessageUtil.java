package com.skt.tsop.robot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * Message Util.
 * @author yjkim@ntels.com
 *
 */
@Component
public class MessageUtil {
	
	/**
	 * Message Source.
	 */
	@Autowired
	private MessageSource messageSource;
	
	/**
	 * Message Source Accessor.
	 */
	private MessageSourceAccessor messageSourceAccessor;
	
	/**
	 * 초기화.
	 */
	@PostConstruct
    private void init() {
		messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }
	
	/**
	 * 메시지 조회.
	 * @param code 메시지 코드
	 * @return String 메시지
	 */
	public String getMessage(String code) {
		return messageSourceAccessor.getMessage(code);
	}
}
