package subGame.shootingPractice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class SparkFlash 
{
	private ImageIcon sparkFlash=null;
	private Image sparkFlashImage=null;
	private int averageRadius=20;
	private int radius=0;
	private Point center=null;
	
	public static final int FLASH_TIME=3;
	private int flashTimeCounter=0;
	
	private boolean useAble=true;
	
	public SparkFlash(Point center)
	{
		this(center,20);
	}
	
	public SparkFlash(Point center, int averageRadius)
	{
		this.center=center;
		this.averageRadius=averageRadius;
		
		int rand=(int)(Math.random()*10);
	    sparkFlash=new ImageIcon("pic/SubGame/shootingPractice/sparkFlash"+rand+".gif");
	
	    this.radius=(int)(this.averageRadius+Math.random()*this.averageRadius);
	    
	    
	}
	
	public void selfProcess()
	{
		flashTimeCounter++;
		if(flashTimeCounter>=FLASH_TIME)
		{
			flashTimeCounter=0;
			useAble=false;
		}
	}
	
	public void paint(Graphics g, ImageObserver observer)
	{
		if(useAble)
		{
			Image image=sparkFlash.getImage();
			if(image!=null)
			{
				g.drawImage(image,center.x-radius,center.y-radius,2*radius,2*radius,observer);
			}
		}
	}

	public boolean isUseAble() {
		return useAble;
	}

	public int getAverageRadius() {
		return averageRadius;
	}

	public void setAverageRadius(int averageRadius) {
		this.averageRadius = averageRadius;
	}

	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	

}
