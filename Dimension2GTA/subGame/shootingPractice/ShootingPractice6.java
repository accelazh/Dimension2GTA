package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;

import subGameSuper.*;
import utilities.MyPoint;
import gameDisplayProcessor.*;
import java.applet.*;
import java.net.*;
import java.awt.image.*;
import java.io.*;

public class ShootingPractice6 extends SubGame
implements ActionListener
{
	//for debug
	private final static boolean debug=false;
	private final static boolean debug2=false;
	private final static boolean debug3=false;
	private final static boolean debug4=false;
	
	private JPanel mainPanel=new JPanel();
	private static final Dimension mainPanelSize=new Dimension(800,564);
	private JPanel controlPanel=null;
	
	private ImageIcon background=null;
	
	private JButton startButton=new JButton("Start");
	private JButton cancleButton=new JButton("Cancle");
	
	private JLabel hitLabel=new JLabel("Hits: ");
	private JLabel hitTimesLabel=new JLabel("0");
	private int hitTimes=0;
	private int roundHitTimes=0;
	
	private JLabel timeLabel=new JLabel("Time: ");
	private JLabel timeDisplay=new JLabel("0");
	private int timeCounter=0;
	private int time=0;
	
	private JLabel infoBar=new JLabel();
	
	public final static int TIMER_INTERVAL=10;
	private Timer timer=new Timer(TIMER_INTERVAL,this);
	
	private WallPanel wallPanel=null;
	public static final Dimension brickSize=new Dimension(40,13);
	public static final Dimension wallPanelTotalSize=mainPanelSize;
	public static final Dimension brickAreaSize=new Dimension(800,390);
	public static final int brickRowNum=brickAreaSize.height/brickSize.height;
	public static final int brickColumNum=brickAreaSize.width/brickSize.width;
	public static final int groundLine=497;  //描述地面在那里，以便处理球撞地		
	public static final int ballRadius=16;
	public static final int screenMoveByBallDistance=25;   //球离屏幕边缘这个距离的时候就要移动屏幕
	public static final int screenMoveStep=20;
	
	public static final int DEAGLE=1;
	public static final int M4=0;
	public static final int AWP=2;
	public static final int ROCKET=3;
	
	public static int origTotalNumOfRocket=5;
	public int totalNumOfRocket=origTotalNumOfRocket;
	
	//音效
	private AudioClip deagleClip[]=null;
	private int deagleClipPointer=0;
	private AudioClip[] m4Clip=null;
	private int m4ClipPointer=0;
	private AudioClip awpClip=null;
	private AudioClip[] rocketClip=null;
	private int rocketClipPointer=0;
	private AudioClip[] rocketEmptyClip=null;
	private int rocketEmptyClipPointer=0;
	
	private AudioClip gunSwitchClip=null;
	
	private AudioClip[] ric=null;
	private int ricPointer=0; 
	//游戏当前的状态
	private final int STOPPED=0;
	private final int STARTED=1;
	private int gameState=STOPPED;

	private JLabel timeOverLabel=new JLabel("See How High Can You Reach!");
	
	//对靶子的运动周期的控制
	
	public final static double gravity=1500;   //单位象素每秒的平方
	
	
	public ShootingPractice6(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		setLayout(new BorderLayout());
		//初始化mianPanel
		mainPanel.setLayout(null);
		
		mainPanel.setPreferredSize(mainPanelSize);
		mainPanel.setSize(mainPanel.getPreferredSize());
		mainPanel.setBackground(new Color(0,128,0));
		
		timeOverLabel.setFont(new Font("Times",Font.BOLD,36));
		timeOverLabel.setForeground(Color.YELLOW);
		timeOverLabel.setBorder(new LineBorder(Color.RED,4));
		timeOverLabel.setVisible(true);
		timeOverLabel.setSize(timeOverLabel.getPreferredSize());
		timeOverLabel.setLocation((mainPanelSize.width-timeOverLabel.getWidth())/2,
				(mainPanelSize.height-timeOverLabel.getHeight())/2);
		
				
		//初始化wallPanel
	    wallPanel=new WallPanel();
	    wallPanel.add(timeOverLabel);
	    mainPanel.add(wallPanel);		
	    
		
	    //加入mainPanel
		add(mainPanel,BorderLayout.CENTER);
		//初始化控制栏
		
				
		JPanel p1=new JPanel(new BorderLayout());
		this.controlPanel=p1;
		
		JPanel p11=new JPanel(new BorderLayout());
		p11.setBorder(new LineBorder(Color.YELLOW,2));
		p11.add(startButton,BorderLayout.CENTER);
		p11.add(cancleButton,BorderLayout.SOUTH);
		p1.add(p11,BorderLayout.WEST);
		
		JPanel p12=new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
				
		JPanel p121=new JPanel();
		
		hitLabel.setFont(new Font("Times",Font.BOLD,16));
		hitLabel.setForeground(Color.BLUE);
		hitLabel.setBorder(new LineBorder(Color.YELLOW,2));
		hitTimesLabel.setFont(new Font("Times",Font.BOLD,16));
		hitTimesLabel.setForeground(Color.RED);
		hitTimesLabel.setBorder(new LineBorder(Color.GREEN,2));
		p121.add(hitLabel);
		p121.add(hitTimesLabel);
		p12.add(p121);
		
		timeLabel.setFont(new Font("Times",Font.BOLD,16));
		timeLabel.setForeground(Color.BLUE);
		timeLabel.setBorder(new LineBorder(Color.YELLOW,2));
		timeDisplay.setFont(new Font("Times",Font.BOLD,16));
		timeDisplay.setForeground(Color.RED);
		timeDisplay.setBorder(new LineBorder(Color.GREEN,2));
		JPanel p122=new JPanel();
		p122.add(timeLabel);
		p122.add(timeDisplay);
		p12.add(p122);
		
		p1.add(p12,BorderLayout.CENTER);
		
		infoBar.setHorizontalAlignment(JLabel.CENTER);
		infoBar.setBorder(new LineBorder(Color.GREEN,2));
		infoBar.setFont(new Font("Times",Font.BOLD,14));
		infoBar.setForeground(Color.RED);
		infoBar.setText("Keep practice and you can reach the hit-man level");
		p1.add(infoBar,BorderLayout.SOUTH);
		
		add(p1,BorderLayout.SOUTH);
		
		//设置背景
		background=new ImageIcon("pic/SubGame/shootingPractice/bk5.jpg");
		
		//加入监听器
		startButton.addActionListener(this);
		cancleButton.addActionListener(this);
			
		//设置音效
		try
		{
			Class metaObject=this.getClass();
			URL url1=metaObject.getResource("deagle-1.wav");
			deagleClip=new AudioClip[3];
			for(int i=0;i<deagleClip.length;i++)
			{
		    	deagleClip[i]=Applet.newAudioClip(url1);
			}
			deagleClipPointer=0;
			
			m4Clip=new AudioClip[20];
			for(int i=0;i<m4Clip.length;i++)
			{
			    m4Clip[i]=Applet.newAudioClip(metaObject.getResource("m4a1.wav"));
			}
			m4ClipPointer=0;
			awpClip=Applet.newAudioClip(metaObject.getResource("awp.wav"));
			
			rocketClip=new AudioClip[5];
			for(int i=0;i<rocketClip.length;i++)
			{
			    rocketClip[i]=Applet.newAudioClip(metaObject.getResource("explode.wav"));
			}
			rocketClipPointer=0;
			
			rocketEmptyClip=new AudioClip[5];
			for(int i=0;i<rocketEmptyClip.length;i++)
			{
				rocketEmptyClip[i]=Applet.newAudioClip(metaObject.getResource("dryfire.wav"));
			}
			rocketEmptyClipPointer=0;
			
			gunSwitchClip=Applet.newAudioClip(metaObject.getResource("boltpull.wav"));
		
            ric=new AudioClip[6];
            for(int i=0;i<ric.length;i++)
            {
            	ric[i]=Applet.newAudioClip(metaObject.getResource("ric"+(i+1)+".wav"));
            }
            ricPointer=0;
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"deagleClip error");
		}
		setFrame();
		setFocusable(false);
		p1.setFocusable(false);
		mainPanel.setFocusable(false);
		startButton.setFocusable(false);
		cancleButton.setFocusable(false);
	
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

	 
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(STARTED==gameState)
		{
			if(e.getSource()==timer)
			{
				wallPanel.selfProcess();
				
				if(timeCounter<1000/TIMER_INTERVAL)
				{
					timeCounter++;
					
				}
				else
				{
					timeCounter=0;
					time++;
					
					timeDisplay.setText(""+time);
					
				}
				
				
				
			}
			
			if(e.getSource()==cancleButton)
			{
				timer.stop();
				gameState=STOPPED;
				startButton.setEnabled(true);
				time=0;
				timeDisplay.setText(""+time);
				
				wallPanel.recordHeighestLine();
				
				winByPlayer();
				
			}
		}
		
		if(STOPPED==gameState)
		{
			if(e.getSource()==cancleButton)
			{
				timer.stop();
				gameState=STOPPED;
				startButton.setEnabled(true);
				time=0;
				timeDisplay.setText(""+time);
				winByPlayer();
				
			}
			
			if(e.getSource()==startButton)
			{
				gameState=STARTED;
				roundHitTimes=0;
				timeOverLabel.setVisible(false);
				
				wallPanel.initialize();
				
				startButton.setEnabled(false);
				time=0;
				timeDisplay.setText(""+time);
				infoBar.setForeground(Color.BLUE);
				infoBar.setText("Shoot the ball. See how High can you reach.");
				timer.start();
				
			}
		}
		
		
	}
	
	public void hitAndScore()
	{
		hitTimes++;
		roundHitTimes++;
		hitTimesLabel.setText(roundHitTimes+"");
	}
	
	
	
	 
	public void winByOpponent() 
	{
		
		
	}

	 
	public void winByPlayer() 
	{
		AddingGunSkill addingGunSkill=new AddingGunSkill(this,(int)(hitTimes*1.0/30*16));
		this.remove(mainPanel);
		this.remove(controlPanel);
		this.add(addingGunSkill);
		getFrame().pack();
		getFrame().setTitle("ADDING SKILL POINTS");
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2);
		
		getFrame().repaint();
		
	}



	private class WallPanel extends JPanel
	implements MouseListener,MouseMotionListener,KeyListener,MouseWheelListener
	{
		private Background background=new Background();
		
		private Brick[][] bricks=new Brick[brickRowNum][brickColumNum]; 
		private Ball ball=null;
		private Gun gun=null;
		
		private Spark[] sparks=null;
		private Spark2[] spark2s=null;
		private Spark3[] spark3s=null;
		private BulletHole[] bulletHoles=null;
		private RocketHole[] rocketHoles=null;
		
		private GunAim gunAim=null;
		private boolean attacking=false;
		private Point mousePoint=new Point(0,0);
		
		private int currentGun=DEAGLE;
		
		private GunDisplay gunDisplay=new GunDisplay(DEAGLE);
		
		private int heighestLine=groundLine-wallPanelTotalSize.height/2;  //标明球的最高点
		
		public WallPanel()
		{
			setLayout(null);
			setPreferredSize(wallPanelTotalSize);
			setSize(wallPanelTotalSize);
			setLocation(0,0);
			addMouseListener(this);
			addMouseMotionListener(this);
			addKeyListener(this);
			addMouseWheelListener(this);
			
			gun=new Gun();
			ball=new Ball(gun);
			
			sparks=new Spark[10];
		    for(int i=0;i<sparks.length;i++)
		    {
		    	sparks[i]=null;
		    }
		    
		    spark2s=new Spark2[10];
		    for(int i=0;i<spark2s.length;i++)
		    {
		    	spark2s[i]=null;
		    }
		    spark3s=new Spark3[10];
		    for(int i=0;i<spark3s.length;i++)
		    {
		    	spark3s[i]=null;
		    }
		    
		    bulletHoles=new BulletHole[10];
		    for(int i=0;i<bulletHoles.length;i++)
		    {
		    	bulletHoles[i]=null;
		    }
		    rocketHoles=new RocketHole[10];
		    for(int i=0;i<rocketHoles.length;i++)
		    {
		    	rocketHoles[i]=null;
		    }
		    
		    this.gunAim=new GunAim();
		    
		    
		    Toolkit tk=Toolkit.getDefaultToolkit();
		    Image temp=tk.getImage("pic/cursors/blank.gif");
		    Cursor cursor=tk.createCustomCursor(temp,new Point(0,0),"none");
		    setCursor(cursor);
	
		    setFocusable(true);
		    
		    //读入最高分
		    readHeighestLine();
		    		    
		}
		
		public void addSpark(Spark newSpark)
		{
			int i;        // to search for the end of the array
			for(i=0;(i<sparks.length)&&(sparks[i]!=null);i++)
				;
			if(i<sparks.length)
			{
				sparks[i]=newSpark;
				
			}
			else
			{
				Spark[] newSparks=new Spark[sparks.length*2];
				for(int j=0;j<newSparks.length;j++)
				{
					newSparks[j]=null;
				}
				System.arraycopy(sparks,0,newSparks,0,sparks.length);
				
				newSparks[sparks.length]=newSpark;
				this.sparks=newSparks;
			}
		}
		
		public void addSpark2(Spark2 newSpark2)
		{
			int i;        // to search for the end of the array
			for(i=0;(i<spark2s.length)&&(spark2s[i]!=null);i++)
				;
			if(i<spark2s.length)
			{
				spark2s[i]=newSpark2;
				
			}
			else
			{
				Spark2[] newSpark2s=new Spark2[spark2s.length*2];
				for(int j=0;j<newSpark2s.length;j++)
				{
					newSpark2s[j]=null;
				}
				System.arraycopy(spark2s,0,newSpark2s,0,spark2s.length);
				
				newSpark2s[spark2s.length]=newSpark2;
				this.spark2s=newSpark2s;
			}
		}
		
		public void addSpark3(Spark3 newSpark3)
		{
			int i;        // to search for the end of the array
			for(i=0;(i<spark3s.length)&&(spark3s[i]!=null);i++)
				;
			if(i<spark3s.length)
			{
				spark3s[i]=newSpark3;
				
			}
			else
			{
				Spark3[] newSpark3s=new Spark3[spark3s.length*3];
				for(int j=0;j<newSpark3s.length;j++)
				{
					newSpark3s[j]=null;
				}
				System.arraycopy(spark3s,0,newSpark3s,0,spark3s.length);
				
				newSpark3s[spark3s.length]=newSpark3;
				this.spark3s=newSpark3s;
			}
		}
		
		public void addBulletHole(BulletHole newBulletHole)
		{
			int i;        // to search for the end of the array
			for(i=0;(i<bulletHoles.length)&&(bulletHoles[i]!=null);i++)
				;
			if(i<bulletHoles.length)
			{
				bulletHoles[i]=newBulletHole;
				
			}
			else
			{
				BulletHole[] newBulletHoles=new BulletHole[bulletHoles.length*2];
				for(int j=0;j<newBulletHoles.length;j++)
				{
					newBulletHoles[j]=null;
				}
				System.arraycopy(bulletHoles,0,newBulletHoles,0,bulletHoles.length);
				
				newBulletHoles[bulletHoles.length]=newBulletHole;
				this.bulletHoles=newBulletHoles;
			}
		}
		public void addRocketHole(RocketHole newRocketHole)
		{
			int i;        // to search for the end of the array
			for(i=0;(i<rocketHoles.length)&&(rocketHoles[i]!=null);i++)
				;
			if(i<rocketHoles.length)
			{
				rocketHoles[i]=newRocketHole;
				
			}
			else
			{
				RocketHole[] newRocketHoles=new RocketHole[rocketHoles.length*2];
				for(int j=0;j<newRocketHoles.length;j++)
				{
					newRocketHoles[j]=null;
				}
				System.arraycopy(rocketHoles,0,newRocketHoles,0,rocketHoles.length);
				
				newRocketHoles[rocketHoles.length]=newRocketHole;
				this.rocketHoles=newRocketHoles;
			}
		}
		
		public void setBrick(Brick brick,int row,int colum)
		{
			this.bricks[row][colum]=brick;
			repaint();
		}
		
		public void initialize()
		{
			this.ball.setLocation(new MyPoint(wallPanelTotalSize.width/2,wallPanelTotalSize.height/2));
		    this.ball.setVx(0);
		    this.ball.setVy(0);
		    
		    totalNumOfRocket=origTotalNumOfRocket;
		}   
		
		public void setCurrentGun(int type)
		{
			gun.switchGun(type);
			currentGun=type;
			gunDisplay.setCurrentGun(type);
			gunSwitchClip.play();
		}
		
		public void playM4Clip()
		{
			m4Clip[m4ClipPointer].play();
			m4Clip[(m4ClipPointer+1)%m4Clip.length].stop();
			m4ClipPointer++;
			m4ClipPointer%=m4Clip.length;
		}
		public void playDeagleClip()
		{
			deagleClip[deagleClipPointer].play();
			deagleClip[(deagleClipPointer+1)%deagleClip.length].stop();
			deagleClipPointer++;
			deagleClipPointer%=deagleClip.length;
		}
		public void playRocketClip()
		{
			if(totalNumOfRocket>0)
			{   
				rocketClip[rocketClipPointer].play();
				rocketClip[(rocketClipPointer+1)%rocketClip.length].stop();
		    	rocketClipPointer++;
		    	rocketClipPointer%=rocketClip.length;
			}
			else
			{
				rocketEmptyClip[rocketEmptyClipPointer].play();
				rocketEmptyClip[(rocketEmptyClipPointer+1)%rocketEmptyClip.length].stop();
		    	rocketEmptyClipPointer++;
		    	rocketEmptyClipPointer%=rocketEmptyClip.length;
			}
			
		}
		public void playRic()
		{
			ric[ricPointer].play();
			ric[(ricPointer+1)%ric.length].stop();
			ricPointer++;
			ricPointer%=ric.length;
		}
		
		public void recordHeighestLine()
		{
			try
			{
				DataOutputStream output=new DataOutputStream(new FileOutputStream("heighScore.dat"));
				output.writeInt(heighestLine);
				output.close();
			}
			catch(IOException ex)
			{
				;
			}
		}
		
		public void readHeighestLine()
		{
			try
			{
				DataInputStream input=new DataInputStream(new FileInputStream("heighScore.dat"));
				heighestLine=input.readInt();  
				input.close();
				
			}
			catch(IOException ex)
			{
				;
			}
		}
		
		public void selfProcess()
		{
			
			if (STARTED == gameState) 
			{
				//处理开枪
				if (attacking) {
					if (gun.shoot())
					{
						
						gunAim.shootAdjust();
						switch(currentGun)
						{
						case DEAGLE: 
						{
						    playDeagleClip();
						    break;
						}
						case M4: 
						{
							playM4Clip();
							break;
						}
						case AWP: 
						{
						    awpClip.play();
						    break;
						}
						case ROCKET:
						{
							playRocketClip();
						}	
						default:
						{
							break;
						}
						}
						
						double distance = ball.getLocation().distance(mousePoint);
						if (currentGun!=ROCKET) {
							if (distance < ballRadius) 
							{
								playRic();
								hitAndScore();
								if (debug3) {
									System.out.println(ball.toString());
									System.out.println("e.getPoint(): "
											+ mousePoint);
									System.out.println("ball.getLocation(): "
											+ ball.getLocation());
								}

								MyPoint hitPoint = new MyPoint(mousePoint.x
										- ball.getLocation().x, mousePoint.y
										- ball.getLocation().y);

								ball.hitByBullet(hitPoint);

								if (debug3) {
									System.out.println(ball.toString());
									System.out.println("e.getPoint(): "
											+ mousePoint);
									System.out.println("ball.getLocation(): "
											+ ball.getLocation());
								}

								// 添加spark
								if (true) {
									addSpark(new Spark(hitPoint, ball
											.getLocation(), this));
								}
							} else {
								if (currentGun != M4) {
									addSpark2(new Spark2(new MyPoint(
											mousePoint.x, mousePoint.y), this));
								} else {
									addSpark2(new Spark2(new MyPoint(
											mousePoint.x, mousePoint.y), this,
											false));
								}

								addBulletHole(new BulletHole(mousePoint));
							}
						}
						else
						{
							if(totalNumOfRocket>0)
							{
								MyPoint hitPoint=new MyPoint(mousePoint.x,mousePoint.y);
							    addSpark3(new Spark3(hitPoint));
							    addRocketHole(new RocketHole(hitPoint.getPoint(),this));
							    ball.hitByRocket(distance,new MyPoint(hitPoint.x-ball.getLocation().x,hitPoint.y-ball.getLocation().y));
							    totalNumOfRocket--;
							    						    
							}
							else
							{
								;
							}
						}
					}
				}
				
				
				
				// ==处理球
				double dertX = ball.getVx() * TIMER_INTERVAL / 1000.0;
				double dertY = ball.getVy() * TIMER_INTERVAL / 1000.0;
				ball.setLocation(ball.getLocation().x + dertX, ball
						.getLocation().y - dertY);

				double dertVy = -gravity * TIMER_INTERVAL / 1000.0;
				ball.setVy(ball.getVy() + dertVy);

				//处理屏幕随着球移动
				if(!debug4){
				if(ball.getLocation().y-ballRadius<screenMoveByBallDistance)
				{
					int step=screenMoveByBallDistance-(int)(ball.getLocation().y-ballRadius);
					screenMove(step,true);
				}
				if(ball.getLocation().y+ballRadius>wallPanelTotalSize.height-screenMoveByBallDistance)
				{
					int step=(int)ball.getLocation().y+ballRadius-(wallPanelTotalSize.height-screenMoveByBallDistance)+1;
					screenMove(step,false);
					
				}
				}
				if ((ball.getLocation().y +ballRadius>= groundLine+background.getOffset())
					    &&(ball.getVy()<0))
				{
					
					
					ball.setLocation(ball.getLocation().x, (background.getOffset()+groundLine)-ballRadius);
					ball.setVy(-ball.getVy() / 2.0);
					ball.setVx(ball.getVx()*19/20);
					
					
				}
				

				if ((ball.getLocation().x -ballRadius<= 0)
					&&(ball.getVx()<0))
				{
					ball.setLocation(ballRadius,ball.getLocation().y);
					ball.setVx(-ball.getVx()*3/4);
					
				}
				
				if ((ball.getLocation().x +ballRadius>=wallPanelTotalSize.width)
						&&(ball.getVx()>0))
					{
						ball.setLocation(wallPanelTotalSize.width-ballRadius,ball.getLocation().y);
						ball.setVx(-ball.getVx()*3/4);
						
					}
				
				heighestLine=(int)Math.max(heighestLine, groundLine+background.getOffset()-ball.getLocation().y+ballRadius);
				
				//处理Spark
				for(int i=0;i<sparks.length;i++)
				{
					if(sparks[i]!=null)
					{
						sparks[i].selfProcess();
						if(!sparks[i].isUseAble())
						{
							sparks[i]=null;
						}
					}
				}
				//处理Spark2
				for(int i=0;i<spark2s.length;i++)
				{
					if(spark2s[i]!=null)
					{
						spark2s[i].selfProcess();
						if(!spark2s[i].isUseAble())
						{
							spark2s[i]=null;
						}
					}
				}
				
				//处理Spark3
				for(int i=0;i<spark3s.length;i++)
				{
					if(spark3s[i]!=null)
					{
						spark3s[i].selfProcess();
						if(!spark3s[i].isUseAble())
						{
							spark3s[i]=null;
						}
					}
				}
				
				//==处理枪
				gun.selfProcess();
				
				//处理准星
				gunAim.selfProcess();
				
				//处理弹孔
				int totalNumOfHoles=0;
				for(int i=0;i<bulletHoles.length;i++)
				{
					if(bulletHoles[i]!=null)
					{
						bulletHoles[i].selfProcess();
					    totalNumOfHoles++;
					}
				}
				if(totalNumOfHoles>50)
				{
					for(int i=0;i<bulletHoles.length;i++)
					{
						bulletHoles[i]=null;
					}
					for(int i=0;i<rocketHoles.length;i++)
					{
						rocketHoles[i]=null;
					}
				}
				
				
				
				
				//处理背景
				background.selfProcess();
				
				//处理gunDisplay
				gunDisplay.selfProcess();
				
				//后续处理
				repaint();
				
				// 检查是否游戏结束
			}
			
			
		}

		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			//draw background
		    background.paint(g,this);
		    						
			//draw bullet holes
			for(int i=0;i<bulletHoles.length;i++)
			{
				if(bulletHoles[i]!=null)
				{
					bulletHoles[i].paint(g);
				}
			}
			for(int i=0;i<rocketHoles.length;i++)
			{
				if(rocketHoles[i]!=null)
				{
					rocketHoles[i].paint(g);
				}
			}
			
			//draw bricks
			
			//draw heightestLine
			int actualLine=-(heighestLine-(background.getOffset()+groundLine));
			g.setColor(Color.RED);
			g.setFont(new Font("Times",Font.BOLD,12));
			FontMetrics fm=g.getFontMetrics();
			g.drawString("Heighest", 10, actualLine-3-2);
			g.fillRect(0, actualLine-3,wallPanelTotalSize.width,3);
			
			//draw ball
			g.setColor(ball.color);
			g.fillOval((int)(ball.getLocation().x-ballRadius),(int)(ball.getLocation().y-ballRadius),
					2*ballRadius,2*ballRadius);
			g.setColor(Color.WHITE);
			g.fillOval((int)(ball.getLocation().x+ballRadius/4),(int)(ball.getLocation().y-3*ballRadius/4),
					ballRadius/2,ballRadius/2);
			
			//draw gunDisplay
			gunDisplay.paint(g);
			
			//draw height score
            Point scoreLocation=new Point(10,wallPanelTotalSize.height-14);
			g.setColor(Color.RED);
			g.setFont(new Font("Times",Font.BOLD,12));
			g.drawString("Heighest: "+heighestLine/10, scoreLocation.x,scoreLocation.y);
			g.drawString("Current: "+(int)(groundLine+background.getOffset()-ball.getLocation().y+ballRadius)/10,
					scoreLocation.x,scoreLocation.y+12);
			//draw bullet sparks
			for(int i=0;i<sparks.length;i++)
			{
				if(sparks[i]!=null)
				{
					sparks[i].paint(g);
				}
			}
			for(int i=0;i<spark2s.length;i++)
			{
				if(spark2s[i]!=null)
				{
					spark2s[i].paint(g);
				}
			}
			for(int i=0;i<spark3s.length;i++)
			{
				if(spark3s[i]!=null)
				{
					spark3s[i].paint(g);
				}
			}
			
			//draw gunAim
			gunAim.paint(g);
				
			
		}
		
		
		
		 
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			this.mousePoint=e.getPoint();
		}

		 
		public void mouseEntered(MouseEvent e)
		{
			this.mousePoint=e.getPoint();
			
		}

		 
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			this.mousePoint=e.getPoint();
		}

		 
		public void mousePressed(MouseEvent e)
		{
			this.mousePoint=e.getPoint();
			attacking=true;
			
			if(e.getButton()==MouseEvent.BUTTON3)
			{
				gunAim.switchAimColor();
			}
			if(e.getButton()==MouseEvent.BUTTON2)
			{
				ball.switchColor();
			}
			
			gunAim.setCenter(e.getPoint());
			if (STARTED == gameState) {
				if (gun.shoot())
				{
					gunAim.shootAdjust();
					switch(currentGun)
					{
					case DEAGLE: 
					{
					    playDeagleClip();
					    break;
					}
					case M4: 
					{
					   playM4Clip();
					    break;
					}
					case AWP: 
					{
					    awpClip.play();
					    break;
					}
					case ROCKET:
					{
						playRocketClip();
					}
					default:
					{
						break;
					}
					}
					double distance = ball.getLocation().distance(
							e.getPoint());
					if (currentGun!=ROCKET) {
						
						if (distance < ballRadius) {
							playRic();
							hitAndScore();
							if (debug3) {
								System.out.println(ball.toString());
								System.out.println("e.getPoint(): "
										+ e.getPoint());
								System.out.println("ball.getLocation(): "
										+ ball.getLocation());
							}

							MyPoint hitPoint = new MyPoint(e.getPoint().x
									- ball.getLocation().x, e.getPoint().y
									- ball.getLocation().y);

							ball.hitByBullet(hitPoint);

							if (debug3) {
								System.out.println(ball.toString());
								System.out.println("e.getPoint(): "
										+ e.getPoint());
								System.out.println("ball.getLocation(): "
										+ ball.getLocation());
							}

							// 添加spark
							if (true) {
								addSpark(new Spark(hitPoint,
										ball.getLocation(), this));
							}
						} else {
							if (currentGun != M4) {
								addSpark2(new Spark2(new MyPoint(
										e.getPoint().x, e.getPoint().y), this));
							} else {
								addSpark2(new Spark2(new MyPoint(
										e.getPoint().x, e.getPoint().y), this,
										false));
							}

							addBulletHole(new BulletHole(e.getPoint()));
						}
					}
					else
					{
						if(totalNumOfRocket>0)
						{
							MyPoint hitPoint=new MyPoint(mousePoint.x,mousePoint.y);
						    addSpark3(new Spark3(hitPoint));
						    addRocketHole(new RocketHole(hitPoint.getPoint(),this));
						    ball.hitByRocket(distance,new MyPoint(hitPoint.x-ball.getLocation().x,hitPoint.y-ball.getLocation().y));
						    totalNumOfRocket--;
						}
						else
						{
							;
						}
					}
				}
			}
		}

		 
		public void mouseReleased(MouseEvent e) 
		{
			this.mousePoint=e.getPoint();
			gunAim.setCenter(e.getPoint());
			attacking=false;
		}

		 
		public void mouseDragged(MouseEvent e) 
		{
			this.mousePoint=e.getPoint();
			gunAim.setCenter(e.getPoint());
			//处理准星
			gunAim.selfProcess();
			repaint();
			attacking=true;
		}

		 
		public void mouseMoved(MouseEvent e) 
		{
			this.mousePoint=e.getPoint();
			gunAim.setCenter(e.getPoint());
			//处理准星
			gunAim.selfProcess();
			repaint();
		}

		 
		public void keyPressed(KeyEvent e)
		{
		    if(e.getKeyCode()==KeyEvent.VK_UP)
		    {
		    	scrollByUser(true);
		    	
		    }
		    if(e.getKeyCode()==KeyEvent.VK_DOWN)
		    {
		    	scrollByUser(false);
		    }
		    if(e.getKeyCode()==ControlSetting.getPlayerMoveUp())
		    {
		    	scrollByUser(true);
		    }
		    if(e.getKeyCode()==ControlSetting.getPlayerMoveDown())
		    {
		    	scrollByUser(false);
		    }
		}

		 
		public void keyReleased(KeyEvent e) 
		{
			if(e.getKeyCode()==KeyEvent.VK_1)
			{
				setCurrentGun(M4);
			}
			if(e.getKeyCode()==KeyEvent.VK_2)
			{
				setCurrentGun(DEAGLE);
			}
			if(e.getKeyCode()==KeyEvent.VK_3)
			{
				setCurrentGun(AWP);
			}
			if(e.getKeyCode()==KeyEvent.VK_4)
			{
				setCurrentGun(ROCKET);
			}
			if(e.getKeyCode()==KeyEvent.VK_F4)
			{
				gunAim.switchAimColor();
			}
			if(e.getKeyCode()==KeyEvent.VK_SPACE)
			{
				ball.switchColor();
			}
			
		}

		 
		public void keyTyped(KeyEvent e) 
		{
			
		}

		 
		public void mouseWheelMoved(MouseWheelEvent e) 
		{
			if(e.getWheelRotation()==-1)
			{
				scrollByUser(true);
			}
			if(e.getWheelRotation()==1)
			{
				scrollByUser(false);
			}
		}
		
		public void scrollByUser(boolean up)
		{
			if(up)
			{
				int step=Math.min(screenMoveStep,wallPanelTotalSize.height-screenMoveByBallDistance-(int)ball.getLocation().getY()-ballRadius);
			    if(debug4)
				{
			    	step=screenMoveStep;
				}
				if(step<0)
			    {
			    	step=0;
			    }
				screenMove(step,true);
			}
			else
			{
				int step=Math.min(screenMoveStep,background.getOffset());
				step=Math.min(step,(int)ball.getLocation().getY()-ballRadius-screenMoveByBallDistance);
				if(debug4)
				{
				   	step=screenMoveStep;
				}
				if(step<0)
			    {
			    	step=0;
			    }
				screenMove(step,false);
			}
		}
		
		public void screenMove(int step, boolean up)
		{
			if(up)
			{
				background.setOffset(background.getOffset()+step);
				ball.setLocation(new MyPoint(ball.getLocation().x,ball.getLocation().y+step));
				for(int i=0;i<sparks.length;i++)
				{
					if(sparks[i]!=null)
					{
						sparks[i].screenMove(step, true);
					}
				}
				for(int i=0;i<spark2s.length;i++)
				{
					if(spark2s[i]!=null)
					{
						spark2s[i].screenMove(step,true);
					}
				}
				
				for(int i=0;i<bulletHoles.length;i++)
				{
					if(bulletHoles[i]!=null)
					{
						bulletHoles[i].screenMove(step,true);
					}
				}
				for(int i=0;i<rocketHoles.length;i++)
				{
					if(rocketHoles[i]!=null)
					{
						rocketHoles[i].screenMove(step,true);
					}
				}
			}
			else
			{
				background.setOffset(background.getOffset()-step);
				ball.setLocation(new MyPoint(ball.getLocation().x,ball.getLocation().y-step));
				for(int i=0;i<sparks.length;i++)
				{
					if(sparks[i]!=null)
					{
						sparks[i].screenMove(step, false);
					}
				}
				for(int i=0;i<spark2s.length;i++)
				{
					if(spark2s[i]!=null)
					{
						spark2s[i].screenMove(step,false);
					}
				}
				
				for(int i=0;i<bulletHoles.length;i++)
				{
					if(bulletHoles[i]!=null)
					{
						bulletHoles[i].screenMove(step,false);
					}
				}
				for(int i=0;i<rocketHoles.length;i++)
				{
					if(rocketHoles[i]!=null)
					{
						rocketHoles[i].screenMove(step,false);
					}
				}
			}
		}
	}
	
	private class Gun
	{
		public int INTERVAL=(int)(0.25*1000/TIMER_INTERVAL);
		private int intervalCounter=0;	
		private boolean shootAble=true;
		
		private double bulletHitV=1500;
		
		public double getBulletHitV() {
			return bulletHitV;
		}

		public Gun()
		{
			
		}
		
		public void switchGun(int type)
		{
			if(DEAGLE==type)
			{
				bulletHitV=1700;
				INTERVAL=(int)(0.25*1000/TIMER_INTERVAL);
				intervalCounter=0;
			}
			
			if(M4==type)
			{
				bulletHitV=1100;
				INTERVAL=(int)(0.06*1000/TIMER_INTERVAL);
				intervalCounter=0;
			}
			
			if(AWP==type)
			{
				bulletHitV=2500;
				INTERVAL=(int)(0.9*1000/TIMER_INTERVAL);
				intervalCounter=0;
			}
			if(ROCKET==type)
			{
				bulletHitV=2000;
				INTERVAL=(int)(0.9*1000/TIMER_INTERVAL);
				intervalCounter=0;
			}
			
		}
		
		public boolean shoot()
		{
			if(shootAble)
			{
				shootAble=false;
				intervalCounter=0;
				return true;
			}
			else
			{
				return false;
			}
		}
		
		public void selfProcess()
		{
			if(shootAble)
			{
				intervalCounter=0;
			}
			else
			{
				if(intervalCounter<=INTERVAL)
				{
				    intervalCounter++;	
				}
				else
				{
					intervalCounter=0;
					shootAble=true;
				}
			}
		}
		
		
	}
	
	private class Ball
	{
		
		private double Vx=0;    //按数学的直交坐标系方向为正
		private double Vy=0;    //单位是象素每秒
		
		private MyPoint location=null;  //圆心的坐标
		private Gun gun=null;
		
		private Color color=Color.BLACK;
		private int colorPointer=0;
		
		public Ball(Gun gun)
		{
			location=new MyPoint(wallPanelTotalSize.width/2,wallPanelTotalSize.height/2);
		    this.gun=gun;
		}
		
		public void switchColor()
		{
			switch(colorPointer)
			{
			case 0:
			{
				color=Color.YELLOW;
				colorPointer=1;
				break;
			}
			case 1:
			{
				color=Color.BLUE;
				colorPointer=2;
				break;
			}
			case 2:
			{
				color=Color.RED;
				colorPointer=3;
				break;
			}
			case 3:
			{
				color=Color.WHITE;
				colorPointer=4;
				
				break;
			}
			case 4:
			{
				color=Color.GREEN;
				colorPointer=5;
				
				break;
			}
			case 5:
			{
				color=Color.BLACK;
				colorPointer=0;
				break;
			}
			default:
			{
				
				break;
			}
			}
		}
		
		public void hitByBullet(MyPoint point)  //相对于球心的击中点
		{
			double dertVx=gun.getBulletHitV()*(-point.x/ballRadius);
			double dertVy=gun.getBulletHitV()*(point.y/ballRadius);
			
			Vx+=dertVx;
			Vy+=dertVy;
		}
		
		public void hitByRocket(double distance,MyPoint point)
		{
			double dertV=gun.getBulletHitV();
			if(distance<SparkFlash2.RADIUS/8)
			{
				dertV=dertV*1.5;
			}
			else
			{
				dertV=1.5*dertV-((1.5*dertV-dertV)/(7.0/8*SparkFlash2.RADIUS))*(distance-SparkFlash2.RADIUS/8);
				if(dertV<0)
				{
					dertV=0;
				}
			}
			
			Vx+=dertV*(-point.x/distance);
			Vy+=dertV*(point.y/distance);
		}

		public double getVx() {
			return Vx;
		}

		public void setVx(double vx) {
			Vx = vx;
		}

		public double getVy() {
			return Vy;
		}

		public void setVy(double vy) {
			Vy = vy;
		}

		public MyPoint getLocation() {
			return location;
		}

		public void setLocation(MyPoint location) {
			this.location = location;
		}
		public void setLocation(double x,double y)
		{
			this.location = new MyPoint(x,y);
		}
		
		public String toString()
		{
			String output="";
			output+="Ball: "+"\n";
			output+="location: ("+location.x+", "+location.y+")"+"\n";
			output+="velocity: "+"Vx=="+Vx+", "+"Vy=="+Vy+"\n";
			return output;
		}
	}
	
	private class GunDisplay
	{
		
		private int currentGun=DEAGLE;
		
		
		public GunDisplay(int currentGun)
		{
			this.currentGun=currentGun;
			
		}
		
		public void selfProcess()
		{
			
		}
		
		public void paint(Graphics g)
		{
			Point startPoint=new Point(10,wallPanelTotalSize.height-66-10);
			
			g.setColor(Color.GREEN);
			
			g.fill3DRect(startPoint.x,startPoint.y,20,3,true);
			g.fill3DRect(startPoint.x,startPoint.y+3+40,20,3,true);
	
			g.fill3DRect(startPoint.x+20+26,startPoint.y,20,3,true);
			g.fill3DRect(startPoint.x+20+26,startPoint.y+3+40,20,3,true);
		
			g.fill3DRect(startPoint.x,startPoint.y+3,3,15,true);
			g.fill3DRect(startPoint.x+3+60,startPoint.y+3,3,15,true);
	
			g.fill3DRect(startPoint.x,startPoint.y+3+15+10,3,15,true);
			g.fill3DRect(startPoint.x+3+60,startPoint.y+3+15+10,3,15,true);
		
		    g.setFont(new Font("Times",Font.BOLD,18));
		    g.setColor(Color.BLUE);
		    String output="";
		    switch(currentGun)
		    {
		    case DEAGLE:
		    {
		    	output+="Deagle";
		    	break;
		    }
		    case M4:
		    {
		    	output+="M4a1";
		    	break;
		    }
		    case AWP:
		    {
		    	output+="AWP";
		    	break;
		    }
		    case ROCKET:
		    {
		    	output+="Rocket";
		    	break;
		    }
		    default:
		    {
		    	break;
		    }
		    }
		    
		    FontMetrics fm=g.getFontMetrics();
		    int width=fm.stringWidth(output);
		    int height=fm.getHeight();
		    
		    Point strStartPoint=new Point(startPoint.x+(66-width)/2,startPoint.y+46-(46-height)/2-8);
		    g.drawString(output,strStartPoint.x,strStartPoint.y);
		}
		
		public void setCurrentGun(int type)
		{
			currentGun=type;
		}
	}
	
	private class Brick
	{
		private Point order=null;  //用砖块在数组中的序号表示它的位置
		
		private ImageIcon imageIcon=null;
		
		private Point location=null;
		
		//用来描述砖块的状态
		public final static int NORMAL=0;
		public final static int HITTED=0;
		public final static int DISAPPEARED=0;
		
		private int currentState=NORMAL;
		
		public Brick(Point order)
		{
			this.order=order;
			int offsetX=(wallPanelTotalSize.width-brickRowNum*brickSize.width)/2;
			location=new Point(offsetX+order.y*brickSize.width,order.x*brickSize.height);
			
			this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/brick1.jpg");
		}
		
		public void setCurrentStateToNormal()
		{
			
		}
		public void setCurrentStateToHitted()
		{
			
		}
		public void setCurrentStateToDisappeared()
		{
			
		}
	}

	 
	public void windowClosed(WindowEvent arg0) {
		super.windowClosed(arg0);
		
		wallPanel.recordHeighestLine();
	}

	 
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		super.windowClosing(arg0);
		wallPanel.recordHeighestLine();
	}
	
	
	public static void main(String[] args)
	{
		ShootingPractice6 panel=new ShootingPractice6(null,null,null);
	
	}
	
	
		

}

