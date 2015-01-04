package subGame.breakBrick.welcomePanel;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.util.*;
import javax.swing.ImageIcon;
import utilities.*;
import animatedGUI.*;
import java.awt.event.*;

import subGame.breakBrick.*;
import subGame.breakBrick.*;
//i.e. outerBk in welcomePanel
public class OuterBackgroundPanel extends APanel 
implements Constants, AMouseMotionListener
{
	

	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=false;

	private ArrayList<Firework> fireworks=new ArrayList<Firework>();
	private double fai=Math.PI/2;   //这两个量描述坐标平面法向量
	private double thita=Math.PI; 
	private static final double distance=600;
	private PlaneCoordinateSystemIn3D plane;
	private static final int OFFSET=100;
	private Dimension panelSize;
	
	//相应鼠标拖动
	private double step=1.0/180*Math.PI;//控制移动步长
	private Point lastMousePoint;
	private int refreshPointCounter=0;  //隔几次更新一下鼠标点
	
	//开关
	private boolean open=false;
	
	//控制左右旋转烟花的按键的状态
	private boolean keyLeft=false;
	private boolean keyRight=false;
	private boolean keyUp=false;
	private boolean keyDown=false;
	
	//控制出现烟花的技术器
	int fireworkCounter=0;

	public OuterBackgroundPanel(Dimension panelSize)
    {
    	super();
    	this.panelSize=panelSize;
    	lastMousePoint=new Point(panelSize.width/2,panelSize.height/2);
    	constructPlane();
    	addAMouseMotionListener(this);
    }
	
	public OuterBackgroundPanel(ImageIcon imageIcon, Point location,Dimension panelSize)
	{
	    super(imageIcon,location);
	    this.panelSize=panelSize;
	    lastMousePoint=new Point(panelSize.width/2,panelSize.height/2);
	    constructPlane();
	    addAMouseMotionListener(this);
	}
	    
	public OuterBackgroundPanel(ImageIcon imageIcon, AAnimationClip animationClip, int mode,Dimension panelSize)
	{
	    super(imageIcon,animationClip,mode);  
	    this.panelSize=panelSize;
	    lastMousePoint=new Point(panelSize.width/2,panelSize.height/2);
	    constructPlane();
	    addAMouseMotionListener(this);
	}

	//建立plane
	private void constructPlane()
	{
		
		if(debug)
		{
			System.out.println("====in constructPlane of OuterBackgroundPanel====");
		   
			System.out.println("fai: "+fai);
			System.out.println("thita: "+thita);
		}
		
		//计算法向量n
		MyVector3D I=new MyVector3D(1,0,0).mutiply(Math.sin(fai)*Math.cos(thita));
		MyVector3D J=new MyVector3D(0,1,0).mutiply(Math.sin(fai)*Math.sin(thita));
		MyVector3D K=new MyVector3D(0,0,1).mutiply(Math.cos(fai));
				
		MyVector3D n=I.addition(J.addition(K));
		
		//计算坐标平面的相关参数
		MyPoint3D center=new MyPoint3D(distance/2,panelSize.width/2,panelSize.height);
		center.move(n.toUnitVector().mutiply(-distance/2));
		MyVector3D grossI=new MyVector3D(n.getY(),-n.getX(),0);
		MyVector3D grossJ=n.outerProduct(grossI);
		
		center.move(grossI.toUnitVector().mutiply(-panelSize.width/2));
		plane=new PlaneCoordinateSystemIn3D(center,grossI,grossJ);
	
		if(debug)
		{
			System.out.println("====end of constructPlane of OuterBackgroundPanel====");
		}
		
	}
	
	protected void selfProcess()
	{
		super.selfProcess();
	
		if(!open)
		{
			fireworks.clear();
		}
		
		if((open)&&(fireworkCounter>4*1000/subGame.breakBrick.Constants.TIMER_INTERVAL))
		{
			fireworkCounter=0;
			
            MyVector3D arrow=new MyVector3D(0,0,1);
			
			//计算三个坐标单位向量
			MyVector3D roughI=new MyVector3D(arrow.getZ(),0,-arrow.getX());
			MyVector3D roughJ=roughI.outerProduct(arrow);
		
			MyVector3D I=roughI.toUnitVector().mutiply(Math.random()*0.1*((Math.random()>0.5)?1:-1));
			MyVector3D J=roughJ.toUnitVector().mutiply(Math.random()*0.1*((Math.random()>0.5)?1:-1));
			MyVector3D K=arrow.toUnitVector();
			
			MyVector3D randomArrow=K.addition(I.addition(J));
			MyPoint3D startPoint=new MyPoint3D(distance*Math.random(),panelSize.width*(1.0/3+1.0/3*Math.random()),OFFSET*Math.random());
			
			Firework firework=null;
			fireworks.add(firework=new Firework(startPoint,randomArrow, (Math.random()>0.5)?true:false,true));
	        firework.setBoomClip(OuterBackgroundPanel.class.getResource("firework.wav"));
		}
		else
		{
			fireworkCounter++;
		}
		
		for(int i=0;i<fireworks.size();i++)
		{
			Firework firework=fireworks.get(i);
			if(firework!=null)
			{
				firework.selfProcess();
				if(!firework.isUseable())
				{
					fireworks.remove(i);
				}
			}
		}
		
		//处理控制烟花旋转的按键
		if(keyLeft)
		{
			thita-=step;
		}
		
		if(keyRight)
		{
			thita+=step;
		}
		
		if(keyUp)
		{
			fai-=step;
		}
		
		if(keyDown)
		{
			fai+=step;
		}
		
		constructPlane();
		
	}
	
	protected void paint(Graphics g, ImageObserver observer)
	{
		super.paint(g, observer);
		
		for(int i=0;i<fireworks.size();i++)
		{
			Firework firework=fireworks.get(i);
			if(firework!=null)
			{
				firework.paint(g,plane);
			}
		}
	}
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	 
	public void mouseDragged(AMouseEvent e)
	{
		if(debug)
		{
			System.out.println("====in mouseDragged of OuterBackgroundPanel====");
		}
		
		Point point=e.getPoint();
		int xDirection=0;
		int yDirection=0;
		
		if(point.x>lastMousePoint.x)
		{
			xDirection=1;
		}
		else
		{
			if(point.x<lastMousePoint.x)
			{
				xDirection=-1;
			}
			else
			{
				xDirection=0;
			}
		}
		
		if(point.y>lastMousePoint.y)
		{
			yDirection=1;
		}
		else
		{
			if(point.y<lastMousePoint.y)
			{
				yDirection=-1;
			}
			else
			{
				yDirection=0;
			}
		}
		
		if(Math.abs(point.x-lastMousePoint.x)>=Math.abs(point.y-lastMousePoint.y))
		{
			yDirection=0;
		}
		else
		{
			xDirection=0;
		}
		
		//方向分析完毕
		
		//处理向量n的水平转动	
		thita-=step*xDirection;
		
		//处理向量n的竖直转动
	    fai-=step*yDirection;	
	    
	    //更新plane
	    constructPlane();
	    
	    //更新lastMousePoint
	    refreshPointCounter++;
	    if(refreshPointCounter>=10)
	    { 
	       	lastMousePoint=point;
	        refreshPointCounter=0;
	    }
	    if(debug)
		{
			System.out.println("====end of mouseDragged of OuterBackgroundPanel====");
		}
	    
	}

	 
	public void mouseMoved(AMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	 
	protected void keyPressedOnMe(KeyEvent e) 
	{
		super.keyPressedOnMe(e);
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			keyLeft=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			keyRight=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			keyUp=true;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			keyDown=true;
		}
		
	}

	@Override
	protected void keyReleasedOnMe(KeyEvent e) 
	{
		super.keyReleasedOnMe(e);
		
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			keyLeft=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			keyRight=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			keyUp=false;
		}
		
		if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			keyDown=false;
		}
		
	}

	@Override
	protected void mousePressedOnMe() 
	{
		// TODO Auto-generated method stub
		super.mousePressedOnMe();
	}

	@Override
	protected void mouseReleasedOnMe() {
		// TODO Auto-generated method stub
		super.mouseReleasedOnMe();
	}

	
}
