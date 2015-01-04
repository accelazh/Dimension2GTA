package utilities;

import javax.swing.*;
import java.awt.*;
import basicConstruction.*;

//����ӿ�֪�����������඼�ǿ��Ա����𣬿������̵����򵽵�
public interface Item 
{
	public abstract String getName();
	
	//�õ���Ʒ��ͼƬ�����̵���ʹ�ã�
	public abstract ImageIcon getPic();
	
	//���ͼƬ�ڵ�ͼ��ʹ�ã��������Ʒ��
	public abstract ImageIcon getMapPic();
	
	
	//�õ��۸�
	public abstract int getPrice();
	
	//�õ���Ʒ�Ľ��ܣ����̵���ʹ�ã�
	public abstract String getInfo();

	//���human���������Ʒ�������Ӧ�ı����ߵ�����
	//ע��Ҫ����ǰ�Ƿ񹻣������Ƿ��Ѿ��������Ʒ��
	//����ֵ˵��human�Ƿ�ɹ������˸���Ʒ
	public abstract boolean Buy(Human buyer);
	
	//���human���������Ʒ�������Ӧ�ı����ߵ�����
	//ע��Ҫ���������Ƿ��Ѿ��������Ʒ��
	//����ֵ˵��human�Ƿ�ɹ������˸���Ʒ
	public abstract boolean PickUp(Human picker);
	
}
