package team.smms.database.tableclass;

public class Sale {
    private String saleId;
    private String commodityId;
    private String sellTime;
    private int number;
    private float money;
    private String operator;

    public String getSaleId() {
        return saleId;
    }

    public void setSaleId(String saleid) {
        this.saleId = saleid;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getSellTime() {
        return sellTime;
    }

    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
