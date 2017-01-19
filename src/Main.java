import team.smms.gui.smpack.Login;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        try {
            new Login();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
