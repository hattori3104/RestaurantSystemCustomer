package model;

public class MultipleToppingInfo {

    private String topping_name;
    private int topping_quantity;

    public MultipleToppingInfo(String topping_name, int topping_quantity) {
        this.setTopping_name(topping_name);
        this.setTopping_quantity(topping_quantity);
    }

    public String getTopping_name() {
        return topping_name;
    }

    public int getTopping_quantity() {
        return topping_quantity;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }

    public void setTopping_quantity(int topping_quantity) {
        this.topping_quantity = topping_quantity;
    }

}
