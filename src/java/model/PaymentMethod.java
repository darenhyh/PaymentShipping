package model;

public class PaymentMethod {
    private int methodId;
    private String methodName;
    private String cardOwner;
    private String cardNumber;
    private String expMonth;
    private String expYear;
    private String cvv;
    
    // Constructors
    public PaymentMethod() {}
    
    public PaymentMethod(String methodName, String cardOwner, String cardNumber, String expMonth, String expYear, String cvv) {
        this.methodName = methodName;
        this.cardOwner = cardOwner;
        this.cardNumber = cardNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.cvv = cvv;
    }
    
    // Getters & Setters
    public int getMethodId() { return methodId; }
    public void setMethodId(int methodId) { this.methodId = methodId; }
    public String getMethodName() { return methodName; }
    public void setMethodName(String methodName) { this.methodName = methodName; }
    public String getCardOwner() { return cardOwner; }
    public void setCardOwner(String cardOwner) { this.cardOwner = cardOwner; }
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    public String getExpMonth() { return expMonth; }
    public void setExpMonth(String expMonth) { this.expMonth = expMonth; }
    public String getExpYear() { return expYear; }
    public void setExpYear(String expYear) { this.expYear = expYear; }
    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}