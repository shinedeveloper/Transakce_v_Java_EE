package net.sevecek.videoboss.util;

public class ExceptionUtils {

    public static RuntimeException rethrowAsUnchecked(Exception ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        } else {
            return new VideoBossInternalException(ex);
        }
    }
}
