package subGame.gym;

import gameDisplayProcessor.MainGameWindow;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import weapon.Fists;
import basicConstruction.Human;
import basicConstruction.Player;

/**
 * ����Fists�Ĺ�������
 */
public class GymSandBag extends GymSuper
{
	//for debug
	private static final boolean debug=true;

	public GymSandBag(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		setType(DUMBBELL);
	}

	@Override
	protected void addScore() 
	{
		//��ȭ����  1����2.2ȭ
		//֬��  10��������10��
		Player player=getPlayer();
		if(player!=null)
		{
			player.getFists().increaseAttackTimesBy((int)(getScore()*2.2));
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
	        picFile=new File("pic/SubGame/gym/sandbag/"+num+".gif");
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

}
