package com.sangspringproject.SANGSpringProject.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(@RequestParam("amount") int amount) throws Exception {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpaySecret);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        return order.toString(); // sends JSON back to frontend
    }
}
