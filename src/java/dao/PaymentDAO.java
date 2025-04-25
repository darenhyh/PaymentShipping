package dao;

import model.Payment;
import model.PaymentMethod;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class PaymentDAO {

    private Connection getConnection() throws Exception {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        return DriverManager.getConnection("jdbc:derby://localhost:1527/glowydays", "nbuser", "nbuser");
    }

    public boolean savePayment(PaymentMethod method) {
        boolean success = false;
        try (Connection con = getConnection()) {
            // Save method
            String methodSql = "INSERT INTO PAYMENTMETHOD(methodName, cardOwner, cardNumber, expMonth, expYear, cvv) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement methodStmt = con.prepareStatement(methodSql, Statement.RETURN_GENERATED_KEYS);
            methodStmt.setString(1, method.getMethodName());
            methodStmt.setString(2, method.getCardOwner());
            methodStmt.setString(3, method.getCardNumber());
            methodStmt.setString(4, method.getExpMonth());
            methodStmt.setString(5, method.getExpYear());
            methodStmt.setString(6, method.getCvv());
            methodStmt.executeUpdate();
            
            ResultSet methodKeys = methodStmt.getGeneratedKeys();
            int methodId = 0;
            if (methodKeys.next()) {
                methodId = methodKeys.getInt(1);
            }

            // Save payment
            String transactionId = "TXN" + System.currentTimeMillis();
            String paySql = "INSERT INTO PAYMENT(transactionId, methodId, paidDate, paidTime) VALUES (?, ?, ?, ?)";
            PreparedStatement payStmt = con.prepareStatement(paySql);
            payStmt.setString(1, transactionId);
            payStmt.setInt(2, methodId);
            payStmt.setDate(3, Date.valueOf(LocalDate.now()));
            payStmt.setTime(4, Time.valueOf(LocalTime.now()));
            payStmt.executeUpdate();

            success = true;
        } catch (Exception e) {
            e.printStackTrace(); // Will print the error in your server log
        }
        return success;
    }
}
