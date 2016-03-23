public class MyBSTnew implements MyMapADT{
	//Most of the code for this class is taken exactly from Prof. Jayanti's lectures
	//and modified to include height variable and modify the types of objects used for 
	//key and values
	
	public class BSTNode{
		public BSTNode parent;
		public BSTNode right;
		public BSTNode left;
		private int key;
		private int value;
		public int height;
		
		public BSTNode(int key, int value){
			this.key = key;
			this.value = value;
			this.height = 0;
			left = right = parent = sentinel;
		}
		
		/**
	     * @return the key of this Node
	     */
	    public int getKey() {
	      return key;
	    }
	    
	    /**
	     * @return the value in this Node
	     */
	    public int getValue() {
	      return value;
	    }
	    
	    /**
	     * Set the value in this Node.
	     */
	    public void setValue(int newValue) {
	      value = newValue;
	    }
	    /**
	     * @return the String representation of this Node
	     */
	    public String toString() {
	      return "" + key + ", " + this.height;
	    }
	}
	
	private BSTNode root;
	public BSTNode sentinel;
	private int entries;
	
	public MyBSTnew(){
		sentinel = new BSTNode(Integer.MAX_VALUE,Integer.MAX_VALUE);
		//sentinel.height = -1;
		root = sentinel;
		
	}
	
	public boolean insert(int k, int v) {
		if(search(k)!=null){
			BSTNode x = search(k);
			x.setValue(v);
			return false;
		}
		
		
		else{
			BSTNode z = new BSTNode(k,v);
			BSTNode x = root;
			BSTNode xParent = sentinel;
		
			while(x!= sentinel){
				xParent =x;
				if(k<x.key){
					x = x.left;
				}	
				else
					x = x.right;
			}	
		
			z.parent = xParent;
		
			if(xParent==sentinel){
				root = z;
			}
			else{
				if(k<xParent.key){
					xParent.left = z;
				}
				else{
					xParent.right = z;
				}
			}
			entries ++;
			fixHeight(z);
			return true;
		}
	}

	//uses the search method given in lectures
	public RetVal find(int k) {
		BSTNode x = search(k);
		if(x!=null){
			return new RetVal(true,x.getValue());
			}
		else {return new RetVal(false,0);}
	}
	
	public BSTNode search(int k) {
	    BSTNode x = root;

	    // Go down the left or right subtree until either we hit the sentinel or
	    // find the key.
	    while (x != sentinel && k!=x.key) {
	      if (k<x.key)
	        x = x.left;
	      else
	        x = x.right;
	    }

	    // If we got to the sentinel, the key was not in the BST.
	    if (x == sentinel)
	      return null;
	    else
	      return x;
	  }
	
	//method fixes height:
	
	public void fixHeight(BSTNode n){
		while(n != root && n.parent.height != n.height + 1){
			n.parent.height = 1 + n.height;
			n = n.parent;
		}
	}
	
	@Override
	public RetVal delete(int k) {
		// TODO Auto-generated method stub
		return null;
	}

	public int size() {
		return entries;
	}
	
	  /**
	   * Return a String representation of this BST, indenting each level by two
	   * spaces. Right subtrees appear before subtree roots, which appear before
	   * left subtrees, so that when viewed sideways, we see the BST structure.
	   */
	  public String toString() {
	    if (root == sentinel)
	      return "";
	    else
	      return print(root, 0);
	  }

	  /**
	   * Return a string of 2*s spaces, for indenting.
	   */
	  private String indent(int s) {
	    String result = "";
	    for (int i = 0; i < s; i++)
	      result += "  ";
	    return result;
	  }

	  /**
	   * Return a String representing the subtree rooted at a node.
	   * 
	   * @param x the root of the subtree
	   * @param depth the depth of x in the BST
	   * @return the String representation of the subtree rooted at x
	   */
	  private String print(BSTNode x, int depth) {
	    if (x == sentinel)
	      return "";
	    else
	      return print(x.right, depth + 1) + indent(depth) + x.toString() + "\n"
	          + print(x.left, depth + 1);
	  }
	
}