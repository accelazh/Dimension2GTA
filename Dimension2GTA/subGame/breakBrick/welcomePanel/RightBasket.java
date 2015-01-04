package subGame.breakBrick.welcomePanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import animatedGUI.*;

import javax.swing.border.*;
import gameDisplayProcessor.*;

public class RightBasket extends APanel
{
	public static final ImageIcon[] gifs=
	{
		new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasket.gif"),
	    new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasketN.gif"),
	    new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasketP.gif"),
	    new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasketC.gif"),
	    new ImageIcon("pic/SubGame/shootingPractice/GUI/rightBasketE.gif"),
	
	};
	
	public static final int EMPTY=0;
	public static final int N=1;
	public static final int P=2;
	public static final int C=3;
	public static final int E=4;
	
	private static final AAnimationClip rClip=new AAnimationClip(
			new Point(WelcomePanel.basketLocations[4].x+gifs[0].getIconWidth(),WelcomePanel.basketLocations[4].y),
			gifs[0].getIconWidth(),
			1000/AAnimationClip.getFrameInterval(),
			AAnimationClip.SIN_MOTION,
			AAnimationClip.LEFT);
	
	private int currentState=0;
	
	public RightBasket()
	{
		super(gifs[0],rClip,AComponent.POP_AND_SHOW);
		
		setCurrentState(EMPTY);
	}
	
	public void setCurrentState(int currentState)
	{
		this.currentState=currentState;
		this.setImageIconAndAdjustSize(gifs[currentState]);
	}
	
	public int getCurrentState()
	{
		return this.currentState;
	}

}
