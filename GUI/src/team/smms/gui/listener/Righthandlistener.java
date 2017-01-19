package team.smms.gui.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPopupMenu;
import javax.swing.JTable;

public class Righthandlistener implements MouseListener{
	
	private JPopupMenu popupmenu;
	private JTable jtable;
	
	public Righthandlistener(JTable jtable,JPopupMenu popupmenu){
		
		this.jtable=jtable;
		this.popupmenu=popupmenu;
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public void mousePressed( MouseEvent e) {  
        triggerEvent(e);  
     }   

	@Override
     public void mouseReleased( MouseEvent e) {  
        triggerEvent(e);   
     }   

     private void triggerEvent(MouseEvent e) {  
        if (e.isPopupTrigger()){
        	int focusedRowIndex = jtable.rowAtPoint(e.getPoint());  
        	if (focusedRowIndex == -1) {  
        		return;  
        	}  
        	jtable.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
        	popupmenu.show(e.getComponent(),e.getX(),e.getY());  
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
	
}