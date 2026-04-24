package model;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryInfo {

    private int order_price;
    private int order_flag;
    private String product_name;
    private int product_quantity;
    List<MultipleToppingInfo> topping_details;

    public OrderHistoryInfo(int order_price, int order_flag, String product_name, int product_quantity,
            List<MultipleToppingInfo> topping_details) {
        this.setOrder_price(order_price);
        this.setOrder_flag(order_flag);
        this.setProduct_name(product_name);
        this.setProduct_quantity(product_quantity);
        this.topping_details = new ArrayList<>(topping_details);
    }

    public int getOrder_price() {
        return order_price;
    }

    public int getOrder_flag() {
        return order_flag;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public List<MultipleToppingInfo> getTopping_details() {
        return topping_details;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public void setOrder_flag(int order_flag) {
        this.order_flag = order_flag;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public void setTopping_details(List<MultipleToppingInfo> topping_details) {
        this.topping_details = new ArrayList<>(topping_details);
    }

    // --- 便利メソッド: トッピングを追加 ---
    public void addTopping(MultipleToppingInfo topping) {
        if (this.topping_details == null) {
            this.topping_details = new ArrayList<>();
        }
        this.topping_details.add(topping);
    }
}
