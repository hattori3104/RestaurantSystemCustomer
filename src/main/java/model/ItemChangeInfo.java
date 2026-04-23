package model;

public class ItemChangeInfo {

    private int order_id;
    private int product_id;
    private String product_name;
    private String category_name;
    private int product_price;
    private int[] topping_id;
    private String[] topping_name;
    private int[] topping_price;
    private int[] topping_quantity;
    private int[] topping_stock;
    private int subtotal;

    public ItemChangeInfo(int order_id, int product_id, String product_name, String category_name, 
            int product_price, int[] topping_id, String[] topping_name, int[] topping_price, 
            int[] topping_quantity, int[] topping_stock, int subtotal) {
        
        this.setOrder_id(order_id);
        this.setProduct_id(product_id);
        this.setProduct_name(product_name);
        this.setCategory_name(category_name);
        this.setProduct_price(product_price);
        this.setTopping_id(topping_id);
        this.setTopping_name(topping_name);
        this.setTopping_price(topping_price);
        this.setTopping_quantity(topping_quantity);
        this.setTopping_stock(topping_stock);
        this.setSubtotal(subtotal);
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public int[] getTopping_id() {
        return topping_id;
    }

    public String[] getTopping_name() {
        return topping_name;
    }

    public int[] getTopping_price() {
        return topping_price;
    }

    public int[] getTopping_quantity() {
        return topping_quantity;
    }
    
    public int[] getTopping_stock() {
        return topping_stock;
    }

    public int getSubtotal() {
        return subtotal;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public void setTopping_id(int[] topping_id) {
        this.topping_id = topping_id;
    }

    public void setTopping_name(String[] topping_name) {
        this.topping_name = topping_name;
    }

    public void setTopping_price(int[] topping_price) {
        this.topping_price = topping_price;
    }

    public void setTopping_quantity(int[] topping_quantity) {
        this.topping_quantity = topping_quantity;
    }
    
    public void setTopping_stock(int[] topping_stock) {
        this.topping_stock = topping_stock;
    }

    public void setSubtotal(int subtotal) {
        this.subtotal = subtotal;
    }
}
