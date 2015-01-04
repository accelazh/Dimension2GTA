package animatedGUI;

import java.awt.*;
import javax.swing.*;

//这个类用来存储组件的动画片段
public class AAnimationClip implements AConstants
{
	private int[][] coordinate=null;
	private int pointer=0;
	
	private boolean active=false;
	//配合构造方法以创建预设的动画
	public static final int SIN_MOTION=0;
    public static final int UNIFORM_MOTION=1;
           
    //constructors
    /**
     * @param coordinate[i].length must be 2 to contain X and Y coordinates 
     */
    public AAnimationClip(int[][] coordinate)
    {
    	if(null==coordinate||0==coordinate.length)
    	{
    		return;
    	}
    	
    	for(int i=0;i<coordinate.length;i++)
    	{
    		if(coordinate[i].length!=2)
    		{
    			return;
    		}
    	}
    	
    	this.coordinate=coordinate;
    	
    }
    
    /**
     * 
     * @param startLocation表示用coordinate表示的一系列组件的位置是以谁为起点的，计算实际位置的时候是以startLocation叠加offset
     * @param maxOffset
     * @param numOfFrames表示动画的长度，直接相关动画播放的速度
     * @param type表示预定义动画的类型，应填入这个类提供的表示预设动画的常量之一
     * @param direction表示预定义动画的方向
     */
    public AAnimationClip(Point startLocation, int maxOffset, int numOfFrames, int type, int direction)
    {
    	
    	if(0==numOfFrames)
    	{
    		return;
    	}
    	
    	int[][] newCoordinate=new int[numOfFrames][2];
    	int offset=0;
    	
    	switch(type)
    	{
    	case SIN_MOTION:
    	{
    		for(int i=0;i<=numOfFrames-1;i++)
    		{
    			offset=(int)(maxOffset*1.0*Math.sin(i*1.0/(numOfFrames-1)*Math.PI/2));
    			switch(direction)
    			{
    			case UP:
    			{
    				newCoordinate[i][0]=startLocation.x;
    				newCoordinate[i][1]=startLocation.y-offset;
    				break;
    			}
    			case DOWN:
    			{
    				newCoordinate[i][0]=startLocation.x;
    				newCoordinate[i][1]=startLocation.y+offset;
    				break;
    			}
    			case LEFT:
    			{
    				newCoordinate[i][0]=startLocation.x-offset;
    				newCoordinate[i][1]=startLocation.y;
    				break;
    			}
    			case RIGHT:
    			{
    				newCoordinate[i][0]=startLocation.x+offset;
    				newCoordinate[i][1]=startLocation.y;
    				break;
    			}
    			default:
    			{
    				break;
    			}
    			}
    		}
    		break;
    	}
    	case UNIFORM_MOTION:
    	{
    		for(int i=0;i<=numOfFrames-1;i++)
    		{
    			offset=(int)(maxOffset*1.0*i/(numOfFrames-1));
    			switch(direction)
    			{
    			case UP:
    			{
    				newCoordinate[i][0]=startLocation.x;
    				newCoordinate[i][1]=startLocation.y-offset;
    				break;
    			}
    			case DOWN:
    			{
    				newCoordinate[i][0]=startLocation.x;
    				newCoordinate[i][1]=startLocation.y+offset;
    				break;
    			}
    			case LEFT:
    			{
    				newCoordinate[i][0]=startLocation.x-offset;
    				newCoordinate[i][1]=startLocation.y;
    				break;
    			}
    			case RIGHT:
    			{
    				newCoordinate[i][0]=startLocation.x+offset;
    				newCoordinate[i][1]=startLocation.y;
    				break;
    			}
    			default:
    			{
    				break;
    			}
    			}
    		}
    		break;
    	}
    	default:
    	{
    		break;
    	}
    	}
    	
    	this.coordinate=newCoordinate;
    	restart();
    }
    
    
	//getters and setters
    public static int getFrameInterval()
    {
    	return TIMER_INTERVAL;
    }
    
    public Point getCurrentLocation()
    {
    	return new Point(coordinate[pointer][0],coordinate[pointer][1]);
    }
    
    
    public int[][] getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(int[][] coordinate) {
		this.coordinate = coordinate;
	}

	protected boolean isActive() {
		return active;
	}

	//other methods
    public void restart()
    {
    	if(null==this.coordinate)
    	{
    		coordinate=new int[1][2];
    		coordinate[0][0]=0;
    		coordinate[0][1]=0;
    	}
    	else
    	{
    		if(0==coordinate.length)
    		{
    			coordinate=new int[1][2];
        		coordinate[0][0]=0;
        		coordinate[0][1]=0;

    		}
    		else
    		{
    			boolean isStandard=true;
    			for(int i=0;i<coordinate.length;i++)
    			{
    				if(coordinate[i].length!=2)
    				{
    					isStandard=false;
    				}
    			}
    			
    			if(!isStandard)
    			{
    				coordinate=new int[1][2];
    	    		coordinate[0][0]=0;
    	    		coordinate[0][1]=0;
    			}
    		}
    	}
    	this.pointer=0;
    	this.active=true;
    }

    protected void selfProcess()
    {
    	if(!active)
    	{
    		return;
    	}
    	
    	if(pointer<coordinate.length-1)
    	{
    		pointer++;
    	}
    	else
    	{
    		pointer=0;
    		active=false;
    	}
    }
    //this method created a new instance with the reversed coordinate
    public AAnimationClip createReversedClip()
    {
    	if(null==coordinate)
    	{
    		return null;
    	}
    	
    	int[][] newCoordinate=coordinate.clone();
    	
    	for(int i=0;i<=newCoordinate.length/2-1;i++)
    	{
    		int[] temp=new int[2];
    		temp=newCoordinate[i];
    		newCoordinate[i]=newCoordinate[newCoordinate.length-1-i];
    		newCoordinate[newCoordinate.length-1-i]=temp;
    	}
    	
    	return new AAnimationClip(newCoordinate);
    }
    
    //this method cut the clip from given index and discard the following part
    //coordinate[index] is not to be discarded
    public void cutOutFollowingPart(int index)
    {
    	if(index<coordinate.length-1)
    	{
    		int[][] newCoordinate=new int[index+1][2];
    		for(int i=0;i<newCoordinate.length;i++)
    		{
    			newCoordinate[i]=coordinate[i];
    		}
    		
    		coordinate=newCoordinate;
    	}
    }
    //this method cut the clip from given index and discard the leading part
    //coordinate[index] is not to be discarded
    public void cutOutLeadingPart(int index)
    {
    	if(index<=coordinate.length-1)
    	{
    		int[][] newCoordinate=new int[coordinate.length-index][2];
    		for(int i=0;i<newCoordinate.length;i++)
    		{
    			newCoordinate[i]=coordinate[index+i];
    		}
    		
    		coordinate=newCoordinate;
    	}
    }
    //this method connect the argument instance to this as its following part
    public void catenation(int[][] newCoordinate)
    {
    	if(null==newCoordinate)
    	{
    		return;
    	}
    	else
    	{
    		if(0==newCoordinate.length)
    		{
    			return;

    		}
    		else
    		{
    			boolean isStandard=true;
    			for(int i=0;i<newCoordinate.length;i++)
    			{
    				if(newCoordinate[i].length!=2)
    				{
    					isStandard=false;
    				}
    			}
    			
    			if(!isStandard)
    			{
    				return;
    			}
    		}
    	}
    	
    	int[][] c=new int[coordinate.length+newCoordinate.length][2];
    	for(int i=0;i<coordinate.length+newCoordinate.length;i++)
    	{
    		c[i]=(i>=coordinate.length)?newCoordinate[i-coordinate.length]:coordinate[i];
    	}
    	
    	coordinate=c;
    }
    public void catenation(AAnimationClip clip)
    {
    	if(null==clip)
    	{
    		return;
    	}
    	
    	catenation(clip.getCoordinate());
    }
    
    public AAnimationClip reverse()
    {
    	if(null==coordinate)
    	{
    		return null;
    	}
    	else
    	{
    		int[][] newCoordinate=new int[coordinate.length][2];
    	    for(int i=0;i<coordinate.length;i++)
    	    {
    	    	newCoordinate[newCoordinate.length-1-i]=coordinate[i];
    	    }
    	    return new AAnimationClip(newCoordinate);
    	}
    }
    
    
}
