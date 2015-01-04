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
	
	//״̬�룬�������ڵ��������Ѿ�������
	public static final int POPPING=0;
	public static final int PREPARED=1;
	private int popState=PREPARED;
	
	//������������ģʽ
	private int mode=POP_AND_SHOW;
	
	//���������ָ������������ʹ�õ�AAnimationClip
	private AAnimationClip animationClip=null;
	//���������ָ���ڶ������������������λ��
	private Point stillLocation=null;
	
	//�����������AFinalContainer�ľ�������,���û�ʹ�õ�ʱ������Ƚ���һ��ˢ��
    private Point absoluteCoordinateToAFinalContainer=new Point(0,0);
	//�ں������
	private AComponent[] AComponentList=null;
	
	//������������غͳ��֣�����ʱ��������ֹͣ������ͼ����ʾ,�����������ں������������ʾ
	private boolean visible=true;
	
	//���������������ͣ����ʱselfProcess�������������κ��£������������ں��������selfProcess()����������
	private boolean pause=false;
	
	//������������ʾʱ��λ��������������ϲ�����֮�ϻ���֮�£�Ĭ��Ϊ������
	private boolean aboveUpperContainer=true;
	
	//�������ָ����ͼ��ʱ���Ƿ����size�Զ�����ͼ��Ĵ�С
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

	//����������ᵼ���齨��������
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
	
	//��������
	//������������������ı߽�,���������λ��
	public Rectangle getBounds()
	{
		Rectangle bounds=new Rectangle();
		bounds.setLocation(location);
	    bounds.setSize(size);
	    
	    return bounds;
	}	
	
	//�����������ˢ��absoluteCoordinateToAFinalContainer
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
	
	
	//�����������������ͬ����ÿһ���ں��������������restart
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

	
	//��������ᱻAContainer��timer��ÿһ�μ����ʱ�����
	//����������Ա����า�ǣ��Ա���������粻���л�imageIcon�ȵ�Ч��
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
	    ��������ᱻAContainer��timer��ÿһ�μ����ʱ����ã��������������
	�û����Ը��������������ʵ������Ч�������Ǳ���ע�⣬��Ϊ�������ʵ�ַ�ʽ
	�����⣬���������������л�һ���������Ϊ��x,y����ͼ��,g�ķ����ж�Ӧ��
	λ�����������(location.x+x,location.y+y)����ϸ������������Ϊ�����
	���������������GUI��������ջ��γ�һ���Զ����JPanelΪ����������Ϊ����
	paint(g,observer)������ʱ�����g��ͷ��β����������JPanel�Ļ�ͼ������
	������е�location����������Լ�����һ�����������λ�ã�������g��ͼ��ʱ��
	location�޷�ʹ�ã������ʹ�����������JPanel�ľ���λ�ã���εõ��أ�ʵ��
	�������������ڲ����û�ͼ������ʱ�򣬱�ֱ�ӵ��õ���paint��g,observer��
	�����ط���paint(g,observer,absoluteCoordinateOfUpperContainer),
	absoluteCoordinateOfUpperContainer��������һ�������ľ������ꡣȻ��
	paint(g,observer,absoluteCoordinateOfUpperContainer)�Ƚ�location
	����absoluteCoordinateOfUpperContainer������������JPanel�ľ������ꡣ
	�ٵ���paint(g,observer)��������paint(g,observer)�е�g.drawImage(image,
	location.x,location.y,size.width,size.height,observer)���������ȷ��
	�����������λ�á�����Ϊ��paint(g,observer)��location�������������������
	һ��������λ�ã��������������JPanel�ľ���λ�ã��ʶ��û�������չpaint(g,observer)
	��ʱ�򣬻�ͼ����Ҫ����ǿ���ı仯����paint(g,observer)������Ϻ�paint(g,
	observer,absoluteCoordinateOfUpperContainer)�ڴ������������ں�������Ļ滭
	�������location��ԭ��
	(����JPanel��AFinalContainer)
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
		
		//����location
	    Point origLocation=location;
	    location=new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
	    this.absoluteCoordinateToAFinalContainer=(Point)location.clone();
		
	    //���visible
	  
	    
	    if (visible) {

			

			if (debug) {
				System.out.println("visible check passed");
			}

			// �����Լ��ں�����ʾʱ���Լ���������
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					if (!AComponentList[i].isAboveUpperContainer()) {
						AComponentList[i].paint(g, observer, location);
					}
				}
			}

			// �����Լ�
			paint(g, observer);

			// �����Լ��ں�����ʾʱ���Լ���������
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					if (AComponentList[i].isAboveUpperContainer()) {
						AComponentList[i].paint(g, observer, location);
					}
				}
			}
		}
	    // �ָ�location
	    location=origLocation;
	    
		
	}
	
	
	
	// �����������Ҫ����Ϊimage�ᰴsize�Ĵ�С����������ò���size�Ĺ��췽������ôsize�Ĵ�С��imageIconһ��
	public void setSize(Dimension size) 
	{
		this.size = size;
	}
	
	
	//���������Ƕ��¼������Ĵ���
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

	//���յ���Ӧ�¼�ʱ�Ĵ���,��ʵ��mouseEntered��mouseExited�¼�,keyEvent�Ժ���˵
	private boolean isMouseOutOfThis=true;
	
	//����������������ʵ�ִ�swing��Event�¼���AGUI��Event�¼���ת��
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
		
		//����e����Լ��ϲ�����������
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//ˢ���Լ��ľ�������
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//����event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//�����Լ�����¼�Ӧ�������ķ�Ӧ
		if(getBounds().contains(eventLocation))
		{
			//���mouseEntered�¼�
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
			//���mouseExit�¼�
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
			//�����ں�������Ը��¼�Ӧ�������ķ�Ӧ
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
		
		//����e����Լ��ϲ�����������
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//ˢ���Լ��ľ�������
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//����event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//�����Լ�����¼�Ӧ�������ķ�Ӧ
		if(getBounds().contains(eventLocation))
		{
			//���mouseEntered�¼�
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
			//���mouseExit�¼�
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
			//�����ں�������Ը��¼�Ӧ�������ķ�Ӧ
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
		
		//����e����Լ��ϲ�����������
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//ˢ���Լ��ľ�������
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//����event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//�����Լ�����¼�Ӧ�������ķ�Ӧ
		if(getBounds().contains(eventLocation))
		{
			//���mouseEntered�¼�
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
			//���mouseExit�¼�
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
			
			//�����ں�������Ը��¼�Ӧ�������ķ�Ӧ
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
		
		//����e����Լ��ϲ�����������
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//ˢ���Լ��ľ�������
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//����event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//�����Լ�����¼�Ӧ�������ķ�Ӧ
		if(getBounds().contains(eventLocation))
		{
			//���mouseEntered�¼�
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
			//���mouseExit�¼�
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
			//�����ں�������Ը��¼�Ӧ�������ķ�Ӧ
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
		
		//����e����Լ��ϲ�����������
		Point eventLocation=new Point(e.getX()-absoluteCoordinateOfUpperContainer.x,
				e.getY()-absoluteCoordinateOfUpperContainer.y);
		//ˢ���Լ��ľ�������
		this.absoluteCoordinateToAFinalContainer =new Point(location.x+absoluteCoordinateOfUpperContainer.x,
	    		location.y+absoluteCoordinateOfUpperContainer.y);
		//����event
		AMouseEvent aE=generateAMouseEvent(e);
		
		//�����Լ�����¼�Ӧ�������ķ�Ӧ
		if(getBounds().contains(eventLocation))
		{
			if(debug22)
			{
				System.out.println("mouse moved on me");
			}
			//���mouseEntered�¼�
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
			
			//���mouseExit�¼�
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
			//�����ں�������Ը��¼�Ӧ�������ķ�Ӧ
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
			//������������� 
			for (int i = 0; i < keyListenerList.length; i++) {
				if (keyListenerList[i] != null) {
					keyListenerList[i].keyPressed(e);
				}
			}
			//���¼����ݸ�����
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveKeyPressedEvent(e);
				}
			}
			//����keyPressedOnMe()
			keyPressedOnMe(e);
		}
	}
	
	protected void receiveKeyReleasedEvent(KeyEvent e)
	{
		if (visible) {
			//������������� 
			for (int i = 0; i < keyListenerList.length; i++) {
				if (keyListenerList[i] != null) {
					keyListenerList[i].keyReleased(e);
				}
			}
			//���¼����ݸ�����
			for (int i = 0; i < AComponentList.length; i++) {
				if (AComponentList[i] != null) {
					AComponentList[i].receiveKeyReleasedEvent(e);
				}
			}
			//����keyReleasedOnMe()
			keyReleasedOnMe(e);
		}
	}
	

	//���Լ����¼��������ʱ�������ķ�Ӧ����������д,����˵��ť��Ҫ�����⼸������������ͼƬ�л�
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

