package net.sevecek.videoboss.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.service.*;
import net.sevecek.videoboss.util.*;

@WebServlet("/new-customer.html")
public class AddCustomerServlet extends HttpServlet {

    private ServiceFacade serviceFacade;


    public void init() throws ServletException {
        serviceFacade = ServiceLocator.getInstance().getServiceFacade();
    }

    @Override
    protected void doGet(HttpServletRequest request, 
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/new-customer.jsp")
                .forward(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, 
                          HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        Customer newCustomer = new Customer(firstName, lastName, address);
        serviceFacade.addCustomer(newCustomer);
        response.sendRedirect(request.getContextPath()
                + "/customers.html");
    }

}
