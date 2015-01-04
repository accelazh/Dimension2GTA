package subGame.gym;

import java.awt.*;

/**
 * 
 * ������¼���õĳ���
 */
public interface GymConstants 
{
	public static int TIMER_INTERVAL=10;
	
	public static Dimension PANEL_SIZE=new Dimension(800,604); 
	/**
	 * ��Ϸ״̬running
	 */
	public static int RUNNING=0;
	/**
	 * ��Ϸ״̬paused
	 */
	public static int PAUSED=1;
	/**
	 * ��������ָʾ�����ֵ��������Ļ���Ͻ��Ǹ�С��
	 */
	public static int MAX_DEGREE=100; 
	/**
	 * ָʾС���Ĵ�С
	 */
	public static Dimension DEGREE_BAR_SIZE=new Dimension(100,10);
	/**
	 * degreeBar��λ��
	 */
	public static Point DEGREE_BAR_LOCATION=new Point(PANEL_SIZE.width-DEGREE_BAR_SIZE.width-30,50);
	/**
	 * ָʾС����ǰ��ɫ
	 */
	public static Color DEGREE_BAR_FOREGROUND=Color.green;
	/**
	 * ָʾС���ı���ɫ
	 */
	public static Color DEGREE_BAR_BACKGROUND=Color.LIGHT_GRAY;
	/**
	 * degree���˵��ٶȣ���ÿ�α�timer��������,��û�е�����ҵ�skill�Լ������Ѷȵȼ�
	 */
    public static double DEGREE_BACKLASH_BASE_SPEED=20*1.0/1000*TIMER_INTERVAL;	
    /**
     * ���level�ȼ���level�ȼ�ֱ�ӹ�ϵ��������backlash�ͼƷ�
     */
    public static int MAX_LEVEL=10;
    /**
     * ���������������ÿһ�ȼ�֮��Ĳ��
     */
    public static int LEVEL_DISTANCE=10;
    /**
     * scoreÿ��timer���õ�������,����û�м��϶��������Ѷȵĵȼ�
     * score�ӷֹ�ʽscore+=SCORE_BASE_STEP*level;
     */
    public static double SCORE_BASE_STEP=1.0*TIMER_INTERVAL*1.0/1000;
    /**
     * ÿ��degree����Ұ����̶�ǰ�����ٶ�
     */
    public static int DEGREE_STEP=5;
    /**
     * ����������֮һ
     */
    public static int DUMBBELL=0;   //������������ϰ
    /**
     * ����������֮һ
     */
    public static int BARBELL=1;    //��������
    /**
     * ����������֮һ
     */
    public static int RUNNING_MACHINE=3; //�ܲ���
    /**
     * ����������֮һ
     */
    public static int BICYCLE=4; //���̶����г�
    /**
     * ����������֮һ
     */
    public static int SANDBAG=5; //��ɳ��
    /**
     * ��������ͼƬ�����ʱ����
     */
    public static int MIN_IMAGE_CHANGE_INTERVAL=(int)(0.01*1000/TIMER_INTERVAL);
}
