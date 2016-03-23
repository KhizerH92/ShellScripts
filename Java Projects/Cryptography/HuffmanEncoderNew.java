import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JFileChooser;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.SwingUtilities;

public class HuffmanEncoderNew{
	
	public static void main(String[] args) throws IOException {
		
		try{ //try working with the filepath provided or give an error saying the file not found
			String path = getFilePath(); //use the getfilepath method to get path
			
			Map<Character, Integer> fileMap = createMap(path); //create a map of frequencies
			// System.out.println(fileMap);
			BinaryTree<SingletonTrees> s = new BinaryTree<SingletonTrees>(new SingletonTrees(null,null));
			
			//Check if there is only one character in the file
			if (fileMap.size()==1){
				Iterator<Character> iter = fileMap.keySet().iterator();
				Character k = iter.next();
				BinaryTree<SingletonTrees> node =
						new BinaryTree<SingletonTrees>(new SingletonTrees(k,fileMap.get(k)));
				s.setLeft(node);
			}
			//if not build the tree using the frequency map
			else{
				s = makeTree(fileMap); //Build a tree based on frequencies
			}
			//System.out.println(s.toString()); test code
			Map<Character, String> codeVal = new HashMap<Character, String>();
			//Map<Character, String> codeVal = codeMap(s);
			getCodesValue("",codeVal,s);
			
			compressFile(path, codeVal); //compress the file
			decompressFile(path, s); //decompress the compressed file
		}
		catch(IOException e){
			System.out.println("File not found");
		}
	}
	
	//path method provided by the professor:
	public static String getFilePath() {
	    final AtomicReference<String> result = new AtomicReference<>();

	    try {
	      SwingUtilities.invokeAndWait(new Runnable() {
	          public void run() {
	            JFileChooser fc = new JFileChooser();

	          int returnVal = fc.showOpenDialog(null);
	          if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File file = fc.getSelectedFile();
	            String pathName = file.getAbsolutePath();
	            result.set(pathName);
	          }
	          else
	            result.set("");
	          }
	      });
	    } catch (InvocationTargetException | InterruptedException e) {
	      e.printStackTrace();
	    }

	    // Create a file chooser.
	    return result.get();
	  }
	
	//Method to create the frequency map for all the characters in the file:
	public static Map<Character, Integer> createMap(String pathName) throws IOException{ //if file not found
		Map<Character,Integer> frequency = new HashMap<Character,Integer>();	
		BufferedReader inputFile =  new BufferedReader(new FileReader(pathName));
		
		int i = inputFile.read(); //read into the file
		while(i != -1){
			Character charac = (char) i;
			if(frequency.containsKey(charac) == false){ //if key not already in the map
				frequency.put(charac, 1);
				}
			else{
				frequency.put(charac,(int)((frequency.get(charac)+1)));	//if there increment	
			}
			i = inputFile.read(); //advance
			}
		inputFile.close(); 
		return frequency; //returns a map of frequencies if file not empty
			
	}
	
	//Make the tree from the frequency map that we have
	
	public static BinaryTree<SingletonTrees> makeTree(Map<Character, Integer> m){
		//Initialize the prioirty queue:
		PriorityQueue<BinaryTree<SingletonTrees>> singletonList=
				new PriorityQueue<BinaryTree<SingletonTrees>>(1+m.size(),new TreeComparator());
		
		//If frequency map is empty just say the file is empty
		if (m.isEmpty())
			System.out.println("The File is empty");
		else { //otherwise build the tree using the helper node 'SingleTrees' class
			
			Set<Character> mapCharacters = m.keySet();
			//System.out.println(mapCharacters);  just testing stuff
			Iterator<Character> iter = mapCharacters.iterator();

			while (iter.hasNext()) {		
				Character k = iter.next();
				Integer value = m.get(k);
				SingletonTrees node = new SingletonTrees(k,value);
				singletonList.add(new BinaryTree<SingletonTrees>(node));
			}
			}
		// Once the priority queue has been built from all the singleton Trees 
		//we can create the tree
		//now we create a single tree from the priority queue
		
		while(singletonList.size()>1){
			BinaryTree<SingletonTrees> t1 = singletonList.remove();
			BinaryTree<SingletonTrees> t2 = singletonList.remove();
			Integer newfreq =(t1.getValue().getFreq()+t2.getValue().getFreq());
			
			BinaryTree<SingletonTrees> head =
					new BinaryTree<SingletonTrees>(new SingletonTrees(null,null));
			
			head.setLeft(t1);
			head.setRight(t2);
			head.getValue().setFreq(newfreq);
			head.getValue().setData(null);
			
			singletonList.add(head);
		}
		
		return singletonList.poll(); //return the main tree left in the end
	}
	
	//create code map using the tree we built from frequencies
	//using recursion we can build bit strings for each character in the file:
	public static void getCodesValue(String s, Map<Character, String> codeVal, BinaryTree<SingletonTrees> t){
		
		if(t!=null){
			
			if(t.hasLeft()){  //has left, then move left after adding a "0"
				getCodesValue(s + "0", codeVal,t.getLeft());
			}
			if(t.hasRight()){ //has right, then move right after adding a "1"
				getCodesValue(s + "1", codeVal,t.getRight());
			}
			if(t.isLeaf()){ //if leaf add to the codeval map
				codeVal.put(t.getValue().getData(),s);
			}
		}
		else
			codeVal.clear(); //otherwise get rid of all mappings
		
	}
	
	//Compressing the files
	
	public static void compressFile(String pathname, Map<Character, String> codeVal) throws IOException{
		BufferedReader inputFile =  new BufferedReader(new FileReader(pathname)); //Reader
		
		//Write a name for the output that we can quickly recover for decompression
		String compressedPathName = pathname.substring(0,pathname.length()-4)+"Compressed.txt";
		//Output file 
		BufferedBitWriter bitOutputFile =  new BufferedBitWriter(compressedPathName); //see the changes
		try{	//take care of an exception here if file doesn't compress or output properly
			int r = inputFile.read();
			while (r != -1){
				Character charac = (char)(r);
				String CharacVal = codeVal.get(charac);
				
				for (int i=0; i <= CharacVal.length()-1; i++){
					if (CharacVal.charAt(i)=='1') {
						bitOutputFile.writeBit(1);
						}
					else if (CharacVal.charAt(i)=='0'){
						bitOutputFile.writeBit(0);
						}
				}				
				r = inputFile.read();
				}
		}
		finally{ //close both files regardless
			inputFile.close(); //watch out for this
			bitOutputFile.close();
		}
				
		}
	
	public static void decompressFile(String PathName,BinaryTree<SingletonTrees> s) throws IOException{
		//Figure out the compressed files name from the pathname basically
		String compressedPathName = PathName.substring(0,PathName.length()-4)+"Compressed.txt";
		//and then write a decompressed file name
		String decompressedPathName = PathName.substring(0,PathName.length()-4)+"Decompressed.txt";
		//System.out.println(compressedPathName);
		BufferedBitReader inputFile =  new BufferedBitReader(compressedPathName);		
		BufferedWriter outputFile =  new BufferedWriter(new FileWriter(decompressedPathName));
		
		
		try{ //watch for an exception
			int i = inputFile.readBit();
			BinaryTree<SingletonTrees> temp = s;
			//we basically go over the bits and when we reach the leaf you use the 
			//string gotten so far along with the code map to figure out the character
			//we have to write
			while(i != -1){
				int tempVal = i;
				
				if(tempVal == 0)  {
					temp = temp.getLeft();
					}
				
				if(tempVal == 1)  {
					temp = temp.getRight();
				}
				
				if(temp.isLeaf()){ 
					outputFile.write(temp.getValue().getData());
					temp = s;
				}
	
			i = inputFile.readBit();	//advance the counter
			}
		}
		finally { //close both files anyway
			outputFile.close();
			inputFile.close();
		}
	}
	}
	//the whole class ends here