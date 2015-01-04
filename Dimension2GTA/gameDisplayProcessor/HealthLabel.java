package gameDisplayProcessor;

import javax.swing.*;
import java.awt.*;
import weapon.*;
public class HealthLabel extends JLabel
{
	
	public HealthLabel(int healthPoint,Vest vest)
	{
		
		setFont(new Font("Times",Font.BOLD+Font.ITALIC,12));
		if(null==vest)
		{
			setText("HP: "+healthPoint+"\n"+"Vest: null");
		}
		else
		{
			setText("HP: "+healthPoint+"\n"+"Vest: "+vest.getHealth());
		}
		
	}
	
	
	
	public void refresh(int healthPoint,Vest vest)
	{
		
		if(null==vest)
		{
			setText("HP: "+healthPoint+"\n"+"Vest: null");
		}
		else
		{
			setText("HP: "+healthPoint+"\n"+"Vest: "+vest.getHealth());
		}
		repaint();
	}
	
	

}
