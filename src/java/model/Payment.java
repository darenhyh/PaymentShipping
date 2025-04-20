package model;

//import java.util.Date;
import java.sql.Date;
import java.sql.Time;

public class Payment {
    private int paymentId;
    private String transactionId;
    //private PaymentMethod paymentMethod;
    private int methodId;
    private Date paidDate;
    private Time paidTime;
    
    // Constructors
    public Payment() {}
    
    public Payment(String transactionId, int methodId, Date paidDate, Time paidTime) {
        this.transactionId = transactionId;
        this.methodId = methodId;
        this.paidDate = paidDate;
        this.paidTime = paidTime;
    }
    
    // Getters and Setters
    public int getPaymentId() { return paymentId; }
    public void setPaymentId(int paymentId) { this.paymentId = paymentId; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public int getMethodId() { return methodId; }
    public void setMethodId(int methodId) { this.methodId = methodId; }
    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }
    public Time getPaidTime() { return paidTime; }
    public void setPaidTime(Time paidTime) { this.paidTime = paidTime; }
}