package team.smms.gui.listener;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class Textfieldlistener implements FocusListener{
	
	private JTextField text;
	private String str="";
	
	public Textfieldlistener(JTextField text,String str){
		this.text=text;
		this.str=str;
	}

	@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text&&text.getText().equals(str))
			this.text.setText("");
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==text&&text.getText().trim().isEmpty())
			this.text.setText(str);
	}
	
}