//package dao;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.UUID;
//import model.*;
//
//public class PaymentDAOold {
//    private static final String JDBC_URL = "jdbc:derby://localhost:1527/glowydays;create=true";
//    private static final String USERNAME = "nbuser";
//    private static final String PASSWORD = "nbuser";
//
//    public Payment processPayment(PaymentMethod paymentMethod, BuyerDetail buyer, Address address) throws SQLException {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        Payment payment = null;
//        
//        try {
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
//            conn.setAutoCommit(false);
//            
//            // Insert PaymentMethod
//            String methodSql = "INSERT INTO PAYMENTMETHOD (methodName, cardOwner, cardNumber, expMonth, expYear, cvv) VALUES (?, ?, ?, ?, ?, ?)";
//            stmt = conn.prepareStatement(methodSql, Statement.RETURN_GENERATED_KEYS);
//            stmt.setString(1, paymentMethod.getMethodName());
//            stmt.setString(2, paymentMethod.getCardOwner());
//            stmt.setString(3, paymentMethod.getCardNumber());
//            stmt.setString(4, paymentMethod.getExpMonth());
//            stmt.setString(5, paymentMethod.getExpYear());
//            stmt.setString(6, paymentMethod.getCvv());
//            stmt.executeUpdate();
//            
//            rs = stmt.getGeneratedKeys();
//            int methodId = rs.next() ? rs.getInt(1) : -1;
//            rs.close();
//            stmt.close();
//            
//            // Insert BuyerDetail
//            String buyerSql = "INSERT INTO BUYERDETAIL (fullName, email, mobile) VALUES (?, ?, ?)";
//            stmt = conn.prepareStatement(buyerSql, Statement.RETURN_GENERATED_KEYS);
//            stmt.setString(1, buyer.getFullName());
//            stmt.setString(2, buyer.getEmail());
//            stmt.setString(3, buyer.getMobile());
//            stmt.executeUpdate();
//            
//            rs = stmt.getGeneratedKeys();
//            int buyerId = rs.next() ? rs.getInt(1) : -1;
//            rs.close();
//            stmt.close();
//            
//            // Insert Address
//            String addressSql = "INSERT INTO ADDRESS (address, city, state, postcode) VALUES (?, ?, ?, ?)";
//            stmt = conn.prepareStatement(addressSql, Statement.RETURN_GENERATED_KEYS);
//            stmt.setString(1, address.getAddress());
//            stmt.setString(2, address.getCity());
//            stmt.setString(3, address.getState());
//            stmt.setString(4, address.getPostcode());
//            stmt.executeUpdate();
//            
//            rs = stmt.getGeneratedKeys();
//            int addressId = rs.next() ? rs.getInt(1) : -1;
//            rs.close();
//            stmt.close();
//            
//            // Insert ShippingDetail
//            String shippingSql = "INSERT INTO SHIPPINGDETAIL (buyerId, addressId) VALUES (?, ?)";
//            stmt = conn.prepareStatement(shippingSql);
//            stmt.setInt(1, buyerId);
//            stmt.setInt(2, addressId);
//            stmt.executeUpdate();
//            stmt.close();
//            
//            // Insert Payment
//            String paymentSql = "INSERT INTO PAYMENT (transactionId, methodId, paidDate, paidTime) VALUES (?, ?, ?, ?)";
//            stmt = conn.prepareStatement(paymentSql);
//            
//            String transactionId = "TXN" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
//            Date currentDate = Date.valueOf(LocalDate.now());
//            Time currentTime = Time.valueOf(LocalTime.now());
//            
//            stmt.setString(1, transactionId);
//            stmt.setInt(2, methodId);
//            stmt.setDate(3, currentDate);
//            stmt.setTime(4, currentTime);
//            stmt.executeUpdate();
//            
//            payment = new Payment();
//            payment.setTransactionId(transactionId);
//            payment.setMethodId(methodId);
//            payment.setPaidDate(currentDate);
//            payment.setPaidTime(currentTime);
//            
//            conn.commit();
//            
//        } catch (ClassNotFoundException | SQLException e) {
//            if (conn != null) conn.rollback();
//            throw new SQLException("Database error: " + e.getMessage());
//        } finally {
//            if (rs != null) rs.close();
//            if (stmt != null) stmt.close();
//            if (conn != null) conn.close();
//        }
//        
//        return payment;
//    }
//}