package subGame.breakBrick.gamePanel;

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

/**
 * 
 * 建立游戏时，先运行initialize（）方法,
 * 再运行initializeBricks方法之一
 * gameState 控制游戏的暂停（STARTED,STOPPED）
 */
public class WallPanel extends JPanel implements Constants, MouseListener,
		MouseMotionListener, KeyListener, MouseWheelListener, ActionListener {

	//for debug
	private final static boolean debug = false;
	private final static boolean debug2 = false;
	private final static boolean debug3 = false;
	private final static boolean debug4 = true;

	private int gameState=STOPPED;
	private WallPanel wallPanel = this;

	
	public static final Dimension wallPanelTotalSize = wallPanelSize;
	public static final Image fireBallImage = Toolkit.getDefaultToolkit()
			.getImage("pic/SubGame/shootingPractice/fireBallToBeUsed.gif");

	public static final int DEAGLE = 1;
	public static final int M4 = 0;
	public static final int AWP = 2;
	public static final int ROCKET = 3;

	public static int origTotalNumOfRocket = 3;
	public int totalNumOfRocket = origTotalNumOfRocket;

	//bricks
	public final static Image[][] brickImages = {
			brickImagesR,
			brickImagesO,
			brickImagesY,
			brickImagesG,
			brickImagesQ,
			brickImagesB,
			brickImagesD,
			brickImagesP,
			brickImagesH,
			brickImagesW,
			brickImagesExplosive,
			brickImagesFirm,
	};
	

	//音效
	private SoundPlayer bangClip=null;
	private AudioClip start1Clip=null;
	private AudioClip start2Clip=null;
	private AudioClip reballClip=null;
	private AudioClip guitClip=null;
	private AudioClip deagleClip[] = null;
	private int deagleClipPointer = 0;
	private AudioClip[] m4Clip = null;
	private int m4ClipPointer = 0;
	private AudioClip awpClip = null;
	private AudioClip[] rocketClip = null;
	private int rocketClipPointer = 0;
	private AudioClip[] rocketEmptyClip = null;
	private int rocketEmptyClipPointer = 0;

	private AudioClip gunSwitchClip = null;

	private AudioClip[] ric = null;
	private int ricPointer = 0;

	//对靶子的运动周期的控制
	
	private Brick[][] bricks = new Brick[brickRowNum][brickColumNum];
	private Ball ball = null;
	private Gun gun = null;

	private Spark[] sparks = null;
	private Spark2[] spark2s = null;
	private Spark3[] spark3s = null;
	private Rewards[] rewards = null;
	private BulletHole[] bulletHoles = null;
	private RocketHole[] rocketHoles = null;

	private GunAim gunAim = null;
	private boolean attacking = false;
	private Point mousePoint = new Point(0, 0);

	private int currentGun = DEAGLE;

	private GunDisplay gunDisplay = new GunDisplay(DEAGLE);

	private int score = 0;

	//用来弹射球的板子
	public static final ImageIcon boardIcon=new ImageIcon("pic\\SubGame\\shootingPractice\\board.gif");
	public static final Image boardImage=boardIcon.getImage();
	public static final Dimension boardSize=new Dimension(boardIcon.getIconWidth(),boardIcon.getIconHeight());
	public static final int boardStep=10;
	
	private int boardX=(wallPanelSize.width-boardSize.width)/2;
	private boolean boardMoveLeftRequested=false;
	private boolean boardMoveRightRequested=false;	
	
	//设置有几条命
	public static final int totalBallLives=10;
	private int ballLivesLeft=totalBallLives;
	
	//information label
	private JLabel infoLabel=new JLabel("");
	
	//用于控制过关的延时
	private int mapPassedCounter=0;
	
	//面板管理器的引用
	private BrickBreak.PanelConductor panelConductor;
	
	//自己的计时器
	private Timer timer=new Timer(TIMER_INTERVAL,this);
	
	//火花效果的开关
	private boolean sparkEffectSwitch=true;
	
	public WallPanel() 
	{	
		setLayout(null);
		setPreferredSize(wallPanelTotalSize);
		setSize(wallPanelTotalSize);
		setLocation(0, 0);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addMouseWheelListener(this);

		setBackground(backgroundColor);
		
		gun = new Gun();
		ball = new Ball(gun);

		sparks = new Spark[10];
		for (int i = 0; i < sparks.length; i++) {
			sparks[i] = null;
		}

		spark2s = new Spark2[10];
		for (int i = 0; i < spark2s.length; i++) {
			spark2s[i] = null;
		}
		spark3s = new Spark3[10];
		for (int i = 0; i < spark3s.length; i++) {
			spark3s[i] = null;
		}

		rewards = new Rewards[5];
		for (int i = 0; i < rewards.length; i++) {
			rewards[i] = null;
		}

		bulletHoles = new BulletHole[10];
		for (int i = 0; i < bulletHoles.length; i++) {
			bulletHoles[i] = null;
		}
		rocketHoles = new RocketHole[10];
		for (int i = 0; i < rocketHoles.length; i++) {
			rocketHoles[i] = null;
		}

		this.gunAim = new GunAim();

		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				bricks[i][j] = null;
			}
		}

		Toolkit tk = Toolkit.getDefaultToolkit();
		Image temp = tk.getImage("pic/cursors/blank.gif");
		Cursor cursor = tk.createCustomCursor(temp, new Point(0, 0), "none");
		setCursor(cursor);

		//setFocusable(true);
		
		//加入infoLabel
		infoLabel.setVisible(false);
		infoLabel.setBackground(Color.YELLOW);
		infoLabel.setForeground(Color.RED);
		infoLabel.setBorder(new LineBorder(Color.RED,5));
		infoLabel.setFont(new Font("Times",Font.BOLD,36));
		add(infoLabel);
			
		//设置音效
		try {
			Class metaObject = this.getClass();
			
			bangClip=new SoundPlayer(metaObject.getResource("bang.wav"),30);
			start1Clip=Applet.newAudioClip(metaObject.getResource("start1.wav"));
			start2Clip=Applet.newAudioClip(metaObject.getResource("start2.wav"));
			reballClip=Applet.newAudioClip(metaObject.getResource("reball.wav"));
			guitClip=Applet.newAudioClip(metaObject.getResource("guit.wav"));
			
			deagleClip = new AudioClip[3];
			for (int i = 0; i < deagleClip.length; i++) {
				deagleClip[i] = Applet.newAudioClip(metaObject.getResource("deagle-1.wav"));
			}
			deagleClipPointer = 0;

			m4Clip = new AudioClip[20];
			for (int i = 0; i < m4Clip.length; i++) {
				m4Clip[i] = Applet.newAudioClip(metaObject
						.getResource("m4a1.wav"));
			}
			m4ClipPointer = 0;
			awpClip = Applet.newAudioClip(metaObject.getResource("awp.wav"));

			rocketClip = new AudioClip[5];
			for (int i = 0; i < rocketClip.length; i++) {
				rocketClip[i] = Applet.newAudioClip(metaObject
						.getResource("explode.wav"));
			}
			rocketClipPointer = 0;

			rocketEmptyClip = new AudioClip[5];
			for (int i = 0; i < rocketEmptyClip.length; i++) {
				rocketEmptyClip[i] = Applet.newAudioClip(metaObject
						.getResource("dryfire.wav"));
			}
			rocketEmptyClipPointer = 0;

			gunSwitchClip = Applet.newAudioClip(metaObject
					.getResource("boltpull.wav"));

			ric = new AudioClip[6];
			for (int i = 0; i < ric.length; i++) {
				ric[i] = Applet.newAudioClip(metaObject.getResource("ric"
						+ (i + 1) + ".wav"));
			}
			ricPointer = 0;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "audio error");
		}
		
		timer.start();
	}

	public void addSpark(Spark newSpark) {
		int i; // to search for the end of the array
		for (i = 0; (i < sparks.length) && (sparks[i] != null); i++)
			;
		if (i < sparks.length) {
			sparks[i] = newSpark;

		} else {
			Spark[] newSparks = new Spark[sparks.length * 2];
			for (int j = 0; j < newSparks.length; j++) {
				newSparks[j] = null;
			}
			System.arraycopy(sparks, 0, newSparks, 0, sparks.length);

			newSparks[sparks.length] = newSpark;
			this.sparks = newSparks;
		}
	}

	public void addSpark2(Spark2 newSpark2) {
		int i; // to search for the end of the array
		for (i = 0; (i < spark2s.length) && (spark2s[i] != null); i++)
			;
		if (i < spark2s.length) {
			spark2s[i] = newSpark2;

		} else {
			Spark2[] newSpark2s = new Spark2[spark2s.length * 2];
			for (int j = 0; j < newSpark2s.length; j++) {
				newSpark2s[j] = null;
			}
			System.arraycopy(spark2s, 0, newSpark2s, 0, spark2s.length);

			newSpark2s[spark2s.length] = newSpark2;
			this.spark2s = newSpark2s;
		}
	}

	public void addSpark3(Spark3 newSpark3) {
		int i; // to search for the end of the array
		for (i = 0; (i < spark3s.length) && (spark3s[i] != null); i++)
			;
		if (i < spark3s.length) {
			spark3s[i] = newSpark3;

		} else {
			Spark3[] newSpark3s = new Spark3[spark3s.length * 2];
			for (int j = 0; j < newSpark3s.length; j++) {
				newSpark3s[j] = null;
			}
			System.arraycopy(spark3s, 0, newSpark3s, 0, spark3s.length);

			newSpark3s[spark3s.length] = newSpark3;
			this.spark3s = newSpark3s;
		}
	}

	public void addRewards(Rewards newRewards) {
		int i; // to search for the end of the array
		for (i = 0; (i < rewards.length) && (rewards[i] != null); i++)
			;
		if (i < rewards.length) {
			rewards[i] = newRewards;

		} else {
			Rewards[] newRewardss = new Rewards[rewards.length * 2];
			for (int j = 0; j < newRewardss.length; j++) {
				newRewardss[j] = null;
			}
			System.arraycopy(rewards, 0, newRewardss, 0, rewards.length);

			newRewardss[rewards.length] = newRewards;
			this.rewards = newRewardss;
		}
	}

	public void addBulletHole(BulletHole newBulletHole) {
		int i; // to search for the end of the array
		for (i = 0; (i < bulletHoles.length) && (bulletHoles[i] != null); i++)
			;
		if (i < bulletHoles.length) {
			bulletHoles[i] = newBulletHole;

		} else {
			BulletHole[] newBulletHoles = new BulletHole[bulletHoles.length * 2];
			for (int j = 0; j < newBulletHoles.length; j++) {
				newBulletHoles[j] = null;
			}
			System.arraycopy(bulletHoles, 0, newBulletHoles, 0,
					bulletHoles.length);

			newBulletHoles[bulletHoles.length] = newBulletHole;
			this.bulletHoles = newBulletHoles;
		}
	}

	public void addRocketHole(RocketHole newRocketHole) {
		int i; // to search for the end of the array
		for (i = 0; (i < rocketHoles.length) && (rocketHoles[i] != null); i++)
			;
		if (i < rocketHoles.length) {
			rocketHoles[i] = newRocketHole;

		} else {
			RocketHole[] newRocketHoles = new RocketHole[rocketHoles.length * 2];
			for (int j = 0; j < newRocketHoles.length; j++) {
				newRocketHoles[j] = null;
			}
			System.arraycopy(rocketHoles, 0, newRocketHoles, 0,
					rocketHoles.length);

			newRocketHoles[rocketHoles.length] = newRocketHole;
			this.rocketHoles = newRocketHoles;
		}
	}

	public void initializeBricks() {
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				double rand = Math.random();
				boolean isExist = false;
				if (rand > 0.8) {
					isExist = true;
				}

				rand = Math.random();
				int brickType = -1;
				if (rand <= 0.4) {
					brickType = Brick.COMMON;
				} else {
					if (rand < 0.7) {
						brickType = Brick.EXPLOSIVE;
					} else {
						brickType = Brick.EXPLOSIVE;
						if(Math.random()<0.01)
						{
							brickType=Brick.FIRM;
						}
					}
				}

				int color = RED + (int) (Math.random() * (WHITE - RED + 1));
				int reward = 0 + (int) (Math.random() * 3);

				if (isExist) {
					bricks[i][j] = new Brick(new Point(i, j), brickType,
							reward, color, this);
				}

			}
		}
	}
    public void initializeBricks(int[][] brickTypes)
    {
    	boolean flag1=false;
    	if((brickTypes!=null)
    		&&(brickTypes.length==brickRowNum))
    	{
    		flag1=true;
    	}
    	boolean flag2=true;
    	for(int i=0;i<brickTypes.length;i++)
    	{
    		if(brickTypes[i].length!=brickColumNum)
    		{
    			flag2=false;
    		}
    	}
    	
    	if((!flag1)||(!flag2))
    	{
    		System.out.println("Error in initializeBricks of WallPanel");
    		return;
    	}
    	
    	//test finished
    	double rewardRate=0.02;  
    	
    	for(int i=0;i<bricks.length;i++)
    	{
    		for(int j=0;j<bricks[i].length;j++)
    		{
    			if(brickTypes[i][j]!=-1)
    			{
    				int rewardTypeRandom=(int)(Math.random()*3);
    				int rewardType=Rewards.NONE;
    				
    				if (Math.random() < rewardRate)
    				{
    					switch (rewardTypeRandom)
    					{
    					case 0: {
    						rewardType = Rewards.FIRE_BALL;
    						break;
    					}
    					case 1: {
    						rewardType = Rewards.IRON_BALL;
    						break;
    					}
    					default: {
    						rewardType = Rewards.ROCKET;
    						break;
    					}
    					}
    				}
    				
    				//以上初始化完毕rewardType
    				Point order=new Point(i,j);
    				
    				if(brickTypes[i][j]<10)
    				{
    					bricks[i][j]=new Brick(order,Brick.COMMON,rewardType,brickTypes[i][j],this);
    				}
    				else
    				{
    					if(10==brickTypes[i][j])
    					{
    						bricks[i][j]=new Brick(order,Brick.EXPLOSIVE,rewardType,BLUE,this);
    					}
    					
    					if(11==brickTypes[i][j])
    					{
    						bricks[i][j]=new Brick(order,Brick.FIRM,rewardType,BLUE,this);
    					}
    					
    				}
    			}
    		}
    	}
    }
    public void initializeBricks(File file)
    {
    	try 
		{
			ObjectInputStream in = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(
							file)));
			int[][] brickTypes = (int[][]) (in.readObject());
            in.close();
            
            initializeBricks(brickTypes);
            
            System.out.println("end");
		} 
		catch (Exception ex) 
		{
			System.out.println("Error When Open File in WallPanel");
			ex.printStackTrace();
		}
		finally
		{
			if(Math.random()<0.5)
			{
				//start1Clip.play();
			}
			else
			{
				//start2Clip.play();
			}
		}
		
    }
	
	public void initialize() {
		this.ball.setLocation(new MyPoint(wallPanelTotalSize.width / 2,
				groundLine - ballRadius));
	    boardX=(wallPanelSize.width-boardSize.width)/2;
		
		this.ball.setVx(0);
		this.ball.setVy(0);

		totalNumOfRocket = origTotalNumOfRocket;
		//ballLivesLeft=totalBallLives;
		
		infoLabel.setVisible(false);
		gameState=STARTED;
		
		requestFocusInWindow();
		timer.start();
		
		//initializeBricks();
	}

	public void setCurrentGun(int type) {
		gun.switchGun(type);
		currentGun = type;
		gunDisplay.setCurrentGun(type);
		gunSwitchClip.play();
	}

	public void playM4Clip() {
		m4Clip[m4ClipPointer].play();
		m4Clip[(m4ClipPointer + 1) % m4Clip.length].stop();
		m4ClipPointer++;
		m4ClipPointer %= m4Clip.length;
	}

	public void playDeagleClip() {
		deagleClip[deagleClipPointer].play();
		deagleClip[(deagleClipPointer + 1) % deagleClip.length].stop();
		deagleClipPointer++;
		deagleClipPointer %= deagleClip.length;
	}

	public void playRocketClip() {
		if (totalNumOfRocket > 0) {
			rocketClip[rocketClipPointer].play();
			rocketClip[(rocketClipPointer + 1) % rocketClip.length].stop();
			rocketClipPointer++;
			rocketClipPointer %= rocketClip.length;
		} else {
			rocketEmptyClip[rocketEmptyClipPointer].play();
			rocketEmptyClip[(rocketEmptyClipPointer + 1)
					% rocketEmptyClip.length].stop();
			rocketEmptyClipPointer++;
			rocketEmptyClipPointer %= rocketEmptyClip.length;
		}

	}

	public void playRic() {
		ric[ricPointer].play();
		ric[(ricPointer + 1) % ric.length].stop();
		ricPointer++;
		ricPointer %= ric.length;
	}
	
	//这个方法处理球挂掉
	public void ballDied()
	{
	    if(ballLivesLeft>0)
	    {
	    	ballLivesLeft-=1;
	    	ball.setLocation(boardX+boardSize.width/2,groundLine-ballRadius);
	    	ball.setVx(0);
	    	ball.setVy(0);
	    	
	    	reballClip.play();
	    }
	    else
	    {
	    	guitClip.play();
	    	gameOver();
	    }

	}
	
	public void addScore(int value)
	{
		score+=value;
	}
	
	public void gameOver()
	{
		displayInfoLabel("GAME OVER");
		final JButton button=new JButton("OK");
		wallPanel.add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				wallPanel.remove(button);
				sendGameOverMessage();
			}
		});
		button.setSize(button.getPreferredSize());
	    button.setLocation(infoLabel.getLocation().x+(infoLabel.getSize().width-button.getSize().width)/2,
	    		infoLabel.getLocation().y+infoLabel.getSize().height+20);
	    button.setCursor(Cursor.getDefaultCursor());
	    setGameState(STOPPED);
	}
	
	public void mapPassed()
	{
		displayInfoLabel("MAP PASSED");		
		final JButton button=new JButton("OK");
		wallPanel.add(button);
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				wallPanel.remove(button);
				sendMapPassedMessage();
			}
		});
		button.setSize(button.getPreferredSize());
	    button.setLocation(infoLabel.getLocation().x+(infoLabel.getSize().width-button.getSize().width)/2,
	    		infoLabel.getLocation().y+infoLabel.getSize().height+20);
	    button.setCursor(Cursor.getDefaultCursor());
	    setGameState(STOPPED);
	}
	
	//下面两个方法用来个整个游戏的主控制器发送信息
	//====================================
	public void sendGameOverMessage()
	{
		timer.stop();
		panelConductor.receiveGameOverMessageFromWallPanel();
	}
	
	public void sendMapPassedMessage()
	{
		timer.stop();
		panelConductor.receiveMapPassedMessageFromWallPanel();	
	}
	//=====================================
	
	public void displayInfoLabel(String info)
	{
		infoLabel.setText(info);
		infoLabel.setSize(infoLabel.getPreferredSize());
		infoLabel.setLocation((wallPanelSize.width-infoLabel.getSize().width)/2,
				(wallPanelSize.height-infoLabel.getSize().height)/2);
	    infoLabel.setVisible(true);
	    repaint();
	}

	public void selfProcess() {

		if (STARTED == getGameState()) {
			//处理开枪
			if (attacking) {
				if (gun.shoot()) {
					gunAim.shootAdjust();
					switch (currentGun) {
					case DEAGLE: {
						playDeagleClip();
						break;
					}
					case M4: {
						playM4Clip();
						break;
					}
					case AWP: {
						awpClip.play();
						break;
					}
					case ROCKET: {
						playRocketClip();
					}
					default: {
						break;
					}
					}

					double distance = ball.getLocation().distance(mousePoint);
					if (currentGun != ROCKET)
					{
						if (distance < ballRadius) 
						{
							playRic();
							if (debug3) 
							{
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

							if (debug3)
							{
								System.out.println(ball.toString());
								System.out.println("e.getPoint(): "
										+ mousePoint);
								System.out.println("ball.getLocation(): "
										+ ball.getLocation());
							}

							// 添加spark
						
							if (sparkEffectSwitch)
							{
								     addSpark(new Spark(hitPoint,
										ball.getLocation(), this));
							}
						}
							else
							{
						    	if (currentGun != M4) {
							    	if (sparkEffectSwitch) {
										addSpark2(new Spark2(new MyPoint(
												mousePoint.x, mousePoint.y),
												this));
									}
						    	} 
						      	else 
						    	{
						    		if (sparkEffectSwitch) {
										addSpark2(new Spark2(new MyPoint(
												mousePoint.x, mousePoint.y),
												this, false));
									}
						    	}

						    	addBulletHole(new BulletHole(mousePoint));
						  	}
						
					}
					else 
					{
						if (totalNumOfRocket > 0) 
						{
							MyPoint hitPoint = new MyPoint(mousePoint.x,
									mousePoint.y);
							addSpark3(new Spark3(hitPoint));
							addRocketHole(new RocketHole(hitPoint.getPoint(),
									this));
							ball.hitByRocket(distance, new MyPoint(hitPoint.x
									- ball.getLocation().x, hitPoint.y
									- ball.getLocation().y));
							totalNumOfRocket--;

						} 
						else 
						{
							;
						}
					}

					//处理击中reward
					//处理击中reward
					for (int i = 0; i < rewards.length; i++) {
						if (rewards[i] != null) {
							if (rewards[i].contains(mousePoint)
									&& rewards[i].isUseAble()
									&& !rewards[i].isHitted()) {
								rewards[i].setHitted(true);
								addScore(50);

								switch (rewards[i].getType()) {
								case Rewards.FIRE_BALL: {
									ball.setFireBallTrue();
									break;
								}
								case Rewards.IRON_BALL: {
									ball.setIronBallTrue();
									break;
								}
								case Rewards.ROCKET: {
									totalNumOfRocket++;
									break;
								}
								default: {
									break;
								}
								}
							}
						}
					}
				}
			}

			// ==处理球
			double dertX = ball.getVx() * TIMER_INTERVAL / 1000.0;
			double dertY = ball.getVy() * TIMER_INTERVAL / 1000.0;
			ball.setLocation(ball.getLocation().x + dertX, ball.getLocation().y
					- dertY);

			double dertVy = -gravity * TIMER_INTERVAL / 1000.0;
			ball.setVy(ball.getVy() + dertVy);

			//球与brick的碰撞处理

			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[i].length; j++) {
					if (bricks[i][j] != null) {
						bricks[i][j].collisionProcess(ball);
					}
				}
			}

			ball.selfProcesss();

			//球的撞墙处理		
			//撞board处理部分
			if ((ball.getLocation().y + ballRadius >= groundLine)&&(ball.getLocation().y-2*ballRadius<groundLine+boardSize.height)
					&& (ball.getVy() < 0)&&(ball.getLocation().x>boardX)&&(ball.getLocation().x<boardX+boardSize.width)) {
				ball.setLocation(ball.getLocation().x, groundLine - ballRadius);
				ball.setVy(-ball.getVy() / 1.5);
				ball.setVx(ball.getVx() * 19 / 20);
			}

			//撞其他地方的处理
			if ((ball.getLocation().y - ballRadius <= 0) && (ball.getVy() > 0)) {
				ball.setLocation(ball.getLocation().x, ballRadius);
				ball.setVy(-ball.getVy());

			}

			if ((ball.getLocation().x - ballRadius <= 0) && (ball.getVx() < 0)) {
				ball.setLocation(ballRadius, ball.getLocation().y);
				ball.setVx(-ball.getVx() * 3 / 4);

			}

			if ((ball.getLocation().x + ballRadius >= wallPanelTotalSize.width)
					&& (ball.getVx() > 0)) {
				ball.setLocation(wallPanelTotalSize.width - ballRadius, ball
						.getLocation().y);
				ball.setVx(-ball.getVx() * 3 / 4);

			}

			//处理球挂掉
			if(ball.getLocation().y-ballRadius>wallPanelSize.height+ballRadius*30)
			{
				ballDied();
			}
			
			//处理board移动
			
			boolean ballOnBoard=false;
			if ((ball.getLocation().y + ballRadius >= groundLine-1)&&(ball.getLocation().y+ballRadius<groundLine+boardSize.height)
					&&(ball.getLocation().x>boardX)&&(ball.getLocation().x<boardX+boardSize.width)) 
			{
				//TODO add a radio clip
				ballOnBoard=true;
			}
			else
			{
				ballOnBoard=false;
			}
			
			if(boardMoveLeftRequested)
			{
				if(boardX>0)
				{
					boardX-=boardStep;
					if(ballOnBoard)
					{
						ball.setLocation(ball.getLocation().x-boardStep,ball.getLocation().y);
					}
				}
			}
			if(boardMoveRightRequested)
			{
				if(boardX<wallPanelSize.width-boardSize.width)
				{
					boardX+=boardStep;
					if(ballOnBoard)
					{
					    ball.setLocation(ball.getLocation().x+boardStep,ball.getLocation().y);
					}
				}
			}
			
			//处理Spark
			for (int i = 0; i < sparks.length; i++) {
				if (sparks[i] != null) {
					sparks[i].selfProcess();
					if (!sparks[i].isUseAble()) {
						sparks[i] = null;
					}
				}
			}
			//处理Spark2
			for (int i = 0; i < spark2s.length; i++) {
				if (spark2s[i] != null) {
					spark2s[i].selfProcess();
					if (!spark2s[i].isUseAble()) {
						spark2s[i] = null;
					}
				}
			}

			//处理Spark3
			for (int i = 0; i < spark3s.length; i++) {
				if (spark3s[i] != null) {
					spark3s[i].selfProcess();
					if (!spark3s[i].isUseAble()) {
						spark3s[i] = null;
					}
				}
			}

			//==处理枪
			gun.selfProcess();

			//处理准星
			gunAim.selfProcess();

			//处理弹孔
			int totalNumOfHoles = 0;
			for (int i = 0; i < bulletHoles.length; i++) {
				if (bulletHoles[i] != null) {
					bulletHoles[i].selfProcess();
					totalNumOfHoles++;
				}
			}
			if (totalNumOfHoles > 50) {
				for (int i = 0; i < bulletHoles.length; i++) {
					bulletHoles[i] = null;
				}
				for (int i = 0; i < rocketHoles.length; i++) {
					rocketHoles[i] = null;
				}
			}

			//处理背景

			//处理gunDisplay
			gunDisplay.selfProcess();

			//处理bricks
			for (int i = 0; i < bricks.length; i++) {
				for (int j = 0; j < bricks[i].length; j++) {
					if (bricks[i][j] != null) {
						bricks[i][j].selfProcess();
					}
				}
			}

			//处理rewards
			for (int i = 0; i < rewards.length; i++) {
				if (rewards[i] != null) {
					rewards[i].selfProcess();
					if (!rewards[i].isUseAble()) {
						rewards[i] = null;
					}
				}

			}
			
			//检查是否所有砖块都被打光
			boolean bricksLeft=false;
		    for(int i=0;i<bricks.length;i++)
		    {
		    	for(int j=0;j<bricks[i].length;j++)
		    	{
		    		if((bricks[i][j]!=null)
	    				&&(bricks[i][j].brickType!=Brick.FIRM)
		    			&&(bricks[i][j].currentState!=Brick.DISAPPEARED))
		    		{
		    			bricksLeft=true;
		    		}
		    	}
		    }
		    
		    if(!bricksLeft)
		    {
		    	mapPassedCounter++;
		    }
		    
		    if(mapPassedCounter>=2*1000/TIMER_INTERVAL)
		    {
		    	mapPassedCounter=0;
		    	mapPassed();
		    }

			//后续处理
			repaint();
		}

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		//draw background
		g.drawImage(background.getImage(), 0, 0, wallPanelTotalSize.width,
				wallPanelTotalSize.height, this);

		//draw bullet holes
		for (int i = 0; i < bulletHoles.length; i++) {
			if (bulletHoles[i] != null) {
				bulletHoles[i].paint(g);
			}
		}
		for (int i = 0; i < rocketHoles.length; i++) {
			if (rocketHoles[i] != null) {
				rocketHoles[i].paint(g);
			}
		}

		//draw bricks
		for (int i = 0; i < bricks.length; i++) {
			for (int j = 0; j < bricks[i].length; j++) {
				if (bricks[i][j] != null) {
					bricks[i][j].paint(g);
				}
			}
		}
		
		//draw board
		g.drawImage(boardImage,boardX,groundLine,boardSize.width,boardSize.height,this);
		
		
		//draw rewards
		for (int i = 0; i < rewards.length; i++) {
			if (rewards[i] != null) {
				rewards[i].paint(g, this);
			}
		}

		//draw ball
		ball.paint(g);

		//draw gunDisplay
		gunDisplay.paint(g);

		//draw height score
		Point scoreLocation = new Point(10, wallPanelTotalSize.height - 14);
		g.setColor(Color.RED);
		g.setFont(new Font("Times", Font.BOLD, 12));

		g.drawString("Score: " + score, scoreLocation.x,
				scoreLocation.y - 10 + 9);

		//draw ballLivesLeft
		g.setColor(Color.RED);
        g.setFont(new Font("Times",Font.BOLD,16));
        FontMetrics fm=g.getFontMetrics();
        String lives="Life: "+ballLivesLeft;
        int strLength=fm.stringWidth(lives);	
		g.drawString(lives,wallPanelSize.width-strLength-5,wallPanelSize.height-10);		
        
		//draw numOfRocket
		String rNum = "Rockets: ";
		for (int i = 0; i < totalNumOfRocket; i++) {
			rNum += "| ";
		}
		g.setColor(Color.GREEN);
		FontMetrics fm1 = g.getFontMetrics();
		int strWidth = fm1.stringWidth(rNum);

		g.drawString(rNum, (wallPanelTotalSize.width - strWidth) / 2,
				scoreLocation.y - 10 + 9);

		//draw bullet sparks
		for (int i = 0; i < sparks.length; i++) {
			if (sparks[i] != null) {
				sparks[i].paint(g);
			}
		}
		for (int i = 0; i < spark2s.length; i++) {
			if (spark2s[i] != null) {
				spark2s[i].paint(g);
			}
		}
		for (int i = 0; i < spark3s.length; i++) {
			if (spark3s[i] != null) {
				spark3s[i].paint(g);
			}
		}

		//draw gunAim
		gunAim.paint(g);

	}

	 
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mousePoint = e.getPoint();
	}

	 
	public void mouseEntered(MouseEvent e) {
		this.mousePoint = e.getPoint();

	}

	 
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		this.mousePoint = e.getPoint();
	}

	 
	public void mousePressed(MouseEvent e) {
		this.mousePoint = e.getPoint();
		attacking = true;

		if (e.getButton() == MouseEvent.BUTTON3) {
			gunAim.switchAimColor();
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			ball.switchColor();
		}

		gunAim.setCenter(e.getPoint());
		if (STARTED == getGameState()) {
			if (gun.shoot()) {
				
				gunAim.shootAdjust();
				switch (currentGun) {
				case DEAGLE: {
					playDeagleClip();
					break;
				}
				case M4: {
					playM4Clip();
					break;
				}
				case AWP: {
					awpClip.play();
					break;
				}
				case ROCKET: {
					playRocketClip();
				}
				default: {
					break;
				}
				}
				double distance = ball.getLocation().distance(e.getPoint());
				if (currentGun != ROCKET) {

					if (distance < ballRadius) {
						playRic();
						if (debug3) {
							System.out.println(ball.toString());
							System.out.println("e.getPoint(): " + e.getPoint());
							System.out.println("ball.getLocation(): "
									+ ball.getLocation());
						}

						MyPoint hitPoint = new MyPoint(e.getPoint().x
								- ball.getLocation().x, e.getPoint().y
								- ball.getLocation().y);

						ball.hitByBullet(hitPoint);

						if (debug3) {
							System.out.println(ball.toString());
							System.out.println("e.getPoint(): " + e.getPoint());
							System.out.println("ball.getLocation(): "
									+ ball.getLocation());
						}

						// 添加spark
						if (sparkEffectSwitch) 
						{
							addSpark(new Spark(hitPoint, ball.getLocation(),
									this));
						}
					} 
					else 
					{
						if (sparkEffectSwitch) {
							if (currentGun != M4) {
								addSpark2(new Spark2(new MyPoint(
										e.getPoint().x, e.getPoint().y), this));
							} else {
								addSpark2(new Spark2(new MyPoint(
										e.getPoint().x, e.getPoint().y), this,
										false));
							}
						}
						addBulletHole(new BulletHole(e.getPoint()));
					}
				} 
				else 
				{
					if (totalNumOfRocket > 0) 
					{
						MyPoint hitPoint = new MyPoint(mousePoint.x,
								mousePoint.y);
						addSpark3(new Spark3(hitPoint));
						addRocketHole(new RocketHole(hitPoint.getPoint(), this));
						ball.hitByRocket(distance, new MyPoint(hitPoint.x
								- ball.getLocation().x, hitPoint.y
								- ball.getLocation().y));
						totalNumOfRocket--;
					} 
					else 
					{
						;
					}
				}

				//处理击中reward
				for (int i = 0; i < rewards.length; i++) {
					if (rewards[i] != null) {
						if (rewards[i].contains(e.getPoint())
								&& rewards[i].isUseAble()
								&& !rewards[i].isHitted()) {
							rewards[i].setHitted(true);

							switch (rewards[i].getType()) {
							case Rewards.FIRE_BALL: {
								ball.setFireBallTrue();
								break;
							}
							case Rewards.IRON_BALL: {
								ball.setIronBallTrue();
								break;
							}
							case Rewards.ROCKET: {
								totalNumOfRocket++;
								break;
							}
							default: {
								break;
							}
							}
						}
					}
				}
			}
		}
	}

	 
	public void mouseReleased(MouseEvent e) {
		this.mousePoint = e.getPoint();
		gunAim.setCenter(e.getPoint());
		attacking = false;
	}

	 
	public void mouseDragged(MouseEvent e) {
		this.mousePoint = e.getPoint();
		gunAim.setCenter(e.getPoint());
		//处理准星
		gunAim.selfProcess();
		repaint();
		attacking = true;
	}

	 
	public void mouseMoved(MouseEvent e) {
		this.mousePoint = e.getPoint();
		gunAim.setCenter(e.getPoint());
		//处理准星
		gunAim.selfProcess();
		repaint();
	}

	 
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()=='A'||e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			boardMoveLeftRequested=true;
		}
		
		if(e.getKeyCode()=='D'||e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			boardMoveRightRequested=true;
		}

	}

	 
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_1) {
			setCurrentGun(M4);
		}
		if (e.getKeyCode() == KeyEvent.VK_2) {
			setCurrentGun(DEAGLE);
		}
		if (e.getKeyCode() == KeyEvent.VK_3) {
			setCurrentGun(AWP);
		}
		if (e.getKeyCode() == KeyEvent.VK_4) {
			setCurrentGun(ROCKET);
		}
		if (e.getKeyCode() == KeyEvent.VK_F4) {
			gunAim.switchAimColor();
		}
		if (e.getKeyCode() == KeyEvent.VK_F3) {
			sparkEffectSwitch=!sparkEffectSwitch;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			ball.switchColor();
		}
   
		//处理board的移动
		if(e.getKeyCode()=='A'||e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			boardMoveLeftRequested=false;
		}
		
		if(e.getKeyCode()=='D'||e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			boardMoveRightRequested=false;
		}
		
		//处理退出
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
		{
			int returnVal=JOptionPane.showConfirmDialog(this,new JLabel("Sure to Quit?"));
		    if(0==returnVal)
		    {
		    	gameOver();
		    }
		}

	}

	 
	public void keyTyped(KeyEvent e) {

	}

	 
	public void mouseWheelMoved(MouseWheelEvent e) {
		gunAim.switchAimColor();
	}
	
	 
	public void actionPerformed(ActionEvent e)
	{
		wallPanel.selfProcess();
	}

	private class Gun {
		public int INTERVAL = (int) (0.25 * 1000 / TIMER_INTERVAL);
		private int intervalCounter = 0;
		private boolean shootAble = true;

		private double bulletHitV = 1500;

		public double getBulletHitV() {
			return bulletHitV;
		}

		public Gun() {

		}

		public void switchGun(int type) {

			if (DEAGLE == type) {
				bulletHitV = 1700;
				INTERVAL = (int) (0.25 * 1000 / TIMER_INTERVAL);
				intervalCounter = 0;
			}

			if (M4 == type) {
				bulletHitV = 1300;
				INTERVAL = (int) (0.06 * 1000 / TIMER_INTERVAL);
				intervalCounter = 0;
			}

			if (AWP == type) {
				bulletHitV = 2500;
				INTERVAL = (int) (0.9 * 1000 / TIMER_INTERVAL);
				intervalCounter = 0;
			}
			if (ROCKET == type) {
				bulletHitV = 3000;
				INTERVAL = (int) (0.9 * 1000 / TIMER_INTERVAL);
				intervalCounter = 0;
			}

		}

		public boolean shoot() {
			if (shootAble) {
				shootAble = false;
				intervalCounter = 0;
				return true;
			} else {
				return false;
			}
		}

		public void selfProcess() {
			if (shootAble) {
				intervalCounter = 0;
			} else {
				if (intervalCounter <= INTERVAL) {
					intervalCounter++;
				} else {
					intervalCounter = 0;
					shootAble = true;
				}
			}
		}

	}

	private class Ball {

		private double Vx = 0; //按数学的直交坐标系方向为正
		private double Vy = 0; //单位是象素每秒

		private MyPoint location = null; //圆心的坐标
		private Gun gun = null;

		private Color color = Color.BLACK;
		private int colorPointer = 0;

		private boolean isFireBall = false; //是否是爆炸性的火球
		public final static int FIRE_BALL_SPAN = 20 * 1000 / TIMER_INTERVAL;
		private int fireBallCounter = 0; //用于限时　

		private boolean isIronBall = false; //是否是穿甲球
		public final static int IRON_BALL_SPAN = 20 * 1000 / TIMER_INTERVAL;
		private int ironBallCounter = 0;

		public Ball(Gun gun) {
			location = new MyPoint(wallPanelTotalSize.width / 2, groundLine
					- ballRadius);
			this.gun = gun;
		}

		public void setFireBallTrue() {
			isFireBall = true;
			fireBallCounter = 0;
		}

		public void setIronBallTrue() {
			isIronBall = true;
			ironBallCounter = 0;
		}

		public void switchColor() {
			switch (colorPointer) {
			case 0: {
				color = Color.YELLOW;
				colorPointer = 1;
				break;
			}
			case 1: {
				color = Color.BLUE;
				colorPointer = 2;
				break;
			}
			case 2: {
				color = Color.RED;
				colorPointer = 3;
				break;
			}
			case 3: {
				color = Color.WHITE;
				colorPointer = 4;

				break;
			}
			case 4: {
				color = Color.GREEN;
				colorPointer = 5;

				break;
			}
			case 5: {
				color = Color.BLACK;
				colorPointer = 0;
				break;
			}
			default: {

				break;
			}
			}
		}

		public void hitByBullet(MyPoint point) //相对于球心的击中点
		{
			double dertVx = gun.getBulletHitV() * (-point.x / ballRadius);
			double dertVy = gun.getBulletHitV() * (point.y / ballRadius);

			Vx += dertVx;
			Vy += dertVy;
		}

		public void hitByRocket(double distance, MyPoint point) {
			double dertV = gun.getBulletHitV();
			if (distance < SparkFlash2.RADIUS / 8) {
				dertV = dertV * 1.5;
			} else {
				dertV = 1.5
						* dertV
						- ((1.5 * dertV - dertV) / (7.0 / 8 * SparkFlash2.RADIUS))
						* (distance - SparkFlash2.RADIUS / 8);
				if (dertV < 0) {
					dertV = 0;
				}
			}

			Vx += dertV * (-point.x / distance);
			Vy += dertV * (point.y / distance);
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

		public void setLocation(double x, double y) {
			this.location = new MyPoint(x, y);
		}

		public String toString() {
			String output = "";
			output += "Ball: " + "\n";
			output += "location: (" + location.x + ", " + location.y + ")"
					+ "\n";
			output += "velocity: " + "Vx==" + Vx + ", " + "Vy==" + Vy + "\n";
			return output;
		}

		public void selfProcesss() {
			if (isFireBall) {
				if (fireBallCounter < FIRE_BALL_SPAN) {
					fireBallCounter++;
				} else {
					fireBallCounter = 0;
					isFireBall = false;
				}
			}

			if (isIronBall) {
				if (ironBallCounter < IRON_BALL_SPAN) {
					ironBallCounter++;
				} else {
					ironBallCounter = 0;
					isIronBall = false;
				}
			}
		}

		public void paint(Graphics g) {

			if (isIronBall) {
				if (color.equals(Color.WHITE)) {
					g.setColor(Color.YELLOW);

				} else {
					g.setColor(Color.WHITE);

				}
				int thick = 4;
				g.fillOval((int) (getLocation().x - ballRadius) - thick,
						(int) (getLocation().y - ballRadius) - thick, 2
								* ballRadius + 2 * thick, 2 * ballRadius + 2
								* thick);
			}

			if (isFireBall) {
				g.drawImage(fireBallImage,
						(int) (getLocation().x - ballRadius),
						(int) (getLocation().y - ballRadius), 2 * ballRadius,
						2 * ballRadius, wallPanel);
			} else {
				g.setColor(color);
				g.fillOval((int) (getLocation().x - ballRadius),
						(int) (getLocation().y - ballRadius), 2 * ballRadius,
						2 * ballRadius);
				g.setColor(Color.WHITE);
				if (color.equals(Color.WHITE)) {
					g.setColor(Color.YELLOW);
				}
				g.fillOval((int) (getLocation().x + ballRadius / 4),
						(int) (getLocation().y - 3 * ballRadius / 4),
						ballRadius / 2, ballRadius / 2);
			}
		}

		public boolean isFireBall() {
			return isFireBall;
		}

		public void setFireBall(boolean isFireBall) {
			this.isFireBall = isFireBall;
		}

		public boolean isIronBall() {
			return isIronBall;
		}

		public void setIronBall(boolean isIronBall) {
			this.isIronBall = isIronBall;
		}
	}

	private class GunDisplay {

		private int currentGun = DEAGLE;

		public GunDisplay(int currentGun) {
			this.currentGun = currentGun;

		}

		public void selfProcess() {

		}

		public void paint(Graphics g) {
			Point startPoint = new Point(10,
					wallPanelTotalSize.height - 66 - 10);

			g.setColor(Color.GREEN);

			g.fill3DRect(startPoint.x, startPoint.y, 20, 3, true);
			g.fill3DRect(startPoint.x, startPoint.y + 3 + 40, 20, 3, true);

			g.fill3DRect(startPoint.x + 20 + 26, startPoint.y, 20, 3, true);
			g.fill3DRect(startPoint.x + 20 + 26, startPoint.y + 3 + 40, 20, 3,
					true);

			g.fill3DRect(startPoint.x, startPoint.y + 3, 3, 15, true);
			g.fill3DRect(startPoint.x + 3 + 60, startPoint.y + 3, 3, 15, true);

			g.fill3DRect(startPoint.x, startPoint.y + 3 + 15 + 10, 3, 15, true);
			g.fill3DRect(startPoint.x + 3 + 60, startPoint.y + 3 + 15 + 10, 3,
					15, true);

			g.setFont(new Font("Times", Font.BOLD, 18));
			g.setColor(new Color(0,198,255));
			String output = "";
			switch (currentGun) {
			case DEAGLE: {
				output += "Deagle";
				break;
			}
			case M4: {
				output += "M4a1";
				break;
			}
			case AWP: {
				output += "AWP";
				break;
			}
			case ROCKET: {
				output += "Rocket";
				break;
			}
			default: {
				break;
			}
			}

			FontMetrics fm = g.getFontMetrics();
			int width = fm.stringWidth(output);
			int height = fm.getHeight();

			Point strStartPoint = new Point(startPoint.x + (66 - width) / 2,
					startPoint.y + 46 - (46 - height) / 2 - 8);
			g.drawString(output, strStartPoint.x, strStartPoint.y);
		}

		public void setCurrentGun(int type) {
			currentGun = type;
		}
	}

	private class Brick {
		private Point order = null; //用砖块在数组中的序号表示它的位置

		private Point location = null;

		private ImageObserver observer = null;

		private Spark4 spark4 = null;
		//用来描述砖块的状态
		public final static int NORMAL = 0;
		public final static int HITTED = 1;
		public int hittedCounter = 0;
		public final static int HITTED_COUNTER_SPAN = 2;
		public final static int DISAPPEARED = 2;

		private int currentState = NORMAL;

		//砖头的种类
		public final static int COMMON = 0;
		public final static int FIRM = 1;
		public final static int EXPLOSIVE = 2;
		private final static int EXPLOSIVE_HITTED_COUNTER_SPAN = 32;
		private int explosiveHittedCounter = 0;
		public final static int CHANGE_PIC_SPAN = 40;
		private int changePicCounter = 0;
		private boolean hasBombedNeighbour = false; //指明是否已经引爆了周围的砖
		private int brickType = COMMON;

		//砖头的图片组
		private Image[] imageIcons = null;
		private int imagePointer = 0;

		//砖头中藏有的rewards
		private Rewards reward = null;

		private Brick(Point order, ImageObserver observer) {
			this.order = order;
			this.observer = observer;
			int offsetX = (wallPanelTotalSize.width - brickColumNum
					* brickSize.width) / 2;
			location = new Point(offsetX + order.y * brickSize.width, order.x
					* brickSize.height);

			imagePointer = 0;

			setCurrentStateToNormal();
		}

		public Brick(Point order, int brickType, ImageObserver observer) {
			this(order, observer);
			this.brickType = brickType;
			switch (brickType) {
			case COMMON: {
				this.imageIcons = brickImages[BLUE];
				break;
			}
			case EXPLOSIVE: {
				this.imageIcons = brickImages[EXPLOSIVE_PIC];
				break;
			}
			case FIRM: {
				this.imageIcons = brickImages[FIRM_PIC];
				break;
			}
			default: {
				break;
			}

			}
		}

		public Brick(Point order, int brickType, int colorType,
				ImageObserver observer) {
			this(order, brickType, observer);
			if (COMMON == brickType) {
				if (colorType >= RED && colorType <= WHITE) {
					this.imageIcons = brickImages[colorType];
				}
			}

		}

		public Brick(Point order, int brickType, int rewardType, int colorType,
				ImageObserver observer) {
			this(order, brickType, observer);
			if (COMMON == brickType) {
				if (colorType >= RED && colorType <= WHITE) {
					this.imageIcons = brickImages[colorType];
				}
			}

			if (rewardType != Rewards.NONE) {
				this.reward = new Rewards(rewardType);
			}
		}

		public void setCurrentStateToNormal() {
			currentState = NORMAL;
			imagePointer = 0;
		}

		public void setCurrentStateToHitted() 
		{
			currentState = HITTED;
			imagePointer = 0;

			bangClip.play();
			
			if (EXPLOSIVE == brickType) {
				//添加火花
				this.spark4 = new Spark4(new MyPoint(getCenter().x,
						getCenter().y),sparkEffectSwitch);
				

			}

			if (brickType != FIRM) {
				if (this.reward != null) {
					this.reward.initialize(getCenter());
					wallPanel.addRewards(this.reward);
				}
			}
		}

		public void setCurrentStateToDisappeared() {
			currentState = DISAPPEARED;
		}

		public void selfProcess() {
			if (NORMAL == currentState) {
				if (EXPLOSIVE == brickType) {
					if (changePicCounter < CHANGE_PIC_SPAN) {
						changePicCounter++;
					} else {
						changePicCounter = 0;
						imagePointer = (imagePointer + 1) % imageIcons.length;
					}
				}
			}

			if (HITTED == currentState) {
				if (COMMON == brickType) {
					if (hittedCounter < HITTED_COUNTER_SPAN) {
						hittedCounter++;
					} else {
						hittedCounter = 0;
						imagePointer++;
						if (imagePointer >= imageIcons.length) {
							imagePointer = 0;
							hittedCounter = 0;
							setCurrentStateToDisappeared();
						}
					}
				}

				if (FIRM == brickType) {
					setCurrentStateToNormal();
				}

				if (EXPLOSIVE == brickType) {
					if (explosiveHittedCounter < EXPLOSIVE_HITTED_COUNTER_SPAN) {
						explosiveHittedCounter++;
					} else {
						imagePointer = 0;
						explosiveHittedCounter = 0;
						setCurrentStateToDisappeared();
					}

					// 引爆周围的bricks
					if ((explosiveHittedCounter >= EXPLOSIVE_HITTED_COUNTER_SPAN / 4)
							&& (!hasBombedNeighbour)) {
						Point[] neighbourOrders = {
								new Point(order.x + 1, order.y),
								new Point(order.x, order.y + 1),
								new Point(order.x - 1, order.y),
								new Point(order.x, order.y - 1),

						};

						for (int i = 0; i < neighbourOrders.length; i++) {
							if ((neighbourOrders[i].x >= 0 && neighbourOrders[i].x <= brickRowNum - 1)
									&& (neighbourOrders[i].y >= 0 && neighbourOrders[i].y <= brickColumNum - 1)) {
								Brick brick = wallPanel.bricks[neighbourOrders[i].x][neighbourOrders[i].y];
								if (brick != null
										&& NORMAL == brick.currentState) {
									brick.setCurrentStateToHitted();
								}
							}
						}
						hasBombedNeighbour = true;
					}
				}
			}

			if (DISAPPEARED == currentState) {
				;
			}

			if (spark4 != null) {
				spark4.selfProcess();
				if (!spark4.isUseAble()) {
					spark4 = null;
				}
			}
		}

		public Point getCenter() {
			return new Point(location.x + brickSize.width / 2, location.y
					+ brickSize.height / 2);
		}

		public void collisionProcess(Ball ball) {
			if (currentState != NORMAL) {
				return;
			}

			Point[] fourPoints = {
					new Point(location.x + brickSize.width, location.y
							+ brickSize.height),
					new Point(location.x + brickSize.width, location.y),
					new Point(location.x, location.y),
					new Point(location.x, location.y + brickSize.height), };

			double[] fourArcs = {
					VectorClass.arcOfPoints(getCenter(), fourPoints[0]),
					VectorClass.arcOfPoints(getCenter(), fourPoints[1]),
					VectorClass.arcOfPoints(getCenter(), fourPoints[2]),
					VectorClass.arcOfPoints(getCenter(), fourPoints[3]), };

			//使四个定位角都在[0，2*PI]的区间上
			for (int i = 0; i < fourArcs.length; i++) {
				while (fourArcs[i] < 0) {
					fourArcs[i] += Math.PI * 2;
				}
				while (fourArcs[i] >= 2 * Math.PI) {
					fourArcs[i] -= Math.PI * 2;
				}
			}
			//确定球是否碰撞
			boolean isCollided = false;
			VectorClass ballV = new VectorClass(ball.getVx(), ball.getVy(),
					true);
			if ((ball.getLocation().x >= location.x)
					&& (ball.getLocation().x <= location.x + brickSize.width)) {
				if (ball.getLocation().y >= location.y - ballRadius) {
					if (ball.getLocation().y <= location.y + brickSize.height
							+ ballRadius) {
						if ((ball.getLocation().y <= getCenter().y)
								&& (ball.getVy() < 0)) {
							isCollided = true;
						}
						if ((ball.getLocation().y > getCenter().y)
								&& (ball.getVy() > 0)) {
							isCollided = true;
						}
					}
				}
			}

			if ((ball.getLocation().y >= location.y)
					&& (ball.getLocation().y <= location.y + brickSize.height)) {
				if (ball.getLocation().x >= location.x - ballRadius) {
					if (ball.getLocation().x <= location.x + brickSize.width
							+ ballRadius) {
						if ((ball.getLocation().x <= getCenter().x)
								&& (ball.getVx() > 0)) {
							isCollided = true;
						}
						if ((ball.getLocation().x > getCenter().x)
								&& (ball.getVx() < 0)) {
							isCollided = true;
						}
					}
				}
			}

			if (ball.getLocation().x > location.x + brickSize.width) {
				if (ball.getLocation().y > location.y + brickSize.height) {
					if (ball.getLocation().distance(fourPoints[0]) <= ballRadius) {
						if (ballV.valueDecompositionBy(fourArcs[0]) < 0) {
							isCollided = true;
						}
					}
				}

				if (ball.getLocation().y < location.y) {
					if (ball.getLocation().distance(fourPoints[1]) <= ballRadius) {
						if (ballV.valueDecompositionBy(fourArcs[1]) < 0) {
							isCollided = true;
						}
					}
				}
			}

			if (ball.getLocation().x < location.x) {
				if (ball.getLocation().y > location.y + brickSize.height) {
					if (ball.getLocation().distance(fourPoints[3]) <= ballRadius) {
						if (ballV.valueDecompositionBy(fourArcs[3]) < 0) {
							isCollided = true;
						}
					}
				}

				if (ball.getLocation().y < location.y) {
					if (ball.getLocation().distance(fourPoints[2]) <= ballRadius) {
						if (ballV.valueDecompositionBy(fourArcs[2]) < 0) {
							isCollided = true;
						}
					}
				}
			}

			if (!ball.isIronBall()) {// 确定ball在brick的哪个方向
				final int D = 0;
				final int DR = 1;
				final int R = 2;
				final int RU = 3;
				final int U = 4;
				final int UL = 5;
				final int L = 6;
				final int LD = 7;

				int direction = -1;

				double ballArc = VectorClass.arcOfPoints(getCenter(), ball
						.getLocation().getPoint());
				while (ballArc < 0) {
					ballArc += Math.PI * 2;
				}
				while (ballArc >= 2 * Math.PI) {
					ballArc -= Math.PI * 2;
				}

				if ((ballArc >= 0) && (ballArc < fourArcs[1])) {
					direction = R;
				}
				if (ballArc == fourArcs[1]) {
					direction = RU;
				}
				if ((ballArc > fourArcs[1]) && (ballArc < fourArcs[2])) {
					direction = U;
				}
				if (ballArc == fourArcs[2]) {
					direction = UL;
				}
				if ((ballArc > fourArcs[2]) && (ballArc < fourArcs[3])) {
					direction = L;
				}
				if (ballArc == fourArcs[3]) {
					direction = LD;
				}
				if ((ballArc > fourArcs[3]) && (ballArc < fourArcs[0])) {
					direction = D;
				}
				if (ballArc == fourArcs[0]) {
					direction = DR;
				}
				if ((ballArc > fourArcs[0]) && (ballArc < 2 * Math.PI)) {
					direction = R;
				}

				// 根据不同的方位处理碰撞
				switch (direction) {
				case D: {
					if (isCollided) {
						ball.setVy(-ball.getVy());
					}
					break;
				}
				case DR: {
					if (isCollided) {
						ball.setVx(-ball.getVx());
						ball.setVy(-ball.getVy());
					}
					break;
				}
				case R: {
					if (isCollided) {
						ball.setVy(-ball.getVx());
					}
					break;
				}
				case RU: {
					if (isCollided) {
						ball.setVx(-ball.getVx());
						ball.setVy(-ball.getVy());
					}
					break;
				}
				case U: {
					if (isCollided) {
						ball.setVy(-ball.getVy());
					}
					break;
				}
				case UL: {
					if (isCollided) {
						ball.setVx(-ball.getVx());
						ball.setVy(-ball.getVy());
					}
					break;
				}
				case L: {
					if (isCollided) {
						ball.setVy(-ball.getVx());
					}
					break;
				}
				case LD: {
					if (isCollided) {
						ball.setVx(-ball.getVx());
						ball.setVy(-ball.getVy());
					}
					break;
				}
				default: {
					break;
				}
				}
			}
			if (isCollided) {
				this.setCurrentStateToHitted();

				if (ball.isFireBall()) {
					// 引爆周围的bricks
					Point[] neighbourOrders = {
							new Point(order.x + 1, order.y),
							new Point(order.x, order.y + 1),
							new Point(order.x - 1, order.y),
							new Point(order.x, order.y - 1),

							new Point(order.x + 1, order.y + 1),
							new Point(order.x - 1, order.y + 1),
							new Point(order.x - 1, order.y - 1),
							new Point(order.x + 1, order.y - 1), };

					for (int i = 0; i < neighbourOrders.length; i++) {
						if ((neighbourOrders[i].x >= 0 && neighbourOrders[i].x <= brickRowNum - 1)
								&& (neighbourOrders[i].y >= 0 && neighbourOrders[i].y <= brickColumNum - 1)) {
							Brick brick = wallPanel.bricks[neighbourOrders[i].x][neighbourOrders[i].y];
							if (brick != null && NORMAL == brick.currentState) {
								brick.setCurrentStateToHitted();
								brick.addDefaultSpark4();
							}
						}
					}

					//添加火花
					if (brickType != FIRM) {
						spark4 = new Spark4(new MyPoint(getCenter().x,
								getCenter().y),sparkEffectSwitch);
					}
				}
			}
			
			if(isCollided)
			{
				addScore(10);
			}

		}

		public void addDefaultSpark4() {
			if (null == spark4) {
					spark4 = new Spark4(new MyPoint(getCenter().x, getCenter().y),sparkEffectSwitch);
			}
		}

		public void paint(Graphics g) {
			if (currentState != DISAPPEARED) {
				if (COMMON == brickType || FIRM == brickType) {
					g.drawImage(imageIcons[imagePointer], location.x,
							location.y, observer);
				}

				if (EXPLOSIVE == brickType) {
					if (NORMAL == currentState) {
						g.drawImage(imageIcons[imagePointer], location.x,
								location.y, observer);
					} else {
						;
					}
				}

				if (spark4 != null) {
					spark4.paint(g);
				}

			}
		}

		public Rewards getReward() {
			return reward;
		}

		public void setReward(Rewards reward) {
			this.reward = reward;
		}
	}

	public int getGameState() {
		return gameState;
	}

	public void setGameState(int gameState) {
		this.gameState = gameState;
	}

	
	public BrickBreak.PanelConductor getPanelConductor() {
		return panelConductor;
	}

	public void setPanelConductor(BrickBreak.PanelConductor panelConductor) {
		this.panelConductor = panelConductor;
	}

	//test
	public static void main(String[] args)
	{
		
    	WallPanel wallPanel=new WallPanel();
		wallPanel.initialize();
    	
		JFrame frame=new JFrame();
    	frame.getContentPane().add(wallPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}

	public int getScore() {
		return score;
	}
	
	
}
