package net.sevecek;

import javax.servlet.http.*;
import net.sevecek.transactions.service.*;

public class MySessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        MultipleRequestService statefulBean =
                (MultipleRequestService) se.getSession().getAttribute(
                        "myStatefulBean");
        if (statefulBean != null) {
            statefulBean.close();
        }
    }
}
