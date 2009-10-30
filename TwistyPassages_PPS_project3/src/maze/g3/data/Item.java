package maze.g3.data;

public class Item {
	
	private int indexNumber;
	
	public Item ( int indexNumber ) {
		setIndexNumber( indexNumber );
	}

	
	/**
	 * @param indexNumber the indexNumber to set
	 */
	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}

	
	/**
	 * @return the indexNumber
	 */
	public int getIndexNumber() {
		return indexNumber;
	}

}
