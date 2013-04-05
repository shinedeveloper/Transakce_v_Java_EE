package net.sevecek.transactions;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.annotation.*;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.sql.*;
import net.sevecek.transactions.repository.*;
import net.sevecek.transactions.service.*;

import static net.sevecek.transactions.util.Resources.*;

@WebServlet("/test-bench.html")
public class TestBenchServlet extends HttpServlet {

    @Resource(lookup = "jdbc/VideoBoss")
    DataSource dataSource;

    JdbcRepository repository;

    TopLevelTransactionBean topLevelTransactionBean;

    NestedTransactionBean nestedTransactionBean;

    RequestDispatcher viewDispatcher;


    @Override
    public void init() {
        createComponents();
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
        try {
            int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
            try (Connection firstConn = dataSource.getConnection()) {
                repository.updateDatabase(firstConn, initialNumber);
                results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);
            }

            try (Connection secondConn = dataSource.getConnection()) {
                topLevelTransactionBean.performTopLevelOperationAndRollback(secondConn, initialNumber, results);
            }

            try (Connection thirdConn = dataSource.getConnection()) {
                int finalNumber = repository.queryDatabase(thirdConn);
                results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
            }
        } catch (SQLException ex) {
            addExceptionToResults(results, ex);
        }
    }


    private void test2(Collection<String> results) {
        try {
            int initialNumber = ((int) (Math.random() * 1000.0)) * 10;
            try (Connection firstConn = dataSource.getConnection()) {
                repository.updateDatabase(firstConn, initialNumber);
                results.add(NUMBER_IN_DB_BEFORE_ANY_TRANSACTION + initialNumber);
            }

            try (Connection secondConn = dataSource.getConnection()) {
                topLevelTransactionBean.performTopLevelOperationAndCommit(secondConn, initialNumber, results);
            }

            try (Connection thirdConn = dataSource.getConnection()) {
                int finalNumber = repository.queryDatabase(thirdConn);
                results.add(NUMBER_IN_DB_AFTER_ALL_TRANSACTIONS + finalNumber);
            }
        } catch (SQLException ex) {
            addExceptionToResults(results, ex);
        }
    }


    private void addExceptionToResults(Collection<String> results, Exception ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter, true);
        ex.printStackTrace(printWriter);
        printWriter.close();

        String errorText = stringWriter.toString();
        errorText = "                " + errorText;
        errorText = errorText.replace("\n", "\n                ");

        results.add(EXCEPTION_BEGIN);
        results.add(errorText);
        results.add(EXCEPTION_END);
    }


    private void createComponents() {
        repository = new JdbcRepository();
        nestedTransactionBean = new NestedTransactionBean(repository);
        topLevelTransactionBean = new TopLevelTransactionBean(nestedTransactionBean, repository);
    }
}
