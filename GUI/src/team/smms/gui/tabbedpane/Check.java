package team.smms.gui.tabbedpane;

import team.smms.gui.listener.Datelistener;
import team.smms.gui.listener.ExportExcel;
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


public class Check extends JTabbedPane {
    private int type;
    private Companel companel;
    private Stockpanel stockpanel;
    private Operation operation;

    public Check(int type, Operation operation) {
        this.operation = operation;

        this.type = type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif", 1, 20));

        companel = new Companel(this.operation = operation);
        stockpanel = new Stockpanel(this.operation = operation);

        this.add("商品信息", companel);
        this.add("库存信息", stockpanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }

}

class Companel extends JPanel implements ActionListener {

    private JPanel northpanel;
    private JPanel searchpanel1;
    private JPanel searchpanel2;
    private JPanel centerpanel;
    private JPanel buttonpanel;

    private JButton searchbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JTable jtable;
    private DefaultTableModel tablemodel;
    private Operation operation;

    public Companel(Operation operation) {
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
        s.fill = GridBagConstraints.VERTICAL;
        northpanel.setLayout(layout);

        label = new JLabel[6];
        label[0] = new JLabel("商品批号:");
        label[1] = new JLabel("商品名称:");
        label[2] = new JLabel("生产日期:");
        label[3] = new JLabel("至");
        label[4] = new JLabel();
        label[5] = new JLabel("供应商:");
        for (int i = 0; i < 6; i++)
            label[i].setOpaque(false);

        text = new JTextField[5];

        GridBagLayout northlayout = new GridBagLayout();
        GridBagConstraints n = new GridBagConstraints();
        n.insets = new Insets(5, 5, 5, 5);
        n.fill = GridBagConstraints.NONE;

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
        n.gridwidth = 2;
        northlayout.setConstraints(text[2], n);
        searchpanel2.add(label[3]);
        n.gridwidth = 1;
        northlayout.setConstraints(label[3], n);
        text[3] = new JTextField(10);
        searchpanel2.add(text[3]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[3], n);
        searchpanel2.add(label[4]);
        n.gridwidth = 3;
        northlayout.setConstraints(label[4], n);
        searchpanel2.add(label[5]);
        n.gridwidth = 2;
        northlayout.setConstraints(label[5], n);
        text[4] = new JTextField(10);
        searchpanel2.add(text[4]);
        n.gridwidth = 0;
        northlayout.setConstraints(text[4], n);
        northpanel.add(searchpanel2);
        s.gridwidth = 6;
        layout.setConstraints(searchpanel2, s);

        for (int i = 2; i < 4; i++) {
            text[i].addMouseListener(new Datelistener(text[i]));
            text[i].setEnabled(false);
        }

        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton = new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagLayout buttonlayout = new GridBagLayout();
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(2, 2, 2, 2);
        b.fill = GridBagConstraints.HORIZONTAL;
        buttonpanel = new JPanel(buttonlayout);
        buttonpanel.setOpaque(false);
        buttonpanel.add(searchbutton);
        b.gridwidth = 1;
        buttonlayout.setConstraints(searchbutton, b);
        buttonpanel.add(exportbutton);
        b.gridwidth = 0;
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
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {    //商品信息查询
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(10);
            ArrayList<String> matchInformation = new ArrayList<String>(15);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("commodityid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("commodityname");
                matchInformation.add(text[1].getText().trim());
            }
            if (!((text[2].getText().trim().equals("")) && (text[3].getText().trim().equals("")))) {
                memberName.add("producedate");
                matchInformation.add(text[2].getText().trim());
                matchInformation.add(text[3].getText().trim());
            }
            if (!(text[4].getText().trim().equals(""))) {
                memberName.add("providername");
                matchInformation.add(text[4].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = operation.searchCommodity(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "没有查到匹配信息", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "查询商品信息失败", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "查询商品信息失败", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
                e1.printStackTrace();
            }
        }
        if (e.getSource() == exportbutton) {
            ExportExcel excel = new ExportExcel(tablemodel);
            excel.export();
        }
    }
}

class Stockpanel extends JPanel implements ActionListener {

    private JPanel northpanel;
    private JPanel centerpanel;

    private JButton searchbutton;
    private JButton exportbutton;
    private JTextField text[];
    private JLabel label[];
    private JTable jtable;
    private DefaultTableModel tablemodel;
    private Operation operation;

    public Stockpanel(Operation operation) {
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
        for (int i = 0; i < 4; i++)
            text[i] = new JTextField(10);

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
        northpanel.add(label[3]);
        s.gridwidth = 1;
        layout.setConstraints(label[3], s);
        northpanel.add(text[3]);
        s.gridwidth = 1;
        layout.setConstraints(text[3], s);
        northpanel.add(label[4]);
        s.gridwidth = 1;
        layout.setConstraints(label[4], s);

        searchbutton = new JButton("查询");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton = new JButton("导出");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(2, 2, 2, 2);
        b.fill = GridBagConstraints.HORIZONTAL;
        northpanel.add(searchbutton);
        s.gridwidth = 1;
        layout.setConstraints(searchbutton, s);
        northpanel.add(exportbutton);
        s.gridwidth = 0;
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
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {  //库存信息查询
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
                this.tablemodel = operation.searchStock(memberName, matchInformation);
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
        if (e.getSource() == exportbutton) {
            ExportExcel excel = new ExportExcel(tablemodel);
            excel.export();
        }
    }
}