import dao.PaymentDAO;
import dao.ShippingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PaymentMethod;
import model.BuyerDetail;
import model.Address;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PaymentShippingServlet")
public class PaymentShippingServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            // Get shipping inputs
            String fullName = request.getParameter("shippingName");
            String email = request.getParameter("shippingEmail");
            String mobile = request.getParameter("shippingMobile");
            String addressStr = request.getParameter("shippingAddress");
            String city = request.getParameter("shippingCity");
            String state = request.getParameter("shippingState");
            String postcode = request.getParameter("shippingPostcode");

//            // Get payment inputs
//            String methodName = request.getParameter("payment_method");
//            String cardOwner = request.getParameter("cardOwner");
//            String cardNumber = request.getParameter("cardNumber");
//            String expMonth = request.getParameter("expMonth");
//            String expYear = request.getParameter("expYear");
//            String cvv = request.getParameter("cvv");

            // Create model objects
            BuyerDetail buyer = new BuyerDetail(fullName, email, mobile);
            Address address = new Address(addressStr, city, state, postcode);
//            PaymentMethod paymentMethod = new PaymentMethod(methodName, cardOwner, cardNumber, expMonth, expYear, cvv);

            // Use DAO
            // Insert Shipping Data
            ShippingDAO shippingDAO = new ShippingDAO();
            boolean shippingSaved = shippingDAO.saveShipping(buyer, address);

//            // Insert Payment Data
//            PaymentDAO paymentDAO = new PaymentDAO();
//            boolean paymentSaved = paymentDAO.savePayment(paymentMethod);

            // Redirect or show error
            if (shippingSaved ){
//            if (shippingSaved && paymentSaved) {

                //response.sendRedirect("/GlowyDays-JDBC/ThankYouServlet.java");
                response.sendRedirect(request.getContextPath() + "/ThankYouServlet");
            } else {
                // Clear any existing output
                response.resetBuffer();

                // Set proper content type for error messages
                response.setContentType("text/html");
                
                if (!shippingSaved) {
                    out.println("<h3>Shipping save failed.</h3>");
                }
//                if (!paymentSaved) {
//                    out.println("<h3>Payment save failed.</h3>");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
