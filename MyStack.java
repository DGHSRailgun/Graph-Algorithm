package experiment;

import java.util.NoSuchElementException;
	
	class StackNode
	{
		int number;//车牌号
		int arrive_Time;//到达时间
		
		public StackNode()
		{
			number = 0;
			arrive_Time = 0;
		}
	
		public StackNode(int a,int b)
		{
			number = a;
			arrive_Time = b;			
		}
	
	}
	
public class MyStack {
	StackNode[] nodes;
	int top;
	public MyStack(int size)
	{
		nodes = new StackNode[size];
		top = -1;		
	}
	
	public void push(StackNode n)
	{
		if(isFull())
			throw new ArrayIndexOutOfBoundsException();	
		nodes[++top] = n;
	
	}
	
	public StackNode pop()
	{
		if(isEmpty())
			throw new NoSuchElementException();
		return nodes[top--];
	}
	
	public StackNode peek()
	{
		if(isEmpty())
			throw new NoSuchElementException();
		return nodes[top];				
	}
	
	public boolean isFull()
	{
		return top == nodes.length - 1;
	}
	
	public boolean isEmpty()
	{
		return top == -1; 
	}
	
	public void clear()
	{
		top = -1;
	}

}
