package subGame.dancingFight;

import javax.swing.*;
import javax.swing.border.LineBorder;

import subGame.longFan.LongFan;
import subGame.longFan.RandomNumberPanel;
import basicConstruction.*;
import gameDisplayProcessor.*;

import java.awt.*;
import java.awt.event.*;

import gameDisplayProcessor.*;
import subGameSuper.*;


public class DancingFight extends SubGame implements KeyListener,ActionListener
{
	
	

	//for debug
	private static boolean debug14=ControlSetting.debug14;
	private static boolean debug15=ControlSetting.debug15;
	//final
	private final int DEFAULT = -1;
	private final int PLAYER_TURN = 0;
	private final int OPPONENT_TURN = 1;
	private final int WIN_OR_DIE = 2;
	final int PLAYER_WIN = 3;
	final int OPPONENT_WIN = 4;
	 
	//finite statement machine
	private int currentState;
	//user settings
	private int playerHealth;
	private final int playerMaxHealth = 5000;

	private int opponentHealth;
	private final int opponentMaxHealth = 5000;
	
	private int HEALTH_STEP=100;                            
	
    private int winner;
    private JLabel whosRound=new JLabel();
    
	// GUI date field
	
	private JLabel playerPortait = new JLabel();
	private Icon playerPortaitImage = new ImageIcon(
			"pic/SubGame/DancingFight/playerPortait.jpg");  //100*200
	private JLabel opponentPortait = new JLabel();
	private Icon opponentPortaitImage = new ImageIcon(
			"pic/SubGame/DancingFight/opponentPortait.jpg");

	private JLabel playerHealthBar = new JLabel();
	private JLabel opponentHealthBar = new JLabel();
	private Icon healthBarImage = new ImageIcon(
			"pic/SubGame/DancingFight/healthBar.jpg");

	private Dimension idealSize; //600*300
	
	private final int rectSize=24;
	
	private JLabel playerRect1=new JLabel();
	private JLabel playerRect2=new JLabel();
	private Icon playerRectImage=new ImageIcon("pic/SubGame/DancingFight/playerRectImage.jpg");//rectSize*rectSize
	
	private JLabel opponentRect1=new JLabel();
	private JLabel opponentRect2=new JLabel();
	private Icon opponentRectImage=new ImageIcon("pic/SubGame/DancingFight/opponentRectImage.jpg");
	
	private JLabel countingDown=new JLabel();   //负责每回合的倒计时
	
	private JButton finishButton =new JButton("OK");   //游戏结束时，按这个按钮才会退出
	//data field processing game data
	private DanceRect[] danceRectArray;
    private Timer turnTimer;
    private final int TURN_TIMER_INTERVAL=1000;
	private int turnCount;
	private int TURN_COUNT_SPAN=11;
	
	
	//controlling the moving of DanceRects
	private Timer movingTimer;   //control the moving of dancingRects
	private final int INTERVAL_OF_MOVING_TIMER=10;
	private int STEP=400*INTERVAL_OF_MOVING_TIMER/1000;   //velocity == 400 pixel/s
	
	//attack interval
	private Timer attackIntervalTimer1;
	private int ATTACK_INTERVAL_TIMER_INTERVAL=120;
	private final int ATTACK_INTERVAL_SPAN=1;
	private int attackIntervalCounter1;
	
	private Timer attackIntervalTimer2;
	private int attackIntervalCounter2;
	
	private boolean attackable1;
	private boolean attackable2;
	
	//AI field
	private Timer AIAttackTimer;
	private int AI_ATTACK_TIMER_INTERVAL=500;
		
	private Timer AIDefenceTimer;
	private final int AI_DEFENCE_TIMER_INTERVAL=100;
	private double AI_SUCCESSFUL_DEFENCE_PROBABILITY=0.8;
	
	//这一部分给游戏一些装饰
	private int playerMovementState=-1;  //-1表示没有动作
	private int opponentMovementState=1; 
	
	private ImageIcon playerMovementDefaultImageIcon=new ImageIcon
	("pic/SubGame/DancingFight/movement/default.jpg");
	private ImageIcon[] playerMovementImageIcons={
			new ImageIcon("pic/SubGame/DancingFight/movement/UP_UP.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/UP_DOWN.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/UP_LEFT.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/UP_RIGHT.jpg"),
			
			new ImageIcon("pic/SubGame/DancingFight/movement/DOWN_UP.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/DOWN_DOWN.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/DOWN_LEFT.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/DOWN_RIGHT.jpg")
			
	};
	
	private ImageIcon opponentMovementDefaultImageIcon=new ImageIcon
	("pic/SubGame/DancingFight/movement/O_default.jpg");
	private ImageIcon[] opponentMovementImageIcons={
			new ImageIcon("pic/SubGame/DancingFight/movement/O_UP_UP.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_UP_DOWN.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_UP_LEFT.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_UP_RIGHT.jpg"),
			
			new ImageIcon("pic/SubGame/DancingFight/movement/O_DOWN_UP.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_DOWN_DOWN.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_DOWN_LEFT.jpg"),
			new ImageIcon("pic/SubGame/DancingFight/movement/O_DOWN_RIGHT.jpg")
			
	};
	//methods 
		
	public DancingFight(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		//user setting
		playerHealth=playerMaxHealth;
   	    opponentHealth=opponentMaxHealth;
   	    
   	    
   	    winner=DEFAULT;
		
   	    //game setting
   	    danceRectArray=new DanceRect[10];
   	    for(int i=0;i<10;i++)
   	    {
   	    	danceRectArray[i]=null;
   	    }
   	    
   	    turnTimer=new Timer(TURN_TIMER_INTERVAL,this);
   	    turnTimer.start();
   	    turnCount=0;
   	    
   	    //controlling the moving DanceRects
   	    movingTimer=new Timer(INTERVAL_OF_MOVING_TIMER,this);
   	    movingTimer.start();
   	    
   	    //attack interval
   	    attackIntervalTimer1=new Timer(ATTACK_INTERVAL_TIMER_INTERVAL,this);
   	    attackIntervalTimer2=new Timer(ATTACK_INTERVAL_TIMER_INTERVAL,this);
 	    attackIntervalCounter1=0;
 	    attackIntervalCounter2=0;
   	    attackable1=true;
   	    attackable2=true;
 	    
   	    //AI setting
   	    AIAttackTimer=new Timer(AI_ATTACK_TIMER_INTERVAL,this);
   	    AIDefenceTimer=new Timer(AI_DEFENCE_TIMER_INTERVAL,this);
		//GUI designs
		idealSize=new Dimension(600,300);
   	    setPreferredSize(idealSize);
   	    setSize(idealSize);
   	    setBorder(new LineBorder(Color.BLUE,2));
   	    setLayout(null);
   	    
   	    playerPortait.setIcon(playerPortaitImage);
   	    playerPortait.setPreferredSize(new Dimension(playerPortaitImage.getIconWidth(),
   	    playerPortaitImage.getIconHeight()));
   	    playerPortait.setSize(playerPortaitImage.getIconWidth(),
   	    playerPortaitImage.getIconHeight());
   	       	    
   	    opponentPortait.setIcon(opponentPortaitImage);   	    
   	    opponentPortait.setPreferredSize(new Dimension(opponentPortaitImage.getIconWidth(),
   	    opponentPortaitImage.getIconHeight()));
   	    opponentPortait.setSize(opponentPortaitImage.getIconWidth(),
   	    opponentPortaitImage.getIconHeight());
   	    
   	 
   	    playerHealthBar.setIcon(healthBarImage);
   	    playerHealthBar.setSize(playerPortait.getWidth(),10);
   	    playerHealthBar.setBackground(Color.GREEN);
   	    playerHealthBar.setHorizontalAlignment(JLabel.CENTER);
   	    playerHealthBar.setVerticalAlignment(JLabel.CENTER);
   	    playerHealthBar.setHorizontalAlignment(JLabel.CENTER);
   	    playerHealthBar.setVerticalTextPosition(JLabel.CENTER);
	    
   	    
	   
	    opponentHealthBar.setIcon(healthBarImage);
   	    opponentHealthBar.setSize(opponentPortait.getWidth(),10);
   	    opponentHealthBar.setBackground(Color.GREEN);
   	    opponentHealthBar.setHorizontalAlignment(JLabel.CENTER);
   	    opponentHealthBar.setVerticalAlignment(JLabel.CENTER);
   	    opponentHealthBar.setHorizontalAlignment(JLabel.CENTER);
   	    opponentHealthBar.setVerticalTextPosition(JLabel.CENTER);
   	    
   	     	    
   	    playerRect1.setSize(rectSize,rectSize);
   	    playerRect1.setBorder(new LineBorder(Color.YELLOW,2));
   	    playerRect2.setSize(rectSize,rectSize);
   	    playerRect2.setBorder(new LineBorder(Color.YELLOW,2));
   	    playerRect1.setIcon(playerRectImage);
   	    playerRect2.setIcon(playerRectImage);
   	    
   	    opponentRect1.setSize(rectSize,rectSize);
   	    opponentRect1.setBorder(new LineBorder(Color.YELLOW,2));
   	    opponentRect2.setSize(rectSize,rectSize);
   	    opponentRect2.setBorder(new LineBorder(Color.YELLOW,2));
   	    opponentRect1.setIcon(opponentRectImage);
	    opponentRect2.setIcon(opponentRectImage);
	    
      	whosRound.setPreferredSize(new Dimension(70,30));
      	whosRound.setHorizontalAlignment(JLabel.CENTER);
      	whosRound.setVerticalAlignment(JLabel.CENTER);
      	whosRound.setSize(whosRound.getPreferredSize());
      	whosRound.setBorder(new LineBorder(Color.BLUE,2));
      	whosRound.setFont(new Font("Times",Font.BOLD,12));
      	whosRound.setForeground(Color.RED);
   	    
      	countingDown.setPreferredSize(new Dimension(30,30));
      	countingDown.setSize(countingDown.getPreferredSize());
      	countingDown.setHorizontalAlignment(JLabel.CENTER);
      	countingDown.setVerticalAlignment(JLabel.CENTER);
      	countingDown.setBorder(new LineBorder(Color.BLUE,2));
      	countingDown.setFont(new Font("Times",Font.BOLD,18));
      	countingDown.setForeground(Color.RED);
      	
      	finishButton.setVisible(false);
      	finishButton.setSize(finishButton.getPreferredSize());
      	
   	    //set GUI component's location and add them
      	finishButton.setLocation((idealSize.width-finishButton.getWidth())/2,
   	    		(idealSize.height-finishButton.getHeight())/2);
   	    add(finishButton);
   	         	
   	    playerPortait.setLocation(50,30);
   	    add(playerPortait);
   	    
   	    opponentPortait.setLocation(idealSize.width-50-opponentPortait.getWidth(),30);
   	    add(opponentPortait);
   	    
   	    playerHealthBar.setLocation(playerPortait.getX(),
   	    playerPortait.getY()+playerPortait.getHeight());
   	    add(playerHealthBar);
   	    
   	    opponentHealthBar.setLocation(opponentPortait.getX(),
   	    opponentPortait.getY()+opponentPortait.getHeight());
   	    add(opponentHealthBar);
   	    
   	    whosRound.setLocation((idealSize.width-whosRound.getWidth())/2,
   	    		playerPortait.getY()+20);
   	    add(whosRound);
   	    
   	    playerRect1.setLocation(playerPortait.getX()+playerPortait.getWidth()+5,
   	    		whosRound.getY()+whosRound.getHeight()+30);
   	    playerRect2.setLocation(playerRect1.getX(),playerRect1.getY()+rectSize+10);
   	    opponentRect1.setLocation(opponentPortait.getX()-rectSize-5,whosRound.getY()+whosRound.getHeight()+30);
	    opponentRect2.setLocation(opponentRect1.getX(),opponentRect1.getY()+rectSize+10);
   	    add(playerRect1);
   	    add(playerRect2);
   	    add(opponentRect1);
   	    add(opponentRect2);
	    
   	    countingDown.setLocation((idealSize.width-countingDown.getWidth())/2,
   	    		playerRect2.getY()+playerRect2.getHeight()+20);
   	    add(countingDown);
   	    
   	    
   	    //add listeners and set Focus
   	    addKeyListener(this);
   	    finishButton.addActionListener(this);
   	    setFocusable(true);
   	    
   	    //game starts
   	
        try 
		{
			int step=Integer.parseInt(JOptionPane.showInputDialog("Please input a velocity. (default: "+getVelocity()+")\n"+
					"higher one contributes to your rewards more."));
			setVelocity(step);
		}
		catch(Exception ex)
		{
			;
		}
		
		try 
		{
			setAIAttackInterval(Integer.parseInt(JOptionPane.showInputDialog
					("Please input AI attack interval. " +
							"(default: "+getAIAttackInterval()+")\n"+
							"higher one contributes to your rewards more.")));
			
		}
		catch(Exception ex)
		{
			;
		}
		
		setFrame();
   	    
   	    if(Math.random()<0.5)
	    {
	    	currentState=PLAYER_TURN;
	    	switchToPlayerTurn();
	    }
	    else
	    {
	    	currentState=OPPONENT_TURN;
	       	switchToOpponentTurn();
	        
	    }
   	   
	}
	//currentState is not initialized yet
	
	//以下两个方法对player和CPU均可用
	private void attackKeyPressed(int direction)
	{ 
		addDanceRect(direction);
		
	}
	
	private void defenceKeyPressed(int direction)
	{
		if(debug15)
		{
			System.out.println("==== in method defenceKeyPressed ==");
		    System.out.println("direction == "+direction);
		}
		
		for(int i=0;i<danceRectArray.length;i++)
		{
			if(danceRectArray[i]!=null)
			{
				if(debug15)
				{
					System.out.println("danceRectArray["+i+"] is a DanceRect.");
					System.out.println("defendable == "+danceRectArray[i].defendable);
					System.out.println("its direction == "+direction);
				}
				if((danceRectArray[i].defendable)
						&&(danceRectArray[i].getDirection()==direction))
				{
					if(debug15)
					{
						System.out.println("beDefended run");
					}
					danceRectArray[i].beDefended();
				}
			}
		}
	}
	
	private void setPlayerHealth(int health)
	   {
	   	    if(health>=0)
	   	    {
	   	        playerHealth=health;
	   	        playerHealthBar.setSize((int)(playerPortait.getWidth()*playerHealth*1.0/playerMaxHealth),
	   	            playerHealthBar.getHeight());
	   	        repaint();
	   	    }
	   	    else
	   	    {
	   	        playerHealth=0;
	   	        playerHealthBar.setSize((int)(playerPortait.getWidth()*playerHealth*1.0/playerMaxHealth),
	   	            playerHealthBar.getHeight());
	   	        repaint();	
	   	    
	   	    }
	   	    
	   }
	   
	private void setOpponentHealth(int health)
	   {
	   	 if(health>=0)
	   	 {  
	   	 	opponentHealth=health;
	   	    opponentHealthBar.setSize((int)(opponentPortait.getWidth()*opponentHealth*1.0/opponentMaxHealth),
	   	        opponentHealthBar.getHeight());
	   	    repaint();
	   	 }
	   	 else
	   	 {
	   	 	opponentHealth=0;
	   	    opponentHealthBar.setSize((int)(opponentPortait.getWidth()*opponentHealth*1.0/opponentMaxHealth),
	   	        opponentHealthBar.getHeight());
	   	    repaint();
	   	 }
	   }
	
	private void hittedByTheOpposite()
	{
		if(PLAYER_TURN==currentState)
		{
		    setOpponentHealth(opponentHealth-HEALTH_STEP);	
		}
		else
		{
			if(OPPONENT_TURN==currentState)
			{
				setPlayerHealth(playerHealth-HEALTH_STEP);
			}
		}
	}
	
	private void addDanceRect(int direction)
	{
		//create newDanceRect
		DanceRect newDanceRect=new DanceRect(direction,currentState,this);
		//save newDanceRect
		int i;        // to search for the end of the array
		for(i=0;(i<danceRectArray.length)&&(danceRectArray[i]!=null);i++)
			;
		if(i<danceRectArray.length)
		{
			danceRectArray[i]=newDanceRect;
			
		}
		else
		{
			DanceRect[] newDanceRectArray=new DanceRect[danceRectArray.length*2];
			for(int j=0;j<newDanceRectArray.length;j++)
			{
				newDanceRectArray[j]=null;
			}
			System.arraycopy(danceRectArray,0,newDanceRectArray,0,danceRectArray.length);
			
			newDanceRectArray[danceRectArray.length]=newDanceRect;
			danceRectArray=newDanceRectArray;
		}
	}
	
	private void removeDanceRect(DanceRect danceRect)
	{
		for(int i=0;i<danceRectArray.length;i++)
		{
			if(danceRect==danceRectArray[i])
			{
				danceRectArray[i]=null;
			}
		}
		
		remove(danceRect.getDancingLabel());
	}
	
	private void switchToPlayerTurn()
	{
		currentState=PLAYER_TURN;
		whosRound.setText("P Round");
		AIAttackTimer.stop();
		AIDefenceTimer.start();
		
		attackable1=true;
		attackable2=true;
		attackIntervalCounter1=0;
		attackIntervalCounter2=0;
		attackIntervalTimer1.stop();
		attackIntervalTimer2.stop();
		
		
		for(int i=0;i<danceRectArray.length;i++)
		{
			if(danceRectArray[i]!=null)
			{
				
				remove(danceRectArray[i].getDancingLabel());
				danceRectArray[i]=null;
			}
		}
		repaint();
		
	}
	
	private void switchToOpponentTurn()
	{
		currentState=OPPONENT_TURN;
		whosRound.setText("O Round");
		AIAttackTimer.start();
		AIDefenceTimer.stop();
		
		attackable1=true;
		attackable2=true;
		attackIntervalCounter1=0;
		attackIntervalCounter2=0;
		attackIntervalTimer1.stop();
		attackIntervalTimer2.stop();
		
		for(int i=0;i<danceRectArray.length;i++)
		{
			if(danceRectArray[i]!=null)
			{
				
				remove(danceRectArray[i].getDancingLabel());
				danceRectArray[i]=null;
			}
		}
		repaint();
		
	}
	
	private void switchToWinOrDie()
	{
		currentState=WIN_OR_DIE;
		turnTimer.stop();
		movingTimer.stop();
		
		AIAttackTimer.stop();
		AIDefenceTimer.stop();
		
		attackIntervalTimer1.stop();
		attackIntervalTimer2.stop();
		
		for(int i=0;i<danceRectArray.length;i++)
		{
			if(danceRectArray[i]!=null)
			{
				
				remove(danceRectArray[i].getDancingLabel());
				danceRectArray[i]=null;
			}
		}
		repaint();
		
		if(playerHealth<=0)
		{
			winner=OPPONENT_WIN;
			whosRound.setText("P LOSE");
			winByOpponent();
		}
		if(opponentHealth<=0)
		{
			winner=PLAYER_WIN;
			whosRound.setText("P WIN");
			winByPlayer();
		}
		
		
		finishButton.setVisible(true);	
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(OPPONENT_TURN==currentState)
		{
			if (e.getKeyCode() == ControlSetting.getPlayerMoveUp())
			{
				
					defenceKeyPressed(DanceRect.UP_UP);
			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveDown()) 
			{
				
					defenceKeyPressed(DanceRect.UP_DOWN);
				 
			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveLeft())
			{
				   defenceKeyPressed(DanceRect.UP_LEFT);
				
			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveRight()) 
			{
				   defenceKeyPressed(DanceRect.UP_RIGHT);
				
			}

			if (e.getKeyCode() == ControlSetting.getSpecialUp()) 
			{
					defenceKeyPressed(DanceRect.DOWN_UP);
			}
			if (e.getKeyCode() == ControlSetting.getSpecialDown()) 
			{
					defenceKeyPressed(DanceRect.DOWN_DOWN);
					
			}	
			if (e.getKeyCode() == ControlSetting.getSpecialLeft())
			{
				
					defenceKeyPressed(DanceRect.DOWN_LEFT);
			}
			if (e.getKeyCode() == ControlSetting.getSpecialRight()) 
			{
				
					defenceKeyPressed(DanceRect.DOWN_RIGHT);
					
			}
		}
		
		if (e.getKeyCode() == ControlSetting.getPlayerMoveUp())
		{
			playerPortait.setIcon(playerMovementImageIcons[0]);
			playerMovementState=0;
				
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveDown()) 
		{
			playerPortait.setIcon(playerMovementImageIcons[1]);
			playerMovementState=1;
			 
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveLeft())
		{
			 
			playerPortait.setIcon(playerMovementImageIcons[2]);
			playerMovementState=2;
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveRight()) 
		{
			 
			playerPortait.setIcon(playerMovementImageIcons[3]);
			playerMovementState=3;
		}

		if (e.getKeyCode() == ControlSetting.getSpecialUp()) 
		{
			playerPortait.setIcon(playerMovementImageIcons[4]);
			playerMovementState=4;
		}
		if (e.getKeyCode() == ControlSetting.getSpecialDown()) 
		{
			playerPortait.setIcon(playerMovementImageIcons[5]);
			playerMovementState=5;
				
		}	
		if (e.getKeyCode() == ControlSetting.getSpecialLeft())
		{
			playerPortait.setIcon(playerMovementImageIcons[6]);
			playerMovementState=6;
				
		}
		if (e.getKeyCode() == ControlSetting.getSpecialRight()) 
		{
			playerPortait.setIcon(playerMovementImageIcons[7]);
			playerMovementState=7;
				
				
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(debug14)
		{
			System.out.println("KeyRelealsed : "+e.getKeyChar());
		    System.out.println("currentState == "+currentState);
		    System.out.println("attackable1 == "+attackable1);
		    System.out.println("attackable2 == "+attackable2);
		}
		
		if (PLAYER_TURN == currentState) 
		{
			
			if (e.getKeyCode() == ControlSetting.getPlayerMoveUp())
			{
				if(attackable1)
				{
					attackKeyPressed(DanceRect.UP_UP);
					attackIntervalCounter1=0;
					attackable1=false;
					attackIntervalTimer1.start();
					
				}

			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveDown()) 
			{
				if(attackable1)
				{
					attackKeyPressed(DanceRect.UP_DOWN);
				   	attackIntervalCounter1=0;
				    attackable1=false;
				    attackIntervalTimer1.start();
			
				}
			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveLeft())
			{
				if(attackable1)
				{ 
				   attackKeyPressed(DanceRect.UP_LEFT);
				   attackIntervalCounter1=0;
				   attackable1=false;
				   attackIntervalTimer1.start();
				}
			}
			if (e.getKeyCode() == ControlSetting.getPlayerMoveRight()) 
			{
				
				if (attackable1)
				{
					attackKeyPressed(DanceRect.UP_RIGHT);
					attackIntervalCounter1 = 0;
					attackable1 = false;
					attackIntervalTimer1.start();
				}
			}

			if (e.getKeyCode() == ControlSetting.getSpecialUp()) 
			{
				if (attackable2)
				{
					attackKeyPressed(DanceRect.DOWN_UP);
					attackIntervalCounter2 = 0;
					attackable2 = false;
					attackIntervalTimer2.start();
				}

			}
			if (e.getKeyCode() == ControlSetting.getSpecialDown()) 
			{
				if (attackable2)
				{
					attackKeyPressed(DanceRect.DOWN_DOWN);
					attackIntervalCounter2 = 0;
					attackable2 = false;
					attackIntervalTimer2.start();
				}
			}
			if (e.getKeyCode() == ControlSetting.getSpecialLeft())
			{
				if (attackable2)
				{
					attackKeyPressed(DanceRect.DOWN_LEFT);
					attackIntervalCounter2 = 0;
					attackable2 = false;
					attackIntervalTimer2.start();
				}

			}
			if (e.getKeyCode() == ControlSetting.getSpecialRight()) 
			{
				if (attackable2)
				{
					attackKeyPressed(DanceRect.DOWN_RIGHT);
					attackIntervalCounter2 = 0;
					attackable2 = false;
					attackIntervalTimer2.start();
				}
			}
		}
		
		if (e.getKeyCode() == ControlSetting.getPlayerMoveUp())
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
				
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveDown()) 
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
			 
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveLeft())
		{
			 
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
		}
		if (e.getKeyCode() == ControlSetting.getPlayerMoveRight()) 
		{
			 
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
		}

		if (e.getKeyCode() == ControlSetting.getSpecialUp()) 
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
		}
		if (e.getKeyCode() == ControlSetting.getSpecialDown()) 
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
				
		}	
		if (e.getKeyCode() == ControlSetting.getSpecialLeft())
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
				
		}
		if (e.getKeyCode() == ControlSetting.getSpecialRight()) 
		{
			playerPortait.setIcon(playerMovementDefaultImageIcon);
			playerMovementState=-1;
				
				
		}
		

	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==movingTimer)
		{
			for(int i=0;i<danceRectArray.length;i++)
			{
				if(danceRectArray[i]!=null)
				{
					DanceRect danceRect=danceRectArray[i];
					
					danceRect.currentOffset+=STEP;
					danceRect.dancingLabel.setLocation(danceRect.dancingLabel.getX()+STEP*danceRect.offsetDirection,
							danceRect.dancingLabel.getY());
					repaint();
					
					if((danceRect.currentOffset>=danceRect.inDefendableZoneOffset)
						&&(danceRect.currentOffset<=danceRect.outDefendableZoneOffset))
					{
						danceRect.defendable=true;
						
					}
					else
					{
						
						danceRect.defendable=false;
					   
					}
					
					if(danceRect.currentOffset>=danceRect.selfDestoryOffset+5)
					{
						
						if(!danceRect.defended)
						{
							hittedByTheOpposite();
						}
						
						removeDanceRect(danceRect);
					}
				}
			}
			repaint();
		}
		
		if(e.getSource()==attackIntervalTimer1)
		{
			attackIntervalCounter1++;
			if(ATTACK_INTERVAL_SPAN==attackIntervalCounter1)
			{
				attackIntervalCounter1=0;
				attackable1=true;
				attackIntervalTimer1.stop();
			}
			
		}
		if(e.getSource()==attackIntervalTimer2)
		{
			attackIntervalCounter2++;
			if(ATTACK_INTERVAL_SPAN==attackIntervalCounter2)
			{
				attackIntervalCounter2=0;
				attackable2=true;
				attackIntervalTimer2.stop();
			}
			
		}
		
		if(e.getSource()==AIAttackTimer)
		{
			int random=(int)(Math.random()*8);
			if((random>=0)&&(random<=7))
			{
				attackKeyPressed(random);
				opponentPortait.setIcon(opponentMovementImageIcons[random]);
				opponentMovementState=random;
			}
			
		}
		
		if(e.getSource()==AIDefenceTimer)
		{
			for(int i=0;i<danceRectArray.length;i++)
			{
				if(danceRectArray[i]!=null)
				{
					if(danceRectArray[i].defendable)
				    {
					    if(Math.random()<=AI_SUCCESSFUL_DEFENCE_PROBABILITY)
					    {
						    danceRectArray[i].beDefended();
						    opponentPortait.setIcon(opponentMovementImageIcons[danceRectArray[i].direction]);
						    opponentMovementState=danceRectArray[i].direction;
					    }
					    else
					    {
					    	opponentPortait.setIcon(opponentMovementDefaultImageIcon);
					    	opponentMovementState=-1;
					    }
				    }
				}
			}
		}
		
		
		if(e.getSource()==turnTimer)
		{
			turnCount++;
			countingDown.setText(""+(TURN_COUNT_SPAN-turnCount));
			if(TURN_COUNT_SPAN==turnCount)
			{
				turnCount=1;
				countingDown.setText(""+10);
				if(PLAYER_TURN==currentState)
				{
					switchToOpponentTurn();
					
				}
				else
				{
					if(OPPONENT_TURN==currentState)
					{
						switchToPlayerTurn();
					}
				}
			}
			
			if(playerHealth<=0||opponentHealth<=0)
			{
				switchToWinOrDie();
			}
		}
	
		if(e.getSource()==finishButton)
		{
			
			endOfGame();
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		
		int playerOffset1=0;
		int playerOffset2=0;
		if((playerMovementState>=0)&&(playerMovementState<4))
		{
			playerOffset1=5;
		}
		if((playerMovementState>=4)&&(playerMovementState<8))
		{
			playerOffset2=5;
		}
		
		
		g.drawRect(playerRect1.getX()+2*rectSize-playerOffset1,playerRect1.getY()-playerOffset1,rectSize+2*playerOffset1,rectSize+2*playerOffset1);
		g.drawRect(playerRect1.getX()+2*rectSize+1-playerOffset1,playerRect1.getY()+1-playerOffset1,rectSize-2+2*playerOffset1,rectSize-2+2*playerOffset1);
		
		g.drawRect(playerRect2.getX()+2*rectSize-playerOffset2,playerRect2.getY()-playerOffset2,rectSize+2*playerOffset2,rectSize+2*playerOffset2);
		g.drawRect(playerRect2.getX()+2*rectSize+1-playerOffset2,playerRect2.getY()+1-playerOffset2,rectSize-2+2*playerOffset2,rectSize-2+2*playerOffset2);
		
		g.drawRect(opponentRect1.getX()-2*rectSize,opponentRect1.getY(),rectSize,rectSize);
		g.drawRect(opponentRect1.getX()-2*rectSize+1,opponentRect1.getY()+1,rectSize-2,rectSize-2);
		
		g.drawRect(opponentRect2.getX()-2*rectSize,opponentRect2.getY(),rectSize,rectSize);
		g.drawRect(opponentRect2.getX()-2*rectSize+1,opponentRect2.getY()+1,rectSize-2,rectSize-2);
	    
	}
	
	//==inner Class ==
	private class DanceRect 
	{
		private int currentOffset;
		private int offsetDirection;  //1 or -1 ,1 for right;
		
		private final int movingSpace=opponentRect1.getX()-playerRect1.getX()-rectSize-2*rectSize;
		private final int inDefendableZoneOffset=movingSpace-2*rectSize;
		private final int outDefendableZoneOffset=movingSpace-rectSize;
		
		private final int selfDestoryOffset=movingSpace;
		
		private int whosTurn;
		private int direction;  //配合下面的常量使用
		private static final int UP_UP=0;  //第一个UP表示是上面那个各自发出的
		private static final int UP_DOWN=1;
		private static final int UP_LEFT=2;
		private static final int UP_RIGHT=3;
		private static final int DOWN_UP=4;
		private static final int DOWN_DOWN=5;
		private static final int DOWN_LEFT=6;
		private static final int DOWN_RIGHT=7;
					
		private JLabel dancingLabel=new JLabel();
		private boolean defendable;
		private boolean defended;
		
		private DancingFight owner;
		
		//image field
		private Icon upUpImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/upUpImage.jpg");
		private Icon upDownImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/upDownImage.jpg");
		private Icon upLeftImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/upLeftImage.jpg");
		private Icon upRightImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/upRightImage.jpg");
		
		private Icon downUpImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/downUpImage.jpg");
		private Icon downDownImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/downDownImage.jpg");
		private Icon downLeftImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/downLeftImage.jpg");
		private Icon downRightImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/downRightImage.jpg");
		
		private Icon defendedImage=new ImageIcon("pic/SubGame/DancingFight/DancingRect/defendedImage.jpg");	
		private DanceRect(int direction,int whosTurn,DancingFight owner )
		{
			this.direction=direction;
			this.whosTurn=whosTurn;
			this.owner=owner;
			this.defendable=false;
			this.defended=false;  //是否已经被对手防住
			
			currentOffset=0;
			
			dancingLabel.setSize(rectSize,rectSize);
			dancingLabel.setBorder(new LineBorder(Color.RED,2));
			
			if(PLAYER_TURN==whosTurn)
			{
				offsetDirection=1;
				
			    switch(direction)
			    {
			        case UP_UP : 
			        	{
			        		dancingLabel.setLocation(playerRect1.getX()+2*rectSize,playerRect1.getY());
			        	    dancingLabel.setIcon(upUpImage);
			        		break;
			        	}
			        case UP_DOWN : 
			            {
			                dancingLabel.setLocation(playerRect1.getX()+2*rectSize,playerRect1.getY());
				        	dancingLabel.setIcon(upDownImage);
				        	break;
				       }
			        
			        case UP_LEFT : 
			        {
		        		dancingLabel.setLocation(playerRect1.getX()+2*rectSize,playerRect1.getY());
		        	    dancingLabel.setIcon(upLeftImage);
		        		break;
		        	}
			        case UP_RIGHT : 
			        {
		        		dancingLabel.setLocation(playerRect1.getX()+2*rectSize,playerRect1.getY());
		        	    dancingLabel.setIcon(upRightImage);
		        		break;
		        	}
			    
			        case DOWN_UP : 
			        {
		        		dancingLabel.setLocation(playerRect2.getX()+2*rectSize,playerRect2.getY());
		        	    dancingLabel.setIcon(downUpImage);
		        		break;
		        	}
			        case DOWN_DOWN : 
			        {
		        		dancingLabel.setLocation(playerRect2.getX()+2*rectSize,playerRect2.getY());
		        	    dancingLabel.setIcon(downDownImage);
		        		break;
		        	}
		    	    case DOWN_LEFT :
		    	    {
		        		dancingLabel.setLocation(playerRect2.getX()+2*rectSize,playerRect2.getY());
		        	    dancingLabel.setIcon(downLeftImage);
		        		break;
		        	}
		    	    case DOWN_RIGHT : 
		    	    {
		        		dancingLabel.setLocation(playerRect2.getX()+2*rectSize,playerRect2.getY());
		        	    dancingLabel.setIcon(downRightImage);
		        		break;
		        	}
			    
			        default : ;break;
			    }
			}
			else
			{
				offsetDirection=-1;
				
				if(OPPONENT_TURN==whosTurn)
				{
					switch(direction)
				    {
				        case UP_UP : 
				        	{
				        		dancingLabel.setLocation(opponentRect1.getX()-2*rectSize,opponentRect1.getY());
				        	    dancingLabel.setIcon(upUpImage);
				        		break;
				        	}
				        case UP_DOWN : 
				            {
				            	dancingLabel.setLocation(opponentRect1.getX()-2*rectSize,opponentRect1.getY());
					        	dancingLabel.setIcon(upDownImage);
					        	break;
					       }
				        
				        case UP_LEFT : 
				        {
				        	dancingLabel.setLocation(opponentRect1.getX()-2*rectSize,opponentRect1.getY());
			        	    dancingLabel.setIcon(upLeftImage);
			        		break;
			        	}
				        case UP_RIGHT : 
				        {
				        	dancingLabel.setLocation(opponentRect1.getX()-2*rectSize,opponentRect1.getY());
			        	    dancingLabel.setIcon(upRightImage);
			        		break;
			        	}
				    
				        case DOWN_UP : 
				        {
				        	dancingLabel.setLocation(opponentRect2.getX()-2*rectSize,opponentRect2.getY());
			        	    dancingLabel.setIcon(downUpImage);
			        		break;
			        	}
				        case DOWN_DOWN : 
				        {
				        	dancingLabel.setLocation(opponentRect2.getX()-2*rectSize,opponentRect2.getY());
			        	    dancingLabel.setIcon(downDownImage);
			        		break;
			        	}
			    	    case DOWN_LEFT :
			    	    {
			    	    	dancingLabel.setLocation(opponentRect2.getX()-2*rectSize,opponentRect2.getY());
			        	    dancingLabel.setIcon(downLeftImage);
			        		break;
			        	}
			    	    case DOWN_RIGHT : 
			    	    {
			    	    	dancingLabel.setLocation(opponentRect2.getX()-2*rectSize,opponentRect2.getY());
			        	    dancingLabel.setIcon(downRightImage);
			        		break;
			        	}
				    
				        default : ;break;
				    }
				}
				else
				{
					JOptionPane.showMessageDialog(null, "error in DanceRect");
				}
			}
			
			owner.add(dancingLabel);
			
			
		}
		
		
		
		
		private void beDefended()
		{
			defended=true;
			dancingLabel.setIcon(defendedImage);
			dancingLabel.setBorder(new LineBorder(Color.GREEN,2));
			repaint();
		}
		
		private JLabel getDancingLabel()
		{
			return this.dancingLabel;
		}

		private boolean isDefendable() {
			return defendable;
		}
		
		private int getDirection()
		{
			return direction;
		}

	}

	//AIattactInterval and STEP will contribute to the rewards player gets
	public void winByPlayer()
	{
		if(null!=getOpponent())
		{
			getPlayer().setMoney(getPlayer().getMoney()+(int)(120*STEP/400.0*800.0/(300+AI_ATTACK_TIMER_INTERVAL))+(int)(60*Math.random()));
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
	
	//可被外部访问区域
	
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		DancingFight dancingFight = new DancingFight(null,null,null);
		frame.getContentPane().add(dancingFight);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public int getTimeLengthOfARound()
	{
		return TURN_COUNT_SPAN;
	}

	public void setTimeLengthOfARound(int TimeLengthOfARound) {
		TURN_COUNT_SPAN = TimeLengthOfARound;
	}

	public int getVelocity() {
		return STEP*1000/INTERVAL_OF_MOVING_TIMER;
	}

	public void setVelocity(int velocity) {
		STEP = (int)(1.0*velocity*INTERVAL_OF_MOVING_TIMER/1000);
	}

	public int getAIAttackInterval() {
		return AI_ATTACK_TIMER_INTERVAL;
	}

	public void setAIAttackInterval(int AIAttackInterval) {
		AI_ATTACK_TIMER_INTERVAL = AIAttackInterval;
	}

	public double getAISuccessfulDefenceProbability() {
		return AI_SUCCESSFUL_DEFENCE_PROBABILITY;
	}

	public void setAISuccessfulDefenceProbability(
			double AISuccessfulDefenceProbability) {
		AI_SUCCESSFUL_DEFENCE_PROBABILITY = AISuccessfulDefenceProbability;
	}

	public int getHealthReduceStep() {
		return HEALTH_STEP;
	}
	
	public void setHealthReduceStep(int healthReduceStep)
	{
		HEALTH_STEP=healthReduceStep;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getAttackInterval() {
		return AI_ATTACK_TIMER_INTERVAL;
	}

	public void setAttackInterval(int attackInterval) {
		AI_ATTACK_TIMER_INTERVAL = attackInterval;
	}

}
