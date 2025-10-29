package com.workouttracker.workouttracker.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.workouttracker.workouttracker.service.WorkoutService;

@RestController
@RequestMapping("/api/stripe")
public class PaymentController {

    @Autowired
    private WorkoutService workoutService; 
    
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
            Long userId = Long.parseLong(request.get("userId"));
            SessionCreateParams params = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("http://localhost:8080/api/stripe/payment-success?session_id={CHECKOUT_SESSION_ID}") //Vill byta denna till en mer lämplig i längden, borde göra en success.html
                    .setCancelUrl("http://localhost:4200/homepage") //Vill byta denna till en mer lämplig i längden, borde göra en cancel.html
                    .addLineItem(
                            SessionCreateParams.LineItem.builder() 
                                .setQuantity(1L) 
                                .setPrice(priceId) 
                                .build())
                    .putMetadata("userId", String.valueOf(userId))
                    .putMetadata("priceId", priceId)
                    .build();

                    Session session = Session.create(params);
                    
                    // Om lyckas så returnar vi till frontenden en url som frontenden sedan följer för att redirecta användaren
                    return ResponseEntity.ok(Map.of("url", session.getUrl()));
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    // Gör en mapping som stripe redirectar till vid lyckat köp
    // Denna mappingen kommer redirecta frontend som vanligt, men innefattar backend logik för att lägga till i databasen vad användaren köpte 
    @GetMapping("/payment-success")
    public ResponseEntity<String> handlePaymentSuccess(@RequestParam("session_id") String sessionId) {
        try {
            Stripe.apiKey = stripeSecretKey;

            Session session = Session.retrieve(sessionId);

            Map<String, String> metadata = session.getMetadata();

            Long userId = Long.parseLong(metadata.get("userId"));
            String priceId = metadata.get("priceId");

            // Kontrollerar att betalning gick igenom (mockad ofc)
            if ("paid".equalsIgnoreCase(session.getPaymentStatus())){
                // Om betalt så kör vi en metod för att lägga till workout beroende på vilken workout man köpt
                workoutService.addBoughtWorkout(userId, priceId);
            } 

            return ResponseEntity.status(302).header("Location", "http://localhost:4200/homepage").build();


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error with payment");
        }
    }

}
