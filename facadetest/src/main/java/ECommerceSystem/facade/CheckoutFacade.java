package ECommerceSystem.facade;
import ECommerceSystem.builder.Order;
import ECommerceSystem.factory.Payment;
import ECommerceSystem.decorators.*;

public class CheckoutFacade{
    public void processOrder(Order order, Payment payment, boolean useDiscount, boolean useCashback, boolean useFraudCheck){
        System.out.println("\n Processing Order: " + order);
        double amount = order.getTotal();
        if (useDiscount){payment = new DiscountDecorator(payment, 10);}
        if (useFraudCheck){payment = new FraudDetectionDecorator(payment);}
        if (useCashback){payment = new CashbackDecorator(payment, 5);}
        payment.pay(amount);
        System.out.println("Order completed successfully!\n");}
}

