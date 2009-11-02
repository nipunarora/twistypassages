package maze.g3.data;

import java.util.ArrayList;

import maze.g3.Logger;
import maze.g3.Logger.LogLevel;

public class BagOfHolding {
	
	private Logger log = new Logger(LogLevel.DEBUG, this.getClass());
	private ArrayList<Item> bag;
	public int lastItemLabel;
	//public boolean didPutItem;
	
	public BagOfHolding (  ) {
		bag = new ArrayList<Item> ();
	}

	public void fill( int totalNumberOfItems ) {
		for (int i=2; i<=totalNumberOfItems; i++) {
			bag.add( new Item( i ));
		}
		log.debug("after fill bag size:"+bag.size());
	}
	
	
	public void dropItemIfAvailable() {
		if( this.isNotEmpty() ) {
			this.lastItemLabel = this.useItem().getLabel();
			log.debug("lastitem:"+this.lastItemLabel);
		}
	}
	/**
	 * Pops Item and returns the topmost item
	 * returns null if empty
	 * @return
	 */
	public Item getItem(){
		if(this.isNotEmpty()){
			return this.useItem();
		}
		else{
			return null;
		}
	}
	public Item useItem () {
		Item item = bag.get( 0 );
		bag.remove( 0 );
		return item;
	}
	/**
	 * adds item to the bag
	 * @param item
	 */
	public void returnItem ( Item item ) {
		bag.add( item );
	}
	
	public boolean isNotEmpty() {
		return !bag.isEmpty();
	}
	
}
