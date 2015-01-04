package gameDisplayProcessor;

import javax.swing.*;
import java.awt.*;
public class MoneyLabel extends JLabel
{
	
	public MoneyLabel(long money)
	{
		
		setFont(new Font("Times",Font.BOLD,24));
		
		setText("| Money: "+money);
	}
	
	
	public void refresh(long money)
	{
		
		setText("| Money: "+money);
		repaint();
	}
}
