package ECommerceSystem.decorators;
import ECommerceSystem.factory.Payment;

public class CashbackDecorator extends PaymentDecorator{private double cashback;
    public CashbackDecorator(Payment payment, double cashback){
        super(payment);
        this.cashback = cashback;}
    public void pay(double amount){
        super.pay(amount);
        System.out.println("You got " + (amount * cashback/100) + " tenge cashback");}
}
