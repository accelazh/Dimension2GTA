package subGame.longFan;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import basicConstruction.*;
import subGameSuper.*;
import gameDisplayProcessor.*;

public class LongFan extends SubGame implements ActionListener,
		RandomNumberPanelListener {
	//for debug
	private static final boolean localDebug = false;

	//data field

	final int PLAYER_TURN = 0;
	final int OPPONENT_TURN = 1;
	final int WIN_OR_DIE = 2;
	final int PLAYER_WIN = 3;
	final int OPPONENT_WIN = 4;

	private int currentState;

	private int AIPressTime1;
	private int AIPressTime2;
	private int AIPressTime3;

	private int AIPressTimeCount;
	private Timer AItimer;
	private final int AI_TIMER_INTERVAL = 200;

	private int playerHealth;
	private final int playerMaxHealth = 2500;

	private int opponentHealth;
	private final int opponentMaxHealth = 2500;

	private int winner;

	//GUI components

	private RandomNumberPanel randPanel = new RandomNumberPanel();
	private JButton pressButton = new JButton();

	private JLabel playerPortait = new JLabel();
	private Icon playerPortaitImage = new ImageIcon(
			"pic/SubGame/LongFan/playerPortait.jpg");
	private JLabel opponentPortait = new JLabel();
	private Icon opponentPortaitImage = new ImageIcon(
			"pic/SubGame/LongFan/opponentPortait.jpg");

	private JLabel playerHealthBar = new JLabel();
	private JLabel opponentHealthBar = new JLabel();
	
	private Icon playerHealthBarImage = new ImageIcon(
			"pic/SubGame/LongFan/healthBar1.jpg");
	private Icon opponentHealthBarImage = new ImageIcon(
	        "pic/SubGame/LongFan/healthBar2.jpg");

	
	private JLabel background = new JLabel();;
	private Icon backgroundImage = new ImageIcon(
			"pic/SubGame/LongFan/background.jpg");

	private Dimension idealSize;

	private JLabel currentScore;
	private JLabel whosRound;

	private JButton finishButton=new JButton("OK");
	
	//score pictures
	private ImageIcon[] playerScorePics={
			new ImageIcon("pic/SubGame/LongFan/scorePics/player0.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player1.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player2.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player3.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player4.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player5.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player6.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/player7.jpg")
			
	};
	
	private ImageIcon[] opponentScorePics={
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent0.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent1.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent2.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent3.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent4.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent5.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent6.jpg"),
			new ImageIcon("pic/SubGame/LongFan/scorePics/opponent7.jpg")
	};
	//methods

	public LongFan(Player player, Human opponent, MainGameWindow master) {
		super(player, opponent, master);

		AIPressTimeCount = 0;
		AItimer = new Timer(AI_TIMER_INTERVAL, this);
		AIPressTime1 = 0;
		AIPressTime2 = 0;
		AIPressTime3 = 0;

		playerHealth = playerMaxHealth;
		opponentHealth = opponentMaxHealth;

		winner = -1;

		//GUI setting
		idealSize = new Dimension(400, 300);
		setPreferredSize(idealSize);
		setSize(idealSize);
		
		setLayout(null);

		playerPortait.setIcon(playerPortaitImage);
		playerPortait.setPreferredSize(new Dimension(playerPortaitImage
				.getIconWidth(), playerPortaitImage.getIconHeight()));
		playerPortait.setSize(playerPortaitImage.getIconWidth(),
				playerPortaitImage.getIconHeight());

		opponentPortait.setIcon(opponentPortaitImage);
		opponentPortait.setPreferredSize(new Dimension(opponentPortaitImage
				.getIconWidth(), opponentPortaitImage.getIconHeight()));
		opponentPortait.setSize(opponentPortaitImage.getIconWidth(),
				opponentPortaitImage.getIconHeight());

		playerHealthBar.setIcon(playerHealthBarImage);
		playerHealthBar.setSize(playerPortait.getWidth(), 10);
		playerHealthBar.setBackground(Color.GREEN);
		playerHealthBar.setHorizontalAlignment(JLabel.CENTER);
		playerHealthBar.setVerticalAlignment(JLabel.CENTER);
		playerHealthBar.setHorizontalAlignment(JLabel.CENTER);
		playerHealthBar.setVerticalTextPosition(JLabel.CENTER);

		opponentHealthBar.setIcon(opponentHealthBarImage);
		opponentHealthBar.setSize(opponentPortait.getWidth(), 10);
		opponentHealthBar.setBackground(Color.GREEN);
		opponentHealthBar.setHorizontalAlignment(JLabel.CENTER);
		opponentHealthBar.setVerticalAlignment(JLabel.CENTER);
		opponentHealthBar.setHorizontalAlignment(JLabel.CENTER);
		opponentHealthBar.setVerticalTextPosition(JLabel.CENTER);

		pressButton.setText("Press");
		pressButton.setSize(pressButton.getPreferredSize());
		randPanel.setSize(randPanel.getPreferredSize());
		randPanel.setBorder(new LineBorder(Color.BLUE, 2));
		randPanel.setOwner(this);

		background.setIcon(backgroundImage);
		background.setSize(idealSize);
		background.setPreferredSize(idealSize);

		currentScore = new JLabel();
		currentScore.setPreferredSize(pressButton.getPreferredSize());
		currentScore.setHorizontalAlignment(JLabel.CENTER);
		currentScore.setVerticalAlignment(JLabel.CENTER);
		currentScore.setSize(currentScore.getPreferredSize());
		currentScore.setBorder(new LineBorder(Color.YELLOW, 2));
		currentScore.setFont(new Font("Times", Font.BOLD, 18));
		currentScore.setForeground(Color.RED);

		whosRound = new JLabel();
		whosRound.setPreferredSize(pressButton.getPreferredSize());
		whosRound.setHorizontalAlignment(JLabel.CENTER);
		whosRound.setVerticalAlignment(JLabel.CENTER);
		whosRound.setSize(whosRound.getPreferredSize());
		whosRound.setBorder(new LineBorder(Color.BLUE, 2));
		whosRound.setFont(new Font("Times", Font.BOLD, 12));
		whosRound.setForeground(Color.RED);

		finishButton.setVisible(false);
		finishButton.setSize(finishButton.getPreferredSize());
		//set GUI component's location and add them
		randPanel.setLocation((idealSize.width - randPanel.getWidth()) / 2, 60);
		add(randPanel);

		pressButton.setLocation((idealSize.width - pressButton.getWidth()) / 2,
				randPanel.getY() + randPanel.getHeight() + 10);
		add(pressButton);

		currentScore.setLocation(
				(idealSize.width - currentScore.getWidth()) / 2, pressButton
						.getY()
						+ currentScore.getHeight() + 10);
		add(currentScore);

		whosRound.setLocation((idealSize.width - whosRound.getWidth()) / 2,
				currentScore.getY() + whosRound.getHeight() + 10);
		add(whosRound);

		playerPortait.setLocation(50, 30);
		add(playerPortait);

		opponentPortait.setLocation(idealSize.width - 50
				- opponentPortait.getWidth(), 30);
		add(opponentPortait);

		playerHealthBar.setLocation(playerPortait.getX(), playerPortait.getY()
				+ playerPortait.getHeight());
		add(playerHealthBar);

		opponentHealthBar.setLocation(opponentPortait.getX(), opponentPortait
				.getY()
				+ opponentPortait.getHeight());
		add(opponentHealthBar);

		finishButton.setLocation(pressButton.getX()+(pressButton.getWidth()-finishButton.getWidth())/2,
				pressButton.getY()+(pressButton.getHeight()-finishButton.getHeight())/2);
		add(finishButton);
		
		background.setLocation(0, 0);
		add(background);

		
		
				
		//add listeners
		pressButton.addActionListener(this);
        finishButton.addActionListener(this);
		//start game

        setFrame();
        
		if (Math.random() < 0.5) {
			currentState = PLAYER_TURN;
			switchToPlayerTurn();
		} else {
			currentState = OPPONENT_TURN;
			switchToOpponentTurn();

		}
	}

	public void switchToPlayerTurn() {
		currentState = PLAYER_TURN;
		whosRound.setText("P's Round");
		randPanel.restart();
		pressButton.setEnabled(true);
		AItimer.stop();
		AIPressTimeCount = 0;

	}

	public void switchToOpponentTurn() {
		currentState = OPPONENT_TURN;
		whosRound.setText("O's Round");
		randPanel.restart();
		pressButton.setEnabled(false);

		AIPressTimeCount = 0;
		AIPressTime1 = (int) (Math.random() * 1000 / AI_TIMER_INTERVAL);
		AIPressTime2 = (int) ((1 + Math.random()) * 1000 / AI_TIMER_INTERVAL);
		AIPressTime3 = (int) ((2 + Math.random()) * 1000 / AI_TIMER_INTERVAL);

		AItimer.start();

	}

	public void switchToWinOrDie() {
		currentState = WIN_OR_DIE;
		pressButton.setEnabled(false);
		AItimer.stop();
		AIPressTimeCount = 0;

		if (playerHealth <= 0) {
			winner = OPPONENT_WIN;
			whosRound.setText("P LOSE");
			
			playerPortait.setIcon(playerScorePics[6]);
			opponentPortait.setIcon(opponentScorePics[7]);
			
			winByOpponent();
		} else {
			winner = PLAYER_WIN;
			whosRound.setText("P WIN");
			
			playerPortait.setIcon(playerScorePics[7]);
			opponentPortait.setIcon(opponentScorePics[6]);
			
			winByPlayer();
		}

		finishButton.setVisible(true);
		pressButton.setVisible(false);

	}

	public void setPlayerHealth(int health) {
		if (health >= 0) {
			playerHealth = health;
			playerHealthBar.setSize((int) (playerPortait.getWidth()
					* playerHealth * 1.0 / playerMaxHealth), playerHealthBar
					.getHeight());
			repaint();
		} else {
			playerHealth = 0;
			playerHealthBar.setSize((int) (playerPortait.getWidth()
					* playerHealth * 1.0 / playerMaxHealth), playerHealthBar
					.getHeight());
			repaint();

		}

	}

	public void setOpponentHealth(int health) {
		if (health >= 0) {
			opponentHealth = health;
			opponentHealthBar.setSize((int) (opponentPortait.getWidth()
					* opponentHealth * 1.0 / opponentMaxHealth),
					opponentHealthBar.getHeight());
			repaint();
		} else {
			opponentHealth = 0;
			opponentHealthBar.setSize((int) (opponentPortait.getWidth()
					* opponentHealth * 1.0 / opponentMaxHealth),
					opponentHealthBar.getHeight());
			repaint();
		}
	}

	public void receiveRandomNumberPanelEvent(RandomNumberPanelEvent e) 
	{
		if (PLAYER_TURN == currentState)
		{
			currentScore.setText("" + e.getValue());
			
			if(e.getValue()<100)
			{
				playerPortait.setIcon(playerScorePics[0]);
				opponentPortait.setIcon(opponentScorePics[1]);
			}
			if((e.getValue()>=100)&&(e.getValue()<500))
			{
				playerPortait.setIcon(playerScorePics[2]);
				opponentPortait.setIcon(opponentScorePics[3]);
			}
			if((e.getValue()>=500)&&(e.getValue()<800))
			{
				playerPortait.setIcon(playerScorePics[3]);
				opponentPortait.setIcon(opponentScorePics[2]);
			}
			if((e.getValue()>=800)&&(e.getValue()<900))
			{
				playerPortait.setIcon(playerScorePics[4]);
				opponentPortait.setIcon(opponentScorePics[5]);
			}
			if((e.getValue()>=900)&&(e.getValue()<950))
			{
				playerPortait.setIcon(playerScorePics[4]);
				opponentPortait.setIcon(opponentScorePics[5]);
			}
			if((e.getValue()>=950)&&(e.getValue()<1000))
			{
				playerPortait.setIcon(playerScorePics[7]);
				opponentPortait.setIcon(opponentScorePics[6]);
			}
			
			setOpponentHealth(opponentHealth - e.getValue());
			if (opponentHealth > 0) {
				switchToOpponentTurn();
			} 
			else
			{
				switchToWinOrDie();
			}
			
			
			

		} 
		else
		{
			if (OPPONENT_TURN == currentState) 
			{
				currentScore.setText("" + e.getValue());
				
				if(e.getValue()<100)
				{
					opponentPortait.setIcon(opponentScorePics[0]);
					playerPortait.setIcon(playerScorePics[1]);
				}
				if((e.getValue()>=100)&&(e.getValue()<500))
				{
					opponentPortait.setIcon(opponentScorePics[2]);
					playerPortait.setIcon(playerScorePics[3]);
				}
				if((e.getValue()>=500)&&(e.getValue()<800))
				{
					opponentPortait.setIcon(opponentScorePics[3]);
					playerPortait.setIcon(playerScorePics[2]);
				}
				if((e.getValue()>=800)&&(e.getValue()<900))
				{
					opponentPortait.setIcon(opponentScorePics[4]);
					playerPortait.setIcon(playerScorePics[5]);
				}
				if((e.getValue()>=900)&&(e.getValue()<950))
				{
					opponentPortait.setIcon(opponentScorePics[4]);
					playerPortait.setIcon(playerScorePics[5]);
				}
				if((e.getValue()>=950)&&(e.getValue()<1000))
				{
					opponentPortait.setIcon(opponentScorePics[7]);
					opponentPortait.setIcon(opponentScorePics[6]);
				}
				
				setPlayerHealth(playerHealth - e.getValue());
				if (playerHealth > 0) 
				{
					switchToPlayerTurn();
				} 
				else 
				{
					switchToWinOrDie();
				}

			}
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (localDebug) {
			System.out.println("====actionPerformed==== ");
			System.out.println("currentState == " + currentState);
			System.out.println("AIPressTimeCount == " + AIPressTimeCount);
		}

		if (PLAYER_TURN == currentState) {
			if (localDebug) {
				System.out.println("player\'s turn and pressButton");
			}

			if (e.getSource() == pressButton) {
				randPanel.buttonPressed();
			}
		}

		if (OPPONENT_TURN == currentState) {
			if (e.getSource() == AItimer) {
				AIPressTimeCount++;
				if ((AIPressTime1 == AIPressTimeCount
						|| AIPressTime2 == AIPressTimeCount || AIPressTime3 == AIPressTimeCount)
						|| (AIPressTimeCount > 5 * 1000 / AI_TIMER_INTERVAL)) {
					if (localDebug) {
						System.out.println("AIPressTime1 == " + AIPressTime1);
						System.out.println("AIPressTime2 == " + AIPressTime2);
						System.out.println("AIPressTime3 == " + AIPressTime3);
					}
					randPanel.buttonPressed();
				}
			}
		}
		
		if(e.getSource()==finishButton)
		{
			endOfGame();
		}

	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getAIPressTime1() {
		return AIPressTime1;
	}

	public void setAIPressTime1(int pressTime1) {
		AIPressTime1 = pressTime1;
	}

	public int getAIPressTime2() {
		return AIPressTime2;
	}

	public void setAIPressTime2(int pressTime2) {
		AIPressTime2 = pressTime2;
	}

	public int getAIPressTime3() {
		return AIPressTime3;
	}

	public void setAIPressTime3(int pressTime3) {
		AIPressTime3 = pressTime3;
	}

	public int getAIPressTimeCount() {
		return AIPressTimeCount;
	}

	public void setAIPressTimeCount(int pressTimeCount) {
		AIPressTimeCount = pressTimeCount;
	}

	public Timer getAItimer() {
		return AItimer;
	}

	public void setAItimer(Timer itimer) {
		AItimer = itimer;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public RandomNumberPanel getRandPanel() {
		return randPanel;
	}

	public void setRandPanel(RandomNumberPanel randPanel) {
		this.randPanel = randPanel;
	}

	public JButton getPressButton() {
		return pressButton;
	}

	public void setPressButton(JButton pressButton) {
		this.pressButton = pressButton;
	}

	public JLabel getPlayerPortait() {
		return playerPortait;
	}

	public void setPlayerPortait(JLabel playerPortait) {
		this.playerPortait = playerPortait;
	}

	public Icon getPlayerPortaitImage() {
		return playerPortaitImage;
	}

	public void setPlayerPortaitImage(Icon playerPortaitImage) {
		this.playerPortaitImage = playerPortaitImage;
	}

	public JLabel getOpponentPortait() {
		return opponentPortait;
	}

	public void setOpponentPortait(JLabel opponentPortait) {
		this.opponentPortait = opponentPortait;
	}

	public Icon getOpponentPortaitImage() {
		return opponentPortaitImage;
	}

	public void setOpponentPortaitImage(Icon opponentPortaitImage) {
		this.opponentPortaitImage = opponentPortaitImage;
	}

	public JLabel getPlayerHealthBar() {
		return playerHealthBar;
	}

	public void setPlayerHealthBar(JLabel playerHealthBar) {
		this.playerHealthBar = playerHealthBar;
	}

	public JLabel getOpponentHealthBar() {
		return opponentHealthBar;
	}

	public void setOpponentHealthBar(JLabel opponentHealthBar) {
		this.opponentHealthBar = opponentHealthBar;
	}

	public Icon getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Icon backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Dimension getIdealSize() {
		return idealSize;
	}

	public void setIdealSize(Dimension idealSize) {
		this.idealSize = idealSize;
	}

	public int getPLAYER_TURN() {
		return PLAYER_TURN;
	}

	public int getOPPONENT_TURN() {
		return OPPONENT_TURN;
	}

	public int getWIN_OR_DIE() {
		return WIN_OR_DIE;
	}

	public int getPLAYER_WIN() {
		return PLAYER_WIN;
	}

	public int getOPPONENT_WIN() {
		return OPPONENT_WIN;
	}

	public int getAI_TIMER_INTERVAL() {
		return AI_TIMER_INTERVAL;
	}

	public int getPlayerHealth() {
		return playerHealth;
	}

	public int getPlayerMaxHealth() {
		return playerMaxHealth;
	}

	public int getOpponentHealth() {
		return opponentHealth;
	}

	public int getOpponentMaxHealth() {
		return opponentMaxHealth;
	}

	public void winByPlayer() {
		if (null != getOpponent()) 
		{
			getPlayer().setMoney(getPlayer().getMoney()+50+(int)(Math.random()*40));
			getOpponent().setAlive(false);
		}

	}

	public void winByOpponent() {
		if (null != getPlayer()) {
			getPlayer().setAlive(false);
		}
	}
	
	public void setFrame() 
	{
		getFrame().pack();
				
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		getFrame().setLocation((screenSize.width-getFrame().getWidth())/2,
				(screenSize.height-getFrame().getHeight())/2-50);
		
		getFrame().setResizable(false);
		getFrame().setVisible(true);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		LongFan longFan = new LongFan(null, null, null);
		frame.getContentPane().add(longFan);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		System.out.println(longFan.getPressButton());
		System.out.println(longFan.getPlayerHealthBar());
		System.out.println(longFan.getOpponentHealthBar());
	}

}