package net.sevecek.transactions;

import java.io.*;
import java.util.*;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import net.sevecek.transactions.repository.*;
import net.sevecek.transactions.service.*;

import static net.sevecek.transactions.util.Resources.*;

@WebServlet(urlPatterns = {"/operation1.html", "/operation2.html"})
public class TestBenchServlet extends HttpServlet {

    @EJB
    MultipleRequestService multipleRequestService;
    RequestDispatcher viewDispatcher;
    JdbcRepository repository;


    @Override
    public void init() {
        viewDispatcher = getServletContext().getRequestDispatcher(
                "/WEB-INF/view/results.jsp");
        repository = new JdbcRepository();
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<String> results = new ArrayList<>();
        Thread currentThread = Thread.currentThread();
        results.add("Request Thread = {" + currentThread.hashCode() + "} = " + currentThread.toString());

        String operation = request.getRequestURI();
        if (operation.contains("operation1")) {
            sleep(1000L);
            test1(request, results);
        } else {
            sleep(3000L);
            test2(request, results);
        }
        request.setAttribute("results", results);
        viewDispatcher.forward(request, response);
    }


    private void test1(HttpServletRequest request, Collection<String> results) {
        try {
            int initialNumber = ((int) (Math.random() * 900.0)) * 10 + 1000;
            repository.updateDatabase(initialNumber, results);
            results.add("Number in DB before operation1 = " + initialNumber);

            getMultipleRequestService().firstOperation(initialNumber, results);

            int finalNumber = repository.queryDatabase(results);
            results.add("Number in DB after operation1  = " + finalNumber);
        } catch (Exception ex) {
            addExceptionToResults(results, ex);
        }
    }


    private void test2(HttpServletRequest request, Collection<String> results) {
        try {
            int initialNumber = repository.queryDatabase(results);
            results.add("Number in DB before operation2 = " + initialNumber);

            getMultipleRequestService().secondOperation(results);

            int finalNumber = repository.queryDatabase(results);
            results.add("Number in DB after operation2  = " + finalNumber);
        } catch (Exception ex) {
            addExceptionToResults(results, ex);
        }
    }


    private void addExceptionToResults(Collection<String> results, Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        e.printStackTrace(printWriter);
        printWriter.close();

        String errorText = stringWriter.toString();
        errorText = "                " + errorText;
        errorText = errorText.replace("\n", "\n                ");

        results.add(EXCEPTION_BEGIN);
        results.add(errorText);
        results.add(EXCEPTION_END);
    }


    public MultipleRequestService getMultipleRequestService() {
        return multipleRequestService;
    }


    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
