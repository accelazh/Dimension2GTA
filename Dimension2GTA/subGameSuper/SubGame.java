package subGameSuper;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import gameDisplayProcessor.*;

import utilities.*;

//所有subGame(小游戏或者商店等)，都通过扩展SubGame类实现，在子类
//的构造方法中，有super(player,opponent,master),在结束的时候，
//必须调用endOfGame方法。不用负责关闭游戏窗口，游戏在一个JPanel
//上编辑。在游戏中，按照游戏内容改变player和opponent的属性的任务
//交给子类处理。而使子类的GUI界面出现在MainGameWindow中和归还
//player和opponent，并使子类的GUI界面消失的任务交给父类处理。
//subGame的子类必须调整frame，否则会显示不出来
public abstract class SubGame extends JPanel 
implements WindowListener
{
	//data field
	private Player player;
	private Human opponent;
	
	private MainGameWindow master;
	private ImageViewer imageViewer;
	
	private JFrame frame;
		
	public SubGame(Player player,Human opponent,MainGameWindow master)
	{
		this.player=player;
		this.opponent=opponent;
		this.master=master;
		
		this.imageViewer=new ImageViewer("pic/SubGame/useful/none.jpg");	
		this.imageViewer.setStretched(true);
		
		this.frame=new JFrame();
		frame.addWindowListener(this);		
		
		initialization();
	}
	
	//这个两个方法提醒子类要实现player和opponent的输赢处理办法
	//如果子类并不是一个输赢类的游戏，那么这两个方法为空即可
	public abstract void winByPlayer();
	
	public abstract void winByOpponent();
	
	//这个方法提醒子类的设计者，要调整好frame
	public abstract void setFrame();
	
	//负责初始化
	final private void initialization()
	{
		if(master!=null)
		{
			master.getGodTimer().stop();
		    master.getScreenDisplayPanel().setVisible(false);
		
		    master.getLandMapContainer().removeSolid(player);
		    master.getLandMapContainer().removeSolid(opponent);
		
			master.getPlayer().setUseRequested(false);
			master.getPlayer().setMoveUpRequested(false);
			master.getPlayer().setMoveDownRequested(false);
			master.getPlayer().setMoveLeftRequested(false);
			master.getPlayer().setMoveRightRequested(false);
					    
		    //在屏幕中加载背景图
		    master.getContentPane().add(imageViewer,BorderLayout.CENTER);
		}		    
		    //在新窗口中加载小游戏
		    frame.getContentPane().add(this);
		   		    		    
		    frame.setTitle("Enjoy it!");
		   
		    	    
		
	}
	
	//负责将screenDisplayPanel还原，并还回player和opponent
	final public void endOfGame()
	{
		endOfGameMessage();
		
		if (master!=null)
		{
			
			master.getLandMapContainer().addSolid(player);
			master.getLandMapContainer().addSolid(opponent);
			master.remove(imageViewer);
			master.getScreenDisplayPanel().setVisible(true);
			
			master.getPlayer().setUseRequested(false);
			master.getPlayer().setMoveUpRequested(false);
			master.getPlayer().setMoveDownRequested(false);
			master.getPlayer().setMoveLeftRequested(false);
			master.getPlayer().setMoveRightRequested(false);
			
			master.getGodTimer().start();
		}
		frame.dispose();
		
	}
	
	/**
	 * 子类可以覆盖这个方法，这个方法将在窗口推出时调用
	 */
	public void endOfGameMessage()
	{
		
	}

	public Player getPlayer() {
		return player;
	}

	public Human getOpponent() {
		return opponent;
	}

	public JFrame getFrame() {
		return frame;
	}
	
	
	 
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void windowClosed(WindowEvent arg0) 
	{
		
		
	}

	 
	public void windowClosing(WindowEvent arg0) 
	{
		endOfGame();
		
	}

	 
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	 
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

		

}
