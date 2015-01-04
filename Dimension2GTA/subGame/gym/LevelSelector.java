package subGame.gym;

import animatedGUI.*;
import gameDisplayProcessor.*;

import javax.swing.*;

import utilities.MyPoint;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import subGame.breakBrick.*;
import basicConstruction.*;

import java.io.*;
import javax.swing.border.*;
import utilities.*;
import subGameSuper.*;

/**
 * 
 * 这个类用来生成弹出的选择level的面板
 */
public class LevelSelector extends ATopContainer
implements GymConstants,AActionListener
{
	//for debug
	private static final boolean debug=true;
	
	//面板大小
	public static final Dimension DEFAULT_SIZE=new Dimension(350,600);
	
	//两个主按钮Level和Leave的位置，
	private Point[] levelLocations={
			new Point(77,210),
			new Point(78,51),
			
	};
	private Point[] leaveLocations={
			new Point(77,290),
			new Point(78,511),
	};
	
	private Point[][] mainButtonLocations={
			levelLocations,
			leaveLocations,
	};
	
	//十个等级选择按钮的位置,从上到下
	private Point[] levelsLocations={
			new Point(62,110),
			new Point(62,150),
			new Point(62,150+40),
			new Point(62,150+2*40),
			new Point(62,150+3*40),
			new Point(62,150+4*40),
			new Point(62,150+5*40),
			new Point(62,150+6*40),
			new Point(62,150+7*40),
			new Point(62,150+8*40),
	};
	
	//panel的位置
	private Point panelLocation=new Point(0,0);
	
	//panel的图片
	private ImageIcon panelIcon=new ImageIcon("pic\\SubGame\\gym\\GUI\\popPanel.png");
	
	//标记面板状态，是否已经展开
	private boolean folded=false;
   
	//组件
	private AButton level;
	private AButton leave;
	private AButton[] levels=new AButton[10];
	
	private APanel panel;
	
	//动画
	private AAnimationClip animationClipOfLevel;
	private AAnimationClip animationClipOfLeave;
	
	//图片
	private ImageIcon[] levelIcons={
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel.png"),
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel_down.png"),
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel_on.png"),
	};
	
	private ImageIcon[] leaveIcons={
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLeave.png"),
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLeave_down.png"),
			new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLeave_on.png"),
	};
	
	private ImageIcon[][] mainButtonIcons={
			levelIcons,
			leaveIcons,
	};
	
	private ImageIcon[][] levelsIcons={
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel1.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel1_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel1_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel2.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel2_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel2_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel3.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel3_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel3_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel4.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel4_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel4_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel5.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel5_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel5_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel6.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel6_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel6_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel7.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel7_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel7_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel8.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel8_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel8_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel9.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel9_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel9_on.png"),
			},
			new ImageIcon[]{
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel10.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel10_down.png"),
					new ImageIcon("pic\\SubGame\\gym\\GUI\\buttonLevel10_on.png"),
			},
	};
	
	//音效
	private URL clickUrl=LevelSelector.class.getResource("buttonClick.wav");
	private URL onUrl=LevelSelector.class.getResource("buttonMouseOn.wav");
	private URL releaseUrl=LevelSelector.class.getResource("buttonReleased.wav");
	private SoundPlayer doorClip=new SoundPlayer(LevelSelector.class.getResource("door.wav"),2);
	
	//GymSuper的引用
	private GymSuper gymSuper;
	
	
	//方法
	
	public LevelSelector(GymSuper gymSuper)
	{
		this.gymSuper=gymSuper;
		
		//初始化自己 
		this.setPreferredSize(DEFAULT_SIZE);
		this.setSize(DEFAULT_SIZE);
		this.setLayout(null);
		this.setVisible(false);
		this.setOpaque(false);
		
		this.setLocation((GymSuper.PANEL_SIZE.width-DEFAULT_SIZE.width)/2,
				(GymSuper.PANEL_SIZE.height-DEFAULT_SIZE.height)/2);
		//初始化动画插件
		animationClipOfLevel=new AAnimationClip(levelLocations[0],levelLocations[0].y-levelLocations[1].y,(int)(0.5*1000/AAnimationClip.getFrameInterval()), AAnimationClip.SIN_MOTION,AAnimationClip.UP);
		animationClipOfLevel=animationClipOfLevel.reverse();
		animationClipOfLeave=new AAnimationClip(leaveLocations[0],leaveLocations[1].y-leaveLocations[0].y,(int)(0.5*1000/AAnimationClip.getFrameInterval()), AAnimationClip.SIN_MOTION,AAnimationClip.DOWN);
	    animationClipOfLeave=animationClipOfLeave.reverse();
		
		//初始化level和leave
		level=new AButton(levelIcons[0],levelIcons[1],levelIcons[2],levelLocations[0]);
		level.setAnimationClip(animationClipOfLevel);
		level.setMode(AButton.SHOW);
		level.addAActionListener(this);
		level.setMouseClickClip(clickUrl);
		level.setMouseOnClip(onUrl);
		level.setMouseReleasedClip(releaseUrl);
		
		leave=new AButton(leaveIcons[0],leaveIcons[1],leaveIcons[2],leaveLocations[0]);
		leave.setAnimationClip(animationClipOfLeave);
		leave.setMode(AButton.SHOW);
		leave.addAActionListener(this);
		leave.setMouseClickClip(clickUrl);
		leave.setMouseOnClip(onUrl);
		leave.setMouseReleasedClip(releaseUrl);
		
		//初始化levels
		for(int i=0;i<levels.length;i++)
		{
			levels[i]=new AButton(levelsIcons[i][0],levelsIcons[i][1],levelsIcons[i][2],levelsLocations[i]);
		    levels[i].setVisible(false);
		    levels[i].addAActionListener(this);
		    
		    levels[i].setMouseClickClip(clickUrl);
			levels[i].setMouseOnClip(onUrl);
			levels[i].setMouseReleasedClip(releaseUrl);
		}
		
		//初始化panel
		panel=new APanel(panelIcon,panelLocation);
		
		//加入组件
		add(panel);
		for(int i=0;i<levels.length;i++)
	    {
	       	panel.add(levels[i]);
	    }
        panel.add(level);
        panel.add(leave);
       
	}

	 
	public void actionPerformed(AActionEvent e) 
	{
		if(e.getSource()==level)
		{
			if(debug)
			{
				System.out.println("button level pressed");
			}
			
			if(level.getPopState()==AButton.PREPARED
					&&leave.getPopState()==AButton.PREPARED)
			{
				animationClipOfLevel=animationClipOfLevel.reverse();
				animationClipOfLeave=animationClipOfLeave.reverse();
				level.setAnimationClip(animationClipOfLevel);
				leave.setAnimationClip(animationClipOfLeave);
				level.setMode(AButton.POP_AND_SHOW);
				leave.setMode(AButton.POP_AND_SHOW);
				folded=!folded;
				doorClip.play();
				
				if(!folded)
				{
					for(int i=0;i<levels.length;i++)
					{
						levels[i].setVisible(false);
					}
				}
			}
		}
		
		if(e.getSource()==leave)
		{
			if(debug)
			{
				System.out.println("button leave pressed");
			}
			
			stopTimer();
			this.setVisible(false);
			if(gymSuper!=null)
			{
				gymSuper.winByPlayer();
				gymSuper.endOfGame();
			}
		}
		
		for(int i=0;i<levels.length;i++)
		{
			if(e.getSource()==levels[i])
			{
				if(debug)
				{
					System.out.println("button levels["+i+"] pressed");
				}
				
				stopTimer();
				if(gymSuper!=null)
				{
					gymSuper.setLevel(i+1);
				}
				this.setVisible(false);
				if(gymSuper!=null)
				{
					gymSuper.resume();
				}
			}
		}
	}

	 
	protected void selfProcess() 
	{
		super.selfProcess();
		
		if((folded)&&(level.getPopState()==AButton.PREPARED
				&&leave.getPopState()==AButton.PREPARED))
		{
			for(int i=0;i<levels.length;i++)
			{
				levels[i].setVisible(true);
			}
		}
	}
	
	/**
	 * 这个方法负责是自己出现，并调整状态
	 */
	public void popUp()
	{
		if(debug)
		{
			System.out.println("====  in method popUp of LevelSelector====");
		}
		
		for(int i=0;i<levels.length;i++)
		{
			levels[i].setVisible(false);
		}
		animationClipOfLevel=new AAnimationClip(levelLocations[0],levelLocations[0].y-levelLocations[1].y,(int)(0.5*1000/AAnimationClip.getFrameInterval()), AAnimationClip.SIN_MOTION,AAnimationClip.UP);
		animationClipOfLevel=animationClipOfLevel.reverse();
		animationClipOfLeave=new AAnimationClip(leaveLocations[0],leaveLocations[1].y-leaveLocations[0].y,(int)(0.5*1000/AAnimationClip.getFrameInterval()), AAnimationClip.SIN_MOTION,AAnimationClip.DOWN);
		animationClipOfLeave=animationClipOfLeave.reverse();
		level.setAnimationClip(animationClipOfLevel);
		leave.setAnimationClip(animationClipOfLeave);
		level.setMode(AButton.SHOW);
		leave.setMode(AButton.SHOW);
		folded=false;
				
		if(gymSuper!=null)
		{
			gymSuper.pause();
		}
	    this.setVisible(true);
		startTimer();
		
		if(debug)
		{
			System.out.println("====  end of method popUp of LevelSelector====");
		}
	}
	
	//test
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.getContentPane().add(new LevelSelector(null));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	
	
}
