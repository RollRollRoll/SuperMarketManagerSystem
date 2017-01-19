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
import java.sql.SQLException;
import java.util.ArrayList;

public class BasicInformation extends JTabbedPane {

    private int type;
    private ComManagepanel commanagepanel;
    private ProManagepanel promanagepanel;
    private Operation operation;

    public BasicInformation(int type, Operation operation) {
        this.operation = operation;

        this.type = type;

        this.setBackground(Color.lightGray);
        this.setFont(new java.awt.Font("Serif", 1, 20));

        commanagepanel = new ComManagepanel(this.operation = operation);
        promanagepanel = new ProManagepanel(this.operation = operation);

        this.add("��Ʒ������Ϣ����", commanagepanel);
        this.add("��Ӧ����Ϣ����", promanagepanel);
        this.setBorder(new LineBorder(Color.white));
        this.setVisible(true);
    }
}

class ComManagepanel extends JPanel implements ActionListener {

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

    public ComManagepanel(Operation operation) {


        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem = new JMenuItem[5];
        menuitem[0] = new JMenuItem("��ӱ���");
        menuitem[1] = new JMenuItem("�޸ı���");
        menuitem[2] = new JMenuItem("ɾ������");
        menuitem[3] = new JMenuItem("��ʾͼƬ");
        menuitem[4] = new JMenuItem("�������");
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
        label[0] = new JLabel("��Ʒ����:");
        label[1] = new JLabel("��Ʒ����:");
        label[2] = new JLabel("��������:");
        label[3] = new JLabel("��");
        label[4] = new JLabel();
        label[5] = new JLabel("��Ӧ��:");
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

        addbutton = new JButton("���");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton = new JButton("��ѯ");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton = new JButton("����");
        exportbutton.setBackground(Color.lightGray);
        exportbutton.setEnabled(false);
        exportbutton.addActionListener(this);
        GridBagLayout buttonlayout = new GridBagLayout();
        GridBagConstraints b = new GridBagConstraints();
        b.insets = new Insets(2, 2, 2, 2);
        b.fill = GridBagConstraints.HORIZONTAL;
        buttonpanel = new JPanel(buttonlayout);
        buttonpanel.setOpaque(false);
        buttonpanel.add(addbutton);
        b.gridwidth = 1;
        buttonlayout.setConstraints(addbutton, b);
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
        jtable.addMouseListener(new Righthandlistener(jtable, popupmenu));
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);


        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {   //��Ʒ��Ϣ����
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
                    JOptionPane.showMessageDialog(null, "û�в鵽ƥ����Ϣ", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "��ѯ��Ʒ��Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "��ѯ��Ʒ��Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0] || e.getSource() == addbutton) {
            exportbutton.setEnabled(true);
            Object o[] = new Object[16];
            o[0] = new JTextField("��Ʒ����:");
            ((JTextField) o[0]).setEditable(false);
            o[1] = new JTextField();
            o[2] = new JTextField("��Ʒ����:");
            ((JTextField)o[2]).setEditable(false);
            o[3] = new JTextField();
            o[4] = new JTextField("�����");
            ((JTextField)o[4]).setEditable(false);
            try {
                String[] t = operation.getAllStockID();
                o[5] = new JComboBox(t);
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[6] = new JTextField("��������:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField();
            ((JTextField)o[7]).setEnabled(false);
            ((JTextField)o[7]).addMouseListener(new Datelistener((JTextField)o[7]));
            o[8] = new JTextField("��Ч��:");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField();
            o[10] = new JTextField("��λ:");
            ((JTextField)o[10]).setEditable(false);
            o[11] = new JTextField();
            o[12] = new JTextField("��Ӧ�̱��:");
            ((JTextField)o[12]).setEditable(false);
            try {
                o[13] = new JComboBox(operation.getAllProviderID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[14] = new JTextField("��ƷͼƬ");
            ((JTextField)o[14]).setEditable(false);
            o[15] = new JTextField();
            ((JTextField)o[15]).addMouseListener(new PicChooser((JTextField)o[15]));
            ((JTextField)o[15]).setEnabled(false);
            String but[] = {"ȷ��", "ȡ��"};
            int go = JOptionPane.showOptionDialog(null, o, "�����Ϣ", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[8];
                String[] matchInformation = new String[8];
                for (int i = 1; i < 16; i++) {
                    if (i % 2 == 1) {
                        if (i == 15)
                            matchInformation[i / 2] = ((JTextField)o[i]).getText().trim().replaceAll("\\\\", "/");
                        else if(i == 5||i == 13)
                            matchInformation[i / 2] = ((JComboBox)o[i]).getSelectedItem().toString().trim();
                        else {
                            matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                            System.out.println(matchInformation[i / 2]);
                        }
                    }
                }
                memberName = new String[]{"commodityid", "commodityname", "stockid", "producedate", "storagetime", "unit", "providerid", "pic"};
                try {
                    boolean flag = operation.addCommodity(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "��ӳɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllCommodity();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "���ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "�����Ʒ��Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "�����Ʒ��Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "���ʧ�ܣ��벹����Ϣ", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            Object o[] = new Object[16];
            o[0] = new JTextField("��Ʒ����:");
            ((JTextField)o[0]).setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            o[2] = new JTextField("��Ʒ����:");
            ((JTextField)o[2]).setEditable(false);
            o[3] = new JTextField(tablemodel.getValueAt(row, 1).toString());
            o[4] = new JTextField("�����");
            ((JTextField)o[4]).setEditable(false);
            try {
                o[5] = new JComboBox(operation.getAllStockID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            o[6] = new JTextField("��������:");
            ((JTextField)o[6]).setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            ((JTextField)o[7]).setEnabled(false);
            ((JTextField)o[7]).addMouseListener(new Datelistener((JTextField)o[7]));
            o[8] = new JTextField("��Ч��:");
            ((JTextField)o[8]).setEditable(false);
            o[9] = new JTextField(tablemodel.getValueAt(row, 4).toString());
            o[10] = new JTextField("��λ:");
            ((JTextField)o[10]).setEditable(false);
            o[11] = new JTextField(tablemodel.getValueAt(row, 5).toString());
            o[12] = new JTextField("��Ӧ�̱��:");
            ((JTextField)o[12]).setEditable(false);
            try {
                o[13] = new JComboBox(operation.getAllProviderID());
            } catch (SQLException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            String but[] = {"ȷ��", "ȡ��"};
            int go = JOptionPane.showOptionDialog(null, o, "�޸���Ϣ", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[8];
                String[] matchInformation = new String[8];
                for (int i = 1; i < 16; i++) {
                    if (i % 2 == 1) {
                        if (i == 15) {
                            matchInformation[i / 2] = null;
                        }
                        else if(i == 5||i == 13)
                            matchInformation[i / 2] = ((JComboBox)o[i]).getSelectedItem().toString().trim();
                        else
                            matchInformation[i / 2] = ((JTextField)o[i]).getText().trim();
                    }
                }
                memberName = new String[]{"commodityid", "commodityname", "stockid", "producedate", "storagetime", "unit", "providerid", "pic"};
                try {
                    boolean flag = operation.changeCommodity(memberName, matchInformation, this.tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "�޸ĳɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllCommodity();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "�޸�ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "�޸���Ʒ��Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "�޸���Ʒ��Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(10);
            ArrayList<String> matchInformation = new ArrayList<String>(10);
            memberName.add("commodityid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteCommodity(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "ɾ���ɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllCommodity();
                    this.jtable.setModel(tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "ɾ��ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "ɾ����Ʒ��Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "ɾ����Ʒ��Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == menuitem[3]) {
            try {
                new CommodityPic(operation, jtable, 0);
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(null, "û���ҵ�ͼƬ");
            }
        }
        if (e.getSource() == exportbutton || e.getSource() == menuitem[4]) {
            ExportExcel excel = new ExportExcel(tablemodel);
            excel.export();
        }

    }
}

class ProManagepanel extends JPanel implements ActionListener {

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

    public ProManagepanel(Operation operation) {


        this.setOpaque(false);
        this.setLayout(new BorderLayout());


        menuitem = new JMenuItem[4];
        menuitem[0] = new JMenuItem("��ӱ���");
        menuitem[1] = new JMenuItem("�޸ı���");
        menuitem[2] = new JMenuItem("ɾ������");
        menuitem[3] = new JMenuItem("�������");
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
        s.fill = GridBagConstraints.NONE;
        northpanel.setLayout(layout);

        label = new JLabel[4];
        label[0] = new JLabel("��Ӧ�̱��:");
        label[1] = new JLabel("��Ӧ������:");
        label[2] = new JLabel("��Ӧ�̵�ַ:");
        label[3] = new JLabel("��Ӧ�̵绰:");

        text = new JTextField[4];
        for (int i = 0; i < 4; i++)
            text[i] = new JTextField(10);

        addbutton = new JButton("���");
        addbutton.setBackground(Color.lightGray);
        addbutton.addActionListener(this);
        searchbutton = new JButton("��ѯ");
        searchbutton.setBackground(Color.lightGray);
        searchbutton.addActionListener(this);
        exportbutton = new JButton("����");
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
        northpanel.add(label[3]);
        s.gridwidth = 1;
        layout.setConstraints(label[3], s);
        northpanel.add(text[3]);
        s.gridwidth = 1;
        layout.setConstraints(text[3], s);
        northpanel.add(addbutton);
        s.gridwidth = 1;
        layout.setConstraints(addbutton, s);
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
        jtable.addMouseListener(new Righthandlistener(jtable, popupmenu));
        JScrollPane spane = new JScrollPane(jtable);
        spane.setOpaque(false);
        spane.getViewport().setOpaque(false);
        centerpanel.add(spane, BorderLayout.CENTER);
        this.add(centerpanel, BorderLayout.CENTER);

        this.operation = operation;
    }

    @Override
    public void actionPerformed(ActionEvent e) {  //��Ӧ����Ϣ����
        if (e.getSource() == searchbutton) {
            exportbutton.setEnabled(true);
            ArrayList<String> memberName = new ArrayList<String>(10);
            ArrayList<String> matchInformation = new ArrayList<String>(15);
            if (!(text[0].getText().trim().equals(""))) {
                memberName.add("providerid");
                matchInformation.add(text[0].getText().trim());
            }
            if (!(text[1].getText().trim().equals(""))) {
                memberName.add("providername");
                matchInformation.add(text[1].getText().trim());
            }
            if (!(text[2].getText().trim().equals(""))) {
                memberName.add("provideraddress");
                matchInformation.add(text[2].getText().trim());
            }
            if (!(text[3].getText().trim().equals(""))) {
                memberName.add("phone");
                matchInformation.add(text[3].getText().trim());
            }
            memberName.trimToSize();
            matchInformation.trimToSize();
            try {
                this.tablemodel = operation.searchProvider(memberName, matchInformation);
                if (this.tablemodel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(null, "û�в鵽ƥ����Ϣ", null, JOptionPane.INFORMATION_MESSAGE);
                }
                this.jtable.setModel(this.tablemodel);
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "��ѯ��Ӧ����Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
                exportbutton.setEnabled(false);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "��ѯ��Ӧ����Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                e1.printStackTrace();
                exportbutton.setEnabled(false);
            }
        }
        if (e.getSource() == menuitem[0] || e.getSource() == addbutton) {
            exportbutton.setEnabled(true);
            JTextField o[] = new JTextField[8];
            o[0] = new JTextField("��Ӧ�̱��:");
            o[0].setEditable(false);
            o[1] = new JTextField(operation.buildKey("Provider"));
            o[1].setEditable(false);
            o[2] = new JTextField("��Ӧ������:");
            o[2].setEditable(false);
            o[3] = new JTextField();
            o[4] = new JTextField("��Ӧ�̵�ַ:");
            o[4].setEditable(false);
            o[5] = new JTextField();
            o[6] = new JTextField("��Ӧ�̵绰:");
            o[6].setEditable(false);
            o[7] = new JTextField();
            String but[] = {"ȷ��", "ȡ��"};
            int go = JOptionPane.showOptionDialog(null, o, "�����Ϣ", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[4];
                String[] matchInformation = new String[4];
                for (int i = 1; i < 8; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"providerid", "providername", "provideraddress", "phone"};
                try {
                    boolean flag = operation.addProvider(memberName, matchInformation);
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "��ӳɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllProvider();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "���ʧ�ܣ��벹����Ϣ", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "��ӹ�������Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "��ӹ�������Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                    e1.printStackTrace();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "���ʧ�ܣ��벹����Ϣ", null, JOptionPane.INFORMATION_MESSAGE);
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource() == menuitem[1]) {
            int row = jtable.getSelectedRow();
            JTextField o[] = new JTextField[8];
            o[0] = new JTextField("��Ӧ�̱��:");
            o[0].setEditable(false);
            o[1] = new JTextField(tablemodel.getValueAt(row, 0).toString());
            o[1].setEditable(false);
            o[2] = new JTextField("��Ӧ������:");
            o[2].setEditable(false);
            o[3] = new JTextField(tablemodel.getValueAt(row, 1).toString());
            o[4] = new JTextField("��Ӧ�̵�ַ:");
            o[4].setEditable(false);
            o[5] = new JTextField(tablemodel.getValueAt(row, 2).toString());
            o[6] = new JTextField("��Ӧ�̵绰:");
            o[6].setEditable(false);
            o[7] = new JTextField(tablemodel.getValueAt(row, 3).toString());
            String but[] = {"ȷ��", "ȡ��"};
            int go = JOptionPane.showOptionDialog(null, o, "�޸���Ϣ", JOptionPane.YES_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, but, but[0]);
            if (go == 0) {
                String[] memberName = new String[4];
                String[] matchInformation = new String[4];
                for (int i = 1; i < 8; i++) {
                    if (i % 2 == 1)
                        matchInformation[i / 2] = o[i].getText().trim();
                }
                memberName = new String[]{"providerid", "providername", "provideraddress", "phone"};
                try {
                    boolean flag = operation.changeProvider(memberName, matchInformation, this.tablemodel.getValueAt(row, 0).toString());
                    if (flag == true) {
                        JOptionPane.showMessageDialog(null, "�޸ĳɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                        this.tablemodel = operation.searchAllProvider();
                        this.jtable.setModel(tablemodel);
                    } else {
                        JOptionPane.showMessageDialog(null, "�޸�ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (SQLException e1) {
                    JOptionPane.showMessageDialog(null, "�޸Ĺ�Ӧ����Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException e1) {
                    JOptionPane.showMessageDialog(null, "�޸Ĺ�Ӧ����Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
                }
            }

        }
        if (e.getSource() == menuitem[2]) {
            int row = jtable.getSelectedRow();
            ArrayList<String> memberName = new ArrayList<String>(4);
            ArrayList<String> matchInformation = new ArrayList<String>(4);
            memberName.add("providerid");
            matchInformation.add(this.tablemodel.getValueAt(row, 0).toString());
            try {
                boolean flag = operation.deleteProvider(memberName, matchInformation);
                if (flag == true) {
                    JOptionPane.showMessageDialog(null, "ɾ���ɹ�", null, JOptionPane.INFORMATION_MESSAGE);
                    this.tablemodel = operation.searchAllProvider();
                    this.jtable.setModel(this.tablemodel);
                } else {
                    JOptionPane.showMessageDialog(null, "ɾ��ʧ��", null, JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException e1) {
                JOptionPane.showMessageDialog(null, "ɾ����Ӧ����Ϣʧ��", "SQLException", JOptionPane.ERROR_MESSAGE);
            } catch (ClassNotFoundException e1) {
                JOptionPane.showMessageDialog(null, "ɾ����Ӧ����Ϣʧ��", "ClassNotFoundException", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == exportbutton || e.getSource() == menuitem[3]) {
            ExportExcel excel = new ExportExcel(tablemodel);
            excel.export();
        }

    }
}