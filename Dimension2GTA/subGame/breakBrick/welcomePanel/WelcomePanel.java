package subGame.breakBrick.welcomePanel;

import javax.swing.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import animatedGUI.*;

import javax.swing.border.*;
import gameDisplayProcessor.*;
import subGame.breakBrick.*;
import java.net.*;
import java.net.*;

public class WelcomePanel extends ATopContainer
implements Constants, AActionListener, AMouseMotionListener, AMouseListener
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug21=ControlSetting.debug21;
	
	//static resource data
	public static final ImageIcon[] basketGifs={
		new ImageIcon("pic/SubGame/shootingPractice/GUI/outerBk.gif"),
		new ImageIcon("pic/SubGame/shootingPractice/GUI/outerBasket.gif"),
		new ImageIcon("pic/SubGame/shootingPractice/GUI/leftBasket.gif"),
		new ImageIcon("pic/SubGame/shootingPractice/GUI/downBasket.gif"),
		new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasket.gif"),
	
	};
	
	public static final ImageIcon[][] buttonGifs={
		new ImageIcon[]{
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonN.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonN_On.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonN_Press.gif"),
			
		},
		
		new ImageIcon[]{
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonP.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonP_On.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonP_Press.gif"),
			
		},
		
        new ImageIcon[]{
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonC.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonC_On.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonC_Press.gif"),
			
		},
		
        new ImageIcon[]{

				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonE.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonE_On.gif"),
				new ImageIcon("pic/SubGame/shootingPractice/GUI/buttonE_Press.gif"),
		},
	};
	
	public static final Point[] basketLocations={
		new Point(0,0),
		new Point(0,0),
		new Point(18,19),
		new Point(38,484),
		new Point(552,19),
	};
	
	public static final Point[] buttonLocations={
		new Point(59-basketLocations[2].x,85-basketLocations[2].y),
		new Point(59-basketLocations[2].x,84+49-basketLocations[2].y),
		new Point(59-basketLocations[2].x,84+49+49-basketLocations[2].y),
		new Point(59-basketLocations[2].x,84+49+49+49-basketLocations[2].y),
	};
	
	//AGUI Components
	private OuterBackgroundPanel outerBk=null;
	private APanel outerBasket=null;
	private APanel leftBasket=null;
	private DownBasket downBasket=null;
	private RightBasket rightBasket=null;
	
	private AButton buttonN=null;
	private AButton buttonP=null;
	private AButton buttonC=null;
	private AButton buttonE=null;
	
	//button sound effect
	private URL mouseOnUrl=WelcomePanel.class.getResource("buttonMouseOn.wav");
	private URL mouseClickUrl=WelcomePanel.class.getResource("buttonClick.wav");
	private URL mouseReleasedUrl=WelcomePanel.class.getResource("buttonReleased.wav");
	
	
	//标识自己正在关闭状态
    private boolean ending=false;
    
    //标识在自己结束的时候是按了哪个按钮
    private boolean sendNewGameMessageRequested=false;
    private boolean sendPlayMapMessageRequested=false;
    private boolean sendCreateMapMessageRequested=false;
    private boolean sendExitMessageRequested=false;
	
	//面板管理器的引用
	private BrickBreak.PanelConductor panelConductor;
	
	//音效
	private AudioClip doorSlidClip;
	private AudioClip backgroundClip;
	
	public WelcomePanel()
	{
		this.setPreferredSize(GUI_SIZE);
		this.setSize(GUI_SIZE);
		this.setLayout(null);
		this.requestFocusInWindow();
		
		//==initial AGUI components==
		//outerBk
		outerBk=new OuterBackgroundPanel(basketGifs[0],basketLocations[0],GUI_SIZE);
		this.add(outerBk);
		
		//leftBasket
		AAnimationClip lClip=new AAnimationClip(
				new Point(basketLocations[2].x-basketGifs[2].getIconWidth(),basketLocations[2].y),
				basketGifs[2].getIconWidth(),
				1000/AAnimationClip.getFrameInterval(),
				AAnimationClip.SIN_MOTION,
				AAnimationClip.RIGHT);
		if(debug)
		{
			System.out.println("is lClip null? "+(null==lClip));
		    System.out.println("is lClip.getCoordinate() null? "+(null==lClip.getCoordinate()));
		}
		
		leftBasket=new APanel(basketGifs[2],lClip,AComponent.POP_AND_SHOW);
		this.add(leftBasket);
		
		//downBasket
		downBasket=new DownBasket();
		this.add(downBasket);
		
		//rightBasket
		rightBasket=new RightBasket();
		this.add(rightBasket);
				
		//outerBasket
		outerBasket=new APanel(basketGifs[1],basketLocations[1]);
		this.add(outerBasket);
		
		//buttonN
		buttonN=new AButton(buttonGifs[0][0],buttonGifs[0][2],buttonGifs[0][1],buttonLocations[0]);
		leftBasket.add(buttonN);
		buttonN.addAActionListener(this);
		buttonN.addAMouseMotionListener(this);
		buttonN.addAMouseListener(this);
		
		buttonN.setMouseOnClip(mouseOnUrl);
		buttonN.setMouseClickClip(mouseClickUrl);
		buttonN.setMouseReleasedClip(mouseReleasedUrl);
		
		//buttonP
		buttonP=new AButton(buttonGifs[1][0],buttonGifs[1][2],buttonGifs[1][1],buttonLocations[1]);
		leftBasket.add(buttonP);
		buttonP.addAActionListener(this);
		buttonP.addAMouseMotionListener(this);
		buttonP.addAMouseListener(this);
		
		buttonP.setMouseOnClip(mouseOnUrl);
		buttonP.setMouseClickClip(mouseClickUrl);
		buttonP.setMouseReleasedClip(mouseReleasedUrl);
		
		//buttonC
		buttonC=new AButton(buttonGifs[2][0],buttonGifs[2][2],buttonGifs[2][1],buttonLocations[2]);
		leftBasket.add(buttonC);
		buttonC.addAActionListener(this);
		buttonC.addAMouseMotionListener(this);
		buttonC.addAMouseListener(this);
		
		buttonC.setMouseOnClip(mouseOnUrl);
		buttonC.setMouseClickClip(mouseClickUrl);
		buttonC.setMouseReleasedClip(mouseReleasedUrl);
		
		//buttonE
		buttonE=new AButton(buttonGifs[3][0],buttonGifs[3][2],buttonGifs[3][1],buttonLocations[3]);
		leftBasket.add(buttonE);
		buttonE.addAActionListener(this);
		buttonE.addAMouseMotionListener(this);
		buttonE.addAMouseListener(this);
		
		buttonE.setMouseOnClip(mouseOnUrl);
		buttonE.setMouseClickClip(mouseClickUrl);
		buttonE.setMouseReleasedClip(mouseReleasedUrl);
		
		//add components
		
		//sound
		
		try
		{
			doorSlidClip=Applet.newAudioClip(WelcomePanel.class.getResource("door.wav"));
			backgroundClip=Applet.newAudioClip(WelcomePanel.class.getResource("start.wav"));
		}
		catch(Exception ex)
		{
			System.out.println("Error when create doorSlidClip in WelcomePanel");
		}
		
		doorSlidClip.play();
		backgroundClip.play();
		
		//startTimer
		startTimer();
	}
	
	//结束的时候的动画
	private void endingWelcomePanel()
	{
		if(ending)
		{
			return;
		}
		
		outerBk.setAnimationClip(outerBk.getAnimationClip().reverse());
		outerBasket.setAnimationClip(outerBasket.getAnimationClip().reverse());
		leftBasket.setAnimationClip(leftBasket.getAnimationClip().reverse());
		downBasket.setAnimationClip(downBasket.getAnimationClip().reverse());
		rightBasket.setAnimationClip(rightBasket.getAnimationClip().reverse());
		
		outerBk.restart();
		outerBasket.restart();
		leftBasket.restart();
		downBasket.restart();
		rightBasket.restart();
		
		doorSlidClip.play();
		ending=true;
	}
	
	 
	public void actionPerformed(AActionEvent e) 
	{
		if(e.getSource()==buttonN)
		{
			if(!ending)
			{
				sendNewGameMessageRequested=true;
			}
			endingWelcomePanel();
		}
		
		if(e.getSource()==buttonP)
		{
			if(!ending)
			{
				sendPlayMapMessageRequested=true;
			}
			endingWelcomePanel();
			
		}
		
		if(e.getSource()==buttonC)
		{
			if(!ending)
			{
				sendCreateMapMessageRequested=true;
			}
			endingWelcomePanel();
			
		}
		
		if(e.getSource()==buttonE)
		{
			if(!ending)
			{
				sendExitMessageRequested=true;
			}
			endingWelcomePanel();
			
		}
	
		
	}

	 
	public void actionPerformed(ActionEvent e)
	{
		super.actionPerformed(e);
		
		if(!ending)
		{
			if((leftBasket.getPopState()==AComponent.PREPARED)
					&&(rightBasket.getPopState()==AComponent.PREPARED)
					&&(downBasket.getPopState()==AComponent.PREPARED))
			{
				outerBk.setOpen(true);
			}
		}
		
		if(ending)
		{
			outerBk.setOpen(false);
			
			if((leftBasket.getPopState()==AComponent.PREPARED)
					&&(rightBasket.getPopState()==AComponent.PREPARED)
					&&(downBasket.getPopState()==AComponent.PREPARED))
			{
				
				backgroundClip.stop();
				
				if (sendNewGameMessageRequested) 
				{
					stopTimer();
					sendNewGameMessage();
				}
				if (sendPlayMapMessageRequested) 
				{
					stopTimer();
					sendPlayMapMessage();
				}
				if (sendCreateMapMessageRequested) 
				{
					stopTimer();
					sendCreateMapMessage();
				}
				if (sendExitMessageRequested)
				{
					stopTimer();
					sendExitMessage();
				}
			}
		}
	}
	
	 
	public void mouseDragged(AMouseEvent e) {
		// TODO Auto-generated method stub
		
		
	}


	 
	public void mouseMoved(AMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseClicked(AMouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	 
	public void mouseEntered(AMouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==buttonN)
		{
			rightBasket.setCurrentState(RightBasket.N);
		}
		
		if(e.getSource()==buttonP)
		{
			rightBasket.setCurrentState(RightBasket.P);
			
		}

		if(e.getSource()==buttonC)
		{
			rightBasket.setCurrentState(RightBasket.C);
			
		}
		
		if(e.getSource()==buttonE)
		{
			rightBasket.setCurrentState(RightBasket.E);
		   
		}
	}


	 
	public void mouseExited(AMouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==buttonN)
		{
			rightBasket.setCurrentState(RightBasket.EMPTY);
		}
		
		if(e.getSource()==buttonP)
		{
			rightBasket.setCurrentState(RightBasket.EMPTY);
		}

		if(e.getSource()==buttonC)
		{
			rightBasket.setCurrentState(RightBasket.EMPTY);
		}
		
		if(e.getSource()==buttonE)
		{
			rightBasket.setCurrentState(RightBasket.EMPTY);
		}
	}


	 
	public void mousePressed(AMouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	 
	public void mouseReleased(AMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public BrickBreak.PanelConductor getPanelConductor() {
		return panelConductor;
	}


	public void setPanelConductor(BrickBreak.PanelConductor panelConductor) {
		this.panelConductor = panelConductor;
	}

	//下面的几个方法负责向panelConductor传递信息
	//=====================================
	private void sendNewGameMessage()
	{
		panelConductor.receiveNewGameMessageFormWelcomePanel();
	}
	
	private void sendPlayMapMessage()
	{
		panelConductor.receivePlayMapMessageFormWelcomePanel();
	}
	
	private void sendCreateMapMessage()
	{
		panelConductor.receiveCreateMapMessageFormWelcomePanel();
	}
	
	private void sendExitMessage()
	{
		panelConductor.receiveExitMessageFormWelcomePanel();
	}
	//===========================================

	//test	
	public static void main(String[] args)
    {
    	WelcomePanel panel=null;
    	JFrame frame=new JFrame();
    	frame.add(panel=new WelcomePanel());
    	//panel.setBorder(new LineBorder(Color.BLUE,2));
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.pack();
    	frame.setVisible(true);
    
    }

}
