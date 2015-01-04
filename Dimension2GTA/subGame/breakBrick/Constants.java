package subGame.breakBrick;

import javax.swing.ImageIcon;
import java.awt.*;

public interface Constants {
	public static final Dimension GUI_SIZE = new Dimension(800, 564);
	public static final int TIMER_INTERVAL = 10;

	// for gamePanel
	public static final Color backgroundColor=new Color(103,69,8);
	
	public final static Dimension wallPanelSize = GUI_SIZE;
	public final static double gravity = 1500; // 单位象素每秒的平方

	public final int STOPPED = 0;
	public final int STARTED = 1;

	public static final Dimension brickSize = new Dimension(40, 13);
	public static final Dimension brickAreaSize = new Dimension(800, 390);
	public static final int brickRowNum = brickAreaSize.height
			/ brickSize.height;
	public static final int brickColumNum = brickAreaSize.width
			/ brickSize.width;
	public static final int groundLine = 497; // 描述地面在那里，以便处理球撞地
	public static final int ballRadius = 11;

	public ImageIcon background = new ImageIcon(
	"pic/SubGame/shootingPractice/bk7.jpg");
	// 对各种不同砖块的定义
	// 砖块的颜色
	public final static int RED = 0;
	public final static int ORINGE = 1;
	public final static int YELLOW = 2;
	public final static int GREEN = 3;
	public final static int QING = 4;
	public final static int BLUE = 5;
	public final static int DBLUE = 6;
	public final static int PURPLE = 7;
	public final static int BLACK = 8;
	public final static int WHITE = 9;
	public final static int EXPLOSIVE_PIC = 10;
	public final static int FIRM_PIC = 11;

	// 各种砖块的图像
	public static Image[] brickImagesR = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/r" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesO = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/o" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesY = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/y" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesG = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/g" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesQ = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/q" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesB = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/b" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesD = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/d" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesP = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/p" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesH = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/h" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesW = {
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 0 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 1 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 2 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 3 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 4 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 5 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 6 + ".gif").getImage(),
			new ImageIcon("pic/SubGame/shootingPractice/brick/w" + 7 + ".gif").getImage(), };

	public static Image[] brickImagesExplosive={
		Toolkit
		.getDefaultToolkit()
		.getImage(
				"pic/SubGame/shootingPractice/brick/explosive0.gif"),
        Toolkit
		.getDefaultToolkit()
		.getImage(
				"pic/SubGame/shootingPractice/brick/explosive1.gif")};
	
	public static Image[] brickImagesFirm={ 
		Toolkit.getDefaultToolkit().getImage(
        "pic/SubGame/shootingPractice/brick/firm.gif")};
}
