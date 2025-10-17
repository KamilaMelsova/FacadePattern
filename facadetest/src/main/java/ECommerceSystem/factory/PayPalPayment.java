package ECommerceSystem.factory;
public class PayPalPayment implements Payment{
    public void pay(double amount){
        System.out.println("Paid " + amount + " tenge via PayPal");
    }
}

