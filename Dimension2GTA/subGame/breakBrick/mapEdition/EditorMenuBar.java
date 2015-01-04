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
	private JMenuItem exit=new JMenuItem("Exit");   //exitӦ�ûص�welcomePanel

	//menu about
	private JMenu about=new JMenu("About");
	private JMenuItem help=new JMenuItem("Help");
	private JMenuItem aboutUs=new JMenuItem("About us");
	
	//others
	private PaintPanel paintPanel;
	private File currentFile;
	private JFileChooser fileChooser=new JFileChooser();
	
	//��������������
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
	//����ֵҪô����Ϊ�û�ȡ������null��ҪôӦ����ר�������洢brickTypes����չ�����������formationָ�����ļ�
	//open==true ����ļ�,open==false��洢�ļ�
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
	
	//�洢�ļ�
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
	
	//����������������Ϣ
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
			JOptionPane.showMessageDialog(paintPanel,"    ��������м�����Ϳ��Դ���ש�飬����Ĺ���������ѡ��ש������ࡣ","Help",JOptionPane.INFORMATION_MESSAGE);
		}
		
		if(e.getSource()==aboutUs)
		{
			JOptionPane.showMessageDialog(paintPanel,"    Author: Zyl\n    ��Ȩû�У����治��","About",JOptionPane.INFORMATION_MESSAGE);
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
