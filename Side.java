package experiment;
/**
 * ����
 * @author ��Զʶ
 *
 */
public class Side {
	int weight;//�ߵ�Ȩ��
	Node start;//�ߵ����
	Node end;//�ߵ��յ�
	Side linked;//ָ����һ��
	
	public Side(Node s,Node e,int w)
	{
		start = s;
		end = e;
		weight = w;
		linked = null;
	}
}

