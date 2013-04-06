package net.sevecek.videoboss.util;

import java.text.MessageFormat;

public class VideoBossInternalException extends RuntimeException {

    public VideoBossInternalException() {
    }


    public VideoBossInternalException(String message, Object... errorMessageParameters) {
        super(MessageFormat.format(message, errorMessageParameters));
    }


    public VideoBossInternalException(Throwable cause, String message, Object... errorMessageParameters) {
        super(MessageFormat.format(message, errorMessageParameters), cause);
    }


    public VideoBossInternalException(Throwable cause) {
        super(cause);
    }


    /**
     * @deprecated Don't use this version of the constructor
     *             because it could easily be mis-interpreted
     *             as the other constructor
     *             {@link #VideoBossInternalException(String errorCode, Object... errorMessageParameters)}.<br/>
     *             Use VideoBossInternalException(Throwable cause, String errorCode) instead.
     */
    @Deprecated
    public VideoBossInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
