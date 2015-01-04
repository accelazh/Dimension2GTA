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
 * �����ܲ��ٶȡ�����������
 * ����֬��
 * 
 */
public class GymRunningMachine extends GymSuper
{
	//for debug
	private static final boolean debug=true;

	public GymRunningMachine(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		setType(RUNNING_MACHINE);
	}

	@Override
	protected void addScore() 
	{
		//springSpeedSkill  10��������15��
		//staminaSkill 10����28��
		//helthPointSkill 10����15��
		//fat���� 10����50��
		Player player=getPlayer();
		if(player!=null)
		{
			player.setSpringSpeedSkill(player.getSpringSpeedSkill()+(int)(getScore()*1.0/(10*60)*15));
			player.setStaminaSkill(player.getStaminaSkill()+(int)(getScore()*1.0/(10*60)*28));
			player.setHealthPointSkill(player.getHealthPointSkill()+(int)(getScore()*1.0/(10*60)*15));
			player.setFat(player.getFat()-(int)(getScore()*1.0/(10*60)*50));
		}
	}

	@Override
	protected double getSkillDegree() 
	{
		if(getPlayer()!=null)
		{
			Player player=getPlayer();
			return (player.getSpringSpeedSkill()+player.getStaminaSkill()+player.getHealthPointSkill())/(Human.topSpringSpeedSkill+Human.topStaminaSkill+Human.topHealthPointSkill);
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
	        picFile=new File("pic/SubGame/gym/runningMachine/"+num+".gif");
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
