package subGame.gym;

import java.awt.*;

/**
 * 
 * 用来记录公用的常量
 */
public interface GymConstants 
{
	public static int TIMER_INTERVAL=10;
	
	public static Dimension PANEL_SIZE=new Dimension(800,604); 
	/**
	 * 游戏状态running
	 */
	public static int RUNNING=0;
	/**
	 * 游戏状态paused
	 */
	public static int PAUSED=1;
	/**
	 * 锻炼力度指示的最大值，就是屏幕左上角那个小条
	 */
	public static int MAX_DEGREE=100; 
	/**
	 * 指示小条的大小
	 */
	public static Dimension DEGREE_BAR_SIZE=new Dimension(100,10);
	/**
	 * degreeBar的位置
	 */
	public static Point DEGREE_BAR_LOCATION=new Point(PANEL_SIZE.width-DEGREE_BAR_SIZE.width-30,50);
	/**
	 * 指示小条的前景色
	 */
	public static Color DEGREE_BAR_FOREGROUND=Color.green;
	/**
	 * 指示小条的背景色
	 */
	public static Color DEGREE_BAR_BACKGROUND=Color.LIGHT_GRAY;
	/**
	 * degree后退的速度，以每次被timer调用来算,还没有叠加玩家的skill以及锻炼难度等级
	 */
    public static double DEGREE_BACKLASH_BASE_SPEED=20*1.0/1000*TIMER_INTERVAL;	
    /**
     * 最大level等级，level等级直接关系到锻炼的backlash和计分
     */
    public static int MAX_LEVEL=10;
    /**
     * 这个参数用来拉大每一等级之间的差距
     */
    public static int LEVEL_DISTANCE=10;
    /**
     * score每次timer调用的增加量,这里没有加上锻炼器材难度的等级
     * score加分公式score+=SCORE_BASE_STEP*level;
     */
    public static double SCORE_BASE_STEP=1.0*TIMER_INTERVAL*1.0/1000;
    /**
     * 每次degree因玩家按键盘而前进的速度
     */
    public static int DEGREE_STEP=5;
    /**
     * 锻炼器材名之一
     */
    public static int DUMBBELL=0;   //用哑铃曲臂练习
    /**
     * 锻炼器材名之一
     */
    public static int BARBELL=1;    //杠铃卧推
    /**
     * 锻炼器材名之一
     */
    public static int RUNNING_MACHINE=3; //跑步机
    /**
     * 锻炼器材名之一
     */
    public static int BICYCLE=4; //琦固定自行车
    /**
     * 锻炼器材名之一
     */
    public static int SANDBAG=5; //打沙袋
    /**
     * 更换动画图片的最大时间间隔
     */
    public static int MIN_IMAGE_CHANGE_INTERVAL=(int)(0.01*1000/TIMER_INTERVAL);
}
