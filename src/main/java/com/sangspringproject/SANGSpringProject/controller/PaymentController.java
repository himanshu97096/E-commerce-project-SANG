package com.sangspringproject.SANGSpringProject.controller;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    private static final String RAZORPAY_KEY_ID = "rzp_test_RIlXdQyidkY1TJ";  // replace with your test key
    private static final String RAZORPAY_SECRET = "HXrZzBMJVcthQ6hEGkaLPhkb";      // replace with your secret

    @PostMapping("/createOrder")
    @ResponseBody
    public String createOrder(@RequestParam("amount") int amount) throws Exception {
        RazorpayClient client = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_SECRET);

        JSONObject options = new JSONObject();
        options.put("amount", amount * 100); // amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + System.currentTimeMillis());

        Order order = client.orders.create(options);

        return order.toString(); // sends JSON back to frontend
    }
}
