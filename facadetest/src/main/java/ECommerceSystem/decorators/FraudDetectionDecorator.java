package ECommerceSystem.decorators;
import ECommerceSystem.factory.Payment;

public class FraudDetectionDecorator extends PaymentDecorator{
    public FraudDetectionDecorator(Payment payment){
        super(payment);
    }
    public void pay(double amount){
        System.out.println("Checking for fraud before payment...");
        super.pay(amount);
        System.out.println("No fraud detected");
    }
}
