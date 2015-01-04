package basicConstruction;

import java.awt.*;
import javax.swing.*;

import utilities.MyPoint;

import gameDisplayProcessor.*;

public class MapContainer {
	//for debug
	private static boolean debug=ControlSetting.debug;
	private static boolean debug4=ControlSetting.debug4;
	// private date field
	private int mapID;   //这个非常重要，是地图的序号，
	                     //在程序中检索地图时发挥地图名字的作用,
	                     //默认主地图的ID是0;
	// private date field
	private Dimension totalSize;

	private Icon surfaceImage;

	private String containerName;

    //这里约定，数组中前面紧密排放solid，后面的空位置必须都是null,且solid[]不准为null
	private Solid[] solidList;// player也应该被装在这里

	private Player player;
	
	//这里火花等效果准备
	private Effect[] effectList;  //为特殊效果创建的实例都加入这里，这个数组自身不能为null，但其中的实例可以和null交替排列

	

// methods
	// constructors
	
	public MapContainer(Icon surfaceImage, Dimension totalSize,Player player) 
	{
		this.mapID=0;
		this.surfaceImage = surfaceImage;
		this.totalSize = totalSize;
		this.player=player;
		this.solidList=new Solid[10];
		for(int i=0;i<solidList.length;i++)
		{
			solidList[i]=null;
		}
		
		this.containerName ="Land Map Container";
		
		this.effectList=new Effect[10];
		for(int i=0;i<effectList.length;i++)
		{
			effectList[i]=null;
		}
	}
	public MapContainer()
	{
		this(new ImageIcon("pic/default"),new Dimension(1024*4,768*4),null);
	}

	// gets and sets
	public void setTotalSize(Dimension totalSize) {
		this.totalSize = totalSize;
	}

	public void setSufaceImage(Icon surfaceImage) {
		this.surfaceImage = surfaceImage;
	}

	public void setContainerName(String s) {
		this.containerName = s;
	}
    
	public void setPlayer(Player player)
	{
		this.player=player;
	}
	 
	public Dimension getTotalSize() {
		return totalSize;
	}

	public Icon setSufaceImage() {
		return surfaceImage;
	}

	public String getContainerName() {
		return containerName;
	}

	public Player getPlayer()
	{
		return player;
	}
	
	public String toString() 
	{
		String output;
		output="ContainerName : "+containerName+"\n";
		for(int i=0;i<solidList.length;i++)
		{
			if(solidList[i]!=null)
			{
			    output+="===== solid["+i+"] ===== \n";
			    output+=solidList[i].toString();
			    output+="\n";
			}
		}
		return output;
	}

	// other methods
	//注意这个判断相撞的算法实际上是错的。它没有考虑两矩形重叠但他们的定点都不在对方中的情况
	//但是就本程序的应用来说，即使不考虑这种情况，对应用还没什么影响
	public static boolean isACollidingWithB(Solid A, Solid B) // 这里的碰撞是指进入到solid的inaccessible区域
	{
		if(debug4)
		{
			System.out.println("====in method isACollidingWithB====");
			System.out.println("displaying solids' info : ");
			System.out.println("solid A : ");
			System.out.println(A);
			System.out.println("solid B :");
			System.out.println(B);
			System.out.println("=display finished=");
		}
		if ((null == A) || (null == B)) 
		{
			return false;
		} 
		else
		{

		
			
			Rectangle humanARectangle = A.getLocationRectangle();
			Rectangle humanBRectangle = B.getLocationRectangle();

			if (debug4) {
				System.out.println("in branch A and B are not both human : ");
				System.out.println("A's locationRectangle : ");
				System.out.println(humanARectangle);
				System.out.println("B's locationRectangle : ");
				System.out.println(humanBRectangle);
			}

			Point[] humanAFourPoints = new Point[4];
			humanAFourPoints[0] = new Point(humanARectangle.getLocation().x
					- humanBRectangle.getLocation().x, humanARectangle
					.getLocation().y
					- humanBRectangle.getLocation().y);
			humanAFourPoints[1] = new Point(humanARectangle.getLocation().x
					+ humanARectangle.width - humanBRectangle.getLocation().x,
					humanARectangle.getLocation().y
							- humanBRectangle.getLocation().y);
			humanAFourPoints[2] = new Point(humanARectangle.getLocation().x
					+ humanARectangle.width - humanBRectangle.getLocation().x,
					humanARectangle.getLocation().y + humanARectangle.height
							- humanBRectangle.getLocation().y);
			humanAFourPoints[3] = new Point(humanARectangle.getLocation().x
					- humanBRectangle.getLocation().x, humanARectangle
					.getLocation().y
					+ humanARectangle.height - humanBRectangle.getLocation().y);

			if (debug4) {
				System.out.println("AFourPoints : ");
				for (int i = 0; i < humanAFourPoints.length; i++) {
					System.out.println(humanAFourPoints[i]);
				}
			}

			boolean flagA = false;
			for (int i = 0; i < 4; i++) {
				if (B.isAccesible(humanAFourPoints[i]) == false) 
				{
					Point[] relatedPoints={
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x-1,humanAFourPoints[i].y+humanBRectangle.getLocation().y-1),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x-1,humanAFourPoints[i].y+humanBRectangle.getLocation().y),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x-1,humanAFourPoints[i].y+humanBRectangle.getLocation().y+1),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x,humanAFourPoints[i].y+humanBRectangle.getLocation().y-1),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x,humanAFourPoints[i].y+humanBRectangle.getLocation().y),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x,humanAFourPoints[i].y+humanBRectangle.getLocation().y+1),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x+1,humanAFourPoints[i].y+humanBRectangle.getLocation().y-1),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x+1,humanAFourPoints[i].y+humanBRectangle.getLocation().y),
							new Point(humanAFourPoints[i].x+humanBRectangle.getLocation().x+1,humanAFourPoints[i].y+humanBRectangle.getLocation().y+1),
					};
					
					for(int j=0;j<9;j++)
					{
						if(!A.isAccesibleFromMap(relatedPoints[j]))
						{
							flagA=true;
						}
					}
				}
			}
			if (debug4) {
				System.out.println("flagA == " + flagA);
			}

			if (debug4) {
				System.out.println("coming to the opposite part : ");
			}
			Point[] humanBFourPoints = new Point[4];
			humanBFourPoints[0] = new Point(humanBRectangle.getLocation().x
					- humanARectangle.getLocation().x, humanBRectangle
					.getLocation().y
					- humanARectangle.getLocation().y);
			humanBFourPoints[1] = new Point(humanBRectangle.getLocation().x
					+ humanBRectangle.width - humanARectangle.getLocation().x,
					humanBRectangle.getLocation().y
							- humanARectangle.getLocation().y);
			humanBFourPoints[2] = new Point(humanBRectangle.getLocation().x
					+ humanBRectangle.width - humanARectangle.getLocation().x,
					humanBRectangle.getLocation().y + humanBRectangle.height
							- humanARectangle.getLocation().y);
			humanBFourPoints[3] = new Point(humanBRectangle.getLocation().x
					- humanARectangle.getLocation().x, humanBRectangle
					.getLocation().y
					+ humanBRectangle.height - humanARectangle.getLocation().y);

			if (debug4) {
				System.out.println("BFourPoints : ");
				for (int i = 0; i < humanBFourPoints.length; i++) {
					System.out.println(humanBFourPoints[i]);
				}
			}

			boolean flagB = false;
			for (int i = 0; i < 4; i++) {
				if (A.isAccesible(humanBFourPoints[i]) == false)
				{
					Point[] relatedPoints={
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x-1,humanBFourPoints[i].y+humanARectangle.getLocation().y-1),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x-1,humanBFourPoints[i].y+humanARectangle.getLocation().y),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x-1,humanBFourPoints[i].y+humanARectangle.getLocation().y+1),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x,humanBFourPoints[i].y+humanARectangle.getLocation().y-1),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x,humanBFourPoints[i].y+humanARectangle.getLocation().y),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x,humanBFourPoints[i].y+humanARectangle.getLocation().y+1),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x+1,humanBFourPoints[i].y+humanARectangle.getLocation().y-1),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x+1,humanBFourPoints[i].y+humanARectangle.getLocation().y),
							new Point(humanBFourPoints[i].x+humanARectangle.getLocation().x+1,humanBFourPoints[i].y+humanARectangle.getLocation().y+1),
					};
					
					for(int j=0;j<9;j++)
					{
						if(!B.isAccesibleFromMap(relatedPoints[j]))
						{
							flagB=true;
						}
					}
				}
			}

			if (debug4) {
				System.out.println("flagB == " + flagB);
				System.out.println("=end of this method=");
			}

			return flagB || flagA;
			

		}

	}
		
	public static boolean isAOverlappedWithB(Solid A,Solid B)
	{
		
		// test if solidA of solidB is null
		if((null==A)||(null==B))
			return false;
		else 
		{

			boolean flagA = false;
			boolean flagB = false;

			// test if A has stepped in B
			Rectangle ARectangle = A.getLocationRectangle();
			Rectangle BRectangle = B.getLocationRectangle();
			if(debug)
			{
				
				System.out.println("====method isAOverLappedWithB====");
				System.out.println("A's name is : "+A.getSolidName());
				System.out.println("B's name is : "+B.getSolidName());
				System.out.println("in testing if A has stepped in B");
				System.out.println("ARectangle : \n"+ARectangle);
				System.out.println("BRectangle : \n"+BRectangle);
			}
			

			Point[] AFourPoints = new Point[4];
			AFourPoints[0] = new Point(ARectangle.getLocation().x, ARectangle
					.getLocation().y);
			AFourPoints[1] = new Point(ARectangle.getLocation().x
					+ ARectangle.width, ARectangle.getLocation().y);
			AFourPoints[2] = new Point(ARectangle.getLocation().x
					+ ARectangle.width, ARectangle.getLocation().y
					+ ARectangle.height);
			AFourPoints[3] = new Point(ARectangle.getLocation().x, ARectangle
					.getLocation().y
					+ ARectangle.height);

			if(debug)
			{
				System.out.println("AFourPoints : ");
				for(int i=0;i<AFourPoints.length ;i++)
				{					
					System.out.println(AFourPoints[i]);
				}
			}
			
			for (int i = 0; i < 4; i++) 
			{
				if (B.isInLocationRectangle(AFourPoints[i]) == true) 
				{
					if(debug)
					{
						System.out.println("flagA=true");
					}
					flagA = true;
				}
			}

			
			
			// test if B has stepped in A
			
			if(debug)
			{
				System.out.println("in testing if B has stepped in A");
				
			}
			
			Point[] BFourPoints = new Point[4];
			BFourPoints[0] = new Point(BRectangle.getLocation().x, BRectangle
					.getLocation().y);
			BFourPoints[1] = new Point(BRectangle.getLocation().x
					+ BRectangle.width, BRectangle.getLocation().y);
			BFourPoints[2] = new Point(BRectangle.getLocation().x
					+ BRectangle.width, BRectangle.getLocation().y
					+ BRectangle.height);
			BFourPoints[3] = new Point(BRectangle.getLocation().x, BRectangle
					.getLocation().y
					+ BRectangle.height);

			if(debug)
			{
				System.out.println("BFourPoints : ");
				for(int i=0;i<BFourPoints.length ;i++)
				{
					System.out.println(BFourPoints[i]);
				}
			}
			for (int i = 0; i < 4; i++) 
			{
				if (A.isInLocationRectangle(BFourPoints[i]) == true) 
				{
					if(debug)
					{
						System.out.println("flagB=true");
					}
					flagB = true;
				}
			}
			
			
			//这里考虑两矩形重叠，但顶点都不在对方中的情况
			boolean flag3=false;
			
			if((AFourPoints[0].x>=BFourPoints[0].x)&&
					(AFourPoints[1].x<=BFourPoints[1].x))
			{
				if((AFourPoints[0].y<=BFourPoints[0].y)&&
						(AFourPoints[3].y>=BFourPoints[3].y))
				{
					flag3=true;
				}
			}
			
			if((AFourPoints[0].x<=BFourPoints[0].x)&&
					(AFourPoints[1].x>=BFourPoints[1].x))
			{
				if((AFourPoints[0].y>=BFourPoints[0].y)&&
						(AFourPoints[3].y<=BFourPoints[3].y))
				{
					flag3=true;
				}
			}

			// put back the result

			return flagA || flagB||flag3;
		}
	}

    //这里约定，数组中前面紧密排放solid，后面的空位置必须都是null
	public static Solid[] addSolidToThis(Solid newSolid,Solid[] solidArray)
	{
		int i;        // to search for the end of the array
		for(i=0;(i<solidArray.length)&&(solidArray[i]!=null);i++)
			;
		if(i<solidArray.length)
		{
			solidArray[i]=newSolid;
			return solidArray;
		}
		else
		{
			Solid[] newSolidArray=new Solid[solidArray.length*2];
			for(int j=0;j<newSolidArray.length;j++)
			{
				newSolidArray[j]=null;
			}
			System.arraycopy(solidArray,0,newSolidArray,0,solidArray.length);
			
			newSolidArray[solidArray.length]=newSolid;
			return newSolidArray;
		}
	}
	
	public Solid[] SolidsInRectangle(Rectangle rectangle)//根据数据域的solid列表，返回与给定矩形有重叠部分的solid的列表
	{
		Solid[] solidArray=new Solid[10];
		for(int i=0;i<solidArray.length;i++)
		{
			solidArray[i]=null;
		}
		
		//把重叠的固体都加进来
		
		Solid solidAsRectangle=new BuildingSolid();
		solidAsRectangle.setLocation(rectangle.getLocation());
		solidAsRectangle.setTotalSize(rectangle.getSize());
		solidAsRectangle.setSolidName("solidAsRectangle");
		
		if (debug) 
		{
			System.out.println("===method solidsInRectangle===");
			
		}		
		for(int i=0;i<solidList.length;i++)
		{	
			if (debug) 
			{
				System.out.println("===in testing if solidList[" + i
						+ "] is overlapped with solidAsRectangle===");
				
			}			
			
			if(isAOverlappedWithB(solidList[i],solidAsRectangle)==true)
			{
				if(debug)
				{
					System.out.println("++++++overlapped!++++++");
				}
			 	solidArray=addSolidToThis(solidList[i],solidArray);
			} 
				
		}
		
		//prepare the return value
		return solidArray;
		
	}
	
	public static Solid[] SolidsInRectangle(Rectangle rectangle,Solid[] allSolids)//根据指定的solid列表，返回与给定矩形有重叠部分的solid的列表
	{
		Solid[] solidArray=new Solid[10];
		for(int i=0;i<solidArray.length;i++)
		{
			solidArray[i]=null;
		}
		
		//把重叠的固体都加进来
		
		Solid solidAsRectangle=new BuildingSolid();
		solidAsRectangle.setLocation(rectangle.getLocation());
		solidAsRectangle.setTotalSize(rectangle.getSize());
		solidAsRectangle.setSolidName("solidAsRectangle");
		
		if (debug) 
		{
			System.out.println("===method solidsInRectangle2===");
			
		}		
		for(int i=0;i<allSolids.length;i++)
		{	
			if (debug) 
			{
				System.out.println("===in testing if allSolids[" + i
						+ "] is overlapped with solidAsRectangle===");
				
			}			
			
			if(isAOverlappedWithB(allSolids[i],solidAsRectangle)==true)
			{
				if(debug)
				{
					System.out.println("++++++overlapped!++++++");
				}
			 	solidArray=addSolidToThis(allSolids[i],solidArray);
			} 
				
		}
		
		//prepare the return value
		return solidArray;
		
	}

    public void addSolid(Solid newSolid)
    {
        solidList=addSolidToThis(newSolid,solidList);	
    }

    public static String getSolidListInfo(Solid[] solidArray)
    {
    	String output="";
		for(int i=0;i<solidArray.length;i++)
		{
			if(solidArray[i]!=null)
			{
			    output+="===== solidArray["+i+"] ===== \n";
			    output+=solidArray[i].toString();
			    output+="\n";
			}
		}
		return output;
    	
    }

    public Effect[] addEffectToThis(Effect newEffect,Effect[] effectArray)
    {
    	int i;        // to search for the end of the array
	    for(i=0;(i<effectArray.length)&&(effectArray[i]!=null);i++)
		    ;
	    if(i<effectArray.length)
	    {
	    	effectArray[i]=newEffect;
	    	return effectArray;
	    }
	    else
	    {
	    	Effect[] newEffectArray=new Effect[effectArray.length*2];
	     	for(int j=0;j<newEffectArray.length;j++)
	    	{
		    	newEffectArray[j]=null;
		    }
	    	System.arraycopy(effectArray,0,newEffectArray,0,effectArray.length);
		
	    	newEffectArray[effectArray.length]=newEffect;
	    	return newEffectArray;
    	}
    	
    }

    public void addSpark1(MyPoint location)
    {
    	effectList=addEffectToThis(new Spark1(location),effectList);   	
    	 
    }
    
    public void addSpark1(Point location)
    {
    	effectList=addEffectToThis(new Spark1(new MyPoint(location.x,location.y)),effectList);   	
    	 
    }
    
    public void addSpark2(MyPoint location)
    {
    	Spark1 spark2=new Spark1(location);
    	spark2.setPicSequenceLocator("pic/effect/spark2/");
    	effectList=addEffectToThis(spark2,effectList);   	
    	 
    }
    
    public void addSpark2(Point location)
    {
    	Spark1 spark2=new Spark1(new MyPoint(location.x,location.y));
    	spark2.setPicSequenceLocator("pic/effect/spark2/");
    	effectList=addEffectToThis(spark2,effectList);   	
    	 
    }

    public void addGunLine(MyPoint startPoint,double arc)
    {
    	effectList=addEffectToThis(new GunLine(startPoint,arc),effectList);
    }
    
    public void clearUselessEffect()
    {
    	for(int i=0;i<effectList.length ;i++)
    	{
    		if(null!=effectList[i])
    		{
    		    if(Effect.OFF==effectList[i].getOnOrOff())
    		    {
    			    effectList[i]=null;
    		    
    		    }
    		}
    	}
    	
    }
    
    public Effect[] getEffectList()
    {
    	return effectList;
    }
	public Solid[] getSolidList() {
		return solidList;
	}
	public void setSolidList(Solid[] solidList) {
		this.solidList = solidList;
	}
	public void setEffectList(Effect[] effectList) {
		this.effectList = effectList;
	}
	
	public void removeSolid(int i)
	{
		solidList[i]=null;
	}
	
	public void removeSolid(Solid s)
	{
		for(int i=0;i<solidList.length;i++)
		{
			if(s==solidList[i])
			{
				solidList[i]=null;
			}
		}
	}
	public int getMapID() {
		return mapID;
	}
	public void setMapID(int mapID) {
		this.mapID = mapID;
	}
	
	//这个方法返回地图的
	public Rectangle getBounds()
	{
		Rectangle bounds=new Rectangle();
		bounds.setLocation(0,0);
		bounds.setSize(this.totalSize);
		
		return bounds;
	}
	
	


}
