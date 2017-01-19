package team.smms.database.tableclass;

public class Account {
    private String managerID;
    private String accountPassword;
    private String managerName;
    private String mobilePhone;
    private String phone;
    private String mangerEmail;
    private int accountType;
    private int accountState;
    private String timeOfError;
    private int countOfError;
    private float earning = 0;
    private float profit = 0;


    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        this.managerID = managerID;
    }

    public String getAccountPassword() {
        return accountPassword;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMangerEmail() {
        return mangerEmail;
    }

    public void setMangerEmail(String mangerEmail) {
        this.mangerEmail = mangerEmail;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public int getAccountState() {
        return accountState;
    }

    public void setAccountState(int accountState) {
        this.accountState = accountState;
    }

    public String getTimeOfError() {
        return timeOfError;
    }

    public void setTimeOfError(String timeOfError) {
        this.timeOfError = timeOfError;
    }

    public int getCountOfError() {
        return countOfError;
    }

    public void setCountOfError(int countOfError) {
        this.countOfError = countOfError;
    }

    public float getEarning() {
        return earning;
    }

    public void setEarning(float earning) {
        this.earning = earning;
    }

    public float getProfit() {
        return profit;
    }

    public void setProfit(float profit) {
        this.profit = profit;
    }
}
