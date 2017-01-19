package team.smms.gui.tabbedpane;

import team.smms.gui.listener.*;
import team.smms.proxy.operation.Operation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class Sales extends JTabbedPane {

    private int type;
    private ComSalepanel comsalepanel;
    private ReSalepanel resalepanel;
    private SaleManpanel salemanpanel;
    private Operation operation;

    public Sales(int type, Operation operation) {
        this.operation = operation;

        this.type = type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif", 1, 20));

        comsalepanel = new ComSalepanel(this.operation = operation);
        resalepanel = new ReSalepanel(this.operation = operation);
        salemanpanel = new SaleManpanel(this.operation = operation);

        this.add("商品销售信息", comsalepanel);
        this.add("顾客退货", resalepanel);
        this.add("销售员业绩", salemanpanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }
}

class ComSalepanel extends JPanel implements ActionListener {

    private JPanel northpanel;
    private JPanel searchpanel1;
    private JPanel searchpanel2;
    private JPanel searchpanel3;
    private JPanel centerpanel;
    private JPanel buttonpanel;

    private JButton searchbutton;
    private JButton addbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JMenuItem menuitem[];
    private JPopupMenu popupmenu;
    private JTable jtable;
    private DefaultTableModel tablemodel;
    private Operation operation;

    public ComSalepanel(Operation operation) {
        this.operation = operation;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem = new JMenuItem[5];
        menuitem[0] = new JMenuItem("添加表项");
        menuitem[1] = new JMenuItem("修改表项");
        menuitem[2] = new JMenuItem("删除表项");
        menuitem[3] = new JMenuItem("显示图片");
        menuitem[4] = new JMenuItem("表格导出");
        popupmenu = new JPopupMenu();
        for (int i = 0; i < 5; i++) {
            popupmenu.add(menuitem[i]);
            menuitem[i].addActionListener(this);
        }


        centerpanel = new JPanel(new BorderLayout());
        TitledBorder t1 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Result");
        t1.setTitleJustification(TitledBorder.RIGHT);
        t1.setTitleColor(Color.lightGray);
        centerpanel.setBorder(t1);
        centerpanel.setOpaque(false);

        northpanel = new JPanel();
        TitledBorder t2 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Operation");
        t2.setTitleJustification(TitledBorder.RIGHT);
        t2.setTitleColor(Color.lightGray);
        northpanel.setBorder(t2);
        northpanel.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.VERTICAL;
        northpanel.setLayout(layout);

        label = new JLabel[6];
        label[0] = new JLabel("销售票号:");
        label[1] = new JLabel("商品批号:");
        label[2] = new JLabel("商品名称:");
        label[3] = new JLabel("销售员:");
        label[4] = new JLabel("销售日期:");
        label[5] = new JLabel("至");
        for (int i = 0; i < 6; i++) {
            label[i].setOpaque(false);
            label[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        text = new JTextField[6];

        GridBagLayout northlayout = new GridBagLayout();
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 5, 5, 5);
        n.fill = GridBagConstraints.HORIZONTAL;

        searchpanel1 = new JPanel();
        searchpanel1.setLayout(northlayout);
        searchpanel1.setOpaque(false);
        searchpanel1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel1.add(label[0]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[0], n);
        text[0] = new JTextField(10);
        searchpanel1.add(text[0]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[0], n);
        searchpanel1.add(label[1]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[1], n);
        text[1] = new JTextField(10);
        searchpanel1.add(text[1]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[1], n);
        northpanel.add(searchpanel1);
        s.gridwidth = 3;
        layout.setConstraints(searchpanel1, s);

        searchpanel2 = new JPanel();
        searchpanel2.setLayout(northlayout);
        searchpanel2.setOpaque(false);
        searchpanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel2.add(label[2]);
        n.gridwidth = 2;
        northlayout.setConstraints(label[2], n);
        text[2] = new JTextField(10);
        searchpanel2.add(text[2]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[2], n);
        searchpanel2.add(label[3]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[3], n);
        text[3] = new JTextField(10);
        searchpanel2.add(text[3]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[3], n);
        northpanel.add(searchpanel2);
        s.gridwidth = 3;
        layout.setConstraints(searchpanel2, s);

        searchpanel3 = new JPanel();
        searchpanel3.setLayout(northlayout);
        searchpanel3.setOpaque(false);
        searchpanel3.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel3.add(label[4]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[4], n);
        text[4] = new JTextField(10);
        searchpanel3.add(text[4]);
        n.gridwidth = 1;
        northlayout.setConstraints(text[4], n);
        searchpanel3.add(label[5]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[5], n);
        text[5] = new JTextField(10);
        searchpanel3.add(text[5]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[5], n);
        northpanel.add(searchpanel3);
        s.gridwidth = 6;
        layout.setConstraints(searchpanel3, s);

        for (int i = 4; i < 6; i++) {
            text[i].addMouseListener(new Datelistener(text[i]));
            text[i].setEnabled(false);
        }

        addbutton=new JButton("添加");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton=new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagLayout buttonlayout = new GridBagLayout();
        GridBagConstraints b= new GridBagConstraints();
        b.insets=new Insets(2,2,2,2);
        b.fill = GridBagConstraints.HORIZONTAL;
        buttonpanel=new JPanel(buttonlayout);
        buttonpanel.setOpaque(false);
        buttonpanel.add(addbutton);
        b.gridwidth=1;
        buttonlayout.setConstraints(addbutton, b);
        buttonpanel.add(searchbutton);
        b.gridwidth=1;
        buttonlayout.setConstraints(searchbutton, b);
        buttonpanel.add(exportbutton);
        b.gridwidth=0;
        buttonlayout.setConstraints(exportbutton, b);

        northpanel.add(buttonpanel);
        s.gridwidth = 0;
        layout.setConstraints(searchbutton, s);

        this.add(northpanel, BorderLayout.NORTH);

        tablemodel = new DefaultTableModel();
        jtable = new JTable(tablemodel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        jtable.setOpaque(false);
        jtable.addMouseListener(new Righthandlistener(jtable, popupmenu));
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {   //销售表管理
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(7);
            ArrayList<String> matchInformation = new ArrayList<String>(14);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("saleid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("commodityid");
                matchInformation.add(text[1].getText().trim());
            }
            if (!(text[2].getText().trim().equals(""))) {
                memberName.add("commodityname");
                matchInformation.add(text[2].getText().trim());
            }
            if (!(text[3].getText().trim().equals(""))) {
                memberName.add("operator");
                matchInformation.add(text[3].getText().trim());
            }
            if (!((text[4].getText().trim().equals("")) && (text[5].getText().trim().equals("")))) {
                memberName.add("selltime");
                matchInformation.add(text[4].getText().trim());
                matchInformation.add(text[5].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = operation.searchSale(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询商品销售信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询商品销售信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0]||e.getSource() == addbutton) {
            exportbutton.setEnabled(true);
            Object o[] = new Object[12];
            o[0] = new JTextField("销售票号:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(operation.buildKey("Sale"));
            ((JTextField)o[1]).setEditable(false);
            o[2] = new JTextField("商品批号:");
            ((JTextField)o[2]).setEditable(false);
            try {
                o[3] = new JComboBox(operation.getAllCommodityID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[4] = new JTextField("销售日期:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField();
            ((JTextField)o[5]).setEnabled(false);
            ((JTextField)o[5]).addMouseListener(new Datelistener((JTextField)o[5]));
            o[6] = new JTextField("销售数量:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField();
            o[8] = new JTextField("收入(元):");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField();
            o[10] = new JTextField("销售员:");
            ((JTextField)o[10]).setEditable(false);
            try {
                o[11] = new JTextField(operation.getUserNmae());
                ((JTextField)o[11]).setEditable(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "添加信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[6];
                String[] matchInformation = new String[6];
                for (int i = 1; i < 12; i++) {
                    if(i == 3) {
                        matchInformation[i / 2] = ((JComboBox) o[i]).getSelectedItem().toString().trim();
                        System.out.println(matchInformation[i/2]);
                    }
                    else if (i % 2 == 1)
                        matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                }
                memberName = new String[]{"saleid", "commodityid", "selltime", "number", "money", "operator"};
                try {
                    boolean flag = operation.addSale(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "添加成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllSale();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "添加商品销售信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "添加商品销售信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                } catch(Exception e1){
                    JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            Object o[] = new Object[12];
            o[0] = new JTextField("销售票号:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            ((JTextField)o[1]).setEditable(false);
            o[2] = new JTextField("商品批号:");
            ((JTextField)o[2]).setEditable(false);
            try {
                o[3] = new JComboBox(operation.getAllCommodityID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[4] = new JTextField("销售日期:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            ((JTextField)o[5]).setEnabled(false);
            ((JTextField)o[5]).addMouseListener(new Datelistener((JTextField)o[5]));
            o[6] = new JTextField("销售数量:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            o[8] = new JTextField("收入(元):");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField(tablemodel.getValueAt(row, 4).toString());
            o[10] = new JTextField("销售员:");
            ((JTextField)o[10]).setEditable(false);
            try {
                o[11] = new JTextField(operation.getUserNmae());
                ((JTextField)o[11]).setEditable(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "修改信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[6];
                String[] matchInformation = new String[6];
                for (int i = 1; i < 12; i++) {
                    if(i == 3)
                        matchInformation[i / 2] = ((JComboBox)o[i]).getSelectedItem().toString().trim();
                    else if (i % 2 == 1)
                        matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                }
                memberName = new String[]{"saleid", "commodityid", "selltime", "number", "money", "operator"};
                try {
                    boolean flag = operation.changeSale(memberName, matchInformation, tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "修改成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllSale();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "修改商品销售信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "修改商品销售信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(6);
            ArrayList<String> matchInformation = new ArrayList<String>(6);
            memberName.add("saleid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteSale(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "删除成功", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllSale();
                    this.jtable.setModel(tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "删除商品销售信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "删除商品销售信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==menuitem[3]){
            try {
                new CommodityPic(operation,jtable,1);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null,"没有找到图片");
            }
        }
        if(e.getSource()==exportbutton||e.getSource()==menuitem[4]){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }
    }
}

class ReSalepanel extends JPanel implements ActionListener {

    private JPanel northpanel;
    private JPanel searchpanel1;
    private JPanel searchpanel2;
    private JPanel searchpanel3;
    private JPanel centerpanel;
    private JPanel buttonpanel;

    private JButton searchbutton;
    private JButton addbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JMenuItem menuitem[];
    private JPopupMenu popupmenu;
    private JTable jtable;
    private DefaultTableModel tablemodel;
    private Operation operation;

    public ReSalepanel(Operation operation) {
        this.operation = operation;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem = new JMenuItem[5];
        menuitem[0] = new JMenuItem("添加表项");
        menuitem[1] = new JMenuItem("修改表项");
        menuitem[2] = new JMenuItem("删除表项");
        menuitem[3] =new JMenuItem("显示图片");
        menuitem[4] =new JMenuItem("导出表格");
        popupmenu = new JPopupMenu();
        for (int i = 0; i < 5; i++) {
            popupmenu.add(menuitem[i]);
            menuitem[i].addActionListener(this);
        }


        centerpanel = new JPanel(new BorderLayout());
        TitledBorder t1 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Result");
        t1.setTitleJustification(TitledBorder.RIGHT);
        t1.setTitleColor(Color.lightGray);
        centerpanel.setBorder(t1);
        centerpanel.setOpaque(false);

        northpanel = new JPanel();
        TitledBorder t2 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Search");
        t2.setTitleJustification(TitledBorder.RIGHT);
        t2.setTitleColor(Color.lightGray);
        northpanel.setBorder(t2);
        northpanel.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.VERTICAL;
        northpanel.setLayout(layout);

        label = new JLabel[6];
        label[0] = new JLabel("退货票号:");
        label[1] = new JLabel("商品批号:");
        label[2] = new JLabel("商品名称:");
        label[3] = new JLabel("操作员:");
        label[4] = new JLabel("退货时间:");
        label[5] = new JLabel("至");
        for (int i = 0; i < 6; i++) {
            label[i].setOpaque(false);
            label[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        text = new JTextField[6];

        GridBagLayout northlayout = new GridBagLayout();
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 5, 5, 5);
        n.fill = GridBagConstraints.HORIZONTAL;

        searchpanel1 = new JPanel();
        searchpanel1.setLayout(northlayout);
        searchpanel1.setOpaque(false);
        searchpanel1.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel1.add(label[0]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[0], n);
        text[0] = new JTextField(10);
        searchpanel1.add(text[0]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[0], n);
        searchpanel1.add(label[1]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[1], n);
        text[1] = new JTextField(10);
        searchpanel1.add(text[1]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[1], n);
        northpanel.add(searchpanel1);
        s.gridwidth = 3;
        layout.setConstraints(searchpanel1, s);

        searchpanel2 = new JPanel();
        searchpanel2.setLayout(northlayout);
        searchpanel2.setOpaque(false);
        searchpanel2.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel2.add(label[2]);
        n.gridwidth = 2;
        northlayout.setConstraints(label[2], n);
        text[2] = new JTextField(10);
        searchpanel2.add(text[2]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[2], n);
        searchpanel2.add(label[3]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[3], n);
        text[3] = new JTextField(10);
        searchpanel2.add(text[3]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[3], n);
        northpanel.add(searchpanel2);
        s.gridwidth = 3;
        layout.setConstraints(searchpanel2, s);

        searchpanel3 = new JPanel();
        searchpanel3.setLayout(northlayout);
        searchpanel3.setOpaque(false);
        searchpanel3.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel3.add(label[4]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[4], n);
        text[4] = new JTextField(10);
        searchpanel3.add(text[4]);
        n.gridwidth = 1;
        northlayout.setConstraints(text[4], n);
        searchpanel3.add(label[5]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[5], n);
        text[5] = new JTextField(10);
        searchpanel3.add(text[5]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[5], n);
        northpanel.add(searchpanel3);
        s.gridwidth = 6;
        layout.setConstraints(searchpanel3, s);

        for (int i = 4; i < 6; i++) {
            text[i].addMouseListener(new Datelistener(text[i]));
            text[i].setEnabled(false);
        }


        addbutton=new JButton("添加");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton=new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagLayout buttonlayout = new GridBagLayout();
        GridBagConstraints b= new GridBagConstraints();
        b.insets=new Insets(2,2,2,2);
        b.fill = GridBagConstraints.HORIZONTAL;
        buttonpanel=new JPanel(buttonlayout);
        buttonpanel.setOpaque(false);
        buttonpanel.add(addbutton);
        b.gridwidth=1;
        buttonlayout.setConstraints(addbutton, b);
        buttonpanel.add(searchbutton);
        b.gridwidth=1;
        buttonlayout.setConstraints(searchbutton, b);
        buttonpanel.add(exportbutton);
        b.gridwidth=0;
        buttonlayout.setConstraints(exportbutton, b);

        northpanel.add(buttonpanel);
        s.gridwidth = 0;
        layout.setConstraints(searchbutton, s);

        this.add(northpanel, BorderLayout.NORTH);

        tablemodel = new DefaultTableModel();
        jtable = new JTable(tablemodel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        jtable.setOpaque(false);
        jtable.addMouseListener(new Righthandlistener(jtable, popupmenu));
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {  //销售退货管理
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(7);
            ArrayList<String> matchInformation = new ArrayList<String>(14);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("rsaleid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("commodityid");
                matchInformation.add(text[1].getText().trim());
            }
            if (!(text[2].getText().trim().equals(""))) {
                memberName.add("commodityname");
                matchInformation.add(text[2].getText().trim());
            }
            if (!(text[3].getText().trim().equals(""))) {
                memberName.add("operator");
                matchInformation.add(text[3].getText().trim());
            }
            if (!((text[4].getText().trim().equals("")) && (text[5].getText().trim().equals("")))) {
                memberName.add("returntime");
                matchInformation.add(text[4].getText().trim());
                matchInformation.add(text[5].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = this.operation.searchReturnSale(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询销售退货信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询销售退货信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0]||e.getSource()==addbutton) {
            exportbutton.setEnabled(true);
            Object o[] = new Object[12];
            o[0] = new JTextField("退货票号:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(operation.buildKey("ReturnSale"));
            ((JTextField)o[1]).setEditable(false);
            o[2] = new JTextField("商品批号:");
            ((JTextField)o[2]).setEditable(false);
            try {
                o[3] = new JComboBox(operation.getAllCommodityID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[4] = new JTextField("商品数量:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField();
            o[6] = new JTextField("金额(元):");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField();
            o[8] = new JTextField("退货时间:");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField();
            ((JTextField)o[9]).setEnabled(false);
            ((JTextField)o[9]).addMouseListener(new Datelistener((JTextField)o[9]));
            o[10] = new JTextField("操作员:");
            ((JTextField)o[10]).setEditable(false);
            try {
                o[11] = new JTextField(operation.getUserNmae());
                ((JTextField)o[11]).setEditable(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "添加信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[6];
                String[] matchInformation = new String[6];
                for (int i = 1; i < 12; i++) {
                    if(i == 3)
                        matchInformation[i / 2] = ((JComboBox)o[i]).getSelectedItem().toString().trim();
                    else if (i % 2 == 1)
                        matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                }
                memberName = new String[]{"rsaleid", "commodityid", "commodityname", "number", "money", "returntime", "operator"};
                try {
                    boolean flag = operation.addReturnSale(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "添加成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllReturnSale();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "添加退货商品信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "添加退货商品信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch(Exception e1){
                    JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            Object o[] = new Object[12];
            o[0] = new JTextField("退货票号:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            ((JTextField)o[1]).setEditable(false);
            o[2] = new JTextField("商品批号:");
            ((JTextField)o[2]).setEditable(false);
            try {
                o[3] = new JComboBox(operation.getAllCommodityID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[4] = new JTextField("商品数量:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            o[6] = new JTextField("金额(元):");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            o[8] = new JTextField("退货时间:");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField(tablemodel.getValueAt(row, 4).toString());
            ((JTextField)o[9]).setEnabled(false);
            ((JTextField)o[9]).addMouseListener(new Datelistener((JTextField)o[9]));
            o[10] = new JTextField("操作员:");
            ((JTextField)o[10]).setEditable(false);
            try {
                o[11] = new JTextField(operation.getUserNmae());
                ((JTextField)o[11]).setEditable(false);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "修改信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[6];
                String[] matchInformation = new String[6];
                for (int i = 1; i < 12; i++) {
                    if(i == 3)
                        matchInformation[i / 2] = ((JComboBox)o[i]).getSelectedItem().toString().trim();
                    else if (i % 2 == 1)
                        matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                }
                memberName = new String[]{"rsaleid", "commodityid", "number", "money", "returntime", "operator"};
                try {
                    boolean flag = operation.changeReturnSale(memberName, matchInformation, tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "修改成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllReturnSale();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "修改退货商品信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "修改退货商品信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(6);
            ArrayList<String> matchInformation = new ArrayList<String>(6);
            memberName.add("rsaleid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteReturnSale(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "删除成功", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllReturnSale();
                    this.jtable.setModel(this.tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "删除退货商品信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "删除退货商品信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==menuitem[3]){
            try {
                new CommodityPic(operation,jtable,1);
            } catch (IOException e1) {
                JOptionPane.showMessageDialog(null,"没有找到图片");
            }
        }
        if(e.getSource()==exportbutton||e.getSource()==menuitem[4]){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }
    }
}

class SaleManpanel extends JPanel implements ActionListener {

    private JPanel northpanel;
    private JPanel centerpanel;
    private JPanel buttonpanel;

    private JButton searchbutton;
    private JButton chartbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JTable jtable;
    private DefaultTableModel tablemodel;
    private Operation operation;

    public SaleManpanel(Operation operation) {
        this.operation = operation;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        centerpanel = new JPanel(new BorderLayout());
        TitledBorder t1 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Result");
        t1.setTitleJustification(TitledBorder.RIGHT);
        t1.setTitleColor(Color.lightGray);
        centerpanel.setBorder(t1);
        centerpanel.setOpaque(false);

        northpanel = new JPanel();
        TitledBorder t2 = new TitledBorder(new LineBorder(Color.LIGHT_GRAY, 1), "Search");
        t2.setTitleJustification(TitledBorder.RIGHT);
        t2.setTitleColor(Color.lightGray);
        northpanel.setBorder(t2);
        northpanel.setOpaque(false);
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.NONE;
        northpanel.setLayout(layout);

        label = new JLabel[3];
        label[0] = new JLabel("员工编号:");
        label[1] = new JLabel("员工姓名:");
        label[2] = new JLabel("手机号码:");

        text = new JTextField[3];
        for (int i = 0; i < 3; i++)
            text[i] = new JTextField(10);

        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        chartbutton = new JButton("饼状图");
        chartbutton.setBackground(Color.lightGray);
        chartbutton.addActionListener(this);
        chartbutton.setEnabled(false);
        exportbutton=new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);

        northpanel.add(label[0]);
        s.gridwidth = 1;
        layout.setConstraints(label[0], s);
        northpanel.add(text[0]);
        s.gridwidth = 1;
        layout.setConstraints(text[0], s);
        northpanel.add(label[1]);
        s.gridwidth = 1;
        layout.setConstraints(label[1], s);
        northpanel.add(text[1]);
        s.gridwidth = 1;
        layout.setConstraints(text[1], s);
        northpanel.add(label[2]);
        s.gridwidth = 1;
        layout.setConstraints(label[2], s);
        northpanel.add(text[2]);
        s.gridwidth = 1;
        layout.setConstraints(text[2], s);
        northpanel.add(searchbutton);
        s.gridwidth = 1;
        layout.setConstraints(searchbutton, s);
        northpanel.add(chartbutton);
        s.gridwidth = 1;
        layout.setConstraints(chartbutton, s);
        northpanel.add(exportbutton);
        s.gridwidth=0;
        layout.setConstraints(exportbutton,s);

        this.add(northpanel, BorderLayout.NORTH);

        tablemodel = new DefaultTableModel();
        jtable = new JTable(tablemodel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false);
                }
                return c;
            }
        };
        jtable.setOpaque(false);
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {    //销售员业绩排行查询
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            chartbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(5);
            ArrayList<String> matchInformation = new ArrayList<String>(5);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("managerid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("managername");
                matchInformation.add(text[1].getText().trim());
            }
            if (!(text[2].getText().trim().equals(""))) {
                memberName.add("phone");
                matchInformation.add(text[2].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = this.operation.searchAchievements(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询业绩信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
                chartbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询业绩信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
                chartbutton.setEnabled(false);
            }
        }

        if (e.getSource() == chartbutton) {
            int i=0;
            new JFreeChart("总收入图",this.tablemodel,0,1,2,i,i);
            i+=50;
            new JFreeChart("产生利润图",this.tablemodel,0,1,3,i,i);
        }

        if(e.getSource()==exportbutton){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }

    }
}