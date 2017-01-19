package team.smms.gui.listener;


import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

public class PicChooser implements MouseListener{

    public JFileChooser jfc;
    public String dir;
    public JTextField text;

    public PicChooser(JTextField text){

        this.text=text;
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==text) {
            jfc = new JFileChooser();
            jfc.addChoosableFileFilter(new FileFilter() {
                public boolean accept(File file) {
                    return (file.getName().endsWith(".jpg"));
                }

                public String getDescription() {
                    return ".jpg";
                }
            });
            int i=jfc.showOpenDialog(new JDialog());
            if(i==JFileChooser.APPROVE_OPTION) {
                dir = jfc.getSelectedFile().getAbsolutePath().trim();
                text.setText(dir);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {


    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
