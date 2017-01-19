package team.smms.gui.tabbedpane;

import team.smms.database.tableclass.Account;
import team.smms.proxy.operation.MySQLUser;
import team.smms.gui.listener.YZlistener;
import team.smms.gui.smpack.Login;
import team.smms.proxy.operation.Operation;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.ListIterator;

public class SecAccount extends JTabbedPane {

	private int type;
	private BaseInforpanel baseinforpanel;
	private ChangPasswordpanel changpasswordpanel;
	private Operation operation;

	public SecAccount(int type, Operation operation, JFrame mainframe) throws ClassNotFoundException, SQLException {
		this.operation = operation;

		this.type = type;

		this.setBackground(Color.lightGray);
		this.setFont(new java.awt.Font("Serif", 1, 20));
		baseinforpanel = new BaseInforpanel(operation);
		changpasswordpanel = new ChangPasswordpanel(operation, mainframe);
		this.add("基本信息", baseinforpanel);
		this.add("修改密码", changpasswordpanel);

		this.setVisible(true);
	}
}

class BaseInforpanel extends JPanel {
	Operation operation;
	JPanel panel;
	JExpandablePanel j[];
	Operation rootOperation = new Operation("root");

	public BaseInforpanel(Operation operation) throws ClassNotFoundException, SQLException {
		this.operation = operation;
		this.setOpaque(false);
		panel = new JPanel();
		panel.setOpaque(false);

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints s = new GridBagConstraints();
		s.insets = new Insets(5, 5, 5, 5);
		s.fill = GridBagConstraints.REMAINDER;
		panel.setLayout(layout);

		// ******************************//
		String tableName = "Account";
		String[] memberName = { "managerID" };
		int index = operation.getUser().toString().lastIndexOf("@");
		String managerID = operation.getUser().toString().substring(0, index);
		String[] matchInformation = { managerID };
		List<Account> list = rootOperation.<Account>search(tableName, memberName, matchInformation);
		ListIterator<Account> listIterator = list.listIterator();
		Account temp = null;
		while (listIterator.hasNext()) {
			temp = listIterator.next();
		}
		// *********************************//

		j = new JExpandablePanel[5];
		j[0] = new JExpandablePanel("ID", temp.getManagerID());
		s.gridwidth = 0;
		layout.setConstraints(j[0], s);
		panel.add(j[0]);

		j[1] = new JExpandablePanel("姓名", temp.getManagerName());
		s.gridwidth = 0;
		layout.setConstraints(j[1], s);
		panel.add(j[1]);

		j[2] = new JExpandablePanel("手机号码", temp.getMobilePhone());
		s.gridwidth = 0;
		layout.setConstraints(j[2], s);
		panel.add(j[2]);

		j[3] = new JExpandablePanel("固定电话", temp.getPhone());
		s.gridwidth = 0;
		layout.setConstraints(j[3], s);
		panel.add(j[3]);

		j[4] = new JExpandablePanel("电子邮件", temp.getMangerEmail());
		s.gridwidth = 0;
		layout.setConstraints(j[4], s);
		panel.add(j[4]);

		this.add(panel);
	}

	class HeaderPanel extends JPanel {

		private boolean Show;
		private JLabel title;
		private JLabel message;
		private JLabel edit;

		public HeaderPanel(String title, String message) {

			this.Show = false;
			this.setLayout(null);
			this.setPreferredSize(new Dimension(850, 45));
			this.setBackground(new Color(245, 245, 245));

			if (message.trim().equals(""))
				message = "未填写";

			this.title = new JLabel(title);
			this.title.setBounds(20, 10, 1000, 30);
			this.title.setFont(new java.awt.Font("Serif", 0, 15));
			this.title.setOpaque(false);
			this.message = new JLabel(message);
			this.message.setFont(new java.awt.Font("Serif", 0, 15));
			this.message.setForeground(Color.gray);
			this.message.setBounds(350, 10, 1000, 30);
			this.message.setOpaque(false);
			this.edit = new JLabel("编辑");
			this.edit.setFont(new java.awt.Font("Serif", 0, 15));
			this.edit.setForeground(new Color(100, 149, 237));
			this.edit.setBounds(800, 10, 1000, 30);
			this.edit.setOpaque(false);

			this.add(this.title);
			if (!(this.isShow())) {
				this.add(this.message);
				if (!(title.equals("ID")))
					this.add(this.edit);
			}
		}

		public void addlabel() {
			if (!(this.isShow())) {
				this.add(this.message);
				this.edit.setText("编辑");
			}
		}

		public void deletelabel() {
			if (this.isShow()) {
				this.remove(this.message);
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

	class ContentPanel extends JPanel implements ActionListener {

		private boolean Showing;
		private JLabel jlabel[];
		private JTextField text[];
		private JButton jbutton;
		Operation operation;
		private JLabel headlabel;

		public ContentPanel(String title, String message, Operation operation, JLabel headlabel) {

			this.headlabel = headlabel;
			this.operation = operation;
			this.setBackground(Color.WHITE);
			this.jlabel = new JLabel[2];
			this.text = new JTextField[2];

			GridBagLayout layout = new GridBagLayout();
			GridBagConstraints s = new GridBagConstraints();
			s.insets = new Insets(5, 5, 5, 5);
			s.fill = GridBagConstraints.NONE;
			this.setLayout(layout);

			jlabel[0] = new JLabel("现" + title + ":");
			jlabel[1] = new JLabel("新" + title + ":");

			if (message.trim().equals(""))
				message = "未填写";

			text[0] = new JTextField(message, 10);
			text[0].setEditable(false);
			text[0].setOpaque(false);

			text[1] = new JTextField(10);

			jbutton = new JButton("保存");
			jbutton.setBackground(Color.lightGray);

			this.add(jlabel[0]);
			s.gridwidth = 1;
			layout.setConstraints(jlabel[0], s);
			this.add(text[0]);
			s.gridwidth = 0;
			layout.setConstraints(text[0], s);
			this.add(jlabel[1]);
			s.gridwidth = 1;
			layout.setConstraints(jlabel[1], s);
			this.add(text[1]);
			s.gridwidth = 0;
			layout.setConstraints(text[1], s);
			this.add(jbutton);
			s.gridwidth = 0;
			layout.setConstraints(jbutton, s);
			jbutton.addActionListener(this);

			this.setVisible(false);
		}

		public boolean isShowing() {
			return Showing;
		}

		public void setShowing(boolean showing) {
			Showing = showing;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == jbutton) {
				String[] memberName = null;
				if (jlabel[0].getText().contains("ID")) {
					String[] temp = { "managerID" };
					memberName = temp;
				}
				if (jlabel[0].getText().contains("姓名")) {
					String[] temp = { "managerName" };
					memberName = temp;
				}
				if (jlabel[0].getText().contains("手机号码")) {
					String[] temp = { "MobilePhone" };
					memberName = temp;
				}
				if (jlabel[0].getText().contains("固定电话")) {
					String[] temp = { "phone" };
					memberName = temp;
				}
				if (jlabel[0].getText().contains("电子邮件")) {
					String[] temp = { "managerEmail" };
					memberName = temp;
				}
				String[] updateInformation = { text[1].getText() };
				int index = 0;
				try {
					index = operation.getUser().toString().lastIndexOf("@");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String key = null;
				try {
					key = operation.getUser().toString().substring(0, index);
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				;
				try {
					rootOperation.changeAccount(memberName, updateInformation, key);
					text[0].setText(updateInformation[0]);
					headlabel.setText(updateInformation[0]);
					JOptionPane.showMessageDialog(this, "修改成功");
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(this, "修改失败");
					e1.printStackTrace();
				}
			}

		}

	}

	class JExpandablePanel extends JPanel implements MouseListener {
		private static final long serialVersionUID = 1L;
		private HeaderPanel headerPanel;
		private ContentPanel contentPanel;

		public JExpandablePanel(String title, String message) {
			super();
			initComponents(title, message);
		}

		private void initComponents(String title, String message) {
			this.setLayout(new BorderLayout());
			headerPanel = new HeaderPanel(title, message);
			if (!(title.equals("ID")))
				headerPanel.addMouseListener(this);
			contentPanel = new ContentPanel(title, message, operation, headerPanel.message);
			this.add(headerPanel, BorderLayout.NORTH);
			this.add(contentPanel, BorderLayout.CENTER);
			this.setBorder(new LineBorder(Color.lightGray));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == headerPanel) {
				headerPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				if (!(headerPanel.isShow()))
					headerPanel.setBackground(new Color(250, 250, 250));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if (e.getSource() == headerPanel) {
				if (!(headerPanel.isShow()))
					headerPanel.setBackground(new Color(245, 245, 245));
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
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

class ChangPasswordpanel extends JPanel implements ActionListener {

	private JLabel PWlabel[];
	private JLabel YZlabel;
	private JLabel label[];
	private JLabel YZimagelabel;
	private JPasswordField PWtext[];
	private JTextField YZtext;
	private JButton button[];
	private YZlistener yzlistener;
	private JFrame mainframe;
	Operation operation;

	public ChangPasswordpanel(Operation operation, JFrame mainframe) {
		this.operation = operation;
		this.setOpaque(false);
		this.mainframe = mainframe;

		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints s = new GridBagConstraints();
		s.insets = new Insets(0, 0, 40, 10);
		s.fill = GridBagConstraints.NONE;
		this.setLayout(layout);

		label = new JLabel[3];
		for (int i = 0; i < 3; i++) {
			label[i] = new JLabel("*");
			label[i].setForeground(Color.RED);
			label[i].setHorizontalAlignment(SwingConstants.LEFT);
		}

		YZimagelabel = new JLabel();
		YZimagelabel.setEnabled(false);
		YZimagelabel.setBackground(Color.WHITE);
		YZimagelabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		PWlabel = new JLabel[3];
		PWlabel[0] = new JLabel("原密码:");
		PWlabel[1] = new JLabel("新密码:");
		PWlabel[2] = new JLabel("确认密码:");
		YZlabel = new JLabel("验证码:");

		PWtext = new JPasswordField[3];
		for (int i = 0; i < 3; i++)
			PWtext[i] = new JPasswordField(20);
		YZtext = new JTextField("验证码", 20);

		yzlistener = new YZlistener(YZimagelabel, YZtext);

		YZtext.addFocusListener(yzlistener);
		YZimagelabel.addMouseListener(yzlistener);

		button = new JButton[2];
		button[0] = new JButton("确认");
		button[1] = new JButton("取消");
		button[0].setBackground(Color.lightGray);
		button[1].setBackground(Color.lightGray);
		button[0].addActionListener(this);
		button[1].addActionListener(this);

		for (int i = 0; i < 3; i++) {
			this.add(PWlabel[i]);
			s.gridwidth = 1;
			layout.setConstraints(PWlabel[i], s);
			this.add(PWtext[i]);
			s.gridwidth = 2;
			layout.setConstraints(PWtext[i], s);
			this.add(label[i]);
			s.gridwidth = 0;
			layout.setConstraints(label[i], s);
		}

		this.add(YZlabel);
		s.gridwidth = 1;
		layout.setConstraints(YZlabel, s);
		this.add(YZtext);
		s.gridwidth = 2;
		layout.setConstraints(YZtext, s);
		this.add(YZimagelabel);
		s.gridwidth = 0;
		layout.setConstraints(YZimagelabel, s);

		this.add(button[0]);
		s.gridwidth = 1;
		s.gridx = 1;
		s.gridy = 5;
		layout.setConstraints(button[0], s);
		this.add(button[1]);
		s.gridwidth = 1;
		s.gridx = 2;
		s.gridy = 5;
		layout.setConstraints(button[1], s);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) { // 修改密码操作
		if (arg0.getSource() == button[0]) {
			try {
				String str = "";
				if (new String(PWtext[0].getPassword()).trim().equals("")) {
					str = "请输入原密码";
				} else if (new String(PWtext[1].getPassword()).trim().equals("")) {
					str = "请输入新密码";
				} else if (new String(PWtext[2].getPassword()).trim().equals("")) {
					str = "请确认新密码";
				} else if (!new String(PWtext[1].getPassword()).trim()
						.equals(new String(PWtext[2].getPassword()).trim())) {
					str = "两次密码不相同，请确认";
				} else if (YZtext.getText().trim().equals("验证码")) {
					str = "请输入验证码";
				} else if (!(YZtext.getText().trim().toLowerCase().equals(yzlistener.getYzm()))) {
					str = "验证码错误";
				}
				if (!str.equals("")) {
					JOptionPane.showMessageDialog(this, str);
					return;
				}
				Operation rootOperation = new Operation("root");
				int index = 0;
				try {
					index = operation.getUser().toString().lastIndexOf("@");
				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String key = null;
				try {
					key = operation.getUser().toString().substring(0, index);
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				String tableName = "Account";
				String[] memberName = { "managerID" };
				String[] matchInformation = { key };
				List<Account> list = rootOperation.search(tableName, memberName, matchInformation);
				ListIterator<Account> listIterator = list.listIterator();
				Account temp = null;
				while (listIterator.hasNext()) {
					temp = listIterator.next();
				}
				String correctPW = temp.getAccountPassword();
				if (!correctPW.equals(PWtext[0].getText())) {
					str = "原密码错误,请重新输入";
					JOptionPane.showMessageDialog(this, str);
					PWtext[0].setText("");
					return;
				}
				String[] updateInformation = { PWtext[1].getText() };
				String[] string = { "accountPassword" };
				memberName = string;
				index = 0;
				try {
					index = operation.getUser().toString().lastIndexOf("@");
				} catch (SQLException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				try {
					key = operation.getUser().toString().substring(0, index);
				} catch (SQLException e4) {
					// TODO Auto-generated catch block
					e4.printStackTrace();
				}
				try {
					rootOperation.changeAccount(memberName, updateInformation, key);

					MySQLUser user = new MySQLUser(key,PWtext[1].getText(),temp.getAccountType());
					rootOperation.changeAuthority(1,user);
					rootOperation.changeAuthority(0,user);

					JOptionPane.showMessageDialog(this, "密码重置成功,请重新登陆!");
					new Login();
					mainframe.dispose();
				} catch (Exception e) {
				}
			} catch (Exception e) {
			}
		}
	}
}
