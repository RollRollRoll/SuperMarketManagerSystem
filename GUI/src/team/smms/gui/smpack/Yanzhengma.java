package team.smms.gui.smpack;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class Yanzhengma {

    String s;
    public String getS() {
        s=s.toLowerCase();
        return s;
    }
    BufferedImage img;
    public BufferedImage getImg() {
        return img;
    }
    public static Random random = new Random();
    public static int r(int min,int max){
        int num=0;
        num=random.nextInt(max-min)+min;
        return num;
    }
    public Yanzhengma()throws IOException {
        // TODO Auto-generated method stub
        //在内存中创建一副图片
        int w=120;
        int h=50;
        img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //在图片上画一个矩形当背景
        Graphics g = img.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, w, h);

        String str = "ABDEFGHJKLMNPQRSTUVWXYZabdfghijkmnpqrstuvwxyz23456789";
        s="";
        for(int i=0;i<4;i++){
            g.setColor(new Color(r(50,180),r(50,180),r(50,180)));
            g.setFont(new Font("黑体",Font.PLAIN,40));
            char c = str.charAt(r(0,str.length()));
            g.drawString(String.valueOf(c), 10+i*30, 30);
            s+=String.valueOf(c);
        }

        //画随机线
        for(int i=0;i<25;i++){
            g.setColor(new Color(r(50,180),r(50,180),r(50,180)));
            g.drawLine(r(0,w), r(0,h),r(0,w), r(0,h));
        }

    }

}