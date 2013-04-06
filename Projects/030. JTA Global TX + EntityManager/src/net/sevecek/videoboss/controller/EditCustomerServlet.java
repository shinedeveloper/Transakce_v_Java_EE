package net.sevecek.videoboss.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import net.sevecek.videoboss.entity.*;
import net.sevecek.videoboss.service.*;
import net.sevecek.videoboss.util.*;

@WebServlet("/edit-customer.html")
public class EditCustomerServlet extends HttpServlet {

    private ServiceFacade serviceFacade;


    public void init() throws ServletException {
        serviceFacade = ServiceLocator.getInstance().getServiceFacade();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        String idString = (String) request.getParameter("id");
        if (idString == null) {
            throw new VideoBossException("Chybí URL parametr 'id'");
        }
        long id = Long.parseLong(idString);
        Customer customer = serviceFacade.findCustomer(id);
        if (customer == null) {
            throw new VideoBossException("Tento zákazník neexistuje");
        }
        request.setAttribute("customer", customer);
        request.getRequestDispatcher("/WEB-INF/jsp/edit-customer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String version = request.getParameter("version");
        String isDeleted = request.getParameter("deleted");
        if (idString == null) {
            throw new VideoBossException("Neplatné URL");
        }

        Customer customer = new Customer();
        customer.setId(new Long(idString));
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setVersion(Integer.parseInt(version));
        customer.setDeleted(Boolean.parseBoolean(isDeleted));

        serviceFacade.updateCustomer(customer);
        response.sendRedirect(request.getContextPath()
                + "/customers.html");
    }
}