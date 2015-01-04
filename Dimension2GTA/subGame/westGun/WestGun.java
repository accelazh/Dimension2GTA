package subGame.westGun;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import basicConstruction.*;
import subGameSuper.*;
import gameDisplayProcessor.*;
import javax.swing.border.*;

public class WestGun extends SubGame 
implements ActionListener,MouseListener,MouseMotionListener
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=false;
	private static final boolean debug3=false;
	//data field
	private final int SECOND_COUNTING=0;
	private final int FIGHTING=1;
	private final int WIN_OR_DIE=2;
	private int currentState=0;  //游戏现在所处的状态
	private final int INTERVAL=10;
		
	Timer gameTimer=new Timer(INTERVAL,this);;
	private final int COUNT_SECOND=4;  //游戏开始的倒计时秒数+1
	private int countSecond=0;  //当前的倒计时秒数
	
	Timer AITimer=new Timer(10,this);  //负责电脑AI的计时器
	private int AI_SHOOT_TIME=100;  //CPU在这时开枪命中player头部  
	private int AIShootTimerCount=0; 
	
	private int AIaverageShootTime=25;
	
	//玩家设置
	SubGameSpark playerSpark=new SubGameSpark();
	SubGameSpark opponentSpark=new SubGameSpark();
	
	Rectangle headOfPlayer=new Rectangle();
	Rectangle headOfOpponent=new Rectangle();
	
	//player's gun
	GunOfWestGun gun=new GunOfWestGun();
	
	private final static int GUN_SPARK_COUNTING_SPAN=30;
	private boolean playerGunSparkCounting=false;
	private int playerGunSparkCounter=0;
	
	private boolean opponentGunSparkCounting=false;
	private int opponentGunSparkCounter=0;
	
	//picture Setting
	private Icon background=new ImageIcon("pic/SubGame/WestGun/background.jpg");
	private JLabel backgroundLabel=new JLabel();
	
	private Icon playerStart=new ImageIcon("pic/SubGame/WestGun/playerStart.jpg");
	private Icon playerFaceTo=new ImageIcon("pic/SubGame/WestGun/playerFaceTo.jpg");
	private Icon playerShoot=new ImageIcon("pic/SubGame/WestGun/playerShoot.jpg");
	private Icon playerDied=new ImageIcon("pic/SubGame/WestGun/playerDied.jpg");
	
	private JLabel playerLabel=new JLabel();
	
	private Icon opponentStart=new ImageIcon("pic/SubGame/WestGun/opponentStart.jpg");
	private Icon opponentFaceTo=new ImageIcon("pic/SubGame/WestGun/opponentFaceTo.jpg");
	private Icon opponentShoot=new ImageIcon("pic/SubGame/WestGun/opponentShoot.jpg");
	private Icon opponentDied=new ImageIcon("pic/SubGame/WestGun/opponentDied.jpg");
	
	private JLabel opponentLabel=new JLabel();
	
	//Panel Size
	private Dimension panelSize=new Dimension(600,300+24);
	private Dimension gamePanelSize=new Dimension(600,300);
	private Dimension messagePanelSize=new Dimension(600,24);
	
	//计时器
	private JLabel timeCounter=new JLabel();
	private int timeCounted=0;
	
	//winner
	private String winner="";
	private JButton finishButton=new JButton("OK");
	
	//message Panel
	private JLabel infoLabel=new JLabel();
	
	//要求游戏倒计时的时候，鼠标放在图片握抢的手的位置
	private Point gunPoint=new Point(72,180);   //表示player握抢的地方的点，游戏倒计时的时候要求鼠标在这个点的对应圆内
	private boolean isInGunZone=false;
	
	
	//methods
		
	public WestGun(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		try
		{
			AIaverageShootTime=Integer.parseInt(JOptionPane.showInputDialog("Please input the AI-average-shoot-time.(Default: "+AIaverageShootTime+")\n"+
					"Your rewards increase with the AI-average-shoot-time."));
		}
		catch(Exception ex)
		{
			;
		}
		
		//随机生成一个AI_SHOOT_TIME
		AI_SHOOT_TIME=1+(int)(2*AIaverageShootTime*Math.random());
		if (debug3)
		{
			System.out.println(AI_SHOOT_TIME);
		}
		setPreferredSize(panelSize);
		setSize(panelSize);
		
		backgroundLabel.setIcon(background);
		backgroundLabel.setPreferredSize( new Dimension(background.getIconWidth(),
				background.getIconHeight()));
		backgroundLabel.setSize( new Dimension(background.getIconWidth(),
				background.getIconHeight()));
		Point backgroundLocation=new Point((this.getWidth()-backgroundLabel.getWidth())/2,
				(this.getHeight()-backgroundLabel.getHeight())/2);
		backgroundLabel.setLocation(backgroundLocation);
		
		playerLabel.setIcon(playerStart);
		playerLabel.setPreferredSize( new Dimension(playerStart.getIconWidth(),
				playerStart.getIconHeight()));
		playerLabel.setSize( new Dimension(playerStart.getIconWidth(),
				playerStart.getIconHeight()));
		Point playerLabelLocation=new Point(backgroundLocation.x+50,backgroundLocation.y+60);
		playerLabel.setLocation(playerLabelLocation);
		
		opponentLabel.setIcon(opponentStart);
		opponentLabel.setPreferredSize( new Dimension(opponentStart.getIconWidth(),
				opponentStart.getIconHeight()));
		opponentLabel.setSize( new Dimension(opponentStart.getIconWidth(),
				opponentStart.getIconHeight()));
		Point opponentLabelLocation=new Point(backgroundLocation.x+backgroundLabel.getWidth()-opponentLabel.getWidth()-50,
				backgroundLocation.y+60);
		opponentLabel.setLocation(opponentLabelLocation);
		
		currentState=SECOND_COUNTING;
		countSecond=0;
				
		headOfPlayer.setSize(37,37);
		headOfPlayer.setLocation(playerLabel.getX()+37,playerLabel.getY()+37);
		
		headOfOpponent.setSize(37,37);
		headOfOpponent.setLocation(opponentLabel.getX()+opponentLabel.getWidth()-37-headOfOpponent.width,opponentLabel.getY()+37 );
		
		JPanel gamePanel=new JPanel();
		gamePanel.setLayout(null);
		gamePanel.add(playerSpark.getPicLabel());
		gamePanel.add(opponentSpark.getPicLabel());
		gamePanel.setPreferredSize(gamePanelSize);
		gamePanel.setSize(gamePanelSize);
		
		gamePanel.add(playerLabel);
		gamePanel.add(opponentLabel);
		gamePanel.add(backgroundLabel);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		finishButton.addActionListener(this);
		
		//message Panel
		timeCounter.setText("0000");
		timeCounter.setBorder(new LineBorder(Color.YELLOW,2));
		JPanel messagePanel=new JPanel(new BorderLayout());
		messagePanel.setPreferredSize(messagePanelSize);
		messagePanel.setSize(messagePanelSize);
		
		infoLabel.setHorizontalAlignment(JLabel.CENTER);
		messagePanel.add(infoLabel,BorderLayout.CENTER);
		messagePanel.add(timeCounter,BorderLayout.EAST);
	
		finishButton.setVisible(false);
		messagePanel.add(finishButton,BorderLayout.WEST);
		
	    setLayout(null);
	    gamePanel.setLocation(0,0);
	    add(gamePanel,BorderLayout.CENTER);
	    messagePanel.setLocation(0,gamePanel.getHeight());
	    messagePanel.setBorder(new LineBorder(Color.BLUE,2));
	    add(messagePanel,BorderLayout.SOUTH);
	    
	    //game prepared
	    setFrame();
	    
	    infoLabel.setText("Please locate your mouse where player's gun is held. Then game will start");
	    
	}
	
	

	
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == gameTimer) 
		{
			if(debug2)
			{
				System.out.println("timeCounted == "+timeCounted);
			}

			if (SECOND_COUNTING == currentState) 
			{
				if (COUNT_SECOND == (countSecond/100+1)) 
				{
					currentState = FIGHTING;
					countSecond = 0;
					playerLabel.setIcon(playerFaceTo);
					opponentLabel.setIcon(opponentFaceTo);
					backgroundLabel.setIcon(background);
					
										
				} 
				else 
				{
					countSecond++;
					backgroundLabel
							.setIcon(new ImageIcon(
									"pic/SubGame/WestGun/background"
											+ (countSecond/100+1) + ".jpg"));
				}
			}

			if (FIGHTING == currentState) 
			{
				timeCounted ++;
				
							
				String timeCountedStr=String.valueOf(timeCounted);
				if(debug)
				{
					System.out.println("timeCounted == "+timeCounted);
					System.out.println("length of timeCountedStr == "+timeCountedStr.length());
					for(int i=0;i<timeCountedStr.length();i++)
					{
						System.out.println("char at timeCountedStr["+i+"]: "+timeCountedStr.charAt(i));
					}
				}
		
				int dertLength=4-timeCountedStr.length();
				for(int i=0;i<dertLength;i++)
				{
					timeCountedStr="0"+timeCountedStr;
				}
				
				timeCounter.setText(timeCountedStr);
			}
		}
		
		if((e.getSource()==gameTimer)
			&&(FIGHTING==currentState))
		{
			AIShootTimerCount++;
			if(debug2)
			{
				System.out.println("AIShootTimerCount == "+AIShootTimerCount);
				System.out.println("AI_SHOOT_TIME == "+AI_SHOOT_TIME);
			}
			if(AI_SHOOT_TIME<=AIShootTimerCount)
			{
				if(debug)
				{
					System.out.println("opponent spark active");
				}
				opponentSpark.reStart(new Point((int)headOfPlayer.getCenterX(),
						(int)headOfPlayer.getCenterY()));
				currentState=WIN_OR_DIE;
				opponentGunSparkCounting=true;
				opponentGunSparkCounter=0;
				opponentLabel.setIcon(opponentShoot);
				playerLabel.setIcon(playerDied);
				if(debug)
				{
					System.out.println("opponent win");
				}
				gameTimer.stop();
				AITimer.stop();
				winner="opponent";
				winByOpponent();
				
                infoLabel.setText("<<--OK, men. You've got shoot!");
                finishButton.setVisible(true);
                
			}
		}
		
		if(true==playerGunSparkCounting)
		{
			playerGunSparkCounter++;
			if(GUN_SPARK_COUNTING_SPAN==playerGunSparkCounter)
			{
				playerGunSparkCounting=false;
				playerGunSparkCounter=0;
				playerLabel.setIcon(playerFaceTo);
			}
			else
			{
				playerLabel.setIcon(new ImageIcon
					("pic/SubGame/WestGun/playerShoot"+playerGunSparkCounter/5+".jpg"));
				
				
			}
			
			
		}
		
		if(true==opponentGunSparkCounting)
		{
			opponentGunSparkCounter++;
			if(GUN_SPARK_COUNTING_SPAN==opponentGunSparkCounter)
			{
				opponentGunSparkCounting=false;
				opponentGunSparkCounter=0;
				opponentLabel.setIcon(opponentFaceTo);
			}
			else
			{
				opponentLabel.setIcon(new ImageIcon
					("pic/SubGame/WestGun/opponentShoot"+opponentGunSparkCounter/5+".jpg"));
				
				
			}
			
			
		}
		if(e.getSource()==finishButton)
		{
			
			endOfGame();
		}
	}
	
	
	public void mouseClicked(MouseEvent e) {
	

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) 
	{
		if (FIGHTING == currentState) 
		{
			if (gun.isShootAble())
            {
				// ==perform shooting==
				
				// create a spark
				if(debug)
				{
					System.out.println("playerSpark active");
				}
				playerSpark.reStart(new Point(e.getX(),e.getY()));
				playerLabel.setIcon(playerShoot);
				playerGunSparkCounting=true;
				playerGunSparkCounter=0;
				repaint();
				

				// check if win
				if(headOfOpponent.contains(new Point(e.getX(),e.getY())))
				{
					if(debug)
					{
						System.out.println("playerSpark win");
					}
					opponentLabel.setIcon(opponentDied);
					currentState=WIN_OR_DIE;
					gameTimer.stop();
					AITimer.stop();
					gun.getTimer().stop();
					winner="player";
					winByPlayer();
                    
					infoLabel.setText("<<--WELL DOWN! You win!");
                    finishButton.setVisible(true);
				}
				
			}
		
		}

	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) 
	{
		if(SECOND_COUNTING==currentState)
		{
			//将鼠标的位置限定在某处
			if(!isInGunZone)
			{
				if(gunPoint.distance(e.getPoint())<40)
				{
					isInGunZone=true;
					countSecond=0;
					infoLabel.setText("Okey, keep this until counting down to zero, then shoot!!");
					gameTimer.start();
					
				}
			}
			else
			{
				if(gunPoint.distance(e.getPoint())>=40)
				{
					infoLabel.setText("Please locate your mouse where player's gun is held. Then game will start");
					isInGunZone=false;
					gameTimer.stop();
				}
			}
		}

	}

	public void mouseMoved(MouseEvent e) 
	{
		if(SECOND_COUNTING==currentState)
		{
			//将鼠标的位置限定在某处
			if(!isInGunZone)
			{
				if(gunPoint.distance(e.getPoint())<40)
				{
					isInGunZone=true;
					countSecond=0;
					infoLabel.setText("Okey, keep this until counting down to zero, then shoot!!");
					gameTimer.start();
					
				}
			}
			else
			{
				if(gunPoint.distance(e.getPoint())>=40)
				{
					infoLabel.setText("Please locate your mouse where player's gun is held. Then game will start");
					isInGunZone=false;
					gameTimer.stop();
				}
			}
		}

	}
	
	//对player的奖励会随着AIaverageShootTime的升高而升高
	public void winByPlayer()
	{
		if(null!=getOpponent())
		{
			getPlayer().setMoney(getPlayer().getMoney()+200+(int)(200*Math.random()*(25.0+5)/(AIaverageShootTime+5)));
			
			getOpponent().setAlive(false);
		}
		
		
	}
	public void winByOpponent()
	{
		if(null!=getPlayer())
		{
			getPlayer().setAlive(false);
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
	
	public int setAIaverageShootTime()
	{
		return AIaverageShootTime;
	}
	
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		WestGun westGun=new WestGun(null,null,null);
        
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(westGun,BorderLayout.CENTER);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}

