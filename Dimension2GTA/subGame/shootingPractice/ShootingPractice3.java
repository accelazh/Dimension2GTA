package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;

import subGameSuper.*;
import utilities.SoundPlayer;
import gameDisplayProcessor.*;

public class ShootingPractice3 extends SubGame
implements ActionListener, MouseListener
{

	//for debug
	private final static boolean debug=false;
	
	private JPanel mainPanel=new JPanel();
	private final Dimension mainPanelSize=new Dimension(630,370);
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
	private Timer timer=new Timer(TIMER_INTERVAL,this);
	
	private Target[] targets=new Target[8];
	
	//游戏当前的状态
	private final int STOPPED=0;
	private final int STARTED=1;
	private int gameState=STOPPED;

	private JLabel timeOverLabel=new JLabel("Time Over!");
	
	//对靶子的运动周期的控制
	private int maxTargetCycle=5*1000/TIMER_INTERVAL;
	private int changingMaxTargetCycle=maxTargetCycle;
	
	private int roundTime=40; 
	private int movingSpeed=6;
	
	//imageIcons
	private ImageIcon[] imageIcons={
			new ImageIcon("pic/SubGame/shootingPractice/aim4.jpg"),
			new ImageIcon("pic/SubGame/shootingPractice/aim5.jpg"),
			new ImageIcon("pic/SubGame/shootingPractice/aim6.jpg"),
			new ImageIcon("pic/SubGame/shootingPractice/aim7.jpg"),
	};
	private Dimension[] targetSizes={
	        new Dimension(imageIcons[0].getIconWidth(),imageIcons[0].getIconHeight()),
	        new Dimension(imageIcons[1].getIconWidth(),imageIcons[1].getIconHeight()),
	        new Dimension(imageIcons[2].getIconWidth(),imageIcons[2].getIconHeight()),
	        new Dimension(imageIcons[3].getIconWidth(),imageIcons[3].getIconHeight()),
	};
	
	//sound effect
	private SoundPlayer deagleClip=new SoundPlayer(ShootingPractice.class.getResource("deagle-1.wav"));
	
	public ShootingPractice3(Player player,Human opponent,MainGameWindow master)
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
		for(int i=0;i<4;i++)
		{
			targets[2*i]=new Target(i,Target.LEFT);
			targets[2*i+1]=new Target(i,Target.RIGHT);
		}
		
		for(int i=targets.length-1;i>=0;i--)
		{
			mainPanel.add(targets[i]);
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
		background.setIcon(new ImageIcon("pic/SubGame/shootingPractice/bk2.jpg"));
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
				(screenSize.height-getFrame().getHeight())/2-30);
		
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
					targets[i].restartTarget();
						
					
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
		AddingGunSkill addingGunSkill=new AddingGunSkill(this,(int)(hitTimes*1.0/100*15));
		this.remove(mainPanel);
		this.remove(controlPanel);
		this.add(addingGunSkill);
		getFrame().pack();
		getFrame().setTitle("ADDING SKILL POINTS");
		
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2-50);
		
		getFrame().repaint();
		
	}



	 
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mousePressed(MouseEvent e) {
		deagleClip.play();
		
	}

	 
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}



	private class Target extends JLabel
	implements MouseListener
	{
		private Dimension origSize=null;	
		private Point dstLocation=null;
		private Point origLocation=null;
		private ImageIcon imageIcon=null;
		
	    public static final int UP=0;
	    public static final int DOWN=1;
	    public static final int LEFT=2;
	    public static final int RIGHT=3;
	    
		private int offsetDirection=UP;  //target飞出的方向
	    
	    //一下是衡量靶子运动的量
		private int gamePopUpSpeed=2*3;  //要求为偶数
	    private final int popUpSpeed=gamePopUpSpeed;  //要为偶数
	    private int waitingSpan=0;
	    
	    public final int WAITING=0;
	    private int waitingCounter=0;
	    public final int POPING_UP=1;
	    private int offset=1;
	    public final int HOLDING=4;
	    private int movingOffset=0;
	   public final int POPING_DOWN=2;
	    public final int HITTED=3;
	    
	    private int currentState=WAITING;
	    
	    private int totalLength=0;  //描述应该运动多远
	    
		public Target(int layer,int direction)
		{
			this.origSize=targetSizes[layer];
			this.imageIcon=imageIcons[layer];
			
			if(LEFT==direction)
			{
				switch(layer)
				{
				case 0:
				{
					this.origLocation=new Point(162,127);
					this.dstLocation=new Point(439,127);
					this.totalLength=439-162;
					break;
				}
				case 1:
				{
					this.origLocation=new Point(132,154);
					this.dstLocation=new Point(456,154);
					this.totalLength=456-132;
					break;
				}
				case 2:
				{
					this.origLocation=new Point(92,181);
					this.dstLocation=new Point(481,181);
					this.totalLength=481-92;
					break;
				}
				case 3:
				{
					this.origLocation=new Point(35,211);
					this.dstLocation=new Point(516,211);
					this.totalLength=516-35;
					break;
				}
				default:
				{
					this.origLocation=new Point(0,0);
					this.dstLocation=new Point(0,0);
					this.totalLength=0;
					break;
				}
				}
				this.offsetDirection=RIGHT;
			}
			if(RIGHT==direction)
			{
				switch(layer)
				{
				case 0:
				{
					this.dstLocation=new Point(162,127);
					this.origLocation=new Point(439,127);
					this.totalLength=439-162;
					break;
				}
				case 1:
				{
					this.dstLocation=new Point(132,154);
					this.origLocation=new Point(456,154);
					this.totalLength=456-132;
					break;
				}
				case 2:
				{
					this.dstLocation=new Point(92,181);
					this.origLocation=new Point(481,181);
					this.totalLength=481-92;
					break;
				}
				case 3:
				{
					this.dstLocation=new Point(35,211);
					this.origLocation=new Point(516,211);
					this.totalLength=516-35;
					break;
				}
				default:
				{
					this.origLocation=new Point(0,0);
					this.dstLocation=new Point(0,0);
					this.totalLength=0;
					break;
				}
				}
				this.offsetDirection=LEFT;
			}
			
			setLocation(origLocation);
			setIcon(imageIcon);
			setPreferredSize(origSize);
			setSize(origSize);
			this.setBackground(Color.WHITE);
			setVisible(false);
			
			addMouseListener(this);
		}
		
		public void initializeTarget()
		{
			setLocation(origLocation);
			setIcon(imageIcon);
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
			    	setIcon(new ImageIcon("pic/SubGame/shootingPractice/white.jpg"));
			    	setBorder(new LineBorder(Color.GREEN,2));
			    
			    	hitAndScore();
			    	repaint();
			    	
			    	deagleClip.play();
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
			setIcon(imageIcon);
			setBorder(null);
			offset=1;
			movingOffset=0;
		    setVisible(false);
			
			if(UP==offsetDirection)
			{
				setSize(origSize.width,1);
			    setLocation(origLocation.x,origLocation.y+origSize.height-1);
			    
			}
			
			if(DOWN==offsetDirection)
			{
				setSize(origSize.width,1);
			    setLocation(origLocation.x,origLocation.y);
			    
			}
			
			if(LEFT==offsetDirection)
			{
				setSize(1,origSize.height);
			    setLocation(origLocation.x+origSize.width-1,origLocation.y);
			    
			}
			
			if(RIGHT==offsetDirection)
			{
				setSize(1,origSize.height);
			    setLocation(origLocation.x,origLocation.y);
			    
			}
			
			setLocation(origLocation);
			setIcon(imageIcon);
			setPreferredSize(origSize);
			setSize(origSize);
		}
		
		public void selfProcess()
		{
			if(HITTED==currentState)
			{
				
				
				if((getSize().width>popUpSpeed)
					&&(getSize().height>popUpSpeed))
				{
					setSize(getSize().width-popUpSpeed,getSize().height-popUpSpeed);
					setLocation(getLocation().x+popUpSpeed/2,getLocation().y+popUpSpeed/2);
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
				
				
				if(LEFT==offsetDirection)
				{
					if(offset<=origSize.width-popUpSpeed)
					{
						offset+=popUpSpeed;
						setSize(offset,origSize.height);
						setLocation(origLocation.x+origSize.width-offset,origLocation.y);
					}
					else
					{
						currentState=HOLDING;
						offset=1;
						movingOffset=0;
						
						
					}
				}
				
				if(RIGHT==offsetDirection)
				{
					if(offset<=origSize.width-popUpSpeed)
					{
						offset+=popUpSpeed;
						setSize(offset,origSize.height);
						setLocation(origLocation.x,origLocation.y);
					}
					else
					{
						currentState=HOLDING;
						offset=1;
						movingOffset=0;
						
					}
				}
				
			}
			
			if(HOLDING==currentState)
			{
				if(RIGHT==offsetDirection)
				{
					movingOffset+=movingSpeed;
					setLocation(origLocation.x+movingOffset,getLocation().y);
					if(movingOffset>=totalLength)
					{
						currentState=POPING_DOWN;
						movingOffset=0;
					}
				}
				
				if(LEFT==offsetDirection)
				{
					movingOffset+=movingSpeed;
					setLocation(origLocation.x-movingOffset,getLocation().y);
					if(movingOffset>=totalLength)
					{
						currentState=POPING_DOWN;
						movingOffset=0;
					}
				}
			}
			
			if(POPING_DOWN==currentState)
			{
				if(LEFT==offsetDirection)
				{
					if(offset>=popUpSpeed)
					{
						offset-=popUpSpeed;
						setSize(offset,origSize.height);
						setLocation(dstLocation.x,dstLocation.y);
					}
					else
					{
						offset=10;
						restartTarget();
					}
				}
				
				if(RIGHT==offsetDirection)
				{
					if(offset>=popUpSpeed)
					{
						offset-=popUpSpeed;
						setSize(offset,origSize.height);
						setLocation(dstLocation.x+origSize.width-offset,dstLocation.y);
					}
					else
					{
						offset=1;
						restartTarget();
					}
				}
			}
			
			repaint();
		}
		
		
	}
}
