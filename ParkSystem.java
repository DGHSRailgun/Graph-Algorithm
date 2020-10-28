package experiment;

import java.util.Scanner;
import java.util.StringTokenizer;;

public class ParkSystem {
	
	MyStack garage = new MyStack(3);//ͣ����ģ��LIFO
	MyQueue road = new MyQueue();//����������
	MyStack buffer = new MyStack(3);//�������ģ��FIFO
	
	/**
	 * ģ������ͣ��ķ���
	 * @param c
	 * @param a
	 */
	public void in(int c,int a)
	{
		int curr = 0;
		int count = 0;
		//����ͣ�������ų�������ͬ�����
		for(int i = 0;i<garage.top+1;i++)
		{
			if(garage.nodes[i].number == c)
			{
				curr = i;
				count++;
				break;
			}

		}
		
		if(count != 0)
		{
			System.out.println("����ͣ����ͬ���յĳ�!");
			return;
		}
		
		if(!garage.isFull())
		{
			garage.push(new StackNode(c,a));
			System.out.println("����: "+c+"��,ͣ��ͣ�����ڵ�"+garage.top+"��!");
		}else {
			road.enQueue(c,a);
			System.out.println("��������,��ͣ���˱��!");
		}

	}

	/**
	 * ģ��������ͣ����ʻ���ķ���
	 * @param c
	 * @param a
	 */
	public void out(int c,int a)
	{
		int curr = 0;
		int count = 0;
		//����Ҫ�����ĳ��Ƿ���ͣ��������
		for(int i = 0;i<garage.top+1;i++)
		{
			if(garage.nodes[i].number == c)
			{
				curr = i;
				count++;
				break;
			}

		}
		
		if(count == 0)
		{
			System.out.println("�����ڴ˳�!");
			return;
		}
			//��ͣ��Ҫ�����ĳ�����ĳ����뻺������
			StackNode n;
			while(garage.top != curr)
			{
				n = garage.pop();
				buffer.push(n);
			}
			//����ջ
			n = garage.pop();			
			int money = a - n.arrive_Time;		
			System.out.println("���ƺ�Ϊ"+n.number+"�ĳ��뿪,ͣ����:"+money+"Ԫ��");
			
			while(!buffer.isEmpty())
			{
				n = buffer.pop();
				garage.push(n);
			}
			//�������ϲ��ǿյģ���ô�ö��׵ĳ�����ͣ����
			if(!road.isEmpty())
			{
				n = new StackNode(road.deQueue(),a);
				garage.push(n);
				System.out.println("���ƺ�Ϊ"+n.number+"�ĳ�"+a+"���·�Ͻ���ͣ������");
			}

	}
	/**
	 * ѡ��ʻ�뻹��ʻ��
	 */
	public void choose()
	{
		System.out.println("***************���ѽ���ͣ��������ϵͳ***************");
		System.out.println("====================================================");
		Scanner scanner = new Scanner(System.in);
		while(true)
		{
			System.out.println("��ֱ�����ʻ�뻹�ǿ���(in/out),���ƺ�,����ʱ�䡣����0���˳�ͣ��������ϵͳ��");//�ÿո�ֿ�
			String s = scanner.nextLine();
			if(s.equals("0"))
			{
				System.out.println("�˳�ͣ��������ϵͳ!");
				return;
			}
			StringTokenizer st=new StringTokenizer(s," ");
			
			int arrive_Time;//����ʱ��
			int car_Number;//���ƺ�
			String option;//����
			
			option = st.nextToken();
			try {
				car_Number = Integer.parseInt(st.nextToken());
			}catch(Exception e){
				System.out.println("��������������!");
				continue;
			}
			try {
				arrive_Time = Integer.parseInt(st.nextToken());
			}catch(Exception e) {
				System.out.println("����������ʱ��!");
				continue;
			}
			
			//ѡ�񷽷�
			if(option.equals("in"))
			{
				in(car_Number,arrive_Time);
			}
			if(option.equals("out"))
			{
				out(car_Number,arrive_Time);				
			}
	
			if(!option.equals("in")&&!option.equals("out"))
			{
				System.out.println("��������ȷ�Ĳ���!");
				continue;
			}

		}

	}
		
}
