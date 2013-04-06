package net.sevecek.videoboss.controller;

import java.io.*;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.service.*;
import net.sevecek.videoboss.util.*;

@WebServlet("/delete-customer.html")
public class DeleteCustomerServlet extends HttpServlet {


    @EJB
    private ServiceFacade serviceFacade;


    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String versionString = request.getParameter("version");
        if (idString == null || versionString == null) {
            throw new VideoBossException("Neplatné URL");
        }
        long id = Long.parseLong(idString);
        int version = Integer.parseInt(versionString);
        Customer customer = new Customer();
        customer.setId(id);
        customer.setVersion(version);
        serviceFacade.deleteCustomer(customer);
        response.sendRedirect(request.getContextPath()
                + "/customers.html");
    }

}
