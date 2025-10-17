package ECommerceSystem.factory;

public class PaymentFactory{public static Payment createPayment(String type){
        if (type.equalsIgnoreCase("credit")){return new CreditCardPayment();}
        else if (type.equalsIgnoreCase("paypal")){return new PayPalPayment();}
        else{throw new IllegalArgumentException("Unknown payment type: " + type);}
    }
}
