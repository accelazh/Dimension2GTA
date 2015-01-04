package subGame.shootingPractice;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import weapon.*;
import subGameSuper.*;

import basicConstruction.*;

//以后如果添加的了新的枪支，这里的面板个数要改

public class AddingGunSkill extends JPanel
implements ActionListener
{
	private int totalSkillPoints=0;
	private GunPanel[] gunPanels=null;
	
	private JLabel title=new JLabel("ADDING SKILL POINTS");
	private JLabel totalPointsLabel=new JLabel();
	
	private SubGame subGame=null;
	
	private JButton OKButton=new JButton("OK");
	
	public AddingGunSkill(SubGame subGame,int totalSkillPoints)
	
	{
		this.totalSkillPoints=totalSkillPoints;
		this.subGame=subGame;
		
		//初始化各个枪支的面板
		gunPanels=new GunPanel[10];
		gunPanels[0]=new GunPanel(new AK47(null));
		gunPanels[1]=new GunPanel(new AUG(null));
		gunPanels[2]=new GunPanel(new AWP(null));
		gunPanels[3]=new GunPanel(new CheapGun(null));
		gunPanels[4]=new GunPanel(new DesertEagle(null));
		gunPanels[5]=new GunPanel(new M249(null));
		gunPanels[6]=new GunPanel(new M4(null));
		gunPanels[7]=new GunPanel(new MetalStorm(null));
		gunPanels[8]=new GunPanel(new MP5(null));
		gunPanels[9]=new GunPanel(new SIG552(null));
		
		//初始化GUI
		setLayout(new BorderLayout());
		
		JPanel p1=new JPanel(new BorderLayout());
		title.setFont(new Font("Times",Font.BOLD,24));
		title.setForeground(Color.BLUE);
		title.setHorizontalAlignment(JLabel.CENTER);
		p1.add(title,BorderLayout.CENTER);
		
		totalPointsLabel.setFont(new Font("Times",Font.BOLD,20));
		totalPointsLabel.setForeground(Color.RED);
		totalPointsLabel.setText(""+this.totalSkillPoints);
		totalPointsLabel.setHorizontalAlignment(JLabel.CENTER);
		p1.add(totalPointsLabel,BorderLayout.SOUTH);
		
		add(p1,BorderLayout.NORTH);
		
	    JPanel p2=new JPanel(new GridLayout(5,0,10,10));
		for(int i=0;i<gunPanels.length;i++)
		{
			p2.add(gunPanels[i]);
		}
		
		add(p2,BorderLayout.CENTER);
		
		JPanel p3=new JPanel();
	    p3.setPreferredSize(new Dimension(40,200));
	    JPanel p4=new JPanel();
	    p4.setPreferredSize(new Dimension(40,200));
	    add(p3,BorderLayout.WEST);
	    add(p4,BorderLayout.EAST);
	    
	    JPanel p5=new JPanel(new FlowLayout());
	    p5.add(OKButton);
	    OKButton.addActionListener(this);
	    add(p5,BorderLayout.SOUTH);
	}
	
	
	
	 
	public void actionPerformed(ActionEvent e)
	{
		if(null==subGame)
		{
			return;
		}
		
		if(totalSkillPoints>0)
		{
			return;
		}
		
		if(e.getSource()==OKButton)
		{
			Player player=subGame.getPlayer();
			//处理加分
			for(int i=0;i<gunPanels.length;i++)
			{
				player.setGunSkill(gunPanels[i].gun.getGunNameCode(), gunPanels[i].points);
			}
			
			setVisible(true);
			subGame.endOfGame();
						
		}
		
	}



	private class GunPanel extends JPanel
	implements MouseListener
	{
		private Gun gun=null;
		private Font font=new Font("Times",Font.BOLD,16);
		private int points=0;
		
		private JLabel name=new JLabel();
		private JLabel point=new JLabel();
		private JLabel button=new JLabel();
		public GunPanel(Gun gun)
		{
			this.gun=gun;
						
			setLayout(new FlowLayout());
			setBorder(new LineBorder(Color.BLUE,2));
			
			name.setPreferredSize(new Dimension(90,20));
			name.setFont(font);
			name.setText(gun.getName());
			add(name);
			
			point.setPreferredSize(new Dimension(50,20));
			point.setFont(font);
			point.setText(""+points);
			point.setHorizontalAlignment(JLabel.CENTER);
			add(point);
			
			button.setPreferredSize(new Dimension(20,20));
			button.setIcon(new ImageIcon("pic/SubGame/shootingPractice/button.jpg"));
			add(button);
			
			button.addMouseListener(this);
		
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
			if(e.getSource()==button)
			{
				button.setIcon(new ImageIcon("pic/SubGame/shootingPractice/buttonPressed.jpg"));
			    if(totalSkillPoints>0)
			    {
			    	totalSkillPoints--;
			    	totalPointsLabel.setText(""+totalSkillPoints);
			        points++;
			        point.setText(""+points);
			        
			    }
			
			}
			
		}
		 
		public void mouseReleased(MouseEvent e) 
		{
			if(e.getSource()==button)
			{
				button.setIcon(new ImageIcon("pic/SubGame/shootingPractice/button.jpg"));
			    
			}
			
		}
		
		
	}

	public static void main(String[] args)
	{
		JFrame frame=new JFrame();
		frame.getContentPane().add(new AddingGunSkill(null,20));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
