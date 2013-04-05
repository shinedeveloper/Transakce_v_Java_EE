package net.sevecek.videoboss.util;

import java.text.*;
import java.util.*;

public class TextUtils {

    public static String formatErrorMessageForLoggingButNotForUserUI(String errorCode, Object[] errorMessageParameters) {
        String errorMessageWithPlaceholders =
                prepareErrorMessageWithoutResourceBundle(errorCode);
        return MessageFormat.format(errorMessageWithPlaceholders, errorMessageParameters);
    }


    public static String formatErrorMessageForUI(String errorCode, Object[] errorMessageParameters, ResourceBundle messages) {
        String errorMessageWithPlaceholders;
        if (messages != null) {
            errorMessageWithPlaceholders = messages.getString(errorCode);
        } else {
            errorMessageWithPlaceholders =
                    prepareErrorMessageWithoutResourceBundle(errorCode);
        }
        return MessageFormat.format(errorMessageWithPlaceholders, errorMessageParameters);
    }


    private static String prepareErrorMessageWithoutResourceBundle(String errorCode) {
        StringBuilder errorBuilder = new StringBuilder(errorCode);

        int lastDelimiter = errorBuilder.lastIndexOf("->");
        if (lastDelimiter > -1) {
            errorBuilder.delete(0, lastDelimiter + "->".length());
        }

        for (int i = 0; i < errorBuilder.length(); i++) {
            if (errorBuilder.charAt(i) == '_') {
                errorBuilder.setCharAt(i, ' ');
            }
        }

        return errorBuilder.toString();
    }
}
