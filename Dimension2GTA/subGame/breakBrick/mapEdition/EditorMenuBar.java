package subGame.breakBrick.mapEdition;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import subGame.breakBrick.*;

import javax.swing.border.*;
import java.io.*;

public class EditorMenuBar extends JMenuBar
implements ActionListener
{
	
	//menu file
	private JMenu file=new JMenu("File");
	private JMenuItem create=new JMenuItem("New");
	private JMenuItem open=new JMenuItem("Open");
	private JMenuItem save=new JMenuItem("Save");
	private JMenuItem saveAs=new JMenuItem("Save as");
	private JMenuItem exit=new JMenuItem("Exit");   //exit应该回到welcomePanel

	//menu about
	private JMenu about=new JMenu("About");
	private JMenuItem help=new JMenuItem("Help");
	private JMenuItem aboutUs=new JMenuItem("About us");
	
	//others
	private PaintPanel paintPanel;
	private File currentFile;
	private JFileChooser fileChooser=new JFileChooser();
	
	//面板管理器的引用
	private BrickBreak.PanelConductor panelConductor;
	
	public EditorMenuBar(PaintPanel paintPanel)
	{
		this.paintPanel=paintPanel;
		
		file.add(create);
		file.add(open);
		file.add(save);
		file.add(saveAs);
		file.add(exit);
		
		about.add(help);
    	about.add(aboutUs);
    	
    	add(file);
    	add(about);
    	
    	//add listeners
    	create.addActionListener(this);
    	open.addActionListener(this);
    	save.addActionListener(this);
    	saveAs.addActionListener(this);
    	exit.addActionListener(this);
    	
    	help.addActionListener(this);
    	aboutUs.addActionListener(this);
    	
    	//set fileChooser
    	fileChooser.setFileFilter(new BrickTypesFileFilter());
	    fileChooser.setAcceptAllFileFilterUsed(false);
	}
	
	private void setFileNameShown()
	{
		if(currentFile!=null)
		{
			paintPanel.setFileNameShown(currentFile.getName());
		}
		else
		{
			paintPanel.setFileNameShown("");
		}
	}
	
	//open and save operation use this to choose a file
	//返回值要么是因为用户取消返回null，要么应该是专门用来存储brickTypes的扩展名由数据域的formation指定的文件
	//open==true 则打开文件,open==false则存储文件
	private File chooseFile(boolean open)
	{
		BrickTypesFileFilter filter=new BrickTypesFileFilter();
		
		if(open)
		{
			File gotFile=fileChooser.showOpenDialog(paintPanel)==JFileChooser.APPROVE_OPTION?fileChooser.getSelectedFile():null;
            			
			gotFile=filter.adjustToAccuratelyAcceptedFile(gotFile);
			if(gotFile!=null)
			{
				if(filter.acceptAccurately(gotFile))
				{
					return gotFile;
				}
			}
			
			return null;
		}
		else
		{
			boolean reSelect=true;
		
			do
			{
				File gotFile = fileChooser.showSaveDialog(paintPanel) == JFileChooser.APPROVE_OPTION ? fileChooser
						.getSelectedFile()
						: null;
				if (null == gotFile) 
				{
					reSelect = false;
					break;
				} 
				
				gotFile = filter.adjustToAccuratelyAcceptedFile(gotFile);
				if (gotFile != null) 
				{
					if (filter.accept(gotFile)) 
					{
						reSelect=false;
						return gotFile;
					}
					else
					{
						JOptionPane.showMessageDialog(paintPanel,"Sorry invalid file selected!","Error",JOptionPane.ERROR_MESSAGE);
					    reSelect=true;
					}
				}
				else
				{
					JOptionPane.showMessageDialog(paintPanel,"Sorry invalid file selected!","Error",JOptionPane.ERROR_MESSAGE);
				    reSelect=true;
				}
			} while (reSelect);
			
			return null;
		}
	}
	
	private void open(File gotFile)
	{
		if((gotFile != null)&&(gotFile.exists()))
		{
			try 
			{
				ObjectInputStream in = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(
								gotFile)));
				int[][] brickTypes = (int[][]) (in.readObject());

				paintPanel.initialize(brickTypes);
				currentFile = gotFile;

				in.close();
			} 
			catch (Exception ex) 
			{
				System.out.println("Error When Open File");
				ex.printStackTrace();
			}
		}
	}
	
	//存储文件
	private void save(File gotFile)
	{
		if (gotFile != null)
		{
			try 
			{
				ObjectOutputStream out = new ObjectOutputStream(
						new BufferedOutputStream(new FileOutputStream(
								gotFile)));
				out.writeObject(paintPanel.getBrickTypes());

			    currentFile = gotFile;

				out.close();
			} 
			catch (Exception ex) 
			{
				System.out.println("Error When Save File As ...");
				ex.printStackTrace();
			}
		}
	}
	
	
	private void saveAs()
	{
		File gotFile=null;
		boolean repeat=false;
		do
		{
			gotFile=chooseFile(false);
			
			if(gotFile!=null)
			{
				if(gotFile.exists())
				{
					switch(JOptionPane.showConfirmDialog(paintPanel,new JLabel("File already exists, sure to override?")))
					{
					case 0:
					{
						repeat=false;
						break;
					}
					case 1:
					{
						repeat=true;
						break;
					}
					default:
					{
						repeat=false;
						gotFile=null;
						break;
					}
						
					}
				}
				else
				{
					repeat=false;
				}
			}
			else
			{
				repeat=false;
			}
			
		}while(repeat);
		
		save(gotFile);
	}
	
	//向面板管理器发送信息
	//============================
	private void sendExitMessage()
	{
		panelConductor.receiveExitMessageFromMapEditorMenuBar();
	}
	//============================
	
	 
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==create)
		{
			int returnVal=JOptionPane.showConfirmDialog(paintPanel,new JLabel("This will cause the current file to lost if not saved, \nsure to continue?"));
		    if(0==returnVal)
		    {
		    	paintPanel.initialize();
		    	currentFile=null;
		    }
			
		}
		
		if(e.getSource()==open)
		{
			int returnVal=JOptionPane.showConfirmDialog(paintPanel,new JLabel("This will cause the current file to lost if not saved, \nsure to continue?"));
		    if (0 == returnVal) 
		    {
				File gotFile = chooseFile(true);
				open(gotFile);
			}
		}
		
		if(e.getSource()==save)
		{
			if(currentFile!=null)
			{
				save(currentFile);
			}
			else
			{
				saveAs();
			}
		}
		
		if(e.getSource()==saveAs)
		{
			saveAs();
		}
		
		if(e.getSource()==exit)
		{
			sendExitMessage();
		}
		
		if(e.getSource()==help)
		{
			JOptionPane.showMessageDialog(paintPanel,"    用鼠标点击中间的面板就可以创建砖块，下面的工具栏可以选择砖块的种类。","Help",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource()==aboutUs)
		{
			JOptionPane.showMessageDialog(paintPanel,"    Author: Zyl\n    版权没有，盗版不究","About",JOptionPane.INFORMATION_MESSAGE);
		}
		
		setFileNameShown();
	}
	public BrickBreak.PanelConductor getPanelConductor() {
	    return panelConductor;
	}

	public void setPanelConductor(BrickBreak.PanelConductor panelConductor) {
		this.panelConductor = panelConductor;
	}
	
	//test
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		ToolPanel toolPanel=new ToolPanel();
		PaintPanel paintPanel=new PaintPanel(toolPanel);
		EditorMenuBar editorMenuBar=new EditorMenuBar(paintPanel);
		
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(paintPanel,BorderLayout.CENTER);
		frame.getContentPane().add(toolPanel,BorderLayout.SOUTH);
		frame.setJMenuBar(editorMenuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);	
	}

}
