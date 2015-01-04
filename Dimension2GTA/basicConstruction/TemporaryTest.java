package basicConstruction;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

public class TemporaryTest extends JFrame
{
	public TemporaryTest()
	{
		Icon iconImage1=new ImageIcon("pic/human1.jpg");
		JLabel jlbl=new JLabel();
		jlbl.setIcon(iconImage1);
		
		jlbl.setPreferredSize(new Dimension(jlbl.getIcon().getIconWidth(),
				jlbl.getIcon().getIconHeight()));
	    
		TitledBorder titledBorder=new TitledBorder("pic");
		jlbl.setBorder(titledBorder);
		
		
		Container container=getContentPane();
		container.add(jlbl);
		
		setTitle("Temporary Test");
		setSize(400,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
		
		setVisible(true);
		
		
	}
	
	public static void main(String[] args)
	{
		Rectangle rect=new Rectangle();
		rect.setSize(new Dimension(200,100));
		rect.setLocation(new Point(500,600));
		
		System.out.println("Rect : \n"+"x = "+rect.x+" , "+"getX() = "+rect.getX());
		System.out.println("y = "+rect.y+" , "+"getY() = "+rect.getY());
		System.out.println("Rect.getLocation() : ");
		System.out.println(rect.getLocation().x +"  "+rect.getLocation().y);
		System.out.println(rect.getLocation().getX()+"  "+rect.getLocation().getY());
	
	    System.out.println(Math.pow(10,3));
	    
	    TemporaryTest frame=new TemporaryTest();
	    
	    Rectangle rect2=new Rectangle();
	    rect2.setSize(new Dimension(200,200));
	    rect2.setLocation(new Point(0,0));
	    
	    System.out.println(rect2.contains(new Point(100,201)));
	   System.out.println(Math.sqrt(4));
	}

}
