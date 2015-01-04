package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;

import subGameSuper.*;
import gameDisplayProcessor.*;
import java.awt.image.*;
import utilities.*;
import java.util.*;
import java.applet.*;

public class ShootingPractice4 extends SubGame
implements ActionListener
{
	//for debug
	private final static boolean debug=false;
	private final static boolean debug2=false;
	private final static boolean debug3=true;
	
	private MainPanel mainPanel=new MainPanel();
	private final Dimension mainPanelSize=new Dimension(949,614);
	private JPanel controlPanel=null;
	
	private Image background=new ImageIcon("pic/SubGame/shootingPractice/bk3.jpg").getImage();
	
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
	
	private final static int TIMER_INTERVAL=10;
	private javax.swing.Timer timer=new javax.swing.Timer(TIMER_INTERVAL,this);
	
	//定位个体个飞靶
	private Point[] launchPoints={
    		new Point(97,289),
    		new Point(194,246),
    		new Point(267,201),
    		new Point(366,191),
    		new Point(457,223),
    		new Point(526,257),
    		new Point(627,235),
    		new Point(724,224),
    		new Point(835,279),
    };

    private Point[] disappearPoints={
    		new Point(0,234),
    		new Point(0,181),
    		new Point(0,58),
    		new Point(0,0),
    		
    		new Point(117,0),
    		new Point(237,0),
    		new Point(350,0),
    		new Point(472,0),
    		new Point(592,0),
    		new Point(704,0),
    		new Point(816,0),
    		new Point(882,0),
    		
    		new Point(882,56),
    		new Point(882,125),
    		new Point(882,208),
    };
	
	private Target[] targets=new Target[launchPoints.length*disappearPoints.length];
	
	//游戏当前的状态
	private final int STOPPED=0;
	private final int STARTED=1;
	private int gameState=STOPPED;

	private JLabel timeOverLabel=new JLabel("Time Over!");
	
	//对靶子的运动周期的控制
	private int maxTargetCycle=8*1000/TIMER_INTERVAL;
	private int changingMaxTargetCycle=maxTargetCycle;
	private int roundTime=40; 
	private int movingSpeed=4;
	
	//枪口准星
	private GunAim gunAim=new GunAim();
	
	//枪口火花
	private ArrayList<SparkFlash> sparkFlashs=new ArrayList<SparkFlash>();
	
	//射击控制
	private boolean shootRequested=false;
	
	public static final int GUN_READY_SPAN=(int)(0.06*1000/TIMER_INTERVAL);  //每发子弹的间隔时间
	private int gunReadyCounter=0;
	private boolean gunReady=true;
	
	private boolean bulletRemain=true;   //控制是否不在换弹夹
	public static final int ORIG_BULLET_NUM=30;  //弹夹容量
	private int numOfBullets=ORIG_BULLET_NUM;
	public static final int RELOAD_SPAN=1*1000/TIMER_INTERVAL;
	private int reloadCounter=0;
	
	//用来画剩余子弹数目的字体
	Font bulletFont=new Font("Times",Font.BOLD,72);
	
	//音效
	private SoundPlayer m4Clip=new SoundPlayer(ShootingPractice4.class.getResource("m4a1.wav"),30);
	private SoundPlayer duckClip=new SoundPlayer(ShootingPractice4.class.getResource("duck.wav"));
	private SoundPlayer bullBkClip=new SoundPlayer(ShootingPractice4.class.getResource("bullBk.wav"),1);

	private SoundPlayer m4Deploy=new SoundPlayer(ShootingPractice4.class.getResource("m4a1_deploy.wav"),1);
	private SoundPlayer m4ClipOut=new SoundPlayer(ShootingPractice4.class.getResource("m4a1_clipout.wav"),1);
	private SoundPlayer m4ClipIn=new SoundPlayer(ShootingPractice4.class.getResource("m4a1_clipin.wav"),1);
	
	public ShootingPractice4(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		//设置鼠标
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image temp=tk.getImage("pic/cursors/aim1.gif");
		Cursor cursor=tk.createCustomCursor(temp,new Point(16,16),"aim1");
		setCursor(cursor);
		
		setLayout(new BorderLayout());
	
		//初始化mianPanel
		mainPanel.setLayout(null);
		
		mainPanel.setPreferredSize(mainPanelSize);
		mainPanel.setSize(mainPanel.getPreferredSize());
		mainPanel.setBackground(new Color(0,128,0));
		
		timeOverLabel.setFont(new Font("Times",Font.BOLD,36));
		timeOverLabel.setForeground(Color.YELLOW);
		timeOverLabel.setBorder(new LineBorder(Color.RED,4));
		timeOverLabel.setVisible(false);
		timeOverLabel.setSize(timeOverLabel.getPreferredSize());
		timeOverLabel.setLocation((mainPanelSize.width-timeOverLabel.getWidth())/2,
				(mainPanelSize.height-timeOverLabel.getHeight())/2);
		mainPanel.add(timeOverLabel);
		
		//初始化准星
		gunAim.switchAimColor(3);
				
		//初始化targets
	    for(int i=0;i<launchPoints.length;i++)
	    {
	    	for(int j=0;j<disappearPoints.length;j++)
	    	{
	    		targets[i*disappearPoints.length+j]=new Target(launchPoints[i],disappearPoints[j],false);
	    	}
	    }
				
		/*for(int i=targets.length-1;i>=0;i--)
		{
			mainPanel.add(targets[i]);
		}*/
		
		
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
		
		//加入背景
		/*background.setPreferredSize(mainPanelSize);
		background.setSize(background.getPreferredSize());
		background.setIcon(new ImageIcon("pic/SubGame/shootingPractice/bk3.jpg"));
		background.setLocation(0,0);
		//mainPanel.add(background);*/
		
		//加入监听器
		startButton.addActionListener(this);
		cancleButton.addActionListener(this);
			
		setFrame();
		
		try
		{
			int speed=Integer.parseInt(JOptionPane.showInputDialog
			("Please input target moving speed(default:"+movingSpeed+")."));
			movingSpeed=speed;			
		}
		catch(Exception ex)
		{
			;
		}
		
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
		
		mainPanel.selfProcess();
		
		if(STARTED==gameState)
		{
			if(e.getSource()==timer)
			{
				for(int i=0;i<targets.length;i++)
				{
					targets[i].selfProcess();
				}
				
				if(timeCounter<1000/TIMER_INTERVAL)
				{
					timeCounter++;
					
				}
				else
				{
					timeCounter=0;
					
					time--;
					
					changingMaxTargetCycle-=(maxTargetCycle-500/TIMER_INTERVAL)/roundTime;
					
					timeDisplay.setText(""+time);
					if(time<=0)
					{
						gameState=STOPPED;
						
						timeOverLabel.setVisible(true);
						
						for(int i=0;i<targets.length;i++)
						{
							targets[i].initializeTarget();
							
						}
						
						infoBar.setForeground(Color.RED);
						infoBar.setText("Time Over! You can play another round.");
					
						startButton.setEnabled(true);
						
						timer.stop();
						bullBkClip.stop();
					}
				}
				
				
			}
			
			if(e.getSource()==cancleButton)
			{
				timer.stop();
				gameState=STOPPED;
				startButton.setEnabled(true);
				time=0;
				timeDisplay.setText(""+time);
				winByPlayer();
				
				timer.stop();
				bullBkClip.stop();
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
				
				timer.stop();
				bullBkClip.stop();
			}
			
			if(e.getSource()==startButton)
			{
				changingMaxTargetCycle=maxTargetCycle;
				gameState=STARTED;
				roundHitTimes=0;
				timeOverLabel.setVisible(false);
				for(int i=0;i<targets.length;i++)
				{
					targets[i].restartTarget();
						
					
				}
				startButton.setEnabled(false);
				time=roundTime;
				timeDisplay.setText(""+time);
				infoBar.setForeground(Color.BLUE);
				infoBar.setText("OK,GO GO GO. Keep Fighting!!!");
				timer.start();		
				
				bullBkClip.play();
			}
		}
		repaint();
		
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
		AddingGunSkill addingGunSkill=new AddingGunSkill(this,(int)(hitTimes*1.0/130*15));
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
	
	public void endOfGameMessage()
	{
		bullBkClip.stop();
	}
    
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    }

	private class Target /*extends JPanel*/
	/*implements MouseListener*/
	{
		private Dimension origSize=null;	
		private Point dstLocation=null;
		private Point origLocation=null;
		private ImageIcon imageIcon=null;
		private boolean shrink=false;
			    
		private final Dimension picSize=new Dimension(65,31);
	    //一下是衡量靶子运动的量
		
	    private final int popUpSpeed=2*3;  //要为偶数
	    private int waitingSpan=0;
	    
	    public final int WAITING=0;
	    private int waitingCounter=0;
	    public final int POPING_UP=1;
	    public final int HOLDING=4;
	    public final int POPING_DOWN=2;
	    public final int HITTED=3;
	    
	    private int currentState=WAITING;
	    
	    private int totalLength=0;  //描述应该运动多远
	    private int movingOffset=0;
	    private double arc=0;     
	    
	    //控制图像切换
	    private int picType=1;
	    private int picTypeSub=0;
	    private int switchPicCounter=0;
	    
	    private String r="";  //用来设定鸟飞的方向的图片
	    
	    //大小和位置
	    private Dimension size=new Dimension(0,0);
	    private Point location=new Point(0,0);
	    
	    //隐藏
	    private boolean visible=false;
		
		public Target(Point origLocation,Point dstLocation,boolean shrink)
		{
			this.origSize=picSize;
			this.origLocation=origLocation;
			
			this.shrink=shrink;
						
			this.arc=VectorClass.arcOfPoints(origLocation,dstLocation);
				
			this.dstLocation=new Point((int)(dstLocation.x+Math.cos(arc)*(origSize.width+origSize.height)),
					(int)(dstLocation.y-Math.sin(arc)*(origSize.width+origSize.height)));
			this.totalLength=(int)MyPoint.getDistance(origLocation,this.dstLocation);
			
			this.r=(dstLocation.x>origLocation.x)?"r":"";
			int rand=(int)(Math.random()*5);
			switch(rand)
			{
			case 0 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck1"+r+".gif");
			    picTypeSub=0;
			    picType=1;
			    break; 
			}
			case 1 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck2"+r+".gif");
			    picTypeSub=0;
			    picType=2;
			    break; 
			}
			case 2 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck3"+r+".gif");
			    picTypeSub=0;
			    picType=3;
			    break; 
			}
			case 3 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck4"+r+".gif");
			    picTypeSub=0;
			    picType=4;
			    break; 
			}
			case 4 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck5"+r+".gif");
			    picTypeSub=0;
			    picType=5;
			    break; 
			}
			default:
			{
				break;
			}
			}
			
			setLocation(origLocation);
			setSize(origSize);
			/*this.setBackground(Color.WHITE);*/
			setVisible(false);
			
			/*addMouseListener(this);*/
		}
		
		public void initializeTarget()
		{
			int rand=(int)(Math.random()*5);
			switch(rand)
			{
			case 0 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck1"+r+".gif");
			    picTypeSub=0;
			    picType=1;
			    break; 
			}
			case 1 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck2"+r+".gif");
			    picTypeSub=0;
			    picType=2;
			    break; 
			}
			case 2 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck3"+r+".gif");
			    picTypeSub=0;
			    picType=3;
			    break; 
			}
			case 3 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck4"+r+".gif");
			    picTypeSub=0;
			    picType=4;
			    break; 
			}
			case 4 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck5"+r+".gif");
			    picTypeSub=0;
			    picType=5;
			    break; 
			}
			default:
			{
				break;
			}
			}
			
			setLocation(origLocation);
			setSize(origSize);
			/*this.setBackground(Color.WHITE);*/
			
			setVisible(false);
			
		}

		public void getShot() 
		{
			
			
			if(STARTED==gameState)
			{
				if((POPING_UP==currentState)
					||(POPING_DOWN==currentState)
					||(HOLDING==currentState))
			    {
					if(debug)
					{
						System.out.println("hit the target");
					}
					
			    	currentState=HITTED;
			    				    
			    	hitAndScore();
			    }
			}
		}
		
		//控制靶子的运动周期
		public void restartTarget()
		{
			waitingSpan=(int)(changingMaxTargetCycle*Math.random());
			waitingCounter=0;
			currentState=WAITING;

			int rand=(int)(Math.random()*5);
			switch(rand)
			{
			case 0 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck1"+r+".gif");
			    picTypeSub=0;
			    picType=1;
			    break; 
			}
			case 1 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck2"+r+".gif");
			    picTypeSub=0;
			    picType=2;
			    break; 
			}
			case 2 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck3"+r+".gif");
			    picTypeSub=0;
			    picType=3;
			    break; 
			}
			case 3 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck4"+r+".gif");
			    picTypeSub=0;
			    picType=4;
			    break; 
			}
			case 4 :
			{
			    this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck5"+r+".gif");
			    picTypeSub=0;
			    picType=5;
			    break; 
			}
			default:
			{
				break;
			}
			}
			
			setLocation(origLocation);
			/*setPreferredSize(origSize);*/
			setSize(origSize);
			/*this.setBackground(Color.WHITE);*/
			
			movingOffset=0;
		    setVisible(false);
			
		}
		
	    public boolean contains(Point point)
	    {
	    	Rectangle rect=new Rectangle();
	    	rect.setLocation(this.location.x,this.location.y);
	    	rect.setSize(this.size.width,this.size.height);
	    
	    	return rect.contains(point);
	    }
		
		public void selfProcess()
		{
			if(HITTED==currentState)
			{
				
				
				if(((getSize().width>1)
					&&(getSize().height>1))
					&&(getLocation().y>1))
				{
					setSize(getSize().width,getSize().height-1);
					setLocation(getLocation().x,getLocation().y+2*popUpSpeed);
			    }
				else
				{
					restartTarget();
				}
			}
			
			if(WAITING==currentState)
			{
				if(waitingSpan<=waitingCounter)
				{
					currentState=POPING_UP;
					setVisible(true);
					waitingCounter=0;
				}
				else
				{
					waitingCounter++;
				}
			}
			
			if(POPING_UP==currentState)
			{
				currentState=HOLDING;
			}
						
			if(HOLDING==currentState)
			{
				if(switchPicCounter<=100/TIMER_INTERVAL)
				{
					switchPicCounter++;
				}
				else
				{
					switchPicCounter=0;
					if(0==picTypeSub)
					{
						imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck"+picType+"1"+r+".gif");
					    picTypeSub=1;
					}
					else
					{
						imageIcon=new ImageIcon("pic/SubGame/shootingPractice/duck"+picType+r+".gif");
					    picTypeSub=0;
					}
				}
				
				if(shrink)
				{
					this.setSize((int)(origSize.width*(1-movingOffset*1.0/totalLength)),
					        (int)(origSize.height*(1-movingOffset*1.0/totalLength)));
				}
				
				if(movingOffset<=totalLength-movingSpeed)
				{
					movingOffset+=movingSpeed;
				    this.setLocation(origLocation.x+(int)(movingOffset*Math.cos(arc)),
					    	origLocation.y-(int)(movingOffset*Math.sin(arc)));
				}
				else
				{
					movingOffset=0;
					currentState=POPING_DOWN;
				}
					
				    
			}
			
			if(POPING_DOWN==currentState)
			{
				restartTarget();              
			}
			
			/*repaint();*/
		}
		
		/*protected void paintComponent(Graphics g)
		{
			if(debug2)
			{
				System.out.println("====in method paintComponent====");
				if(getSize().height<picSize.height)
				{
					System.out.println("some pic's height is wrong");
				}
				if(getSize().width<picSize.width)
				{
					System.out.println("some pic's width is wrong");
				}
			}
			super.paintComponent(g);
			Image image=imageIcon.getImage();
			if(image!=null)
			{
				g.drawImage(image,0,0,
				 	getSize().width,getSize().height, this);
			}
		}*/
		
		public void paint(Graphics g, ImageObserver observer)
		{
			if(!visible)
			{
				return;
			}
			
			Image image=imageIcon.getImage();
			if(image!=null)
			{
				g.drawImage(image,getLocation().x+0,getLocation().y+0,
				 	getSize().width,getSize().height, observer);
			}
		}
		
		public Dimension getSize() {
			return size;
		}

		public void setSize(Dimension size) {
			this.size = size;
		}
		public void setSize(int width, int height) {
			this.size = new Dimension(width,height);
		}

		public Point getLocation() {
			return location;
		}

		public void setLocation(Point location) {
			this.location = location;
		}
		
		public void setLocation(int x, int y) {
			this.location = new Point(x,y);
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

	}

	private class MainPanel extends JPanel
	implements MouseListener,MouseMotionListener
	{
		public MainPanel()
		{
			 Toolkit tk=Toolkit.getDefaultToolkit();
			 Image temp=tk.getImage("pic/cursors/blank.gif");
			 Cursor cursor=tk.createCustomCursor(temp,new Point(0,0),"none");
			 setCursor(cursor);
			 
			 addMouseListener(this);
			 addMouseMotionListener(this);
		}
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			//draw background
			g.drawImage(background,0,0,mainPanelSize.width,mainPanelSize.height,this);
			
			//draw ducks
			for(int i=0;i<targets.length;i++)
			{
				if(targets[i]!=null)
				{
					targets[i].paint(g, this);
				}
			}
			
			//draw number of bullets
			g.setColor(Color.RED);
			g.setFont(bulletFont);
			FontMetrics fm=g.getFontMetrics();
			String out=""+numOfBullets;
			g.drawString(out,mainPanelSize.width-fm.stringWidth(out)-10,mainPanelSize.height-10);
			
			//draw aim
			gunAim.paint(g);
			
			//draw spark flash
			for(int i=0;i<sparkFlashs.size();i++)
			{
				SparkFlash sf=sparkFlashs.get(i);
				if(sf!=null)
				{
					sf.paint(g,this);
				}
			}
			
		}
		
		//用于处理枪、准星、换弹夹等等
		protected void selfProcess()
		{
			if(STARTED==gameState)
			{
				//==处理开枪==
				if(bulletRemain)
				{
					if(shootRequested)
				    {
						if (gunReady) 
						{
							//上弹状态打开
							gunReady=false;							
							//射击
							gunAim.shootAdjust();
							//添加火花
							sparkFlashs.add(new SparkFlash(gunAim.getCenter(),40));
							
							//制造声音
							System.out.println("sound played");
							m4Clip.play();
							//子弹减少
							numOfBullets--;
							if (numOfBullets <= 0) 
							{
								bulletRemain = false;
							}
							//检查击中
							for (int i = 0; i < targets.length; i++) 
							{
								if (targets[i] != null) 
								{
									if (targets[i].contains(gunAim.getCenter())) 
									{
										targets[i].getShot();
									}
								}
							}
						}
						else
						{
							gunReadyCounter++;
													
							if(gunReadyCounter>=GUN_READY_SPAN)
							{
								gunReadyCounter=0;
								gunReady=true;
							}
						}
						
			    	}
				}
				else
				{
					reloadCounter++;
					
					//声音控制
					int deployLength=(int)(0.3*1000/TIMER_INTERVAL);
					int clipOutLength=(int)(0.3*1000/TIMER_INTERVAL);
					
					if(reloadCounter==1)
					{
						m4Deploy.play();
					}
					
					if(reloadCounter==deployLength)
					{
						m4ClipOut.play();
					}
					
					if(reloadCounter==deployLength+clipOutLength)
					{
						m4ClipIn.play();
					}
					
					if(reloadCounter>=RELOAD_SPAN)
					{
						reloadCounter=0;
						bulletRemain=true;
						numOfBullets=ORIG_BULLET_NUM;
					}
				}
			}
			
			//处理准星
			gunAim.selfProcess();
			
			//处理火花
			for(int i=0;i<sparkFlashs.size();i++)
			{
				SparkFlash sf=sparkFlashs.get(i);
				if(sf!=null)
				{
					sf.selfProcess();
					if(!sf.isUseAble())
					{
						sparkFlashs.remove(i);
					}
				}
			}
			
			//处理鸭子叫声
			if(Math.random()<0.05)
			{
				duckClip.play();
			}
			
		}
		
		 
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mousePressed(MouseEvent e) 
		{
			shootRequested=true;
		}

		 
		public void mouseReleased(MouseEvent e) 
		{
			shootRequested=false;
			
		}

		 
		public void mouseDragged(MouseEvent e) 
		{
			gunAim.setCenter(e.getPoint());
		}

		 
		public void mouseMoved(MouseEvent e)
		{
			gunAim.setCenter(e.getPoint());
			
		}

	}
	
	//test
	public static void main(String[] args)
	{
		ShootingPractice4 panel=new ShootingPractice4(null,null,null);
	
		for(int i=0;i<panel.getComponents().length;i++)
		{
			System.out.println((panel.getComponents())[i]);
			System.out.println();
		}
		
		Component[] c=panel.getFrame().getContentPane().getComponents();
		for(int i=0;i<c.length;i++)
		{
			System.out.println(c[i]);
		}
		
		System.out.println(panel.isVisible());
	}
	
}

