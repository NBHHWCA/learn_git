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
	MenuBar mb;            //�˵���
	Menu mf,me,mh;         //�˵� 
	CheckboxMenuItem cbm;  //����ѡ��˵���
	PopupMenu pm;          //����ʽ�˵�
	Dialog d;              //�Ի���
	Label l;               //�Ի����ϵı�ǩ
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
		f.addMouseListener(this);        //Ϊ���fע������¼���������
		
		f.setVisible(true);
		setMenu();	  
		setPopupMenu();
		showDialog();
	}   
	public void setPopupMenu(){       //���õ���ʽ�˵�
		pm = new PopupMenu("Popup");    //����һ������ʽ�˵�����
		pm.add(new MenuItem("Cut"));    //����˵���
		pm.add(new MenuItem("Copy"));
		pm.add(new MenuItem("Paste"));
		pm.addSeparator();              //�ӷָ���
		pm.add(new MenuItem("Open"));
		pm.add(new MenuItem("Exit"));
		pm.addActionListener(this);     //Ϊ�˵�ע���¼���������
		f.add(pm);                      //���f����ӵ���ʽ�˵�	
	}
	public void showDialog(){         //��ʾ�Ի���
		d = new Dialog(f,"����",true);
		l=new Label("һ��ģʽ�Ի���");
		d.add(l,"Center");
		d.setSize(200,160);
		d.setLocation(400,270);
		d.addWindowListener(this);      //Ϊ�Ի���dע���¼���������     
	}  
	public void setMenu(){            //���ô��ڲ˵�
		mb = new MenuBar();             //����һ���˵���
		f.setMenuBar(mb);               //���f����Ӳ˵���
		mf = new Menu("File");          //����һ���˵�
		me = new Menu("Edit");
		mh = new Menu("Help");
		mb.add(mf);                      //�˵����м���˵�        
		mb.add(me);
		mb.add(mh);
		//mf.add(new MenuItem("Open Dialog")); //���ɲ˵�����뵽�˵�
		mf.add(new MenuItem("Save",new MenuShortcut(KeyEvent.VK_S)));
		mf.add(new MenuItem("Open",new MenuShortcut(KeyEvent.VK_O)));
		mf.addSeparator();                  //�ӷָ���       
		//mf.add(me);                         //�˵����뵽�˵���Ϊ�����˵�
		//cbm = new CheckboxMenuItem("Delet",true);
		//mf.add(cbm);
		mf.add(new MenuItem("Exit"));
		mf.addActionListener(this);         //Ϊ�˵�ע���¼���������
		me.add(new MenuItem("Paint"));
		me.add(new MenuItem("Text"));
		me.addActionListener(this);
		mh.add(new MenuItem("About")); 
		mh.addActionListener(this);
	}
	public void windowClosing(WindowEvent e){
		if(e.getSource()==d)               //��������ĶԻ���Ĺرհ�ť��������
			d.setVisible(false);
		else
			System.exit(0);                  //�������Ǵ���f�Ĺرհ�ť���˳�Ӧ�ó���
	}
	public void actionPerformed(ActionEvent e){ //ѡ��˵���ʱ����ActionEvent�¼�                                      
		String s=e.getActionCommand();            //��ȡ��ѡ�˵���ı�ǩ��
		if((s.equals("Open"))) {
			openDia = new FileDialog(f, "��", FileDialog.LOAD);                     //��ʾ�Ի���
			openDia.setVisible(true);//��ʾ���ļ��Ի���
			dirpath = openDia.getDirectory();//��ȡ���ļ�·�������浽�ַ����С�
			fileName = openDia.getFile();//��ȡ���ļ����Ʋ����浽�ַ�����
			if (dirpath == null || fileName == null)//�ж�·�����ļ��Ƿ�Ϊ��
				return;
			else
				tf.setText(null);//�ļ���Ϊ�գ����ԭ���ļ����ݡ�
			file = new File(dirpath, fileName);//�����µ�·��������
			try {
				BufferedReader bufr = new BufferedReader(new FileReader(file));//���Դ��ļ��ж�����
				String line = null;//�����ַ�����ʼ��Ϊ��
				while ((line = bufr.readLine()) != null) {
					tf.append(line + "\r\n");//��ʾÿһ������
				}
				bufr.close();//�ر��ļ�
			} catch (FileNotFoundException e1) {
				// �׳��ļ�·���Ҳ����쳣
				e1.printStackTrace();
			} catch (IOException e1) {
				// �׳�IO�쳣
				e1.printStackTrace();
			}
		}
		if(s.equals("Exit"))
			System.exit(0);
		if(s.equals("About")){                           //ѡ��Help�˵���About���
			l.setText("2019112293\r\n��־��\r\n�˹�����\r\n����(é��)2019-01��");	
			d.setVisible(true);                           //��ʾ�Ի���	
		}    
		if(s.equals("Save")){
			saveDia = new FileDialog(f, "����", FileDialog.SAVE);
			if (file == null) {
				saveDia.setVisible(true);//��ʾ�����ļ��Ի���
				dirpath = saveDia.getDirectory();//��ȡ�����ļ�·�������浽�ַ����С�
				fileName = saveDia.getFile();////��ȡ�򱣴��ļ����Ʋ����浽�ַ�����
				
				if (dirpath == null || fileName == null)//�ж�·�����ļ��Ƿ�Ϊ��
					return;//�ղ���
				else
					file=new File(dirpath,fileName);//�ļ���Ϊ�գ��½�һ��·��������
			}
			try {
				BufferedWriter bufw = new BufferedWriter(new FileWriter(file));
				String text = tf.getText();//��ȡ�ı�����
				bufw.write(text);//����ȡ�ı�����д�뵽�ַ������
				bufw.close();//�ر��ļ�
			} catch (IOException e1) {
				//�׳�IO�쳣
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
	public void mouseClicked(MouseEvent mec){   //�������ʱ����
		if (/*mec.isPopupTrigger()*/mec.getButton() == mec.BUTTON3)   //������������Ҽ�
			pm.show(f,mec.getX(),mec.getY());       //����굥������ʾ�˵�
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
		Graphics g = pa.getGraphics();  //��ȡ����
		g.setColor(Color.BLUE);      //���û�����ɫ
		g.drawLine(x,y,newX,newY);   //����֮�仮��
		x=newX;     y=newY;
	}
	public static void main(String[] args) 
	{
		(new Mydesign()).display();
	}
}
