<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; ISO-8859-1" %>

<!DOCTYPE html>
<html lang="en" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <title>Check Out</title>
    <style type="text/css">
        table{border: 0}
        table td {padding: 10px}
    </style>
</head>
<body>
<div id="checkoutDIV" align="center">
    <h1>Check Out</h1>
    <form action="authorize_payment" method="post">
        <table>
            <tr>
                <td>Product/Services:</td>
                <td><input type="test" name="product" value="Next iPhone"/></td>
            </tr>
            <tr>
                <td>Sub Total</td>
                <td><input type="test" name="subtotal" value="100" /></td>
            </tr>
            <tr>
                <td>Shipping</td>
                <td><input type="text" name="shipping" value="10" /></td>
            </tr>
            <tr>
                <td>Tax</td>
                <td><input type="text" name="tax" value="10" /></td>
            </tr>
            <tr>
                <td>Total</td>
                <td><input type="text" name="total" value="120"/></td>
            </tr>
            <input type="hidden" name="paymentId" value="${charityId}"/>
            <tr>
                <td>
                    <input type="hidden" >
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Checkout" />
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>