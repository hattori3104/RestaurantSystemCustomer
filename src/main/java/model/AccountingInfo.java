package model;

public class AccountingInfo {

    private String tableNo;
    private String totalPrice;

    private int table_id;
    private int total_price;
    
    public AccountingInfo(int table_id,  int total_price) {
        
        this.table_id = table_id;
        this.total_price = total_price;
    }
    
    public int getTable_id() {
        return table_id;
    }
    
    public int getTotal_price() {
        return total_price;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }
    
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public AccountingInfo(String strTableNo, String strTotalPrice) {
        this.setTableNo(strTableNo);
        this.setTotalPrice(strTotalPrice);
    }

    public String getTableNo() {
        return tableNo;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }
}
