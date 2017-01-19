package team.smms.gui.listener;

import com.mkk.swing.JCalendarChooser;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;


public class Datelistener implements MouseListener{

	private JTextField Datetext;
	private JDialog jdialog;

	public Datelistener(JTextField Datetext){
		
		this.Datetext=Datetext;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==Datetext&&e.getButton()==MouseEvent.BUTTON1){
			jdialog=new JDialog();
			JCalendarChooser jcalendarchooser=new JCalendarChooser(jdialog);
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String dateStr=df.format(jcalendarchooser.showCalendarDialog().getTime());
			Datetext.setText(dateStr);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/*@Override
	public void focusGained(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==Datetext){
			jdialog=new JDialog();
			JCalendarChooser jcalendarchooser=new JCalendarChooser(jdialog);
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			String dateStr=df.format(jcalendarchooser.showCalendarDialog().getTime());
			Datetext.setText(dateStr);
		}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==Datetext)
			jdialog.dispose();
	}
	*/

}