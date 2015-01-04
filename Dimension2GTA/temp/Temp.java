package temp;

import javax.swing.ImageIcon;

import nameConstants.NameConstants;
import clothes.Clothes;
import javax.swing.*;
import java.awt.*;

public class Temp 
{
	public static void main(String[] args)
	{
		for(int i=1;i<=15;i++)
		{
			System.out.println(" case NameConstants.CLOTHES_"+i+" :{\n"+
				    	"    newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_"+i+"));\n"+
				    	 "    break;\n"+
				    "}");
		}
		
		for(int i=0;i<10;i++)
		{
			System.out.println(" case NameConstants.FOOD_"+i+" :{\n"+
				"    name=\"Food 1\";\n"+
			    "    imageIcon=new ImageIcon(\"pic/food/food"+i+".jpg\");\n"+
			    "    price=100;\n"+
			    "    points=20\n"+ 
			    "    break;\n"+
				"}\n");
		}
		
		for(int i=0;i<10;i++)
		{
			System.out.println("case NameConstants.FOOD_"+i+" :{\n"+
				        "    newItems=addItem(newItems,new Food(NameConstants.FOOD_"+i+"));\n"+
				        "     break;\n"+
				    "}");
		}
		
		for(int i=0;i<10;i++)
		{
			System.out.println("    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_"+i+"));");
			
		}
		
		Image image=Toolkit.getDefaultToolkit().getImage("123456.jpg");
		System.out.println("is image null? "+(null==image));
        ImageIcon imageIcon=new ImageIcon("123445.jpg");	
        System.out.println("is imageIcon null? "+(null==imageIcon));
	}

}
