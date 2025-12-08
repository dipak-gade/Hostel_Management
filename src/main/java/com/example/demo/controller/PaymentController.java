package com.example.demo.controller;

import com.example.demo.service.PaymentService;
import com.razorpay.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin(origins = "http://localhost:5173") // <-- Vite uses port 5173
public class PaymentController {

	@Autowired
	private PaymentService paymentService;

	@PostMapping("/create-order")
	public String createOrder(@RequestBody OrderRequest request) throws Exception {
		Order order = paymentService.createOrder(request.getAmount());
		return order.toJson().toString();

	}
}

class OrderRequest {
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
