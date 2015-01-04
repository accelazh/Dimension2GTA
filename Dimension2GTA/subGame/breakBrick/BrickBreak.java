package subGame.breakBrick;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

import subGame.breakBrick.*;
import subGame.breakBrick.gamePanel.*;
import subGame.breakBrick.mapEdition.*;
import subGame.breakBrick.welcomePanel.*;
import java.applet.*;

public class BrickBreak extends JFrame implements Constants
{
	private WallPanel wallPanel;
	private WelcomePanel welcomePanel;
	private MapEditorPanel mapEditorPanel;

	private PanelConductor panelConductor=new PanelConductor();
	
	private AudioClip gameUp=Applet.newAudioClip(BrickBreak.class.getResource("gamestartup.mp3"));
	
	private JFileChooser fileChooser=new JFileChooser();
	
	public BrickBreak()
	{
		this.setTitle("Brick Break Verion 1.0");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		setFocusable(false);
		
		fileChooser.setFileFilter(new BrickTypesFileFilter());
	    fileChooser.setAcceptAllFileFilterUsed(false);
	    		
		setResizable(false);
		gameUp.play();
		
	}
	
	public void initialize()
	{
		switchToWelcomePanel();
	}
	
	public void switchToWelcomePanel()
	{
		welcomePanel=new WelcomePanel();
		welcomePanel.setPanelConductor(panelConductor);
		
		setJMenuBar(null);
		mapEditorPanel=null;
		wallPanel=null;
		
		this.getContentPane().removeAll();
		this.getContentPane().add(welcomePanel);
		welcomePanel.requestFocusInWindow();
		this.setSize(GUI_SIZE.width+6,GUI_SIZE.height+32);
		
		this.setVisible(true);
	}
	
	public void switchToMapEditorPanel()
	{
		
		mapEditorPanel=new MapEditorPanel();
		mapEditorPanel.setPanelConductor(panelConductor);
		
		welcomePanel=null;
		wallPanel=null;
		
		this.getContentPane().removeAll();
		this.getContentPane().add(mapEditorPanel);
		this.setJMenuBar(mapEditorPanel.getEditorMenuBar());
		this.setSize(GUI_SIZE.width+6,GUI_SIZE.height+32+25);
		
		this.setVisible(true);
		
	}
	
	public void switchToGamePanel(File mapFile)
	{
		BrickTypesFileFilter filter=new BrickTypesFileFilter();
		if(filter.acceptAccurately(mapFile))
		{
			
			wallPanel=new WallPanel();
			wallPanel.setPanelConductor(panelConductor);
			
			setJMenuBar(null);
			welcomePanel=null;
			mapEditorPanel=null;
			
			this.getContentPane().removeAll();
			this.getContentPane().add(wallPanel);
			wallPanel.initialize();
			wallPanel.initializeBricks(mapFile);
			this.setSize(GUI_SIZE.width+6,GUI_SIZE.height+32);
			
			this.setVisible(true);
			
		}
	}
	
	//这个内部类负责各个版面之间的调度
	public class PanelConductor
	{
		//表明用户现在所在的位置
		public static final int WELCOME_PANEL=0;
		private int currentMap=0;
		public static final int NEW_GAME=1;
		public static final int PLAY_MAP=2;
		public static final int CREATE_MAP=3;
		public static final int EXIT=4;
		
		private int currentState=WELCOME_PANEL;
		
		//for welcomePanel
		public void receiveNewGameMessageFormWelcomePanel()
		{
			currentMap=0;
			File mapFile=new File("Dimension2GTA\\subGame\\breakBrick\\maps\\"+currentMap+".brickTypes");
			if(mapFile.exists())
			{
				currentState=NEW_GAME;
				switchToGamePanel(mapFile);
			}
			else
			{
				JOptionPane.showMessageDialog(BrickBreak.this,
						" Error:\nMap File Lost!",
						"Error",JOptionPane.ERROR_MESSAGE);
				switchToWelcomePanel();
			}
		}
		
		public void receivePlayMapMessageFormWelcomePanel()
		{
			File gotFile=chooseFile();
			if(gotFile!=null)
			{
				currentState=PLAY_MAP;
				switchToGamePanel(gotFile);
			}
			else
			{
				switchToWelcomePanel();
			}
		}
		
		public void receiveCreateMapMessageFormWelcomePanel()
		{
			currentState=CREATE_MAP;
			switchToMapEditorPanel();
		}
		
		public void receiveExitMessageFormWelcomePanel()
		{
			if(0==JOptionPane.showConfirmDialog(BrickBreak.this,new JLabel("Sure to Quit?")))
			{
				BrickBreak.this.setVisible(false);
				currentState=EXIT;
			}
			else
			{
				switchToWelcomePanel();
			}
		}
		
		//for wallPanel
		public void receiveMapPassedMessageFromWallPanel()
		{
			if (NEW_GAME == currentState) 
			{
				currentMap++;
				File mapFile = new File(
						"Dimension2GTA\\subGame\\breakBrick\\maps\\"
								+ currentMap + ".brickTypes");
				if (mapFile.exists())
				{
					wallPanel.initialize();
					wallPanel.initializeBricks(mapFile);
				} 
				else 
				{
					JOptionPane
							.showMessageDialog(
									BrickBreak.this,
									"    Congratulations!!\nYou have passed all maps!\nNow start from the first map",
									"CONGRATULATION",
									JOptionPane.INFORMATION_MESSAGE);

					currentMap=0;
					File mapFileZero=new File("Dimension2GTA\\subGame\\breakBrick\\maps\\"+currentMap+".brickTypes");
					if(mapFileZero.exists())
					{
						wallPanel.initialize();
						wallPanel.initializeBricks(mapFileZero);
					}
					else
					{
						JOptionPane.showMessageDialog(BrickBreak.this,
								" Error:\nMap File Lost!",
								"Error",JOptionPane.ERROR_MESSAGE);
						
						currentState=WELCOME_PANEL;
						switchToWelcomePanel();
					}
				
				}
			} 
			else 
			{
				currentState = WELCOME_PANEL;
				switchToWelcomePanel();
			}
			
		}
		
		public void receiveGameOverMessageFromWallPanel()
		{
			currentState=WELCOME_PANEL;
			switchToWelcomePanel();
		}
		
		//for mapEditorPanel
		public void receiveExitMessageFromMapEditorMenuBar()
		{
			currentState=WELCOME_PANEL;
			switchToWelcomePanel();
		}
		
		private File chooseFile()
		{
			BrickTypesFileFilter filter=new BrickTypesFileFilter();

			File gotFile=fileChooser.showOpenDialog(BrickBreak.this)==JFileChooser.APPROVE_OPTION?fileChooser.getSelectedFile():null;
				
				if(gotFile!=null)
				{
					if(filter.acceptAccurately(gotFile))
					{
						return gotFile;
					}
				}
				
				return null;
		}
		
	}
	
	
	//test
	
	public static void main(String[] args)
	{
		BrickBreak frame=new BrickBreak();
		frame.initialize();
	}
}
