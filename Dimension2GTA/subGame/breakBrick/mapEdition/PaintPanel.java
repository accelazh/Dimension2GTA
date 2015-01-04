package subGame.breakBrick.mapEdition;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import subGame.breakBrick.*;
import javax.swing.border.*;

public class PaintPanel extends JPanel
implements Constants
{
	//for debug
	private static boolean debug=false;
	
	private Dimension DEFAULT_SIZE=new Dimension(wallPanelSize.width,groundLine);
	
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
	
	private ToolPanel toolPanel;
    private BrickPanel[][] brickPanels=new BrickPanel[brickRowNum][brickColumNum];
	
    
    private String fileNameShown="";  //要写出的文件名
    
    public PaintPanel(ToolPanel toolPanel)
    {
    	this.toolPanel=toolPanel;
    	this.setPreferredSize(DEFAULT_SIZE);
    	this.setSize(DEFAULT_SIZE);
    	this.setLayout(null);
    	
    	this.setBackground(backgroundColor);
    	
    	for(int i=0;i<brickPanels.length;i++)
    	{
    		for(int j=0;j<brickPanels[i].length;j++)
    		{
    			brickPanels[i][j]=new BrickPanel(i,j);
    			add(brickPanels[i][j]);
    		}
    	}
    }
    
    public void setFileNameShown(String str)
    {
    	if(str!=null)
    	{
    		fileNameShown=str;
    	}
    	else
    	{
    		fileNameShown="";
    	}
    	repaint();
    }
    
    /**
     * 该方法返回地图中所有地方砖块的种类，-1表示没有砖块,0~11表示某一种砖块
     */
    public int[][] getBrickTypes()
    {
    	int[][] brickTypes=new int[brickRowNum][brickColumNum];
    	
    	for(int i=0;i<brickPanels.length;i++)
    	{
    		for(int j=0;j<brickPanels[i].length;j++)
    		{
    			brickTypes[i][j]=brickPanels[i][j].brickType;
    		}
    	}
    	
    	return brickTypes;
    	
    }
    
    public void initialize()
    {
    	for(int i=0;i<brickPanels.length;i++)
    	{
    		for(int j=0;j<brickPanels[i].length;j++)
    		{
    			brickPanels[i][j].setBrickType(-1);
    		}
    	}
    }
    
    public void initialize(int[][] brickTypes)
    {
    	boolean flag1=false;
    	if((brickTypes!=null)
    		&&(brickTypes.length==brickRowNum))
    	{
    		flag1=true;
    	}
    	boolean flag2=true;
    	for(int i=0;i<brickTypes.length;i++)
    	{
    		if(brickTypes[i].length!=brickColumNum)
    		{
    			flag2=false;
    		}
    	}
    	
    	if((!flag1)||(!flag2))
    	{
    		System.out.println("Error in initialize of PaintPanel");
    		return;
    	}
    	
    	for(int i=0;i<brickPanels.length;i++)
    	{
    		for(int j=0;j<brickPanels[i].length;j++)
    		{
    			brickPanels[i][j].setBrickType(brickTypes[i][j]);
    		}
    	}
    }
	
    protected void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
    	if (fileNameShown != null)
    	{
			g.setFont(new Font("Times New Roman", Font.BOLD, 18));
			g.setColor(Color.WHITE);
			FontMetrics fm = g.getFontMetrics();
			int length = fm.stringWidth(fileNameShown);
			g.drawString(fileNameShown, (DEFAULT_SIZE.width - length) / 2,
					DEFAULT_SIZE.height - 10);
		}
    }
    
	private class BrickPanel extends JPanel
	implements MouseListener,MouseMotionListener
	{
		private int brickType=-1;  //-1表示空，0~11表示某一种砖
		
		private int posI;  //表明自己的位置
		private int posJ;
		private Point brickLocation;
		
		private int counter=0;
		public BrickPanel(int posI,int posJ)
		{
			this.posI=posI;
			this.posJ=posJ;
			setPreferredSize(brickSize);
			setSize(brickSize);
			
			int offsetX = (wallPanelSize.width - brickColumNum
					* brickSize.width) / 2;
			brickLocation = new Point(offsetX + posJ * brickSize.width, posI
					* brickSize.height);
			setLocation(brickLocation);
			
			if(debug)
			{
				this.setBorder(new LineBorder(Color.BLUE,1));
			}
			
			setBackground(new Color(255,255,255,0));
		
		    addMouseListener(this);		
		    addMouseMotionListener(this);
		}
		
		private void setBrickType(int brickType)
		{
			this.brickType=brickType;
			repaint();
		}
		
		private void switchBrickType()
		{
			if(brickType<FIRM_PIC)
			{
				setBrickType(brickType+1);
			}
			else
			{
				setBrickType(-1);
			}
		}
		
		protected void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			if(brickType>=0)
			{
				g.drawImage(brickImages[brickType],0,0,this);
			}
			else
			{
				g.setColor(backgroundColor);
				g.fillRect(0,0,getWidth(),getHeight());
			}
		}

		 
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		 
		public void mousePressed(MouseEvent e) 
		{
			if (e.getButton()==MouseEvent.BUTTON1) 
			{
				if (toolPanel != null) {
					int type = toolPanel.getSelectedType();

					if (-1 == type) {
						switchBrickType();
					} else {
						if (brickType != type) {
							setBrickType(type);
						}
					}
				} else {
					switchBrickType();
				}
			}
			else
			{
				if(e.getButton()==MouseEvent.BUTTON3)
				{
					setBrickType(-1);
				}
			}
			if(debug)
			{
				System.out.println("mousePressed, currentBrickType: "+brickType);
			}
			
		}

		 
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
		 
		public void mouseDragged(MouseEvent e)
		{
			
		}

		 
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	//test
	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		ToolPanel toolPanel=new ToolPanel();
		PaintPanel paintPanel=new PaintPanel(toolPanel);
		frame.setLayout(new BorderLayout());
		frame.getContentPane().add(paintPanel,BorderLayout.CENTER);
		frame.getContentPane().add(toolPanel,BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
	}
}
