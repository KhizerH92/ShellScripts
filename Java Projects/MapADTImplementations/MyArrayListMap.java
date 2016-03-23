import java.util.ArrayList;

// So I basically just used two arraylists to add keys and values simultaneously and then delete
// them simultaneously
public class MyArrayListMap implements MyMapADT{
	private ArrayList<Integer> keys;
	private ArrayList<Integer> values;
	
	public MyArrayListMap(){
		keys = new ArrayList<Integer>();
		values = new ArrayList<Integer>();
		
	}
	
	public boolean insert(int k, int v) {
		int index = -1;
		if(find(k) != null){
			index = keys.indexOf(k);
		}
		
		if(index == -1){
			keys.add(k);
			values.add(v);
			return true;
		}
		else{
			values.add(index,v);
			return false;
		}
		
	}

	public RetVal find(int k) {
		RetVal returnedVal = null;
		int indexval = keys.indexOf(k);
		if(indexval != -1){
			returnedVal = new RetVal(true, values.get(indexval));
			return returnedVal;
		}
		else{
			return new RetVal(false, 0);
		}
	}

	public RetVal delete(int k) {
		int indexDel = keys.indexOf(k);
		if(indexDel != -1){
			int valueremoved = values.remove(indexDel);
			keys.remove(indexDel);
			return new RetVal(true,valueremoved);
		}
		
		else{
			return new RetVal(false,0);
		}
	}

	public int size() {
		// TODO Auto-generated method stub
		return keys.size();
	}
	
	public String toString(){
		return keys.toString();
	}
	
	
}