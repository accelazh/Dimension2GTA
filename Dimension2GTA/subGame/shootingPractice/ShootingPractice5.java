package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;


import subGameSuper.*;
import utilities.MyPoint;
import gameDisplayProcessor.*;
import java.util.*;
import utilities.*;

public class ShootingPractice5 extends SubGame
implements ActionListener,MouseListener
{
	//for debug
	private final static boolean debug=false;
	private final static boolean debug2=true;
	
	private JPanel mainPanel=new JPanel();
	private final Dimension mainPanelSize=new Dimension(882,614);
	private JPanel controlPanel=null;
	
	private JLabel background=new JLabel();
	
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
    		new Point(0,183),
    		new Point(0,249),
    		new Point(0,311),
    		
    		new Point(820,183),
    		new Point(820,249),
    		new Point(820,311),
    		
    };

	private final int diskHeightOffset=150;
    private Point[] disappearPoints={
    		new Point(116, 340-diskHeightOffset), 
    		new Point(269, 332-diskHeightOffset),
			new Point(330, 328-diskHeightOffset), 
			new Point(404, 321-diskHeightOffset), 
			new Point(438, 294-diskHeightOffset),
			new Point(485, 319-diskHeightOffset),
			new Point(576, 349-diskHeightOffset),
			new Point(685, 334-diskHeightOffset),
			new Point(804, 336-diskHeightOffset),
    		
    };
	
	private Target[] targets=new Target[launchPoints.length*disappearPoints.length];
	
	//游戏当前的状态
	private final int STOPPED=0;
	private final int STARTED=1;
	private int gameState=STOPPED;

	private JLabel timeOverLabel=new JLabel("Time Over!");
	
	//对靶子的运动周期的控制
	private int maxTargetCycle=40*1000/TIMER_INTERVAL;
	private int changingMaxTargetCycle=maxTargetCycle;
	private int roundTime=40; 
	private int movingSpeed=4;
	
	//sound effect
	private SoundPlayer deagleClip=new SoundPlayer(ShootingPractice5.class.getResource("deagle-1.wav"));
	private SoundPlayer diskClip=new SoundPlayer(ShootingPractice5.class.getResource("disk.wav"));
	
	public ShootingPractice5(Player player,Human opponent,MainGameWindow master)
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
				
		//初始化targets
	    
	    
	    for(int i=0;i<launchPoints.length;i++)
	    {
	    	for(int j=0;j<disappearPoints.length;j++)
	    	{
	    		if(((launchPoints[i].x<=mainPanelSize.width/2)&&
	    			(disappearPoints[j].x<=mainPanelSize.width/2+10))
	    		||(((launchPoints[i].x>=mainPanelSize.width/2)&&
		    			(disappearPoints[j].x>=mainPanelSize.width/2-10))))
	    		{
	    			targets[i*disappearPoints.length+j]=new Target(launchPoints[i],disappearPoints[j],true);
	    		}
	    		else
	    		{
	    			targets[i*disappearPoints.length+j]=null;
	    		}
	    	}
	    }
				
		for(int i=targets.length-1;i>=0;i--)
		{
			if(targets[i]!=null)
			{
				mainPanel.add(targets[i]);
			}
		}
		
		
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
		background.setPreferredSize(mainPanelSize);
		background.setSize(background.getPreferredSize());
		background.setIcon(new ImageIcon("pic/SubGame/shootingPractice/bk4.jpg"));
		background.setLocation(0,0);
		mainPanel.add(background);
		
		//加入监听器
		startButton.addActionListener(this);
		cancleButton.addActionListener(this);
		this.addMouseListener(this);
		
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
		
		
		if(STARTED==gameState)
		{
			if(e.getSource()==timer)
			{
				for(int i=0;i<targets.length;i++)
				{
					if(targets[i]!=null)
					{
						if(targets[i]!=null)
						{
							targets[i].selfProcess();
						}
					}
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
							if(targets[i]!=null)
							{
								targets[i].initializeTarget();
							}
							
						}
						
						infoBar.setForeground(Color.RED);
						infoBar.setText("Time Over! You can play another round.");
					
						startButton.setEnabled(true);
					
						timer.stop();
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
			}
			
			if(e.getSource()==startButton)
			{
				changingMaxTargetCycle=maxTargetCycle;
				gameState=STARTED;
				roundHitTimes=0;
				timeOverLabel.setVisible(false);
				for(int i=0;i<targets.length;i++)
				{
					if(targets[i]!=null)
					{
						targets[i].restartTarget();
					}
						
					
				}
				startButton.setEnabled(false);
				time=roundTime;
				timeDisplay.setText(""+time);
				infoBar.setForeground(Color.BLUE);
				infoBar.setText("OK,GO GO GO. Keep Fighting!!!");
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
		AddingGunSkill addingGunSkill=new AddingGunSkill(this,(int)(hitTimes*1.0/30*15));
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



	 
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mousePressed(MouseEvent e) {
		deagleClip.play();
		
	}

	 
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}



	private class Target extends JPanel
	implements MouseListener
	{
		private Dimension origSize=null;	
		private Point dstLocation=null;
		private Point origLocation=null;
		private ImageIcon imageIcon=null;
		private boolean shrink=false;
			    
		private final Dimension picSize=new Dimension(62*2,29*2);
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
	    
	  	public Target(Point origLocation,Point dstLocation,boolean shrink)
		{
			this.origSize=picSize;
			this.dstLocation=dstLocation;
			
			this.shrink=shrink;
						
			this.arc=VectorClass.arcOfPoints(origLocation,dstLocation);
				
			this.origLocation=new Point((int)(origLocation.x-Math.cos(arc)*(origSize.width+origSize.height)),
					(int)(origLocation.y+Math.sin(arc)*(origSize.width+origSize.height)));
			this.totalLength=(int)MyPoint.getDistance(this.origLocation,dstLocation);
			
			
			this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/disk.jpg");
			
			setLocation(origLocation);
			setPreferredSize(origSize);
			setSize(origSize);
			this.setBackground(Color.WHITE);
			setVisible(false);
			
			addMouseListener(this);
		}
		
		public void initializeTarget()
		{
			this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/disk.jpg");
			
			setLocation(origLocation);
			setPreferredSize(origSize);
			setSize(origSize);
			this.setBackground(Color.WHITE);
			setBorder(null);
			
			setVisible(false);
			repaint();
			
		}

		 
		public void mouseClicked(MouseEvent arg0) 
		{
			
			
		}

		 
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mousePressed(MouseEvent arg0) 
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
			    	deagleClip.play();			    	
			    	repaint();
			    }
			}
		}

		 
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		//控制靶子的运动周期
		public void restartTarget()
		{
			waitingSpan=(int)(changingMaxTargetCycle*Math.random());
			waitingCounter=0;
			currentState=WAITING;

			this.imageIcon=new ImageIcon("pic/SubGame/shootingPractice/disk.jpg");
			
			setLocation(origLocation);
			setPreferredSize(origSize);
			setSize(origSize);
			this.setBackground(Color.WHITE);
			
			setBorder(null);
			movingOffset=0;
		    setVisible(false);
			
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
				diskClip.play();
			}
						
			if(HOLDING==currentState)
			{
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
			
			repaint();
		}
		
		public void paintComponent(Graphics g)
		{
			
			super.paintComponent(g);
			Image image=imageIcon.getImage();
			if(image!=null)
			{
				g.drawImage(image,0,0,
				 	getSize().width,getSize().height, this);
			}
		}
		
	}
	
	//test
	
	public static void main(String[] args)
	{
		ShootingPractice5 panel=new ShootingPractice5(null,null,null);
	
		Component[] c=panel.getFrame().getContentPane().getComponents();
		for(int i=0;i<c.length;i++)
		{
			System.out.println(c[i]);
		}
	}

}
