package animatedGUI;

import java.awt.*;

public class AMouseEvent implements AConstants
{
	private AComponent source=null;
	private long when=0;
	private Point point=null;
	
	private int button=-1;
	
	public AMouseEvent(AComponent source, long when, Point where, int button)
	{
		this.source=source;
		this.point=where;
		this.when=when;
		this.button=button;
	}

	
	
	public AMouseEvent(AComponent source,Point where,int button)
	{
		this(source,System.currentTimeMillis(),where,button);
	}

	public AComponent getSource() {
		return source;
	}

	public long getWhen() {
		return when;
	}

	public Point getPoint() {
		return point;
	}

	public int getButton() {
		return button;
	}
	
	public int getX()
	{
		return point.x;
	}
	
	public int getY()
	{
		return point.y;
	}
	
	
}
