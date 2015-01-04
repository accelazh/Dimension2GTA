package animatedGUI;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.ImageIcon;
import java.awt.event.*;

public class APanel extends AComponent
{

	//constructors
	public APanel()
    {
    	super();
    }
	
	public APanel(ImageIcon imageIcon, Point location)
	{
	    super(imageIcon,location);
	}
	    
	public APanel(ImageIcon imageIcon, AAnimationClip animationClip, int mode)
	{
	    super(imageIcon,animationClip,mode);    	
	}

	@Override
	protected void mouseClickedOnMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mouseDraggedOnMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mouseEnteredMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mouseExitedMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mouseMovedOnMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mousePressedOnMe() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void mouseReleasedOnMe() {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	protected void keyPressedOnMe(KeyEvent e)
	{
		
	}
	@Override
	protected void keyReleasedOnMe(KeyEvent e)
	{
		
	}
    
}
