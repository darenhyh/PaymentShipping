import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ValidatePaymentServlet")
public class ValidatePaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String paymentMethod = request.getParameter("payment_method");
        String cardOwner = request.getParameter("cardOwner");
        String cardNumber = request.getParameter("cardNumber");
        String expMonth = request.getParameter("expMonth");
        String expYear = request.getParameter("expYear");
        String cvv = request.getParameter("cvv");

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            out.print("Payment Method Required");
            return;
        }

        // Validate only if Visa or MasterCard
        if (paymentMethod.equalsIgnoreCase("Visa") || paymentMethod.equalsIgnoreCase("Master")) {
            if (cardOwner == null || cardOwner.trim().isEmpty() ||
                cardNumber == null || cardNumber.trim().isEmpty() ||
                expMonth == null || expMonth.trim().isEmpty() ||
                expYear == null || expYear.trim().isEmpty() ||
                cvv == null || cvv.trim().isEmpty()) {
                out.print("Missing Card Info");
                return;
            }
            
            // Card number (15-16 digits)
            if (!cardNumber.matches("\\d{15,16}")) {
                out.print("Card number must be 15-16 digits.");
                return;
            }
            
            // Expiry month (1-12)
            try {
                int month = Integer.parseInt(expMonth);
                if (month < 1 || month > 12) {
                    out.print("Expiry month must be 1-12.");
                    return;
                }
            } catch (NumberFormatException e) {
                out.print("Invalid expiry month.");
                return;
            }

            // Expiry year (>= current year + 20 years)
            try {
                int year = Integer.parseInt(expYear);
                if (year < 2025 || year > 2100) {
                    out.print("Invalid Expiry Year");
                    return;
                }
            } catch (NumberFormatException e) {
                out.print("Invalid Year Format");
                return;
            }
            
            // CVV (3 digits)
            if (!cvv.matches("\\d{3}")) {
                out.print("CVV must be 3 digits.");
                return;
            }
        }

       //out.print("OK");
    }
}
