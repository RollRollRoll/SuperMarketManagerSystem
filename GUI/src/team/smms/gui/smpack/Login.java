package team.smms.gui.smpack;

import team.smms.proxy.operation.Operation;
import team.smms.database.tableclass.Account;
import team.smms.gui.listener.Textfieldlistener;
import team.smms.gui.listener.YZlistener;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.ListIterator;


public class Login extends JFrame implements ActionListener {

    private JPanel jpanel, panel1, panel2, panel3;
    private JPanel logpanel;
    private JTextField IDtext, YZtext;
    private JPasswordField PWtext;
    private JLabel IDlabel, PWlabel, YZlabel, YZimagelabel;
    private JButton logbutton;
    private YZlistener yzlistener;
    private Textfieldlistener textfieldlistener;
    private Pic pic = null;
    private Operation operation = new Operation("root");

    public Login() throws SQLException, ClassNotFoundException {
        super("超市管理系统");
        pic = new Pic();
        pic.setBackground(Color.WHITE);
        this.setSize(500, 320);
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.WHITE);
        this.setLayout(new BorderLayout());
        this.getContentPane().add(pic, BorderLayout.CENTER);

        YZimagelabel = new JLabel();
        YZimagelabel.setEnabled(false);
        YZimagelabel.setBackground(Color.WHITE);
        YZimagelabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        jpanel = new JPanel(new BorderLayout());
        jpanel.setBackground(Color.WHITE);
        logpanel = new JPanel(new GridLayout(1, 3));
        logpanel.setBackground(Color.WHITE);
        panel1 = new JPanel(new GridLayout(3, 1));
        panel2 = new JPanel(new GridLayout(3, 1));
        panel3 = new JPanel();
        panel1.setBackground(Color.WHITE);
        panel2.setBackground(Color.WHITE);
        panel3.setBackground(Color.WHITE);
        IDlabel = new JLabel("User:  ");
        IDlabel.setHorizontalAlignment(SwingConstants.RIGHT);
        PWlabel = new JLabel("密码:  ");
        PWlabel.setHorizontalAlignment(SwingConstants.RIGHT);
        YZlabel = new JLabel("验证码:");
        YZlabel.setHorizontalAlignment(SwingConstants.RIGHT);
        IDtext = new JTextField("UID");
        String idstr = IDtext.getText();
        textfieldlistener = new Textfieldlistener(IDtext, idstr);
        IDtext.addFocusListener(textfieldlistener);
        YZtext = new JTextField("验证码");
        PWtext = new JPasswordField();
        panel1.add(IDlabel);
        panel2.add(IDtext);
        panel1.add(PWlabel);
        panel2.add(PWtext);
        panel1.add(YZlabel);
        panel2.add(YZtext);
        panel3.add(YZimagelabel);
        logpanel.add(panel1);
        logpanel.add(panel2);
        logpanel.add(panel3);
        jpanel.add(logpanel, BorderLayout.CENTER);

        yzlistener = new YZlistener(YZimagelabel, YZtext);
        YZimagelabel.addMouseListener(yzlistener);
        YZtext.addFocusListener(yzlistener);

        JPanel buttonpanel = new JPanel();
        buttonpanel.setBackground(Color.WHITE);
        logbutton = new JButton("登录");
        logbutton.addActionListener(this);
        logbutton.setBackground(Color.white);
        buttonpanel.add(logbutton);
        jpanel.add(buttonpanel, BorderLayout.SOUTH);
        this.getContentPane().add(jpanel, BorderLayout.SOUTH);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    public boolean timeIsUp(String timeOfError) {
        Calendar now = Calendar.getInstance();
        int thisYear = now.get(Calendar.YEAR);
        int thisMonth = now.get(Calendar.MONTH) + 1;
        int thisDay = now.get(Calendar.DAY_OF_MONTH);
        String[] errorDay = timeOfError.split("-");
        if (thisYear > Integer.parseInt(errorDay[0])) return true;
        if (thisYear == Integer.parseInt(errorDay[0]) && thisMonth > Integer.parseInt(errorDay[1])) return true;
        if (thisYear == Integer.parseInt(errorDay[0]) && thisMonth == Integer.parseInt(errorDay[1]) && thisDay > Integer.parseInt(errorDay[2]))
            return true;
        return false;
    }

    public String giveMessage(String ID, String PW) {
        String tableName = "Account";
        String[] memberName = {"managerID"};
        String[] matchInformation = {ID};
        List<Account> list = null;
        try {
            list = operation.<Account>search(tableName, memberName, matchInformation);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ListIterator<Account> listIterator = list.listIterator();
        Account temp = null;
        while (listIterator.hasNext()) {
            temp = listIterator.next();
        }
        if (temp == null) return "用户名错误！";
        if (temp != null) {
            if (timeIsUp(temp.getTimeOfError())) {
                String[] tmemberName = {"countOfError", "accountState"};
                String[] updateInformation = {"0", "1"};
                try {
                    operation.changeAccount(tmemberName, updateInformation, ID);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    list = operation.<Account>search(tableName, memberName, matchInformation);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    temp = listIterator.next();
                }
            }
            if (temp.getAccountState() == 3 && !timeIsUp(temp.getTimeOfError())) return "您的账户已冻结,今日禁止登录！";
            else {
                if (temp.getAccountPassword().equals(PW)) {
                    Calendar now = Calendar.getInstance();
                    int thisYear = now.get(Calendar.YEAR);
                    int thisMonth = now.get(Calendar.MONTH) + 1;
                    int thisDay = now.get(Calendar.DAY_OF_MONTH);
                    String timeOfError = String.valueOf(thisYear) + "-" + String.valueOf(thisMonth) + "-" + String.valueOf(thisDay);
                    String[] tmemberName = {"accountState", "countOfError", "timeOfError"};
                    String[] updateInformation = {"2", "0", timeOfError};
                    try {
                        operation.changeAccount(tmemberName, updateInformation, ID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    int type = temp.getAccountType();
                    Operation operation = new Operation(ID, PW);
                    new MainInterface(type, operation);
                    this.dispose();
                    return "登录成功！";
                } else {
                    Calendar now = Calendar.getInstance();
                    int thisYear = now.get(Calendar.YEAR);
                    int thisMonth = now.get(Calendar.MONTH) + 1;
                    int thisDay = now.get(Calendar.DAY_OF_MONTH);
                    String timeOfError = String.valueOf(thisYear) + "-" + String.valueOf(thisMonth) + "-" + String.valueOf(thisDay);
                    int countOfError = temp.getCountOfError() + 1;
                    System.out.println(countOfError);
                    String accountState = "1";
                    if (countOfError >= 5) {
                        countOfError = 5;
                        accountState = "3";
                    }
                    String[] tmemberName = {"timeOfError", "countOfError", "accountState"};
                    String[] updateInformation = {timeOfError, String.valueOf(countOfError), accountState};
                    try {
                        operation.changeAccount(tmemberName, updateInformation, ID);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (countOfError == 5) return "您的账户已冻结,今日禁止登录！";
                    return "密码错误,今日还有" + (5 - countOfError) + "次登录机会";
                }
            }
        }
        return "未知错误";
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        if (arg0.getSource() == logbutton) {
            String str = "";
            try {
                if (IDtext.getText().trim().equals("") || IDtext.getText().trim().equals("UID"))
                    str = "请输入ID";
                else if ((new String(PWtext.getPassword())).equals(""))
                    str = "请输入密码";
                else if (YZtext.getText().trim().equals("") || YZtext.getText().trim().equals("验证码"))
                    str = "请输入验证码";
                else if (!(YZtext.getText().trim().toLowerCase().equals(yzlistener.getYzm())))
                    str = "验证码错误";
                if (!str.equals("")) {
                    JOptionPane.showMessageDialog(this, str);
                    return;
                }
                str = giveMessage(IDtext.getText(), PWtext.getText());
                if (str.equals("登录成功！")) return;
                JOptionPane.showMessageDialog(this, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}


class Pic extends JPanel {

    Image image = null;

    public void paint(Graphics g) {
        try {
            image = ImageIO.read(new File("GUI/image/land.JPG"));
            g.drawImage(image, 5, 5, 480, 170, null);
        } catch (Exception e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
}