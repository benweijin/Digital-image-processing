package test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * bmp�ļ���ȡ����
 */
public class GuessYourChance  extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int map[][];//����������ɫ������
	int mapz[][];
    MyPanel center;//��ͼ���
    File selectFile;//��ȡ���ļ�
    int width;//ͼ����
    int height;//ͼ��߶�
    byte temp1[];//��ȡBMP�ļ���ǰ18���ֽ���Ϣ
    byte temp2[];//��ȡBMP�ļ��ĺ�28���ֽ���Ϣ
    byte temp3[];
    float realx[][];
    float imagx[][];
    float realt[];
    float imagt[];
    JScrollPane scrollpane;//�������
    public GuessYourChance() {
	      try {
		        UIManager
		        .setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
    this.setLayout(new BorderLayout());//��������ò�����������
    //��ʼ����ͼ���
    center=new MyPanel();
    center.setBackground(Color.WHITE);
    center.setPreferredSize(new Dimension(400,200));
    scrollpane=new JScrollPane(center);//��center��ʼ���������
    scrollpane.setPreferredSize(new Dimension(500,300));
    MyListener lis=new MyListener();
    
    //��ʼ���˵���
    JMenuBar menuBar=new JMenuBar();
    JMenu fileMenu1=new JMenu("file");
    JMenu fileMenu2=new JMenu("function1");
    JMenu fileMenu3=new JMenu("function2");
    JMenuItem open=new JMenuItem("open");
    JMenuItem save=new JMenuItem("save");
    open.addActionListener(lis);
    save.addActionListener(lis);
    fileMenu1.add(open);
    fileMenu1.add(save);
    
    JMenuItem dft=new JMenuItem("dft");
    dft.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			showdft();
		}}
    );
    JMenuItem idft=new JMenuItem("idft");
    idft.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			showidft();
		}}
    );
    fileMenu2.add(dft);
    fileMenu2.add(idft);
    
    JMenuItem smooth=new JMenuItem("smooth");
    smooth.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int input[][] = new int [7][7];
			for (int x = 0; x < 7; x++) {
				for (int y = 0; y < 7; y++) {
					input[x][y] = 1;
				}
			}
			filter(input);
		}}
    );
    JMenuItem sharpen=new JMenuItem("sharpen");
    smooth.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int input[][] = new int [3][3];
			input[0][1] = -1;
			input[1][0] = -1;
			input[1][1] = 5;
			input[1][2] = -1;
			input[2][1] = -1;
			filter(input);
		}}
    );
    fileMenu3.add(smooth);
    fileMenu3.add(sharpen);
    
    menuBar.add(fileMenu1);
    menuBar.add(fileMenu2);
    menuBar.add(fileMenu3);
    //
    JPanel left=new JPanel();
    left.setBackground(Color.DARK_GRAY);
    left.setPreferredSize(new Dimension(50,0));
    this.add(left,BorderLayout.WEST);
    this.setJMenuBar(menuBar);
    this.add(scrollpane,BorderLayout.CENTER);//������ǹ������
    this.setTitle("bmp");
    this.setLocation(300, 150);
    this.setSize(500, 400);
    this.setDefaultCloseOperation(3);
    this.setVisible(true);
    
}
    public void dft(float[][] a){
    	int h = a.length;
    	int w = a[0].length;
int tol = h*w;
float real[] = new float[tol];
float imag[] = new float[tol];
realx = new float[h][w];
imagx = new float[h][w];
         for(int x = 0; x < h; x++) {
	         for(int y = 0; y < w; y++){
	        	 a[x][y] *= Math.pow((-1), (x+y));
	 }
	 }

    	for(int x = 0; x < h; x++) {
	    	 for(int v = 0; v < w; v++){
	    		 for(int y = 0; y < w; y++) {
	    				real[x*w+v] += Math.cos(2 * Math.PI *v*y/(double)w)*a[x][y];
	    				imag[x*w+v] += (-1)* Math.sin(2 * Math.PI *v*y/(double)w)*a[x][y];
	    		 }
	    	 }
	    	 }
    	for(int u = 0; u < h; u++) {
	    	 for(int v = 0; v < w; v++){
	    		 for(int x = 0; x < h; x++) {
	    				realx[u][v] += Math.cos(2 * Math.PI *u*x/(double)h)*real[x*w+v]+ Math.sin(2 * Math.PI *u*x/(double)h)*imag[x*w+v];
	    				imagx[u][v] += (-1)*Math.sin(2 * Math.PI *u*x/(double)h)*real[x*w+v]+Math.cos(2 * Math.PI *u*x/(double)h)*imag[x*w+v];
	    		 }
	    	 }
	    	 }
    	for(int u = 0; u < h; u++) {
	    	 for(int v = 0; v < w; v++){
	    		 realx[u][v] = realx[u][v]/tol;
	    		 imagx[u][v] = imagx[u][v]/tol;
	    	 }
	    	 }
    }
  
    public void showdft(){
    	float ans[] = new float[height*width];
    	int tans[] = new int[height*width];
    	float mapp[][] = new float[height][width];
    	for(int u = 0; u < height; u++) {
	    	 for(int v = 0; v < width; v++){
	    		 mapp[u][v] = (float)mapz[u][v];
	    	 }
	    	 }
    	dft(mapp);
    	for(int u = 0; u < height; u++) {
	    	 for(int v = 0; v < width; v++){
	    		 ans[u*width+v] = (float) Math.sqrt(realx[u][v] * realx[u][v] + imagx[u][v]/(height*width) * imagx[u][v]/(height*width));
	    		 if(100*ans[u*width+v] < 255) tans[u*width+v] = (int)(100*ans[u*width+v]);
	    		 if(100*ans[u*width+v] >= 255) tans[u*width+v] = 255;
	    		 Color c = new Color(tans[u*width+v], tans[u*width+v], tans[u*width+v]);
	       	     map[u][v] = c.getRGB();
	    	 }
	    	 }
    	System.out.println("finshdft");
    }
    public void idft(float[][] a,float[][] b){
    	int h = a.length;
    	int w = a[0].length;
    	int tol = h*w;
    	float real[] = new float[tol];
    	float imag[] = new float[tol];
    	realt = new float[tol];
    	imagt = new float[tol];
    	for(int x = 0; x < h; x++) {
	    	 for(int v = 0; v < w; v++){
	    		 for(int y = 0; y < w; y++) {
	    				real[x*w+v] += Math.cos(2 * Math.PI *v*y/(double)w)*a[x][y]-Math.sin(2 * Math.PI *v*y/(double)w)*b[x][y];
	    				imag[x*w+v] += Math.sin(2 * Math.PI *v*y/(double)w)*a[x][y]+Math.cos(2 * Math.PI *v*y/(double)w)*b[x][y];
	    		 }
	    	 }
	    	 }
    	for(int u = 0; u < h; u++) {
	    	 for(int v = 0; v < w; v++){
	    		 for(int x = 0; x < h; x++) {
	    				realt[u*w+v] += Math.cos(2 * Math.PI *u*x/(double)h)*real[x*width+v]-Math.sin(2 * Math.PI *u*x/(double)h)*imag[x*w+v];
	    				imagt[u*w+v] += Math.sin(2 * Math.PI *u*x/(double)h)*real[x*width+v]+Math.cos(2 * Math.PI *u*x/(double)h)*imag[x*w+v];
	    		 }
	    	 }
	    	 }

    }
    
    public void showidft(){
    	int tans[] = new int[height*width];
    	idft(realx, imagx);
    	for(int u = 0; u < height; u++) {
	    	 for(int v = 0; v < width; v++){
	    		 realt[u*width+v] *= Math.pow((-1), (u+v));
	    		 
	    		 if(realt[u*width+v] < 255) tans[u*width+v] = (int)(realt[u*width+v]);
	    		 if(realt[u*width+v] >= 255) tans[u*width+v] = 255;
	    		 System.out.println(realt[u*width+v] + "+" + tans[u*width+v]);
	    		 Color c = new Color(tans[u*width+v], tans[u*width+v], tans[u*width+v]);
	       	     map[u][v] = c.getRGB();
	    	 }
	    	 }
    	
    	System.out.println("finshidft");
    }
    
    public void filter(int[][] aa){

         int h = mapz.length *2;
         int w = mapz[0].length *2;
         float pic[][] = new float [h][w];
         float fil[][] = new float [h][w];
         float real[][] = new float [h][w];
         float imag[][] = new float [h][w];
         float reala[][] = new float [h][w];
         float imaga[][] = new float [h][w];
         
         for (int x = 0; x < h ;x++) {
        	 for (int y = 0; y < w ; y++) {
        		 if (x < mapz.length && y < mapz[0].length){
        		 pic[x][y] = (float)mapz[x][y];
        		 } else {
        	     pic[x][y] = 0;
        		 }
        	 }
         }

         System.out.println("a1");
         dft(pic);
     	System.out.println("a");
         for (int x = 0; x < h ;x++) {
        	 for (int y = 0; y < w ; y++) {
        	real[x][y] = realx[x][y];
        	imag[x][y] = imagx[x][y];
        	 }
        	 }
         for (int x = 0; x < h ;x++) {
        	 for (int y = 0; y < w ; y++) {
        		 if(x < aa.length && y < aa[0].length) {
        			 fil[x][y] = (float)aa[x][y];
        		 } else {
        	     fil[x][y] = 0;
        		 }
        	 }
         }
         dft(fil);
     	System.out.println("b");
         for (int x = 0; x < h ;x++) {
        	 for (int y = 0; y < w ; y++) {
        		 reala[x][y] = real[x][y] * realx[x][y] - imag[x][y] * imagx[x][y];
        		 imaga[x][y] = imag[x][y] * realx[x][y] + imagx[x][y] * real[x][y];
     	       	 
        	 }
        	 }
         idft(reala, imaga);
     	System.out.println("c");
         int tans[] = new int[h*w];
         float max = 0;
         float min = 0;
     	for(int u = 0; u < height; u++) {
 	    	 for(int v = 0; v < width; v++){
 	    		realt[u*w+v] *= Math.pow((-1), (u+v));
 	    		if(realt[u*w+v] >= max) max = realt[u*w+v];
 	    		if(realt[u*w+v] <= min) min = realt[u*w+v];
 	    	 }
 	    	 }
     	for(int u = 0; u < height; u++) {
	    	 for(int v = 0; v < width; v++){
	    		 realt[u*w+v] = 255 * (realt[u*w+v] - min) / (max - min);
	    		 System.out.println(realt[u*w+v]);
 	    		 if(realt[u*w+v] < 255 && realt[u*w+v] > 0) tans[u*w+v] = (int)(realt[u*w+v]);
 	    		 if(realt[u*w+v] >= 255) tans[u*w+v] = 255;
 	    		if(realt[u*w+v] <= 0) tans[u*w+v] = 0;
 	    		 Color c = new Color(tans[u*w+v], tans[u*w+v], tans[u*w+v]);
 	       	     map[u][v] = c.getRGB();
	    	 }
	    	 }
     	System.out.println("d");
         
         
    }
/**
   * ��ȡbmp�ļ�
   */
public void readBMP(int pic_bit) {
	try {
        FileInputStream fis=new FileInputStream(selectFile);
        BufferedInputStream bis=new BufferedInputStream(fis);
        byte[] wb=new byte[4];//��ȡ��ȵ��ֽ�����
        byte[] hb=new byte[4];//��ȡ�߶ȵ��ֽ�����
        temp1=new byte[18];
        bis.read(temp1);//bis.skip(18);//����ǰ18��byte
        bis.read(wb);//��ȡ���
        bis.read(hb);//��ȡ�߶�
        width=byteToint(wb);
        height=byteToint(hb);
        map=new int[height][width];
        mapz=new int[height][width];
        int skip=4-width*3%4;//�õ�ÿ��Ҫ����������(��windows ϵͳ�����й�)
        temp2=new byte[28];
        bis.read(temp2);//bis.skip(28);
        if (pic_bit == 8) {
        temp3=new byte[1024];
        bis.read(temp3);
        }
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
            	if(pic_bit==24) {
                int blue=bis.read();
                int green=bis.read();
                int red=bis.read();
                Color c=new Color(red,green,blue);
                map[i][j]=c.getRGB();
            	}
            	if(pic_bit==8) {
            		int gray=bis.read();
                    Color c=new Color(gray, gray, gray);
                    map[i][j]=c.getRGB();
            	}
        }
        if(skip!=4)
        bis.skip(skip);
    }
    bis.close();
    for(int i = 0; i < height; i++) {
        for(int j = 0; j < width; j++) {
      	  Color c = new Color(map[i][j]);
      	  int gray = (int) (c.getRed());
      	  mapz[i][j] = gray;
      	  
    }
}
    center.setPreferredSize(new Dimension(width,height));
    javax.swing.SwingUtilities.updateComponentTreeUI(center);//�������Ҫ��updateComponentTreeUI(center)����
    //������repaint()����

    } catch (Exception e) {
      e.printStackTrace();
    }

}
    
public void writeBMP(int pic_bit) {
    try {
      FileOutputStream fos=new FileOutputStream(selectFile);
      BufferedOutputStream bos=new BufferedOutputStream(fos);
      bos.write(temp1);//
      bos.write(intTobyte(width,4));//д����
      bos.write(intTobyte(height,4));//д��߶�
      bos.write(temp2);
      if(pic_bit == 8) {
      bos.write(temp3);
      }
      int skip=0;
      if(width * 3 % 4 != 0)
      {
        skip = 4 - width * 3 % 4;
      }

      for(int i = 0; i < height; i++)
      {
        for(int j = 0; j < width; j++)
        {
        	if(pic_bit==24) {
          Color c = new Color(map[i][j]);
          int red = c.getBlue();
          int green = c.getGreen();
          int blue = c.getRed();
          bos.write(blue);
          bos.write(green);
          bos.write(red);
        	} else {
        		Color c = new Color(map[i][j]);
                int gray = c.getBlue();
                bos.write(gray);
        	}
          if(j == 0) {
              bos.write(new byte[skip]);
        }
        }
      }
      bos.flush();
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  //�ֽ�תint
  public static  int byteToint(byte b[])
  {
    int t1=(b[3]&0xff)<<24;
    int t2=(b[2]&0xff)<<16;
    int t3=(b[1]&0xff)<<8;
    int t4=b[0]&0xff;
    //System.out.println(b[1]&0xff);//�������һ����������
    //��java�У����int�ͱ�intλ������С������b����byte,char�ȣ������Ȱ�С������չ��int�������㣬
    //return( t1<<24)+(t2<<16)+(t3<<8)+t4;//���������
    return t1+t2+t3+t4;
  }
  //int תbyte
  public static byte[] intTobyte(int a,int len)
  {
    byte []t=new byte[len];
      t[0]=(byte) ((a&0xff));
      if(len>1)
      t[1]=(byte)((a&0xff00)>>8);
      if(len>2)
      t[2]=(byte)((a&0xff0000)>>16);
      if(len>3)
      t[3]=(byte)((a&0xff000000)>>24);
    return t;
  }
  /**
   * ��ͼ���
   * @author ZhangZunC
   *
   */
class MyPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public void paint(Graphics g) {
      super.paint(g);
      if(map!=null)
      {
        for(int i = 0; i < map.length;i++)
        {
          for(int j = 0; j < map[i].length;j++)
          {
            g.setColor(new Color(map[i][j]));
            g.drawLine(j, height - i, j, height - i);
          }
        }
      }
    }
  }
  
  class MyListener implements ActionListener{

	JFileChooser fileChosser;

      FileNameExtensionFilter filter8=new FileNameExtensionFilter("8λλͼ(*.bmp)", "bmp");
      FileNameExtensionFilter filter24=new FileNameExtensionFilter("24λλͼ(*.bmp)", "bmp");
      MyListener(){
      fileChosser=new JFileChooser();
      fileChosser.setFileFilter(filter24);
      fileChosser.setFileFilter(filter8);
      fileChosser.setCurrentDirectory(new File("\\"));}

    public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand().equals("open"))//ѡ����Ǵ�
      {
        int choose=fileChosser.showOpenDialog(null);
        if(choose==JFileChooser.APPROVE_OPTION)//�������ȷ����ť
        {
          selectFile=fileChosser.getSelectedFile();
          if(fileChosser.getFileFilter() == filter24)
          readBMP(24);
          if(fileChosser.getFileFilter() == filter8)
              readBMP(8);
        }
      }
      if(e.getActionCommand().equals("save"))//ѡ����Ǵ�
      {
        int choose=fileChosser.showSaveDialog(null);
        if(choose==JFileChooser.APPROVE_OPTION)
        {
          selectFile=fileChosser.getSelectedFile();
          if(fileChosser.getFileFilter() == filter24)
          writeBMP(24);
          if(fileChosser.getFileFilter() == filter8)
              writeBMP(8);
        }
      }
    }
    
  }
  
  
    public static void main(String[] args) {
    new GuessYourChance();
    
}
}  