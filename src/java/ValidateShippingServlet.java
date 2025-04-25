import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;
import dao.*;

@WebServlet("/ValidateShippingServlet")
public class ValidateShippingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String fullName = request.getParameter("shippingName");
        String email = request.getParameter("shippingEmail");
        String mobile = request.getParameter("shippingMobile");
        String addressStr = request.getParameter("shippingAddress");
        String city = request.getParameter("shippingCity");
        String state = request.getParameter("shippingState");
        String postcode = request.getParameter("shippingPostcode");

        // NAME VALIDATION : only letters (A-Z, a-z), spaces, and '/'
        if (fullName != null && fullName.matches("^[A-Za-z\\s/]+$")) {
            response.getWriter().write("Valid Name");
        } else {
            response.getWriter().write("Invalid Name");
        }
        
        // EMAIL VALIDATION : must contain "@" and "."
        if (email != null && email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            response.getWriter().write("Valid Email");
        } else {
            response.getWriter().write("Invalid Email Format! Pls enter a valid email address");
        }
        
        // MOBILE VALIDATION : Malaysia's format (01X-XXXXXXX / 01X-XXXXXXXX)
        if (mobile != null && mobile.matches("/^01[0-9]-[0-9]{7,8}$/")) {
            response.getWriter().write("Valid Mobile");
        } else {
            response.getWriter().write("Invalid Mobile Format! Pls enter a complete mobile number");
        }
        
        // ADDRESS VALIDATION : at least 5 characters
        if (addressStr.trim().length() >= 5) {
            response.getWriter().write("Valid Address");
        } else {
            response.getWriter().write("Invalid Address! Pls enter a complete address");
        }
        
        // CITY VALIDATION : at least 2 characters
        if (city.trim().length() >= 2) {
            response.getWriter().write("Valid City");
        } else {
            response.getWriter().write("Invalid City! Pls enter a valid city name");
        }
        
        // STATE VALIDATION
        // List of valid Malaysian states
        String[] validStates = {
            "Johor", "Kedah", "Kelantan", "Kuala Lumpur", 
            "Labuan", "Melaka", "Negeri Sembilan", "Pahang",
            "Penang", "Perak", "Perlis", "Putrajaya",
            "Sabah", "Sarawak", "Selangor", "Terengganu"
        };
        
        boolean isValid = false;
        for (String validState : validStates) {
            if (validState.equalsIgnoreCase(state)) {
                isValid = true;
                break;
            }
        }
        
        if (isValid) {
            response.getWriter().write("Valid State");
        } else {
            response.getWriter().write("Invalid State! Pls select a valid state");
        }
    
        // POSTCODE VALIDATION : 5 digits
        if (postcode.matches("\\d{5}")) {
            response.getWriter().write("Valid Postcode");
        } else {
            response.getWriter().write("Invalid Postcode! Pls enter a valid postcode");
        }
    }
}
