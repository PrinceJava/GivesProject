package com.javaproject.javaprojectthree.service.impl;

import com.javaproject.javaprojectthree.model.Charity;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.javaproject.javaprojectthree.model.OrderDetail;
import com.javaproject.javaprojectthree.service.PaymentService;

import java.util.ArrayList;
import java.util.List;

public class PaymentServiceImpl implements PaymentService {

    Charity charity = null;

    //private static final String CLIENT_ID = "ASZi1Ccgr3gq3AL_Qg046RBD556tg_mADvDUAdX0uB7ysX35FvbN3JzUxh-9ycCy61Ba5qrLcpacuZ8w";
    // private static final String CLIENT_ID = charity.
    private static final String CLIENT_SECRET = "EHczyy5HpMK2emUAPSW8TOfk77oiutx5xd-QdXpfYNTo5sYeDx-I1yZGgfayLYG1kiGgMEuW_DJdC73A";
    private static final String MODE = "sandbox";


    private String getApprovalLink(Payment approvedPayment){
        List<Links> links = approvedPayment.getLinks();
        String approvalLink = null;
        for(Links link : links){
            if(link.getRel().equalsIgnoreCase("approval_url")){
                approvalLink = link.getHref();
            }
        }
        return approvalLink;
    }

    @Override
    public String authorizePayment(OrderDetail orderDetail) throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> transactionList = getTransactionInformation(orderDetail);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(transactionList)
                .setRedirectUrls(redirectUrls)
                .setPayer(payer)
                .setIntent("authorize");

        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        Payment approvedPayment = requestPayment.create(apiContext);

        System.out.println(approvedPayment);

        return getApprovalLink(approvedPayment);
    }

    @Override
    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        Payment payment = new Payment().setId(paymentId);
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return payment.execute(apiContext, paymentExecution);
    }

    @Override
    public Payer getPayerInformation() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Matthew")
                .setLastName("James")
                .setEmail("matjames@paypal.com");
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    @Override
    public RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:8080/cancel.html");
        redirectUrls.setReturnUrl("http://localhost:8080/review_payment");
        return redirectUrls;
    }

    @Override
    public List<Transaction> getTransactionInformation(OrderDetail orderDetail) {
        Details details = new Details();

        details.setShipping(orderDetail.getShipping());
        details.setSubtotal(orderDetail.getSubtotal());
        details.setTax(orderDetail.getTax());

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(orderDetail.getTotal());
        amount.setDetails(details);

        Transaction transaction  = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(orderDetail.getProductName());

        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();

        Item item = new Item();
        item.setCurrency("USD")
                .setName(orderDetail.getProductName())
                .setPrice(orderDetail.getSubtotal())
                .setTax(orderDetail.getTax())
                .setQuantity("1");

        items.add(item);
        itemList.setItems(items);
        transaction.setItemList(itemList);

        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        return transactionList;
    }
}