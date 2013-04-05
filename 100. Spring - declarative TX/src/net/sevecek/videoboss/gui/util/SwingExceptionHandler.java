package net.sevecek.videoboss.gui.util;

import net.sevecek.videoboss.util.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;
import javax.swing.*;

public class SwingExceptionHandler implements Thread.UncaughtExceptionHandler {

    private JFrame mainWindow;


    public SwingExceptionHandler(JFrame mainWindow) {
        this.mainWindow = mainWindow;
    }


    public static void install(JFrame mainWindow) {
        Thread.setDefaultUncaughtExceptionHandler(new SwingExceptionHandler(mainWindow));
    }

    @Override
    public void uncaughtException(Thread t, Throwable error) {
        if (error instanceof VideoBossException) {
            VideoBossException exception = (VideoBossException) error;
            String message = TextUtils.formatErrorMessageForUI(
                    exception.getErrorCode(), exception.getErrorMessageParameters(), null);
            JOptionPane.showMessageDialog(
                    mainWindow,
                    message,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(
                mainWindow,
                "Internal error occurred:\n"
                        + error.getMessage() + "\n("
                        + error.getClass().getCanonicalName() + ")",
                "Internal Error",
                JOptionPane.ERROR_MESSAGE);

        StringWriter stringWriter = new StringWriter();
        PrintWriter errorStackTraceWriter = new PrintWriter(stringWriter);
        error.printStackTrace(errorStackTraceWriter);
        errorStackTraceWriter.flush();
        Logger.getAnonymousLogger().severe(stringWriter.toString());
    }
}
