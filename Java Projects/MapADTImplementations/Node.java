//basically just designing a new type of node to built our binary tree with
//this is kind of an all-encompassing class that I was going to use for all data structures
public class Node{
	public int key;
	private int value;
	public Node parent;
	public Node left;
	public Node right;
	public Node next;
	public int height;
	//public Node sentinel;
	public static Node freshestNewkid;
	
	public Node(int key,int value) {
		this.key = key;
		this.value = value;
		//this.parent = null;
		//this.next = null;
		//this.right = null;
		//this.left = null;
		//this.height = 0;
		//parent=left=right = sentinel;
	  }
	
	public boolean hasNext(){
		Node c = null;
		c = this.next;
		if(c!=null){return true;}
		else return false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	public int getheight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}
	
	public Node getkeyNode(int key){
		if(this.key == key){
			return this;
		}
		else if(this.key>key && this.left!= null){
			return this.left.getkeyNode(key);
		}
		else{
			if(this.right!=null){
				return this.right.getkeyNode(key);
			}
			else return null;
		}
	}

}