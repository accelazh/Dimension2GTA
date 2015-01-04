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
	private Point initialScreenPosition; //��Ļ��ʾ�ĳ�ʼλ��
	private Rectangle screenArea;
	private MyPoint screenAreaLocation;
	private MapContainer landMapContainer;
	private Player player;
	private Solid[] solidsInScreen;   //���������Ļ��Ҫ��ʾ��solids,Լ�����ǲ�ʹ�������ͷΪnull
    
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
		
		//�����������һ��װ��
		
		
		
		//�������ֹ�����
	
		setLayout(null);
		
	
	    //������̽���
		setFocusable(false);
		//���������趨
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
	
	//������Ļ����player���ƶ�,������ĻӦ�е��ٶ�
	public VectorClass getVelocity()
	{
		double vx; //ˮƽ���ҵķ��ٶ�
		double vy; //ˮƽ���ϵķ��ٶ�
		double p=9.0/10;;  //һ������
		
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
	//ˢ����Ļ��ʾ���㷨
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
		removeAll();  //�Ժ����������listener���������Ҫע��᲻���и�����
		//���������Ϊ��һ�����ɾ����listener��Ҫ�ټ���listener���
		
		//��MapContainer�е�effectList�е�ʵ����ӵ���Ļ��
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
		
		//��solids����ӵ���Ļ��
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
		//��������forѭ�����Ⱥ������ͷ����ﻭ��
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
		
		//��������
		repaint();
		//����cycleState�Ķ�������MapCcontainer�е�EffectList
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
    
	//����������ݵ�ǰ��landMapContainer�ı߽��player��λ�ã��Զ���ʼ��screenArea
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
	
	
	//����������������ͼʱ�ĳ�ʼ��,������Ӧ���������µĵ�ͼ
	//ע�������������landMapContainer��player������֮���ٵ���
	public void initWhenMapChanged(MapContainer landMapContainer)
	{    
	    //ˢ���Լ��Ĳ���landMapContainer
	    this.landMapContainer=landMapContainer;
	    //ˢ��screenArea
	    initializeScreenArea();
		
	}


	public boolean isDrawBackgroundPic() {
		return drawBackgroundPic;
	}


	public void setDrawBackgroundPic(boolean drawBackgroundPic) {
		this.drawBackgroundPic = drawBackgroundPic;
	}
	
	
}
