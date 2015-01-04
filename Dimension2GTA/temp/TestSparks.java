package temp;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

import utilities.*;

public class TestSparks extends JPanel
implements ActionListener, KeyListener
{
	private SubSpark subSpark=new SubSpark(new MyVector3D(0,100,1000),new MyPoint3D(0,100,100));
	private double arc=0;
	private PlaneCoordinateSystemIn3D plane=new PlaneCoordinateSystemIn3D(arc,600,new Dimension(800,600));
	
	private Sparks sparks=new Sparks(new MyPoint3D(100,100,100),new MyVector3D(0,100,100),true);
	private Timer timer=new Timer(10,this);
	
	public TestSparks()
	{
		timer.start();
		addKeyListener(this);
	    setFocusable(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==timer)
		{
			subSpark.selfProcess();
			sparks.selfProcess();
			repaint();
		}
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		subSpark.paint(g, plane);
		sparks.paint(g, plane);
	}
	
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.getContentPane().add(new TestSparks());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,600);
		frame.setVisible(true);
		}

	 
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			setArc(getArc()-1.0/18*Math.PI);
		}
		
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			setArc(getArc()+1.0/18*Math.PI);
		}
	}

	 
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public double getArc()
	{
		return arc;
	}
	
	public void setArc(double arc)
	{
		this.arc=arc;
		plane=new PlaneCoordinateSystemIn3D(arc,600,new Dimension(800,600));
	}

}
