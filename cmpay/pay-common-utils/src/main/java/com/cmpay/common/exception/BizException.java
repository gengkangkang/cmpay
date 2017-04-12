package com.cmpay.common.exception;
/**
 * @author gengkangkang
 * @E-mail gengkangkang@cm-inv.com
 *
 * 2016年11月10日 下午2:00:17
 *
 * 业务异常基类，所有业务异常都必须继承于此异常 .
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;


    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected String code;

    public BizException(String code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    /**
     * 实例化异常
     *
     * @param msgFormat
     * @param args
     * @return
     */
    public BizException newInstance(String msgFormat, Object... args) {
        return new BizException(this.code, msgFormat, args);
    }

}
