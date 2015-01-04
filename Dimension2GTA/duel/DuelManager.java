package duel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.event.*;

import weapon.*;

import basicConstruction.*;
import gameDisplayProcessor.*;

import utilities.*;

import java.io.*;
import java.util.*;

import subGame.dancingFight.*;
import subGame.longFan.*;
import subGame.westGun.*;


import subGameByMapChanging.gunFight.*;
import subGameByMapChanging.hegrenadeFight.*;
import subGameByMapChanging.rocketFight.*;
import subGameByMapChanging.fistsFight.*;
import nameConstants.*;

public class DuelManager extends JFrame 
implements ActionListener,ListSelectionListener
{
	
	private Player player;
	private Human aimHuman;
	private MainGameWindow master;
	
	private JList list;
	private JButton OKButton=new JButton("OK");
	private JButton CancleButton=new JButton("Cancle");
	
	private ImageViewer imageViewer=new ImageViewer();
	private JTextArea infoArea=new JTextArea();
	
	private JMenuBar menuBar=new JMenuBar();
		
	private JMenu menu1=new JMenu("File");
	private JMenuItem menuStart=new JMenuItem("Start");
	private JMenuItem menuExit=new JMenuItem("Exit");
	
	private JMenu menu2=new JMenu("About");
	private JMenuItem menuHelp=new JMenuItem("Help");
	private JMenuItem menuAbout=new JMenuItem("About");
	
	
	private ImageIcon[] imageIcons={
			new ImageIcon("pic/showPic/westGun.jpg"),
			new ImageIcon("pic/showPic/danceFight.jpg"),
			new ImageIcon("pic/showPic/longFan.jpg"),
			new ImageIcon("pic/showPic/gunFight.jpg"),
			new ImageIcon("pic/showPic/hegrenadeFight.jpg"),
	        new ImageIcon("pic/showPic/rocketFight.jpg"),
	        new ImageIcon("pic/showPic/fistsFight.jpg"),
	};
	
	private String[] infos=new String[imageIcons.length];
	private String[] names={
			"West Gun",
			"Dancing Fight",
			"Long Fan",
			"Cheap Gun Fight",  //3
			"Hegrenade Fight",
			"Rocket Fight",
			
			"AK47 Fight",    //6
			"AUG Fight",
			"AWP Fight",
			"Desert Eagle Fight",
			"M249 Fight",
			"M4 Fight",
			"MP5 Fight",
			"SIG552 Fight",    //13
			
			"Fists Fight",
			
	};
	
	public DuelManager(Player player,Human aimHuman,MainGameWindow master)
	{
		this.player=player;
		this.aimHuman=aimHuman;
	    this.master=master;
	    
	    master.getPlayer().setUseRequested(false);
		master.getPlayer().setMoveUpRequested(false);
		master.getPlayer().setMoveDownRequested(false);
		master.getPlayer().setMoveLeftRequested(false);
		master.getPlayer().setMoveRightRequested(false);
	    
		setLayout(new BorderLayout());
		
		//初始化infos
		try
		{
			initInfos("westGun",0);
			initInfos("danceFight",1);
			initInfos("longFan",2);
			initInfos("gunFight",3);
			initInfos("hegrenadeFight",4);
				
		}
		catch(Exception ex)
		{
			;
		}
		
		//初始化list
		list=new JList(names);
		list.setBackground(new Color(185,219,247));
		list.setSelectionBackground(Color.BLUE);
		list.setSelectionForeground(Color.WHITE);
		
			
		//初始化文本区
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		infoArea.setFont(new Font("Times",Font.BOLD,12));
		infoArea.setText("    Please select a mode to fight a duel.");
		
		infoArea.setRows(5);
		infoArea.setColumns(10);
		infoArea.setEditable(false);
		
		
		//初始化imageViewer
		imageViewer.setPreferredSize(new Dimension(260,160));
		imageViewer.setBorder(new LineBorder(Color.BLUE,2));
		//加入组件
		JPanel p1=new JPanel(new BorderLayout());
		p1.add(new JScrollPane(list),BorderLayout.CENTER);
		JPanel p11=new JPanel(new BorderLayout());
		p11.add(OKButton,BorderLayout.NORTH);
		p11.add(CancleButton,BorderLayout.SOUTH);
	
		p1.add(p11,BorderLayout.SOUTH);
		getContentPane().add(p1,BorderLayout.WEST);
		
		JPanel p2=new JPanel(new BorderLayout());
		p2.add(imageViewer,BorderLayout.NORTH);
		p2.add(new JScrollPane(infoArea),BorderLayout.CENTER);
	    getContentPane().add(p2,BorderLayout.CENTER);
	    
	    //设置菜单栏
	    setJMenuBar(menuBar);
	    menuBar.add(menu1);
	    menuBar.add(menu2);
	    menu1.add(menuStart);
	    menu1.add(menuExit);
	    menu2.add(menuHelp);
	    menu2.add(menuAbout);
	    
	    //addListeners
	    list.addListSelectionListener(this);
	    OKButton.addActionListener(this);
	    CancleButton.addActionListener(this);
	    
	    menuStart.addActionListener(this);
	    menuExit.addActionListener(this);
	    menuHelp.addActionListener(this);
	    menuAbout.addActionListener(this);
	    
	    setTitle("Select Duel Mode");
	    
	    setSize(400,300);
	    setResizable(false);
	    
	    Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((screenSize.width-getWidth())/2,(screenSize.height-getHeight())/2-50);
		
	    
	    setVisible(true);
	}
	
	private void initInfos(String name,int i)
	throws Exception
	{
		infos[i]="";
		File textFile=new File("pic/showPic/"+name+".txt");
		Scanner input=new Scanner(textFile);
		
		while(input.hasNext())
		{
			infos[i]+=input.nextLine();
		}
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if((e.getSource()==OKButton)
			||(e.getSource()==menuStart))
		{
			int index=list.getSelectedIndex();
			if((index<=names.length)&&(index>=0))
			{
				if((player!=null)&&(aimHuman!=null)&&(master!=null))
				{
					setVisible(false);
					
					switch(index)
					{
					    case 0:
					    {
						    WestGun westGun=new WestGun(player,aimHuman,master);
					        break;
					    }
					    case 1:
					    {
						    DancingFight danceFight=new DancingFight(player,aimHuman,master);
					        break;
					    }
					    case 2:
					    {
						    LongFan longFan=new LongFan(player,aimHuman,master);
					        break;
					    }
					    case 3:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 4:
					    {
						    HegrenadeFight hegrenadeFight=new HegrenadeFight(player,aimHuman,master);
						    player.setSubGameByMapChanging(hegrenadeFight);
						    break;
					    }
					    case 5:
					    {
					    	 RocketFight rocketFight=new RocketFight(player,aimHuman,master);
							 player.setSubGameByMapChanging(rocketFight);
							 break;
					    }
					    
					    
					    case 6:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_AK47);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 7:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_AUG);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 8:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_AWP);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 9:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_DESERT_EAGLE);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 10:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_M249);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 11:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_M4);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 12:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_MP5);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 13:
					    {
						    GunDuel gunFight=new GunDuel(player,aimHuman,master,NameConstants.WEAPON_GUN_SIG552);
					        player.setSubGameByMapChanging(gunFight);
						    break;
					    }
					    case 14:
					    {
					    	FistsFight fistsFight=new FistsFight(player,aimHuman,master);
					        player.setSubGameByMapChanging(fistsFight);
						    break;
					    }
					    
					    
					    default :break;
					
					}
					
					
				}
			}
		
			
		}
		if((e.getSource()==CancleButton)
			||(e.getSource()==menuExit))
		{
			dispose();
		}
		
		if(e.getSource()==menuHelp)
		{
			JOptionPane.showMessageDialog(null,"  选择一种决斗方式，按OK按钮后就可以按照提示\n" +
					                           "进入游戏。部分决斗游戏允许您设定相关参数，以便\n"+
					                           "使难度事宜。但是难度越大，奖励越高。\n"+
					                           "个别电脑很难对付，祝好运～");
		}
		if(e.getSource()==menuAbout)
		{
			JOptionPane.showMessageDialog(null,"  Dimension2GTA 决斗小游戏 By ZYL");
		}
		
	}
	
	 
	public void valueChanged(ListSelectionEvent e) 
	{
		int index=list.getSelectedIndex();
		if((index<=5)&&(index>=0))
		{
			imageViewer.setImage(imageIcons[index]);
			infoArea.setText(infos[index]);
		}
		else
		{
			if((index>=6)&&(index<=13))
			{
				imageViewer.setImage(imageIcons[3]);
				infoArea.setText(infos[3]);
			}
			else
			{
				if(14==index)
				{
					imageViewer.setImage(imageIcons[6]);
					infoArea.setText("==Fists Fight==\n    Fighters can use fists only.");
				}
			}
		}
		
		
	}	
	
    
	

}
