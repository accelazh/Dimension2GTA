package subGame.breakBrick.welcomePanel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import animatedGUI.*;

import javax.swing.border.*;
import gameDisplayProcessor.*;

import java.awt.image.*;

public class DownBasket extends APanel
{
	//for debug
	private static final boolean debug=false;
	
	//date fields
    private int current=0;
    private static final int COUNTER_SPAN=15;
	private int counter=0;
    
    public static final ImageIcon selfGif=new ImageIcon("pic/SubGame/shootingPractice/GUI/downBasket.gif");
	public static final Point selfLocation=new Point(38,484);

	public static final Point downBarLocation=new Point(47,20);
    
    private static AAnimationClip dClip=new AAnimationClip(
			new Point(WelcomePanel.basketLocations[3].x+selfGif.getIconWidth(),WelcomePanel.basketLocations[3].y),
			selfGif.getIconWidth(),
			1000/AAnimationClip.getFrameInterval(),
			AAnimationClip.SIN_MOTION,
			AAnimationClip.LEFT);

    private static final int numOfBarGifs=26;
    private static final ImageIcon[] barGifs=new ImageIcon[numOfBarGifs];
    private static final APanel[] bars=new APanel[numOfBarGifs];
    
    public DownBasket()
    {
    	super(selfGif,dClip,AComponent.POP_AND_SHOW);
    	
    	for(int i=0;i<bars.length;i++)
    	{
    		barGifs[i]=new ImageIcon("pic/SubGame/shootingPractice/GUI/"+i+".gif");
    	    bars[i]=new APanel(barGifs[i],new Point(downBarLocation.x+barGifs[0].getIconWidth()*i,downBarLocation.y));
    	
    	    bars[i].setVisible(false);
    	    add(bars[i]);
    	}
    	
    }

    protected void selfProcess()
    {
    	super.selfProcess();
    	
    	if(PREPARED==getPopState())
    	{
    		
    		for(int i=0;i<bars.length;i++)
    		{
    			if(i<=current)
    			{
    				if(i!=bars.length-1)
    				{
    					bars[i].setVisible(true);
    				}
    			}
    			else
    			{
    				bars[i].setVisible(false);
    			}
    		}
    		
    		counter++;
    		if (counter >= COUNTER_SPAN) 
    		{
				if (current < numOfBarGifs - 1) 
				{
					current++;
				} 
				else 
				{
					current = 0;
				}
				
				counter=0;
			}
    	
    	}
    	
    }
}
