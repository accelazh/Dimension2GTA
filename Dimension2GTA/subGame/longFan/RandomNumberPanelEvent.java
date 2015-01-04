package subGame.longFan;


public class RandomNumberPanelEvent 
{
	private int value;
	private RandomNumberPanel source;
	
	public void setValue(int v)
	{
		this.value=v;
	}
	public int getValue()
	{
		return value;
	}
	
	public void setSource(RandomNumberPanel s)
	{
		this.source=s;
	}
	public RandomNumberPanel getSource()
	{
		return source;
	}
}

