package subGameSuper;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import basicConstruction.*;
import gameDisplayProcessor.*;

import utilities.*;

//����subGame(С��Ϸ�����̵��)����ͨ����չSubGame��ʵ�֣�������
//�Ĺ��췽���У���super(player,opponent,master),�ڽ�����ʱ��
//�������endOfGame���������ø���ر���Ϸ���ڣ���Ϸ��һ��JPanel
//�ϱ༭������Ϸ�У�������Ϸ���ݸı�player��opponent�����Ե�����
//�������ദ����ʹ�����GUI���������MainGameWindow�к͹黹
//player��opponent����ʹ�����GUI������ʧ�����񽻸����ദ��
//subGame������������frame���������ʾ������
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
	
	//�������������������Ҫʵ��player��opponent����Ӯ����취
	//������ಢ����һ����Ӯ�����Ϸ����ô����������Ϊ�ռ���
	public abstract void winByPlayer();
	
	public abstract void winByOpponent();
	
	//��������������������ߣ�Ҫ������frame
	public abstract void setFrame();
	
	//�����ʼ��
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
					    
		    //����Ļ�м��ر���ͼ
		    master.getContentPane().add(imageViewer,BorderLayout.CENTER);
		}		    
		    //���´����м���С��Ϸ
		    frame.getContentPane().add(this);
		   		    		    
		    frame.setTitle("Enjoy it!");
		   
		    	    
		
	}
	
	//����screenDisplayPanel��ԭ��������player��opponent
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
	 * ������Ը����������������������ڴ����Ƴ�ʱ����
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
