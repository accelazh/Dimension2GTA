package temp;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import subGame.breakBrick.*;
import subGame.breakBrick.mapEdition.EditorMenuBar;
import subGame.breakBrick.mapEdition.PaintPanel;
import subGame.breakBrick.mapEdition.ToolPanel;

import javax.swing.border.*;
import java.io.*;

public class TestMenuBar 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		
		JMenuBar menuBar=new JMenuBar();
		//menuBar.add(new JMenu("File"));
		//menuBar.add(new JMenu("File"));
		
		frame.setJMenuBar(menuBar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setVisible(true);	
	}

}
