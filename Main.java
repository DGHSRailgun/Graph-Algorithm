package experiment;

import java.util.Scanner;
import java.io.*;

public class Main {
	static Graph graph = new Graph();//图对象
	static ParkSystem Parking_Lot = new ParkSystem();//停车场对象
	static String message = null;//通知
	
	/**
	 * 实现UI方法
	 * @throws IOException
	 */
	public static void UserLogin() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		boolean isRight = true;
		
		while(true)
		{
			System.out.println("请输入用户名: ");
			String s1 = scanner.nextLine();
			System.out.println("请输入密码: ");
			String s2 = scanner.nextLine();
			if(s1.equals("lys")&&(s2.equals("lys")))
			{
				System.out.println("登录成功!");
				break;
			}
			
			if(!s1.equals("lys"))
			{
				System.out.println("用户名错误,请重新输入!");
				continue;
			}
			
			if(s1.equals("lys")&&!(s2.equals("lys")))
			{
				System.out.println("密码错误,请重新输入!");
				continue;
			}
	
		}
		
		
		String s = "请选择您想进行的操作:"+"\n"+"1.新建景点"+"\n"+"2.删除景点"+"\n"+"3.新建景区道路"+"\n"+"4.删除景区道路"+"\n"+"5.发布通知"+"\n"+"0.退出管理系统";
		
		String option = "";
		
		while(isRight)
		{
			while(true)
			{
				System.out.println(s);
				option = scanner.nextLine();
				int i = Integer.MAX_VALUE;
				try {
					i = Integer.parseInt(option);
				}catch(Exception e){
					System.out.println("请输入选项中的整数!");
					continue;
				}
				
				if(i<0||i>5)
					System.out.println("请输入选项范围内(0-5)的整数!");
				else
				break;
	
			}
			
			switch(option) 
			{
			case "1":
						graph.addNode();
						break;
			case "2":
						graph.deleteNode();
						break;
			case "3":
						graph.addSide();
						break;
			case "4":
						graph.deleteSide();
						break;
			case "5":
						System.out.println("请输入要发布的内容: ");
						String s1 = scanner.nextLine();
						try {
							BufferedWriter bw = new BufferedWriter(new FileWriter("message.txt"));
							bw.write(s1);
							bw.close();
							BufferedReader bfr = new BufferedReader(new FileReader("message.txt"));
							message = bfr.readLine();
							bfr.close();
						}catch(FileNotFoundException fnfe) {
							fnfe.printStackTrace();
						}
						System.out.println("已发布!");
						break;
			case "0":
						System.out.println("退出管理员系统!");
						isRight = false;
						break;
			
			}

		}

	}
	/**
	 * 选择排序的分类
	 */
	public static void sort_Type()
	{
		System.out.println("请输入根据受欢迎度(0)还是岔路口数目(1)排序");
		Scanner scanner = new Scanner(System.in); 
		while(true)
		{
			int i = Integer.parseInt(scanner.nextLine());
			if(i != 0&&i != 1)
			{
				System.out.println("请输入正确的数字!");
			}
			
			if(i == 0)
			{
				graph.popularity_Sort();
				break;		
			}
			
			if(i == 1)
			{
				graph.branch_Num_Sort();				
				break;
			}
				
		}
			
	}
	
	/**
	 * 主方法
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//读取文件景点信息和边
		graph.readedge("side.txt");
		graph.readinfo("information.txt");
		BufferedReader bfr = new BufferedReader(new FileReader("message.txt"));
		message = bfr.readLine();
		
		while(true)
		{
			System.out.println("=====================================");
			System.out.println("         欢迎使用景区信息管理系统                      ");
			System.out.println("        ***请选择菜单***                 ");
			System.out.println("=====================================");
			System.out.println("1.管理员登陆");
			System.out.println("2.输出景区景点分布图");
			System.out.println("3.景区的查找");
			System.out.println("4.景区的排序");
			System.out.println("5.输出导游路线图");
			System.out.println("6.两个景点间的最短路径和最短距离");
			System.out.println("7.停车场车辆进出信息");
			System.out.println("0.退出");
			System.out.println("通知: "+message);
			
			Scanner scanner = new Scanner(System.in);
			String option = null;
			while(true) 
			{
				
				option = scanner.nextLine();
				
				if(Integer.parseInt(option)<0||Integer.parseInt(option)>7)
				{
					System.out.println("请输入0-7范围内的数字");
				}else {
					break;
				}
				
				
			}
			
			
			switch(option) {
				case "1":
							UserLogin();
							break;	
				case "2":
							graph.adjmatrix();
							break;
				case "3":
							graph.seek();
							break;
				case "4":
							sort_Type();			
							break;
				case "5":
							graph.prim_Algorithm();
							break;
				case "6":
							System.out.println("请选择算法:1.Dijkstra 2.Floyd");
							Scanner scanner1 = new Scanner(System.in);
							String str = scanner.nextLine();
							graph.resetAttribute();
							if(str.equals("1"))
							graph.dijkstra_Algorithm();
							if(str.equals("2"))
							graph.floyd_Algorithm();
							String shortest_Road = graph.create_Shortest_Path();
							if(shortest_Road.equals("wrong"))
								break;
							System.out.println(shortest_Road);
							break;
				case "7":
							Parking_Lot.choose();
							break;
				case "0":
							System.out.println("再见!");
							System.exit(0);
			}
	
		}
	
	}
	
}
