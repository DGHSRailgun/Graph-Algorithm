package experiment;
/**
 * 边类
 * @author 李远识
 *
 */
public class Side {
	int weight;//边的权重
	Node start;//边的起点
	Node end;//边的终点
	Side linked;//指向下一条
	
	public Side(Node s,Node e,int w)
	{
		start = s;
		end = e;
		weight = w;
		linked = null;
	}
}

