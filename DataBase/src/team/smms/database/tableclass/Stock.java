package team.smms.database.tableclass;

/**
 * Created by kgfan on 2016/12/13.
 */
public class Stock {
    private String stockID;
    private String commodityName;
    private float saleprice;
    private int number;
    private int downLimit;

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public float getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(float saleprice) {
        this.saleprice = saleprice;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getDownLimit() {
        return downLimit;
    }

    public void setDownLimit(int downLimit) {
        this.downLimit = downLimit;
    }
}
