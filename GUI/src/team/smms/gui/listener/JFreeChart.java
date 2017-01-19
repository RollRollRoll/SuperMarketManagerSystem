package team.smms.gui.listener;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.table.DefaultTableModel;

public class JFreeChart
{
	DefaultTableModel tablemodel;
	 DefaultPieDataset dpd;
	
	public JFreeChart(String title,DefaultTableModel tablemodel,int number,int name,int data,int x,int y){
		this.tablemodel=tablemodel;
		dpd=new DefaultPieDataset();
		for(int i=0;i<this.tablemodel.getRowCount();i++){
			dpd.setValue(((String)(tablemodel.getValueAt(i,number))+(String)(tablemodel.getValueAt(i,name))),(Number)(tablemodel.getValueAt(i, data)));
		}
		org.jfree.chart.JFreeChart chart=ChartFactory.createPieChart(title,dpd,true,true,false); 
		 ChartFrame chartFrame=new ChartFrame(title,chart);
		 chartFrame.pack();
		 chartFrame.setLocation(x,y);
		 chartFrame.setVisible(true);
	}
}