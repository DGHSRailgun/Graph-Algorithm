package experiment;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Graph {
	Node[] nodes = new Node[50];//������ž���(ͼ�ڵ�)
	String s1 = "";//�����洢Dijkstra��Floyd�㷨�е������յ�
	String s2 = "";//�����洢Dijkstra��Floyd�㷨�е������յ�
	/**
	 * �����ļ�"side.txt"��������
	 * @param e
	 */
	public void readedge(String e)
	{
		//e���ļ�����
		for(int i = 0;i<50;i++)
		{
		nodes[i] = new Node();	
		}
		//��ʼ��
		FileReader fr = null;
		try {
			fr = new FileReader(e);					
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		Scanner s = new Scanner(fr);
		
		int j,k = 0;
		//���ж�
		while(s.hasNextLine())
		{
			j = 0;
			k = 0;
			String  b = s.nextLine();
			StringTokenizer st = new StringTokenizer(b,"����");
			Node x = new Node(st.nextToken());
			Node y = new Node(st.nextToken());
			while(!nodes[j].empty())
			{
				if(nodes[j].equal(x))//�жϾ����Ƿ����
				{
					k++;
					break;
				}
				j++;
				if(j >= 50)
				{
					k = 1;
					break;
				}
			}
			
			if(k == 0)//�����������
			nodes[j] = x;
			String p = st.nextToken();////�ѱ߼ӵ��ڽӱ�
			if(nodes[j].adjList == null)
			{//����ǿյ�
				nodes[j].adjList = new Side(x,y,Integer.parseInt(p));
				nodes[j].rear = nodes[j].adjList;
				nodes[j].bif_Num = 1+nodes[j].bif_Num;	
			}else {//����Ѵ��ھ�����ӵ�β��
				nodes[j].rear.linked = new Side(x,y,Integer.parseInt(p));
				nodes[j].rear = nodes[j].rear.linked;
				nodes[j].bif_Num = 1+nodes[j].bif_Num;
			}
			j = 0;
			k = 0;
			
			while(!nodes[j].empty())
			{
				if(nodes[j].equal(y))
				{
					k++;
					break;
				}
				j++;
				if(j >= 50)
				{
					k = 1;
					break;
				}
			}
			
			if(k == 0)
			{
				nodes[j] = y;
			}
			//����һ��������ӱ�
			if(nodes[j].adjList == null)
			{
				nodes[j].adjList = new Side(y,x,Integer.parseInt(p));
				nodes[j].rear = nodes[j].adjList;
				nodes[j].bif_Num++;
			}
			else {
				nodes[j].rear.linked = new Side(y,x,Integer.parseInt(p));
				nodes[j].rear = nodes[j].rear.linked;
				nodes[j].bif_Num++;
			}
			
		}
		s.close();
	}
	/**
	 * ��������Ϣ�ļ�information.txt��������
	 * @param i
	 */
	public void readinfo(String i)
	{
		//i���ļ���
		FileReader fr = null;
		try {
			fr = new FileReader(i);
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}		
		Scanner s = new Scanner(fr);
		while(s.hasNextLine())
		{
			String line = s.nextLine();
			StringTokenizer st =new StringTokenizer(line," ");
			String name = st.nextToken();
			int j = 0;
			while(!nodes[j].empty())
			{
				if(nodes[j].name.equals(name))
					break;
				else;
					j++;
			}
			nodes[j].introduction = st.nextToken();
			nodes[j].popularity = Integer.parseInt(st.nextToken());
			nodes[j].restArea = Boolean.parseBoolean(st.nextToken());
			nodes[j].toilet = Boolean.parseBoolean(st.nextToken());		
		}
	}
	/**
	 * �����ڽӾ����һ��
	 */
	public void adjmatrix()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\t");				
		int i = 0;
		int j = 0;
		String s;
		for(i = 0;!nodes[i].empty();i++)
		{
			s = nodes[i].name+"\t";//����
			sb.append(s);
		}
		
		sb.append("\n");
		
		i = 0;
		
		while(!nodes[i].empty())
		{

			s = nodes[i].name+"\t";//����
			sb.append(s);
			Side x = nodes[i].adjList;
			int k = 0;
			j = 0;
			while(!nodes[j].empty())
			{
				if(i == j)
				{
					s = "0"+"\t";
					sb.append(s);
					j++;
					continue;
				}	
			
				x = nodes[i].adjList;
				k = 0;
				while(x != null)
				{
					if(x.end.name.equals(nodes[j].name))
					{
						k++;
						break;
					}
					x = x.linked;				
				}
			
				if(k == 0)
				{
					s = "32767"+"\t";
					sb.append(s);
				}else {
					s = x.weight + "\t";
					sb.append(s);
				}
				j++;
			}
		
		sb.append("\n");
		i++;
		}
		System.out.println(sb.toString());
	}
	
	//��ʼ���ڵ�����	
	public void resetAttribute()
	{
		for(int i = 0;!this.nodes[i].empty();i++)
			this.nodes[i] = new Node(this.nodes[i].name,this.nodes[i].adjList,this.nodes[i].rear);
	}
	/**
	 * ����Ѱ�����·������̾����Dijkstra�㷨
	 */
	public void dijkstra_Algorithm()
	{
		System.out.println("���������յ�: ");
		Scanner s = new Scanner(System.in);
		s1 = s.next();
		s2 = s.next();
		int i = 0,j = 0;
		int count = 0,k = 0;
		int moving = 0;
		int temp = 0;
		int stamp = 0;
		while(!nodes[k].empty())//�ж�����Ƿ����
		{
			if(nodes[k].name.equals(s1))
			{
				stamp++;
				break;
			}
			k++;
		}
		
		Side x = nodes[k].adjList;
		if(stamp == 0)
		{
			System.out.println("�����������: ");
			return;
		}
		
		while(x != null)
		{
			moving = 0;
			while(!nodes[moving].empty())//��ʼ��
			{
				if(nodes[moving].name.equals(x.end.name))
				{
					nodes[moving].beforeNode = nodes[k];
					nodes[moving].cost = x.weight;
					break;
				}
				moving++;
			}
			x = x.linked;
		}
			nodes[k].cost = 0;
			nodes[k].isShort = true;
		
		while(!nodes[count].empty())
		{
			count++;
		}
		
		for(i = 0;i <= count-2;i++)//���ѭ��ÿ��Ѱ��һ���ڵ�����·��
		{
			int min_cost = Integer.MAX_VALUE;
			for(j = 0;j < count;j++)//����"���ҵ�"
			{
				if(!nodes[j].isShort&&nodes[j].cost<min_cost)
					{
						temp = j;
						min_cost = nodes[j].cost;
					}
			}
			
			nodes[temp].isShort = true;
			Side si = nodes[temp].adjList;
			while(si != null)
			{
				moving = 0;
				while(!nodes[moving].empty())
				{
					if(nodes[moving].name.equals(si.end.name)) {
						if(nodes[temp].cost+si.weight < nodes[moving].cost)
						{
							//����ҵ���С�ģ����½ڵ��cost
							nodes[moving].cost = nodes[temp].cost+si.weight;
							nodes[moving].beforeNode = nodes[temp];
						}
						break;
					}
					moving++;		
				}
				si = si.linked;				
			}		
		}
	}
	/**
	 * ����Ѱ�����·������̾����Floyd�㷨
	 * �����ڴ洢��ʱ��Node�ڵ�������ԣ�������beforeNode�����ԣ���˴˷���������Dijkstra�㷨
	 */
	public void floyd_Algorithm()
	{
		System.out.println("���������յ�: ");
		Scanner s = new Scanner(System.in);
		s1 = s.next();
		s2 = s.next();
		int i = 0,j = 0;
		int count = 0,k = 0;
		int moving = 0;
		int temp = 0;
		int stamp = 0;
		while(!nodes[k].empty())
		{
			if(nodes[k].name.equals(s1))
			{
				stamp++;
				break;
			}
			k++;
		}
		
		Side x = nodes[k].adjList;
		if(stamp == 0)
		{
			System.out.println("�����������: ");
			return;
		}
		
		while(x != null)
		{
			moving = 0;
			while(!nodes[moving].empty())
			{
				if(nodes[moving].name.equals(x.end.name))
				{
					nodes[moving].cost = x.weight;
					nodes[moving].beforeNode = nodes[k];		
					break;
				}
				moving++;
			}
			x = x.linked;
		}
		
			nodes[k].cost = 0;
			nodes[k].isShort = true;
		
		while(!nodes[count].empty())
		{
			count++;
		}
		
		for(i = 0;i < count-1;i++)
		{
			int min_cost = Integer.MAX_VALUE;
			for(j = 0;j < count;j++)
			{
				if(!nodes[j].isShort&&nodes[j].cost<min_cost)
					{
						min_cost = nodes[j].cost;
						temp = j;	
					}
			}
			
			Side si = nodes[temp].adjList;
			nodes[temp].isShort = true;
			while(si != null)
			{
				moving = 0;
				while(!nodes[moving].empty())
				{
					if(nodes[moving].name.equals(si.end.name)) {
						if(nodes[temp].cost+si.weight < nodes[moving].cost)
						{
							nodes[moving].beforeNode = nodes[temp];
							nodes[moving].cost = nodes[temp].cost+si.weight;							
						}
						break;
					}
					moving++;		
				}
				si = si.linked;				
			}
			
		}

	}
	/**
	 * �������·����������̾���
	 * @return
	 */
	public String create_Shortest_Path()
	{
		int stamp = 0;
		int point = 0;
		
		while(!nodes[point].empty())
		{
			if(nodes[point].name.equals(s2))
			{
				stamp++;
				break;
			}
			point++;
			if(point >= 50)
				break;	
		}
		
		if(stamp == 0)
		{
			System.out.println("�����յ�����: ");
			return "wrong";
		}
		
		String string = s2;
		Node n = nodes[point];
		
		while(!n.name.equals(s1))
		{
			string = n.beforeNode.name + " ���������� " + string;
			n = n.beforeNode;		
		}	
		int i = 0;
		while(!nodes[i].empty())
		{
			if(nodes[i].name.equals(s2))
				break;
			i++;
			if(i >= 50)
				break;
		}	
		return "���·��Ϊ: "+string+"\n"+"��"+s1+"��"+s2+"����̾���Ϊ: "+nodes[i].cost;
	}
	/**
	 * ����ķ���	
	 * @throws IOException
	 */
	public void addNode() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫ���ӵľ�������: ");
		String s = scanner.nextLine();
		int i = 0;
		s = s.replaceAll(" ","");
		while(!nodes[i].empty())
		{
			if(nodes[i].name.equals(s))
			{
				System.out.println("�þ����Ѿ�����!");
				return;
			}
			i++;
		}
		nodes[i] = new Node(s);
		System.out.println("�ɹ����Ӿ���"+s+"!");
		//������ӵľ���д���ļ���
		BufferedWriter bw = new BufferedWriter(new FileWriter("information.txt"));
		for(int m = 0;!nodes[m].empty();m++)
		{
			bw.write(nodes[m].name+" "+nodes[m].introduction+" "+nodes[m].popularity+" "+nodes[m].restArea+" "+nodes[m].toilet+"\n");		
		}
		bw.close();	
	}
	/**
	 * ��Ӿ�����·�ķ���
	 * @throws IOException
	 */
	public void addSide() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("�������һ�����ڶ�����������ƺ�Ȩ��ֵ: ");
		String s = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(s," ");//�ÿո�ֿ�
		String str1 = st.nextToken();
		String str2 = st.nextToken();
		int count_Weight;
		try {
			count_Weight = Integer.parseInt(st.nextToken());//�����±߼�¼Ȩ��
		}catch(Exception e) {
			System.out.println("����ȷ����!");
			return;
		}
		
		
		int i = 0;
		int j = 0;
		int stamp = 0;
		//�ж�����ӵı��Ƿ��Ѿ�����
		while(!nodes[i].empty())
		{//�����ڵ�����Ѱ�����
			if(nodes[i].name.equals(str1)) 
			{
				break;
			}
			i++;
		}
		
		Side sd = nodes[i].adjList;
		
		while(sd != null)
		{//Ѱ�������ڽӱ������Ƿ����յ�
			if(sd.end.name.equals(str2))
			{
				stamp++;
				break;
			}
			sd = sd.linked;
			
		}
		
		if(stamp > 0)
		{//����������Ѿ�����
			System.out.println("�������Ѿ����ڣ��������!");
			return;
		}
		
		while(!nodes[j].empty())
		{//�ھ�����������Ѱ���յ��λ��
			if(nodes[j].name.equals(str2))
			{
				break;
			}
			j++;
		}
		
		if(nodes[i].adjList == null)
		{//����ڽӱ�Ϊ�գ�ֱ�����
			nodes[i].adjList = new Side(nodes[i],nodes[j],count_Weight);
			nodes[i].rear = nodes[i].adjList;
			nodes[i].bif_Num++;
		}else {//�����Ϊ�գ���ӵ��ڽӱ��β
			nodes[i].rear.linked = new Side(nodes[i],nodes[j],count_Weight);
			nodes[i].rear = nodes[i].rear.linked;
			nodes[i].bif_Num++;		
		}
		
		if(nodes[j].adjList == null)
		{//��������ͼ�����յ���ӱ�Ԫ��
			nodes[j].adjList = new Side(nodes[j],nodes[i],count_Weight);
			nodes[j].rear = nodes[j].adjList;
			nodes[j].bif_Num++;
		}else {//ͬ�ϣ�����յ���ڽӱ�Ϊ�գ�����ӵ���β
			nodes[j].rear.linked = new Side(nodes[j],nodes[i],count_Weight);
			nodes[j].rear = nodes[j].rear.linked;
			nodes[j].bif_Num++;		
		}
		
		System.out.println("��ӳɹ�!");
		//������ӵ�"side.txt"�ļ�����
		BufferedWriter bw = new BufferedWriter(new FileWriter("side.txt",true));
		bw.write("\n"+str1+"����"+str2+"����"+count_Weight+"\n");
		bw.close();	
	}
	/**
	 * ɾ�����㷽��
	 * @throws IOException
	 */
	public void deleteNode() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("����������ά���ľ���: ");
		String s = scanner.nextLine();
		
		int i = 0,j = 0;
		boolean isFound = false;
		while(!nodes[i].empty())
		{//������������Ѱ��Ҫɾ���ľ���
			if(nodes[i].name.equals(s)) 
			{
				isFound = true;
				break;
			}
			i++;
		}
		
		if(!isFound)
		{//������㲻����
			System.out.println("�þ��㲻���ڻ����Ѿ�ɾ��");
			return;
		}
		
		Side sd = nodes[i].adjList;
		while(sd != null)
		{
			j = 0;
			//Ѱ�ұ�ɾ���ڵ�֮����Ѱ���ڽ��б��ϵľ���
			while(!nodes[j].empty())
			{
				if(nodes[j].name.equals(sd.end.name))
				{
					//������ڽӱ��ϵ�һ���ڵ㣬��ֱ������������ɾ���ڵ�ı�
					Side sd1 = nodes[j].adjList;
					if(sd1.end.name.equals(s))
					{
						nodes[j].adjList = nodes[j].adjList.linked;
						nodes[j].bif_Num--;
						break;
					}
					//��������
					while(sd1.linked != null)
					{
						if(sd1.linked.end.name.equals(s))
						{
							sd1.linked = sd1.linked.linked;//����������ɾ���ڵ�ı�
							nodes[j].bif_Num--;
							break;
						}
						sd1 = sd1.linked;
						
					}
		
				}
				
				j++;
			}		
			sd = sd.linked;
		}
		
		while(!nodes[i].empty())
		{	
			nodes[i] = nodes[++i];//�ھ�����������ɾ����ɾ���ľ���
		}
		
		System.out.println("����"+s+"�Ѿ��ɹ�ɾ��!");
		//�����ļ�����д"information.txt"��"side.txt"
		BufferedWriter bw = new BufferedWriter(new FileWriter("information.txt"));	
		for(int t = 0;!nodes[t].empty();t++)
		{
			bw.write(nodes[t].name+" "+nodes[t].introduction+" "+nodes[t].popularity+" "+nodes[t].restArea+" "+nodes[t].toilet+"\n");	
		}
		bw.close();
		
		BufferedReader fr = new BufferedReader(new FileReader("side.txt"));
		String[] sb = new String[50];
		String temp = fr.readLine();
		int r = 0;
	
		while(temp!=null)
		{
			if(!temp.contains(s))
			{
				sb[r] = temp;
				r++;
				temp = fr.readLine();
			}else {
				temp = fr.readLine();
			}
		}
		fr.close();
		BufferedWriter bw1 = new BufferedWriter(new FileWriter("side.txt"));
		for(int h = 0;sb[h]!=null;h++)
		{
			bw1.write(sb[h]+"\n");
		}
		bw1.close();
		
	}
	/**
	 * ɾ��������·����
	 * @throws IOException
	 */
	public void deleteSide() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("��ֱ������һ���͵ڶ������������: ");//�м��ÿո�ֿ�
		
		String s = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(s," ");
		String str1 = st.nextToken();
		String str2 = st.nextToken();
		
		int i = 0;
		int a1 = 0;
		int a2 = 0;
		
		while(!nodes[i].empty())
		{
			if(nodes[i].name.equals(str1))
			{
				a2++;
				break;
			}
			i++;
		}
		
		if(a2 == 0)//�жϾ���1�Ƿ�����ھ���������
		{
			System.out.println("���뾰��1����!");
			return;
		}
		
		Side sd1 = nodes[i].adjList;
		
		if(sd1.end.name.equals(str2))
		{//������ڽӱ��ϵ�һ���ڵ㣬��ֱ������������ɾ���ڵ�ı�
			nodes[i].adjList = nodes[i].adjList.linked;
			nodes[i].bif_Num--;
			a1++;
			a2++;
		}
		//����Ѱ��
		while(a1 == 0&&sd1.linked != null)
		{
			if(sd1.linked.end.name.equals(str2))
			{
				sd1.linked = sd1.linked.linked;//����Ҫɾ���ı�
				nodes[i].bif_Num--;
				a2++;
				break;
			}
			sd1 = sd1.linked;

		}
		
		if(a2 == 1)
		{//���������������
			System.out.println("�����߲�����!");
			return;
		}
		//����Ѱ����һ�������ʱ�򷽷���Ѱ�ҵ�һ��������ͬ	
		i = 0;
		a1 = 0;
		while(!nodes[i].empty())
		{
			if(nodes[i].name.equals(str2))
				{
					break;
				}
			
			i++;
		}
			
		Side sd2 = nodes[i].adjList;	
		if(sd2.end.name.equals(str1))
		{
			nodes[i].adjList = nodes[i].adjList.linked;
			nodes[i].bif_Num--;
			a1++;
		}
			
		while(a1 == 0&&sd2.linked != null)
		{
			if(sd2.linked.end.name.equals(str1))
			{
				sd2.linked = sd2.linked.linked;
				nodes[i].bif_Num--;
				break;
			}
			sd2 = sd2.linked;
		}
		System.out.println("�ɹ�ɾ����"+str1+"��"+str2+"�ı�!");	
				
		BufferedReader br2 = new BufferedReader(new FileReader("side.txt"));	
		int k = 0;
		String[] string = new String[50];
		String temp = br2.readLine();
		while(temp!=null)
		{
			if(!temp.contains(str1+"����"+str2)&&!temp.contains(str2+"����"+str1))
			{
				string[k] = temp;
				k++;
				temp = br2.readLine();
			}else {
				temp = br2.readLine();
			}
		}
		br2.close();
		
		BufferedWriter bw = new BufferedWriter(new FileWriter("side.txt"));
		
		for(int u = 0;string[u] != null;u++)
		{
			bw.write(string[u]+"\n");
		}
		bw.close();
	}
	/**
	 * ���Ҿ�������
	 */
	public void seek()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("������Ҫ���ҵĹؼ���(����:����,��������,���޲���,������Ϣ��): ");
		String s = scanner.nextLine();
		int i = 0;
		int count = 0;
		String has_Toilet = "";
		String has_RestArea = "";
		boolean isFound = false;
		Pattern p = Pattern.compile(s);

		while(!nodes[i].empty())
		{
			if(nodes[i].toilet)
			{
				has_Toilet = "�в���";
			}else{
				has_Toilet = "�޲���";
			}
			
			if(nodes[i].restArea)
			{
				has_RestArea = "����Ϣ��";
			}else {
				has_RestArea = "����Ϣ��";
			}							
			Matcher m = p.matcher(nodes[i].name + nodes[i].introduction + has_Toilet + has_RestArea);
			isFound = m.find();
			if(isFound)
			{
				count++;
				System.out.println("�ɹ�ƥ�����о���: ");
				System.out.println("����"+count+": ");
				System.out.println("ƥ�侰������: "+nodes[i].name);
				System.out.println("ƥ�侰������: "+nodes[i].introduction);
				System.out.println("�Ƿ��в���: "+has_Toilet);
				System.out.println("�Ƿ�����Ϣ��: "+has_RestArea);
				System.out.println("***********************************");
				i++;
			}else {
				i++;
			}		
		}		
		if(count == 0)
		{
			System.out.println("û���ҵ�ƥ�侰��!");
		}	
	}
	/**
	 * �����в�������Ȼ�󷵻ػ��ֵı�׼
	 * @param a
	 * @param low
	 * @param high
	 * @param s
	 * @return
	 */
	public int re_Partition(Node[] a,int low,int high,String s)//s�������ݰ�����һ�ַ�������
	{
		int base_Position = low;//��׼λ��
		Node curr = a[low];
		a[low] = a[(high+low)/2];
		a[(high+low)/2] = curr;
		Node n = a[low];
		
		for(int i = low+1;i <= high;i++)
		{
			if(s.equals("popularity"))
			{
				if(a[i].popularity<n.popularity&&++base_Position != i)
				{//��С�ڱ�׼ֵ�Ļ��ֵ����ȥ
					curr = a[i];
					a[i] = a[base_Position];
					a[base_Position] = curr;
				}
				
			}else {
				if(a[i].bif_Num<n.bif_Num&&++base_Position != i)
				{//��С�ڱ�׼ֵ�Ļ��ֵ����ȥ
					curr = a[i];
					a[i] = a[base_Position];
					a[base_Position] = curr;							
				}
			}
		}	
		curr = a[low];
		a[low] = a[base_Position];
		a[base_Position] = curr;
				
		return base_Position;//���ػ��ֻ�׼	
	}
	/**
	 * ���ŷ���
	 * @param a
	 * @param first
	 * @param last
	 * @param s
	 */
	void quickSort(Node[] a,int first,int last,String s)
	{
		if(last > first)
		{
			int pivotIndex = re_Partition(a,first,last,s);
			quickSort(a,first,pivotIndex - 1,s);//��׼����ţ��ݹ�
			quickSort(a,pivotIndex + 1,last,s);//��׼�Ҳ��ţ��ݹ�			
		}
	}
	/**
	 * �����ܻ�ӭ������
	 */
	public void popularity_Sort()
	{
		Node[] nodes1 = new Node[50];
		String flag = "popularity";
		for(int i = 0;i<50;i++)
		{
			nodes1[i] = new Node();			
		}
			
		int j = 0;
		while(!nodes[j].empty())
		{
			nodes1[j].name = nodes[j].name;
			nodes1[j].popularity = nodes[j].popularity;
			j++;
		}
		
		quickSort(nodes1,0,j - 1,flag);
		
		int k = 0;
		
		System.out.println("�����ܻ�ӭ�ȴӵ͵��ߵ�����Ϊ: ");
		while(!nodes1[k].empty())
		{
			System.out.print(nodes1[k].name +"("+nodes1[k].popularity+")"+" ");
			k++;
		}
			
		System.out.println();	
	}
	
	/**
	 * ���ݲ�·��������
	 */
	public void branch_Num_Sort()
	{
		Node[] nodes2 = new Node[50];
		String flag = "branch_Num_Sort";
		for(int k = 0;k<50;k++)
		{
			nodes2[k] = new Node();			
		}
		
		int i = 0;
		while(!nodes[i].empty())
		{
			nodes2[i].name = nodes[i].name;
			nodes2[i].bif_Num = nodes[i].bif_Num;
			i++;
		}
		
		quickSort(nodes2,0,i - 1,flag);
		
		i = 0;
		
		System.out.println("���ղ�·����Ŀ���ٵ��������Ϊ: ");
		while(!nodes2[i].empty())
		{
			System.out.print(nodes2[i].name+"("+nodes2[i].bif_Num+")");
			i++;
		}
			
		System.out.println();	
	}
	
	
	/**
	 * ����prim�㷨������С��������ǰ�������С��������������̹��ܶ��ٻ�·
	 * @throws IOException
	 */
	public void prim_Algorithm() throws IOException
	{
		String s = null;
		System.out.println("ѡ��������ʽ:1.ѡ��һ����Ϊ�����յ�2.�Զ��������յ�");
		Scanner choice = new Scanner(System.in);
		String choose = choice.nextLine();
		String destination = null;
		//ѡ�����ɷ�ʽ
		if(choose.equals("1"))
		{
			System.out.println("�����뿪ʼ��������:");
			Scanner scanner1 = new Scanner(System.in);
			s = scanner1.nextLine();
		}
		if(choose.equals("2"))
		{
			System.out.println("��ֱ����������յ�:");
			Scanner scanner2 = new Scanner(System.in);
			String temp = scanner2.nextLine();
			StringTokenizer st = new StringTokenizer(temp," ");
			String start = st.nextToken();
			s = start;
			destination = st.nextToken();
		}
		
		int i = 0,j = 0;
		while(!nodes[i].empty())
		{
			i++;
		}
		
		MinTree mst = new MinTree(i);

		int vertices  = i;//�������(ͼ����ĸ���)
		int[] nearBy = new int[vertices];		
		int[] min_Weight = new int[vertices];
		int count = 0;//��¼��������ھ��������е�λ��
		boolean stamp = false;//�ж��Ƿ������������
		
		while(!nodes[count].empty())
		{
			if(nodes[count].name.equals(s))
			{
				stamp = true;
				break;
			}
			count++;
		}
		
		if(!stamp)
		{//������㲻����
			System.out.println("���뾰������!");
			return;
		}
		int flag = 0;
		
		for(i = 0;i<vertices;i++)
		{
			flag = 0;
			Side sd1 = nodes[count].adjList;
			while(sd1 != null)
			{
				if(nodes[i].name.equals(sd1.end.name))
				{
					min_Weight[i] = sd1.weight;//�þ��㵽�����ߵ�cost
					flag++;
					break;
				}
				sd1 = sd1.linked;			
			}
			
			if(flag == 0)
			{
				min_Weight[i] = 32767;
			}
			nearBy[i] = count;
		}
		//��Ӹ������㵽�������ļ���
		nearBy[count] = -1;
		min_Weight[count] = 0;
		
		for(i = 1;i<vertices;i++)
		{//��ӱ�
			int min = 32767;
			int vts = 0;
			for(j = 0;j<vertices;j++)
			{
				if(nearBy[j]!=-1&&min_Weight[j]<min)
				{
					vts = j;
					min = min_Weight[j];
				}
			}
			//���������ⶥ�㵽�������ڶ��������СȨֵ�ı�, vts�ǵ�ǰ����СȨֵ�ıߵ�λ��
			if(vts!=count)
			{//���vts == count�������Ҳ�������ҵ���Ӧ�ľ�����
				if(nearBy[vts]!=-1)
				{//�������߼�����С������
					mst.add(new MSTSideNode(nearBy[vts],vts,min_Weight[vts]));
				}
				
				nearBy[vts] = -1;//������ı������
				for(j = 0;j<vertices;j++)
				{
					if(j == count)
					{continue;}
					
					Side sd1 = nodes[vts].adjList;
					int curr = 32767;
					while(sd1!=null)
					{
						if(nodes[j].name.equals(sd1.end.name))
						{
							curr = sd1.weight;
							break;
						}
						sd1 = sd1.linked;	
					}
					if(nearBy[j]!=-1&&curr<min_Weight[j])//�޸�
					{
						min_Weight[j] = curr;
						nearBy[j] = vts;
					}					
				}
			}
		}		
		System.out.println("Ϊ�����ɵĵ���·��ͼΪ: ");
		if(choose.equals("1"))//����1ʵ��һ��������ͬ���յ�
		{
			System.out.print(nodes[count].name+"����������");
			for(int k = 0;k<mst.size;k++)
			{
				System.out.print(nodes[mst.sides[k].getHead()].name+"����������");
			}
			
			System.out.print(nodes[count].name);
			System.out.println("\n");	
			BinaryTree bTree = new BinaryTree(new TreeNode(count));
		}
		
		if(choose.equals("2"))//����2ʵ��һ����㲻ͬ���յ�
		{
			System.out.print(nodes[count].name+"����������");
			for(int p = 0;p<mst.size;p++)
			{
				if(!nodes[mst.sides[p].getHead()].name.equals(destination))
				{
					System.out.print(nodes[mst.sides[p].getHead()].name+"����������");
				}else {
					continue;
				}
			}
			System.out.print(destination);
			System.out.println("\n");
			BinaryTree bTree = new BinaryTree(new TreeNode(count));
		}
		
	}
	
}