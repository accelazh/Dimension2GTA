package temp;

import java.awt.*;
import javax.swing.*;
import utilities.*;
import javax.swing.border.*;
import java.awt.event.*;

/**
 * 
 * @author ZYL
 * ≤‚ ‘MyLineImage3D
 *
 */
public class temp5 extends JFrame 
{
	
	public static void main(String[] args)
	{
		JFrame frame=new temp5();
	}
	
	public temp5()
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1024,768);
		this.setLayout(new BorderLayout());
		this.getContentPane().add(new Panel());
		
		
		this.setVisible(true);
	}
	
	private class Panel extends JPanel implements ActionListener
	{
		PlaneCoordinateSystemIn3D plane=new PlaneCoordinateSystemIn3D(0,600,new Dimension(800,600)); 
		MyLineImage3D lineImage=MyLineImage3D.createLineImage(MyLineImage3D.OVAL_IMAGE);
		double arc=0;
		Timer timer=new Timer(10,this);
		
		public Panel()
		{
			this.setBorder(new LineBorder(Color.BLUE,2));
			lineImage.initializeImage();
			timer.start();
		}
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			System.out.println("paint");
			lineImage.paint(g, plane);
		}
		
		public void actionPerformed(ActionEvent e)
		{
			arc+=1.0/180*Math.PI;
			plane=new PlaneCoordinateSystemIn3D(arc,600,new Dimension(800,600));
		    repaint();
		}
	}

}
