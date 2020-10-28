package experiment;

import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Graph {
	Node[] nodes = new Node[50];//用来存放景点(图节点)
	String s1 = "";//用来存储Dijkstra和Floyd算法中的起点和终点
	String s2 = "";//用来存储Dijkstra和Floyd算法中的起点和终点
	/**
	 * 将边文件"side.txt"读进数组
	 * @param e
	 */
	public void readedge(String e)
	{
		//e是文件名称
		for(int i = 0;i<50;i++)
		{
		nodes[i] = new Node();	
		}
		//初始化
		FileReader fr = null;
		try {
			fr = new FileReader(e);					
		}catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
		
		Scanner s = new Scanner(fr);
		
		int j,k = 0;
		//按行读
		while(s.hasNextLine())
		{
			j = 0;
			k = 0;
			String  b = s.nextLine();
			StringTokenizer st = new StringTokenizer(b,"——");
			Node x = new Node(st.nextToken());
			Node y = new Node(st.nextToken());
			while(!nodes[j].empty())
			{
				if(nodes[j].equal(x))//判断景点是否存在
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
			
			if(k == 0)//不存在则读入
			nodes[j] = x;
			String p = st.nextToken();////把边加到邻接表
			if(nodes[j].adjList == null)
			{//如果是空的
				nodes[j].adjList = new Side(x,y,Integer.parseInt(p));
				nodes[j].rear = nodes[j].adjList;
				nodes[j].bif_Num = 1+nodes[j].bif_Num;	
			}else {//如果已存在景点添加到尾部
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
			//给另一个景点添加边
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
	 * 将景点信息文件information.txt读进数组
	 * @param i
	 */
	public void readinfo(String i)
	{
		//i是文件名
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
	 * 生成邻接矩阵第一行
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
			s = nodes[i].name+"\t";//列名
			sb.append(s);
		}
		
		sb.append("\n");
		
		i = 0;
		
		while(!nodes[i].empty())
		{

			s = nodes[i].name+"\t";//行名
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
	
	//初始化节点属性	
	public void resetAttribute()
	{
		for(int i = 0;!this.nodes[i].empty();i++)
			this.nodes[i] = new Node(this.nodes[i].name,this.nodes[i].adjList,this.nodes[i].rear);
	}
	/**
	 * 这是寻找最短路径和最短距离的Dijkstra算法
	 */
	public void dijkstra_Algorithm()
	{
		System.out.println("输入起点和终点: ");
		Scanner s = new Scanner(System.in);
		s1 = s.next();
		s2 = s.next();
		int i = 0,j = 0;
		int count = 0,k = 0;
		int moving = 0;
		int temp = 0;
		int stamp = 0;
		while(!nodes[k].empty())//判断起点是否存在
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
			System.out.println("输入起点有误: ");
			return;
		}
		
		while(x != null)
		{
			moving = 0;
			while(!nodes[moving].empty())//初始化
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
		
		for(i = 0;i <= count-2;i++)//这个循环每次寻找一个节点的最短路径
		{
			int min_cost = Integer.MAX_VALUE;
			for(j = 0;j < count;j++)//设置"已找到"
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
							//如果找到更小的，更新节点的cost
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
	 * 这是寻找最短路径和最短距离的Floyd算法
	 * 由于在存储的时候Node节点的特殊性，里面有beforeNode的属性，因此此方法类似于Dijkstra算法
	 */
	public void floyd_Algorithm()
	{
		System.out.println("输入起点和终点: ");
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
			System.out.println("输入起点有误: ");
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
	 * 创建最短路径，生成最短距离
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
			System.out.println("输入终点有误: ");
			return "wrong";
		}
		
		String string = s2;
		Node n = nodes[point];
		
		while(!n.name.equals(s1))
		{
			string = n.beforeNode.name + " →→→→→ " + string;
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
		return "最短路线为: "+string+"\n"+"从"+s1+"到"+s2+"的最短距离为: "+nodes[i].cost;
	}
	/**
	 * 添景点的方法	
	 * @throws IOException
	 */
	public void addNode() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要增加的景点名称: ");
		String s = scanner.nextLine();
		int i = 0;
		s = s.replaceAll(" ","");
		while(!nodes[i].empty())
		{
			if(nodes[i].name.equals(s))
			{
				System.out.println("该景点已经存在!");
				return;
			}
			i++;
		}
		nodes[i] = new Node(s);
		System.out.println("成功增加景区"+s+"!");
		//将新添加的景点写入文件中
		BufferedWriter bw = new BufferedWriter(new FileWriter("information.txt"));
		for(int m = 0;!nodes[m].empty();m++)
		{
			bw.write(nodes[m].name+" "+nodes[m].introduction+" "+nodes[m].popularity+" "+nodes[m].restArea+" "+nodes[m].toilet+"\n");		
		}
		bw.close();	
	}
	/**
	 * 添加景区道路的方法
	 * @throws IOException
	 */
	public void addSide() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入第一个、第二个景点的名称和权重值: ");
		String s = scanner.nextLine();
		StringTokenizer st = new StringTokenizer(s," ");//用空格分开
		String str1 = st.nextToken();
		String str2 = st.nextToken();
		int count_Weight;
		try {
			count_Weight = Integer.parseInt(st.nextToken());//用来新边记录权重
		}catch(Exception e) {
			System.out.println("请正确输入!");
			return;
		}
		
		
		int i = 0;
		int j = 0;
		int stamp = 0;
		//判断新添加的边是否已经存在
		while(!nodes[i].empty())
		{//遍历节点数组寻找起点
			if(nodes[i].name.equals(str1)) 
			{
				break;
			}
			i++;
		}
		
		Side sd = nodes[i].adjList;
		
		while(sd != null)
		{//寻找起点的邻接表里面是否有终点
			if(sd.end.name.equals(str2))
			{
				stamp++;
				break;
			}
			sd = sd.linked;
			
		}
		
		if(stamp > 0)
		{//如果这条边已经存在
			System.out.println("这条边已经存在，无需添加!");
			return;
		}
		
		while(!nodes[j].empty())
		{//在景点数组里面寻找终点的位置
			if(nodes[j].name.equals(str2))
			{
				break;
			}
			j++;
		}
		
		if(nodes[i].adjList == null)
		{//如果邻接表为空，直接添加
			nodes[i].adjList = new Side(nodes[i],nodes[j],count_Weight);
			nodes[i].rear = nodes[i].adjList;
			nodes[i].bif_Num++;
		}else {//如果不为空，添加到邻接表结尾
			nodes[i].rear.linked = new Side(nodes[i],nodes[j],count_Weight);
			nodes[i].rear = nodes[i].rear.linked;
			nodes[i].bif_Num++;		
		}
		
		if(nodes[j].adjList == null)
		{//由于无向图，给终点添加边元素
			nodes[j].adjList = new Side(nodes[j],nodes[i],count_Weight);
			nodes[j].rear = nodes[j].adjList;
			nodes[j].bif_Num++;
		}else {//同上，如果终点的邻接表不为空，则添加到结尾
			nodes[j].rear.linked = new Side(nodes[j],nodes[i],count_Weight);
			nodes[j].rear = nodes[j].rear.linked;
			nodes[j].bif_Num++;		
		}
		
		System.out.println("添加成功!");
		//将边添加到"side.txt"文件里面
		BufferedWriter bw = new BufferedWriter(new FileWriter("side.txt",true));
		bw.write("\n"+str1+"——"+str2+"——"+count_Weight+"\n");
		bw.close();	
	}
	/**
	 * 删除景点方法
	 * @throws IOException
	 */
	public void deleteNode() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入正在维护的景点: ");
		String s = scanner.nextLine();
		
		int i = 0,j = 0;
		boolean isFound = false;
		while(!nodes[i].empty())
		{//遍历景点数组寻找要删除的景点
			if(nodes[i].name.equals(s)) 
			{
				isFound = true;
				break;
			}
			i++;
		}
		
		if(!isFound)
		{//如果景点不存在
			System.out.println("该景点不存在或者已经删除");
			return;
		}
		
		Side sd = nodes[i].adjList;
		while(sd != null)
		{
			j = 0;
			//寻找被删除节点之后，再寻找邻接列表上的景点
			while(!nodes[j].empty())
			{
				if(nodes[j].name.equals(sd.end.name))
				{
					//如果在邻接表上第一个节点，则直接跳过包含被删除节点的边
					Side sd1 = nodes[j].adjList;
					if(sd1.end.name.equals(s))
					{
						nodes[j].adjList = nodes[j].adjList.linked;
						nodes[j].bif_Num--;
						break;
					}
					//继续查找
					while(sd1.linked != null)
					{
						if(sd1.linked.end.name.equals(s))
						{
							sd1.linked = sd1.linked.linked;//跳过包含被删除节点的边
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
			nodes[i] = nodes[++i];//在景点数组里面删除被删除的景点
		}
		
		System.out.println("景点"+s+"已经成功删除!");
		//更新文件，重写"information.txt"和"side.txt"
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
	 * 删除景区道路方法
	 * @throws IOException
	 */
	public void deleteSide() throws IOException
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请分别输入第一个和第二个景点的名称: ");//中间用空格分开
		
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
		
		if(a2 == 0)//判断景点1是否存在于景点数组中
		{
			System.out.println("输入景点1有误!");
			return;
		}
		
		Side sd1 = nodes[i].adjList;
		
		if(sd1.end.name.equals(str2))
		{//如果在邻接表上第一个节点，则直接跳过包含被删除节点的边
			nodes[i].adjList = nodes[i].adjList.linked;
			nodes[i].bif_Num--;
			a1++;
			a2++;
		}
		//继续寻找
		while(a1 == 0&&sd1.linked != null)
		{
			if(sd1.linked.end.name.equals(str2))
			{
				sd1.linked = sd1.linked.linked;//跳过要删除的边
				nodes[i].bif_Num--;
				a2++;
				break;
			}
			sd1 = sd1.linked;

		}
		
		if(a2 == 1)
		{//如果不存在这条边
			System.out.println("这条边不存在!");
			return;
		}
		//下面寻找下一个景点的时候方法与寻找第一个景点相同	
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
		System.out.println("成功删除从"+str1+"到"+str2+"的边!");	
				
		BufferedReader br2 = new BufferedReader(new FileReader("side.txt"));	
		int k = 0;
		String[] string = new String[50];
		String temp = br2.readLine();
		while(temp!=null)
		{
			if(!temp.contains(str1+"——"+str2)&&!temp.contains(str2+"——"+str1))
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
	 * 查找景区方法
	 */
	public void seek()
	{
		Scanner scanner = new Scanner(System.in);
		System.out.println("请输入要查找的关键字(比如:名称,景区介绍,有无厕所,有无休息区): ");
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
				has_Toilet = "有厕所";
			}else{
				has_Toilet = "无厕所";
			}
			
			if(nodes[i].restArea)
			{
				has_RestArea = "有休息区";
			}else {
				has_RestArea = "无休息区";
			}							
			Matcher m = p.matcher(nodes[i].name + nodes[i].introduction + has_Toilet + has_RestArea);
			isFound = m.find();
			if(isFound)
			{
				count++;
				System.out.println("成功匹配下列景区: ");
				System.out.println("景区"+count+": ");
				System.out.println("匹配景区名称: "+nodes[i].name);
				System.out.println("匹配景区介绍: "+nodes[i].introduction);
				System.out.println("是否有厕所: "+has_Toilet);
				System.out.println("是否有休息区: "+has_RestArea);
				System.out.println("***********************************");
				i++;
			}else {
				i++;
			}		
		}		
		if(count == 0)
		{
			System.out.println("没有找到匹配景区!");
		}	
	}
	/**
	 * 快排中部分排序然后返回划分的标准
	 * @param a
	 * @param low
	 * @param high
	 * @param s
	 * @return
	 */
	public int re_Partition(Node[] a,int low,int high,String s)//s决定根据按照哪一种分类排序
	{
		int base_Position = low;//标准位置
		Node curr = a[low];
		a[low] = a[(high+low)/2];
		a[(high+low)/2] = curr;
		Node n = a[low];
		
		for(int i = low+1;i <= high;i++)
		{
			if(s.equals("popularity"))
			{
				if(a[i].popularity<n.popularity&&++base_Position != i)
				{//将小于标准值的划分到左侧去
					curr = a[i];
					a[i] = a[base_Position];
					a[base_Position] = curr;
				}
				
			}else {
				if(a[i].bif_Num<n.bif_Num&&++base_Position != i)
				{//将小于标准值的划分到左侧去
					curr = a[i];
					a[i] = a[base_Position];
					a[base_Position] = curr;							
				}
			}
		}	
		curr = a[low];
		a[low] = a[base_Position];
		a[base_Position] = curr;
				
		return base_Position;//返回划分基准	
	}
	/**
	 * 快排方法
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
			quickSort(a,first,pivotIndex - 1,s);//基准左侧排，递归
			quickSort(a,pivotIndex + 1,last,s);//基准右侧排，递归			
		}
	}
	/**
	 * 根据受欢迎度排序
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
		
		System.out.println("按照受欢迎度从低到高的排序为: ");
		while(!nodes1[k].empty())
		{
			System.out.print(nodes1[k].name +"("+nodes1[k].popularity+")"+" ");
			k++;
		}
			
		System.out.println();	
	}
	
	/**
	 * 根据岔路口数排序
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
		
		System.out.println("按照岔路口数目从少到多的排序为: ");
		while(!nodes2[i].empty())
		{
			System.out.print(nodes2[i].name+"("+nodes2[i].bif_Num+")");
			i++;
		}
			
		System.out.println();	
	}
	
	
	/**
	 * 根据prim算法生成最小生成树，前序遍历最小生成树，生成最短哈密尔顿回路
	 * @throws IOException
	 */
	public void prim_Algorithm() throws IOException
	{
		String s = null;
		System.out.println("选择游览方式:1.选择一点作为起点和终点2.自定义起点和终点");
		Scanner choice = new Scanner(System.in);
		String choose = choice.nextLine();
		String destination = null;
		//选择生成方式
		if(choose.equals("1"))
		{
			System.out.println("请输入开始景点名称:");
			Scanner scanner1 = new Scanner(System.in);
			s = scanner1.nextLine();
		}
		if(choose.equals("2"))
		{
			System.out.println("请分别输入起点和终点:");
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

		int vertices  = i;//景点个数(图顶点的个数)
		int[] nearBy = new int[vertices];		
		int[] min_Weight = new int[vertices];
		int count = 0;//记录输入起点在景点数组中的位置
		boolean stamp = false;//判断是否存在输入的起点
		
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
		{//如果景点不存在
			System.out.println("输入景点有误!");
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
					min_Weight[i] = sd1.weight;//该景点到各个边的cost
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
		//添加各个景点到生成树的集合
		nearBy[count] = -1;
		min_Weight[count] = 0;
		
		for(i = 1;i<vertices;i++)
		{//添加边
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
			//求生成树外顶点到生成树内顶点具有最小权值的边, vts是当前具最小权值的边的位置
			if(vts!=count)
			{//如果vts == count则代表再也不可能找到对应的景点了
				if(nearBy[vts]!=-1)
				{//将这条边加入最小生成树
					mst.add(new MSTSideNode(nearBy[vts],vts,min_Weight[vts]));
				}
				
				nearBy[vts] = -1;//将加入的边做标记
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
					if(nearBy[j]!=-1&&curr<min_Weight[j])//修改
					{
						min_Weight[j] = curr;
						nearBy[j] = vts;
					}					
				}
			}
		}		
		System.out.println("为您生成的导游路线图为: ");
		if(choose.equals("1"))//功能1实现一个起点和相同的终点
		{
			System.out.print(nodes[count].name+"→→→→→");
			for(int k = 0;k<mst.size;k++)
			{
				System.out.print(nodes[mst.sides[k].getHead()].name+"→→→→→");
			}
			
			System.out.print(nodes[count].name);
			System.out.println("\n");	
			BinaryTree bTree = new BinaryTree(new TreeNode(count));
		}
		
		if(choose.equals("2"))//功能2实现一个起点不同的终点
		{
			System.out.print(nodes[count].name+"→→→→→");
			for(int p = 0;p<mst.size;p++)
			{
				if(!nodes[mst.sides[p].getHead()].name.equals(destination))
				{
					System.out.print(nodes[mst.sides[p].getHead()].name+"→→→→→");
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