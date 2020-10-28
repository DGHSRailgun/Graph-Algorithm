package experiment;

public class Node {
	
	String name;//景点名称
	String introduction = "";//景区介绍
	int popularity = 0;//受欢迎度
	int bif_Num = 0;//岔路口数目
	boolean restArea = false;//是否有休息区
	boolean toilet = false;//是否有厕所
	Side adjList;//邻接表
	Side rear;//邻接表尾部
	/**
	 * 下面是Dijkstra
	 */
	int cost;//Dijkstra和Floyd算法中记录当前最小开销
	Node beforeNode;//此节点的前一个节点
	boolean isShort;//是否为当前最短路径标志
	
	
	public Node()
	{
		name = "non"; 
		adjList = null;
		rear = adjList;
		beforeNode = null;
		cost = 0;
		isShort = false;
	}
	
	public Node(String n)
	{
		name = n;
		beforeNode = null;
		isShort = false;
		cost = Integer.MAX_VALUE;
	}
	
	public Node(String n,Side s,Side e)
	{
		name = n;
		adjList = s;
		rear = e;
		beforeNode = null;
		isShort = false;
		cost = Integer.MAX_VALUE;
	}
	
	public boolean empty()
	{
		if(this.name.equals("non"))
			return true;
		else
			return false;		
	}
	
	public boolean equal(Node n)
	{
		if(this.name.equals(n.name))
			return true;
		else
			return false;
	}
		
}
