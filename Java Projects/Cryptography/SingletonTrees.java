//basically just designing a new type of node to built our binary tree with
public class SingletonTrees{
	public Character data;
	public Integer freq;

	public SingletonTrees(Character data,Integer freq) {
		this.setData(data);
		this.setFreq(freq);
	  }

	public int getFreq() {
		return freq;
	}

	public void setFreq(Integer freq) {
		this.freq = freq;
	}

	public char getData() {
		return data;
	}

	public void setData(Character data) {
		this.data = data;
	}
}