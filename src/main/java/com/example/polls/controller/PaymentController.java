package com.example.polls.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.polls.service.StripeService;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private Gson gson;
	
	@Value("${stripe.api.key}")
	private String stripeApiKey;
	
	@PostConstruct
	public void setup() {
		Stripe.apiKey = stripeApiKey;
	}
	
	@Autowired
	private StripeService stripeService;

    static class CreatePayment {
        @SerializedName("items")
        Object[] items;
        public Object[] getItems() {
          return items;
        }
		public void setItems(Object[] items) {
			this.items = items;
		}
      }
      static class CreatePaymentResponse {
        public String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
          this.clientSecret = clientSecret;
        }
		public String getClientSecret() {
			return clientSecret;
		}
		public void setClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
		}
        
      }
      
    @PostMapping("/create-payment-intent")
    public CreatePaymentResponse createPaymentIntent(@RequestBody CreatePayment createPayment) throws StripeException {
    	Stripe.apiKey = stripeApiKey;
        PaymentIntentCreateParams createParams = new PaymentIntentCreateParams.Builder()
        .setCurrency("usd")
        .setAmount(15 * 100L)
        .build();
        // Create a PaymentIntent with the order amount and currency
        PaymentIntent intent = PaymentIntent.create(createParams);
        return new CreatePaymentResponse(intent.getClientSecret());
    }

	public Gson getGson() {
		return gson;
	}

	public void setGson(Gson gson) {
		this.gson = gson;
	}

	public String getStripeApiKey() {
		return stripeApiKey;
	}

	public void setStripeApiKey(String stripeApiKey) {
		this.stripeApiKey = stripeApiKey;
	}

	public StripeService getStripeService() {
		return stripeService;
	}

	public void setStripeService(StripeService stripeService) {
		this.stripeService = stripeService;
	}
    
    
}