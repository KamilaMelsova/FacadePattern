package ECommerceSystem.builder;

public class OrderBuilder{
    private String item;
    private int quantity;
    private double price;

    public OrderBuilder setItem(String item){
        this.item = item;
        return this;}
    public OrderBuilder setQuantity(int quantity){
        this.quantity = quantity;
        return this;}
    public OrderBuilder setPrice(double price){
        this.price = price;
        return this;}
    public Order build(){
        return new Order(item, quantity, price);
    }
}
