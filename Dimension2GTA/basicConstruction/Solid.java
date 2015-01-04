package basicConstruction;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;
import weapon.*;
import gameDisplayProcessor.*;

public abstract class Solid {
	//for debug
	private static boolean debug=ControlSetting.debug;
	private static boolean debug4=ControlSetting.debug4;
	// private date field
	private Dimension totalSize;

	private Icon surfaceImage;
    private Icon usualSurfaceImage;  //假如human已经died，那么在他复活的时候，恢复旧的surfaceImage就得靠这个了
    
	private MyPoint location;  //用于表明在大地图中的位置

	private int healthPoint;

	private JLabel picLabel;

	private boolean solidVisible;

	private String solidName;
 
	private VectorClass velocity;     //单位 ：像素/s
	private VectorClass acceleration;   //单位 ：像素/(s*s)
	private double mass;    //solid的质量Kg，
	// methods
	// Constructor
	public Solid() 
	{
		this(new ImageIcon("pic/default1.jpg"),new MyPoint(100,100),100,true);
	}

	public Solid(Icon surfaceImage, MyPoint location, int healthPoint,
			boolean solidVisible) 
	{
		this.surfaceImage = surfaceImage;
		this.usualSurfaceImage=surfaceImage;
		this.location = location;
		this.healthPoint = healthPoint;
		this.solidVisible = solidVisible;
        
		if(null!=surfaceImage)
        {	
        	this.totalSize = new Dimension(surfaceImage.getIconWidth(),
        		surfaceImage.getIconHeight());
        }
		else
		{
			this.totalSize=new Dimension(100,100);
		}
        
		    this.picLabel = new JLabel();
		picLabel.setIcon(surfaceImage);
		picLabel.setPreferredSize(totalSize);
		picLabel.setSize(totalSize);
		picLabel.setHorizontalAlignment(SwingConstants.CENTER);
		picLabel.setVerticalAlignment(SwingConstants.CENTER);
		picLabel.setVisible(this.solidVisible);
		
		this.velocity =new VectorClass(0,0);
		this.acceleration =new VectorClass(0,0);
		this.mass=60;
		
		this.solidName = "Nonnamed solid"; 
	}

	// sets and gets
	public void setTotalSize(Dimension totalSize) {
		this.totalSize = totalSize;
	}

	public void setSurfaceImage(Icon surfaceImage) {
		this.surfaceImage = surfaceImage;
		this.usualSurfaceImage=surfaceImage;
		
		this.totalSize = new Dimension(surfaceImage.getIconWidth(),
				surfaceImage.getIconHeight());
		
		picLabel.setIcon(surfaceImage);
		picLabel.setPreferredSize(totalSize);
		picLabel.setSize(totalSize);
		
	}

	public void setLocation(MyPoint location) {
		this.location = location;
	}
	
	public void setLocation(Point location)
	{
		this.location=new MyPoint(location.x,location.y);
	}

	public void setHealthPoint(int healthPoint) {
		this.healthPoint = healthPoint;
	}

	public void setSolidVisible(boolean b) {
		this.solidVisible = b;
		picLabel.setVisible(this.solidVisible);
	}

	public void setSolidName(String s) {
		this.solidName = s;
	}

	public void setAcceleration(VectorClass a)
	{
		this.acceleration=a;
	}
	
	public void setVelocity(VectorClass v)
	{
		this.velocity=v;
	}
	
	public void setMass(double m)
	{
		this.mass=m;
	}
	
	public Dimension getTotalSize() {
		return totalSize;
	}

	public Icon getSurfaceImage() {
		return surfaceImage;
	}

	public MyPoint getLocation() {
		return location;
	}

	public int getHealthPoint() {
		return healthPoint;
	}

	public boolean isSolidVisible() {
		return solidVisible;
	}

	public String getSolidName() {
		return solidName;
	}
	
	public JLabel getPicLabel()
	{
		return picLabel;
	}

	public VectorClass getAcceleration()
	{
		return acceleration;
	}
	
	public VectorClass getVelocity()
	{
		return velocity;
	}
	
	public double getMass()
	{
		return mass;
	}
	
	public MyPoint getDoubleCenterPoint()
	{
		return new MyPoint(getLocation().x+getTotalSize().getWidth()/2.0,
				getLocation().y+getTotalSize().getHeight()/2.0);
	}
	
	public Point getCenterPoint()
	{
		return getDoubleCenterPoint().getPoint();
	}
	
	// other methods
	public boolean isAccesible(MyPoint point) // 这里的点指相对图片左上角的位置，反映给定的一点能不能进入
	{
		if((point.x>=0)&&(point.x<=totalSize.getWidth()))
		{
			if((point.y>=0)&&(point.y<=totalSize.getHeight()))
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		else
		{
			return true;
		}
		
	}
	
	public boolean isAccesible(Point point) // 这里的点指相对图片左上角的位置，反映给定的一点能不能进入
	{
		if((point.x>=0)&&(point.x<=totalSize.getWidth()))
		{
			if((point.y>=0)&&(point.y<=totalSize.getHeight()))
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		else
		{
			return true;
		}
	}

	public boolean isAccesibleFromMap(MyPoint point)  //这里的点指相对大地图左上角的位置
	{
		if((point.x>=location.x )&&(point.x<=location.x+totalSize.getWidth()))
		{
			if((point.y>=location.y)&&(point.y<=location.y+totalSize.getHeight()))
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		else
		{
			return true;
		}
	}
	
	public boolean isAccesibleFromMap(Point point)
	{
		if((point.x>=location.x )&&(point.x<=location.x+totalSize.getWidth()))
		{
			if((point.y>=location.y)&&(point.y<=location.y+totalSize.getHeight()))
			{
				return false;
			}
			else
			{
				return true;
			}
			
		}
		else
		{
			return true;
		}
	}
	
	public boolean isInLocationRectangle(MyPoint point)   //这个方法指明指定点是否在locationRectangle中,这里的点指相对大地图左上角的位置，
	{
		if(debug)
		{
			System.out.println("in method isInLocationRectangle in Solid");
		}
		if((point.x>=getLocationRectangle().x)&&(point.x<=getLocationRectangle().x+getLocationRectangle().width))
		{
			if(debug)
			{
				System.out.println("test1 passed");
			}
			if((point.y>=getLocationRectangle().y)&&(point.y<=getLocationRectangle().y+getLocationRectangle().height ))
			{
				if(debug)
				{
					System.out.println("test2 passed");
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	
	public boolean isInLocationRectangle(Point point)   //这个方法指明指定点是否在locationRectangle中,这里的点指相对大地图左上角的位置，
	{
		if(debug)
		{
			System.out.println("in method isInLocationRectangle in Solid");
		}
		if((point.x>=getLocationRectangle().x)&&(point.x<=getLocationRectangle().x+getLocationRectangle().width))
		{
			if(debug)
			{
				System.out.println("test1 passed");
			}
			if((point.y>=getLocationRectangle().y)&&(point.y<=getLocationRectangle().y+getLocationRectangle().height ))
			{
				if(debug)
				{
					System.out.println("test2 passed");
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
		
	}
	
	

	public String toString() 
	{
		String output;
	    output="solidName : "+solidName;
	    if(this instanceof Creature)
	    {
	    	output+="  (Creature)\n";
	    }
	    else
	    {
	    	output+="  (Noncreature)\n";
	    }
	    output+="totalSize : "+totalSize.toString()+ "\n";
	    output+="surfaceImage : "+surfaceImage.getIconWidth() +"*"+surfaceImage.getIconHeight()+"\n";
	    output+="usualSurfaceImage : "+usualSurfaceImage.getIconWidth() +"*"+usualSurfaceImage.getIconHeight()+"\n";
	    output+="location : "+location+"\n";
	    output+="healthPoint : "+healthPoint+"\n";
	    output+="picLabel : "+picLabel.toString()+"\n";
	    output+="solidVisible : "+solidVisible+"\n";
	    output+="velocity : "+velocity+"\n";
	    output+="acceleration : "+acceleration+"\n";
	    output+="\n";
	    
	    return output;		
	}
	
	public Rectangle getLocationRectangle()
	{
		Rectangle locationRectangle=new Rectangle();
		locationRectangle.setSize(totalSize);
		locationRectangle.setLocation(location.getPoint());
		
		return locationRectangle;
		
	}

	//根据velocity和acceleration实现运动
	public void movementPerformance()
	{
		//按照从s到v到a的顺序
		double dertX=velocity.valueDecompositionBy(0)
		    *ControlSetting.godTimerInterval/1000.0;
		double dertY=velocity.valueDecompositionBy(-Math.PI/2)
		    *ControlSetting.godTimerInterval/1000.0;
		setLocation(new MyPoint(dertX+location.x,dertY+location.y));
		
		
		double dertVx=acceleration.valueDecompositionBy(0)
		    *ControlSetting.godTimerInterval /1000.0;
		double dertVy=acceleration.valueDecompositionBy(Math.PI/2)
		    *ControlSetting.godTimerInterval /1000.0;
		velocity=new VectorClass(velocity.valueDecompositionBy(0)+dertVx,
			velocity.valueDecompositionBy(Math.PI/2)+dertVy,true);
		
		
	}
	
    //这个方法建立一个以solid为中心的矩形匡，方便检测可能和它相撞的solids
	//以及可能进入的FunctionZone。
	public Rectangle createCollisionDetectingRectangle()
	{
		final int sensitiveDistance=10; 
		
		Rectangle solidRect=new Rectangle();
		solidRect.setLocation(getLocation().getPoint().x-sensitiveDistance,
				getLocation().getPoint().y-sensitiveDistance);
		solidRect.setSize(getTotalSize().width+2*sensitiveDistance,
				getTotalSize().height+2*sensitiveDistance);
		
		return solidRect;
		
		
	}
    
	//这个方法检查自己solid是否会和给定的solid在下一个godTimer激活时相撞
	public boolean isToBeCollidedNextStep(Solid solid)
	{
		if(debug4)
		{
			System.out.println("===in method isToBeCollidedNextStep===");
			System.out.println("this == "+this.toString());
			System.out.println("solid == "+solid);
		}
		if(null==solid)
		{
			return false;
		}
		boolean isToBeCollided;
		if((this instanceof Building)&&
				(solid instanceof Building))
		{
			isToBeCollided=false;
		}
		else
		{
			if(solid instanceof Building)
			{
				if(debug4)
				{
					System.out.println("in branch this=nonbuilding, solid=building : ");
				}
				 
				double dertX=velocity.valueDecompositionBy(0)
			        *ControlSetting.godTimerInterval/1000.0;
			    double dertY=velocity.valueDecompositionBy(-Math.PI/2)
			        *ControlSetting.godTimerInterval/1000.0;
			    MyPoint oldLocation=new MyPoint(location.x,location.y);
			    setLocation(new MyPoint(dertX+location.x,dertY+location.y));
			    
			    if(debug4)
			    {
			    	System.out.println("this.oldLocation : "+oldLocation);
			    	System.out.println("this.newLocation : "+location);
			    }
			    
			    isToBeCollided=MapContainer.isACollidingWithB(this,solid);
			    if(debug4)
			    {
			    	System.out.println("after invoking method isACollidingWithB :");
			    	System.out.println("isToBeCollided == "+isToBeCollided);
			    }
			    setLocation(oldLocation);
			    if(debug4)
			    {
			    	System.out.println("branch out");
			    }
			}
			else
			{
				if(this instanceof Building)
				{
					double solidDertX=solid.getVelocity().valueDecompositionBy(0)
			            *ControlSetting.godTimerInterval/1000.0;
			        double solidDertY=solid.getVelocity().valueDecompositionBy(-Math.PI/2)
			            *ControlSetting.godTimerInterval/1000.0;
			        MyPoint solidOldLocation=new MyPoint(solid.getLocation().x,solid.getLocation().y);
			        solid.setLocation(new MyPoint(solidDertX+solid.getLocation().x,solidDertY+solid.getLocation().y));
			    
			        isToBeCollided=MapContainer.isACollidingWithB(this,solid);
			        solid.setLocation(solidOldLocation);
				}
				else
				{
					if(debug4)
					{
						System.out.println("in branch both-are-nonbuildings :");
					}
					double dertX=velocity.valueDecompositionBy(0)
			           *ControlSetting.godTimerInterval/1000.0;
			        double dertY=velocity.valueDecompositionBy(-Math.PI/2)
			            *ControlSetting.godTimerInterval/1000.0;
			        MyPoint oldLocation=new MyPoint(location.x,location.y);
			        setLocation(new MyPoint(dertX+location.x,dertY+location.y));
			    
			        if(debug4)
				    {
				    	System.out.println("this.oldLocation : "+oldLocation);
				    	System.out.println("this.newLocation : "+location);
				    } 
			        
			        double solidDertX=solid.getVelocity().valueDecompositionBy(0)
		                *ControlSetting.godTimerInterval/1000.0;
		            double solidDertY=solid.getVelocity().valueDecompositionBy(-Math.PI/2)
		                *ControlSetting.godTimerInterval/1000.0;
		            MyPoint solidOldLocation=new MyPoint(solid.getLocation().x,solid.getLocation().y);
		            solid.setLocation(new MyPoint(solidDertX+solid.getLocation().x,solidDertY+solid.getLocation().y));
		    
		            if(debug4)
				    {
				    	System.out.println("solid.oldLocation : "+solidOldLocation);
				    	System.out.println("solid.newLocation : "+solid.getLocation());
				    }
		            
		            isToBeCollided=MapContainer.isACollidingWithB(this,solid);
		            
		            if(debug4)
		            {
		            	System.out.println("after invoking method isACollidingWithB :");
				    	System.out.println("isToBeCollided == "+isToBeCollided);
		            }
		            
		            setLocation(oldLocation);
		            solid.setLocation(solidOldLocation);
		            
		            
		            //这里增加一个判定相撞的条件,判断两个对象是不是相对速度互相靠近

		            if(debug4)
		            {
		            	System.out.println("the additional judgement : ");
		            	System.out.println("display soilds info : ");
		            	System.out.println("this : \n"+this);
		            	System.out.println("solid : \n"+solid);
		            }
		            double tsArc=VectorClass.arcOfPoints(this.getDoubleCenterPoint(), solid.getDoubleCenterPoint());
		            double solidVx=solid.getVelocity().valueDecompositionBy(tsArc);
		            double thisVx=(this.velocity).valueDecompositionBy(tsArc);
		            
		            if(debug4)
		            {
		            	System.out.println("tsArc == " +tsArc/Math.PI+"PI");
		            	System.out.println("solidVx == "+solidVx);
		            	System.out.println("thisVx == "+thisVx);
		            }
		            
		            boolean isComingClose;
		            if(thisVx-solidVx>0)
		            {
		            	isComingClose=true;
		            }
		            else
		            {
		            	isComingClose=false;
		            }
		            
		            if(debug4)
		            {
		            	System.out.println("isComingClose == "+isComingClose);
		            }
		            
		            isToBeCollided=isToBeCollided&&isComingClose;
	
		            if(debug4)
		            {
		            	System.out.println("isToBeCollided at last == "+isToBeCollided);
		            	
		            }
		         
			
					
				}
			}
		}
		if(debug4)
		{
			System.out.println("===ending method isToBeCollidedNextStep===");
		}
		return isToBeCollided;
	}

	public boolean isToBeOverlappedNextStep(Solid solid)
	{
		if(null==solid)
		{
			return false;
		}
		boolean isToBeOverlapped;
		if((this instanceof Building)&&
				(solid instanceof Building))
		{
			//忽略对building的检查
			isToBeOverlapped=false;
		}
		else
		{
			if(solid instanceof Building)
			{
				 
				double dertX=velocity.valueDecompositionBy(0)
			        *ControlSetting.godTimerInterval/1000.0;
			    double dertY=velocity.valueDecompositionBy(-Math.PI/2)
			        *ControlSetting.godTimerInterval/1000.0;
			    MyPoint oldLocation=new MyPoint(location.x,location.y);
			    setLocation(new MyPoint(dertX+location.x,dertY+location.y));
			    
			    isToBeOverlapped=MapContainer.isAOverlappedWithB(this,solid);
			    setLocation(oldLocation);
			}
			else
			{
				if(this instanceof Building)
				{
					double solidDertX=solid.getVelocity().valueDecompositionBy(0)
			            *ControlSetting.godTimerInterval/1000.0;
			        double solidDertY=solid.getVelocity().valueDecompositionBy(-Math.PI/2)
			            *ControlSetting.godTimerInterval/1000.0;
			        MyPoint solidOldLocation=new MyPoint(solid.getLocation().x,solid.getLocation().y);
			        solid.setLocation(new MyPoint(solidDertX+solid.getLocation().x,solidDertY+solid.getLocation().y));
			    
			        isToBeOverlapped=MapContainer.isAOverlappedWithB(this,solid);
			        solid.setLocation(solidOldLocation);
				}
				else
				{
					double dertX=velocity.valueDecompositionBy(0)
			           *ControlSetting.godTimerInterval/1000.0;
			        double dertY=velocity.valueDecompositionBy(-Math.PI/2)
			            *ControlSetting.godTimerInterval/1000.0;
			        MyPoint oldLocation=new MyPoint(location.x,location.y);
			        setLocation(new MyPoint(dertX+location.x,dertY+location.y));
			    
			             
			        double solidDertX=solid.getVelocity().valueDecompositionBy(0)
		                *ControlSetting.godTimerInterval/1000.0;
		            double solidDertY=solid.getVelocity().valueDecompositionBy(-Math.PI/2)
		                *ControlSetting.godTimerInterval/1000.0;
		            MyPoint solidOldLocation=new MyPoint(solid.getLocation().x,solid.getLocation().y);
		            solid.setLocation(new MyPoint(solidDertX+solid.getLocation().x,solidDertY+solid.getLocation().y));
		    
		            isToBeOverlapped=MapContainer.isAOverlappedWithB(this,solid);
		            setLocation(oldLocation);
		            solid.setLocation(solidOldLocation);
		            
					
				}
			}
		}
		return isToBeOverlapped;
		
	}

	//这个方法解决当两个solid撞上的时候的处理办法
	public static void collisionPerformace(Solid A,Solid B)
	{
		if(debug4)
		{
			System.out.println("====in method collisionPerformance====");
			System.out.println("Solid A :");
			System.out.println(A);
			System.out.println("Solid B :");
			System.out.println(B);
			
		}
		if(null==A||null==B)
		{
			return;
		}
		
		if(A instanceof Rocket)
		{
			((Rocket)A).setCollided(true);
		}
		if(B instanceof Rocket)
		{
			((Rocket)B).setCollided(true);
		}
		
		if((A instanceof Building)&&(B instanceof Building))
		{
			;
		}
		else
		{
			if((A instanceof Building)&&(!(B instanceof Building)))
			{
				B.setVelocity(new VectorClass(0,0));
			}
			else
			{
				if((B instanceof Building)&&(!(A instanceof Building)))
				{
					A.setVelocity(new VectorClass(0,0));
				}
				else
				{
					if((!(A instanceof Building))&&(!(B instanceof Building)))
					{
						if(debug4)
						{
							System.out.println("in branch both A and B are humans");
							
						}
						
                        double e=1;//碰撞的弹性系数
						
						Solid humanA=A;
						Solid humanB=B;
						
						//求向量AB的方向角
						double arcAB=VectorClass.arcOfPoints(humanA.getDoubleCenterPoint(),
								humanB.getDoubleCenterPoint());
					
						//求A,B关于向量AB方向的投影速度分量
						double humanAVx=humanA.getVelocity().valueDecompositionBy(arcAB);
						double humanAVy=humanA.getVelocity().valueDecompositionBy(arcAB+Math.PI/2);
						
						double humanBVx=humanB.getVelocity().valueDecompositionBy(arcAB);
						double humanBVy=humanB.getVelocity().valueDecompositionBy(arcAB+Math.PI/2);
					
						//求出碰撞后的humanAVx,humanBVx(humanAVy,humanBVy不变)
						double humanAVx2;
						double humanBVx2;
						
						humanAVx2=humanAVx+(1+e)*(humanB.getMass()/(humanA.getMass()+humanB.getMass()))*(humanBVx-humanAVx);
						humanBVx2=humanBVx+(1+e)*(humanA.getMass()/(humanA.getMass()+humanB.getMass()))*(humanAVx-humanBVx);
						
						//得到碰撞后的速度
						VectorClass humanAVx2Vector=new VectorClass(humanAVx2,arcAB);
						VectorClass humanAVy2Vector=new VectorClass(humanAVy,arcAB+Math.PI/2);
						VectorClass humanAV2=humanAVx2Vector.addition(humanAVy2Vector);
						
						VectorClass humanBVx2Vector=new VectorClass(humanBVx2,arcAB);
						VectorClass humanBVy2Vector=new VectorClass(humanBVy,arcAB+Math.PI/2);
						VectorClass humanBV2=humanBVx2Vector.addition(humanBVy2Vector);
						
						humanA.setVelocity(humanAV2);
						humanB.setVelocity(humanBV2);
					}
					
					
				}
			}
		}
		if(debug4)
		{
			System.out.println("method collisionPerformance ended");
		}
		
	}

	public abstract void hittedByBullet(Bullet bullet) ;
	
	public abstract void hurtByHegrenade(MyPoint explosionPoint);
	
	
	public abstract void hittedByFists(Fists fists);
	

	public abstract void hurtByValue(int value);
	
	public abstract void hurtByMine(MyPoint point,int hurtValue);
	
	public abstract void hurtByRocket(MyPoint point);
	
	
	public Icon getUsualSurfaceImage() {
		return usualSurfaceImage;
	}
	
}
