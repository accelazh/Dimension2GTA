package subGame.shootingPractice;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Background 
{
	//for debug
	private final static boolean debug=false;
	
	
	private final static Dimension wallPanelSize=ShootingPractice6.wallPanelTotalSize;
	
	private int offset=0;
	
	private int[] backgroundLocator=null;
	
	private int n=0;
	private int picOffset=offset%wallPanelSize.height;
		
	//这两个作缓冲存储使用，总是装着前后5张图
	private ImageIcon[] backgroundBufferUp=new ImageIcon[5];
	private ImageIcon[] backgroundBufferDown=new ImageIcon[5];
	
	private int picPointer1=0;  //用于记录backgroundBufferUp的(末+1)位置指向的地方
	private int picPointer2=0;
	
	public Background()
	{
		int numOfBk=0;
		File pic=new File("pic/SubGame/shootingPractice/bks/0.jpg");
		while(pic.exists())
		{
			numOfBk++;
			pic=new File("pic/SubGame/shootingPractice/bks/"+numOfBk+".jpg");
		}
		
		backgroundLocator=new int[numOfBk];   //得出一共有多少图片
		for(int i=0;i<backgroundLocator.length;i++)
		{
			backgroundLocator[i]=i;
		}
				
		//随机交换图片
		if(false){
		for(int i=0;i<backgroundLocator.length/2;i++)
		{
			int n1=1+(int)(Math.random()*backgroundLocator.length-1);
			int n2=1+(int)(Math.random()*backgroundLocator.length-1);
			
			int temp=backgroundLocator[n1];
			backgroundLocator[n1]=backgroundLocator[n2];
			backgroundLocator[n2]=temp;
		}
		}
		picPointer1=n+1;
		picPointer2=n;
				
		//装填缓冲区
		loadBackgroundBufferUp();
		loadBackgroundBufferDown();
		
		if(debug)
		{
			System.out.println(backgroundLocator.length);
			System.out.println("after constructor: ");
		    for(int i=0;i<backgroundBufferUp.length;i++)
		    {
		        System.out.println("is backgroundBufferUp["+i+"] null? "+backgroundBufferUp[i]);
		    }
		    for(int i=0;i<backgroundBufferDown.length;i++)
		    {
		        System.out.println("is backgroundBufferDown["+i+"] null? "+backgroundBufferDown[i]);
		    }
		    System.out.println("picPointer1 == "+picPointer1);
		    System.out.println("picPointer2 == "+picPointer2);
		   
		    System.out.println();
		}
		
				
		
	}
	
	public void loadBackgroundBufferUp()
	{
		for(int i=0;i<backgroundBufferUp.length;i++)
		{
			backgroundBufferUp[i]=new ImageIcon("pic/SubGame/shootingPractice/bks/"+picPointer1+".jpg");
			movePicPointer1(true);
		}
		
	}
	
	public void loadBackgroundBufferDown()
	{
		for(int i=0;i<backgroundBufferDown.length;i++)
		{
			backgroundBufferDown[i]=new ImageIcon("pic/SubGame/shootingPractice/bks/"+picPointer2+".jpg");
			movePicPointer2(false);
		}
		
	}
	
	public void movePicPointer1(boolean forth)
	{
		if(forth)
		{
			picPointer1+=1;
			picPointer1%=backgroundLocator.length;
		}
		else
		{
			picPointer1-=1;
			picPointer1+=backgroundLocator.length;
			picPointer1%=backgroundLocator.length;
		}
		
		if(debug)
		{
			System.out.println("picPointer1 == "+picPointer1);
		}
	}
	public void movePicPointer2(boolean forth)
	{
		if(forth)
		{
			picPointer2+=1;
			picPointer2%=backgroundLocator.length;
		}
		else
		{
			picPointer2-=1;
			picPointer2+=backgroundLocator.length;
			picPointer2%=backgroundLocator.length;
		}
		
		if(debug)
		{
			System.out.println("picPointer2 == "+picPointer2);
		}
	}
	
	public void selfProcess()
	{
		
	}
	
	public void setOffset(int offset)
	{
		boolean isUp=offset>this.offset;
		this.offset=offset;
		if(this.offset<0)
		{
			this.offset=0;
		}
		
		picOffset=offset%wallPanelSize.height;
		int newN=(offset/wallPanelSize.height)%backgroundLocator.length;
		
		if(n!=newN)
		{
			shiftBuffer(isUp);
			n=newN;
			
			if(debug)
			{
				System.out.println("after shiftBuffer ");
			    for(int i=0;i<backgroundBufferUp.length;i++)
			    {
			        System.out.println("is backgroundBufferUp["+i+"] null? "+backgroundBufferUp[i]);
			    }
			    for(int i=0;i<backgroundBufferDown.length;i++)
			    {
			        System.out.println("is backgroundBufferDown["+i+"] null? "+backgroundBufferDown[i]);
			    }
			    System.out.println("picPointer1 == "+picPointer1);
			    System.out.println("picPointer2 == "+picPointer2);
			    System.out.println();
			}
		}
	}
	
	public void shiftBuffer(boolean forth)
	{
		if(forth)
		{
			//处理backgroundBufferDown
			for(int i=backgroundBufferDown.length-1;i>=1;i--)
			{
				backgroundBufferDown[i]=backgroundBufferDown[i-1];
			}	
			backgroundBufferDown[0]=backgroundBufferUp[0];
			movePicPointer2(true);
			
			//处理backgroundBufferUp
			for(int i=1;i<backgroundBufferUp.length;i++)
			{
				backgroundBufferUp[i-1]=backgroundBufferUp[i];
			}	
		
			backgroundBufferUp[backgroundBufferUp.length-1]=new ImageIcon("pic/SubGame/shootingPractice/bks/"+picPointer1+".jpg");
			movePicPointer1(true);
		
		}
		else
		{
			//处理backgroundBufferUp
			for(int i=backgroundBufferUp.length-1;i>=1;i--)
			{
				backgroundBufferUp[i]=backgroundBufferUp[i-1];
			}
				
			backgroundBufferUp[0]=backgroundBufferDown[0];
			movePicPointer1(false);
			
			//处理backgroundBufferDown
			for(int i=0;i<=backgroundBufferDown.length-2;i++)
			{
				backgroundBufferDown[i]=backgroundBufferDown[i+1];
			}	
			backgroundBufferDown[backgroundBufferDown.length-1]=new ImageIcon("pic/SubGame/shootingPractice/bks/"+picPointer2+".jpg");
			movePicPointer2(false);
					
		}
	}
	
	public void paint(Graphics g, ImageObserver observer)
	{
		g.drawImage(backgroundBufferUp[0].getImage(),0,picOffset-wallPanelSize.height,wallPanelSize.width,wallPanelSize.height,observer);
		g.drawImage(backgroundBufferDown[0].getImage(),0,picOffset,wallPanelSize.width,wallPanelSize.height,observer);
	}

	public int getOffset() {
		return offset;
	}

	/*
	 * int n=(offset/wallPanelSize.height)%backgroundLocator.length;
		int picOffset=offset%wallPanelSize.height;
		
		int pointerToUpPic=(n+1)%backgroundLocator.length;
		int pointerToDownPic=n;
		
		Image backgroundUp=Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/bks/"+pointerToUpPic+".jpg");
		Image backgroundDown=Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/bks/"+pointerToDownPic+".jpg");
	 */
}


