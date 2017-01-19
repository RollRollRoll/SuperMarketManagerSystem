package team.smms.gui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import team.smms.gui.smpack.Yanzhengma;

public class YZlistener implements MouseListener,FocusListener{

	private String yzm="";
	private Yanzhengma yz;
	private JLabel YZimagelabel;
	private JTextField YZtext;
	
	public String getYzm() {
		return yzm;
	}

	public YZlistener(JLabel YZimagelabel,JTextField YZtext){
		
		this.YZimagelabel=YZimagelabel;
		this.YZtext=YZtext;
		
		try {
			yz=new Yanzhengma();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "验证码未生成");
		}
		
	}
	
	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==YZtext&&YZtext.getText().equals("验证码")){
			this.YZtext.setText("");
			yzm=yz.getS();
			YZimagelabel.setEnabled(true);
			YZimagelabel.setIcon((new ImageIcon(yz.getImg())));
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==YZtext&&YZtext.getText().trim().isEmpty())
			this.YZtext.setText("验证码");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==YZimagelabel&&arg0.getButton()==MouseEvent.BUTTON1)
		try {
			yz=new Yanzhengma();
			yzm=yz.getS();
			YZimagelabel.setIcon((new ImageIcon(yz.getImg())));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "验证码未生成");
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}