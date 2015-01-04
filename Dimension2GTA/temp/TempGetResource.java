package temp;

import java.net.*;

public class TempGetResource 
{
	public static void main(String[] args)
	{
		URL url=TempGetResource.class.getResource("test.txt");
	
		System.out.println("url: "+url);
	}

}
