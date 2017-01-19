package team.smms.proxy.operation;

public class MySQLUser {
    String userName;
    String password;
    int accountType;

    public MySQLUser(String userName, String password, int accountType) {
        this.userName = userName;
        this.password = password;
        this.accountType = accountType;
    }

    public MySQLUser() {
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
