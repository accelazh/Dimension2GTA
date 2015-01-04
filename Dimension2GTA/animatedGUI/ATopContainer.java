package animatedGUI;

import gameDisplayProcessor.ControlSetting;

import javax.swing.*;

import subGame.shootingPractice.Spark2;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class ATopContainer extends JPanel
implements AConstants, ActionListener, MouseListener, MouseMotionListener, KeyListener
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug21=ControlSetting.debug21;
	private static final boolean debug2=false;
	
	
	//data fields
	private AComponent[] AComponentList=null;

	private Timer timer=new Timer(TIMER_INTERVAL,this);
	
	//constructors
	//构造方法并不直接启动timer
	public ATopContainer()
	{
		super();
		
		this.AComponentList=new AComponent[1];
        for(int i=0;i<AComponentList.length;i++)
        {
        	AComponentList[i]=null;
        }
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        
        restartWithoutTimer();
        
	}
	
	public ATopContainer(LayoutManager layout)
	{
		super(layout);
		
		this.AComponentList=new AComponent[1];
        for(int i=0;i<AComponentList.length;i++)
        {
        	AComponentList[i]=null;
        }
        
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        
        restartWithoutTimer();
	}
	
	//相应名字的方法有什么含义，在AComponent中有解释
	
	//对timer的控制
	public void setTimerDelay(int delay)
	{
		if(delay>=1)
		{
			this.timer.setDelay(delay);
		}
	}
	
	public void startTimer()
	{
		this.timer.start();
	}
	
	public void stopTimer()
	{
		this.timer.stop();
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
		
		refreshAbsoluteCoordinateOfComponents();
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
	
	//其他方法
	
	//这个方法会启动timer
	public void restart()
    {
    	restartWithoutTimer();
    	
    	this.timer.start();
    	
    	
    }
	
	//这方方法刷新每个组件的absoluteCoordinateToAFinalContainer
	public void refreshAbsoluteCoordinateOfComponents()
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				AComponentList[i].refreshAbsoluteCoordinate(new Point(0,0));
			}
		}
	}
	
	public void restartWithoutTimer()
	{
		for(int i=0;i<AComponentList.length;i++)
    	{
    		if(AComponentList[i]!=null)
    		{
    			AComponentList[i].restart();
    		}
    	}
		
		refreshAbsoluteCoordinateOfComponents();
	}
	
	protected void selfProcess()
	{
		for(int i=0;i<AComponentList.length;i++)
    	{
    		if(AComponentList[i]!=null)
    		{
    			AComponentList[i].selfProcess();
    		}
    	}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		for(int i=0;i<AComponentList.length;i++)
    	{
    		if(AComponentList[i]!=null)
    		{
    			if(false)
    			{
    			    System.out.println("==== in AFinalContainer's paintComponent() ====");
    			    System.out.println("AComponent["+i+"]'s paint() is invoked");
    			}
    			AComponentList[i].paint(g,this,new Point(0,0));
    		}
    	}
	}

	
	
	//监听器的覆盖方法
	 
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==timer)
		{
			selfProcess();
			repaint();
		}
		
	}

	 
	public void mouseClicked(MouseEvent e)
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				Point absoluteCoordinateOfUpperContainer=new Point(0,0);
				AComponentList[i].receiveMouseClickedEvent(e,absoluteCoordinateOfUpperContainer);
			}
		}
		
	}

	 
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mousePressed(MouseEvent e)
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				Point absoluteCoordinateOfUpperContainer=new Point(0,0);
				AComponentList[i].receiveMousePressedEvent(e,absoluteCoordinateOfUpperContainer);
			}
		}
		
	}

	 
	public void mouseReleased(MouseEvent e) 
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				Point absoluteCoordinateOfUpperContainer=new Point(0,0);
				AComponentList[i].receiveMouseReleasedEvent(e,absoluteCoordinateOfUpperContainer);
			}
		}
	}

	 
	public void mouseDragged(MouseEvent e)
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				Point absoluteCoordinateOfUpperContainer=new Point(0,0);
				AComponentList[i].receiveMouseDraggedEvent(e,absoluteCoordinateOfUpperContainer);
			}
		}
		
	}

	 
	public void mouseMoved(MouseEvent e) 
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				Point absoluteCoordinateOfUpperContainer=new Point(0,0);
				AComponentList[i].receiveMouseMovedEvent(e,absoluteCoordinateOfUpperContainer);
			}
		}
		
	}

	 
	public void keyPressed(KeyEvent e) 
	{
		if(debug2)
		{
			System.out.println("====in method KeyPressed of ATopContainer====");
		}
		
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				AComponentList[i].receiveKeyPressedEvent(e);
			}
		}
		
		if(debug2)
		{
			System.out.println("====end of method KeyPressed of ATopContainer====");
		}
		
		
	}

	 
	public void keyReleased(KeyEvent e) 
	{
		for(int i=0;i<AComponentList.length;i++)
		{
			if(AComponentList[i]!=null)
			{
				AComponentList[i].receiveKeyReleasedEvent(e);
			}
		}
		
	}

	 
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
