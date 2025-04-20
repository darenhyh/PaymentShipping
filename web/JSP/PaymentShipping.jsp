<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Payment Shipping Form</title>
    <link href="../CSS/payment.css" rel="stylesheet" type="text/css">
</head>
<body>
    <div class="container">
        <form action="/GlowyDays-heJDBC/PaymentServlet" method="post">
            <div class="row">
                <div class="column">
                    <h3 class="title">Shipping Address</h3>
                    <div class="input-box">
                        <label>Full Name</label>
                        <input type="text" name="shippingName" id="shippingName" placeholder="Enter full name" required>
                    </div>
                    <div class="input-box">
                        <label>Email</label>
                        <input type="text" name="shippingEmail" id="shippingEmail" placeholder="Enter email" required>
                    </div>
                    <div class="input-box">
                        <label>Mobile Number</label>
                        <input type="tel" name="shippingMobile" id="shippingMobile" pattern="01[0-9]-[0-9]{7,10}" placeholder="Enter mobile number (01X-XXXXXXX)" required>
                    </div>
                    <div class="input-box">
                        <label>Address</label>
                        <input type="text" name="shippingAddress" id="shippingAddress" placeholder="Enter address" required>
                    </div>
                    <div class="input-box">
                        <label>City</label>
                        <input type="text" name="shippingCity" id="shippingCity" placeholder="Enter city" required>
                    </div>

                    <div class="flex">
                        <div class="input-box">
                            <label>State</label>
                            <select name="shippingState" id="shippingState" required>
                                <option value="">Choose state</option>
                                <option value="johor">Johor</option>
                                <option value="kedah">Kedah</option>
                                <option value="kelantan">Kelantan</option>
                                <option value="kualaLumpur">Kuala Lumpur</option>
                                <option value="labuan">Labuan</option>
                                <option value="melaka">Melaka</option>
                                <option value="negeriSembilan">Negeri Sembilan</option>
                                <option value="pahang">Pahang</option>
                                <option value="penang">Penang</option>
                                <option value="perak">Perak</option>
                                <option value="perlis">Perlis</option>
                                <option value="putrajaya">Putrajaya</option>
                                <option value="sabah">Sabah</option>
                                <option value="sarawak">Sarawak</option>
                                <option value="selangor">Selangor</option>
                                <option value="terengganu">Terengganu</option>
                            </select>
                        </div>
                        <div class="input-box">
                            <label>Postcode</label>
                            <input type="number" name="shippingPostcode" id="shippingPostcode" maxlength="5" placeholder="Enter postcode"> 
                        </div>
                    </div>
                </div>
                
                <div class="column">
                    <h3 class="title">Payment</h3>
                    <div class="input-box">
                        <label>Payment Method</label>
                        <input type="hidden" name="payment_method" id="payment_method" value="">
                        <div class="icon-container">
                            <button type="button" id="cashBtn" class="cashBtn" data-method="cash"><img src="../ICON/cash.svg"></button>
                            <button type="button" id="tngBtn" class="tngBtn" data-method="tng"><img src="../ICON/tng.svg"></button>
                            <button type="button" id="visaBtn" class="visaBtn" data-method="visa"><img src="../ICON/visa.svg"></button>
                            <button type="button" id="mcBtn" class="mcBtn" data-method="master"><img src="../ICON/mastercard.svg"></button>
                        </div>
                    </div>
                    <div class="input-box">
                        <label>Name On Card</label>
                        <input type="text" name="cardOwner" id="cardOwner" placeholder="Enter card owner" disabled>
                    </div>
                    <div class="input-box">
                        <label>Credit Card Number</label>
                        <input type="text" name="cardNumber" id="cardNumber" maxlength="16" pattern="\d{15,16}" placeholder="Enter card number" disabled>
                    </div>
                    <div class="input-box">
                        <label>Exp Month</label>
                        <select name="expMonth" id="expMonth" disabled>
                            <option value="">Choose month</option>
                            <option value="jan">January</option>
                            <option value="feb">February</option>
                            <option value="mar">March</option>
                            <option value="apr">April</option>
                            <option value="may">May</option>
                            <option value="jun">June</option>
                            <option value="jul">July</option>
                            <option value="aug">August</option>
                            <option value="sept">September</option>
                            <option value="oct">October</option>
                            <option value="nov">November</option>
                            <option value="dec">December</option>
                        </select>
                    </div>

                    <div class="flex">
                        <div class="input-box">
                            <label>Exp Year </label>
                            <input type="number" name="expYear" id="expYear" maxlength="4" min="2025" max="2100" placeholder="Enter exp year" disabled>
                        </div>
                        <div class="input-box">
                            <label>CVV</label>
                            <input type="number" name="cvv" id="cvv" maxlength="3" pattern="\d{3}" placeholder="Enter CVV" disabled> 
                        </div>
                    </div>
                </div>
            </div>            
            <button type="submit" class="submitBtn">Proceed to Checkout</button>
        </form>
    </div>
    
    <!-- Display Error for Form Validation -->
    <% String error = (String) request.getAttribute("errorMsg"); %>
    <% if (error != null) { %>
        <div style="color: red; font-weight: bold; margin: 10px 0;">
            <%= error %>
        </div>
    <% } %>
    
    <!-- Payment Method Selection & Card Input -->
    <script>
        document.addEventListener('DOMContentLoaded', function(){
            const paymentMethodInput = document.getElementById("payment_method");
            const cardInputs = [
                document.getElementById("cardOwner"),
                document.getElementById("cardNumber"),
                document.getElementById("expYear"),
                document.getElementById("cvv"),
                document.getElementById("expMonth")
            ];
            
            const paymentButtons = {
                cash: document.getElementById("cashBtn"),
                tng: document.getElementById("tngBtn"),
                visa: document.getElementById("visaBtn"),
                mastercard: document.getElementById("mcBtn")
            };

            // Highlight selected button and remove highlight from others
            function highlightSelected(selectedBtn) {
                Object.values(paymentButtons).forEach(btn => btn.classList.remove("selected"));
                selectedBtn.classList.add("selected");
            }
            
            // Enable/disable card inputs & manage required attribute
            function setPaymentMethod(method) {
                paymentMethodInput.value = method;
                const enable = method === "visa" || method === "mastercard";
                
                // Loop 
                cardInputs.forEach(input => {
                    input.disabled = !enable; // Enable/disable the field
                    input.style.backgroundColor = enable ? "white" : "#f0f0f0"; // Set background color
                    if (!enable) input.value = ""; // Clear if disable
                    input.required = enable; // Toggle required attribute
                });
            }

            // Add click event to each payment method button
            Object.entries(paymentButtons).forEach(([method, btn]) => {
                btn.addEventListener("click", () => {
                    setPaymentMethod(method);      // Enable/disable fields based on method
                    highlightSelected(btn);        // Visually indicate selected meth
                });
            });
        });
    </script>
</body>
</html>