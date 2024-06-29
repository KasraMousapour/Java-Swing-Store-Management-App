package model;


public class Sale {
    private int id;
    private float totalCost;
    private String sellerUsername, date;
    private int number;

    public Sale(int id, float totalCost, String sellerUsername, String date, int number) {
        this.id = id;
        this.totalCost = totalCost;
        this.sellerUsername = sellerUsername;
        this.date = date;
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
