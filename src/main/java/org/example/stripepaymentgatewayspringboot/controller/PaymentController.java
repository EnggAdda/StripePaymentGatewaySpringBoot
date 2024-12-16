package org.example.stripepaymentgatewayspringboot.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("http://localhost:63342")
public class PaymentController {

    @PostMapping("/create-checkout-session")
    public Map<String ,Object> createCheckoutSession() throws StripeException {

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.ALIPAY)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/payment/success")
                .setCancelUrl("http://localhost:8080/api/payment/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount(100L)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Test Product")
                                                                .build()
                                                )
                                                .build()
                                ).setQuantity(1L)
                                .build()
                )
                .build();
        Session session  =Session.create(params);
        Map<String ,Object> result = new HashMap<String ,Object>();
        result.put("sessionId", session.getId());
        return ResponseEntity.ok(result).getBody();
    }

    @GetMapping("/success")
    public String getSuccess(){
        return "payment successful";
    }

    @GetMapping("/cancel")
    public String cancel(){
        return "payment canceled";
    }
}
