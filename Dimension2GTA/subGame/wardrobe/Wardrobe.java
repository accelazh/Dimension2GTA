package subGame.wardrobe;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import subGameSuper.*;
import gameDisplayProcessor.*;
import clothes.*;
import utilities.*;

public class Wardrobe extends SubGame
implements ActionListener
{
	//for debug
	private final boolean debug=false;
	
	private ClothesPanel[] clothesPanels=null;
	private JButton OKButton=new JButton("OK");
	private JButton CancleButton=new JButton("Cancle");
	
	public Wardrobe(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		Clothes[] clothesList=getPlayer().getClothesList();
		
		if(debug)
		{
			System.out.println("immediately before adding clothes");
		}
		
		int n=0;  //一共多少件衣服
		for(int i=0;i<clothesList.length;i++)
		{
			if(clothesList[i]!=null)
			{
				clothesPanels=addClothesPanels(clothesPanels,clothesList[i]);
			    n++;
			}
		}
		
		if(debug)
		{
			System.out.println("immediately after adding clothes");
		}
		//计算行数
		n=(0==n%4)?(n/4):(n/4+1);
		setLayout(new BorderLayout());
		
		GridLayout gridLayout=new GridLayout(n,0);
		gridLayout.setHgap(15);
		gridLayout.setVgap(15);
		JPanel p1=new JPanel(gridLayout);
		for(int i=0;i<clothesPanels.length;i++)
		{
			p1.add(clothesPanels[i]);
		}
		
		JPanel p2=new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
		p2.add(OKButton);
		p2.add(CancleButton);
		
		setPreferredSize(new Dimension(260,195));
		setBorder(new LineBorder(Color.WHITE,2));		
		add(new JScrollPane(p1),BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		
		OKButton.addActionListener(this);
		CancleButton.addActionListener(this);
		
		setFrame();
	}
	
	public void setFrame()
	{
		if(debug)
		{
			System.out.println("====in method setFrame====");
		}

		getFrame().pack();
		getFrame().setTitle("Wardrobe");
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2-50);
		
		getFrame().setResizable(false);
		getFrame().setVisible(true);
		
		
	}
	
	 
	public void winByOpponent() 
	{
		// TODO Auto-generated method stub
		
	}

	 
	public void winByPlayer() 
	{
		// TODO Auto-generated method stub
		
	}
	
	private ClothesPanel[] addClothesPanels(ClothesPanel[] panelArray, Clothes c) 
	{

		if (null == c) {
			return panelArray;
		}

		if (null == panelArray) {
			panelArray = new ClothesPanel[1];
			panelArray[0] = new ClothesPanel(c);
			return panelArray;
		} else {
			ClothesPanel[] newPanelArray = new ClothesPanel[panelArray.length + 1];
			System
					.arraycopy(panelArray, 0, newPanelArray, 0,
							panelArray.length);
			newPanelArray[panelArray.length] = new ClothesPanel(c);
			return newPanelArray;
		}

	}

	public void cancleAllSelectedOnes()
	{
		for(int i=0;i<clothesPanels.length;i++)
		{
			if(clothesPanels[i]!=null)
			{
				clothesPanels[i].setSelected(false);
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==OKButton)
		{
			boolean find=false;
			Clothes clothes=null;
			for(int i=0;i<clothesPanels.length;i++)
			{
				if(clothesPanels[i]!=null)
				{
					if(clothesPanels[i].isSelected())
				    {
					    find=true;
				    	clothes=clothesPanels[i].clothes;
				    	break;
			    	}
				}
			}
			
			if(find)
			{
				getPlayer().setClothes(clothes);
				endOfGame();
			}
		}
		
		if(e.getSource()==CancleButton)
		{
			endOfGame();
		}
	}
	
	private class ClothesPanel extends JPanel
	implements MouseListener
	{
	    private Clothes clothes=null;
	    private ImageViewer imageViewer=new ImageViewer();
	    private JLabel nameLabel=new JLabel();
		
	    private boolean selected=false;
	    
		public ClothesPanel(Clothes clothes)
		{
			this.clothes=clothes;
			imageViewer.setImage(clothes.getPic());
			imageViewer.setPreferredSize(new Dimension(50,40));
			nameLabel.setForeground(Color.WHITE);
			nameLabel.setFont(new Font("Times",Font.BOLD,16));
			nameLabel.setPreferredSize(new Dimension(50,16));
		
			setLayout(new BorderLayout());
			add(imageViewer,BorderLayout.CENTER);
			add(nameLabel,BorderLayout.SOUTH);
			
			addMouseListener(this);
		}

		public boolean isSelected()
		{
			return this.selected;
		}
		
		public void setSelected(boolean b)
		{
			if(b)
			{
				selected=true;
				setBorder(new LineBorder(Color.BLUE,2));
			}
			else
			{
				selected=false;
				setBorder(null);
			}
		}
		
		 
		public void mouseClicked(MouseEvent e)
		{
			cancleAllSelectedOnes();
			setSelected(true);
			
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
	
	
}
