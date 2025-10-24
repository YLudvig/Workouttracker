package com.workouttracker.workouttracker.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@RestController
@RequestMapping("/api/stripe")
public class PaymentController {
    
    // Hämtar min secret key för stripe från application properties som tar det från .env filen 
    @Value("${stripe.secret}")
    private String stripeSecretKey;


    // Konstruktor för vår paymentcontroller 
    public PaymentController(){
        Stripe.apiKey = stripeSecretKey;
    }


    //Mapping för att skapa checkout-session
    //Nedan är i stort sett bara en översättning av https://docs.stripe.com/checkout/quickstart?lang=java#run-server 
    //kombinerat med lärdomar från när denna lösningen testades: https://www.youtube.com/watch?v=BczS-wbxgp4
    //Användning av sysop för att hitta fel 
    //Vi tar in ett priceid från frontend och redirectar till en checkout för den varan 
    @PostMapping("/create-checkout-session")
    @ResponseBody
    public ResponseEntity<Map<String, String>> createCheckoutSession(@RequestBody Map<String, String> request ){
        try{

            Stripe.apiKey = stripeSecretKey;

            
            String priceId = request.get("priceId");
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:4200/webbshop") //Vill byta denna till en mer lämplig i längden, borde göra en success.html
                    .setCancelUrl("http://localhost:4200/homepage") //Vill byta denna till en mer lämplig i längden, borde göra en cancel.html
                    .addLineItem(
                            SessionCreateParams.LineItem.builder() 
                                .setQuantity(1L) 
                                .setPrice(priceId) 
                                .build())
                    .build();

                    Session session = Session.create(params);
                    
                    // Om lyckas så returnar vi till frontenden en url som frontenden sedan följer för att redirecta användaren
                    return ResponseEntity.ok(Map.of("url", session.getUrl()));
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }


}
