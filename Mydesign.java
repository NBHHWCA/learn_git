import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.*;
//import java.io.*;
class Mydesign extends WindowAdapter implements ActionListener,MouseListener,MouseMotionListener
{
	Frame f;
	MenuBar mb;            //菜单栏
	Menu mf,me,mh;         //菜单 
	CheckboxMenuItem cbm;  //带复选框菜单项
	PopupMenu pm;          //弹出式菜单
	Dialog d;              //对话框
	Label l;               //对话框上的标签
	FileDialog openDia, saveDia;
	File file;
	String dirpath, fileName;
	TextArea tf;
	JPanel pa;
	int x = 0,y = 0;
	public void display(){  
		f = new Frame("Menu test");
		f.setSize(400,300);
		f.setLocation(400,200);
		f.setBackground(Color.lightGray);
		f.addWindowListener(this);
		f.addMouseListener(this);        //为框架f注册鼠标事件监听程序
		
		f.setVisible(true);
		setMenu();	  
		setPopupMenu();
		showDialog();
	}   
	public void setPopupMenu(){       //设置弹出式菜单
		pm = new PopupMenu("Popup");    //生成一个弹出式菜单对象
		pm.add(new MenuItem("Cut"));    //加入菜单项
		pm.add(new MenuItem("Copy"));
		pm.add(new MenuItem("Paste"));
		pm.addSeparator();              //加分隔线
		pm.add(new MenuItem("Open"));
		pm.add(new MenuItem("Exit"));
		pm.addActionListener(this);     //为菜单注册事件监听程序
		f.add(pm);                      //框架f上添加弹出式菜单	
	}
	public void showDialog(){         //显示对话框
		d = new Dialog(f,"关于",true);
		l=new Label("一个模式对话框");
		d.add(l,"Center");
		d.setSize(200,160);
		d.setLocation(400,270);
		d.addWindowListener(this);      //为对话框d注册事件监听程序     
	}  
	public void setMenu(){            //设置窗口菜单
		mb = new MenuBar();             //生成一个菜单栏
		f.setMenuBar(mb);               //框架f上添加菜单栏
		mf = new Menu("File");          //生成一个菜单
		me = new Menu("Edit");
		mh = new Menu("Help");
		mb.add(mf);                      //菜单栏中加入菜单        
		mb.add(me);
		mb.add(mh);
		//mf.add(new MenuItem("Open Dialog")); //生成菜单项并加入到菜单
		mf.add(new MenuItem("Save",new MenuShortcut(KeyEvent.VK_S)));
		mf.add(new MenuItem("Open",new MenuShortcut(KeyEvent.VK_O)));
		mf.addSeparator();                  //加分隔线       
		//mf.add(me);                         //菜单加入到菜单成为二级菜单
		//cbm = new CheckboxMenuItem("Delet",true);
		//mf.add(cbm);
		mf.add(new MenuItem("Exit"));
		mf.addActionListener(this);         //为菜单注册事件监听程序
		me.add(new MenuItem("Paint"));
		me.add(new MenuItem("Text"));
		me.addActionListener(this);
		mh.add(new MenuItem("About")); 
		mh.addActionListener(this);
	}
	public void windowClosing(WindowEvent e){
		if(e.getSource()==d)               //如果单击的对话框的关闭按钮，隐藏它
			d.setVisible(false);
		else
			System.exit(0);                  //单击的是窗口f的关闭按钮，退出应用程序
	}
	public void actionPerformed(ActionEvent e){ //选择菜单项时触发ActionEvent事件                                      
		String s=e.getActionCommand();            //获取所选菜单项的标签名
		if((s.equals("Open"))) {
			openDia = new FileDialog(f, "打开", FileDialog.LOAD);                     //显示对话框
			openDia.setVisible(true);//显示打开文件对话框
			dirpath = openDia.getDirectory();//获取打开文件路径并保存到字符串中。
			fileName = openDia.getFile();//获取打开文件名称并保存到字符串中
			if (dirpath == null || fileName == null)//判断路径和文件是否为空
				return;
			else
				tf.setText(null);//文件不为空，清空原来文件内容。
			file = new File(dirpath, fileName);//创建新的路径和名称
			try {
				BufferedReader bufr = new BufferedReader(new FileReader(file));//尝试从文件中读东西
				String line = null;//变量字符串初始化为空
				while ((line = bufr.readLine()) != null) {
					tf.append(line + "\r\n");//显示每一行内容
				}
				bufr.close();//关闭文件
			} catch (FileNotFoundException e1) {
				// 抛出文件路径找不到异常
				e1.printStackTrace();
			} catch (IOException e1) {
				// 抛出IO异常
				e1.printStackTrace();
			}
		}
		if(s.equals("Exit"))
			System.exit(0);
		if(s.equals("About")){                           //选择Help菜单的About项触发
			l.setText("2019112293\r\n高志成\r\n人工智能\r\n智能(茅班)2019-01班");	
			d.setVisible(true);                           //显示对话框	
		}    
		if(s.equals("Save")){
			saveDia = new FileDialog(f, "保存", FileDialog.SAVE);
			if (file == null) {
				saveDia.setVisible(true);//显示保存文件对话框
				dirpath = saveDia.getDirectory();//获取保存文件路径并保存到字符串中。
				fileName = saveDia.getFile();////获取打保存文件名称并保存到字符串中
				
				if (dirpath == null || fileName == null)//判断路径和文件是否为空
					return;//空操作
				else
					file=new File(dirpath,fileName);//文件不为空，新建一个路径和名称
			}
			try {
				BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
				String text = tf.getText();//获取文本内容
				bufw.write(text);//将获取文本内容写入到字符输出流
				bufw.close();//关闭文件
			} catch (IOException e1) {
				//抛出IO异常
				e1.printStackTrace();
			}
        }

		if(s.equals("Text")){
			tf = new TextArea();
			f.add(tf);
			f.setVisible(false);
			f.setVisible(true);
		}
		if(s.equals("Paint")){
			
			pa = new JPanel();
			pa.addMouseListener(this);
  			pa.addMouseMotionListener(this);
			f.add(pa);
			f.setVisible(false);
			f.setVisible(true);
		}
	}
	public void mouseClicked(MouseEvent mec){   //单击鼠标时触发
		if (/*mec.isPopupTrigger()*/mec.getButton() == mec.BUTTON3)   //单击的是鼠标右键
			pm.show(f,mec.getX(),mec.getY());       //在鼠标单击处显示菜单
	//JOptionPane.showMessageDialog(null, "hello");
	}
	public void mousePressed(MouseEvent mep)    {  
		x = mep.getX();
		y = mep.getY();
	}
	public void mouseReleased(MouseEvent mer)   {    }
	public void mouseEntered(MouseEvent mee)    {    } 
	public void mouseExited(MouseEvent mex)     {    }
	public void mouseMoved(MouseEvent mmo)		{	 }
	public void mouseDragged(MouseEvent med)    {  
		int newX = med.getX();     
		int newY = med.getY();
		if(pa == null) return;
		Graphics g = pa.getGraphics();  //获取画笔
		g.setColor(Color.BLUE);      //设置画笔颜色
		g.drawLine(x,y,newX,newY);   //两点之间划线
		x=newX;     y=newY;
	}
	public static void main(String[] args) 
	{
		(new Mydesign()).display();
	}
}
