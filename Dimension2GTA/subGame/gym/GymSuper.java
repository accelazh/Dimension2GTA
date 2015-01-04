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
 * ���������������������subGame��ģ�壬
 * ����Ľ�����������������
 * 
 */
public abstract class GymSuper extends SubGame
implements GymConstants,ActionListener,KeyListener
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=true;

	/**
	 * �������ĵ�����
	 */
	private int type=-1;
	/**
	 * ��������ͼƬ��,��һ�ű�����δ����״̬��ͼƬ
	 */
	private Image[] imageSequence;
	/**
	 * ���ڶ�λimageSequence
	 */
	private int imageSequencePointer=0;
	/**
	 * ��������ָʾ��������Ļ���Ͻ��Ǹ�С��
	 */
	private double degree=0;
	/**
	 * �������������Զ���ȡ���ܵ���
	 */
    private double score=0;	
    /**
     * Ŀǰ����Ϸ����ͣ�Ļ�����������
     */
    private int currentState=RUNNING;
    
    private Timer timer=new Timer(TIMER_INTERVAL,this);
    /**
     * �������ĵ��Ѷȵȼ� 1 to MAX_LEVEL
     */
    private int level=1;
    /**
     * ���������Ƿ�����gymUp����
     * ��Ϊ������Ӧ��Ҫ����Ⱥ���
     * һ��gymUp����gymDown��
     */
    private boolean gymUpKeyPressed=false;
    /**
     * �������Ƹ���ͼƬ���ٶ�
     */
    private int changeImageForthCounter=0;
    /**
     * �������Ƹ���ͼƬ���ٶ�
     */
    private int changeImageBackCounter=0;
    /**
     * LevelSelector������ 
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
		
		//��ʼ������
		
		//��������� 
		addKeyListener(this);
		
		//��ʼ��ͼƬ��
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
	 * ������ʼ��ͼƬ�飬�ᱻ�ഴ��ʱ�Զ�����
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
	 * ���������������Ϸ����
	 */
	protected void selfProcess()
	{
		//��������
		double factor=1+(MAX_LEVEL-1)*getSkillDegree();
		
		//score�ӷ�
		score+=(degree*1.0/MAX_DEGREE)*SCORE_BASE_STEP*level/((getSkillDegree()<=1)?(factor):MAX_LEVEL);
		
		//degree����
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
		
		//��������
		repaint();
	}
	//��������������ӻ����degree
	private void changeDegree(boolean increase)
	{
		if(increase)
		{
			degree=Math.min(degree+DEGREE_STEP,MAX_DEGREE);
		}
		else
		{
			//��������
			double factor=1+(MAX_LEVEL-1)*getSkillDegree();
			degree=Math.max(degree-DEGREE_BACKLASH_BASE_SPEED*Math.pow(level,LEVEL_DISTANCE)/Math.pow(factor,LEVEL_DISTANCE),0);
		}
	}
	
	//������������л�����ͼƬ
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
	 * ����������ض�Ӧ������������skill/topSkill�ı�ֵ 
	 */
	protected abstract double getSkillDegree();
		
	/**
	 * �������������μӷ�
	 */
	protected abstract void addScore();

	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//����������ͼƬ
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
		
		//��degree������degreeBar
		g.setColor(DEGREE_BAR_BACKGROUND);
		g.fillRect(DEGREE_BAR_LOCATION.x,DEGREE_BAR_LOCATION.y,DEGREE_BAR_SIZE.width,DEGREE_BAR_SIZE.height);
		
		g.setColor(DEGREE_BAR_FOREGROUND);
		g.fillRect(DEGREE_BAR_LOCATION.x,DEGREE_BAR_LOCATION.y,(int)(DEGREE_BAR_SIZE.width*degree*1.0/MAX_DEGREE),DEGREE_BAR_SIZE.height);
	
		if(debug2)
		{
			//����score
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
 *1��ÿ�ֽ������ĵ�ͼƬ��
 *2����ESC�������Ի��򣬳�ʼ��ҳ����������ť��level������leave����state==menuPage����
 *������level����state=changingPage����ʼ��������ֹͣʱ����state==levelPage����ʾ
 *levelѡ��ť��
 *
 */
