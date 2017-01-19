package team.smms.database.tableclass;

public class ReturnSale {
    private String rsaleID;
    private String commodityID;
    private int number;
    private float money;
    private String returnTime;
    private String operator;

    public String getRsaleID() {
        return rsaleID;
    }

    public void setRsaleID(String rsaleID) {
        this.rsaleID = rsaleID;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public void setCommodityID(String commodityID) {
        this.commodityID = commodityID;
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

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
