package temp;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import subGame.breakBrick.*;
import javax.swing.border.*;
import java.io.*;
import java.awt.image.*;
import subGame.breakBrick.mapEdition.*;

public class TestFileChooser 
{
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,300);
		frame.setVisible(true);
		
		JFileChooser fc=new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		
		fc.setFileFilter(new BrickTypesFileFilter());
		
		fc.showSaveDialog(frame);
		System.out.println(fc.getSelectedFile());
		System.out.println(fc.getSelectedFile().getParent());
		System.out.println(fc.getSelectedFile().getName());
		System.out.println(fc.getSelectedFile().getName().lastIndexOf('.'));
		System.out.println(fc.getSelectedFile().getName().substring(0,3));
		
		StringBuffer buffer=new StringBuffer("1234567");
		buffer.delete(1,3);
		System.out.println(buffer);
		
		BrickTypesFileFilter filter=new BrickTypesFileFilter();
		System.out.println("Adjusted File: "+filter.adjustToAccuratelyAcceptedFile(fc.getSelectedFile()));
		System.out.println("Original File: "+fc.getSelectedFile());
		
		System.out.println(filter.isLegalChar('<'));
		//System.out.println(JOptionPane.showConfirmDialog(null,new JLabel("Confirm")));
	}

}
