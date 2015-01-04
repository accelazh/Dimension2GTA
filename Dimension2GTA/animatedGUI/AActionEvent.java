package animatedGUI;

public class AActionEvent 
{
	private AComponent source=null;
	private long when=0;
	
	public AActionEvent(AComponent source, long when)
	{
	    this.source=source;
	    this.when=when;
	}

	public AActionEvent(AComponent source)
	{
	    this(source,System.currentTimeMillis());
    }
	
	public AComponent getSource() {
		return source;
	}

	public long getWhen() {
		return when;
	}
}
