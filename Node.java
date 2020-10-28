package experiment;

public class Node {
	
	String name;//��������
	String introduction = "";//��������
	int popularity = 0;//�ܻ�ӭ��
	int bif_Num = 0;//��·����Ŀ
	boolean restArea = false;//�Ƿ�����Ϣ��
	boolean toilet = false;//�Ƿ��в���
	Side adjList;//�ڽӱ�
	Side rear;//�ڽӱ�β��
	/**
	 * ������Dijkstra
	 */
	int cost;//Dijkstra��Floyd�㷨�м�¼��ǰ��С����
	Node beforeNode;//�˽ڵ��ǰһ���ڵ�
	boolean isShort;//�Ƿ�Ϊ��ǰ���·����־
	
	
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
