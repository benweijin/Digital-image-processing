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
	int mapg[][];
	int mapb[][];
    MyPanel center;//绘图面板
    File selectFile;//读取的文件
    int width;//图像宽度
    int height;//图像高度
    byte temp1[];//读取BMP文件的前18个字节信息
    byte temp2[];//读取BMP文件的后28个字节信息
    byte temp3[];
    int gr_r[] = new int [256];
    int gr_g[] = new int [256];
    int gr_b[] = new int [256];
    int equ_r[] = new int [256];
    int equ_g[] = new int [256];
    int equ_b[] = new int [256];
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
    JMenu fileMenu2=new JMenu("滤波器");
    JMenu fileMenu3=new JMenu("加噪声");
    JMenu fileMenu4=new JMenu("彩色图像");
    JMenuItem open=new JMenuItem("open");
    JMenuItem save=new JMenuItem("save");
    open.addActionListener(lis);
    save.addActionListener(lis);
    fileMenu1.add(open);
    fileMenu1.add(save);
    
    
    JMenuItem a_3=new JMenuItem("算数均值3*3");
    a_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 0);
		}
    });
    JMenuItem a_9=new JMenuItem("算数均值9*9");
    a_9.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(9, 0);
		}
    });
    JMenuItem b_3=new JMenuItem("调和均值3*3");
    b_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 1);
		}
    });
    JMenuItem b_9=new JMenuItem("调和均值9*9");
    b_9.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(9, 1);
		}
    });
    JMenuItem c_3=new JMenuItem("反调和均值3*3");
    c_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 2);
		}
    });
    JMenuItem c_9=new JMenuItem("反调和均值9*9");
    c_9.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(9, 2);
		}
    });
    JMenuItem d_3=new JMenuItem("几何均值3*3");
    d_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 3);
		}
    });
    JMenuItem e_3=new JMenuItem("中值均值3*3");
    e_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 4);
		}
    });
    JMenuItem f_3=new JMenuItem("最大值3*3");
    f_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 5);
		}
    });
    JMenuItem g_3=new JMenuItem("最小值3*3");
    g_3.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			average(3, 6);
		}
    });

    fileMenu2.add(a_3);
    fileMenu2.add(a_9);
    fileMenu2.add(b_3);
    fileMenu2.add(b_9);
    fileMenu2.add(c_3);
    fileMenu2.add(c_9);
    fileMenu2.add(d_3);
    fileMenu2.add(e_3);
    fileMenu2.add(f_3);
    fileMenu2.add(g_3);
    
    JMenuItem gassnoise=new JMenuItem("加高斯噪声");
    gassnoise.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addnoise();
		}
    });
    JMenuItem saltnoise=new JMenuItem("加盐粒噪声");
    saltnoise.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			saltnoise(0.2);
		}
    });
    JMenuItem peppernoise=new JMenuItem("加椒盐噪声");
    peppernoise.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			peppernoise(0.2,0.2);
		}
    });
    fileMenu3.add(gassnoise);
    fileMenu3.add(saltnoise);
    fileMenu3.add(peppernoise);
    
    JMenuItem redgram=new JMenuItem("红色直方图");
    redgram.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			histogram(1);
		}
    });
    JMenuItem greengram=new JMenuItem("绿色直方图");
    greengram.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			histogram(2);
		}
    });
    JMenuItem bluegram=new JMenuItem("蓝色直方图");
    bluegram.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			histogram(3);
		}
    });
    JMenuItem equalization=new JMenuItem("分别均衡化");
    equalization.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			equalization(1);
		}
    });
    JMenuItem equalization1=new JMenuItem("均值均衡化");
    equalization1.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			equalization(2);
		}
    });
    
    fileMenu4.add(redgram);
    fileMenu4.add(greengram);
    fileMenu4.add(bluegram);
    fileMenu4.add(equalization);
    fileMenu4.add(equalization1);
    
    menuBar.add(fileMenu1);
    menuBar.add(fileMenu2);
    menuBar.add(fileMenu3);
    menuBar.add(fileMenu4);
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
    
  
  
  public void average(int t, int type){
     int fla[][] = new int [height][width];
     int num = (t-1)/2;
     if(type == 0){
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
     } else if(type == 1){
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        		 double ans = 0;
                 for(int k = 0; k < t; k++){
                	 for(int l = 0; l < t; l++){
                		 if(i - num + k >= 0 && i - num + k < height && j - num + l >= 0 && j- num + l < width)
                		 ans+=1/(mapz[i-num+k][j-num+l]+0.00000000001);
                	 }
                 }
                 ans =((t*t)/ans);
                 fla[i][j] =(int) ans;
        	 }
         } 
     } else if(type == 2) {
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        		 double ans_t = 0;
        		 double ans_b = 0;
        		 double ans = 0;
                 for(int k = 0; k < t; k++){
                	 for(int l = 0; l < t; l++){
                		 if(i - num + k >= 0 && i - num + k < height && j - num + l >= 0 && j- num + l < width) {
                		 ans_t+=Math.pow(mapz[i-num+k][j-num+l],1.5);
                		 ans_b+=Math.pow(mapz[i-num+k][j-num+l],0.5);
                		 }
                	 }
                 }
                 ans = ans_t / ans_b;
                 fla[i][j] =(int) ans;
        	 }
         } 
     } else if(type == 3) {
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        		 double ans = 1.0;
                 for(int k = 0; k < t; k++){
                	 for(int l = 0; l < t; l++){
                		 if(i - num + k >= 0 && i - num + k < height && j - num + l >= 0 && j- num + l < width) {
                		 ans *= mapz[i-num+k][j-num+l];
                		 }
                	 }
                 }
                 fla[i][j] =(int) Math.pow(ans, 1/(t*t+0.0));
        	 }
         } 
     } else if(type >= 4) {
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        		 int temp[] = new int [t*t];
                 for(int k = 0; k < t; k++){
                	 for(int l = 0; l < t; l++){
                		 if(i - num + k >= 0 && i - num + k < height && j - num + l >= 0 && j- num + l < width) {
                		 temp[k*t+l] = mapz[i-num+k][j-num+l];
                		 }
                	 }
                 }
                 bubbleSort(temp);
                 if(type == 4) fla[i][j] =(int)(temp[t*t/2]);
                 if(type == 5) fla[i][j] =(int)(temp[t*t-1]);
                 if(type == 6) fla[i][j] =(int)(temp[0]);
        	 }
         } 
     } 

     
     for(int i = 0; i < height; i++) {
    	 for(int j = 0; j < width; j++){
    		 if(fla[i][j] > 255) fla[i][j] = 255;
    		 if(fla[i][j] < 0) fla[i][j] = 0;
    		 mapz[i][j] = fla[i][j];
       	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
       	     map[i][j] = c.getRGB();
    	 }
    	 }
  }
  
  public void bubbleSort(int a[]) { 
	    int n = a.length; 
	    for (int i = 0; i < n - 1; i++) { 
	      for (int j = 0; j < n - 1; j++) { 
	        if (a[j] > a[j + 1]) { 
	          int temp = a[j]; 
	          a[j] = a[j + 1]; 
	          a[j + 1] = temp; 
	        } 
	      } 
	    } 
	} 
  
  public void addnoise(){
         int gass[][] = new int [height][width];
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        	gass[i][j] = (int)(7000*get2Ddata(0, 40, 255*Math.random()));	 
        	mapz[i][j] += gass[i][j];
        	 }
        	 }
         for(int i = 0; i < height; i++) {
        	 for(int j = 0; j < width; j++){
        		 if(mapz[i][j] > 255) mapz[i][j] = 255;
        		 if(mapz[i][j] < 0) mapz[i][j] = 0;
           	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
           	     map[i][j] = c.getRGB();
        	 }
        	 }
         
  }
  
  private double get2Ddata(int n, double stan, double x){
         double ans;
         double temp1 = stan * stan * 2;
         double temp2 = Math.pow((2*Math.PI), (1/2)) * stan;
         ans = Math.exp(-(x-n)*(x-n)/temp1)/temp2;
         return ans;
  }

  public void saltnoise(double x){
      for(int i = 0; i < height; i++) {
     	 for(int j = 0; j < width; j++){
     	if(Math.random()<x)	 
     	mapz[i][j] = 255;
     	 }
     	 }
      for(int i = 0; i < height; i++) {
     	 for(int j = 0; j < width; j++){
     		 if(mapz[i][j] > 255) mapz[i][j] = 255;
     		 if(mapz[i][j] < 0) mapz[i][j] = 0;
        	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
        	     map[i][j] = c.getRGB();
     	 }
     	 }
      
}
  
  public void peppernoise(double x, double y){
      for(int i = 0; i < height; i++) {
     	 for(int j = 0; j < width; j++){
     	if(Math.random()<x)	 
     	mapz[i][j] = 255;
     	if(Math.random()>x && Math.random()<x+y)	 
     	mapz[i][j] = 0;
     	 }
     	 }
      for(int i = 0; i < height; i++) {
     	 for(int j = 0; j < width; j++){
     		 if(mapz[i][j] > 255) mapz[i][j] = 255;
     		 if(mapz[i][j] < 0) mapz[i][j] = 0;
        	     Color c = new Color(mapz[i][j], mapz[i][j], mapz[i][j]);
        	     map[i][j] = c.getRGB();
     	 }
     	 }
      
}
  
  public void histogram(int type) {
		// TODO Auto-generated method stub
	  for (int i = 0;i < 256; i++) {
		  gr_r[i] = 0;
		  gr_g[i] = 0;
		  gr_b[i] = 0;
	  }
    for(int i = 0; i < height; i++) {
        for(int j = 0; j < width; j++) {
      	  gr_r[mapz[i][j]]++;
      	  gr_g[mapg[i][j]]++;
      	  gr_b[mapb[i][j]]++;
      	  
    }
}
    for (int i = 0;i < 256; i++) {
	  System.out.println(gr_r[i]+" "+gr_g[i]+" "+gr_b[i]);
    }
	 if(type==1)shoow(gr_r,1);
	 if(type==2)shoow(gr_g,2);
	 if(type==3)shoow(gr_b,3);
	}
  
private void shoow(int[] gr, int type){
	Graphics2D g2d = histogramImage.createGraphics();
    g2d.setPaint(Color.BLACK);
    g2d.fillRect(0, 0, 280, 280);
    g2d.setPaint(Color.WHITE);
    g2d.drawLine(5, 250, 265, 250);
    g2d.drawLine(5, 250, 5, 5);
    
    g2d.setPaint(Color.GREEN);
    int max = 2800;
    float rate = 200.0f/((float)max);
    int offset = 2;
    for(int i=0; i<gr.length; i++) {
    	int frequency = (int)(gr[i] * rate);
    	g2d.drawLine(5 + offset + i, 250, 5 + offset + i, 250-frequency);
    }
    JFrame jframe = new JFrame(); 
    if(type == 1) jframe.setTitle("红色直方图");
    if(type == 2) jframe.setTitle("绿色直方图");
    if(type == 3) jframe.setTitle("蓝色直方图");
    jframe.add(new JPanel() {
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


public void equalization(int type) {
	int[] equ = new int [256];
	
	  for (int i = 0;i < 256; i++) {
		  gr_r[i] = 0;
		  gr_g[i] = 0;
		  gr_b[i] = 0;
	  }
    for(int i = 0; i < height; i++) {
        for(int j = 0; j < width; j++) {
      	  gr_r[mapz[i][j]]++;
      	  gr_g[mapg[i][j]]++;
      	  gr_b[mapb[i][j]]++;
      	  
    }
    }
    for(int i = 0; i < 256; i++) {
		  equ_r[i] = 0;
		  equ_g[i] = 0;
		  equ_b[i] = 0;
	  }
	  for(int i = 0; i < 256; i++) {
		  for(int j = 0; j < i; j++) {
			  equ_r[i]+=gr_r[j];
			  equ_g[i]+=gr_g[j];
			  equ_b[i]+=gr_b[j];
		  }
	  }
	  for(int i = 0; i < 256; i++) {
		  equ_r[i] =(int)(equ_r[i]*255/(width*height));
		  equ_g[i] =(int)(equ_g[i]*255/(width*height));
		  equ_b[i] =(int)(equ_b[i]*255/(width*height));
		  equ[i] = (int)((equ_r[i]+equ_g[i]+equ_b[i])/3);
	  }
	  
	  for(int i = 0; i < height; i++) {
        for(int j = 0; j < width; j++) {
      	Color c = new Color(map[i][j]);
      	int red = c.getRed();
      	int green = c.getGreen();
      	int blue = c.getBlue();
      	if(type==1){
      	  c = new Color(equ_r[red], equ_g[green], equ_b[blue]);
      	} else {
      	  c = new Color(equ[red], equ[green], equ[blue]);
      	}
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
        mapg=new int[height][width];
        mapb=new int[height][width];
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
      	  int red = (int) (c.getRed());
      	  mapz[i][j] = red;
      	  int blue = (int) (c.getBlue());
      	  mapb[i][j] = blue;
      	  int green = (int) (c.getGreen());
      	  mapg[i][j] = green;
      	  
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