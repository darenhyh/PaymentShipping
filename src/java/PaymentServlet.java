//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import dao.PaymentDAOold;
//import model.*;
//
//@WebServlet("/PaymentServlet")
//public class PaymentServlet extends HttpServlet {
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        // Set response content type
//        response.setContentType("text/html;charset=UTF-8");
//
//        // Get all parameters
//        String paymentMethod = request.getParameter("payment_method");
//        String fullName = request.getParameter("shippingName");
//        String email = request.getParameter("shippingEmail");
//        String mobile = request.getParameter("shippingMobile");
//        String address = request.getParameter("shippingAddress");
//        String city = request.getParameter("shippingCity");
//        String state = request.getParameter("shippingState");
//        String postcode = request.getParameter("shippingPostcode");
//
//        String cardOwner = request.getParameter("cardOwner");
//        String cardNumber = request.getParameter("cardNumber");
//        String expMonth = request.getParameter("expMonth");
//        String expYear = request.getParameter("expYear");
//        String cvv = request.getParameter("cvv");
//
//        // Get session for storing error messages
//        HttpSession session = request.getSession();
//        
//        // Validate required fields
//        if (!PaymentValidator.validateRequiredFields(fullName, email, mobile,
//        address, city, state, postcode, paymentMethod)) {
//            session.setAttribute("errorMsg", "All fields are required!");
//            response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");            
//            return;
//        }
//
//        // Validate email
//        if (!PaymentValidator.validateEmail(email)) {
//            session.setAttribute("errorMsg", "Invalid email format!");
//            response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//            return;
//        }
//
//        // Validate mobile
//        if (!PaymentValidator.validateMobile(mobile)) {
//            session.setAttribute("errorMsg", "Invalid mobile format! Use 01X-XXXXXXX");
//            response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//            return;
//        }
//
//        // Validate card details if payment by card
//        if (paymentMethod.equals("visa") || paymentMethod.equals("master")) {
//            if (!PaymentValidator.validateRequiredFields(cardOwner, cardNumber, expMonth, expYear, cvv)) {
//                session.setAttribute("errorMsg", "All card details are required for card payment!");
//                response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//                return;
//            }
//
//            if (!PaymentValidator.validateCardNumber(cardNumber)) {
//                session.setAttribute("errorMsg", "Invalid card number! Must be 15 or 16 digits");
//                response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//                return;
//            }
//
//            if (!PaymentValidator.validateCVV(cvv)) {
//                session.setAttribute("errorMsg", "Invalid CVV! Must be 3 digits");
//                response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//                return;
//            }
//
//            if (!PaymentValidator.validateExpYear(expYear)) {
//                session.setAttribute("errorMsg", "Invalid expiration year! Must be between 2025-2100");
//                response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//                return;
//            }
//        }
//
//        try {
//            // Create model objects
//            PaymentMethod pm = new PaymentMethod();
//            pm.setMethodName(paymentMethod);
//
//            if (paymentMethod.equals("visa") || paymentMethod.equals("master")) {
//                pm.setCardOwner(cardOwner);
//                pm.setCardNumber(cardNumber);
//                pm.setExpMonth(expMonth);
//                pm.setExpYear(expYear);
//                pm.setCvv(cvv);
//            }
//
//            BuyerDetail buyer = new BuyerDetail(fullName, email, mobile);
//            Address addr = new Address(address, city, state, postcode);
//
//            // Process payment
//            PaymentDAOold dao = new PaymentDAOold();
//            Payment payment = dao.processPayment(pm, buyer, addr);
//
//            // Store in session
//            session.setAttribute("payment", payment);
//            session.setAttribute("buyer", buyer);
//
//            // Redirect to success page
//            response.sendRedirect("/GlowyDays-JDBC/JSP/ThankYou.jsp");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            session.setAttribute("errorMsg", "Database error occurred: " + e.getMessage());
//            response.sendRedirect("/GlowyDays-JDBC/JSP/PaymentShipping.jsp");
//        }
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "User Registration Servlet";
//    }
//}