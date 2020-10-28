package experiment;

/**
 * 生成树的边节点
 * @author 李远识
 *
 */
public class MSTSideNode {
	private int head = 0;
	private int weight = 0;
	private int last = 0;
	public int getHead() {
		return head;
	}

	public void setHead(int head) {
		this.head = head;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getLast() {
		return last;
	}

	public void setLast(int last) {
		this.last = last;
	}
	
	public MSTSideNode(int a,int b,int c)
	{
		last = a;
		head = b;
		weight = c;
	}
	
}
