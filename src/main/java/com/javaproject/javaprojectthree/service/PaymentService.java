package com.javaproject.javaprojectthree.service;

import com.javaproject.javaprojectthree.model.OrderDetail;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;

import java.util.List;

public interface PaymentService {

    public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException;
    public Payer getPayerInformation();
    public RedirectUrls getRedirectURLs();
    public List<Transaction> getTransactionInformation(OrderDetail orderDetail);
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException;
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
//    public String getApprovalLink(Payment approvedPayment);
}