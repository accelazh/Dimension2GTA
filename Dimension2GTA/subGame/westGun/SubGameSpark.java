package subGame.westGun;


import java.awt.Dimension;
import java.awt.Point;


import javax.swing.*;

import java.awt.event.*;


/**
 * 
 * @author Administrator
 * 这个类用于创建枪发射子弹和子弹打中固体时的火花效果
 */
public class SubGameSpark implements ActionListener 
{
	private Timer timer=new Timer(10,null);  //用作制作动画的计时器
	private int onOrOff=OFF;
	public final static int ON=0;
	public final static int OFF=1;
	
	public Timer getTimer()
	{
		return timer;
	}
	
	public int getOnOrOff()
	{
		return this.onOrOff;
	}
	public void setOnOrOff(int onOrOff)
	{
		this.onOrOff=onOrOff;
	}
	//=======================================================//

	private Dimension totalSize;

	private Icon surfaceImage;

	private Point location;  //用于表明在屏幕显示区中的位置

	private JLabel picLabel;

	private boolean sparkVisible;

	private String sparkName;
	
	private final int sparkSize=10;
	
	private int currentState;
	private final int LAST_STATE;
	
	//methods
	public SubGameSpark() 
	{
		this(new Point(100,100));
	}

	public SubGameSpark(Point location) 
	{
		this.surfaceImage = new ImageIcon("pic/SubGame/WestGun/Spark/0.gif");
		this.location = location;
		
		this.sparkVisible = false;
        
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
		picLabel.setLocation(location.x-sparkSize/2,location.y-sparkSize/2);
		
		this.sparkName = "Nonnamed spark"; 
	
		getTimer().setDelay(50);
		getTimer().addActionListener(this);
		currentState=0;
		LAST_STATE=4;
		getTimer().start();
		setOnOrOff(ON);
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

	public void setLocation(Point location) {
		this.location = location;
		picLabel.setLocation(location.x-sparkSize/2,location.y-sparkSize/2);
	}
	
	

	
	public void setSparkVisible(boolean b) {
		this.sparkVisible = b;
		picLabel.setVisible(this.sparkVisible);
	}

	public Dimension getTotalSize() {
		return totalSize;
	}

	public Icon getSurfaceImage() {
		return surfaceImage;
	}

	public Point getLocation() {
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
	
	public void actionPerformed(ActionEvent e)
	{
		
		if(currentState>=LAST_STATE)
		{
			currentState=0;
			setSparkVisible(false);
			getTimer().stop();
			setOnOrOff(OFF);
			picLabel.repaint();
		}
		else
		{
			currentState++;
			String director="pic/SubGame/WestGun/Spark/"+currentState+".gif";
		    setSurfaceImage(new ImageIcon(director));
		    picLabel.repaint();
		}
	}
	
	public String toString()
	{
		return "SubGameSpark";
	}
	
	public void reStart(Point location)
	{
	
		setLocation(location);
		currentState=0;
		setSparkVisible(true);
		getTimer().start();
		setOnOrOff(ON);
		picLabel.repaint();
	}
	
	public void reStart()
	{
		currentState=0;
		setSparkVisible(true);
		getTimer().start();
		setOnOrOff(ON);
		picLabel.repaint();
	}
}

