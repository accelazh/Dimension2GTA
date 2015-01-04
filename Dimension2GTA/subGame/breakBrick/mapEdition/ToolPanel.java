package subGame.breakBrick.mapEdition;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import subGame.breakBrick.*;
import javax.swing.border.*;

/**
 * 
 * 地图制作窗口中的工具栏
 */
public class ToolPanel extends JPanel
implements Constants
{
	//for debug
	private static final boolean debug=false;
	
	private Dimension DEFAULT_SIZE=new Dimension(wallPanelSize.width,wallPanelSize.height-groundLine);

	private Image[] brickImages={
			brickImagesR[0],
			brickImagesO[0],
			brickImagesY[0],
			brickImagesG[0],
			brickImagesQ[0],
			brickImagesB[0],
			brickImagesD[0],
			brickImagesP[0],
			brickImagesH[0],
			brickImagesW[0],
			brickImagesExplosive[0],
			brickImagesFirm[0],
	};
	
	private Image mouseImage=Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/brick/mouse.gif");
	private Dimension mouseImageSize=new Dimension(32,32);
	
	private ToolButton[] buttons={
			new ToolButton(-1),
			new ToolButton(0),
			new ToolButton(1),
			new ToolButton(2),
			new ToolButton(3),
			new ToolButton(4),
			new ToolButton(5),
			new ToolButton(6),
			new ToolButton(7),
			new ToolButton(8),
			new ToolButton(9),
			new ToolButton(10),
			new ToolButton(11),
			
	};
	private int selectedType=-1;
	
	public ToolPanel()
	{
		this.setPreferredSize(DEFAULT_SIZE);
		this.setSize(DEFAULT_SIZE);
		this.setBorder(new LineBorder(Color.GREEN,3));
		this.setLayout(new FlowLayout(FlowLayout.CENTER,9,5));
		
		for(int i=0;i<buttons.length;i++)
		{
			add(buttons[i]);
		}
		selectedType=-1;
		buttons[0].setSelected(true);
	}
	
	private void buttonClickedPerformance(int brickType)
	{
		for(int i=0;i<buttons.length;i++)
		{
			buttons[i].setSelected(false);
		}
		
		buttons[brickType+1].setSelected(true);
		selectedType=brickType;
		
		if(debug)
		{
			System.out.println("button selected: "+getSelectedType());
		}
	}
	
	/**
	 * 
	 * @return 当前被选择的是哪种画笔, -1表示鼠标图案，0~11表示某一种砖块
	 */
	public int getSelectedType()
	{
		
		return selectedType;
	}
	
	private class ToolButton extends JPanel
	implements MouseListener
	{
		private Dimension buttonSize=new Dimension(50,50);
		public boolean selected=false;
		
		private int brickType=-1;  //砖块的种类，-1表示画鼠标图案
		
		public ToolButton(int brickType)
		{
			adjustBorder();
			this.setPreferredSize(buttonSize);
			this.setSize(buttonSize);
			this.setLayout(null);
			addMouseListener(this);
			if(brickType>=-1&&brickType<=11)
			{
				this.brickType=brickType;
			}
			
		}
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if(-1==brickType)
			{
				g.drawImage(mouseImage,(buttonSize.width-mouseImageSize.width)/2,(buttonSize.height-mouseImageSize.height)/2,this);
			}
			else
			{
				g.drawImage(brickImages[brickType],(buttonSize.width-brickSize.width)/2,(buttonSize.height-brickSize.height)/2,this);
			}
		}
		
		public boolean isSelected()
		{
			return selected;
		}
		
		public void setSelected(boolean selected)
		{
			this.selected=selected;
			adjustBorder();
			repaint();
		}
		
		private void adjustBorder()
		{
			if(selected)
			{
				this.setBorder(BorderFactory.createLoweredBevelBorder());
			}
			else
			{
				this.setBorder(BorderFactory.createRaisedBevelBorder());
			}
		}

		public void switchSelected()
		{
			if(selected)
			{
				setSelected(false);
			}
			else
			{
				setSelected(true);
			}
			
		}
		
		 
		public void mouseClicked(MouseEvent e) 
		{
			switchSelected();	
			buttonClickedPerformance(brickType);
		}

		 
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
	}
	
	//test
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		ToolPanel toolPanel=new ToolPanel();
		frame.getContentPane().add(toolPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
	
}
