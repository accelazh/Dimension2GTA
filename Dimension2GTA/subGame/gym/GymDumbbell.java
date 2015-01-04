package subGame.gym;

import gameDisplayProcessor.*;
import javax.swing.*;
import java.awt.*;
import basicConstruction.*;
import java.io.*;
import java.util.*;
import weapon.*;

/**
 * 增加拳头的力量
 * 消耗少量脂肪
 */
public class GymDumbbell extends GymSuper 
{
	//for debug
	private static final boolean debug=false;

	public GymDumbbell(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		setType(DUMBBELL);
	}

	@Override
	protected void addScore() 
	{
		//力量  10分钟增加20点
		//脂肪  10分钟消耗10点
		Player player=getPlayer();
		if(player!=null)
		{
			player.getFists().increaseAttackStrengthBy((int)(getScore()*1.0/(10*60)*20));
			player.setFat(player.getFat()-(int)(getScore()*1.0/(10*60)*10));
		}
	}

	@Override
	protected double getSkillDegree() 
	{
		if(getPlayer()!=null)
		{
			return getPlayer().getFists().getAttackStrength()*1.0/Fists.TOP_ATTACK_STRENGTH;
		}
		else
		{
			return 0;
		}
	}

	@Override
	public void initializeImageSequence() 
	{
		if(debug)
		{
			System.out.println("====in method initializeImageSequence====");
		}
		
		int num=0;
		File picFile=new File("pic/SubGame/gym/dumbbell/"+num+".gif");
		ArrayList<Image> imageArray=new ArrayList<Image>();

		while(picFile.exists())
		{
			if(debug)
			{
				System.out.println("picFile exists, path"+picFile.getPath());
			}
			imageArray.add(new ImageIcon(picFile.getPath()).getImage());
			num++;
	        picFile=new File("pic/SubGame/gym/dumbbell/"+num+".gif");
		}
		
		Image[] images=new Image[imageArray.size()];
		for(int i=0;i<imageArray.size();i++)
		{
			images[i]=imageArray.get(i);
		}
		
		setImageSequence(images);
		
		if(debug)
		{
			System.out.println("====end of method initializeImageSequence====\n\n");
		}
		
	}
	
	//test
	
	public static void main(String[] args)
	{
		GymDumbbell gymDumbbell=new GymDumbbell(null,null,null);
    }
	
	

}
