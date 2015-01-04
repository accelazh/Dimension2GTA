package utilities;

import java.awt.*;
import java.util.*;
import gameDisplayProcessor.*;

/**
 * 
 * @author ZYL
 * ��������Ҫͨ�����Ƿ���imageRadius��ʵ��
 * ������������������ǿռ�������ϵ�������ǣ�����
 * �뾶���ɴ�ָ���ռ�ͼ��(���������귽�̱�ʾ)
 * 
 * ������ʵ�������������initialize()��������
 */
public abstract class MyLineImage3D 
{
	//for debug
	private static final boolean debug23=ControlSetting.debug23;
	private static final boolean debug24=ControlSetting.debug24;
	
	/**
	 * ���������������Ԥ������߶�ͼ
	 */
	public static final int BALL_IMAGE=0;
	public static final int OVAL_IMAGE=1;
	
	private ArrayList<MyLine3D> lines=new ArrayList<MyLine3D>();
	/**
	 * �����߶ε�ʱ��360�ȵĦȷֳɼ���
	 */
	private int nThita=36;  
	/**
	 * �����߶ε�ʱ��180�ȵĦ׷ֳɼ���
	 */
	private int nFai=18;
	
	
	
	public MyLineImage3D()
	{
		
	}
	
	/**
	 * ����imageRadius����MyLine3D�����飬����ͼ��
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
	 * @param arcThita �Ǧ�
	 * @param arcFai���Ǧ�
	 * @return �뾶r
	 */
	protected abstract double imageRadius(double arcThita, double arcFai);

	/**
	 * �滭����
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
	 * �õ�Ԥ������߶�ͼ
	 * @param type �߶�ͼ�����࣬��������Դ��ĳ�����ʾ
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
