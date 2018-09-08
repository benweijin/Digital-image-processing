package dip;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;


import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;  
  
  
public class Bmpreader extends javax.swing.JFrame {  
    private static final long serialVersionUID = 1L;  
  
    /** 
     * 位图的宽 
     */  
    private static int width;  
  
    /** 
     * 位图的高 
     */  
    private static int height;  
  
    
    private static int poi;  

	/** 
     * 位图数据数组,即一个像素的三个分量的数据数组 
     */  
    private static int[][] red, green, blue;  
    
    public int wid;
    public int hei;
    public int level = 1;

  
    Graphics g;  
  
    public static void main(String args[]) {  
    	Bmpreader bmp = new Bmpreader();  
        bmp.inat();  
    }  
  
    private void inat() {
    	JFrame jf;
    	JPanel jp;
    	JTextField jtf2,jtf3;
    	JButton op;
        jf = new JFrame("请输入图片路径");
		
		Container contentPane = jf.getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		jp = new JPanel();
		
		jtf2 = new JTextField(20);
		op = new JButton("open");
		op.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					init(jtf2.getText());
					jf.setVisible(false);
			}
        });

		jp.add(jtf2);
		jp.add(op);
		contentPane.add(jp);
		
		jf.pack();
		jf.setLocation(400, 200);
		jf.setVisible(true);
	}

	public void init(String a) {  
        try {  
            java.io.FileInputStream fin = new java.io.FileInputStream(  
                    a);  
            java.io.BufferedInputStream bis = new java.io.BufferedInputStream(  
                    fin);  
            byte[] array1 = new byte[14];  
            bis.read(array1, 0, 14);  
  
            byte[] array2 = new byte[40];  
            bis.read(array2, 0, 40);  
            width = ChangeInt(array2, 7);  
            height = ChangeInt(array2, 11); 
            poi = ChangeIntx(array2, 15);
            if(poi == 8) {
                byte[] array3 = new byte[1024];  
                bis.read(array3, 0, 1024); 
            }
            getInf(bis);  
  
            fin.close();  
            bis.close();  
            showUI();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  

    public int ChangeInt(byte[] array2, int start) {  
        int i = (int) ((array2[start] & 0xff) << 24)  
                | ((array2[start - 1] & 0xff) << 16)  
                | ((array2[start - 2] & 0xff) << 8)  
                | (array2[start - 3] & 0xff);  
        return i;  
    }  
    
    
    public int ChangeIntx(byte[] array2, int start) {  
        int i = (int)  ((array2[start] & 0xff) << 8)  
                | (array2[start - 1] & 0xff);  
        return i;  
    } 
  
    public void getInf(java.io.BufferedInputStream bis) {  
        // 给数组开辟空间  
        red = new int[height][width];  
        green = new int[height][width];  
        blue = new int[height][width];  
  
        if (poi == 24) {
        int skip_width = 0;  
        int m = width * 3 % 4;  
        if (m != 0) {  
            skip_width = 4 - m;  
        }  
  
        for (int i = height - 1; i >= 0; i--) {  
            for (int j = 0; j < width; j++) {  
                try {    
                    blue[i][j] = bis.read();  
                    green[i][j] = bis.read();  
                    red[i][j] = bis.read();  
                    if (j == 0) {  
                        bis.skip(skip_width);  
                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  

        } else {
        	for (int i = height - 1; i >= 0; i--) {  
                for (int j = 0; j < width; j++) {  
                    try {  
                        red[i][j] = bis.read();  
                        green[i][j] = red[i][j];
                        blue[i][j] = red[i][j];

     
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
        	}
        	}
        	
        
    }  
  
    public void showUI() {  
    	
        // 对窗体的属性进行设置  
        this.setTitle("数图作业1");//设置标题  
        this.setSize(width+10, height+60);//设置窗体大小  
        this.setDefaultCloseOperation(3);//点击关闭，程序自动退出。  
        this.setResizable(false);//设置窗体大小不可以调节  
        this.setLocationRelativeTo(null);//设置窗体出现在屏幕中间  

        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("Scaling");
        JMenu menu2 = new JMenu("Quantization");
        JMenu menu3 = new JMenu("视图");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        JMenuItem item1 = new JMenuItem("192*128");
        JMenuItem item2 = new JMenuItem("96*64");
        JMenuItem item3 = new JMenuItem("48*32");
        JMenuItem item4 = new JMenuItem("24*16");
        JMenuItem item5 = new JMenuItem("12*8");  
        JMenuItem item6 = new JMenuItem("300*200");  
        JMenuItem item7 = new JMenuItem("450*300");  
        JMenuItem item8 = new JMenuItem("500*200");
        JMenuItem qitem1 = new JMenuItem("128");
        JMenuItem qitem2 = new JMenuItem("32");
        JMenuItem qitem3 = new JMenuItem("8");
        JMenuItem qitem4 = new JMenuItem("4");
        JMenuItem qitem5 = new JMenuItem("2");
        
        item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(192,128);
			}
        });
        
        
        item2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(96,64);
			}
        });
        item3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(48,32);
			}
        });
        item4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(24,16);
			}
        });
        item5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(12,8);
			}
        });
        item6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(300,200);
			}
        });
        item7.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(450,300);
			}
        });
        item8.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					scale(500,200);
			}
        });
        qitem1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					quantize(2);
			}
        });
        qitem2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					quantize(8);
			}
        });
        qitem3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					quantize(36);
			}
        });
        qitem4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					quantize(85);
			}
        });
        qitem5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					quantize(255);
			}
        });
        
        
        menu1.add(item1);
        menu1.add(item2);
        menu1.add(item3);
        menu1.add(item4);
        menu1.add(item5);
        menu1.addSeparator();
        menu1.add(item6);
        menu1.add(item7);
        menu1.addSeparator();
        menu1.add(item8);
        
        
        menu2.add(qitem1);
        menu2.add(qitem2);
        menu2.add(qitem3);
        menu2.add(qitem4);
        menu2.add(qitem5);

        
        MyPanel panel = new MyPanel();  
        java.awt.Dimension di = new java.awt.Dimension(width, height);//设置panel大小  
        panel.setPreferredSize(di);  
        this.add(panel);//窗体添加panel  
        
        this.setVisible(true);//使窗体可见。  
        
    }  
    
    public void scale(int a, int b) {
    	wid = a;
   	    hei = b;
    	JFrame jFrame = new JFrame("beyole");
        jFrame.setSize((int) (wid*1.1), (int) (hei+60));
        jFrame.setDefaultCloseOperation(jFrame.HIDE_ON_CLOSE);

        RePanel panel = new RePanel();  
        java.awt.Dimension di = new java.awt.Dimension(width, height);//设置panel大小  
        panel.setPreferredSize(di);  
        
        jFrame.add(panel);//窗体添加panel  
        jFrame.setVisible(true);
    }
    
    
    public void quantize(int a) {
    	level = a;
    	JFrame jFrame = new JFrame("beyole");
    	jFrame.setSize(width+10, height+60);
        jFrame.setDefaultCloseOperation(jFrame.HIDE_ON_CLOSE);

        NePanel panel = new NePanel();  
        java.awt.Dimension di = new java.awt.Dimension(width, height);//设置panel大小  
        panel.setPreferredSize(di);  
        
        jFrame.add(panel);//窗体添加panel  
        jFrame.setVisible(true);
    }
    
  
    public class NePanel extends javax.swing.JPanel {
        private static final long serialVersionUID = 1L;  
        public void paint(Graphics g) {  
            int [][] redx = new int [height][width];
            int [][] greenx = new int [height][width];
            int [][] bluex = new int [height][width];
            super.paint(g);  
            for (int i = 0; i < height; i++) {  
                for (int j = 0; j < width; j++){
                	redx[i][j] = (int)((red[i][j]+0.0)/level + 0.5)*level;
                	greenx[i][j] = (int)((green[i][j]+0.0)/level + 0.5)*level;
                	bluex[i][j] = (int)((blue[i][j]+0.0)/level + 0.5)*level;
                }
                }
            for (int i = 0; i < height; i++) {  
                for (int j = 0; j < width; j++) {  
                	if (redx[i][j] > 255) {
                		redx[i][j] = 255;
                	}
                	if (greenx[i][j] > 255) {
                		greenx[i][j] = 255;
                	}
                	if (bluex[i][j] > 255) {
                		bluex[i][j] = 255;
                	}
                    g.setColor(new Color(redx[i][j], greenx[i][j], bluex[i][j]));  
                    g.fillRect(j, i, 1, 1);// 这里可以使用画点的任何方法，除了上面那种特例。  
                }  
            } 

        }  
    }
    
    
    public class MyPanel extends javax.swing.JPanel {  
        /** 
         *  
         */  
        private static final long serialVersionUID = 1L;  
  
        /** 
         * 重写paint方法 
         */  
        
        
        
        
        public void paint(Graphics g) {  
            super.paint(g);  
            for (int i = 0; i < height; i++) {  
                for (int j = 0; j < width; j++) {  
                	if (red[i][j] > 255) {
                		red[i][j] = 255;
                		green[i][j] = 255;
                		blue[i][j] = 255;
                	}
                	if (red[i][j] < 0) {
                		red[i][j] = 0;
                		green[i][j] = 0;
                		blue[i][j] = 0;
                	}
                	
                    g.setColor(new Color(red[i][j], green[i][j], blue[i][j]));  
                    // g.fillOval(j, i, 1, 1);  
                    g.fillRect(j, i, 1, 1);// 这里可以使用画点的任何方法，除了上面那种特例。  
                }  
            }
        }

        }

	
    
    public class RePanel extends javax.swing.JPanel {  
        private static final long serialVersionUID = 1L;  
        @SuppressWarnings("null")
		public void paint(Graphics g) {  
            int [][] redx = new int [hei][wid];
            int [][] greenx = new int [hei][wid];
            int [][] bluex = new int [hei][wid];
            super.paint(g); 
            for (int i = 0; i < hei; i++) {  
                for (int j = 0; j < wid; j++) {  

                	redx[i][j] = red[(int)(height*i/hei)][(int)(width*j/wid)];
                	greenx[i][j] = green[(int)(height*i/hei)][(int)(width*j/wid)];
                	bluex[i][j] = blue[(int)(height*i/hei)][(int)(width*j/wid)];
                }
            }  
            for (int i = 0; i < hei; i++) {  
                for (int j = 0; j < wid; j++) {  

					g.setColor(new Color(redx[i][j],greenx[i][j],bluex[i][j]));  
                    g.fillRect(j, i, 1, 1);
                }  
            }
        }  
    }
}  