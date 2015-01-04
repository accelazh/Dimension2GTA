package utilities;

import javax.swing.*;
import java.awt.*;
import basicConstruction.*;

//这个接口知名，它的子类都是可以被捡起，可以在商店里买到的
public interface Item 
{
	public abstract String getName();
	
	//得到物品的图片（在商店中使用）
	public abstract ImageIcon getPic();
	
	//这个图片在地图中使用（如捡起物品）
	public abstract ImageIcon getMapPic();
	
	
	//得到价格
	public abstract int getPrice();
	
	//得到物品的介绍（在商店中使用）
	public abstract String getInfo();

	//如果human买了这个物品，如何相应改变买者的属性
	//注意要考虑前是否够，买者是否已经有这个物品了
	//返回值说明human是否成功购买了该物品
	public abstract boolean Buy(Human buyer);
	
	//如果human捡起这个物品，如何相应改变买者的属性
	//注意要考虑买者是否已经有这个物品了
	//返回值说明human是否成功捡起了该物品
	public abstract boolean PickUp(Human picker);
	
}
