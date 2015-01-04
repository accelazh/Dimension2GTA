package temp;

import java.io.*;

public class temp3 {
	public static void main(String[] args)
	{
		try
		{
			File file=new File("pic/SubGame/shootingPractice/GUI/0.gif");
			for(int i=5;i<=27;i++)
			{
				file=new File("pic/SubGame/shootingPractice/GUI/"+i+".gif");
				if(file.exists())
				{
					file.renameTo(new File("pic/SubGame/shootingPractice/GUI/test/"+(i-1)+".gif"));
					System.out.println(i+".gif ok");
				}
			}
		}
		catch(Exception ex)
		{
			;
		}
	}

}
