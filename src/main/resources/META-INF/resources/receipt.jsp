<%@ page language="java" contentType="text/html; ISO-8859-1" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Payment Receipt</title>
    <style type="text/css">
        table{border: 0}
        table td {padding: 10px}
    </style>
</head>
<body>
<div id="checkoutDIV" align="center">
    <h1>Thank you for shopping with us</h1>
        <table>
            <tr>
                <td colspan="2"><b>Transaction Details:</b></td>
                <td></td>
            </tr>
            <tr>
                <td>Merchant:</td>
                <td>Name of Company LLC.</td>
            </tr>
            <tr>
                <td>Payer:</td>
                <td>${payer.firstName} ${payer.lastName}</td>
            </tr>
            <tr>
                <td>Shipping:</td>
                <td>${transaction.amount.details.shipping}</td>
            </tr>
            <tr>
                <td>Tax:</td>
                <td>${transaction.amount.details.tax}</td>
            </tr>
            <tr>
                <td>Total:</td>
                <td>${transaction.amount.total}</td>
            </tr>
            <tr><td><br/></td></tr>
        </table>
</div>
</body>
</html>