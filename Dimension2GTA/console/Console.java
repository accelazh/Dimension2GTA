package console;

import javax.swing.*;

import basicConstruction.Player;

import java.awt.*;

public class Console extends JFrame
{
	
	private JTextArea jtaInfo=new JTextArea();
	private Player player;
	
	public Console(Player player)
	{
		setLayout(new BorderLayout());
		this.player=player;
		getContentPane().add(new JScrollPane(jtaInfo));
		setTitle("Console");
		setSize(360,270);
		
	}
	
	public void refresh()
	{
		jtaInfo.setText(player.toString());
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	public void setVisible(boolean v)
	{
		super.setVisible(v);
		
		refresh();
	}

}
