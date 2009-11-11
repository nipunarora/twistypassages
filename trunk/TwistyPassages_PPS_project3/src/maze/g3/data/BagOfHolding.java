package maze.g3.data;

import java.util.Deque;
import java.util.LinkedList;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

/**
 * 
 * @author colin
 * 
 */

public class BagOfHolding {

	private Logger log = new Logger(LogLevel.DEBUG, this.getClass());
	public int lastItemLabel;
	// public boolean didPutItem;

	private Deque<Integer> bag = new LinkedList<Integer>();

	public void printBag() {
		log.debug("size of bag: " + bag.size());
		while (!bag.isEmpty())
			System.out.print(bag.getFirst());
	}

	public void fill(int totalNumberOfItems) {
		for (int i = 2; i <= totalNumberOfItems; i++) {
			bag.addLast(i);
		}
		log.debug("after fill bag size:" + bag.size());
	}

	public void dropItemIfAvailable() {
		if (isNotEmpty()) {
			lastItemLabel = this.useItem();
			log.debug("lastitem:" + this.lastItemLabel);
		}
	}

	/**
	 * Pops Item and returns the topmost item returns null if empty
	 * 
	 * @return
	 */
	public int getItem() {
		if (bag.isEmpty()) {
			return 0;
		}
		return bag.removeFirst();
	}

	public int useItem() {
		return getItem();
	}

	/**
	 * adds item to the bag
	 * 
	 * @param item
	 */
	public void returnItem(int i) {
		log.debug("adding item to the bag "+ i);
		bag.addFirst(i);
	}

	public boolean isNotEmpty() {
		return !bag.isEmpty();
	}
	
	public boolean isEmpty()
	{
		return bag.isEmpty();
	}
	

}
