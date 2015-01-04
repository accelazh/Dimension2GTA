package basicConstruction;

import java.awt.Dimension;
import java.awt.Point;


import javax.swing.*;

import utilities.MyPoint;

import java.awt.event.*;


/*
 * 
 * @author Administrator
 * 这个类用于创建枪发射子弹和子弹打中固体时的火花效果,
 * 创建spark时，输入的location应为spark的中心位置，
 * 因为在screenDisplay中，会对spark做出对位置的校
 * 正。
 * 
 * 为了提高速度，Spark1的timer的任务--actionPerformed
 * 交给godTimer处理
 */
public class Spark1 extends Effect  
{

	private Dimension totalSize;

	private Icon surfaceImage;

	private MyPoint location;  //用于表明在大地图中的位置

	private JLabel picLabel;

	private boolean sparkVisible;

	private String sparkName;
	
	private String picSequenceLocator;
	
	
	private int currentState;
	private final int LAST_STATE;
	
	private int inactiveCount;
	private final int INACTIVE_SPAN=5;
	
	//methods
	public Spark1() 
	{
		this(new MyPoint(100,100));
	}

	public Spark1(MyPoint location) 
	{
		picSequenceLocator="pic/effect/spark1/";
		
		this.surfaceImage = new ImageIcon(picSequenceLocator+0+".jpg");
		this.location = location;
		
		
		
		this.sparkVisible = true;
        
		if(null!=surfaceImage)
        {	
        	this.totalSize = new Dimension(surfaceImage.getIconWidth(),
        		surfaceImage.getIconHeight());
        }
		else
		{
			this.totalSize=new Dimension(100,100);
		}
        
		    this.picLabel = new JLabel();
		picLabel.setIcon(surfaceImage);
		picLabel.setPreferredSize(totalSize);
		picLabel.setSize(totalSize);
		picLabel.setHorizontalAlignment(SwingConstants.CENTER);
		picLabel.setVerticalAlignment(SwingConstants.CENTER);
		picLabel.setVisible(this.sparkVisible);
		
		this.sparkName = "Nonnamed solid"; 
	
		inactiveCount=0;
		currentState=0;
		LAST_STATE=4;
		setOnOrOff(Effect.ON);
	}

	// sets and gets
	public void setTotalSize(Dimension totalSize) {
		this.totalSize = totalSize;
	}

	public void setSurfaceImage(Icon surfaceImage) {
		this.surfaceImage = surfaceImage;
		
		this.totalSize = new Dimension(surfaceImage.getIconWidth(),
				surfaceImage.getIconHeight());
		
		picLabel.setIcon(surfaceImage);
		picLabel.setPreferredSize(totalSize);
		picLabel.setSize(totalSize);
		
	}

	public void setLocation(MyPoint location) {
		this.location = location;
	}
	
	public void setLocation(Point location)
	{
		this.location=new MyPoint(location.x,location.y);
	}

	
	public void setSparkVisible(boolean b) {
		this.sparkVisible = b;
		picLabel.setVisible(this.sparkVisible);
	}

	public void setSolidName(String s) {
		this.sparkName = s;
	}

	public Dimension getTotalSize() {
		return totalSize;
	}

	public Icon getSurfaceImage() {
		return surfaceImage;
	}

	public MyPoint getLocation() {
		return location;
	}

	

	public boolean isSparkVisible() {
		return sparkVisible;
	}

	public String getSparkName() {
		return sparkName;
	}
	
	public JLabel getPicLabel()
	{
		return picLabel;
	}
	
	public MyPoint getDoubleCenterPoint()
	{
		return new MyPoint(getLocation().x+getTotalSize().getWidth()/2.0,
				getLocation().y+getTotalSize().getHeight()/2.0);
	}
	
	public Point getCenterPoint()
	{
		return getDoubleCenterPoint().getPoint();
	}
	
	public void actionPerformed()
	{
		inactiveCount++;
		if(INACTIVE_SPAN==inactiveCount)
		{
			inactiveCount = 0;
			if (currentState >= LAST_STATE) 
			{
				currentState = 0;
				setSparkVisible(false);
				getTimer().stop();
				setOnOrOff(Effect.OFF);

			} 
			else 
			{
				currentState++;
				String director = picSequenceLocator + currentState + ".jpg";
				setSurfaceImage(new ImageIcon(director));
			}
		}
		
		
	}

	public String getPicSequenceLocator() {
		return picSequenceLocator;
	}

	public void setPicSequenceLocator(String picSecquenceLocator) {
		this.picSequenceLocator = picSecquenceLocator;
	}
}
