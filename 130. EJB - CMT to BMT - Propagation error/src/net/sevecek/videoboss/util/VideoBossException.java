package net.sevecek.videoboss.util;

public class VideoBossException extends RuntimeException {

    private String errorCode;
    private Object[] errorMessageParameters;


    public VideoBossException(String errorCode, Object... errorMessageParameters) {
        super(TextUtils.formatErrorMessageForLoggingButNotForUserUI(errorCode, errorMessageParameters));
        this.errorCode = errorCode;
        this.errorMessageParameters = errorMessageParameters;
    }


    public VideoBossException(Throwable cause, String errorCode, Object... errorMessageParameters) {
        super(TextUtils.formatErrorMessageForLoggingButNotForUserUI(errorCode, errorMessageParameters), cause);
        this.errorCode = errorCode;
        this.errorMessageParameters = errorMessageParameters;
    }


    public String getErrorCode() {
        return errorCode;
    }


    public Object[] getErrorMessageParameters() {
        return errorMessageParameters;
    }


    /**
     * @deprecated Don't use this version of the constructor
     *             because it could easily be mis-interpreted
     *             as the other constructor
     *             {@link #VideoBossException(String errorCode, Object... errorMessageParameters)}.<br/>
     *             Use VideoBossException(Throwable cause, String errorCode) instead.
     */
    @Deprecated
    public VideoBossException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @deprecated Don't call this method. It is here only for backwards compatibility.
     * It doesn't respect user's Locale.
     * Also it doesn't resolve error codes against a ResourceBundle.
     */
    @Deprecated
    @Override
    public String getMessage() {
        return super.getMessage();
    }


    /**
     * @deprecated Don't use this method. Problem is the same as in {@link #getMessage()}
     */
    @Deprecated
    @Override
    public String toString() {
        return super.toString();
    }

}
