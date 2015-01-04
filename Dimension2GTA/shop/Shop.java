package shop;

import subGameSuper.*;
import javax.swing.*;
import javax.swing.BorderFactory.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import gameDisplayProcessor.*;

import utilities.*;
import nameConstants.*;
import weapon.*;
import clothes.*;
import javax.swing.border.*;
import food.*;

public class Shop extends SubGame
implements ActionListener,ListSelectionListener,KeyListener,MouseWheelListener
{
	//for debug
	private static final boolean debuger=false;
	
	private JList list;
	private JButton buyButton=new JButton("Buy");
	private JButton CancleButton=new JButton("Cancle");
	
	private ImageViewer imageViewer=new ImageViewer();
	private JTextArea infoArea=new JTextArea();
	
	private JMenuBar menuBar=new JMenuBar();
		
	private JMenu menu1=new JMenu("File");
	private JMenuItem menuBuy=new JMenuItem("Buy");
	private JMenuItem menuExit=new JMenuItem("Exit");
	
	private JMenu menu2=new JMenu("About");
	private JMenuItem menuHelp=new JMenuItem("Help");
	private JMenuItem menuAbout=new JMenuItem("About");
	
	private JLabel currentWeapon=new JLabel();
	private JLabel currentMoney=new JLabel(); 
	
	//for 常用商店模型
	public static final int WEAPON_SHOP_LOWER_CLASS=0;
	public static final int WEAPON_SHOP_MIDDLE_CLASS=1;
	public static final int WEAPON_SHOP_UPPER_CLASS=2;
	public static final int WEAPON_SHOP_SUPER_CLASS=3;
	public static final int CLOTHES_SHOP=4;
	public static final int HUMBURGER_SHOP=5;
	public static final int CHICKEN_SHOP=6;
	//商品区 
	private Item[] items=null;
	
	public Shop(Player player,MainGameWindow master,Item[] items)
	{
		super(player,null,master);
		
		imageViewer.setStretched(true);
		
		myInitializeGUI(items);
	}

	//用NameConstants中的常量来构造商店
	public Shop(Player player,MainGameWindow master,int[] itemNames)
	{
		super(player,null,master);
		
		imageViewer.setStretched(true);
		
		Item[] newItems=null;
		if(itemNames!=null)
		{
			for(int i=0;i<itemNames.length;i++)
			{
				switch(itemNames[i])
				{
				    case NameConstants.WEAPON_GUN_AK47 :
				    {
				    	newItems=addItem(newItems,new AK47(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_AUG :
				    {
				    	newItems=addItem(newItems,new AUG(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_AWP :
				    {
				    	newItems=addItem(newItems,new AWP(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_M4 :
				    {
				    	newItems=addItem(newItems,new M4(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_SIG552 :
				    {
				    	newItems=addItem(newItems,new SIG552(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_MP5 :
				    {
				    	newItems=addItem(newItems,new MP5(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_DESERT_EAGLE :
				    {
				    	newItems=addItem(newItems,new DesertEagle(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_M249 :
				    {
				    	newItems=addItem(newItems,new M249(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_METAL_STORM :
				    {
				    	newItems=addItem(newItems,new MetalStorm(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_CHEAP_GUN :
				    {
				    	newItems=addItem(newItems,new CheapGun(null));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_ROCKET_LAUNCHER :
				    {
				    	newItems=addItem(newItems,new RocketLauncher(1));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_GUN_ROCKET :
				    {
				    	newItems=addItem(newItems,new Rocket(new MyPoint(-10,-10),new VectorClass(0,0),0));
				    	break;
			    	}
				    
				    case NameConstants.WEAPON_HEGRENADE: {
				    	newItems=addItem(newItems,new Hegrenade(new MyPoint(-10,-10),new VectorClass(0,0)));
						break;
					}
				    
				    case NameConstants.WEAPON_VEST: {
				    	newItems=addItem(newItems,new Vest(0.8-0.48*Math.random(),75));
						break;
					}
				    case NameConstants.WEAPON_BULLET_ITEM: {
				    	newItems=addItem(newItems,new BulletItem());
						break;
					}
				    
				    case NameConstants.CLOTHES_PLAYER_STANDARD :{
				    	newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_PLAYER_STANDARD));
				    	break;
				    }
				    case NameConstants.CLOTHES_NPC_STANDARD :{
				    	newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_NPC_STANDARD));
				    	break;
				    }
				    
				    case NameConstants.CLOTHES_1 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_1));
				        break;
				    }
				     case NameConstants.CLOTHES_2 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_2));
				        break;
				    }
				     case NameConstants.CLOTHES_3 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_3));
				        break;
				    }
				     case NameConstants.CLOTHES_4 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_4));
				        break;
				    }
				     case NameConstants.CLOTHES_5 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_5));
				        break;
				    }
				     case NameConstants.CLOTHES_6 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_6));
				        break;
				    }
				     case NameConstants.CLOTHES_7 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_7));
				        break;
				    }
				     case NameConstants.CLOTHES_8 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_8));
				        break;
				    }
				     case NameConstants.CLOTHES_9 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_9));
				        break;
				    }
				     case NameConstants.CLOTHES_10 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_10));
				        break;
				    }
				     case NameConstants.CLOTHES_11 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_11));
				        break;
				    }
				     case NameConstants.CLOTHES_12 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_12));
				        break;
				    }
				     case NameConstants.CLOTHES_13 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_13));
				        break;
				    }
				     case NameConstants.CLOTHES_14 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_14));
				        break;
				    }
				     case NameConstants.CLOTHES_15 :{
				        newItems=addItem(newItems,new Clothes(NameConstants.CLOTHES_15));
				        break;
				    }
				     
				     case NameConstants.FOOD_0 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_0));
				    	     break;
				    	}
				    	case NameConstants.FOOD_1 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_1));
				    	     break;
				    	}
				    	case NameConstants.FOOD_2 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_2));
				    	     break;
				    	}
				    	case NameConstants.FOOD_3 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_3));
				    	     break;
				    	}
				    	case NameConstants.FOOD_4 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_4));
				    	     break;
				    	}
				    	case NameConstants.FOOD_5 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_5));
				    	     break;
				    	}
				    	case NameConstants.FOOD_6 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_6));
				    	     break;
				    	}
				    	case NameConstants.FOOD_7 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_7));
				    	     break;
				    	}
				    	case NameConstants.FOOD_8 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_8));
				    	     break;
				    	}
				    	case NameConstants.FOOD_9 :{
				    	    newItems=addItem(newItems,new Food(NameConstants.FOOD_9));
				    	     break;
				    	}

				    
				    default :
				    {
				    	break;
				    }
				}
			}
		}
		
        
		
		myInitializeGUI(newItems);
		
	}

	//常用商店模型
	public Shop(Player player,MainGameWindow master,int type)
	{
		super(player,null,master);
		
		imageViewer.setStretched(true);
		
		if(debuger)
		{
			System.out.println("====in method Shop(by type)====");
    	}
		
		Item[] itemArray=null;
		
		if(WEAPON_SHOP_LOWER_CLASS==type)
		{
			if(debuger)
			{
				System.out.println("WEAPON_SHOP_LOWER_CLASS==type");
			}
			
			itemArray=addItem(itemArray,new CheapGun(null));
			if(debuger)
			{
				System.out.println("after add cheapGun, is itemArray null?"+(itemArray==null));
			}
			
			itemArray=addItem(itemArray,new MP5(null));
			if(debuger)
			{
				System.out.println("after add mp5, is itemArray null?"+(itemArray==null));
			}
			itemArray=addItem(itemArray,new DesertEagle(null));
			itemArray=addItem(itemArray,new BulletItem());
		
		}
		
		if(WEAPON_SHOP_MIDDLE_CLASS==type)
		{
			
			itemArray=addItem(itemArray,new AK47(null));
			itemArray=addItem(itemArray,new M4(null));
			itemArray=addItem(itemArray,new Hegrenade(new MyPoint(-10,-10),new VectorClass(0,0)));
		    itemArray=addItem(itemArray,new Vest(0.8-0.48*Math.random(),75));
		
		    itemArray=addItem(itemArray,new CheapGun(null));
			itemArray=addItem(itemArray,new MP5(null));
			itemArray=addItem(itemArray,new DesertEagle(null));
			itemArray=addItem(itemArray,new BulletItem());
		
		}
		
		if(WEAPON_SHOP_UPPER_CLASS==type)
		{
			
			itemArray=addItem(itemArray,new MetalStorm(null));
			itemArray=addItem(itemArray,new RocketLauncher(1));
			itemArray=addItem(itemArray,new Rocket(new MyPoint(-10,-10),new VectorClass(0,0),0));
			
			itemArray=addItem(itemArray,new CheapGun(null));
			itemArray=addItem(itemArray,new MP5(null));
			itemArray=addItem(itemArray,new DesertEagle(null));
			itemArray=addItem(itemArray,new BulletItem());
			
			itemArray=addItem(itemArray,new AK47(null));
			itemArray=addItem(itemArray,new M4(null));
			itemArray=addItem(itemArray,new Hegrenade(new MyPoint(-10,-10),new VectorClass(0,0)));
		    itemArray=addItem(itemArray,new Vest(0.8-0.48*Math.random(),75));
		}
		if(WEAPON_SHOP_SUPER_CLASS==type)
		{
			
			itemArray=addItem(itemArray,new SIG552(null));
			itemArray=addItem(itemArray,new AUG(null));
			itemArray=addItem(itemArray,new AWP(null));
			itemArray=addItem(itemArray,new M249(null));
			
			itemArray=addItem(itemArray,new CheapGun(null));
			itemArray=addItem(itemArray,new MP5(null));
			itemArray=addItem(itemArray,new DesertEagle(null));
			itemArray=addItem(itemArray,new BulletItem());
			
			itemArray=addItem(itemArray,new AK47(null));
			itemArray=addItem(itemArray,new M4(null));
			itemArray=addItem(itemArray,new Hegrenade(new MyPoint(-10,-10),new VectorClass(0,0)));
		    itemArray=addItem(itemArray,new Vest(0.8-0.48*Math.random(),75));
		
		    itemArray=addItem(itemArray,new MetalStorm(null));
			itemArray=addItem(itemArray,new RocketLauncher(1));
			itemArray=addItem(itemArray,new Rocket(new MyPoint(-10,-10),new VectorClass(0,0),0));
		
		}
		
		if(CLOTHES_SHOP==type)
		{
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_PLAYER_STANDARD));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_NPC_STANDARD));
		    
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_1));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_2));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_3));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_4));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_5));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_6));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_7));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_8));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_9));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_10));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_11));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_12));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_13));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_14));
			itemArray=addItem(itemArray,new Clothes(NameConstants.CLOTHES_15));
		
		}
		
		if(HUMBURGER_SHOP==type)
		{
			itemArray=addItem(itemArray,new Food(NameConstants.FOOD_0));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_1));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_2));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_3));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_4));
		    
		}
		
		if(CHICKEN_SHOP==type)
		{
			itemArray=addItem(itemArray,new Food(NameConstants.FOOD_5));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_6));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_7));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_8));
		    itemArray=addItem(itemArray,new Food(NameConstants.FOOD_9));
		}
		
		myInitializeGUI(itemArray);
	}
	
	private void myInitializeGUI(Item[] items)
	{
        //初始化items
		this.items=items;
		if(debuger)
		{
			System.out.println("here");
			System.out.println("=====in method myInitializeGUI====");
			System.out.println("is items null? "+(items==null));
		}
		
		//生成GUI界面
		setLayout(new BorderLayout());
		
		//初始化list
		String[] names=null;
		if(items!=null)
		{
			for(int i=0;i<items.length;i++)
		    {
			    if(items[i]!=null)
			    {
				    names=addString(names,items[i].getName());
			    }
		    }
		}
		if(debuger)
		{
			System.out.println("is names null? "+(names==null));
		}
		
		list=new JList(names);
		list.setBackground(new Color(185,219,247));
		list.setSelectionBackground(Color.BLUE);
		list.setSelectionForeground(Color.WHITE);
		
			
		//初始化文本区
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);
		infoArea.setFont(new Font("Times",Font.BOLD,12));
		infoArea.setText("    Please select some items to buy.");
		
		infoArea.setRows(5);
		infoArea.setColumns(10);
		infoArea.setEditable(false);
		//初始化currentWeapon and currentMoney
		currentWeapon.setFont(new Font("Times",Font.BOLD,12));
		currentWeapon.setBorder(new LineBorder(Color.WHITE,2));
		currentWeapon.setHorizontalAlignment(JLabel.LEFT);
		currentWeapon.setPreferredSize(new Dimension(75,16));
		refreshCurrentWeapon();
		
		currentMoney.setFont(new Font("Times",Font.BOLD,12));
		currentMoney.setBorder(new LineBorder(Color.WHITE,2));
		currentMoney.setHorizontalAlignment(JLabel.RIGHT);
		refreshCurrentMoney();
		
		
		//初始化imageViewer
		imageViewer.setPreferredSize(new Dimension(260,160));
		imageViewer.setBorder(new LineBorder(Color.BLUE,2));
		//加入组件
		JPanel p1=new JPanel(new BorderLayout());
		p1.add(new JScrollPane(list),BorderLayout.CENTER);
		JPanel p11=new JPanel(new BorderLayout());
		p11.add(buyButton,BorderLayout.NORTH);
		p11.add(CancleButton,BorderLayout.SOUTH);
	
		p1.add(p11,BorderLayout.SOUTH);
		add(p1,BorderLayout.WEST);
		
		JPanel p2=new JPanel(new BorderLayout());
		p2.add(imageViewer,BorderLayout.NORTH);
		p2.add(new JScrollPane(infoArea),BorderLayout.CENTER);
	    add(p2,BorderLayout.CENTER);
	    JPanel p21=new JPanel(new BorderLayout());
	    p21.add(currentWeapon,BorderLayout.WEST);
	    p21.add(currentMoney,BorderLayout.CENTER);
	    p2.add(p21,BorderLayout.SOUTH);
	    
	   
	    
	    //addListeners
	    list.addListSelectionListener(this);
	    buyButton.addActionListener(this);
	    CancleButton.addActionListener(this);
	    addMouseWheelListener(this);
	    addKeyListener(this);
	    
	    menuBuy.addActionListener(this);
	    menuExit.addActionListener(this);
	    menuHelp.addActionListener(this);
	    menuAbout.addActionListener(this);
	    
	    setFocusable(true);
	    list.setFocusable(false);
	    //start
	    setFrame();
	}
	
	public void setFrame()
	{
		if(debuger)
		{
			System.out.println("is getFrame() null? "+(getFrame()==null));
		}
		
		 //设置菜单栏
	    getFrame().setJMenuBar(menuBar);
	    menuBar.add(menu1);
	    menuBar.add(menu2);
	    menu1.add(menuBuy);
	    menu1.add(menuExit);
	    menu2.add(menuHelp);
	    menu2.add(menuAbout);
		
	    
	    getFrame().pack();
	  
	   	Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2-50);
		
		getFrame().setResizable(false);
		getFrame().setVisible(true);
	}

	private static String[] addString(String[] stringArray,String s)
	{
		if(null==s)
		{
			return stringArray;
		}
		
		if(null==stringArray)
		{
			stringArray=new String[1];
			stringArray[0]=s;
		}
		else
		{
			String[] newStringArray=new String[stringArray.length+1];
			System.arraycopy(stringArray,0,newStringArray,0,stringArray.length);
			newStringArray[stringArray.length]=s;
			stringArray=newStringArray;
		}
		
		return stringArray;
	}
	
	private static Item[] addItem(Item[] itemArray,Item s)
	{
		if(debuger)
		{
			System.out.println("====in method addItem====");
			System.out.println("is s null?"+(s==null));
			System.out.println("is itemArray null?"+(itemArray==null));
		}
		
		if(null==s)
		{
			return itemArray;
		}
		
		if(null==itemArray)
		{
			if(debuger)
			{
				System.out.println("in if(null==itemArray) : ");
			}
			
			itemArray=new Item[1];
			
			if(debuger)
			{
				System.out.println("after itemArray=new Item[1]");
				System.out.println("is s null?"+(s==null));
				System.out.println("is itemArray null?"+(itemArray==null));
			}
			itemArray[0]=s;
			
			if(debuger)
			{
				System.out.println("after itemArray[0]=s");
				
				System.out.println("is itemArray null?"+(itemArray==null));
			}

		}
		else
		{
			Item[] newItemArray=new Item[itemArray.length+1];
			System.arraycopy(itemArray,0,newItemArray,0,itemArray.length);
			newItemArray[itemArray.length]=s;
			itemArray=newItemArray;
		}
		
		return itemArray;
	}

	
	//over ride

	public void actionPerformed(ActionEvent e)
	{
		refreshCurrentWeapon();
		refreshCurrentMoney();
		
		if((e.getSource()==CancleButton)
				||(e.getSource()==menuExit))
		{
				endOfGame();
		}
			
		if(e.getSource()==menuHelp)
		{
			JOptionPane.showMessageDialog(null,"  选择一种商品，点击Buy按钮就可以购买");
		}
		if(e.getSource()==menuAbout)
		{
				JOptionPane.showMessageDialog(null,"  Dimension2GTA SHOP By ZYL");
		}
		
		
		if((e.getSource()==menuBuy)
			||(e.getSource()==buyButton))
		{
			//购买商品
			Item selectedItem=null;
			if(items!=null)
			{
			    for(int i=0;i<items.length;i++)
			    {
			    	if(items[i]!=null)
				    {
			    		if(items[i].getName().equals(list.getSelectedValue()))
				     	{
				        	selectedItem=items[i];
				    	}
				    }
		    	}
			}
			
			if(selectedItem!=null)
			{
			    boolean isSuccBuy=selectedItem.Buy(getPlayer());
			
			    if(isSuccBuy)
			    {
			    	JOptionPane.showMessageDialog(null,"Successful Buy");
			    }
			}
		}
	}
	
	public void valueChanged(ListSelectionEvent e) 
	{
		refreshCurrentWeapon();
		refreshCurrentMoney();
		
		Item selectedItem=null;
		if(items!=null)
		{
		    for(int i=0;i<items.length;i++)
		    {
		    	if(items[i]!=null)
			    {
		    		if(items[i].getName().equals(list.getSelectedValue()))
			     	{
			        	selectedItem=items[i];
			        	
			    	}
			    }
	    	}
		}
		
		if(selectedItem!=null)
		{
			imageViewer.setImage(selectedItem.getPic());
			infoArea.setText(selectedItem.getInfo()+"\n"+"Price: "+selectedItem.getPrice());
		}
		
	}

	private void refreshCurrentWeapon()
	{
		Weapon weapon=getPlayer().getWeaponList()[getPlayer().getCurrentWeapon()];
		
		if(weapon instanceof Fists)
		{
			currentWeapon.setText("Fists");
		}
		if(weapon instanceof Gun)
		{
			String text="";
			text+=((Gun)weapon).getGunName();
			currentWeapon.setText(text);
		}
		if(weapon instanceof HegrenadeBell)
		{
            String text="";
            text+="HegrenadeBag";
            currentWeapon.setText(text);
            
		}
		if(weapon instanceof RocketLauncher)
		{

            String text="";
            text+="RocketLauncher";
            currentWeapon.setText(text);
		}
		currentWeapon.repaint();
	}
	
	private void refreshCurrentMoney()
	{
		currentMoney.setText("$"+getPlayer().getMoney());
	}

	 
	public void winByOpponent() {
		// TODO Auto-generated method stub
		
	}


	 
	public void winByPlayer() {
		// TODO Auto-generated method stub
		
	}

	 
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(e.getWheelRotation()==1)
		{
			getPlayer().switchCurrentWeaponForth();
		}
		if(e.getWheelRotation()==-1)
		{
			getPlayer().switchCurrentWeaponBack();
		}
		refreshCurrentWeapon();
		refreshCurrentMoney();
	}

	 
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==ControlSetting.getSwitchWeapon())
		{
			getPlayer().switchCurrentWeaponForth();
		}	
		
		refreshCurrentWeapon();
		refreshCurrentMoney();
	}

	 
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	

}




