package temp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import subGame.breakBrick.*;
import javax.swing.border.*;

public class TestBorder 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setLayout(null);
		
		JPanel p=new JPanel();
		p.setPreferredSize(new Dimension(60,60));
		p.setSize(p.getPreferredSize());
		p.setBorder(BorderFactory.createRaisedBevelBorder());
		//p.setBorder(new LineBorder(Color.BLUE,2));
		
		p.setLocation(100,100);
		
		frame.getContentPane().add(p);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setVisible(true);
	}

}
