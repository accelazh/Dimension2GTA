package temp;

import animatedGUI.AAnimationClip;

public class temp2 
{
	public static void main(String[] args)
	{
		int[][] coordinate=new int[11][2];
		
		for(int i=0;i<coordinate.length;i++)
		{
			coordinate[i][0]=(int)(Math.random()*50);
			coordinate[i][1]=(int)(Math.random()*50);
		}
		
		System.out.println("display coordinate: ");
		printArray(coordinate);
		
		int[][] newCoordinate=coordinate.clone();
		
		for(int i=0;i<=newCoordinate.length/2-1;i++)
    	{
    		int[] temp=new int[2];
    		temp=newCoordinate[i];
    		newCoordinate[i]=newCoordinate[newCoordinate.length-1-i];
    		newCoordinate[newCoordinate.length-1-i]=temp;
    	}
		
		System.out.println("display coordinate: ");
		printArray(coordinate);
		
		System.out.println("display newCoordinate: ");
		printArray(newCoordinate);
    	    	
		int[][] c=new int[coordinate.length+newCoordinate.length][2];
    	for(int i=0;i<coordinate.length+newCoordinate.length;i++)
    	{
    		c[i]=(i>=coordinate.length)?newCoordinate[i-coordinate.length]:coordinate[i];
    	}
    	
    	System.out.println("display c: ");
		printArray(c);
	}
	
	public static void printArray(int[][] array)
	{
		for(int i=0;i<array.length;i++)
		{
			for(int j=0;j<array[i].length;j++)
			{
				System.out.print(array[i][j]+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		System.out.println();
	}
	

}
