package experiment;

import java.util.Scanner;
import java.io.*;

public class Main {
	static Graph graph = new Graph();//ͼ����
	static ParkSystem Parking_Lot = new ParkSystem();//ͣ��������
	static String message = null;//֪ͨ
	
	/**
	 * ʵ��UI����
	 * @throws IOException
	 */
	public static void UserLogin() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		boolean isRight = true;
		
		while(true)
		{
			System.out.println("�������û���: ");
			String s1 = scanner.nextLine();
			System.out.println("����������: ");
			String s2 = scanner.nextLine();
			if(s1.equals("lys")&&(s2.equals("lys")))
			{
				System.out.println("��¼�ɹ�!");
				break;
			}
			
			if(!s1.equals("lys"))
			{
				System.out.println("�û�������,����������!");
				continue;
			}
			
			if(s1.equals("lys")&&!(s2.equals("lys")))
			{
				System.out.println("�������,����������!");
				continue;
			}
	
		}
		
		
		String s = "��ѡ��������еĲ���:"+"\n"+"1.�½�����"+"\n"+"2.ɾ������"+"\n"+"3.�½�������·"+"\n"+"4.ɾ��������·"+"\n"+"5.����֪ͨ"+"\n"+"0.�˳�����ϵͳ";
		
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
					System.out.println("������ѡ���е�����!");
					continue;
				}
				
				if(i<0||i>5)
					System.out.println("������ѡ�Χ��(0-5)������!");
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
						System.out.println("������Ҫ����������: ");
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
						System.out.println("�ѷ���!");
						break;
			case "0":
						System.out.println("�˳�����Աϵͳ!");
						isRight = false;
						break;
			
			}

		}

	}
	/**
	 * ѡ������ķ���
	 */
	public static void sort_Type()
	{
		System.out.println("����������ܻ�ӭ��(0)���ǲ�·����Ŀ(1)����");
		Scanner scanner = new Scanner(System.in); 
		while(true)
		{
			int i = Integer.parseInt(scanner.nextLine());
			if(i != 0&&i != 1)
			{
				System.out.println("��������ȷ������!");
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
	 * ������
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		//��ȡ�ļ�������Ϣ�ͱ�
		graph.readedge("side.txt");
		graph.readinfo("information.txt");
		BufferedReader bfr = new BufferedReader(new FileReader("message.txt"));
		message = bfr.readLine();
		
		while(true)
		{
			System.out.println("=====================================");
			System.out.println("         ��ӭʹ�þ�����Ϣ����ϵͳ                      ");
			System.out.println("        ***��ѡ��˵�***                 ");
			System.out.println("=====================================");
			System.out.println("1.����Ա��½");
			System.out.println("2.�����������ֲ�ͼ");
			System.out.println("3.�����Ĳ���");
			System.out.println("4.����������");
			System.out.println("5.�������·��ͼ");
			System.out.println("6.�������������·������̾���");
			System.out.println("7.ͣ��������������Ϣ");
			System.out.println("0.�˳�");
			System.out.println("֪ͨ: "+message);
			
			Scanner scanner = new Scanner(System.in);
			String option = null;
			while(true) 
			{
				
				option = scanner.nextLine();
				
				if(Integer.parseInt(option)<0||Integer.parseInt(option)>7)
				{
					System.out.println("������0-7��Χ�ڵ�����");
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
							System.out.println("��ѡ���㷨:1.Dijkstra 2.Floyd");
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
							System.out.println("�ټ�!");
							System.exit(0);
			}
	
		}
	
	}
	
}
