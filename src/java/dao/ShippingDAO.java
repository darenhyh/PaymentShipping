package dao;

import model.Address;
import model.BuyerDetail;
import model.ShippingDetail;

import java.sql.*;

public class ShippingDAO {
    
    public boolean saveShipping(BuyerDetail buyer, Address address) {
        boolean success = false;
        
        try {
            // 1. Load JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            // 2. Connect to Derby database
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/GlowyDays", "nbuser", "nbuser");

            // 3. Insert BuyerDetail
            String buyerSql = "INSERT INTO BUYERDETAIL(fullName, email, mobile) VALUES (?, ?, ?)";
            PreparedStatement buyerStmt = con.prepareStatement(buyerSql, Statement.RETURN_GENERATED_KEYS);
            buyerStmt.setString(1, buyer.getFullName());
            buyerStmt.setString(2, buyer.getEmail());
            buyerStmt.setString(3, buyer.getMobile());
            int buyerRow = buyerStmt.executeUpdate();

            int buyerId = 0;
            if (buyerRow > 0) {
                ResultSet buyerKeys = buyerStmt.getGeneratedKeys();
                if (buyerKeys.next()) {
                    buyerId = buyerKeys.getInt(1); // get generated buyerId
                }
            }
            
            // 4. Insert Address
            String addressSql = "INSERT INTO ADDRESS(address, city, state, postcode) VALUES (?, ?, ?, ?)";
            PreparedStatement addressStmt = con.prepareStatement(addressSql, Statement.RETURN_GENERATED_KEYS);
            addressStmt.setString(1, address.getAddress());
            addressStmt.setString(2, address.getCity());
            addressStmt.setString(3, address.getState());
            addressStmt.setString(4, address.getPostcode());
            int addressRow = addressStmt.executeUpdate();

            int addressId = 0;
            if (addressRow > 0) {
                ResultSet addressKeys = addressStmt.getGeneratedKeys();
                if (addressKeys.next()) {
                    addressId = addressKeys.getInt(1); // get generated addressId
                }
            }

            // 5. Insert ShippingDetail (linking buyerId & addressId)
            if (buyerId > 0 && addressId > 0) {
                String shipSql = "INSERT INTO SHIPPINGDETAIL(buyerId, addressId) VALUES (?, ?)";
                PreparedStatement shipStmt = con.prepareStatement(shipSql);
                shipStmt.setInt(1, buyerId);
                shipStmt.setInt(2, addressId);
                int shippingRow = shipStmt.executeUpdate();

                if (shippingRow > 0) {
                    success = true; // Success only if all 3 inserts were successful
                }

                shipStmt.close();
            }

            // 6. Close all statements and connection
            buyerStmt.close();
            addressStmt.close();
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return success;
    }
}
