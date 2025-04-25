<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.Payment, model.BuyerDetail" %>

<!DOCTYPE html>
<html>
<head>
    <title>Thank You</title>
    <style>
        .thank-you { text-align: center; margin-top: 50px; }
        .receipt { margin: 20px auto; padding: 20px; border: 1px solid #ddd; width: 60%; }
        a { color: #4CAF50; }
    </style>
</head>
<body>
    <div class="thank-you">
        <h1>Thank You for Your Order!</h1>
        <div class="receipt">
            <p>Your order ID: <strong>${payment.transactionId}</strong></p>
            <p>A receipt has been sent to: <strong>${buyer.email}</strong></p>
<!--            <p>Your order ID: ${orderId}</p>
            <p>A receipt has been sent to your email.</p> -->
            <p><a href="UserHome.jsp">Return to Home</a></p>
        </div>
    </div>
</body>
</html>