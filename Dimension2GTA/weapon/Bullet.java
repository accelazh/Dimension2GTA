package weapon;

import java.awt.*;

import utilities.MyPoint;
import basicConstruction.*;
import gameDisplayProcessor.*;

public class Bullet 
{
	//for debug
	private boolean debug10=ControlSetting.debug10;
	//data field
	private Human owner;//˭������ӵ�
	
	private Point startPoint;
	private double arc;  //����ĽǶȣ��Ѿ�����ɢ���,����ѧ�ļ�����ϵ��
	private int killLevel;  //�ӵ�֮��ɱ����������ͬ��������Կ̻�����ӵ���Ӳ��ɱ����,������ܸı䣬��healthPoint��ͬ���ĵ�λ
	
	private Solid[] possibleHitSolids;
	private Point hitPoint;
	private boolean isHitSomething;
	private Solid hittedSolid;
	
	
	public Bullet(Point startPoint,double arc,int killLevel,Human owner)
	{
		this.startPoint=startPoint;
		this.arc=arc;
		this.killLevel=killLevel;
	    this.owner=owner;
	}
	
	
	public Bullet()
	{
		this(new Point(0,0),0,35,null);
	}
	
	public void initialize(Rectangle screenArea,Solid[] solidsInScreen) //����������ӵ����еĶ�������������ʼ��������
	{
		if(debug10)
		{
			System.out.println("====in method initialize in bullet====");
			System.out.println("screenArea : "+screenArea);
			System.out.println(this);
			
		}
		
		//�Ȳ����ӵ�����Ļ�ڵĵ����������ľ��ο�
		int measureDistance=10;  //ÿ��������֮��ľ���
		Point measurePoint=new Point(startPoint);
		int i=0;
		while(screenArea.contains(measurePoint))
		{
			i++;
			measurePoint.setLocation(startPoint.x+i*measureDistance*Math.cos(arc),
					startPoint.y-i*measureDistance*Math.sin(arc));
			
		}
		
		if(debug10)
		{
			System.out.println("find the first point beyond the screen area");
			System.out.println("endPoint : "+measurePoint);
		}
			
		Dimension bulletRectDimension=new Dimension((int)Math.abs(i*measureDistance*Math.cos(arc)),
				(int)Math.abs(i*measureDistance*Math.sin(arc)));
		Rectangle bulletRect=new Rectangle(new Point(Math.min(startPoint.x,measurePoint.x),
				Math.min(startPoint.y,measurePoint.y)),bulletRectDimension);
		
		if(debug10)
		{
			System.out.println("after create the bulletRect : ");
			System.out.println(bulletRect);
		}
		//�õ�Rectangle�е���Ļ�ڵ�����solid����������
		possibleHitSolids=MapContainer.SolidsInRectangle(bulletRect,solidsInScreen);
		
		if(debug10)
		{
			System.out.println("possibleHitSolids : \n"+MapContainer.getSolidListInfo(possibleHitSolids));
			
		}
		
		//�õ����е�
		
		MyPoint currentPos=new MyPoint(startPoint.x,startPoint.y);
		boolean isHitted=false;
		boolean isOut=false;
		do
		{
			if(debug10)
			{
				System.out.println("while checking solids : ");
				System.out.println("currentPos : "+currentPos);
				System.out.println("currentPos's arc : "+VectorClass.arcOfPoints(startPoint, currentPos.getPoint())/Math.PI+"PI");
			}
			isHitted=false;
			for(int k=0;k<possibleHitSolids.length;k++)
			{
				if((possibleHitSolids[k]!=null)&&(owner!=possibleHitSolids[k]))
				{
					if(!possibleHitSolids[k].isAccesibleFromMap(currentPos))  //accessible����Ҫ�����Լ����ϽǵĶ����Ǵ��ͼ���Ͻ�
					{
						hittedSolid=possibleHitSolids[k];
						hitPoint=currentPos.getPoint();
						isHitted=true;
					}
				}
				
			}
			
			isOut=false;
			if(!screenArea.contains(currentPos.getPoint()))
			{
				isOut=true;
			}
			
			
			currentPos.setLocation(currentPos.x+2*Math.cos(arc),currentPos.y-2*Math.sin(arc));
			
		}while((!isHitted)&&(!isOut));
		
		if(isHitted)
		{
			isHitSomething=true;
						
		}
		else
		{
			isHitSomething=false;
			hitPoint=null;
			hittedSolid=null;
		}
		
		if(debug10)
		{
			System.out.println("after initialization, the bullet :");
			System.out.println(this);
		}
		
		
	}

	public Point getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	public double getArc() {
		return arc;
	}

	public void setArc(double arc) {
		this.arc = arc;
	}

	public int getKillLevel() {
		return killLevel;
	}

	public void setKillLevel(int killLevel) {
		this.killLevel = killLevel;
	}

	public Solid[] getPossibleHitSolids() {
		return possibleHitSolids;
	}

	public void setPossibleHitSolids(Solid[] possibleHitSolids) {
		this.possibleHitSolids = possibleHitSolids;
	}

	public Point getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(Point hitPoint) {
		this.hitPoint = hitPoint;
	}

	public boolean isHitSomething() {
		return isHitSomething;
	}

	public void setHitTarget(boolean isHitSomething) {
		this.isHitSomething = isHitSomething;
	}

	public Solid getHittedSolid() {
		return hittedSolid;
	}

	public void setHittedSolid(Solid hittedSolid) {
		this.hittedSolid = hittedSolid;
	}

	public String toString()
	{
		String output;
		output="bullet : \n";
		output+="startPoint : "+startPoint+"\n";
		output+="arc : "+arc/Math.PI+"PI\n";
		output+="killLevel : "+killLevel+"\n";
		output+="hitPoint : "+hitPoint+"\n";
		output+="isHitSomething : "+isHitSomething+"\n";
		output+="hittedSolid : "+"\n";
		if(null==hittedSolid)
		{
			output+="null\n";
		}
		else
		{
		    output+=hittedSolid.toString()+"\n";
		}
		output+="finished\n";
		
		return output;
	}

	public Human getOwner() {
		return owner;
	}

	public void setOwner(Human owner) {
		this.owner = owner;
	}
}
