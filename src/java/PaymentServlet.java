import java.io.IOException;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.PaymentDAO;
import java.io.PrintWriter;
import model.*;

@WebServlet("/PaymentServlet")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
        // Get all parameters
            String paymentMethod = request.getParameter("payment_method");
            String fullName = request.getParameter("shippingName");
            String email = request.getParameter("shippingEmail");
            String mobile = request.getParameter("shippingMobile");
            String address = request.getParameter("shippingAddress");
            String city = request.getParameter("shippingCity");
            String state = request.getParameter("shippingState");
            String postcode = request.getParameter("shippingPostcode");

            String cardOwner = request.getParameter("cardOwner");
            String cardNumber = request.getParameter("cardNumber");
            String expMonth = request.getParameter("expMonth");
            String expYear = request.getParameter("expYear");
            String cvv = request.getParameter("cvv");

            // Validate required fields
            if (!PaymentValidator.validateRequiredFields(fullName, email, mobile, 
                    address, city, state, postcode, paymentMethod)) {
                request.setAttribute("errorMsg", "All fields are required!");
                request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                return;
            }

            // Validate email
            if (!PaymentValidator.validateEmail(email)) {
                request.setAttribute("errorMsg", "Invalid email format!");
                request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                return;
            }

            // Validate mobile
            if (!PaymentValidator.validateMobile(mobile)) {
                request.setAttribute("errorMsg", "Invalid mobile format! Use 01X-XXXXXXX");
                request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                return;
            }

            // Validate card details if payment is by card
            if (paymentMethod.equals("visa") || paymentMethod.equals("master")) {
                if (!PaymentValidator.validateRequiredFields(cardOwner, cardNumber, expMonth, expYear, cvv)) {
                    request.setAttribute("errorMsg", "All card details are required for card payment!");
                    request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                    return;
                }

                if (!PaymentValidator.validateCardNumber(cardNumber)) {
                    request.setAttribute("errorMsg", "Invalid card number! Must be 15 or 16 digits");
                    request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                    return;
                }

                if (!PaymentValidator.validateCVV(cvv)) {
                    request.setAttribute("errorMsg", "Invalid CVV! Must be 3 digits");
                    request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                    return;
                }

                if (!PaymentValidator.validateExpYear(expYear)) {
                    request.setAttribute("errorMsg", "Invalid expiration year! Must be between 2025-2100");
                    request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
                    return;
                }
            }

            try {
                // Create model objects
                PaymentMethod pm = new PaymentMethod();
                pm.setMethodName(paymentMethod);

                if (paymentMethod.equals("visa") || paymentMethod.equals("master")) {
                    pm.setCardOwner(cardOwner);
                    pm.setCardNumber(cardNumber);
                    pm.setExpMonth(expMonth);
                    pm.setExpYear(expYear);
                    pm.setCvv(cvv);
                }

                BuyerDetail buyer = new BuyerDetail(fullName, email, mobile);
                Address addr = new Address(address, city, state, postcode);

                // Process payment
                PaymentDAO dao = new PaymentDAO();
                Payment payment = dao.processPayment(pm, buyer, addr);

                // Store in session
                HttpSession session = request.getSession();
                session.setAttribute("payment", payment);
                session.setAttribute("buyer", buyer);

                // Redirect to success page
                response.sendRedirect("ThankYou.jsp");

            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMsg", "Database error occurred: " + e.getMessage());
                request.getRequestDispatcher("PaymentShipping.jsp").forward(request, response);
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "User Registration Servlet";
    }
}