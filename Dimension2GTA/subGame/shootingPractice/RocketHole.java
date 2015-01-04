package subGame.shootingPractice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;

public class RocketHole 
{
	private Point center=null;
	public final int radius=120;
	private ImageIcon imageIcon=new ImageIcon("pic/SubGame/shootingPractice/rocketHole.gif");
	private Image image=imageIcon.getImage();
	private ImageObserver observer=null;
	
	public RocketHole(Point center, ImageObserver observer)
	{
		this.center=center;
		this.observer=observer;
	}
	
	public void selfProcess()
	{
		
	}
	
	public void paint(Graphics g)
	{
		g.drawImage(image,center.x-imageIcon.getIconWidth()/2, center.y-imageIcon.getIconHeight()/2,observer);
	}
	
	public void screenMove(int step, boolean up)
	{
		if(up)
		{
			center=new Point(center.x,center.y+step);
		}
		else
		{
			center=new Point(center.x,center.y-step);
		}
	}

}
