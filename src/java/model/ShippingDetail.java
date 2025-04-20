package model;

public class ShippingDetail {
    private int shippingId;
    private int buyerId;
    private int addressId;

    // Constructors
    public ShippingDetail() {}
    
    public ShippingDetail(int buyerId, int addressId) {
        this.buyerId = buyerId;
        this.addressId = addressId;
    }

    // Getters and Setters
    public int getShippingId() { return shippingId; }
    public void setShippingId(int shippingId) { this.shippingId = shippingId; }
    public int getBuyerId() { return buyerId; }
    public void setBuyerId(int buyerId) { this.buyerId = buyerId; }
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
}
