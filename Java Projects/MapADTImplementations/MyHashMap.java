import java.util.Random;

public class MyHashMap implements MyMapADT{

	private int tableSize;
	private Node[] hashTable;
	private int a_val, b_val;
	private Random a;
	private int spacesUsed;
	
	private final double LOAD_FACTOR = 0.5;
	private int entries;
	
	private final int P_TO_USE = 109345121;
	
	public MyHashMap(){
		spacesUsed = 0;
		this.tableSize = 11;
		hashTable = new Node[tableSize];
		this.entries = 0;
		
		a = new Random();
		a_val = a.nextInt(P_TO_USE-1) + 1; //calculate a random once at the start
		b_val = a.nextInt(P_TO_USE);
		
	}
	
	
	
	
public void checkLoadFactor(){ //to see if the table size needs to be doubled
		
		//System.out.println("" + spacesUsed);
		if(((double) spacesUsed)/tableSize >= LOAD_FACTOR){
			tableSize = 2 * tableSize;
			entries = 0;
			spacesUsed = 0;
			a_val = a.nextInt(P_TO_USE-1)+1;
			b_val = a.nextInt(P_TO_USE);
					
			Node [] saved = hashTable; // save the previous one
			hashTable = new Node[tableSize]; //and make a new one of twice the size
			for(int i=0; i< saved.length; i++){
				if(saved[i] !=null){
					Node tempVal = saved[i];
					while(tempVal!=null){ //rehash all the keys in the old one to new plaecs
						insert(tempVal.getKey(),tempVal.getValue());
						tempVal = tempVal.next;
					}
				}
			}
		}
	}
	
	public int hash(int key){
		
		int hval = (int) (((long)a_val*key + b_val)%P_TO_USE)%tableSize; //hashfunction
		return hval;
		
	}
	
	public boolean insert(int k, int v) {
		checkLoadFactor(); //check if needs to be doubled
		int index = hash(k);
		Node insert = new Node(k,v);
		if(hashTable[index]==null){
			spacesUsed ++; //keep track of occupied places
			
			hashTable[index] = insert;
			entries +=1;
			//System.out.println("new");
			return true;
		}
		
		else{
			Node temp = hashTable[index]; //this is wrong. fix it
			while(temp!=null && temp.hasNext() && temp.key != k){
				temp = temp.next;
			}
			if(temp.next == null && temp.key == k){
				temp.setValue(v);
				//System.out.println("updated");
				return false;
			}
			else if(temp.next != null && temp.key == k){
				temp.setValue(v);
				//System.out.println("updated");
				return false;
			}
			else {
				temp.next = insert;
				entries ++;
				
				return true;
			}
		}
		
	}

	public RetVal find(int k) {
		int index = hash(k);
		if(hashTable[index]== null){
			return new RetVal(false,0);
		}
		else{
			Node temp = hashTable[index];
			while(temp != null){
				if(temp.getKey()==k){return new RetVal(true,temp.getValue());}
				temp = temp.next;
			}
			return new RetVal(false,0);
		}
	}

	public RetVal delete(int k) {
		if(find(k).found){
			entries -= 1;
			int index = hash(k);
			Node current = hashTable[index];
			if(current.getKey()==k){
				hashTable[index]=hashTable[index].next; //it its' the first entry in the linked list
				//remove it by making the it's 'next' the new first entry which maybe null
				if(hashTable[index]==null){spacesUsed --;}
				return new RetVal(true,current.getValue());
			}
			else{
				Node pred = null; //otherwise use the iteration method
				while(current.getKey()!=k){pred = current; current = current.next;}
				int val = current.getValue();
				if(current.hasNext()){pred.next=current.next;}
				else{pred.next=null;}
				
				return new RetVal(true,val);
			}
			
		}
		
		else return new RetVal(false,0);
	}

	public int size() {		
		return entries;
		}
	
	//gives a string to be printed representing the tree
	public String stringHelper(){
		String s = "has(k) = (" + a_val + "k +" + b_val + ") mod " + P_TO_USE + ")";
		s += "\n";
		for(int i = 0; i<hashTable.length; i++){
			if(hashTable[i]!=null){
				s +=" "+ i + ": ";
				Node temp = hashTable[i];
				while(temp != null){
					s += temp.getKey() + " ";
					temp = temp.next;
				}
				s += "\n";
			}
		}
		return s;
	}
	
	public String toString(){
		return stringHelper();
	}
	
	}