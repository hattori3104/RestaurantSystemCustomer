package model;

import java.util.List;

public class OrderListInfo {

    private int order_id;
    private int product_id;
    private String product_name;
    private String category_name;
    private int product_price;
    private int product_quantity;
    private int product_stock;
    private List<Integer> topping_id;
    private List<String> topping_name;
    private List<Integer> topping_price;
    private List<Integer> topping_quantity;
    private List<Integer> topping_stock;
    private int menu_subtotal;

    public OrderListInfo(int order_id, int product_id, String product_name, String category_name, 
            int product_price, int product_quantity,int product_stock, 
            List<Integer> topping_id, List<String> topping_name, List<Integer> topping_price, 
            List<Integer> topping_quantity, List<Integer> topping_stock, int menu_subtotal) {
        
        this.setOrder_id(order_id);
        this.setProduct_id(product_id);
        this.setProduct_name(product_name);
        this.setCategory_name(category_name);
        this.setProduct_price(product_price);
        this.setProduct_quantity(product_quantity);
        this.setProduct_stock(product_stock);
        this.setTopping_id(topping_id);
        this.setTopping_name(topping_name);
        this.setTopping_price(topping_price);
        this.setTopping_quantity(topping_quantity);
        this.setTopping_stock(topping_stock);
        this.setMenu_subtotal(menu_subtotal);
    }

    public OrderListInfo(int order_id, int product_id, String product_name, String category_name, 
            int product_price, List<Integer> topping_id, List<String> topping_name, 
            List<Integer> topping_price, List<Integer> topping_quantity, int menu_subtotal) {
        
        this.setOrder_id(order_id);
        this.setProduct_id(product_id);
        this.setProduct_name(product_name);
        this.setCategory_name(category_name);
        this.setProduct_price(product_price);
        this.setTopping_id(topping_id);
        this.setTopping_name(topping_name);
        this.setTopping_price(topping_price);
        this.setTopping_quantity(topping_quantity);
        this.setMenu_subtotal(menu_subtotal);
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

    public int getProduct_quantity() {
        return product_quantity;
    }

    public int getProduct_stock() {
        return product_stock;
    }

    public List<Integer> getTopping_id() {
        return topping_id;
    }

    public List<String> getTopping_name() {
        return topping_name;
    }

    public List<Integer> getTopping_price() {
        return topping_price;
    }

    public List<Integer> getTopping_quantity() {
        return topping_quantity;
    }

    public List<Integer> getTopping_stock() {
        return topping_stock;
    }

    public int getMenu_subtotal() {
        return menu_subtotal;
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

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void setProduct_stock(int product_stock) {
        this.product_stock = product_stock;
    }

    public void setTopping_id(List<Integer> topping_id) {
        this.topping_id = topping_id;
    }

    public void setTopping_name(List<String> topping_name) {
        this.topping_name = topping_name;
    }

    public void setTopping_price(List<Integer> topping_price) {
        this.topping_price = topping_price;
    }

    public void setTopping_quantity(List<Integer> topping_quantity) {
        this.topping_quantity = topping_quantity;
    }

    public void setTopping_stock(List<Integer> topping_stock) {
        this.topping_stock = topping_stock;
    }

    public void setMenu_subtotal(int menu_subtotal) {
        this.menu_subtotal = menu_subtotal;
    }
}
