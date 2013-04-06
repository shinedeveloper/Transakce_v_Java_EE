package net.sevecek.transactions;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.ejb.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.springframework.context.*;
import org.springframework.context.support.*;
import net.sevecek.transactions.repository.*;
import net.sevecek.transactions.service.*;

import static net.sevecek.transactions.util.Resources.*;

public class Main {

    private static ConfigurableApplicationContext applicationContext;
    private static JdbcRepository repository;
    private static TopLevelTransactionBean topLevelTransactionBean;


    public static void main(String[] args) {
        createComponents();

        Collection<String> results = new ArrayList<>();
        test1(results);
        results.add("--------------------");
        test2(results);
        printResults(results);

        destroyComponents();
    }


    private static void test1(Collection<String> results) {
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


    private static void test2(Collection<String> results) {
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


    private static void printResults(Collection<String> results) {
        for (String singleResult : results) {
            System.out.printf("%s %n", singleResult);
        }
    }


    private static void addExceptionToResults(Collection<String> results, RuntimeException e) {
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


    private static void createComponents() {
        applicationContext = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        topLevelTransactionBean = applicationContext.getBean(TopLevelTransactionBean.class);
        repository = applicationContext.getBean(JdbcRepository.class);
    }


    private static void destroyComponents() {
        applicationContext.close();
    }
}
