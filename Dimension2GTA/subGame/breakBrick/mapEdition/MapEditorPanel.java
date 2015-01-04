package subGame.breakBrick.mapEdition;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import subGame.breakBrick.*;

import javax.swing.border.*;
import java.io.*;

import subGame.breakBrick.*;

public class MapEditorPanel extends JPanel
implements Constants
{
	private ToolPanel toolPanel=new ToolPanel();
	private PaintPanel paintPanel=new PaintPanel(toolPanel);
	private EditorMenuBar editorMenuBar=new EditorMenuBar(paintPanel);
	
	public MapEditorPanel()
	{
		this.setPreferredSize(GUI_SIZE);
		this.setSize(GUI_SIZE);
		this.setLayout(new BorderLayout());
		
		this.add(toolPanel,BorderLayout.SOUTH);
		this.add(paintPanel,BorderLayout.CENTER);
	}
	
	
	
	//test
	
	public ToolPanel getToolPanel() {
		return toolPanel;
	}



	public PaintPanel getPaintPanel() {
		return paintPanel;
	}



	public EditorMenuBar getEditorMenuBar() {
		return editorMenuBar;
	}

	public BrickBreak.PanelConductor getPanelConductor() {
		return editorMenuBar.getPanelConductor();
	}



	public void setPanelConductor(BrickBreak.PanelConductor panelConductor) {
		editorMenuBar.setPanelConductor(panelConductor);
	}
	
    //test
	public static void main(String[] args)
	{
        final JFrame frame=new JFrame();
		
        final MapEditorPanel mapEditorPanel=new MapEditorPanel();
        
		frame.getContentPane().add(mapEditorPanel);
		
		Timer timer=new Timer(1000,new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("frame size: "+frame.getSize());
				System.out.println("mapEditorPanel size: "+mapEditorPanel.getSize());
			}
		});
		
		//timer.start();
		
		frame.setJMenuBar(mapEditorPanel.getEditorMenuBar());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		frame.setVisible(true);	
	}
	



}

/*
 * 遗留问题： 1、chooseFile的文件过滤问题
 *          2、当brickType设为-1时，如何清除图像
 * 
 */
