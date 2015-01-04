package gameDisplayProcessor;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import utilities.MyPoint;
import weapon.*;

import basicConstruction.*;
import functionZones.*;
import nameConstants.*;
import shop.*;
import console.*;
import subGame.shootingPractice.*;

public class MainGameWindow extends JFrame 
implements ActionListener,KeyListener,MouseListener,MouseMotionListener,MouseWheelListener
{
	//for debug
	private static int debugCounter=0;
	private boolean debug=ControlSetting.debug;
	private boolean debug2=ControlSetting.debug2;
	private boolean debug3=ControlSetting.debug3;
	private boolean debug4=ControlSetting.debug4;
	private boolean debug0=ControlSetting.debug0;
	private boolean debug5=ControlSetting.debug5;
	private boolean debug6=ControlSetting.debug6;
	private boolean debug7=ControlSetting.debug7;
	private boolean debug8=ControlSetting.debug8;
	private boolean debug9=ControlSetting.debug9;
	
	private boolean debug11=ControlSetting.debug11;
	private boolean debug13=ControlSetting.debug13;
	
	private boolean debug16=ControlSetting.debug16;
	private boolean debug17=ControlSetting.debug17;
	private boolean debug18=ControlSetting.debug18;
	private boolean debug20=ControlSetting.debug20;
		
	//some parameters
	private final int accelerationAdjustment=4;
	//private data field
	private MapContainer landMapContainer;
	private Player player;
	
	private Timer godTimer;
	
	private ScreenDisplayPanel screenDisplayPanel;
	private ScreenMessagePanel screenMessagePanel;
	private Dimension screenSize;
	public static final int titleBarHeight=27;
	public static final int taskBarHeight=27;
	private ControlSetting controlSetting;

	//��ͼ���飬�����ŵ�ͼ
	private MapContainer[] mapContainerList;
	
	//����̨
	private Console console=new Console(null);
	//methods
	//constructors
	public MainGameWindow()
	{
		//ControlSetting created
		controlSetting=new ControlSetting();
		
		Container container=getContentPane();
		container.setLayout(new BorderLayout());
		
		mapContainerList=new MapContainer[10];
		for(int i=0;i<mapContainerList.length;i++)
		{
			mapContainerList[i]=null;
		}
		
        //Create Player
		player=new Player();
		player.setSurfaceImage(new ImageIcon("pic/human/player.jpg"));
		if(true)
		{
		RocketLauncher rocketLauncher=new RocketLauncher(10);
		player.addWeapon(rocketLauncher);
		Gun ak47=new AK47(player);
		player.addWeapon(ak47);
		Gun m4=new M4(player);
		player.addWeapon(m4);
		Gun cheapGun=new CheapGun(player);
		player.addWeapon(cheapGun);
		Gun sig552=new SIG552(player);
		player.addWeapon(sig552);
		Gun aug=new AUG(player);
		player.addWeapon(aug);
		Gun mp5=new MP5(player);
		player.addWeapon(mp5);
		Gun awp=new AWP(player);
		player.addWeapon(awp);
		Gun b13=new DesertEagle(player);
		player.addWeapon(b13);
		Gun b51=new M249(player);
		player.addWeapon(b51);
		Gun metalStorm=new MetalStorm(player);
		player.addWeapon(metalStorm);
		
		player.setNumOfHegrenade(10);
		
		}
		player.setMoney(999999);
		
		console.setPlayer(player);
		//MapContainer configure
		landMapContainer=new MapContainer();
		addToMapContainerList(landMapContainer);
		landMapContainer.setMapID(0);
		landMapContainer.setPlayer(player);
		landMapContainer.addSolid(player);
		/*addSolids
		 * here
		 */
		Solid solid2=new FloorSolid(new ImageIcon("pic/building/building1.jpg"),new MyPoint(0,0));
		solid2.setSolidName("SmallWorld");
		landMapContainer.addSolid(solid2);
		
		BuildingSolid solid3=new BuildingSolid(new ImageIcon("pic/building/building2.jpg"),new MyPoint(84,122),100,true);
		solid3.setSolidName("BulidingSolid3");
		landMapContainer.addSolid(solid3);
		
		BuildingSolid solid4=new BuildingSolid(new ImageIcon("pic/building/building3.jpg"),new MyPoint(279,259),100,true);
		solid4.setSolidName("BulidingSolid4");
		landMapContainer.addSolid(solid4);
		if(false)
		{BuildingSolid solid5=new BuildingSolid(new ImageIcon("pic/building/building4.jpg"),new MyPoint(474,0),100,true);
		solid5.setSolidName("BulidingSolid5");
		landMapContainer.addSolid(solid5);
		}
		
		Human human2=new Human();
		human2.setLocation(new Point(150,130));
		human2.setSolidName("human2");
		human2.setSurfaceImage(new ImageIcon("pic/human/human3.jpg"));
		landMapContainer.addSolid(human2);
		
		Human human3=new Human();
		human3.setLocation(new Point(160,170));
		human3.setSolidName("human3");
		human3.setSurfaceImage(new ImageIcon("pic/human/human2.jpg"));
		landMapContainer.addSolid(human3);
		
		for(int i=10;i<=24;i++)
		{
			landMapContainer.addSolid(new PickUpPlace(i,new MyPoint(800,50*(i-10))));
		}
		
		landMapContainer.addSolid(new Mine(99,new MyPoint(250,150)));
		//�����̵�
		for(int i=0;i<=6;i++)
		{
			landMapContainer.addSolid(new ShopZone(new MyPoint(650,50+i*80),new Dimension(80,59),i));
		    landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/s"+(i+1)+".jpg"),new MyPoint(650,50+i*80)));
		}
		
		int[] itemNames={
				NameConstants.CLOTHES_10,
				NameConstants.CLOTHES_11,
				NameConstants.FOOD_3,
				NameConstants.FOOD_4,
				NameConstants.WEAPON_GUN_DESERT_EAGLE,
				NameConstants.WEAPON_VEST,
		};
		landMapContainer.addSolid(new ShopZone(new MyPoint(650,50+7*80),new Dimension(80,59),itemNames));
	    landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/s"+(7+1)+".jpg"),new MyPoint(650,50+7*80)));
		
	    //����wardrobe
		landMapContainer.addSolid(new WardrobeZone(new MyPoint(880,50)));
	   
		//����ǹе��ϰ��
		landMapContainer.addSolid(new LinkToShootingPractice(new MyPoint(880,150),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap1.jpg"),new MyPoint(880,150)));
		
		landMapContainer.addSolid(new LinkToShootingPractice2(new MyPoint(880,225),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap2.jpg"),new MyPoint(880,225)));
		
		landMapContainer.addSolid(new LinkToShootingPractice3(new MyPoint(880,300),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap3.jpg"),new MyPoint(880,300)));
		
		landMapContainer.addSolid(new LinkToShootingPractice4(new MyPoint(880,375),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap4.jpg"),new MyPoint(880,375)));
		
		landMapContainer.addSolid(new LinkToShootingPractice5(new MyPoint(880,450),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap5.jpg"),new MyPoint(880,450)));
		
		landMapContainer.addSolid(new LinkToShootingPractice6(new MyPoint(880,525),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap6.jpg"),new MyPoint(880,525)));
		
		landMapContainer.addSolid(new LinkToShootingPractice7(new MyPoint(880,600),new Dimension(50,50)));
		landMapContainer.addSolid(new FloorSolid(new ImageIcon("pic/SubGame/shootingPractice/picOnMap7.jpg"),new MyPoint(880,600)));
		
		/*
		 * adding finished
		 */
		
		//create a new mapContainer
		MapContainer landMapContainer2=new MapContainer();
		addToMapContainerList(landMapContainer2);
		landMapContainer2.setMapID(1);
				
		BuildingSolid solid7=new BuildingSolid(new ImageIcon("pic/building/building2.jpg"),new MyPoint(84,322),100,true);
		solid7.setSolidName("BulidingSolid3");
		landMapContainer2.addSolid(solid7);
		
		BuildingSolid solid8=new BuildingSolid(new ImageIcon("pic/building/building3.jpg"),new MyPoint(466,579),100,true);
		solid8.setSolidName("BulidingSolid4");
		landMapContainer2.addSolid(solid8);
		
		BuildingSolid solid9=new BuildingSolid(new ImageIcon("pic/building/building4.jpg"),new MyPoint(800,0),100,true);
		solid9.setSolidName("BulidingSolid5");
		landMapContainer2.addSolid(solid9);
		
		Human human4=new Human();
		human4.setLocation(new Point(500,500));
		human4.setSolidName("human4");
		human4.setSurfaceImage(new ImageIcon("pic/human/human2.jpg"));
		landMapContainer2.addSolid(human4);
		
		
		//creating finished
		//creating a function zone
		if(true)
		{
			FloorSolid temp1=new FloorSolid(new ImageIcon("pic/bk1.jpg"),new MyPoint(1400,380));
		    landMapContainer.addSolid(temp1);
		    FunctionZone entrance=new Entrance(new MyPoint(1400,400),new Dimension(20,20),landMapContainer2,new MyPoint(20,20));
		    landMapContainer.addSolid(entrance);
		}
		if(debug)
		{
			System.out.println("====display landMapContainer when newly created====");
			System.out.println(landMapContainer);
		}
		
		
		screenDisplayPanel=new ScreenDisplayPanel(landMapContainer,player);
		screenMessagePanel=new ScreenMessagePanel(landMapContainer,player);
		
		godTimer=new Timer(ControlSetting.godTimerInterval,this);
		
		container.add(screenDisplayPanel,BorderLayout.CENTER );
		container.add(screenMessagePanel,BorderLayout.SOUTH );
		
		//get ScreenSize
		screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		setTitle("Game 2-Dimension GTA");
		setSize(screenSize.width,screenSize.height-taskBarHeight);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		setVisible(true);
		
		//launch godTimer
		godTimer.start();
	
        //������̽���
		setFocusable(true);
		//����listener
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		//�������
		Toolkit tk=Toolkit.getDefaultToolkit();
		Image temp=tk.getImage("pic/cursors/aim4.gif");
		Cursor cursor=tk.createCustomCursor(temp,new Point(0,0),"aim4");
		setCursor(cursor);
		
	}


	public void actionPerformed(ActionEvent e)
	{
		
		if(debug0)
		{
			debugCounter++;
			System.out.println("\n\n\n");
			System.out.println("********************"+"*****"+"*********");
			System.out.println("*******debugCounter "+debugCounter+" "+"********");
		    System.out.println("********************"+"*****"+"*********");
		    System.out.println("\n\n\n");
		}   
		if(debug2)
		{
			System.out.println("======= In actionPerformed ======");
		}
		//����screenDisplayPanel����
		screenDisplayPanel.refreshScreenDisplay();
		screenDisplayPanel.screenAreaMovementPerformance();
		
		
		//����screenMessagePanel����
		screenMessagePanel.refresh();
		
		
		//����Spark1��actionPerformed()
		Effect[] effectList=landMapContainer.getEffectList();
		for(int i=0;i<effectList.length;i++)
		{
			if(effectList[i] instanceof Spark1)
			{
				((Spark1)(effectList[i])).actionPerformed();
			}
		}
		
		//==================================================
		//========����player������solids���ƶ�����ײ����=========
		//==================================================
		
		// ====1���������ٶ�====

		// ����player�ļ��ٶȵ���

		Human humanToMove = player;

		if (debug17) {
			if ((humanToMove instanceof Player)
					&& (landMapContainer.getMapID() == 999)) {
				System.out.println("humanToMove is player");
				System.out.println("player.getMoveUpRequested(): "
						+ humanToMove.getMoveUpRequested());
				System.out.println("player.getMoveDownRequested(): "
						+ humanToMove.getMoveDownRequested());
				System.out.println("player.getMoveLeftRequested(): "
						+ humanToMove.getMoveLeftRequested());
				System.out.println("player.getMoveRightRequested(): "
						+ humanToMove.getMoveRightRequested());

			}
		}

		int arrowIdentifyX = 0; // �õ�С����ȷ�������Ҫ��ǰ���ķ���
		int arrowIdentifyY = 0;
		if (humanToMove.getMoveUpRequested()) {
			arrowIdentifyY += 1;
		}
		if (humanToMove.getMoveDownRequested()) {
			arrowIdentifyY += -1;
		}
		if (humanToMove.getMoveLeftRequested()) {
			arrowIdentifyX += -1;
		}
		if (humanToMove.getMoveRightRequested()) {
			arrowIdentifyX += 1;
		}

		VectorClass wantedVelocity = new VectorClass(arrowIdentifyX,
				arrowIdentifyY, true);
		if (debug2) {
			System.out.println("when wantedVelocity firstly created :");
			System.out.println("it's info : ");
			System.out.println(wantedVelocity);
		}
		if (Math.abs(wantedVelocity.getAbsoluteValue()) < 1e-6)
			;
		else {

			if (humanToMove.getFootMovingCondition() == Human.WALK) {
				wantedVelocity.setAbsoluteValue(humanToMove.getWalkMaxSpeed());
			} else {
				if (humanToMove.getFootMovingCondition() == Human.RUN) {
					wantedVelocity.setAbsoluteValue(humanToMove
							.getRunMaxSpeed());
				} else {
					wantedVelocity.setAbsoluteValue(humanToMove
							.getSpringMaxSpeed());
				}
			}
		}
		// ����wantedVelocityȷ�����,����ȷ�����ٶ�

		if (debug17) {
			if ((humanToMove instanceof Player)
					&& (landMapContainer.getMapID() == 999)) {
				System.out.println("player's wantedVelocity is: ");
				System.out.println(wantedVelocity);
			}
		}

		if (debug2) {
			System.out.println("after setting wantedVelocity : ");
			System.out.println("wantedVelocity's info :");
			System.out.println(wantedVelocity);
		}
		VectorClass newAcceleration = wantedVelocity.subtract(humanToMove
				.getVelocity());
		newAcceleration = newAcceleration.mutiplyBy(accelerationAdjustment); // �൱��dertV/0.25s

		humanToMove.setAcceleration(newAcceleration);

		if (debug17) {
			if ((humanToMove instanceof Player)
					&& (landMapContainer.getMapID() == 999)) {
				System.out.println("player's velocity is: ");
				System.out.println(humanToMove.getVelocity());

				System.out.println("player's newAcceleration is: ");
				System.out.println(humanToMove.getAcceleration());
			}
		}

		// ��������solids�ļ��ٶȵ���
			
			
			
			// ====2����ײ���====
			// �õ�solidsInScreen
					Solid[] solidsInCurrentScreen=screenDisplayPanel.getSolidsInScreen();
			for(int i=0;i<solidsInCurrentScreen.length;i++ )
			{
				if((null!=solidsInCurrentScreen[i])&&
					(!(solidsInCurrentScreen[i] instanceof Building)))
				{
								
					if(debug4)
					{
						System.out.println("=====solidsInCurrentScreen["+i+"]'s collision detecting=====");
						if(player==solidsInCurrentScreen[i])
						{
							System.out.println("(it is player)");
						}
					}
					//�½���solidsInCurrentScreen[i]Ϊ���ĵľ��ο򣬷��ؿ��е�solids
					Rectangle solidCollisionBound=solidsInCurrentScreen[i].createCollisionDetectingRectangle();
					Solid[] otherSolidsInBound=landMapContainer.SolidsInRectangle(solidCollisionBound);
					//�����ܵ���ײ������
					if(debug4)
					{
						System.out.println("Solid In solidsInCurrentScreen collision detecting bound :");
						System.out.println(MapContainer.getSolidListInfo(otherSolidsInBound));
						System.out.println("other solids in bound display finished\n\n");
					}
					boolean allPass;
					int loopNum=0;
					do
					{
						
						loopNum++;
												
						allPass=true;
						for(int j=0;j<otherSolidsInBound.length ;j++)
						{
							if((null!=otherSolidsInBound[j])&&
									(solidsInCurrentScreen[i]!=otherSolidsInBound[j]))
							{
								
								if (!(otherSolidsInBound[j] instanceof FunctionZone))
								{
									if (debug4)
									{
										System.out
												.println("Checking otherSolidsInBound["
														+ j
														+ "] as a nonfunctionZone: ");

									}
									if (solidsInCurrentScreen[i]
											.isToBeCollidedNextStep(otherSolidsInBound[j])) 
									{
										if (debug4) 
										{
											System.out
													.println("solidsInCurrentScreen["
															+ i
															+ "] is to be collided with otherSolidsInBound["
															+ j + "]");

										}
										if(debug17)
										{
											if((solidsInCurrentScreen[i] instanceof Player)&&(landMapContainer.getMapID()==999))
											{
												System.out.println("player to be collided");
											}
										}
										
										allPass = false;
										
										Solid.collisionPerformace(
												solidsInCurrentScreen[i],
												otherSolidsInBound[j]);
									}
								}
								else
								{
									if(solidsInCurrentScreen[i] instanceof Human)
									{
										if(debug5)
										{
											System.out.println("checking otherSolidsInBound["+j+"] as a functionZone");
										}
										if(solidsInCurrentScreen[i].isToBeOverlappedNextStep(otherSolidsInBound[j]))
										{
											if(debug5)
											{
												System.out.println("solidsInCurrentScreen["+i+"](player)"
														+"is to be overlapped with otherSOlidsInBound["+j+"]"
														+"(FunctionZone)");
											}
											
											if(debug18)
											{
												System.out.println("functionZone checking");
											}
											if(!((FunctionZone)(otherSolidsInBound[j])).isUseNeeded())
											{
												((FunctionZone)otherSolidsInBound[j]).functionPerformed((Human)(solidsInCurrentScreen[i]),this);
											}
											else
											{
												if(((Human)(solidsInCurrentScreen[i])).getUseRequested())
												{
													((FunctionZone)otherSolidsInBound[j]).functionPerformed((Human)(solidsInCurrentScreen[i]),this);
												}
											}
											
										}
										
									}
								}
							}
						}
						
						
					}while((!allPass)&&loopNum<30);
				}
			}	
			
			// 3��ִ���ٶȺͼ��ٶȣ�����dertV��dertS
			for(int i=0;i<solidsInCurrentScreen.length ;i++)
			{
				if((null!=solidsInCurrentScreen[i])&&
						(!(solidsInCurrentScreen[i] instanceof Building)))
				{
					solidsInCurrentScreen[i].movementPerformance();
					
				}
			}
			//����Ϊ���ⳬ����ͼ���������movementPerformance
			for(int i=0;i<solidsInCurrentScreen.length ;i++)
			{
				if((null!=solidsInCurrentScreen[i])&&
						(!(solidsInCurrentScreen[i] instanceof Building)))
				{
					Rectangle movingRect=solidsInCurrentScreen[i].getLocationRectangle();
				    Solid movingSolid=solidsInCurrentScreen[i];
					
				    if(movingRect.x<0)
					{
				    	if(debug17)
						{
							if((movingSolid instanceof Player)&&(landMapContainer.getMapID()==999))
							{
								System.out.println("player out of map");
							}
						}
				    	
						movingSolid.setLocation(new MyPoint(0,movingRect.y));
						movingSolid.setVelocity(movingSolid.getVelocity().decompositionBy(-Math.PI/2));
					}
					if(movingRect.x+movingRect.width>landMapContainer.getBounds().width)
					{
						if(debug17)
						{
							if((movingSolid instanceof Player)&&(landMapContainer.getMapID()==999))
							{
								System.out.println("player out of map");
							}
						}
						
						movingSolid.setLocation(new MyPoint(landMapContainer.getBounds().width-movingRect.width,movingRect.y));
						movingSolid.setVelocity(movingSolid.getVelocity().decompositionBy(-Math.PI/2));
					}

					if(movingRect.y<0)
					{
						if(debug17)
						{
							if((movingSolid instanceof Player)&&(landMapContainer.getMapID()==999))
							{
								System.out.println("player out of map");
							}
						}
						
						movingSolid.setLocation(new MyPoint(movingRect.x,0));
						movingSolid.setVelocity(movingSolid.getVelocity().decompositionBy(0));
					}
					if(movingRect.y+movingRect.height>landMapContainer.getBounds().height)
					{
						if(debug17)
						{
							if((movingSolid instanceof Player)&&(landMapContainer.getMapID()==999))
							{
								System.out.println("player out of map");
							}
						}
						
						movingSolid.setLocation(new MyPoint(movingRect.x,landMapContainer.getBounds().height-movingRect.height));
						movingSolid.setVelocity(movingSolid.getVelocity().decompositionBy(0));
					}
				}
				
			}
			
            //==================================================
			//=======player������solids���ƶ�����ײ���⴦�����=======
			//==================================================
			
			//==================================================
			//=======player������human�������������������=========
			//==================================================
			
			for(int i=0;i<solidsInCurrentScreen.length;i++)
			{
				if((null!=solidsInCurrentScreen)
						&&(solidsInCurrentScreen[i] instanceof Human))
				{
					if(debug6)
					{
						System.out.println("checking solidsInCurrentScreen["+i+"]'s attacking");
						if(player==solidsInCurrentScreen[i])
						{
							System.out.println("(player)");
						}
					}
					
				    Human human=(Human)solidsInCurrentScreen[i]; //���human��������Ƿ��ڹ���
				  	//���ｫ����human�Ĺ�����Ϊ
				  	Weapon currentWeapon=human.getWeaponList()[human.getCurrentWeapon()];
				    if(currentWeapon instanceof Gun)
				    {
				    	if(debug8)
				    	{
				    		if(human.getAttackRequested())
				    		{
				    			System.out.println("human attacking");
				    			System.out.println("human is : ");
				    			System.out.println(human);
				    		}
				    	}
				    	Bullet bullet=((Gun)currentWeapon).selfProcessWhenShot(human.getAttackRequested());
				       	
				    	if(debug7)
				    	{
				    		System.out.println("currentWeapon is a gun, hold by "+solidsInCurrentScreen[i].getSolidName());
				    		System.out.println("after generate the bullet : ");
				    		if(null==bullet)
				    		{
				    			System.out.println("bullet is null");
				    		}
				    		else
				    		{
				    			System.out.println(bullet);
				    		}
				    	}
				    	
				    	if (null!=bullet)
				    	{
				    		
							//bullet initialization
							bullet.initialize(screenDisplayPanel
									.getScreenArea(), screenDisplayPanel
									.getSolidsInScreen());
							if(debug9)
							{
								JOptionPane.showMessageDialog(null,"bullet != null\n"+bullet);
							}
							
							//Ϊhuman���ǹ�ڻ�
							//���ȼ���ǹ��λ��
							MyPoint gunSparkPos=((Gun)currentWeapon).getGunSparkPosition();
							landMapContainer.addSpark1(gunSparkPos);
							
							
							
							if (bullet.isHitSomething()) 
							{
								(bullet.getHittedSolid()).hittedByBullet(bullet);
								landMapContainer.addSpark1(bullet.getHitPoint());
							}
						}
				        	
				    }
				    else
				    {
				            //��ǰ��������ǹ����������
				    	if(currentWeapon instanceof HegrenadeBell)    //HegrenadeBell��human�����׵Ĵ���
				    	{
				    		if(human.getAttackRequested())
				    		{
				    			Hegrenade hegrenade=human.throwHegrenade();
				    			if(null!=hegrenade)
				    			{
				    				if(debug11)
				    				{
				    					System.out.println("Generate a Hegrenade");
				    					
				    				}
				    				
				    				hegrenade.initialize(landMapContainer);
				    				if(debug11)
				    				{
				    					System.out.println("The Hegrenade info : ");
				    					System.out.println(hegrenade);
				    				}
				    				
				    				landMapContainer.addSolid(hegrenade);
				    			}
				    		}
				    	}
				    	else
				    	{
				    		if(currentWeapon instanceof Fists)
				    		{
				    			Fists currentFists=(Fists)currentWeapon;
				    			
				    			boolean fistAttack=false;
				    			fistAttack=currentFists.attackSelfProcess(human.getAttackRequested());
				    			
				    			if(fistAttack)
				    			{
				    				currentFists.findWhoIsAttacked(screenDisplayPanel.getSolidsInScreen());
				    			    
				    				if(currentFists.isHitSomething())
				    				{
				    					landMapContainer.addSpark2(currentFists.getHitPoint());
				    					
				    					(currentFists.getHittedSolid()).hittedByFists(currentFists);
				    				}
				    			}
				    			
				    		}
				    		else
				    		{
				    			if(currentWeapon instanceof RocketLauncher)
				    			{
				    				if(human.getAttackRequested())
						    		{
						    			Rocket rocket=human.launchRocket();
						    			if(null!=rocket)
						    			{
						    				rocket.initialize(landMapContainer);
						    				landMapContainer.addSolid(rocket);
						    			}
						    		}
				    			}
				    		}
				    	}
				    }
				}
			
				
			}
		
	}
	
	public void keyReleased(KeyEvent e)
    {
        //moving detecting
		if(e.getKeyCode()==controlSetting.getPlayerMoveUp())
		{
			
			player.setMoveUpRequested(false);
			if (debug2)
			{
				System.out.println("playerMoveUp : "+player.getMoveUpRequested());
			}			
		}
		
		if(e.getKeyCode()==controlSetting.getPlayerMoveDown())
		{
			player.setMoveDownRequested(false);
		}
		if(e.getKeyCode()==controlSetting.getPlayerMoveLeft())
		{
			player.setMoveLeftRequested(false);
		}
		
		if(e.getKeyCode()==controlSetting.getPlayerMoveRight())
		{
			player.setMoveRightRequested(false);
		}
		
		if(e.getKeyCode()==controlSetting.getUsing())
		{
			player.setUseRequested(false);
		}

		//���ƴ���
		
		if (e.getKeyCode() == 'P'||e.getKeyCode() == 'p') 
		{
		    if (console.isVisible()) 
		    {
		    	console.setVisible(false);
		    } 
		    else 
		    {
		    	console.setVisible(true);
		    }
		}
		
	}
	public void keyTyped(KeyEvent e)
	{
		
	}
	public void keyPressed(KeyEvent e)
	{
		
		//moving detecting
		if(e.getKeyCode()==controlSetting.getPlayerMoveUp())
		{
			
			player.setMoveUpRequested(true);
			
		}
		
		if(e.getKeyCode()==controlSetting.getPlayerMoveDown())
		{
			player.setMoveDownRequested(true);
		}
		if(e.getKeyCode()==controlSetting.getPlayerMoveLeft())
		{
			if(debug17)
			{
				System.out.println("player.setMoveLeftRequested(true)");
			}
			player.setMoveLeftRequested(true);
		}
		
		if(e.getKeyCode()==controlSetting.getPlayerMoveRight())
		{
			player.setMoveRightRequested(true);
		}
		if(e.getKeyCode()==controlSetting.getFootMovingConditionSwitch())
		{
			player.switchFootMovingCondition();
		}
		
		if(e.getKeyCode()==controlSetting.getReloadGun())
		{
			
			Weapon currentWeapon=(player.getWeaponList()[player.getCurrentWeapon()]);
			if(currentWeapon instanceof Gun)
			{
				((Gun)currentWeapon).setReloading();
			}
		}
		
		if(e.getKeyCode()==controlSetting.getUsing())
		{
			if(debug16)
			{
				System.out.println("player.setUseRequested(true)");
			}
			player.setUseRequested(true);
		}
		
		if(e.getKeyCode()==ControlSetting.getSwitchWeapon())
		{
			player.switchCurrentWeaponForth();
		}
		
		
	}
	
	
	
	public void mouseClicked(MouseEvent e) 
	{
		if(debug16)
		{
			System.out.println("====in mouseClicked====");
			System.out.println("player.getUseRequested() == "+player.getUseRequested());
			System.out.println("mouseEvent: "+e);
		}
		//Ҫ�����
		if((player.getUseRequested())
			&&(e.getButton()==MouseEvent.BUTTON3))
		{
			Point mousePoint=new Point(e.getX()+screenDisplayPanel.getScreenArea().x,
					e.getY()+screenDisplayPanel.getScreenArea().y -titleBarHeight);
			Solid[] solidList=landMapContainer.getSolidList();
			Human aimHuman=null;
			for(int i=0;i<solidList.length;i++)
			{
				if(solidList[i] instanceof Human)
				{
					if(solidList[i].getLocationRectangle().contains(mousePoint))
					{
						aimHuman=(Human)solidList[i];
					    break;
					}
				}
			}
			
			if(aimHuman!=null)
			{
				player.duelWith(aimHuman,this);
			}
			
		}
        
        
	}

	
	public void mouseEntered(MouseEvent arg0) 
	{
		

	}

	
	public void mouseExited(MouseEvent arg0) 
	{
	

	}

	public void mousePressed(MouseEvent e)
	{
		if(e.getButton()==MouseEvent.BUTTON1)
		{
			player.setAttackRequested(true);
		 
			if(debug8)
			{
				System.out.println("mouse Button1 Pressed");
				
			}
		
		}
		if(debug8)
		{
			System.out.println("player.getAttackRequested() == "+player.getAttackRequested());
		}

	}

	
	public void mouseReleased(MouseEvent e) 
	{
	
		if(e.getButton()==MouseEvent.BUTTON1)
		{
			player.setAttackRequested(false);
			
			if(debug8)
			{
				System.out.println("mouse Button1 released");
				
			}
		}
		
		if(debug8)
		{
			System.out.println("player.getAttackRequested() == "+player.getAttackRequested());
		}

		
	}

	
	public void mouseDragged(MouseEvent e) 
	{
		player.setFaceToArc(VectorClass.arcOfPoints(player.getCenterPoint(), 
				new Point(e.getX()+screenDisplayPanel.getScreenArea().x,e.getY()+screenDisplayPanel.getScreenArea().y-titleBarHeight )));
		

	}


	public void mouseMoved(MouseEvent e) 
	{
		player.setFaceToArc(VectorClass.arcOfPoints(player.getCenterPoint(), 
				new Point(e.getX()+screenDisplayPanel.getScreenArea().x,e.getY()+screenDisplayPanel.getScreenArea().y -titleBarHeight)));
		if(debug13)
		{
			player.setMouseLocation(new Point(e.getX()+screenDisplayPanel.getScreenArea().x,
					e.getY()+screenDisplayPanel.getScreenArea().y-titleBarHeight));
			System.out.println("screenDisplayPanel.getScreenArea().x == "+screenDisplayPanel.getScreenArea().x );
			System.out.println("screenDisplayPanel.getScreenArea().y == "+screenDisplayPanel.getScreenArea().y );
			
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getWheelRotation()==1)
		{
			player.switchCurrentWeaponForth();
		}
		if(e.getWheelRotation()==-1)
		{
			player.switchCurrentWeaponBack();
		}

	}
	
	public static void main(String[] args)
	{
		MainGameWindow frame=new MainGameWindow();
	
	}


	//�����ͼ�л��ķ�����
	public void addToMapContainerList(MapContainer landMapContainer)
	{
		int i=0;
		for(i=0;i<mapContainerList.length;i++)
		{
			if(null==mapContainerList[i])
			{
				break;
			}
		}
		
		if(i<mapContainerList.length)
		{
			mapContainerList[i]=landMapContainer;
		}
		else
		{
			MapContainer[] newMapContainerList=new MapContainer[mapContainerList.length*2];
		    for(int j=0;j<newMapContainerList.length;j++)
		    {
		    	newMapContainerList[j]=null;
		    }
		    
		    System.arraycopy(mapContainerList,0,newMapContainerList,0,mapContainerList.length);
		    newMapContainerList[mapContainerList.length]=landMapContainer;
		   
		    mapContainerList=newMapContainerList;
		}
	}
	
	//���Ҳ�����Ҫ��ĵ�ͼ���򷵻ص�ǰ��ͼ 
	public MapContainer fetchMapContainer(int mapID)
	{
		MapContainer mapContainer=null;
		for(int i=0;i<mapContainerList.length;i++)
		{
			if(mapContainerList[i].getMapID()==mapID)
			{
				mapContainer=mapContainerList[i];
				break;
			}
		}
		
		if(mapContainer!=null)
		{
			return mapContainer;
		}
		else
		{
			return landMapContainer;
		}
	}
	
	//�������������ͼ�����Ұ���ָ���ĳ����㽫player���ƶ���human�������µ�ͼ
	//���humanΪnull����ֻ����player
	//ע�⣬birthPoint���ʹ���ڲ��ɽ���������Ǿ��鷳��
	public void switchMapContainer(int mapID,
			MyPoint playerBirthPoint,
			Human human,MyPoint humanBirthPoint)
	{
		//ͣ��godTimer��screenDisplayPanel setVisible(false)
		godTimer.stop();
		screenDisplayPanel.setVisible(false);
		//�л�landMapContainer
		MapContainer lastMapContainer=landMapContainer;
		landMapContainer=fetchMapContainer(mapID);
		if (lastMapContainer != landMapContainer) 
		{
			// ��player,humanװ���µ�ͼ
			landMapContainer.addSolid(player);
			if (null != human)
			{
				landMapContainer.addSolid(human);
			}
			// �ھɵ�ͼ��ɾ����Ӧplayer��human

			lastMapContainer.removeSolid(player);
			if (null != human) 
			{
				lastMapContainer.removeSolid(human);
			}
		}
	    //player �� human ��ʼ��
		player.initWhenMapChanged(playerBirthPoint);
		if(null!=human)
		{
			human.initWhenMapChanged(humanBirthPoint);
		}
		//screenDisplayPanel ��ʼ��
		screenDisplayPanel.initWhenMapChanged(landMapContainer);
		//screenMessagePanel ��ʼ��
		screenMessagePanel.initWhenMapChanged(landMapContainer);
		//screenDisplayPanel setVisible(true)
		screenDisplayPanel.setVisible(true);
		//godTimer starts
		godTimer.start();
	}
	
	
	public void switchMapContainer(MapContainer newLandMapContainer,
			MyPoint playerBirthPoint,
			Human human,MyPoint humanBirthPoint)
	{
		//ͣ��godTimer��screenDisplayPanel setVisible(false)
		godTimer.stop();
		screenDisplayPanel.setVisible(false);
		//�л�landMapContainer
		MapContainer lastMapContainer=landMapContainer;
		landMapContainer=newLandMapContainer;
		if (lastMapContainer != landMapContainer) 
		{
			// ��player,humanװ���µ�ͼ
			landMapContainer.addSolid(player);
			if (null != human) {
				landMapContainer.addSolid(human);
			}
			// �ھɵ�ͼ��ɾ����Ӧplayer��human
			lastMapContainer.removeSolid(player);
			if (null != human) {
				lastMapContainer.removeSolid(human);
			}
		}
	    //player �� human ��ʼ��
		player.initWhenMapChanged(playerBirthPoint);
		if(null!=human)
		{
			human.initWhenMapChanged(humanBirthPoint);
		}
		//screenDisplayPanel ��ʼ��
		screenDisplayPanel.initWhenMapChanged(landMapContainer);
		//screenMessagePanel ��ʼ��
		screenMessagePanel.initWhenMapChanged(landMapContainer);
		//screenDisplayPanel setVisible(true)
		screenDisplayPanel.setVisible(true);
		//godTimer starts
		godTimer.start();
	}
	//�����Ǹ����ͼ�л��ķ�����


	public Timer getGodTimer() {
		return godTimer;
	}


	public ScreenDisplayPanel getScreenDisplayPanel() {
		return screenDisplayPanel;
	}


	public MapContainer getLandMapContainer() {
		return landMapContainer;
	}


	public Player getPlayer() {
		return player;
	}
	
}




/*������ܻ�����Ϊ�������������ܼ����¼���������⣬���Ҳ�ܷ���������
 * 
 * ��ν������human������ͼ�����⣿
 * 
 * solid�е�collisionPerformance������ÿ�μ����µ�����Ĺ����Ӧ�ø���
 *
 *�����µ�ʵ�����е�ʱ���췽��Ҫ�󴫸�ĳ��ʵ������location�������ã�ע������������ʵ��(��location)ȥ����
 *���ʵ�����Ⱥ󴴽�������ʵ������ͬһ��location���ᷢ�����µĴ���
 *
 *new ��ΰѻ𻨵�timer������ת�޸�godTimer�������üӿ������ٶȣ���Timerһ������ʹЧ�ʱ�úܵͣ�Զ������������������Ӱ�죩
 *
 *new2 ���ڵ���deulManager
 *
 *gun and ball ��Ϸ������������ģʽ��һ�ִ�ת�飬һ�ֿ�˭��ø�
 */
