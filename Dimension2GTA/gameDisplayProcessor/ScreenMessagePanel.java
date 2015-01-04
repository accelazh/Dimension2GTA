package gameDisplayProcessor;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.*;
import basicConstruction.*;

public class ScreenMessagePanel extends JPanel
{
	private Dimension screenSize;
	private MapContainer landMapContainer;
	private Player player;
 
	private HealthLabel healthLabel;
	private WeaponLabel weaponLabel;
	private FMCLabel FMCLabel;  //footMovingConditionLabel
	private MoneyLabel moneyLabel;
	private DebugInfoLabel debugInfoLabel;
	
	public ScreenMessagePanel(MapContainer landMapContainer,Player player)
	{
		setBorder(new LineBorder(Color.BLUE,2));
		
		screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		
		this.landMapContainer =landMapContainer;
		this.player=player;
		
		//�����Ϣ
	    setLayout(new FlowLayout(FlowLayout.LEFT,10,0));
	    healthLabel=new HealthLabel(player.getHealthPoint(),player.getVest());
	    weaponLabel=new WeaponLabel((player.getWeaponList())[player.getCurrentWeapon()],player);
		FMCLabel=new FMCLabel(player);
		moneyLabel=new MoneyLabel(player.getMoney());
		debugInfoLabel=new DebugInfoLabel(player,landMapContainer);
	    
	    add(healthLabel);
	    add(weaponLabel);
	    add(FMCLabel);
	    add(moneyLabel);
	    
        //������̽���
		setFocusable(false);
				
	}
	
	public void refresh()
	{
		healthLabel.refresh(player.getHealthPoint(),player.getVest());
		weaponLabel.refresh((player.getWeaponList())[player.getCurrentWeapon()]);
		FMCLabel.refresh();
		moneyLabel.refresh(player.getMoney());
		debugInfoLabel.refresh();
	}
	
	
	public Dimension getpreferredSize()
	{
		return new Dimension(screenSize.width,30);
		
	}
	
	//����������������ͼʱ�ĳ�ʼ��,������Ӧ���������µĵ�ͼ
	public void initWhenMapChanged(MapContainer landMapContainer)
	{
		 //ˢ���Լ��Ĳ���landMapContainer
		this.landMapContainer=landMapContainer;
		debugInfoLabel.setLandMapContainer(landMapContainer);
         
	}

}
