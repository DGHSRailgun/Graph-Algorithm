package experiment;

import java.util.Scanner;
import java.util.StringTokenizer;;

public class ParkSystem {
	
	MyStack garage = new MyStack(3);//停车场模拟LIFO
	MyQueue road = new MyQueue();//出车缓冲区
	MyStack buffer = new MyStack(3);//便道队列模拟FIFO
	
	/**
	 * 模拟汽车停入的方法
	 * @param c
	 * @param a
	 */
	public void in(int c,int a)
	{
		int curr = 0;
		int count = 0;
		//遍历停车场，排除车号相同的情况
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
			System.out.println("不能停入相同牌照的车!");
			return;
		}
		
		if(!garage.isFull())
		{
			garage.push(new StackNode(c,a));
			System.out.println("汽车: "+c+"号,停在停车场内第"+garage.top+"号!");
		}else {
			road.enQueue(c,a);
			System.out.println("车库已满,车停在了便道!");
		}

	}

	/**
	 * 模拟汽车从停车场驶出的方法
	 * @param c
	 * @param a
	 */
	public void out(int c,int a)
	{
		int curr = 0;
		int count = 0;
		//查找要开出的车是否在停车场里面
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
			System.out.println("不存在此车!");
			return;
		}
			//把停在要开出的车后面的车放入缓冲区中
			StackNode n;
			while(garage.top != curr)
			{
				n = garage.pop();
				buffer.push(n);
			}
			//车出栈
			n = garage.pop();			
			int money = a - n.arrive_Time;		
			System.out.println("车牌号为"+n.number+"的车离开,停车费:"+money+"元。");
			
			while(!buffer.isEmpty())
			{
				n = buffer.pop();
				garage.push(n);
			}
			//如果便道上不是空的，那么让队首的车进入停车场
			if(!road.isEmpty())
			{
				n = new StackNode(road.deQueue(),a);
				garage.push(n);
				System.out.println("车牌号为"+n.number+"的车"+a+"点从路上进入停车场。");
			}

	}
	/**
	 * 选择驶入还是驶出
	 */
	public void choose()
	{
		System.out.println("***************您已进入停车场管理系统***************");
		System.out.println("====================================================");
		Scanner scanner = new Scanner(System.in);
		while(true)
		{
			System.out.println("请分别输入驶入还是开出(in/out),车牌号,进入时间。输入0则退出停车场管理系统。");//用空格分开
			String s = scanner.nextLine();
			if(s.equals("0"))
			{
				System.out.println("退出停车场管理系统!");
				return;
			}
			StringTokenizer st=new StringTokenizer(s," ");
			
			int arrive_Time;//到达时间
			int car_Number;//车牌号
			String option;//操作
			
			option = st.nextToken();
			try {
				car_Number = Integer.parseInt(st.nextToken());
			}catch(Exception e){
				System.out.println("请输入整数车牌!");
				continue;
			}
			try {
				arrive_Time = Integer.parseInt(st.nextToken());
			}catch(Exception e) {
				System.out.println("请输入整数时间!");
				continue;
			}
			
			//选择方法
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
				System.out.println("请输入正确的操作!");
				continue;
			}

		}

	}
		
}
