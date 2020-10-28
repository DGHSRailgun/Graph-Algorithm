package experiment;
/**
 * 二叉树的节点
 * @author 李远识
 *
 */
public class TreeNode {
	TreeNode left;
	TreeNode right;
	int data;
	public TreeNode(int w,TreeNode x,TreeNode y)
	{
		data = w;
		left = x;
		right = y;
	}
	
	public TreeNode(int w)
	{
		data = w;
		
	}
}



