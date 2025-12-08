package com.example.demo.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    public Order createOrder(int amount) throws Exception {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount * 100); // Convert ₹ to paise
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcpt_" + System.currentTimeMillis());

        return client.orders.create(orderRequest);
    }
}
