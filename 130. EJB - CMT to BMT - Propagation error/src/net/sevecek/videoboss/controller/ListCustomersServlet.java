package net.sevecek.videoboss.controller;

import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.service.*;
import net.sevecek.videoboss.util.*;

@WebServlet("/customers.html")
public class ListCustomersServlet extends HttpServlet {

    @EJB
    ServiceFacade serviceFacade;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        List<Customer> allCustomers = serviceFacade.findAllCustomers();
        request.setAttribute("customers", allCustomers);
        request.getRequestDispatcher("/WEB-INF/jsp/customers.jsp")
                .forward(request, response);
    } 

}
