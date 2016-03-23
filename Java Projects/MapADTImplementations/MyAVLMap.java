public class MyAVLMap extends MyBSTnew implements MyMapADT{
	
	public MyAVLMap(){}
	
		public boolean insert(int k, int v){
			
			boolean latest = super.insert(k, v);
			//once you have inserted the node you update the positions and heights and balance
			BSTNode Z = search(k);
			BSTNode parent = Z.parent;
			
			
			while(parent !=sentinel){
				if(balanced(parent)){
					parent = parent.parent;
				}
				
				else{
					balance(parent);
					break;
				}
			}
			
			return latest;
		}
		
		public boolean balanced(BSTNode n){
			return Math.abs(n.left.height-n.right.height) <=1;
		}
		
		public void balance(BSTNode Z){
			BSTNode Y= null;
			BSTNode X = null;
			if(Z.left.height>Z.right.height){
				Y = Z.left;
			}
			else{
				Y = Z.right;
			}
			if(Y.right.height>Y.left.height){
				X = Y.right;
			}
			else{
				X = Y.left;
			}
			
			if (Y==Z.right && X == Y.right){leftRotate(Z);}
			else if (Y==Z.right && X == Y.left){rightRotate(Y); leftRotate(Z);}
			else if (Y==Z.left && X == Y.left){rightRotate(Z);}
			else if (Y==Z.left && X == Y.right){leftRotate(Y); rightRotate(Z);}
		}
		
		public void leftRotate(BSTNode parent){
			BSTNode grandParent = parent.parent;
			BSTNode root = parent;
			
			BSTNode R = root.right;
			BSTNode RR = root.right.right;
			BSTNode L = root.left;
			BSTNode RL = root.right.left;
			
			root.right = RL;
			RL.parent = root;
			
			root.height = 1+ Math.max(L.height, RL.height);
			
			R.left = root;
			root.parent = R;
			
			R.height = 1+ Math.max(root.height, RR.height);
			
			R.parent = grandParent;
			
			
			
			if(grandParent.left == root){grandParent.left = R;}
			
			else if (grandParent.right == root){
				grandParent.right = R;
			}
			
			else if (grandParent == sentinel){
				root = R;
				root.height = R.height;
			}
			
			if(grandParent!= sentinel && grandParent.height != (1+ 
					Math.max(grandParent.right.height, grandParent.left.height))){
				
				grandParent.height = (1+ Math.max(grandParent.right.height, grandParent.left.height));
				
			}
			
			};
		
		public void rightRotate(BSTNode parent){
			BSTNode grandParent = parent.parent;
			BSTNode root = parent;
			
			BSTNode L = root.left;
			@SuppressWarnings("unused")
			BSTNode LL = root.left.left;
			BSTNode R = root.right;
			BSTNode LR = root.left.right;
			
			root.left = LR;
			LR.parent = root;
			root.height = 1+ Math.max(R.height, LR.height);
			
			
			L.right = root;
			root.parent = L;
			L.height = 1+ Math.max(root.height, L.height);
			
			L.parent = grandParent;
			
			if(grandParent.left == root){
				grandParent.left = L;
			}
			
			else if (grandParent.right == root){grandParent.right = L;}
			
			else if (grandParent == sentinel){root = L;
			root.height = L.height;
			}
			
			if(grandParent!= sentinel && grandParent.height != (1+ 
					Math.max(grandParent.right.height, grandParent.left.height))){
				
				grandParent.height = (1+ Math.max(grandParent.right.height, grandParent.left.height));
				
			}
			
			};
		
		
		public RetVal find(int k){
			return super.find(k);
		}
		
		public String toString(){
			return super.toString();
		}
}