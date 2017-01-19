package team.smms.gui.tabbedpane;

import team.smms.gui.listener.ExcelInToDB;
import team.smms.proxy.operation.Operation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Maintenance extends JTabbedPane {
    private int type;
    private DBManagepanel dbmanagepanel;
    private Devpanel devpanel;
    private Operation operation;

    public Maintenance(int type, Operation operation) {
        this.operation = operation;

        this.type = type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif", 1, 20));

        dbmanagepanel = new DBManagepanel(this.operation = operation);
        devpanel = new Devpanel(this.operation = operation);

        this.add("数据库管理", dbmanagepanel);
        this.add("系统信息", devpanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }
}

class DBManagepanel extends JPanel implements ActionListener {
    private Operation operation;
    private JButton jbutton[];

    public DBManagepanel(Operation operation) {
        this.operation = operation;
        jbutton = new JButton[8];
        jbutton[0] = new JButton("导入员工信息表");
        jbutton[1] = new JButton("导入商品信息表");
        jbutton[2] = new JButton("导入供应商信息表");
        jbutton[3] = new JButton("导入销售表");
        jbutton[4] = new JButton("导入销售退货表");
        //jbutton[5] = new JButton("导入向供应商退货表");
        jbutton[5] = new JButton("导入库存信息表");
        jbutton[6] = new JButton("导入销售员业绩表");
        for (int i = 0; i < 7; i++) {
            jbutton[i].addActionListener(this);
            jbutton[i].setBackground(Color.lightGray);
            this.add(jbutton[i]);
        }


        this.setOpaque(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 8; i++) {
            if (e.getSource() == jbutton[i]) {
                switch (i) {
                    case 0:
                        new ExcelInToDB(operation.getCon(), "account");
                        break;
                    case 1:
                        new ExcelInToDB(operation.getCon(), "commodity");
                        break;
                    case 2:
                        new ExcelInToDB(operation.getCon(), "provider");
                        break;
                    case 3:
                        new ExcelInToDB(operation.getCon(), "sale");
                        break;
                    case 4:
                        new ExcelInToDB(operation.getCon(), "returnsale");
                        break;
                    case 5:
                        new ExcelInToDB(operation.getCon(), "returncommodity");
                        break;
                    case 6:
                        new ExcelInToDB(operation.getCon(), "storage");
                        break;
                    case 7:
                        new ExcelInToDB(operation.getCon(), "achievements");
                        break;
                }
            }

        }
    }
}

class Devpanel extends JPanel {

    private JPanel inforpanel;
    private JPanel developerpanel;
    private JPanel thankspanel;
    private JPanel panel;

    public Devpanel(Operation operation) {

        inforpanel = new JPanel(new GridLayout(4, 2));
        inforpanel.setOpaque(false);
        JLabel jlabel1[] = new JLabel[8];
        jlabel1[0] = new JLabel("系统名称:");
        jlabel1[1] = new JLabel("小型超市管理系统");
        jlabel1[2] = new JLabel("版本号:");
        jlabel1[3] = new JLabel("1.0");
        jlabel1[4] = new JLabel("JDK版本:");
        jlabel1[5] = new JLabel("1.8");
        jlabel1[6] = new JLabel("开发工具:");
        jlabel1[7] = new JLabel("Eclipse-mars      Eclipse-neon     Intellij IDEA");
        for (int i = 0; i < 8; i++) {
            jlabel1[i].setOpaque(false);
            jlabel1[i].setFont(new java.awt.Font("Serif", 0, 15));
            inforpanel.add(jlabel1[i]);
        }

        developerpanel = new JPanel(new GridLayout(5, 2));
        developerpanel.setOpaque(false);
        JLabel jlabel2[] = new JLabel[10];
        jlabel2[0] = new JLabel("(按学号排序)");
        jlabel2[1] = new JLabel("");
        jlabel2[2] = new JLabel("学号");
        jlabel2[3] = new JLabel("姓名");
        jlabel2[4] = new JLabel("140801205");
        jlabel2[5] = new JLabel("丁静军");
        jlabel2[6] = new JLabel("140801206");
        jlabel2[7] = new JLabel("范陈锦");
        jlabel2[8] = new JLabel("140801227");
        jlabel2[9] = new JLabel("赵旭辉");
        for (int i = 0; i < 10; i++) {
            jlabel2[i].setPreferredSize(new Dimension(280, 20));
            jlabel2[i].setOpaque(false);
            jlabel2[i].setFont(new java.awt.Font("Serif", 0, 15));
            developerpanel.add(jlabel2[i]);
        }

        thankspanel = new JPanel(new GridLayout(4, 2));
        thankspanel.setOpaque(false);
        JLabel jlabel3[] = new JLabel[8];
        jlabel3[0] = new JLabel("(按学号排序)");
        jlabel3[1] = new JLabel("");
        jlabel3[2] = new JLabel("学号");
        jlabel3[3] = new JLabel("姓名");
        jlabel3[4] = new JLabel("140801201");
        jlabel3[5] = new JLabel("曹翠洁");
        jlabel3[6] = new JLabel("140801203");
        jlabel3[7] = new JLabel("陈豪");
        for (int i = 0; i < 8; i++) {
            jlabel3[i].setPreferredSize(new Dimension(280, 20));
            jlabel3[i].setOpaque(false);
            jlabel3[i].setFont(new java.awt.Font("Serif", 0, 15));
            thankspanel.add(jlabel3[i]);
        }


        panel = new JPanel();
        panel.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.REMAINDER;
        panel.setLayout(layout);

        JExpandablePanel infor = new JExpandablePanel("系统信息", inforpanel);
        s.gridwidth = 0;
        layout.setConstraints(infor, s);
        panel.add(infor);

        JExpandablePanel dev = new JExpandablePanel("开发人员", developerpanel);
        s.gridwidth = 0;
        layout.setConstraints(dev, s);
        panel.add(dev);

        JExpandablePanel thanks = new JExpandablePanel("特别感谢", thankspanel);
        s.gridwidth = 0;
        layout.setConstraints(thanks, s);
        panel.add(thanks);


        this.add(panel);
        this.setOpaque(false);

    }

    class HeaderPanel extends JPanel {

        private boolean Show;
        private JLabel title;
        private JLabel edit;

        public HeaderPanel(String title) {

            this.Show = false;
            this.setLayout(null);
            this.setPreferredSize(new Dimension(850, 45));
            this.setBackground(new Color(245, 245, 245));

            this.title = new JLabel(title);
            this.title.setBounds(20, 10, 1000, 30);
            this.title.setFont(new java.awt.Font("Serif", 0, 15));
            this.title.setOpaque(false);
            this.edit = new JLabel("展开");
            this.edit.setFont(new java.awt.Font("Serif", 0, 15));
            this.edit.setForeground(new Color(100, 149, 237));
            this.edit.setBounds(800, 10, 1000, 30);
            this.edit.setOpaque(false);

            this.add(this.title);
            if (!(this.isShow())) {
                this.add(this.edit);
            }
        }

        public void addlabel() {
            if (!(this.isShow())) {
                this.edit.setText("展开");
            }
        }

        public void deletelabel() {
            if (this.isShow()) {
                this.edit.setText("收起");
            }
        }

        public boolean isShow() {
            return Show;
        }

        public void setShow(boolean show) {
            Show = show;
        }

    }

    class ContentPanel extends JPanel {

        private boolean Showing;

        public ContentPanel(JPanel jpanel) {

            this.setBackground(Color.WHITE);
            this.add(jpanel);
            this.setVisible(false);
        }

        public boolean isShowing() {
            return Showing;
        }

        public void setShowing(boolean showing) {
            Showing = showing;
        }

    }

    class JExpandablePanel extends JPanel implements MouseListener {
        private static final long serialVersionUID = 1L;
        private HeaderPanel headerPanel;
        private ContentPanel contentPanel;

        public JExpandablePanel(String title, JPanel jpanel) {
            super();
            initComponents(title, jpanel);
        }

        private void initComponents(String title, JPanel jpanel) {
            this.setLayout(new BorderLayout());
            headerPanel = new HeaderPanel(title);
            if (!(title.equals("ID")))
                headerPanel.addMouseListener(this);
            contentPanel = new ContentPanel(jpanel);
            this.add(headerPanel, BorderLayout.NORTH);
            this.add(contentPanel, BorderLayout.CENTER);
            this.setBorder(new LineBorder(Color.lightGray));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == headerPanel) {
                headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                if (!(headerPanel.isShow()))
                    headerPanel.setBackground(new Color(250, 250, 250));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == headerPanel) {
                if (!(headerPanel.isShow()))
                    headerPanel.setBackground(new Color(245, 245, 245));
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
            // TODO Auto-generated method stub
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // TODO Auto-generated method stub
            if (e.getSource() == headerPanel && e.getButton() == 1) {
                operation();
            }

        }

        public void operation() {

            if (contentPanel.isShowing()) {
                headerPanel.setShow(false);
                contentPanel.setShowing(false);
                contentPanel.setVisible(false);
                headerPanel.setBackground(new Color(250, 250, 250));
                headerPanel.addlabel();
            } else {
                headerPanel.setShow(true);
                contentPanel.setShowing(true);
                headerPanel.setBackground(Color.white);
                contentPanel.setVisible(true);
                headerPanel.deletelabel();
            }
            this.repaint();

        }
    }
}