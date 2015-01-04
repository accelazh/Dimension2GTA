package animatedGUI;

import gameDisplayProcessor.ControlSetting;

import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

public  abstract class AComponent 
implements AConstants 
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug21=ControlSetting.debug21;
	private static final boolean debug22=ControlSetting.debug22;
	private static final boolean debug2=true;
	//date fields
	private ImageIcon imageIcon =null;
	private Image image=null;
	private Dimension size=null;
	private Point location=null; 
	
	//状态码，表明正在弹出还是已经弹出了
	public static final int POPPING=0;
	public static final int PREPARED=1;
	private int popState=PREPARED;
	
	//这个组件的运行模式
	private int mode=POP_AND_SHOW;
	
	//这个数据域指明这个组件正在使用的AAnimationClip
	private AAnimationClip animationClip=null;
	//这个数据域指明在动画结束后，组件的最终位置
	private Point stillLocation=null;
	
	//这个组件的相对AFinalContainer的绝对坐标,当用户使用的时候，最好先进行一下刷新
    private Point absoluteCoordinateToAFinalContainer=new Point(0,0);
	//内含的组件
	private AComponent[] AComponentList=null;
	
	//设置组件的隐藏和出现，隐藏时，动画不停止，但是图像不显示,并且这个组件内含的组件都不显示
	private boolean visible=true;
	
	//设置组件动画的暂停，此时selfProcess（）方法不做任何事，并且这个组件内含的组件的selfProcess()都不被调用
	private boolean pause=false;
	
	//设置这个组件显示时的位置是相对于它的上层容器之上还是之下，默认为在其上
	private boolean aboveUpperContainer=true;
	
	//这个变量指出画图的时候，是否根据size自动调整图像的大小
	private boolean paintWithImageSize=false;
	//constructors
	
	public AComponent()
    {
    	this(new ImageIcon("pic/default1.jpg"),new Point(0,0));
    }
	
    public AComponent(ImageIcon imageIcon, Point location)
    {
    	this(imageIcon,new AAnimationClip(new int[][]{new int[]{location.x,location.y}}),SHOW);
    }
    
    public AComponent(ImageIcon imageIcon, AAnimationClip animationClip, int mode)
    {
    	this.imageIcon=imageIcon;
    	this.image=imageIcon.getImage();
    	
    	this.animationClip=animationClip;
    	this.size=new Dimension(imageIcon.getIconWidth(),imageIcon.getIconHeight());
    	this.mode=mode;
    	
    	
    	this.stillLocation=new Point(animationClip.getCoordinate()[animationClip.getCoordinate().length-1][0],
    			animationClip.getCoordinate()[animationClip.getCoordinate().length-1][1]);
    	
    	switch(mode)
    	{
    	case POP_AND_SHOW:
    	{
    		this.location =new Point(animationClip.getCoordinate()[0][0],animationClip.getCoordinate()[0][1]);
    		break;
    	}
    	case POP_AND_LOOP:
    	{
    		this.location =new Point(animationClip.getCoordinate()[0][0],animationClip.getCoordinate()[0][1]);
    		break;
    	}
    	case SHOW:
    	{
    		this.location =new Point(stillLocation.x,stillLocation.y);
    		break;
    	}
    	default:
    	{
    	    break;	
    	}
    	}
    	
    	this.AComponentList=new AComponent[1];
        for(int i=0;i<AComponentList.length;i++)
        {
        	AComponentList[i]=null;
        }
    	
    	restart();
    }
    
    //add and remove AComponents
    public void add(AComponent newC)
    {
    	for(int i=0;i<AComponentList.length;i++)
    	{
    		if(newC==AComponentList[i])
    		{
    			return;
    		}
    	}
    	
    	int i;        // to search for the end of the array
		for(i=0;(i<AComponentList.length)&&(AComponentList[i]!=null);i++)
			;
		if(i<AComponentList.length)
		{
			AComponentList[i]=newC;
			
		}
		else
		{
			AComponent[] newAComponentList=new AComponent[AComponentList.length*2];
			for(int j=0;j<newAComponentList.length;j++)
			{
				newAComponentList[j]=null;
			}
			System.arraycopy(AComponentList,0,newAComponentList,0,AComponentList.length);
			
			newAComponentList[AComponentList.length]=newC;
			this.AComponentList=newAComponentList;
		}
    }

	public void remove(AComponent c)
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(c==AComponentList[i])
			{
				AComponentList[i]=null;
				for(int j=i;j<AComponentList.length;j++)
				{
					if(AComponentList.length-1==j)
					{
						AComponentList[j]=null;
					}
					else
					{
						AComponentList[j]=AComponentList[j+1];
					}
				}
			}
		}
	}
    //getters and setters
    
    public ImageIcon getImageIcon() {
		return imageIcon;
	}

    public Image getImage() {
		return image;
	}
    
	public void setImageIconWithoutAdjustSize(ImageIcon imageIcon)
	{
		this.imageIcon = imageIcon;
		this.image=imageIcon.getImage();
	}
	
	public void setImageIconAndAdjustSize(ImageIcon imageIcon)
	{
		this.imageIcon = imageIcon;
		this.image=imageIcon.getImage();
		
		this.size=new Dimension(imageIcon.getIconWidth(),imageIcon.getIconHeight());
	}

	public Dimension getSize() {
		return size;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public AAnimationClip getAnimationClip() {
		return animationClip;
	}

	public void setAnimationClip(AAnimationClip animationClip) {
		this.animationClip = animationClip;
	}

	public Point getStillLocation() {
		return stillLocation;
	}

	public void setStillLocation(Point stillLocation) {
		this.stillLocation = stillLocation;
	}
	
	public int getMode() {
		return mode;
	}

	//这个方法将会导致组建重新启动
	public void setMode(int mode) {
		this.mode = mode;
		restart();
	}
	
	public void setVisible(boolean visible)
	{
		this.visible=visible;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}
	
	public boolean isAboveUpperContainer() {
		return aboveUpperContainer;
	}

	public void setAboveUpperContainer(boolean aboveUpperContainer) {
		this.aboveUpperContainer = aboveUpperContainer;
	}
	
	public int getPopState() {
		return popState;
	}
	
	public boolean getVisible()
	{
		return this.visible;
	}
	
	public boolean isPaintWithImageSize() {
		return paintWithImageSize;
	}

	public void setPaintWithImageSize(boolean paintWithImageSize) {
		this.paintWithImageSize = paintWithImageSize;
	}
	
	//其他方法
	//这个方法返回这个组件的边界,坐标是相对位置
	public Rectangle getBounds()
	{
		Rectangle bounds=new Rectangle();
		bounds.setLocation(location);
	    bounds.setSize(size);
	    
	    return bounds;
	}	
	
	//这个方法用来刷新absoluteCoordinateToAFinalContainer
	protected void refreshAbsoluteCoordinate(Point absoluteCoordinateOfUpperContainer)
	{
		absoluteCoordinateToAFinalContainer=new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
	
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				AComponentList[i].refreshAbsoluteCoordinate(absoluteCoordinateToAFinalContainer);
			}
		}
	
	}
	
	
	//这个方法将这个组件连同它的每一个内含的组件都进行了restart
	public void restart()
    {
    	switch(mode)
    	{
    	case SHOW:
    	{
    		popState=PREPARED;
    		this.location =stillLocation;
    		break;
    	}
    	case POP_AND_SHOW:
    	{
    		popState=POPPING;
    		this.location =new Point(animationClip.getCoordinate()[0][0],animationClip.getCoordinate()[0][1]);
    		animationClip.restart();
    		break;
    	}
    	case POP_AND_LOOP:
    	{
    		popState=POPPING;
    		this.location =new Point(animationClip.getCoordinate()[0][0],animationClip.getCoordinate()[0][1]);
    		animationClip.restart();
    		break;
    	}
    	default:
    	{
    		popState=PREPARED;
    		this.location =stillLocation;
    		break;
    	}
    	}
    	
    	for(int i=0;i<AComponentList.length;i++)
    	{
    		if(AComponentList[i]!=null)
    		{
    			AComponentList[i].restart();
    		}
    	}
    	
    }

	
	//这个方法会被AContainer的timer在每一次激活的时候调用
	//这个方法可以被子类覆盖，以便制造出比如不断切换imageIcon等的效果
	protected void selfProcess()
	{
		if(pause)
		{
			return;
		}
		
		if(POPPING==popState)
		{
			if(!animationClip.isActive())
			{
				popState=PREPARED;
			}
			else
			{
				this.location=animationClip.getCurrentLocation();
			    animationClip.selfProcess();
			    
			    if(debug21)
			    {
			        System.out.println("this.location=="+location);
			        System.out.println("this.stillLocation=="+stillLocation);
			    }
			}
		}
		
		if(PREPARED==popState)
		{
	        if(POP_AND_LOOP==mode)
		    {
		    	restart();
		    }
		}
		
		for(int i=0;i<AComponentList.length;i++)
    	{
    		if(AComponentList[i]!=null)
    		{
    			AComponentList[i].selfProcess();
    		}
    	}
	}

	/*
	    这个方法会被AContainer的timer在每一次激活的时候调用，用来画出该组件
	用户可以覆盖这个方法，以实现特殊效果。但是必须注意，因为这个方法实现方式
	的问题，假如想在这个组件中画一个相对坐标为（x,y）的图形,g的方法中对应的
	位置坐标必须是(location.x+x,location.y+y)。详细解释起来，因为组件里
	可以套组件，整个GUI的组件最终会形成一棵以顶层的JPanel为根的树，因为调用
	paint(g,observer)方法的时候，这个g从头到尾都是树根处JPanel的绘图环境，
	而组件中的location又是相对于自己的上一层容器的相对位置，这样用g画图的时候，
	location无法使用，因必须使用相对于树根JPanel的绝对位置，如何得到呢？实现
	方法是这样：在层层调用绘图方法的时候，被直接调用的是paint（g,observer）
	的重载方法paint(g,observer,absoluteCoordinateOfUpperContainer),
	absoluteCoordinateOfUpperContainer被赋予上一层容器的绝对坐标。然后
	paint(g,observer,absoluteCoordinateOfUpperContainer)先将location
	加上absoluteCoordinateOfUpperContainer变成相对于树根JPanel的绝对坐标。
	再调用paint(g,observer)，这样在paint(g,observer)中的g.drawImage(image,
	location.x,location.y,size.width,size.height,observer)命令就能正确的
	画出该组件的位置。正因为在paint(g,observer)中location不再是相对于这个组件上
	一层容器的位置，而是相对于树根JPanel的绝对位置，故而用户自在扩展paint(g,observer)
	的时候，画图方法要上面强调的变化。在paint(g,observer)调用完毕后，paint(g,
	observer,absoluteCoordinateOfUpperContainer)在处理完这个组件内含的组件的绘画
	任务后会把location还原。
	(树根JPanel即AFinalContainer)
	*/
	protected void paint(Graphics g, ImageObserver observer)
	{
		if(debug)
		{
			System.out.println("==== in AComponent's paint(g,observer) ====");
			System.out.println("in AComponent's paint(g,observer) is invoked");
		}
		
		
		
		if(!paintWithImageSize)
		{
			g.drawImage(image,location.x,location.y,size.width,size.height,observer);
		}
		else
		{
			g.drawImage(image,location.x,location.y,observer);
		}
	
	}
	
	protected void paint(Graphics g, ImageObserver observer, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug)
		{
			System.out.println("==== in AComponent's paint(g,observer,point) ====");
			System.out.println("AComponent's paint(g,observer,point) is invoked");
		}
		
		//修正location
	    Point origLocation=location;
	    location=new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
	    this.absoluteCoordinateToAFinalContainer=(Point)location.clone();
		
	    //检测visible
	  
	    
	    if (visible) {

			

			if (debug) {
				System.out.println("visible check passed");
			}

			// 画出自己内含的显示时在自己下面的组件
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					if (!AComponentList[i].isAboveUpperContainer()) {
						AComponentList[i].paint(g, observer, location);
					}
				}
			}

			// 画出自己
			paint(g, observer);

			// 画出自己内含的显示时在自己上面的组件
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					if (AComponentList[i].isAboveUpperContainer()) {
						AComponentList[i].paint(g, observer, location);
					}
				}
			}
		}
	    // 恢复location
	    location=origLocation;
	    
		
	}
	
	
	
	// 这个方法很重要，因为image会按size的大小画出，如果用不含size的构造方法，那么size的大小和imageIcon一致
	public void setSize(Dimension size) 
	{
		this.size = size;
	}
	
	
	//以下内容是对事件驱动的处理
	private AActionListener[] actionListenerList={null};
	private AMouseListener[] mouseListenerList={null};
	private AMouseMotionListener[] mouseMotionListenerList={null};
	private MouseWheelListener[] mouseWheelListenerList={null};
	private KeyListener[] keyListenerList={null};
	
	public void addAActionListener(AActionListener l)
	{
		if(null==l)
		{
			return;
		}
		
		if(null==actionListenerList)
		{
			actionListenerList=new AActionListener[1];
			actionListenerList[0]=l;
		}
		else
		{
			AActionListener[] newAActionListenerList=new AActionListener[actionListenerList.length+1];
			System.arraycopy(actionListenerList,0,newAActionListenerList,0,actionListenerList.length);
			newAActionListenerList[actionListenerList.length]=l;
			
			actionListenerList=newAActionListenerList;
		}
		
	}
	public void removeAActionListener(AActionListener l)
	{
		for(int i=0;i<actionListenerList.length;i++)
		{
			if(l==actionListenerList[i])
			{
				actionListenerList[i]=null;
			}
		}
	}
	
	public void addAMouseListener(AMouseListener l)
	{
		if(null==l)
		{
			return;
		}
		
		if(null==mouseListenerList)
		{
			mouseListenerList=new AMouseListener[1];
			mouseListenerList[0]=l;
		}
		else
		{
			AMouseListener[] newAMouseListenerList=new AMouseListener[mouseListenerList.length+1];
			System.arraycopy(mouseListenerList,0,newAMouseListenerList,0,mouseListenerList.length);
			newAMouseListenerList[mouseListenerList.length]=l;
			
			mouseListenerList=newAMouseListenerList;
		}
		
	}
	public void removeAMouseListener(AMouseListener l)
	{
		for(int i=0;i<mouseListenerList.length;i++)
		{
			if(l==mouseListenerList[i])
			{
				mouseListenerList[i]=null;
			}
		}
	}
	
	public void addAMouseMotionListener(AMouseMotionListener l)
	{
		if(null==l)
		{
			return;
		}
		
		if(null==mouseMotionListenerList)
		{
			mouseMotionListenerList=new AMouseMotionListener[1];
			mouseMotionListenerList[0]=l;
		}
		else
		{
			AMouseMotionListener[] newAMouseMotionListenerList=new AMouseMotionListener[mouseMotionListenerList.length+1];
			System.arraycopy(mouseMotionListenerList,0,newAMouseMotionListenerList,0,mouseMotionListenerList.length);
			newAMouseMotionListenerList[mouseMotionListenerList.length]=l;
			
			mouseMotionListenerList=newAMouseMotionListenerList;
		}
		
	}
	public void removeAMouseMotionListener(AMouseMotionListener l)
	{
		for(int i=0;i<mouseMotionListenerList.length;i++)
		{
			if(l==mouseMotionListenerList[i])
			{
				mouseMotionListenerList[i]=null;
			}
		}
	}
	
	public void addMouseWheelListener(MouseWheelListener l)
	{
		if(null==l)
		{
			return;
		}
		
		if(null==mouseWheelListenerList)
		{
			mouseWheelListenerList=new MouseWheelListener[1];
			mouseWheelListenerList[0]=l;
		}
		else
		{
			MouseWheelListener[] newMouseWheelListenerList=new MouseWheelListener[mouseWheelListenerList.length+1];
			System.arraycopy(mouseWheelListenerList,0,newMouseWheelListenerList,0,mouseWheelListenerList.length);
			newMouseWheelListenerList[mouseWheelListenerList.length]=l;
			
			mouseWheelListenerList=newMouseWheelListenerList;
		}
		
	}
	public void removeMouseWheelListener(MouseWheelListener l)
	{
		for(int i=0;i<mouseWheelListenerList.length;i++)
		{
			if(l==mouseWheelListenerList[i])
			{
				mouseWheelListenerList[i]=null;
			}
		}
	}
	
	public void addKeyListener(KeyListener l)
	{
		if(null==l)
		{
			return;
		}
		
		if(null==keyListenerList)
		{
			keyListenerList=new KeyListener[1];
			keyListenerList[0]=l;
		}
		else
		{
			KeyListener[] newKeyListenerList=new KeyListener[keyListenerList.length+1];
			System.arraycopy(keyListenerList,0,newKeyListenerList,0,keyListenerList.length);
			newKeyListenerList[keyListenerList.length]=l;
			
			keyListenerList=newKeyListenerList;
		}
		
	}
	public void removeKeyListener(KeyListener l)
	{
		for(int i=0;i<keyListenerList.length;i++)
		{
			if(l==keyListenerList[i])
			{
				keyListenerList[i]=null;
			}
		}
	}

	//接收到相应事件时的处理,以实现mouseEntered和mouseExited事件,keyEvent以后再说
	private boolean isMouseOutOfThis=true;
	
	//下面两个方法用来实现从swing的Event事件到AGUI的Event事件的转换
	protected AActionEvent generateAActionEvent(ActionEvent e)
	{
		return new AActionEvent(this);
	}
	
	protected AMouseEvent generateAMouseEvent(MouseEvent e)
	{
		Point where=new Point(e.getX()-absoluteCoordinateToAFinalContainer.x,
				e.getY()-absoluteCoordinateToAFinalContainer.y);
	    int button=-1;
		
		switch(e.getButton())
	    {
	    case MouseEvent.BUTTON1:
	    {
	    	button=AMouseEvent.BUTTON1;
	    	break;
	    }
	    case MouseEvent.BUTTON2:
	    {
	    	button=AMouseEvent.BUTTON2;
	    	break;
	    }
	    case MouseEvent.BUTTON3:
	    {
	    	button=AMouseEvent.BUTTON3;
	    	break;
	    }
	    default:
	    {
	    	button=-1;
	    	break;
	    }
	    }
		
		return new AMouseEvent(this,where,button);
		
	}
	
	protected void receiveMouseClickedEvent(MouseEvent e, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug22)
		{
			System.out.println("receiveMouseClickedEvent invoked");
		}
		
		//计算e相对自己上层容器的坐标
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//刷新自己的绝对坐标
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//生成event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//处理自己因该事件应该做出的反应
		if(getBounds().contains(eventLocation))
		{
			//检测mouseEntered事件
			if(isMouseOutOfThis)
			{
				if (visible) 
				{
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseEntered(aE);
						}
					}
					mouseEnteredMe();
				}
			}
			isMouseOutOfThis=false;
			
			for(int i=0;i<mouseListenerList.length;i++)
			{
				if (visible) {
					if (mouseListenerList[i] != null) {
						mouseListenerList[i].mouseClicked(aE);
					}
				}
			}
			if (visible) {
				mouseClickedOnMe();
			}
		}
		else
		{
			//检测mouseExit事件
			if(!isMouseOutOfThis)
			{
				if (visible) 
				{
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseExited(aE);
						}
					}
					mouseExitedMe();
				}
			}
			isMouseOutOfThis=true;
		}
		
		if (visible) {
			//处理内含的组件对该事件应该做出的反应
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveMouseClickedEvent(e,
							absoluteCoordinateToAFinalContainer);
				}
			}
		}
	}
	
	protected void receiveMousePressedEvent(MouseEvent e, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug22)
		{
			System.out.println("receiveMousePressedEvent invoked");
		}
		
		//计算e相对自己上层容器的坐标
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//刷新自己的绝对坐标
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//生成event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//处理自己因该事件应该做出的反应
		if(getBounds().contains(eventLocation))
		{
			//检测mouseEntered事件
			if(isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseEntered(aE);
						}
					}
					mouseEnteredMe();
				}
			}
			isMouseOutOfThis=false;
			
			if (visible) {
				for (int i = 0; i < mouseListenerList.length; i++) {

					if (mouseListenerList[i] != null) {
						mouseListenerList[i].mousePressed(aE);
					}
				}
				mousePressedOnMe();
			}
		}
		else
		{
			//检测mouseExit事件
			if(!isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseExited(aE);
						}
					}
					mouseExitedMe();
				}
			}
			isMouseOutOfThis=true;
		}
		
		if (visible) {
			//处理内含的组件对该事件应该做出的反应
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveMousePressedEvent(e,
							absoluteCoordinateToAFinalContainer);
				}
			}
		}
	}

	
	protected void receiveMouseReleasedEvent(MouseEvent e, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug22)
		{
			System.out.println("receiveMouseReleasedEvent invoked");
		}
		
		//计算e相对自己上层容器的坐标
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//刷新自己的绝对坐标
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//生成event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//处理自己因该事件应该做出的反应
		if(getBounds().contains(eventLocation))
		{
			//检测mouseEntered事件
			if(isMouseOutOfThis)
			{
				if (visible) 
				{
					for (int i = 0; i < mouseListenerList.length; i++) 
					{
						if (mouseListenerList[i] != null) 
						{
							mouseListenerList[i].mouseEntered(aE);
						}
					}
					mouseEnteredMe();
				}
			}
			isMouseOutOfThis=false;
			
			if (visible) 
			{
				for (int i = 0; i < mouseListenerList.length; i++) 
				{
					if (mouseListenerList[i] != null) 
					{
						mouseListenerList[i].mouseReleased(aE);
					}
				}
				if(debug2)
				{
					System.out.println("AComponent mouseReleasedOnMe invoked, "+getImageIcon());
				}
				mouseReleasedOnMe();
			}
		}
		else
		{
			//检测mouseExit事件
			if(!isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseExited(aE);
						}
					}
					mouseExitedMe();
				}
			}
			isMouseOutOfThis=true;
		}
		
		if (visible) 
		{
			if(debug2)
			{
				System.out.println(getImageIcon());
				System.out.println("is visible: "+visible);
			}
			
			//处理内含的组件对该事件应该做出的反应
			for (int i = 0; i < AComponentList.length; i++) 
			{
				if (AComponentList[i] != null) 
				{
					AComponentList[i].receiveMouseReleasedEvent(e,
							absoluteCoordinateToAFinalContainer);
				}
			}
		}
	}
	
	
	protected void receiveMouseDraggedEvent(MouseEvent e, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug22)
		{
			System.out.println("receiveMouseDreggedEvent invoked");
		}
		
		//计算e相对自己上层容器的坐标
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//刷新自己的绝对坐标
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//生成event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//处理自己因该事件应该做出的反应
		if(getBounds().contains(eventLocation))
		{
			//检测mouseEntered事件
			if(isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseEntered(aE);
						}
					}
					mouseEnteredMe();
				}
			}
			isMouseOutOfThis=false;
			
			if (visible) {
				for (int i = 0; i < mouseMotionListenerList.length; i++) {

					if (mouseMotionListenerList[i] != null) {
						mouseMotionListenerList[i].mouseDragged(aE);
					}
				}
				mouseDraggedOnMe();
			}
		}
		else
		{
			//检测mouseExit事件
			if(!isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseExited(aE);
						}
					}
					mouseExitedMe();
				}
			}
			isMouseOutOfThis=true;
		}
		
		if (visible) {
			//处理内含的组件对该事件应该做出的反应
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveMouseDraggedEvent(e,
							absoluteCoordinateToAFinalContainer);
				}
			}
		}
	}
	
	
	protected void receiveMouseMovedEvent(MouseEvent e, Point absoluteCoordinateOfUpperContainer)
	{
		if(debug22)
		{
			System.out.println("receiveMouseMovedEvent invoked");
		}
		
		//计算e相对自己上层容器的坐标
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//刷新自己的绝对坐标
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//生成event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//处理自己因该事件应该做出的反应
		if(getBounds().contains(eventLocation))
		{
			if(debug22)
			{
				System.out.println("mouse moved on me");
			}
			//检测mouseEntered事件
			if(isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseEntered(aE);
						}
					}
					if (debug22) {
						System.out.println("mouseEnteredMe");
					}
					mouseEnteredMe();
				}
			}
			isMouseOutOfThis=false;
				
			if (visible) {
				for (int i = 0; i < mouseMotionListenerList.length; i++) {
					if (mouseMotionListenerList[i] != null) {
						mouseMotionListenerList[i].mouseMoved(aE);
					}
				}
				mouseMovedOnMe();
			}
		}
		else
		{
			if(debug22)
			{
				System.out.println("mouse moved outside me");
			}
			
			//检测mouseExit事件
			if(!isMouseOutOfThis)
			{
				if (visible) {
					for (int i = 0; i < mouseListenerList.length; i++) {
						if (mouseListenerList[i] != null) {
							mouseListenerList[i].mouseExited(aE);
						}
					}
					if (debug22) {
						System.out.println("mouseEnteredMe");
					}
					mouseExitedMe();
				}
			}
			isMouseOutOfThis=true;
		}
		
		if (visible) {
			//处理内含的组件对该事件应该做出的反应
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveMouseMovedEvent(e,
							absoluteCoordinateToAFinalContainer);
				}
			}
		}
	}

	protected void receiveKeyPressedEvent(KeyEvent e)
	{
		if (visible) {
			//发起监听器动作 
			for (int i = 0; i < keyListenerList.length; i++) {
				if (keyListenerList[i] != null) {
					keyListenerList[i].keyPressed(e);
				}
			}
			//将事件传递给子类
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveKeyPressedEvent(e);
				}
			}
			//激活keyPressedOnMe()
			keyPressedOnMe(e);
		}
	}
	
	protected void receiveKeyReleasedEvent(KeyEvent e)
	{
		if (visible) {
			//发起监听器动作 
			for (int i = 0; i < keyListenerList.length; i++) {
				if (keyListenerList[i] != null) {
					keyListenerList[i].keyReleased(e);
				}
			}
			//将事件传递给子类
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveKeyReleasedEvent(e);
				}
			}
			//激活keyReleasedOnMe()
			keyReleasedOnMe(e);
		}
	}
	

	//当自己的事件被激活的时候，作出的反应，由子类填写,比如说按钮就要覆盖这几个方法来制作图片切换
	protected abstract void mouseClickedOnMe();
	protected abstract void mousePressedOnMe();
	protected abstract void mouseReleasedOnMe();
	protected abstract void mouseDraggedOnMe();
	protected abstract void mouseMovedOnMe();
	protected abstract void mouseEnteredMe();
	protected abstract void mouseExitedMe();
	protected abstract void keyPressedOnMe(KeyEvent e);
	protected abstract void keyReleasedOnMe(KeyEvent e);

	public AActionListener[] getActionListenerList() {
		return actionListenerList;
	}

	public AMouseListener[] getMouseListenerList() {
		return mouseListenerList;
	}

	public AMouseMotionListener[] getMouseMotionListenerList() {
		return mouseMotionListenerList;
	}

	public MouseWheelListener[] getMouseWheelListenerList() {
		return mouseWheelListenerList;
	}

	public KeyListener[] getKeyListenerList() {
		return keyListenerList;
	}

}

