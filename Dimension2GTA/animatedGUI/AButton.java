package animatedGUI;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

public class AButton extends AAbstractButton
{
	
	//constructors
	
	public AButton()
    {
    	super();
    }
	
	//��ͼƬ�Ĺ��췽����
   public AButton(ImageIcon upImageIcon, Point location)
   {
	   super(upImageIcon, location);
   }
    
    
  //˫ͼƬ�Ĺ��췽����
   public AButton(ImageIcon upImageIcon, ImageIcon downImageIcon, Point location)
   {
   	    super(upImageIcon,downImageIcon,location);
   }
    
  // ��ͼƬ�Ĺ��췽����
   public AButton(ImageIcon upImageIcon, ImageIcon downImageIcon, ImageIcon mouseOnImageIcon, Point location)
   {
       	super(upImageIcon,downImageIcon,mouseOnImageIcon,location);
   }
   
}
