package experiment;
/**
 * 生成树类
 * @author 李远识
 *
 */
public class MinTree {
	MSTSideNode[] sides;
	int size;
	public MinTree(int i) {
		sides = new MSTSideNode[i - 1];
		size = 0;
	}

	public void add(MSTSideNode i)
	{
		sides[size] = i;
		size++;		
	}
	
}
