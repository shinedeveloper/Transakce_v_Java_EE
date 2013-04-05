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

@WebServlet("/test-bench.html")
public class TestBenchServlet extends HttpServlet {

    @EJB
    TopLevelTransactionBean topLevelTransactionBean;

    @EJB
    JdbcRepository repository;

    RequestDispatcher viewDispatcher;


    @Override
    public void init() {
        viewDispatcher = getServletContext().getRequestDispatcher(
                "/WEB-INF/view/test-bench.jsp");
    }


    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Collection<String> results = new ArrayList<>();
        test1(results);
        results.add("--------------------");
        test2(results);
        request.setAttribute("results", results);
        viewDispatcher.forward(request, response);
    }


    private void test1(Collection<String> results) {
        int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
        repository.updateDatabase(initialNumber);
        results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);

        try {
            topLevelTransactionBean.performTopLevelOperationAndRollback(initialNumber, results);
        } catch (RuntimeException e) {
            addExceptionToResults(results, e);
        }

        int finalNumber = repository.queryDatabase();
        results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
    }


    private void test2(Collection<String> results) {
        int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
        repository.updateDatabase(initialNumber);
        results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);

        try {
            topLevelTransactionBean.performTopLevelOperationAndCommit(initialNumber, results);
        } catch (RuntimeException e) {
            addExceptionToResults(results, e);
        }

        int finalNumber = repository.queryDatabase();
        results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
    }


    private void addExceptionToResults(Collection<String> results, RuntimeException e) {
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

}
