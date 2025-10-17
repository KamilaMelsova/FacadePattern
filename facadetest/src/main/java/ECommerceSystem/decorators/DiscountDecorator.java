package ECommerceSystem.decorators;
import ECommerceSystem.factory.Payment;

public class DiscountDecorator extends PaymentDecorator{private double discount;
    public DiscountDecorator(Payment payment, double discount){
        super(payment);
        this.discount = discount;}
    public void pay(double amount){
        double newAmount = amount * (1 - discount/100);
        System.out.println("Discount " + discount + "% applied. New total: " + newAmount + " tenge");
        super.pay(newAmount);}
}
