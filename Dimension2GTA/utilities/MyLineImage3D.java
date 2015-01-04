package utilities;

import java.awt.*;
import java.util.*;
import gameDisplayProcessor.*;

/**
 * 
 * @author ZYL
 * 这个类的能要通过覆盖方法imageRadius来实现
 * 这个方法的两个参数是空间求坐标系的两个角，返回
 * 半径。由此指定空间图形(即用球坐标方程表示)
 * 
 * 这个类的实例建立后，需调用initialize()方法可用
 */
public abstract class MyLineImage3D 
{
	//for debug
	private static final boolean debug23=ControlSetting.debug23;
	private static final boolean debug24=ControlSetting.debug24;
	
	/**
	 * 这个常量用来生成预定义的线段图
	 */
	public static final int BALL_IMAGE=0;
	public static final int OVAL_IMAGE=1;
	
	private ArrayList<MyLine3D> lines=new ArrayList<MyLine3D>();
	/**
	 * 生成线段的时候，360度的θ分成几份
	 */
	private int nThita=36;  
	/**
	 * 生成线段的时候，180度的ψ分成几份
	 */
	private int nFai=18;
	
	
	
	public MyLineImage3D()
	{
		
	}
	
	/**
	 * 根据imageRadius生成MyLine3D的数组，代表图形
	 */
	public void initializeImage()
	{
		if(debug24)
		{
			System.out.println("====in method initializeImage()====");
		}
		
		for(int i=0;i<nThita;i++)
		{
			double thita1=i*1.0/nThita*2*Math.PI;
			double thita2=(i+1)*1.0/nThita*2*Math.PI;
			
			for(int j=0;j<=nFai;j++)
			{
				double fai1=j*1.0/nFai*Math.PI;
				double fai2=(j+1)*1.0/nFai*Math.PI;
				MyPoint3D p1=new MyPoint3D(imageRadius(thita1,fai1),thita1,fai1,true);
				MyPoint3D p2=new MyPoint3D(imageRadius(thita2,fai1),thita2,fai1,true);
				
				MyPoint3D p3=new MyPoint3D(imageRadius(thita1,fai1),thita1,fai1,true);
				MyPoint3D p4=new MyPoint3D(imageRadius(thita1,fai2),thita1,fai2,true);
				
				if(debug24)
				{
					System.out.println("creating line["+(i*nFai+j)+"]");
					
					System.out.println("thita1: "+thita1);
					System.out.println("thita2: "+thita2);
					System.out.println("fai1: "+fai1);
					System.out.println("fai2: "+fai2);
					
					System.out.println("imageRadius(thita1,fai1): "+imageRadius(thita1,fai1));
					System.out.println("imageRadius(thita2,fai1): "+imageRadius(thita2,fai1));
					System.out.println("imageRadius(thita1,fai2): "+imageRadius(thita1,fai2));
					
					System.out.println("p1: "+p1);
					System.out.println("p2: "+p2);
					System.out.println("p3: "+p3);
					System.out.println("p4: "+p4);
				}
				
				lines.add(new MyLine3D(p1,p2));
				lines.add(new MyLine3D(p3,p4));
			}
		}
		
		if(debug24)
		{
			System.out.println("====end of method initializeImage()====");
		}
	}
	
	/**
	 * 
	 * @param arcThita 角θ
	 * @param arcFai　角ψ
	 * @return 半径r
	 */
	protected abstract double imageRadius(double arcThita, double arcFai);

	/**
	 * 绘画方法
	 */
	public void paint(Graphics g, PlaneCoordinateSystemIn3D plane)
	{
		for(int i=0;i<lines.size();i++)
		{
			MyLine3D line=lines.get(i);
			if(line!=null)
			{
				if(debug23)
				{
					System.out.println("painting line["+i+"]");
				}
				line.paint(g,plane);
			}
		}
	}
	
	/**
	 * 得到预定义的线段图
	 * @param type 线段图的种类，用这个类自带的常量表示
	 */
	
	public static MyLineImage3D createLineImage(int type)
	{
		if(BALL_IMAGE==type)
		{
			return new MyLineImage3DOfBall();
		}
		
		if(OVAL_IMAGE==type)
		{
			return new MyLineImage3DOfOval();
		}
		
		return null;
	}
	
	public int getNThit() 
	{
		return nThita;
	}

	public void setNThit(int nThita) 
	{
		this.nThita = nThita;
		initializeImage();
	}

	public int getNFai() {
		return nFai;
	}

	public void setNFai(int nFai) 
	{
		this.nFai = nFai;
		initializeImage();
	}
	
	public void setNThitAndNFai(int nThita, int nFai)
	{
		this.nThita = nThita;
		this.nFai = nFai;
		initializeImage();
		
	}
}
