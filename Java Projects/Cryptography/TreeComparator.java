import java.util.Comparator;
//to compare frequency values to assist priority queue we are building
public class TreeComparator implements Comparator<BinaryTree<SingletonTrees>>{

	
	public int compare(BinaryTree<SingletonTrees> o1, BinaryTree<SingletonTrees> o2) {
		if (o1.getValue().getFreq()<o2.getValue().getFreq()){
			return -1;
		}
		else if (o1.getValue().getFreq()>o2.getValue().getFreq()){
			return 1;
		}
		else{
			return 0;
		}
	}
}