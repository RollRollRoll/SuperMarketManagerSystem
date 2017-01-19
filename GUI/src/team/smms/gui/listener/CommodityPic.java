package team.smms.gui.listener;

import team.smms.proxy.operation.Operation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CommodityPic{
	
	private JTable jtable;
	private JLabel jlabel;
	private Operation operation;
	private String commodityID;
	
	public CommodityPic(Operation operation,JTable jtable,int column) throws IOException{
		
		this.jtable=jtable;
		this.operation=operation;
		
		int row=this.jtable.getSelectedRow();
		commodityID=(String)(jtable.getModel().getValueAt(row,column));
		try{
			BufferedImage image= ImageIO.read(operation.searchPic(commodityID));
		
			jlabel=new JLabel();
			jlabel.setIcon(new ImageIcon(image));
			JOptionPane.showMessageDialog(null, jlabel, "商品图片", -1);
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null,"没有找到图片");
		}
	}
	
}