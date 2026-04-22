package model;

public class ToppingInfo {

    private int topping_id;
    private String topping_name;
    private int topping_price;
    private int topping_stock;
    private int topping_display_flag;

    public ToppingInfo(int topping_id, String topping_name, int topping_price, 
            int topping_stock, int topping_display_flag) {
        
        this.setTopping_id(topping_id);
        this.setTopping_name(topping_name);
        this.setTopping_price(topping_price);
        this.setTopping_stock(topping_stock);
        this.setTopping_display_flag(topping_display_flag);
    }

    public int getTopping_id() {
        return topping_id;
    }

    public String getTopping_name() {
        return topping_name;
    }

    public int getTopping_price() {
        return topping_price;
    }

    public int getTopping_stock() {
        return topping_stock;
    }

    public int getTopping_display_flag() {
        return topping_display_flag;
    }

    public void setTopping_id(int topping_id) {
        this.topping_id = topping_id;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }

    public void setTopping_price(int topping_price) {
        this.topping_price = topping_price;
    }

    public void setTopping_stock(int topping_stock) {
        this.topping_stock = topping_stock;
    }

    public void setTopping_display_flag(int topping_display_flag) {
        this.topping_display_flag = topping_display_flag;
    }
}
