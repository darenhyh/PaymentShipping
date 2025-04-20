import java.util.regex.Pattern;

public class PaymentValidator {
    public static boolean validateEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return Pattern.matches(emailRegex, email);
    }

    public static boolean validateMobile(String mobile) {
        return mobile.matches("01[0-9]-[0-9]{7,8}");
    }

    public static boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{15,16}");
    }

    public static boolean validateCVV(String cvv) {
        return cvv.matches("\\d{3}");
    }

    public static boolean validateExpYear(String expYear) {
        try {
            int year = Integer.parseInt(expYear);
            return year >= 2025 && year <= 2100;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateRequiredFields(String... fields) {
        for (String field : fields) {
            if (field == null || field.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}