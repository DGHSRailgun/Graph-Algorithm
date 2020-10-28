package experiment;
	
import java.util.NoSuchElementException;
	
public class MyQueue {
	
	class queueNode
	{
		int number;//车牌号
		int arrive_Time;//到达时间
		queueNode next;//队列的下一项
		
		public queueNode()
		{
			number = 0;
			arrive_Time = 0;
	
		}
		
		public queueNode(int a, int b,queueNode c)
		{
			arrive_Time = b;
			number = a;
			next = c;
		}
		
	}

	queueNode front;
	queueNode rear;
	
	public MyQueue()
	{
		rear = front = null;
	}
	
	public boolean isEmpty()
	{
		return front == null;
	}
	
	public void enQueue(int a,int b)
	{
		if(front == null)
			front = rear = new queueNode(a,b,null);
		else
			rear = rear.next = new queueNode(a,b,null);
	}
	
	public int deQueue()
	{
		if(isEmpty())
			throw new NoSuchElementException();
		queueNode q = front;
		int num = q.number;	
		front = front.next;
		if(front == null)
			rear = null;
		return num;
		
	}
	
	public int getFront()
	{
		if(isEmpty())
			throw new NoSuchElementException();	
		return front.number;	
	}
	
}