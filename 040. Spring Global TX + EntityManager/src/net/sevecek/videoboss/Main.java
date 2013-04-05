package net.sevecek.videoboss;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import net.sevecek.videoboss.gui.util.*;

public class Main {

    private static ConfigurableApplicationContext applicationContext;


    public static void main(String args[]) {
        applicationContext = new ClassPathXmlApplicationContext(
                "/applicationContext.xml");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame mainWindow = applicationContext.getBean("mainWindow", JFrame.class);
                SwingExceptionHandler.install(mainWindow);
                mainWindow.setVisible(true);
                mainWindow.addWindowListener(new WindowAdapter() {
                    public void windowClosed(WindowEvent e) {
                        applicationContext.close();
                    }
                });
            }
        });
    }

}
