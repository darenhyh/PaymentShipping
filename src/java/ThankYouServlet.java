import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.UUID;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ThankYouServlet")
public class ThankYouServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String orderId = generateOrderId();

        try (PrintWriter out = response.getWriter()) {
            // Save order ID and related details to database
            saveOrderToDatabase(orderId);

            // Display Thank You message
            out.println("<h1>Thank You for Your Order!</h1>");
            out.println("<p>Your order ID: " + orderId + "</p>");
            out.println("<p>A receipt has been sent to your email.</p>");
            //out.println("<a href='/GlowyDays/JSP/UserHome.jsp'>Return to Home</a>");
            out.println("<a href='" + request.getContextPath() + 
                        "/JSP/UserHome.jsp'>Return to Home</a>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String generateOrderId() {
        // Generate a unique order ID
        return UUID.randomUUID().toString();
    }

    private void saveOrderToDatabase(String orderId) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection("jdbc:derby://localhost:1527/glowyDays", "nbuser", "nbuser");

            // Save order with unique orderId
            String query = "INSERT INTO ORDERS (orderId) VALUES (?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, orderId);

            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
