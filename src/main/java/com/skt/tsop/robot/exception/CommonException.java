package com.skt.tsop.robot.exception;

/**
 * 예외 처리를 위한 공통 클래스.
 *
 * @author yjkim@ntels.com
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = 7063550481838670969L;

    /**
     * 기본 생성자.
     */
    public CommonException() {
    }

    /**
     * 예외 문자열을 받는 생성자.
     *
     * @param message 예외 문자열
     */
    public CommonException(String message) {
        super(message);
    }

    /**
     * Throwable 객체를 받는 생성자.
     *
     * @param throwable 던질 예외
     */
    public CommonException(Throwable throwable) {
        super(throwable);
    }

    /**
     * 예외 문자열과, Throwable 객체를 동시에 받는 생성자.
     *
     * @param message   예외 문자열
     * @param throwable 던질 예외
     */
    public CommonException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
