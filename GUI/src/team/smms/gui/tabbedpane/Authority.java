package team.smms.gui.tabbedpane;

import team.smms.proxy.operation.MySQLUser;
import team.smms.gui.listener.ExportExcel;
import team.smms.gui.listener.Righthandlistener;
import team.smms.proxy.operation.Operation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Authority extends JTabbedPane{
    private int type;
    private Managepanel managepanel;
    private Operation operation;

    public Authority(int type,Operation operation){
        this.operation=operation;

        this.type=type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif",1,20));

        managepanel=new Managepanel(this.operation);

        this.add("员工管理",managepanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }
}

class  Managepanel extends JPanel implements ActionListener{

    private JPanel northpanel;
    private JPanel centerpanel;
    private JButton searchbutton;
    private JButton addbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JMenuItem menuitem[];
    private JPopupMenu popupmenu;
    private JTable jtable;
    private DefaultTableModel tablemodel=null;
    private Operation operation;

    public Managepanel(Operation operation){
        this.operation=operation;


        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem=new JMenuItem[4];
        menuitem[0]=new JMenuItem("添加表项");
        menuitem[1]=new JMenuItem("修改表项");
        menuitem[2]=new JMenuItem("删除表项");
        menuitem[3]=new JMenuItem("表格导出");
        popupmenu=new JPopupMenu();
        for(int i=0;i<4;i++){
            popupmenu.add(menuitem[i]);
            menuitem[i].addActionListener(this);
        }


        centerpanel=new JPanel(new BorderLayout());
        TitledBorder t1=new TitledBorder(new LineBorder(Color.LIGHT_GRAY,1),"Result");
        t1.setTitleJustification(TitledBorder.RIGHT);
        t1.setTitleColor(Color.lightGray);
        centerpanel.setBorder(t1);
        centerpanel.setOpaque(false);

        northpanel=new JPanel();
        TitledBorder t2=new TitledBorder(new LineBorder(Color.LIGHT_GRAY,1),"Operation");
        t2.setTitleJustification(TitledBorder.RIGHT);
        t2.setTitleColor(Color.lightGray);
        northpanel.setBorder(t2);
        northpanel.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s= new GridBagConstraints();
        s.insets=new Insets(5,5,5,5);
        s.fill = GridBagConstraints.NONE;
        northpanel.setLayout(layout);

        label=new JLabel[5];
        label[0]=new JLabel("员工编号:");
        label[1]=new JLabel("员工姓名:");
        label[2]=new JLabel("手机号码:");
        label[3]=new JLabel("固定电话:");
        label[4]=new JLabel("电子邮件:");

        text=new JTextField[5];
        for(int i=0;i<5;i++)
            text[i]=new JTextField(10);

        addbutton=new JButton("添加");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton=new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton=new JButton("导出");
        exportbutton.setEnabled(false);
        exportbutton.setBackground(Color.lightGray);
        exportbutton.addActionListener(this);

        northpanel.add(label[0]);
        s.gridwidth=1;
        layout.setConstraints(label[0], s);
        northpanel.add(text[0]);
        s.gridwidth=1;
        layout.setConstraints(text[0], s);
        northpanel.add(label[1]);
        s.gridwidth=1;
        layout.setConstraints(label[1], s);
        northpanel.add(text[1]);
        s.gridwidth=1;
        layout.setConstraints(text[1], s);
        northpanel.add(label[2]);
        s.gridwidth=1;
        layout.setConstraints(label[2], s);
        northpanel.add(text[2]);
        s.gridwidth=1;
        layout.setConstraints(text[2], s);
        northpanel.add(label[3]);
        s.gridwidth=1;
        layout.setConstraints(label[3], s);
        northpanel.add(text[3]);
        s.gridwidth=1;
        layout.setConstraints(text[3], s);
        northpanel.add(label[4]);
        s.gridwidth=1;
        layout.setConstraints(text[4], s);
        northpanel.add(text[4]);
        s.gridwidth=1;
        layout.setConstraints(text[4], s);
        northpanel.add(addbutton);
        s.gridwidth=1;
        layout.setConstraints(addbutton, s);
        northpanel.add(searchbutton);
        s.gridwidth=1;
        layout.setConstraints(searchbutton, s);
        northpanel.add(exportbutton);
        s.gridwidth=0;
        layout.setConstraints(exportbutton, s);

        this.add(northpanel,BorderLayout.NORTH);

        tablemodel=new DefaultTableModel();
        jtable=new JTable(tablemodel){
            public Component prepareRenderer(TableCellRenderer renderer,int row,int column){
                Component c=super.prepareRenderer(renderer, row, column);
                if(c instanceof JComponent){
                    ((JComponent)c).setOpaque(false);
                }
                return c;
            }
        };
        jtable.setOpaque(false);
        jtable.addMouseListener(new Righthandlistener(jtable,popupmenu));
        JScrollPane spane=new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane,BorderLayout.CENTER);
        this.add(centerpanel,BorderLayout.CENTER);
    }


    public void search(){
        ArrayList<String> memberName = new ArrayList<String>(10);
        ArrayList<String> matchInformation = new ArrayList<String>(15);
        if(!(text[0].getText().trim().equals(""))){
            memberName.add("managerID");
            matchInformation.add(text[0].getText().trim());
        }
        if(!(text[1].getText().trim().equals(""))){
            memberName.add("managername");
            matchInformation.add(text[1].getText().trim());
        }
        if(!(text[2].getText().trim().equals(""))){
            memberName.add("mobilephone");
            matchInformation.add(text[2].getText().trim());
        }
        if(!(text[3].getText().trim().equals(""))){
            memberName.add("phone");
            matchInformation.add(text[3].getText().trim());
        }
        if(!(text[4].getText().trim().equals(""))){
            memberName.add("manageremail");
            matchInformation.add(text[4].getText().trim());
        }
        memberName.trimToSize();
        matchInformation.trimToSize();
        try {
            this.tablemodel = operation.searchAccount(memberName, matchInformation);
            this.jtable.setModel(this.tablemodel);
        } catch (SQLException e1) {

        } catch (ClassNotFoundException e1) {

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {  //员工管理
        if(e.getSource()==searchbutton||e.getSource()==addbutton){
            exportbutton.setEnabled(true);
            this.search();
        }
        if(e.getSource()==menuitem[0]||e.getSource()==addbutton){
            exportbutton.setEnabled(true);
            JTextField o[] = new JTextField[20];
            o[0] = new JTextField("员工编号:");
            o[0].setEditable(false);
            Operation rootOperation = null;
            try {
                rootOperation = new Operation("root");
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            String managerID="";
            try {
                managerID = rootOperation.getMaxManagerID();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            o[1] = new JTextField(Integer.parseInt(managerID)+1+"");
            //********************************//
            o[1].setEditable(false);
            o[2] = new JTextField("登陆密码:");
            o[2].setEditable(false);
            o[3] = new JTextField();
            o[4] = new JTextField("员工姓名:");
            o[4].setEditable(false);
            o[5] = new JTextField();
            o[6] = new JTextField("手机号码:");
            o[6].setEditable(false);
            o[7] = new JTextField();
            o[8] = new JTextField("固定电话:");
            o[8].setEditable(false);
            o[9] = new JTextField("025-");
            o[10] = new JTextField("电子邮件:");
            o[10].setEditable(false);
            o[11] = new JTextField("@163.com");
            o[12] = new JTextField("登陆类型:");
            o[12].setEditable(false);
            o[13] = new JTextField("4");
            o[14] = new JTextField("登陆状态:");
            o[14].setEditable(false);
            o[15] = new JTextField("1");
            o[16] = new JTextField("最后一次登陆时间:");
            o[16].setEditable(false);
            o[17] = new JTextField("1977");
            o[18] = new JTextField("密码连续错误次数:");
            o[18].setEditable(false);
            o[19] = new JTextField("0");
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "添加信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[10];
                String[] matchInformation = new String[10];
                for (int i = 1; i < 20; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"managerID", "accountPassword", "managerName", "mobilePhone", "Phone", "managerEmail", "accountType", "accountState", "timeOfError", "countOfError"};
                try {
                    boolean flag = operation.addAccount(memberName, matchInformation);

                    rootOperation = new Operation("root");
                    MySQLUser user = new MySQLUser(o[1].getText(),o[3].getText(),Integer.parseInt(o[13].getText()));
                    rootOperation.changeAuthority(1,user);
                    rootOperation.changeAuthority(0,user);

                    this.search();
                    if(!flag) {
                        JOptionPane.showMessageDialog(this,"插入失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this,"插入失败");
                }
            }

        }
        if(e.getSource()==menuitem[1]){
            int row = jtable.getSelectedRow();
            JTextField o[] = new JTextField[20];
            o[0] = new JTextField("员工编号:");
            o[0].setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            o[1].setEditable(false);
            o[2] = new JTextField("登陆密码:");
            o[2].setEditable(false);
            o[3] = new JTextField(tablemodel.getValueAt(row, 1).toString());
            o[4] = new JTextField("员工姓名:");
            o[4].setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            o[6] = new JTextField("手机号码:");
            o[6].setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            o[8] = new JTextField("固定电话:");
            o[8].setEditable(false);
            o[9] = new JTextField(tablemodel.getValueAt(row, 4).toString());
            o[10] = new JTextField("电子邮件:");
            o[10].setEditable(false);
            o[11] = new JTextField(tablemodel.getValueAt(row, 5).toString());
            o[12] = new JTextField("登陆类型:");
            o[12].setEditable(false);
            o[13] = new JTextField(tablemodel.getValueAt(row, 6).toString());
            o[14] = new JTextField("登陆状态:");
            o[14].setEditable(false);
            o[15] = new JTextField(tablemodel.getValueAt(row, 7).toString());
            o[16] = new JTextField("最后一次登陆时间:");
            o[16].setEditable(false);
            o[17] = new JTextField(tablemodel.getValueAt(row, 8).toString());
            o[18] = new JTextField("密码连续错误次数:");
            o[18].setEditable(false);
            o[19] = new JTextField(tablemodel.getValueAt(row, 9).toString());
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "修改信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[10];
                String[] matchInformation = new String[10];
                for (int i = 1; i < 20; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"managerID", "accountPassword", "managerName", "mobilePhone", "phone", "managerEmail", "accountType", "accountState", "timeOfError", "countOfError"};
                try {
                    boolean flag = operation.changeAccount(memberName, matchInformation,tablemodel.getValueAt(row, 0).toString());
                    Operation rootOperation = new Operation("root");
                    MySQLUser user = new MySQLUser(o[1].getText(),o[3].getText(),Integer.parseInt(o[13].getText()));
                    rootOperation.changeAuthority(1,user);
                    rootOperation.changeAuthority(0,user);

                    this.search();
                    if(!flag) {
                        JOptionPane.showMessageDialog(this,"修改失败");
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(this,"修改失败");
                }
            }

        }
        if(e.getSource()==menuitem[2]){
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(10);
            ArrayList<String> matchInformation = new ArrayList<String>(10);
            memberName.add("managerID");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteAccount(memberName, matchInformation);
                Operation rootOperation = new Operation("root");
                MySQLUser user = new MySQLUser(tablemodel.getValueAt(row, 0).toString(),tablemodel.getValueAt(row, 1).toString(),Integer.parseInt(tablemodel.getValueAt(row, 6).toString()));
                rootOperation.changeAuthority(1,user);
                this.search();


                if(!flag) {
                    JOptionPane.showMessageDialog(this,"删除失败");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this,"删除失败");
            }

        }
        if(e.getSource()==exportbutton||e.getSource()==menuitem[3]){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }

    }
}