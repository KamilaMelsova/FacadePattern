package ECommerceSystem.factory;
public class CreditCardPayment implements Payment{
    public void pay(double amount){
        System.out.println("Paid " + amount + " tenge with Credit Card");
    }
}
