package team.smms.gui.tabbedpane;

import team.smms.gui.listener.Datelistener;
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

public class Inventory extends JTabbedPane {

    private int type;
    private StoManagepanel stomanagepanel;
    private StoInpanel stoinpanel;
    private Operation operation;

    public Inventory(int type, Operation operation) {
        this.operation = operation;

        this.type = type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif", 1, 20));

        stomanagepanel = new StoManagepanel(this.operation = operation);
        stoinpanel = new StoInpanel(this.operation = operation);

        this.add("库存管理", stomanagepanel);
        this.add("进货入库", stoinpanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }
}

class StoManagepanel extends JPanel implements ActionListener {

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
    private DefaultTableModel tablemodel;
    private Operation operation;


    public StoManagepanel(Operation operation) {
        this.operation = operation;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());

        menuitem = new JMenuItem[5];
        menuitem[0] = new JMenuItem("添加表项");
        menuitem[1] = new JMenuItem("修改表项");
        menuitem[2] = new JMenuItem("删除表项");
        menuitem[3] = new JMenuItem("导出表格");
        popupmenu = new JPopupMenu();
        for (int i = 0; i < 4; i++) {
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

        label = new JLabel[5];
        label[0] = new JLabel("库存编号:");
        label[1] = new JLabel("商品名称:");
        label[2] = new JLabel("销售价格:");
        label[3] = new JLabel("元  至:");
        label[4] = new JLabel("元");
        for (int i = 0; i < 3; i++) {
            label[i].setOpaque(false);
            label[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        text = new JTextField[4];
        for(int i=0;i<4;i++)
            text[i]=new JTextField(10);

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
        layout.setConstraints(label[4], s);

        addbutton = new JButton("添加");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton=new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagConstraints b= new GridBagConstraints();
        b.insets=new Insets(2,2,2,2);
        b.fill = GridBagConstraints.HORIZONTAL;
        northpanel.add(addbutton);
        s.gridwidth=1;
        layout.setConstraints(addbutton, s);
        northpanel.add(searchbutton);
        s.gridwidth=1;
        layout.setConstraints(searchbutton, s);
        northpanel.add(exportbutton);
        s.gridwidth=0;
        layout.setConstraints(exportbutton, s);

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
    public void actionPerformed(ActionEvent e) {   //库存信息管理
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(9);
            ArrayList<String> matchInformation = new ArrayList<String>(13);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("stockid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("commodityname");
                matchInformation.add(text[1].getText().trim());
            }
            if (!((text[2].getText().trim().equals("")) && (text[3].getText().trim().equals("")))) {
                memberName.add("saleprice");
                matchInformation.add(text[2].getText().trim());
                matchInformation.add(text[3].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = this.operation.searchStock(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询库存信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询库存信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0]||e.getSource()==addbutton) {
            exportbutton.setEnabled(true);
            JTextField o[] = new JTextField[10];
            o[0] = new JTextField("库存编号:");
            o[0].setEditable(false);
            o[1] = new JTextField(operation.buildKey("Stock"));
            o[1].setEditable(false);
            o[2] = new JTextField("商品名称:");
            o[2].setEditable(false);
            o[3] = new JTextField();
            o[4] = new JTextField("销售价格:");
            o[4].setEditable(false);
            o[5] = new JTextField();
            o[6] = new JTextField("数量:");
            o[6].setEditable(false);
            o[7] = new JTextField();
            o[8] = new JTextField("数量下限:");
            o[8].setEditable(false);
            o[9] = new JTextField();
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "添加信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[5];
                String[] matchInformation = new String[5];
                for (int i = 1; i < 10; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"stockid", "commodityname", "saleprice", "number", "downlimit"};
                try {
                    boolean flag = operation.addStock(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "添加成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllStock();
                        this.jtable.setModel(this.tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "添加库存信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "添加库存信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            JTextField o[] = new JTextField[10];
            o[0] = new JTextField("库存编号:");
            o[0].setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            o[1].setEditable(false);
            o[2] = new JTextField("商品名称:");
            o[2].setEditable(false);
            o[3] = new JTextField(tablemodel.getValueAt(row, 1).toString());
            o[4] = new JTextField("销售价格:");
            o[4].setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            o[6] = new JTextField("数量:");
            o[6].setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            o[8] = new JTextField("数量下限:");
            o[8].setEditable(false);
            o[9] = new JTextField(tablemodel.getValueAt(row, 4).toString());
            String but[] = {"确定", "取消"};
            int go = JOptionPane.showOptionDialog(null, o, "修改信息", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[5];
                String[] matchInformation = new String[5];
                for (int i = 1; i < 10; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"stockid", "commodityname", "saleprice", "number", "downlimit"};
                try {
                    boolean flag = operation.changeStock(memberName, matchInformation, tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "修改成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllStock();
                        this.jtable.setModel(this.tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "修改库存信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "修改库存信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(9);
            ArrayList<String> matchInformation = new ArrayList<String>(9);
            memberName.add("stockid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteStock(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "删除成功", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllStock();
                    this.jtable.setModel(this.tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "删除库存信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "删除库存信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
            }
        }
        if(e.getSource()==exportbutton||e.getSource()==menuitem[3]){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }
    }
}

class StoInpanel extends JPanel implements ActionListener {

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


    public StoInpanel(Operation operation) {
        this.operation = operation;

        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem = new JMenuItem[5];
        menuitem[0] = new JMenuItem("添加表项");
        menuitem[1] = new JMenuItem("修改表项");
        menuitem[2] = new JMenuItem("删除表项");
        menuitem[3] = new JMenuItem("导出表格");
        popupmenu = new JPopupMenu();
        for (int i = 0; i < 4; i++) {
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

        label = new JLabel[9];
        label[0] = new JLabel("入库编号:");
        label[1] = new JLabel("商品批号:");
        label[2] = new JLabel("商品名称:");
        label[3] = new JLabel("进价:");
        label[4] = new JLabel("元 至");
        label[5] = new JLabel("元");
        label[6] = new JLabel("入库时间:");
        label[7] = new JLabel("至");
        label[8] = new JLabel("操作员:");
        for (int i = 0; i < 9; i++) {
            label[i].setOpaque(false);
            label[i].setHorizontalAlignment(SwingConstants.RIGHT);
        }

        text = new JTextField[8];

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
        text[3] = new JTextField(3);
        searchpanel2.add(text[3]);
        n.gridwidth = 2;
        northlayout.setConstraints(text[3], n);
        searchpanel2.add(label[4]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[4], n);
        text[4] = new JTextField(3);
        searchpanel2.add(text[4]);
        n.gridwidth = 2;
        northlayout.setConstraints(text[4], n);
        searchpanel2.add(label[5]);
        n.gridwidth = 0;
        northlayout.setConstraints(label[5], n);
        northpanel.add(searchpanel2);
        s.gridwidth = 3;
        layout.setConstraints(searchpanel2, s);

        searchpanel3 = new JPanel();
        searchpanel3.setLayout(northlayout);
        searchpanel3.setOpaque(false);
        searchpanel3.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        searchpanel3.add(label[6]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[6], n);
        text[5] = new JTextField(10);
        searchpanel3.add(text[5]);
        n.gridwidth = 4;
        northlayout.setConstraints(text[5], n);
        searchpanel3.add(label[7]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[7], n);
        text[6] = new JTextField(10);
        searchpanel3.add(text[6]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[6], n);
        searchpanel3.add(label[8]);
        n.gridwidth = 4;
        northlayout.setConstraints(label[8], n);
        text[7] = new JTextField(10);
        searchpanel3.add(text[7]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[7], n);
        northpanel.add(searchpanel3);
        s.gridwidth = 6;
        layout.setConstraints(searchpanel3, s);

        for (int i = 5; i < 7; i++) {
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
        layout.setConstraints(buttonpanel, s);

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
    public void actionPerformed(ActionEvent e) {    //进货入库管理
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(9);
            ArrayList<String> matchInformation = new ArrayList<String>(18);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("storageid");
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
            if (!((text[3].getText().trim().equals("")) && (text[4].getText().trim().equals("")))) {
                memberName.add("inprice");
                matchInformation.add(text[3].getText().trim());
                matchInformation.add(text[4].getText().trim());
            }
            if (!((text[5].getText().trim().equals("")) && (text[6].getText().trim().equals("")))) {
                memberName.add("indate");
                matchInformation.add(text[5].getText().trim());
                matchInformation.add(text[6].getText().trim());
            }
            if (!(text[7].getText().trim().equals(""))) {
                memberName.add("operator");
                matchInformation.add(text[7].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = this.operation.searchStorage(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询进货入库信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询进货入库信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0]||e.getSource()==addbutton) {
            Object o[] = new Object[12];
            o[0] = new JTextField("入库编号:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(operation.buildKey("Storage"));
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
            o[4] = new JTextField("数量:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField();
            o[6] = new JTextField("进价:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField();
            o[8] = new JTextField("入库时间:");
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
                memberName = new String[]{"storageid", "commodityid", "number", "inprice", "indate", "operator"};
                try {
                    boolean flag = operation.addStorage(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "添加成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllStorage();
                        this.jtable.setModel(this.tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "添加进货入库信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "添加进货入库信息信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null, "添加失败，请补足信息", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            Object o[] = new Object[12];
            o[0] = new JTextField("入库编号:");
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
            o[4] = new JTextField("数量:");
            ((JTextField)o[4]).setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            o[6] = new JTextField("进价:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            o[8] = new JTextField("入库时间:");
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
                memberName = new String[]{"storageid", "commodityid", "number", "inprice", "indate", "operator"};
                try {
                    boolean flag = operation.changeStorage(memberName, matchInformation, tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "修改成功", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllStorage();
                        this.jtable.setModel(this.tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "修改失败", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "修改入库信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "修改入库信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(9);
            ArrayList<String> matchInformation = new ArrayList<String>(9);
            memberName.add("storageid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteStorage(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "删除成功", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllStorage();
                    this.jtable.setModel(this.tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "删除失败", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "删除入库信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "删除入库信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==exportbutton||e.getSource()==menuitem[3]){
            ExportExcel excel=new ExportExcel(tablemodel);
            excel.export();
        }
    }
}
