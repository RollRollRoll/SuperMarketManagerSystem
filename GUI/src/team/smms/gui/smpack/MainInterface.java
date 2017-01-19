package team.smms.gui.smpack;

import team.smms.gui.tabbedpane.*;
import team.smms.proxy.operation.Operation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class MainInterface extends JFrame implements ActionListener {

    private int type;
    private JScrollPane jscrollpane;
    private JPanel mainpanel, northjpanel, westjpanel;
    private JPanel buttonpanel;
    private JLabel titlelabel, menulabel;
    private JPanel jpanel2, jpanel2_1, jpanel2_2, jpanel2_3;
    private JButton jbutton[];
    JTabbedPane t;
    Operation operation;

    public void dispose() {
        try {
            Operation rootOperation = new Operation("root");
            String[] memberName = { "accountState" };
            String[] updateInformation = { "1" };
            int index = operation.getUser().toString().lastIndexOf("@");
            String key = operation.getUser().toString().substring(0, index);
            rootOperation.changeAccount(memberName, updateInformation, key);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.dispose();
    }

    public MainInterface(int type, Operation operation) {
        super("超市管理系统");
        this.setLayout(new BorderLayout());
        this.setSize(1000, 600);
        // this.setMinimumSize(this.getSize());
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowActivated(WindowEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowClosed(WindowEvent arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void windowClosing(WindowEvent arg0) {
                // TODO Auto-generated method stub
                try {
                    Operation rootOperation = new Operation("root");
                    String[] memberName = { "accountState" };
                    String[] updateInformation = { "1" };
                    int index = operation.getUser().toString().lastIndexOf("@");
                    String key = operation.getUser().toString().substring(0, index);
                    rootOperation.changeAccount(memberName, updateInformation, key);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.exit(0);

            }

            @Override
            public void windowDeactivated(WindowEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowDeiconified(WindowEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowIconified(WindowEvent arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void windowOpened(WindowEvent arg0) {
                // TODO Auto-generated method stub

            }

        });

        UIManager.put("TabbedPane.contentOpaque", false);
        UIManager.put("TabbedPane.selected", Color.white);
        mainpanel = new JPanel(new BorderLayout());
        mainpanel.setBackground(Color.white);
        jscrollpane = new JScrollPane(mainpanel);
        jscrollpane.setBorder(new LineBorder(Color.white));

        this.type = type;

        northjpanel = new JPanel(new BorderLayout());
        northjpanel.setSize(1000, 150);
        northjpanel.setOpaque(false);
        northjpanel.setMinimumSize(northjpanel.getSize());
        titlelabel = new JLabel("      SuperMarket");
        // ImageIcon ic = new ImageIcon("image/title.jpg");
        titlelabel.setOpaque(false);
        titlelabel.setFont(new java.awt.Font("Serif", Font.BOLD + Font.ITALIC, 70));
        northjpanel.add(titlelabel, BorderLayout.WEST);
        // jlabel.setIcon(ic);
        mainpanel.add(northjpanel, BorderLayout.NORTH);

        menulabel = new JLabel("MENU");
        jbutton = new JButton[7];
        jbutton[0] = new JButton("个人中心");
        jbutton[1] = new JButton("查询统计");
        jbutton[2] = new JButton("销售管理");
        jbutton[3] = new JButton("库存管理");
        jbutton[4] = new JButton("基础信息管理");
        jbutton[5] = new JButton("权限管理");
        jbutton[6] = new JButton("系统管理");

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        s.fill = GridBagConstraints.HORIZONTAL;
        s.gridwidth = 0;
        s.weightx = 0;
        s.weighty = 0;
        buttonpanel = new JPanel(layout);
        // buttonpanel.setBackground(Color.lightGray);
        buttonpanel.setOpaque(false);
        menulabel.setBackground(Color.lightGray);
        menulabel.setFont(new java.awt.Font("Serif", Font.BOLD + Font.ITALIC, 25));
        menulabel.setOpaque(false);
        // menulabel.setForeground(Color.GRAY);
        s.insets = new Insets(6, 7, 3, 0);
        s.anchor = GridBagConstraints.PAGE_START;
        layout.setConstraints(menulabel, s);
        buttonpanel.add(menulabel);
        for (int i = 0; i < 7; i++) {
            layout.setConstraints(jbutton[i], s);
            if (i > 2)
                if (this.type > 3)
                    break;
                else if (i > 3)
                    if (this.type > 2)
                        break;
            buttonpanel.add(jbutton[i]);
            jbutton[i].setFont(new java.awt.Font("Serif", Font.BOLD, 20));
            jbutton[i].setForeground(Color.white);
            jbutton[i].addActionListener(this);
            jbutton[i].setBackground(Color.GRAY);
        }
        westjpanel = new JPanel(new BorderLayout()) {
            public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("GUI/image/background2.jpg");
                g.drawImage(icon.getImage(), 0, 0, westjpanel.getSize().width, westjpanel.getSize().height, westjpanel);
            }
        };

        westjpanel.add(buttonpanel, BorderLayout.NORTH);
        // westjpanel.setBackground(Color.lightGray);
        mainpanel.add(westjpanel, BorderLayout.WEST);

        jpanel2 = new JPanel(new BorderLayout()) {
            public void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("GUI/image/background.jpg");
                g.drawImage(icon.getImage(), 0, 0, jpanel2.getSize().width, jpanel2.getSize().height, jpanel2);
            }
        };
        // jpanel2.setBackground(Color.getHSBColor(30,5,5));
        jpanel2.setBackground(Color.white);
        mainpanel.add(jpanel2, BorderLayout.CENTER);

        jpanel2_1 = new JPanel();
        jpanel2_1.setOpaque(false);
        jpanel2_2 = new JPanel();
        jpanel2_2.setOpaque(false);
        jpanel2_3 = new JPanel();
        jpanel2_3.setOpaque(false);

        jpanel2.add(jpanel2_1, BorderLayout.WEST);
        jpanel2.add(jpanel2_2, BorderLayout.SOUTH);
        jpanel2.add(jpanel2_3, BorderLayout.EAST);

        t = new JTabbedPane();
        JPanel p = new JPanel(new BorderLayout());
        JLabel l = new JLabel("                   Welcome");
        l.setOpaque(false);
        l.setFont(new java.awt.Font("Serif", 1, 60));
        l.setForeground(Color.lightGray);
        p.add(l, BorderLayout.CENTER);
        p.setOpaque(false);
        t.add("首页", p);
        t.setFont(new java.awt.Font("Serif", 1, 20));
        jpanel2.add(t, BorderLayout.CENTER);

        this.getContentPane().add(jscrollpane);

        this.setVisible(true);

        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        for (int i = 0; i < 7; i++) {
            jbutton[i].setBorderPainted(true);
            jbutton[i].setForeground(Color.white);
            jbutton[i].setBackground(Color.GRAY);
            if (arg0.getSource() == jbutton[i]) {
                jbutton[i].setBorderPainted(false);
                jbutton[i].setForeground(Color.gray);
                jbutton[i].setBackground(Color.WHITE);
                jpanel2.remove(t);
                switch (i) {
                    case 0:
                        try {
                            t = new SecAccount(type, this.operation, this);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        t.setBorder(new LineBorder(Color.white));
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 1:
                        t = new Check(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 2:
                        t = new Sales(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 3:
                        t = new Inventory(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 4:
                        t = new BasicInformation(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 5:
                        t = new Authority(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                    case 6:
                        t = new Maintenance(type, this.operation);
                        t.setBorder(new LineBorder(Color.white));
                        // this.getContentPane().add(t,BorderLayout.CENTER);
                        jpanel2.add(t, BorderLayout.CENTER);
                        break;
                }
            }
        }

    }

}