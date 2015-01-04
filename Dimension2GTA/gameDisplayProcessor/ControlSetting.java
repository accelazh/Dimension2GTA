package gameDisplayProcessor;

import java.awt.event.*;

public class ControlSetting 
{
	//for debug
	static public boolean debug=false;
	static public boolean debug2=false;
	static public boolean debug3=false;   //disable godTimer
	static public boolean debug4=false;
	static public boolean debug0=false;    //用来控制debug counter
	static public boolean debug5=false;
	static public boolean debug6=false;
	static public boolean debug7=false;
	static public boolean debug8=false;	
	static public boolean debug9=false;
	static public boolean debug10=false;
	
	static public boolean debug11=false;
	static public boolean debug12=false;
	static public boolean debug13=false; 
	static public boolean debug14=false; 
	static public boolean debug15=false; 
	
	static public boolean debug16=false;
	static public boolean debug17=false;
	
	static public boolean debug18=false;
	static public boolean debug19=false;
	static public boolean debug20=true;   //控制窗口
	
	static public boolean debug21=false;
	static public boolean debug22=false;
	static public boolean debug23=false;
	static public boolean debug24=true;
	
	final public static int godTimerInterval=10;  //上帝timer的间隔
	
    private static int playerMoveUp='W';
    private static int playerMoveDown='S';
    private static int playerMoveLeft='A';
    private static int playerMoveRight='D';
    private static int footMovingConditionSwitch=KeyEvent.VK_SHIFT;  //用来切换WALK,RUN,SPRING的键
    private static int reloadGun='R';         //给枪上子弹
    private static int using='E';
    private static int switchWeapon='Q';    
    
    private static int specialUp='I';
    private static int specialDown='K';
    private static int specialLeft='J';
    private static int specialRight='L';
    
    //用来在gym里操纵健身器材的键
    private static int gymUp='=';
    private static int gymDown='-';
    
    public ControlSetting()
    {
    	
        
        
        if(debug2)
        {
        	System.out.println("==== in ControlSetting ====");
        	System.out.println("playerMoveUp == "+playerMoveUp);
        	System.out.println("playerMoveDown == "+playerMoveDown);
        	System.out.println("playerMoveLeft == "+playerMoveLeft);
        	System.out.println("playerMoveRight == "+playerMoveRight);
            System.out.println("footMovingConditionSwith == "+ 
            		footMovingConditionSwitch);
        }

    }

	public static int getPlayerMoveUp() {
		return playerMoveUp;
	}

	public static void setPlayerMoveUp(int playerMoveUp) {
		ControlSetting.playerMoveUp = playerMoveUp;
	}

	public static int getPlayerMoveDown() {
		return playerMoveDown;
	}

	public static void setPlayerMoveDown(int playerMoveDown) {
		ControlSetting.playerMoveDown = playerMoveDown;
	}

	public static int getPlayerMoveLeft() {
		return playerMoveLeft;
	}

	public static void setPlayerMoveLeft(int playerMoveLeft) {
		ControlSetting.playerMoveLeft = playerMoveLeft;
	}

	public static int getPlayerMoveRight() {
		return playerMoveRight;
	}

	public static void setPlayerMoveRight(int playerMoveRight) {
		ControlSetting.playerMoveRight = playerMoveRight;
	}

	public static int getFootMovingConditionSwitch() {
		return footMovingConditionSwitch;
	}

	public static void setFootMovingConditionSwitch(int footMovingConditionSwitch) {
		ControlSetting.footMovingConditionSwitch = footMovingConditionSwitch;
	}

	public static int getReloadGun() {
		return reloadGun;
	}

	public static void setReloadGun(int reloadGun) {
		ControlSetting.reloadGun = reloadGun;
	}

	public static int getSpecialUp() {
		return specialUp;
	}

	public static void setSpecialUp(int specialUp) {
		ControlSetting.specialUp = specialUp;
	}

	public static int getSpecialDown() {
		return specialDown;
	}

	public static void setSpecialDown(int specialDown) {
		ControlSetting.specialDown = specialDown;
	}

	public static int getSpecialLeft() {
		return specialLeft;
	}

	public static void setSpecialLeft(int specialLeft) {
		ControlSetting.specialLeft = specialLeft;
	}

	public static int getSpecialRight() {
		return specialRight;
	}

	public static void setSpecialRight(int specialRight) {
		ControlSetting.specialRight = specialRight;
	}

	public static int getUsing() {
		return using;
	}

	public static void setUsing(int using) {
		ControlSetting.using = using;
	}

	public static int getSwitchWeapon() {
		return switchWeapon;
	}

	public static void setSwitchWeapon(int switchWeapon) {
		ControlSetting.switchWeapon = switchWeapon;
	}

	public static int getGymUp() {
		return gymUp;
	}

	public static void setGymUp(int gymUp) {
		ControlSetting.gymUp = gymUp;
	}

	public static int getGymDown() {
		return gymDown;
	}

	public static void setGymDown(int gymDown) {
		ControlSetting.gymDown = gymDown;
	}
    
    
    
    
}
