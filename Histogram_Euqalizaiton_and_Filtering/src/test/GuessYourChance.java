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
 * bmp文件读取保存
 */
public class GuessYourChance  extends JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int map[][];//保存像素颜色的数组
	int mapz[][];
    MyPanel center;//绘图面板
    File selectFile;//读取的文件
    int width;//图像宽度
    int height;//图像高度
    byte temp1[];//读取BMP文件的前18个字节信息
    byte temp2[];//读取BMP文件的后28个字节信息
    byte temp3[];
    int gr[] = new int [256];
    int equ[] = new int [256];
    private BufferedImage histogramImage = new BufferedImage(280,280, BufferedImage.TYPE_4BYTE_ABGR);;
    JScrollPane scrollpane;//滚动面板
    public GuessYourChance() {
	      try {
		        UIManager
		        .setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
    this.setLayout(new BorderLayout());//最好先设置布局再添加组件
    //初始化画图面板
    center=new MyPanel();
    center.setBackground(Color.WHITE);
    center.setPreferredSize(new Dimension(400,200));
    scrollpane=new JScrollPane(center);//用center初始化滚动面板
    scrollpane.setPreferredSize(new Dimension(500,300));
    MyListener lis=new MyListener();
    
    //初始化菜单栏
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
    
    JMenuItem histogram=new JMenuItem("histogram");
    histogram.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			histogram();
		}
    });
    JMenuItem equalization=new JMenuItem("equalization");
    equalization.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			equalization();
		}
    });
    fileMenu2.add(histogram);
    fileMenu2.add(equalization);
    JMenuItem a_3=new JMenuItem("3*3");
    a_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3);
		}
    });
    JMenuItem a_7=new JMenuItem("7*7");
    a_7.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(7);
		}
    });
    JMenuItem a_11=new JMenuItem("11*11");
    a_11.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(11);
		}
    });
    JMenuItem sharpen=new JMenuItem("sharpen");
    sharpen.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			sharpen();
		}
    });
    JMenuItem high=new JMenuItem("high_boost");
    high.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			high_boost();
		}
    });
    fileMenu3.add(a_3);
    fileMenu3.add(a_7);
    fileMenu3.add(a_11);
    fileMenu3.add(sharpen);
    fileMenu3.add(high);
    menuBar.add(fileMenu1);
    menuBar.add(fileMenu2);
    menuBar.add(fileMenu3);
    //
    JPanel left=new JPanel();
    left.setBackground(Color.DARK_GRAY);
    left.setPreferredSize(new Dimension(50,0));
    this.add(left,BorderLayout.WEST);
    this.setJMenuBar(menuBar);
    this.add(scrollpane,BorderLayout.CENTER);//加入的是滚动面板
    this.setTitle("bmp");
    this.setLocation(300, 150);
    this.setSize(500, 400);
    this.setDefaultCloseOperation(3);
    this.setVisible(true);
    
}
    
  public void histogram() {
		// TODO Auto-generated method stub
	  for (int i = 0;i < 256; i++) {
		  gr[i] = 0;
	  }
      for(int i = 0; i < height; i++) {
          for(int j = 0; j < width; j++) {
        	  Color c = new Color(map[i][j]);
        	  int gray = (int) (c.getRed());
        	  gr[gray]++;
        	  
      }
  }
	  
	  Graphics2D g2d = histogramImage.createGraphics();
      g2d.setPaint(Color.BLACK);
      g2d.fillRect(0, 0, 280, 280);
      g2d.setPaint(Color.WHITE);
      g2d.drawLine(5, 250, 265, 250);
      g2d.drawLine(5, 250, 5, 5);
      
      g2d.setPaint(Color.GREEN);
      int max = findMaxValue(gr);
      float rate = 200.0f/((float)max);
      int offset = 2;
      for(int i=0; i<gr.length; i++) {
      	int frequency = (int)(gr[i] * rate);
      	g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250-frequency);
      }
      
      JFrame jframe = new JFrame("直方图"); 
      jframe.add(new JPanel() {
          /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g) {
              super.paintComponent(g);
              g.drawImage(histogramImage, 0, 0, this);
          }

      }, BorderLayout.CENTER);
      jframe.setSize(330, 330);  
      jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
      jframe.setVisible(true); 
      
	}
  
  private int findMaxValue(int[] intensity) {
		int max = -1;
		for(int i=0; i<intensity.length; i++) {
			if(max < intensity[i]) {
				max = intensity[i];
			}
		}
		return max;
	}
  
  
  public void equalization() {
	  for (int i = 0;i < 256; i++) {
		  gr[i] = 0;
	  }
      for(int i = 0; i < height; i++) {
          for(int j = 0; j < width; j++) {
        	  Color c = new Color(map[i][j]);
        	  int gray = (int) (c.getRed());
        	  gr[gray]++;
        	  
      }
  }
      for(int i = 0; i < 256; i++) {
		  equ[i] = 0;
	  }
	  for(int i = 0; i < 256; i++) {
		  for(int j = 0; j < i; j++) {
			  equ[i]+=gr[j];
		  }
	  }
	  for(int i = 0; i < 256; i++) {
		  equ[i] =(int)(equ[i]*255/(width*height));
	  }
	  
	  for(int i = 0; i < height; i++) {
          for(int j = 0; j < width; j++) {
        	  Color c = new Color(map[i][j]);
        	  int gray = c.getRed();
        	  c = new Color(equ[gray], equ[gray], equ[gray]);
        	  map[i][j] = c.getRGB();      		
      }
  }
	  
	  
  }
  
  
  public void average(int t){
     int fla[][] = new int [height][width];
     int num = (t-1)/2;
     for(int i = 0; i < height; i++) {
    	 for(int j = 0; j < width; j++){
    		 int ans = 0;
             for(int k = 0; k < t; k++){
            	 for(int l = 0; l < t; l++){
            		 if(i - num + k >= 0 && i - num + k < height && j - num + l >= 0 && j- num + l < width)
            		 ans+=mapz[i-num+k][j-num+l];
            	 }
             }
             ans =(int)(ans/(t*t));
             fla[i][j] = ans;
    	 }
     }
     
     for(int i = 0; i < height; i++) {
    	 for(int j = 0; j < width; j++){
    		 mapz[i][j] = fla[i][j];
       	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
       	     map[i][j] = c.getRGB();
    	 }
    	 }
	  
  }
  
  public void sharpen(){
	     int fla[][] = new int [height][width];
	     for(int i = 0; i < height; i++) {
	    	 for(int j = 0; j < width; j++){
	    		 int ans = 0;
	             for(int k = 0; k < 3; k++){
	            	 for(int l = 0; l < 3; l++){
	            		 if(k == 1 && l == 1) {
	            		ans+=8*mapz[i-1+k][j-1+l];
	            		 } else if (i - 1 + k >= 0 && i - 1 + k < height && j - 1 + l >= 0 && j- 1 + l < width)
	            		ans-=mapz[i-1+k][j-1+l];
	            	 }
	             }
	             if(ans < 0){
	            	 fla[i][j] = 0;
	             } else {
	             fla[i][j] = ans;
	    	 }
	    	 }
	     }
	     for(int i = 0; i < height; i++) {
	    	 for(int j = 0; j < width; j++){
	    		 if(mapz[i][j]+fla[i][j] > 255) {
	    			 mapz[i][j] = 255;
	    		 } else if(mapz[i][j]+fla[i][j] < 0) {
	    			 mapz[i][j] = 0;
	    		 }else {
	    			 mapz[i][j] = mapz[i][j]+fla[i][j];
	    		 }
	       	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
	       	     map[i][j] = c.getRGB();
	    	 }
	    	 }
  }
  
  public void high_boost(){
	     int fla[][] = new int [height][width];
	     for(int i = 0; i < height; i++) {
	    	 for(int j = 0; j < width; j++){
	    		 int ans = 0;
	             for(int k = 0; k < 3; k++){
	            	 for(int l = 0; l < 3; l++){
	            		 if(i - 1 + k >= 0 && i - 1 + k < height && j - 1 + l >= 0 && j- 1 + l < width)
	            		 ans+=mapz[i-1+k][j-1+l];
	            	 }
	             }
	             ans =(int)(ans/9);
	             fla[i][j] = ans;
	    	 }
	     }
	     for(int i = 0; i < height; i++) {
	    	 for(int j = 0; j < width; j++){
	    		 mapz[i][j] = 3 * mapz[i][j] - 2 * fla[i][j];
	    		 if(mapz[i][j] > 255) mapz[i][j] = 255;
	    		 if(mapz[i][j] < 0) mapz[i][j] = 0;
	       	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
	       	     map[i][j] = c.getRGB();
	    	 }
	    	 }
  }
  
/**
   * 读取bmp文件
   */
public void readBMP(int pic_bit) {
	try {
        FileInputStream fis=new FileInputStream(selectFile);
        BufferedInputStream bis=new BufferedInputStream(fis);
        byte[] wb=new byte[4];//读取宽度的字节数组
        byte[] hb=new byte[4];//读取高度的字节数组
        temp1=new byte[18];
        bis.read(temp1);//bis.skip(18);//跳过前18个byte
        bis.read(wb);//读取宽度
        bis.read(hb);//读取高度
        width=byteToint(wb);
        height=byteToint(hb);
        map=new int[height][width];
        mapz=new int[height][width];
        int skip=4-width*3%4;//得到每行要跳过的数字(与windows 系统机制有关)
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
    javax.swing.SwingUtilities.updateComponentTreeUI(center);//这里必须要用updateComponentTreeUI(center)函数
    //不能用repaint()函数

    } catch (Exception e) {
      e.printStackTrace();
    }

}
    
public void writeBMP(int pic_bit) {
    try {
      FileOutputStream fos=new FileOutputStream(selectFile);
      BufferedOutputStream bos=new BufferedOutputStream(fos);
      bos.write(temp1);//
      bos.write(intTobyte(width,4));//写入宽度
      bos.write(intTobyte(height,4));//写入高度
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
  //字节转int
  public static  int byteToint(byte b[])
  {
    int t1=(b[3]&0xff)<<24;
    int t2=(b[2]&0xff)<<16;
    int t3=(b[1]&0xff)<<8;
    int t4=b[0]&0xff;
    //System.out.println(b[1]&0xff);//输出的是一个整形数据
    //在java中，设计int和比int位数来的小的类型b，如byte,char等，都是先把小类型扩展成int再来运算，
    //return( t1<<24)+(t2<<16)+(t3<<8)+t4;//必须加括号
    return t1+t2+t3+t4;
  }
  //int 转byte
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
   * 绘图面板
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

      FileNameExtensionFilter filter8=new FileNameExtensionFilter("8位位图(*.bmp)", "bmp");
      FileNameExtensionFilter filter24=new FileNameExtensionFilter("24位位图(*.bmp)", "bmp");
      MyListener(){
      fileChosser=new JFileChooser();
      fileChosser.setFileFilter(filter24);
      fileChosser.setFileFilter(filter8);
      fileChosser.setCurrentDirectory(new File("\\"));}

    public void actionPerformed(ActionEvent e) {
      if(e.getActionCommand().equals("open"))//选择的是打开
      {
        int choose=fileChosser.showOpenDialog(null);
        if(choose==JFileChooser.APPROVE_OPTION)//点击的是确定按钮
        {
          selectFile=fileChosser.getSelectedFile();
          if(fileChosser.getFileFilter() == filter24)
          readBMP(24);
          if(fileChosser.getFileFilter() == filter8)
              readBMP(8);
        }
      }
      if(e.getActionCommand().equals("save"))//选择的是打开
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