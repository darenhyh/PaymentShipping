package model;

public class Address {
    private int addressId;
    private String address;
    private String city;
    private String state;
    private String postcode;
    
    // Constructors
    public Address() {}
    
    public Address(String address, String city, String state, String postcode) {
        this.address = address;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }
    
    // Getters & Setters
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
}