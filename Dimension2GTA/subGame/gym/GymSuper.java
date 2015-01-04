package subGame.gym;

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
 * 这个类用作其他健身器材subGame的模板，
 * 具体的健身器材用它的子类
 * 
 */
public abstract class GymSuper extends SubGame
implements GymConstants,ActionListener,KeyListener
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=true;

	/**
	 * 健身器材的种类
	 */
	private int type=-1;
	/**
	 * 健身房动画图片组,第一张必须是未负重状态的图片
	 */
	private Image[] imageSequence;
	/**
	 * 用于定位imageSequence
	 */
	private int imageSequencePointer=0;
	/**
	 * 锻炼力度指示，就是屏幕左上角那个小条
	 */
	private double degree=0;
	/**
	 * 锻炼量，用以自动换取技能点数
	 */
    private double score=0;	
    /**
     * 目前的游戏是暂停的还是正在运行
     */
    private int currentState=RUNNING;
    
    private Timer timer=new Timer(TIMER_INTERVAL,this);
    /**
     * 锻炼器材的难度等级 1 to MAX_LEVEL
     */
    private int level=1;
    /**
     * 用来鉴别是否按下了gymUp键，
     * 因为做出相应需要玩家先后按下
     * 一组gymUp键和gymDown键
     */
    private boolean gymUpKeyPressed=false;
    /**
     * 用来控制更换图片的速度
     */
    private int changeImageForthCounter=0;
    /**
     * 用来控制更换图片的速度
     */
    private int changeImageBackCounter=0;
    /**
     * LevelSelector的引用 
     * 
     */
    private LevelSelector levelSelector=new LevelSelector(this);
    
    public GymSuper(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		//initialize self
		this.setPreferredSize(PANEL_SIZE);
		this.setSize(PANEL_SIZE);
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.add(levelSelector);
		
		//初始化其他
		
		//加入监听器 
		addKeyListener(this);
		
		//初始化图片组
		initializeImageSequence();
		
		if(debug)
		{
			System.out.println("after initialize");
			for(int i=0;i<imageSequence.length;i++)
			{
				System.out.println("imageSequence["+i+"]: "+imageSequence[i]);
			}
			System.out.println("\n\n");
		}
		
		setFrame();
		
		//gameStart
		restartGame();
	}
    /**
	 * 用来初始化图片组，会被类创建时自动调用
	 */
	public abstract void initializeImageSequence();
	
	public void restartGame()
	{
		this.currentState=RUNNING;
		this.requestFocusInWindow();
		this.timer.start();
	}
	
	public void resume()
	{
		this.currentState=RUNNING;
		this.timer.start();
	}
	
	public void pause()
	{
		this.currentState=PAUSED;
		this.timer.stop();
	}
	
	 
	public void setFrame()
	{
		getFrame().pack();
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2);
		
		getFrame().setResizable(false);
		getFrame().setVisible(true);
	}
	
	/**
	 * 这个方法负责处理游戏机制
	 */
	protected void selfProcess()
	{
		//计算因子
		double factor=1+(MAX_LEVEL-1)*getSkillDegree();
		
		//score加分
		score+=(degree*1.0/MAX_DEGREE)*SCORE_BASE_STEP*level/((getSkillDegree()<=1)?(factor):MAX_LEVEL);
		
		//degree倒退
		changeDegree(false);
		
		//interchange of imageSequence
		if(degree>MAX_DEGREE/10)
		{
			changeImageForthCounter++;
			if(changeImageForthCounter>=(MAX_DEGREE*1.0/degree)*MIN_IMAGE_CHANGE_INTERVAL)
			{
				changeImageForthCounter=0;
				interchangeImageSequence(true);
			}
		}
		else
		{
			changeImageBackCounter++;
			if(changeImageBackCounter>=2*MIN_IMAGE_CHANGE_INTERVAL)
			{
				changeImageBackCounter=0;
				interchangeImageSequence(false);
			}
		}
		
		//后续处理
		repaint();
	}
	//这个方法用于增加或减少degree
	private void changeDegree(boolean increase)
	{
		if(increase)
		{
			degree=Math.min(degree+DEGREE_STEP,MAX_DEGREE);
		}
		else
		{
			//计算因子
			double factor=1+(MAX_LEVEL-1)*getSkillDegree();
			degree=Math.max(degree-DEGREE_BACKLASH_BASE_SPEED*Math.pow(level,LEVEL_DISTANCE)/Math.pow(factor,LEVEL_DISTANCE),0);
		}
	}
	
	//这个方法用于切换动画图片
	private void interchangeImageSequence(boolean isForth)
	{
		if(null==imageSequence)
		{
			return;
		}
		
		if(isForth)
		{
			imageSequencePointer=(++imageSequencePointer)%imageSequence.length;
		}
		else
		{
			imageSequencePointer=Math.max(0,--imageSequencePointer);
		}
	}
	
	/**
	 * 这个方法返回对应这个健身种类的skill/topSkill的比值 
	 */
	protected abstract double getSkillDegree();
		
	/**
	 * 这个方法处理如何加分
	 */
	protected abstract void addScore();

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//画动画健身房图片
		if(imageSequence!=null)
		{
			if(debug)
			{
				if(null==imageSequence[imageSequencePointer])
			    {
					System.out.println("imageSequence["+imageSequencePointer+"]"+" is null ");
			    }
			}
			g.drawImage(imageSequence[imageSequencePointer],0,0,PANEL_SIZE.width,PANEL_SIZE.height,this);
		}
		else
		{
			if(debug)
			{
			    System.out.println("imageSequence==null");
			}
		}
		
		//画degree进度条degreeBar
		g.setColor(DEGREE_BAR_BACKGROUND);
		g.fillRect(DEGREE_BAR_LOCATION.x,DEGREE_BAR_LOCATION.y,DEGREE_BAR_SIZE.width,DEGREE_BAR_SIZE.height);
		
		g.setColor(DEGREE_BAR_FOREGROUND);
		g.fillRect(DEGREE_BAR_LOCATION.x,DEGREE_BAR_LOCATION.y,(int)(DEGREE_BAR_SIZE.width*degree*1.0/MAX_DEGREE),DEGREE_BAR_SIZE.height);
	
		if(debug2)
		{
			//画出score
			g.setColor(Color.WHITE);
			g.drawString(""+score,0,PANEL_SIZE.height);
			g.drawString(""+level,0,PANEL_SIZE.height-20);
		}
	}
	
	 
	public void winByOpponent() 
	{
		addScore();
	}
	 
	public void winByPlayer() 
	{
	    addScore();
	}

	 
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==timer)
	    {
			selfProcess();
	    }
	}
	 
	public void keyPressed(KeyEvent arg0) {
		
		
	}
	 
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()==ControlSetting.getGymUp())
		{
			gymUpKeyPressed=true;
		}
		
		if(e.getKeyCode()==ControlSetting.getGymDown())
		{
			if(gymUpKeyPressed)
			{
				gymUpKeyPressed=false;
				changeDegree(true);
			}
		}
		
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			if(!levelSelector.isVisible())
			{
				levelSelector.popUp();
			}
		}
	}
	 
	public void keyTyped(KeyEvent arg0) {
		
		
	}
	
	
	public Image[] getImageSequence() {
		return imageSequence;
	}
	public void setImageSequence(Image[] imageSequence) {
		this.imageSequence = imageSequence;
	}
	public int getImageSequencePointer() {
		return imageSequencePointer;
	}
	public double getDegree() {
		return degree;
	}
	public double getScore() {
		return score;
	}
	public int getCurrentState() {
		return currentState;
	}
	public int getLevel() {
		return level;
	}
	public boolean isGymUpKeyPressed() {
		return gymUpKeyPressed;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	protected void setLevel(int level) {
		this.level = level;
	}
	
	

}

/*
 *1、每种健身器材的图片组
 *2、按ESC键弹出对话框，初始化页面有两个按钮“level”、“leave”（state==menuPage），
 *单击“level”后state=changingPage，开始动画，到停止时进入state==levelPage，显示
 *level选择按钮板
 *
 */
