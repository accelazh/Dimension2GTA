package gameDisplayProcessor;

import javax.swing.*;

import java.awt.*;

import javax.swing.border.*;

import utilities.MyPoint;

import basicConstruction.*;


public class ScreenDisplayPanel extends JPanel
{
	//for debug
	private boolean debug=ControlSetting.debug ;
	//data field
	private Dimension screenSize;	
	private Point initialScreenPosition; //屏幕显示的初始位置
	private Rectangle screenArea;
	private MyPoint screenAreaLocation;
	private MapContainer landMapContainer;
	private Player player;
	private Solid[] solidsInScreen;   //用来存放屏幕中要显示的solids,约定总是不使这个数组头为null
    
	private boolean drawBackgroundPic;
	private ImageIcon none=new ImageIcon("pic/screenDisplayPanel/none.jpg");
	
	private int cycleState;
	final int LAST_CYCLESTATE=10;
	//methods
	public ScreenDisplayPanel(MapContainer landMapContainer,Player player)
	{
		this.landMapContainer=landMapContainer;
		this.player=player;
		drawBackgroundPic=false;
		
		screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		
		initializeScreenArea();
		
		solidsInScreen=new Solid[1];
		for(int i=0;i<solidsInScreen.length;i++)
		{
			solidsInScreen[i]=null;
		}
		
		//给这个面板加上一点装饰
		
		
		
		//调整布局管理器
	
		setLayout(null);
		
	
	    //处理键盘焦点
		setFocusable(false);
		//处理周期设定
		cycleState=0;
	}
	
	
	//gets and sets
	public Dimension getScreenSize()
	{
		return screenSize;
	}
	
	public Rectangle getScreenArea()
	{
		return screenArea;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	public Solid[] getSolidsInScreen()
	{
		return solidsInScreen;
	}
	
	public int getCycleState()
	{
		return cycleState;
	}
	
	//处理屏幕随着player的移动,返回屏幕应有的速度
	public VectorClass getVelocity()
	{
		double vx; //水平向右的分速度
		double vy; //水平向上的分速度
		double p=9.0/10;;  //一个参数
		
		Point screenCenter=new Point(screenArea.getLocation().x+screenArea.width/2,
				screenArea.getLocation().y+screenArea.height/2 );
	    Point playerCenter=player.getCenterPoint();
	    
	    //Calculate vx
	    int horizontalCenterDistance=playerCenter.x-screenCenter.x;
	    double horizontalRate=(double)horizontalCenterDistance/((double)screenArea.width/2);
	    if(horizontalRate>p)
	    { 
	    	if(player.getVelocity().valueDecompositionBy(0)>0&&
	    			((screenArea.getX()+screenArea.width)<=(landMapContainer.getTotalSize().width)))
	    	{
	    		vx=player.getVelocity().valueDecompositionBy(0);
	    	}
	    	else
	    	{
	    		vx=0;
	    	}
	    }
	    else
	    {
	    	if(horizontalRate<-p)
	    	{
	    		if((player.getVelocity().valueDecompositionBy(0)<0)&&(screenArea.getX()>=0))
		    	{
		    		vx=player.getVelocity().valueDecompositionBy(0);
		    	}
	    		else
	    		{
	    			vx=0;
	    		}
	    	}
	    	else
	    	{
	    	    vx=0;
	    	}
	    }
	    
	    
	    
	    
	    //Calculate vy
	    int verticalCenterDistance=playerCenter.y-screenCenter.y;
	    double verticalRate=(double)verticalCenterDistance/((double)screenArea.height/2);
	   
	    if(verticalRate>p)
	    {
	    	if(player.getVelocity().valueDecompositionBy(Math.PI/2)<0&&
	    			((screenArea.getY()+screenArea.height)<=(landMapContainer.getTotalSize().height)))
	    	{
	    		vy=player.getVelocity().valueDecompositionBy(Math.PI/2);
	    	}
	    	else
	    	{
	    		vy=0;
	    	}
	    }
	    else
	    {
	    	if(verticalRate<-p)
	    	{
	    		if(player.getVelocity().valueDecompositionBy(Math.PI/2)>0&&(screenArea.getY()>=0))
		    	{
		    		vy=player.getVelocity().valueDecompositionBy(Math.PI/2);
		    	}
	    		else
		    	{
		    		vy=0;
		    	}

	    	}
	    	else
	    	{
	    		vy=0;
	    	}
	    }
	    	    
	    //prepare the return value
	    return new VectorClass(vx,vy,true);
	
		
	}
	
	public void screenAreaMovementPerformance()
	{
		double dertX=getVelocity().valueDecompositionBy(0)
	        *ControlSetting.godTimerInterval/1000.0;
	    double dertY=getVelocity().valueDecompositionBy(-Math.PI/2)
	        *ControlSetting.godTimerInterval/1000.0;
	    screenAreaLocation=new MyPoint(screenAreaLocation.x+dertX,
	    		screenAreaLocation.y+dertY);
	    screenArea.setLocation(screenAreaLocation.getPoint());
	}
	//刷新屏幕显示的算法
	public void refreshScreenDisplay()
	{
		if(cycleState>=LAST_CYCLESTATE)
		{
			cycleState=0;
		}
		else
		{
			cycleState+=1;
		}
		removeAll();  //以后如果加上了listener，这条语句要注意会不会有副作用
		//这里可能因为上一条语句删除了listener，要再加上listener语句
		
		//把MapContainer中的effectList中的实例添加到屏幕上
		Effect[] effectList=landMapContainer.getEffectList();
		for(int i=0;i<effectList.length;i++)
		{
			if(effectList[i]!=null)
			{
				if(effectList[i] instanceof Spark1)
				{
					Spark1 spark1=(Spark1)effectList[i];
			        JLabel temp=spark1.getPicLabel();
					temp.setLocation(spark1.getLocation().getPoint().x-screenArea.getLocation().x-spark1.getTotalSize().width/2,
							spark1.getLocation().getPoint().y-screenArea.getLocation().y-spark1.getTotalSize().height/2);
					temp.setSize(temp.getPreferredSize());
													
					add(temp);
				}
					
			}
		}
		
		//把solids都添加到屏幕上
		if(debug)
		{
			System.out.println("==============================================");
			System.out.println("========In method refreshScreenDisplay========");
			System.out.println("==============================================");
		}
		
		solidsInScreen=landMapContainer.SolidsInRectangle(screenArea);
        //Debug : print the solidsInScreen
		if(debug)
		{
		    if(null==solidsInScreen)
		    {
			    System.out.println("solidsInScreen == null");
		    }
		
		    System.out.println("\n\n=======displaying solidsInScreen=======");
		    System.out.println(MapContainer.getSolidListInfo(solidsInScreen));
		    System.out.println("==finished==\n\n");
		}
	
		
		for(int i=solidsInScreen.length-1;i>=0;i--)
		{
			if(solidsInScreen[i]!=null)
			{
				if(solidsInScreen[i] instanceof Creature)
				{
				    JLabel temp=solidsInScreen[i].getPicLabel();
					temp.setLocation(solidsInScreen[i].getLocation().getPoint().x-screenArea.getLocation().x,
							solidsInScreen[i].getLocation().getPoint().y-screenArea.getLocation().y);
					temp.setSize(temp.getPreferredSize());
					
					
					if(debug)
					{
						System.out.println("Immeadiately before adding solidsInScreen["+i+"].getPicLabel\n"+
								"to screenDisplayPanel : ");
						System.out.println("temp.getLocation() : "+temp.getLocation());
						System.out.println("temp.getSize() : "+temp.getSize());
						System.out.println("temp.getPreferredSize() : "+temp.getPreferredSize());
						
					}
					
					add(temp);
				}
					
			}
		}
		//上下两个for循环，先后把生物和非生物画上
		for(int i=solidsInScreen.length-1;i>=0;i--)
		{
			if(solidsInScreen[i]!=null)
			{
				if(solidsInScreen[i] instanceof Solid)
				{
					if(solidsInScreen[i] instanceof Creature)
						;
					else
					{
						JLabel temp=solidsInScreen[i].getPicLabel();
						temp.setLocation(solidsInScreen[i].getLocation().getPoint().x-screenArea.getLocation().x,
								solidsInScreen[i].getLocation().getPoint().y-screenArea.getLocation().y);
						temp.setSize(temp.getPreferredSize());
						
						if(debug)
						{
							System.out.println("Immeadiately before adding solidsInScreen["+i+"].getPicLabel\n"+
									"to screenDisplayPanel : ");
							System.out.println("temp.getLocation() : "+temp.getLocation());
							System.out.println("temp.getSize() : "+temp.getSize());
							System.out.println("temp.getPreferredSize() : "+temp.getPreferredSize());
							
						}
						
						add(temp);
					}
					
				}
			}
		}
			
		
		if(debug)
		{
			System.out.println("==== in method refreshScreenDisplay ====");
			System.out.println("after adding labels,displaying current components \nin screenDisplayPanel : ");
			Component[] tempComponentArray =getComponents();
			for(int i=0;i<tempComponentArray.length;i++)
			{
				System.out.println(tempComponentArray[i]);
			}
			System.out.println("==display finished==\n\n\n");
		}
		
		//后续处理
		repaint();
		//根据cycleState的定期清理MapCcontainer中的EffectList
		if(LAST_CYCLESTATE==cycleState)
		{
			landMapContainer.clearUselessEffect();
		}
		
		
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(isDrawBackgroundPic())
		{
			Image imageNone=none.getImage();
			g.drawImage(imageNone,0,0,
					getSize().width,getSize().height, this);
		}
	}
	
	public Dimension getPreferredSize()
	{
		return new Dimension(screenSize.width,
				screenSize.height-30-MainGameWindow.taskBarHeight);
				
	}
    
	//这个方法根据当前的landMapContainer的边界和player的位置，自动初始化screenArea
	public void initializeScreenArea()
	{
		screenArea=new Rectangle();
		screenArea.setSize(screenSize.width,
				screenSize.height-30-MainGameWindow.taskBarHeight-MainGameWindow.titleBarHeight);
		
		if((landMapContainer.getTotalSize().width>screenArea.width )
			&&(landMapContainer.getTotalSize().height>screenArea.height ))
		{
			screenArea.x=player.getLocationRectangle().x -(screenArea.width-Human.radius)/2;
			screenArea.y=player.getLocationRectangle().y -(screenArea.height-Human.radius)/2;
		
		    if(!landMapContainer.getBounds().contains(screenArea))
		    {
		    	if(screenArea.x<0)
		    	{
		             screenArea.x=0;
		    	}
		    	else
		    	{
		    		if(screenArea.x+screenArea.width>landMapContainer.getBounds().width )
		    		{
		    			screenArea.x=landMapContainer.getBounds().width-screenArea.width;
		    			
		    		}
		    	}
		    	
		    	if(screenArea.y<0)
		    	{
		             screenArea.y=0;
		    	}
		    	else
		    	{
		    		if(screenArea.y+screenArea.height>landMapContainer.getBounds().height )
		    		{
		    			screenArea.y=landMapContainer.getBounds().height-screenArea.height;
		    			
		    		}
		    	}
		    	
		    }
		}
		else
		{
			screenArea.x= -(screenArea.width-landMapContainer.getTotalSize().width)/2;
			screenArea.y= -(screenArea.height-landMapContainer.getTotalSize().height)/2;
		}
		
		initialScreenPosition=new Point(screenArea.x,screenArea.y);
		screenAreaLocation=new MyPoint(initialScreenPosition.x,initialScreenPosition.y);
		
	}
	
	
	//这个方法负责更换地图时的初始化,参数是应传给它的新的地图
	//注意这个方法得在landMapContainer和player调整好之后再调用
	public void initWhenMapChanged(MapContainer landMapContainer)
	{    
	    //刷新自己的参数landMapContainer
	    this.landMapContainer=landMapContainer;
	    //刷新screenArea
	    initializeScreenArea();
		
	}


	public boolean isDrawBackgroundPic() {
		return drawBackgroundPic;
	}


	public void setDrawBackgroundPic(boolean drawBackgroundPic) {
		this.drawBackgroundPic = drawBackgroundPic;
	}
	
	
}
