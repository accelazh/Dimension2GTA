package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import subGameSuper.*;
import gameDisplayProcessor.*;
import java.applet.*;
import java.net.*;
import utilities.*;

public class ShootingPractice extends SubGame
implements ActionListener, MouseListener
{
	
	//for debug
	private final static boolean debug=false;
	
	private JPanel mainPanel=new JPanel();
	private final int mainPanelSize=400;
	private JPanel controlPanel=null;
	
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
	
	private Target[][] targets=new Target[5][5];
	
	//游戏当前的状态
	private final int STOPPED=0;
	private final int STARTED=1;
	private int gameState=STOPPED;

	private JLabel timeOverLabel=new JLabel("Time Over!");
	
	//用于制造开枪的闪光
	private JLabel flash=new JLabel();
	private final int flashVisibleSpan=(int)(0*1000/TIMER_INTERVAL);
	private int flashVisibleCounter=0;
	
	//对靶子的运动周期的控制
	private int maxTargetCycle=5*1000/TIMER_INTERVAL;
	private int changingMaxTargetCycle=maxTargetCycle;
	private int gamePopUpSpeed=2*3;  //要求为偶数
	private int holdingAverageSpan=(int)(1*1000/TIMER_INTERVAL);  //用于控制靶子停留的时间
	private int roundTime=40; 
	
	//sound effect
	private SoundPlayer deagleClip=new SoundPlayer(ShootingPractice.class.getResource("deagle-1.wav"));
	
	public ShootingPractice(Player player,Human opponent,MainGameWindow master)
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
		
		mainPanel.setPreferredSize(new Dimension(mainPanelSize,mainPanelSize));
		mainPanel.setSize(mainPanel.getPreferredSize());
		mainPanel.setBackground(new Color(0,128,0));
		
		flash.setBackground(new Color(255,255,179));
		flash.setPreferredSize(new Dimension(mainPanelSize,mainPanelSize));
		flash.setIcon(new ImageIcon("pic/SubGame/shootingPractice/flash.jpg"));
		flash.setSize(new Dimension(mainPanelSize,mainPanelSize));
		flash.setLocation(0,0);
		flash.setVisible(false);
		mainPanel.add(flash);
		
		timeOverLabel.setFont(new Font("Times",Font.BOLD,36));
		timeOverLabel.setForeground(Color.YELLOW);
		timeOverLabel.setBorder(new LineBorder(Color.RED,4));
		timeOverLabel.setVisible(false);
		timeOverLabel.setSize(timeOverLabel.getPreferredSize());
		timeOverLabel.setLocation((mainPanelSize-timeOverLabel.getWidth())/2,
				(mainPanelSize-timeOverLabel.getHeight())/2);
		mainPanel.add(timeOverLabel);
				
		for(int i=0;i<5;i++)
		{
			for(int j=0;j<5;j++)
			{
				targets[i][j]=new Target(new Point(i,j));
				mainPanel.add(targets[i][j]);
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
		
		//加入监听器
		startButton.addActionListener(this);
		cancleButton.addActionListener(this);
		this.addMouseListener(this); 
		
		setFrame();
		
		try
		{
			double seconds=Double.parseDouble(JOptionPane.showInputDialog
			("Please input the average seconds(default:"+(holdingAverageSpan*TIMER_INTERVAL/1000) +") of the time when targets stop moving."));
			holdingAverageSpan=(int)(seconds*1000/TIMER_INTERVAL);			
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
				(screenSize.height-getFrame().getHeight())/2-50);
		
		getFrame().setResizable(false);
		getFrame().setVisible(true);
	}

	 
	public void actionPerformed(ActionEvent e)
	{
		
		
		if(STARTED==gameState)
		{
			if(flash.isVisible())
			{
				if(flashVisibleCounter<=flashVisibleSpan)
				{
					flashVisibleCounter++;
				}
				else
				{
					flashVisibleCounter=0;
					flash.setVisible(false);
				}
			}
			
			if(e.getSource()==timer)
			{
				for(int i=0;i<targets.length;i++)
				{
					for(int j=0;j<targets[0].length;j++)
					{
						targets[i][j].selfProcess();
						
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
					if(0>=time)
					{
						gameState=STOPPED;
						
						timeOverLabel.setVisible(true);
						
						for(int i=0;i<targets.length;i++)
						{
							for(int j=0;j<targets[0].length;j++)
							{
								targets[i][j].initializeTarget();
								
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
					for(int j=0;j<targets[0].length;j++)
					{
						targets[i][j].restartTarget();
						
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
		AddingGunSkill addingGunSkill=new AddingGunSkill(this,(int)(hitTimes*1.0/20*3));
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
		deagleClip.play();
	}

	 
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	private class Target extends JLabel
	implements MouseListener
	{
		private Point basePoint=null;
		private final static int targetSize=40;
	    private Point order=new Point(0,0);  //自己的序号,在数组中的位置
	    		
	    
	    
	    //一下是衡量靶子运动的量
	    private final int popUpSpeed=gamePopUpSpeed;  //要为偶数
	    private int waitingSpan=0;
	    
	    public final int WAITING=0;
	    private int waitingCounter=0;
	    public final int POPING_UP=1;
	    private int offset=1;
	    public final int HOLDING=4;
	    private int holdingCounter=0;
	    private int holdingSpan=0;
	    public final int POPING_DOWN=2;
	    public final int HITTED=3;
	    
	    private int currentState=WAITING;
	    
	    
		public Target(Point order)
		{
			this.order=order;
			
			int cellSize=mainPanelSize/5;
			Point cellLocation=new Point(order.x*cellSize,order.y*cellSize);
			int offsetX=(cellSize-targetSize)/2;
			int offsetY=(cellSize-targetSize)/4;
			basePoint=new Point(cellLocation.x+offsetX,cellLocation.y+offsetY);
			
			setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
			setSize(targetSize,targetSize);
			setBorder(new LineBorder(Color.RED,2));
			this.setBackground(Color.WHITE);
			
			setLocation(basePoint);
		
		    addMouseListener(this);
		}
		
		public void initializeTarget()
		{
			setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
			setSize(targetSize,targetSize);
			setBorder(new LineBorder(Color.RED,2));
			this.setBackground(Color.WHITE);
			
			setLocation(basePoint);
			setVisible(true);
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
				flash.setVisible(false);
				flash.repaint();
							
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
			
			setSize(targetSize,1);
			setLocation(basePoint.x,basePoint.y+targetSize-1);
			setBorder(new LineBorder(Color.RED));
			
			int rand=(int)(Math.random()*6);
			switch(rand)
			{
			case 0:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
				break;
			}
			case 1:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim2.jpg"));
				break;
			}
			case 2:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim3.jpg"));
				break;
			}
			case 3:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
				break;
			}
			case 4:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
				break;
			}
			case 5:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
				break;
			}
			default:
			{
				setIcon(new ImageIcon("pic/SubGame/shootingPractice/aim.jpg"));
				break;
			
			}
			}
			
			offset=1;
			
			setVisible(false);
		}
		
		public void selfProcess()
		{
			if(HITTED==currentState)
			{
				if((getSize().width>popUpSpeed)
					&&(getSize().height>popUpSpeed))
				{
					setSize(getSize().width-popUpSpeed/2,getSize().height-popUpSpeed/2);
					setLocation(getLocation().x+popUpSpeed/4,getLocation().y+popUpSpeed/4);
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
				if(offset<=targetSize-1)
				{
					offset+=popUpSpeed;
					setSize(getSize().width,offset);
					setLocation(basePoint.x,basePoint.y+targetSize-offset);
				}
				else
				{
					currentState=HOLDING;
					holdingCounter=0;
					holdingSpan=(int)(Math.random()*2*holdingAverageSpan);
				}
			}
			
			if(HOLDING==currentState)
			{
				if(holdingCounter<=holdingSpan)
				{
					holdingCounter++;
				}
				else
				{
					currentState=POPING_DOWN;
				}
			}
			
			if(POPING_DOWN==currentState)
			{
				if(offset>=2)
				{
					offset-=popUpSpeed;
					setSize(getSize().width,offset);
					setLocation(basePoint.x,basePoint.y+targetSize-offset);
				}
				else
				{
					restartTarget();
				}
			}
			
			repaint();
		}
		
		
	}

}


/*
 * 击中靶子后应有的反应，背景图片，整体的靶子阵安排，cancle后的加点数GUI
 * 游戏的主要控制参数移到开头,击中的时候的flash
 */
