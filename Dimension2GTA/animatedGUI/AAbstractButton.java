package animatedGUI;

import javax.swing.*;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

import utilities.*;
import java.net.*;

public abstract class AAbstractButton extends AComponent
{
	//for debug
	private static final boolean debug=true;
	
	//数据域，用来表明当前的按钮状态和装载相关图片
	public static final int UP=0;
	private ImageIcon upImageIcon=null;
	
	public static final int MOUSE_ON=1;
	private ImageIcon mouseOnImageIcon=null;
	
	public static final int DOWN=2;
	private ImageIcon downImageIcon=null;
	
	private int buttonState=UP;
	
	private boolean isMouseInMe=false;
	
	//sound effect
	private SoundPlayer mouseOnClip;
	private SoundPlayer mouseClickClip;
	private SoundPlayer mouseReleasedClip;
	
	//constructors
	public AAbstractButton()
    {
    	super();
    }
	
	//单图片的构造方法组
    public AAbstractButton(ImageIcon upImageIcon, Point location)
    {
    	super(upImageIcon,location);
    	
    	this.upImageIcon=upImageIcon;
    }
      
    
  //双图片的构造方法组
    public AAbstractButton(ImageIcon upImageIcon, ImageIcon downImageIcon, Point location)
    {
    	super(upImageIcon,location);
    	
    	this.upImageIcon=upImageIcon;

    	this.downImageIcon=downImageIcon;
    }
    
   
    
  // 三图片的构造方法组
    public AAbstractButton(ImageIcon upImageIcon, ImageIcon downImageIcon, ImageIcon mouseOnImageIcon, Point location)
    {
    	super(upImageIcon,location);
    	
    	this.upImageIcon=upImageIcon;

    	this.downImageIcon=downImageIcon;
    	
    	this.mouseOnImageIcon=mouseOnImageIcon;
    }
        
    //图片切换效果的实现
    private void setButtonState(int buttonState)
    {
    	switch(buttonState)
    	{
    	case UP:
    	{
    		this.buttonState=buttonState;
    		setImageIconAndAdjustSize(upImageIcon);
    		break;
    	}
    	case MOUSE_ON:
    	{
    		this.buttonState=buttonState;
    		setImageIconAndAdjustSize(mouseOnImageIcon);
    		break;
    	}
    	case DOWN:
    	{
    		this.buttonState=buttonState;
    		setImageIconAndAdjustSize(downImageIcon);
    		break;
    	}
    	default:
    	{
    		break;
    	}
    	}
    }
    
    protected void mouseClickedOnMe()
    {
    	
    }
	protected void mousePressedOnMe()
	{
		setButtonState(DOWN);
		if(mouseClickClip!=null)
		{
			mouseClickClip.play();
		}
	}
	protected void mouseReleasedOnMe()
	{
		if (debug)
		{
			System.out.println("AButton mouseReleasedOnMe invoked, "
					+ getImageIcon());
			System.out.println("is visible: " + getVisible());
		}

	    setButtonState(UP);	
	    
	    if(isMouseInMe)
	    {
	    	setButtonState(MOUSE_ON);
	    	
	    	if(mouseReleasedClip!=null)
	    	{
	    		mouseReleasedClip.play();
	    	}
	    	
	    	AActionListener[] actionListenerList=getActionListenerList();
	    	
	    	AActionEvent aE=new AActionEvent(this);  
	    	
	    	for(int i=0;i<actionListenerList.length;i++)
	    	{
	    		if(actionListenerList[i]!=null)
	    		{
	    			actionListenerList[i].actionPerformed(aE);
	    		}
	    	}
	    	
	    }
	}
	protected void mouseDraggedOnMe()
	{
		
	}
	protected void mouseMovedOnMe()
	{
		if(UP==buttonState)
		{
			setButtonState(MOUSE_ON);
		}
	}
	protected void mouseEnteredMe()
	{
		isMouseInMe=true;
		
		if(UP==buttonState)
		{
			setButtonState(MOUSE_ON);
			if(mouseOnClip!=null)
			{
				mouseOnClip.play();
			}
		}
	}
	protected void mouseExitedMe()
	{
		isMouseInMe=false;
		
		if(MOUSE_ON==buttonState)
		{
			setButtonState(UP);
		}
	}
	
	protected void keyPressedOnMe(KeyEvent e)
	{
		
	}
	
	protected void keyReleasedOnMe(KeyEvent e)
	{
		
	}

	public SoundPlayer getMouseOnClip() {
		return mouseOnClip;
	}

	public void setMouseOnClip(SoundPlayer mouseOnClip) {
		this.mouseOnClip = mouseOnClip;
	}
	
	public void setMouseOnClip(URL mouseOnUrl) {
		this.mouseOnClip = new SoundPlayer(mouseOnUrl,2);
	}

	public SoundPlayer getMouseClickClip() {
		return mouseClickClip;
	}

	public void setMouseClickClip(SoundPlayer mouseClickClip) {
		this.mouseClickClip = mouseClickClip;
	}
	
	public void setMouseClickClip(URL mouseClickUrl) {
		this.mouseClickClip = new SoundPlayer(mouseClickUrl,2);
	}

	public SoundPlayer getMouseReleasedClip() {
		return mouseReleasedClip;
	}

	public void setMouseReleasedClip(SoundPlayer mouseReleasedClip) {
		this.mouseReleasedClip = mouseReleasedClip;
	}
	
	public void setMouseReleasedClip(URL mouseReleasedUrl) {
		this.mouseReleasedClip = new SoundPlayer(mouseReleasedUrl,2);
	}
    
}
