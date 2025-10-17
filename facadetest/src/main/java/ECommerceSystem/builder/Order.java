package ECommerceSystem.builder;

public class Order{
    private String item;
    private int quantity;
    private double price;

    public Order(String item, int quantity, double price){
        this.item = item;
        this.quantity = quantity;
        this.price = price;}
    public double getTotal(){return quantity * price;}
    public String toString(){
        return quantity + "x " + item + " = " + getTotal() + " tenge";
    }
}
