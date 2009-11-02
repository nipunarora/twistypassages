package maze.g3.data;

public class Item {
	
	private int indexNumber;
	
	public Item ( int indexNumber ) {
		setLabel( indexNumber );
	}

	
	/**
	 * @param indexNumber the indexNumber to set
	 */
	public void setLabel(int indexNumber) {
		this.indexNumber = indexNumber;
	}

	
	/**
	 * @return the indexNumber
	 */
	public int getLabel() {
		return indexNumber;
	}

}
