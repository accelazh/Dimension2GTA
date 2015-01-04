package subGame.longFan;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 *this is use as a panel to 
 *generate random numbers
 *
 */

public class RandomNumberPanel extends JPanel implements ActionListener
{
	private Timer timer1;
	private final int INTERVAL1=100;
	
	private Timer timer2;
	private final int INTERVAL2=50;
	
	private Timer timer3;
	private final int INTERVAL3=10;
	
	private Timer timer4;
	private final int INTERVAL4=500;
	private int timer4Count;
	private final int READY_TO_STOP_SPAN=1*1000/INTERVAL4;
	
	
	private int currentState;
	final int STOPPED=0;
	final int RUNNING1=1;
	final int RUNNING2=2;
	final int RUNNING3=3; 
	final int READY_TO_STOP=4;
	
	private RandomNumberPanelListener owner;
	
	private JLabel cell1=new JLabel();
	private JLabel cell2=new JLabel();
	private JLabel cell3=new JLabel();
	private int cellNum1;
	private int cellNum2;
	private int cellNum3;
	
	Font font=new Font("Times",Font.BOLD,24);
	Border border=new LineBorder(Color.BLUE,1);
	
	//methods
	public RandomNumberPanel()
	{
		this(null);
		
	}
	public RandomNumberPanel(RandomNumberPanelListener l)
	{
		this.owner=l;
		timer1=new Timer(INTERVAL1,this);
		timer2=new Timer(INTERVAL2,this);
		timer3=new Timer(INTERVAL3,this);
		timer4=new Timer(INTERVAL4,this);
		
		currentState=STOPPED;
		cellNum1=0;
		cellNum2=0;
		cellNum3=0;
		
		//GUI designs
		cell1.setFont(font);
		cell2.setFont(font);
		cell3.setFont(font);
		
		cell1.setText(""+cellNum1);
		cell2.setText(""+cellNum2);
		cell3.setText(""+cellNum3);
		
		cell1.setBorder(border);
		cell2.setBorder(border);
		cell3.setBorder(border);
		
		setLayout(new FlowLayout());
		setBorder(border);
		
		add(cell3);
		add(cell2);
		add(cell1);
	}
	
	public void setOwner(RandomNumberPanelListener l)
	{
		this.owner=l;
	}
	
	public void setCell1(int n)
	{
		cellNum1=n;
		cell1.setText(""+n);
	}
	
	public void increaseCell1()
	{
		if(cellNum1<9)
		{
			setCell1(cellNum1+1);
		}
		else
		{
			if(cellNum1>=9)
			{
				setCell1(0);
			}
		}
	}
	
	public void increaseCell2()
	{
		if(cellNum2<9)
		{
			setCell2(cellNum2+1);
		}
		else
		{
			if(cellNum2>=9)
			{
				setCell2(0);
			}
		}
	}
	
	public void increaseCell3()
	{
		if(cellNum3<9)
		{
			setCell3(cellNum3+1);
		}
		else
		{
			if(cellNum3>=9)
			{
				setCell3(0);
			}
		}
	}
	
	public void setCell2(int n)
	{
		cellNum2=n;
		cell2.setText(""+n);
	}

	public void setCell3(int n)
	{
		cellNum3=n;
		cell3.setText(""+n);
	}

	
	public void restart()
	{
		setCell1(0);
		setCell2(0);
		setCell3(0);
		
		currentState=RUNNING1;
		
		timer1.start();
		timer2.start();
		timer3.start();
	}
	
	public void buttonPressed()
	{
		if (RUNNING1 == currentState) 
		{
			timer1.stop();
			currentState = RUNNING2;
		} 
		else 
		{
			if (RUNNING2 == currentState) 
			{
				timer2.stop();
				currentState = RUNNING3;
			} 
			else 
			{
				if (RUNNING3 == currentState) 
				{
					timer3.stop();
					timer4.start();
					timer4Count=0;
					currentState = READY_TO_STOP;

					
				}
			}
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==timer1)
		{
			increaseCell1();
		}
		if(e.getSource()==timer2)
		{
			increaseCell2();
		}
		if(e.getSource()==timer3)
		{
			increaseCell3();
		}
		if(e.getSource()==timer4)
		{
			timer4Count++;
			if(READY_TO_STOP_SPAN==timer4Count)
			{
				timer4Count=0;
				currentState=STOPPED;
				timer4.stop();
				
				RandomNumberPanelEvent event = new RandomNumberPanelEvent();
				event.setValue(100 * cellNum3 + 10 * cellNum2 + cellNum1);
				event.setSource(this);

				owner.receiveRandomNumberPanelEvent(event);
			}
		}
	}
}
