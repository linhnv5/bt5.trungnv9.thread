package topica.linh.thread.bai2;

import java.util.ArrayList;

/**
 * Class store to save lamp, not extends List because of want to hide some method add, remove, ...
 * @author ljnk975
 *
 */
public class Store { // Class store

	private ArrayList<Lamp> listOfLamp; // list to store lamp

	public Store() { // Constructor to create a store
		this.listOfLamp = new ArrayList<Lamp>();
	}

	public synchronized void addALamp(Lamp lamp) { // add a lamp to list
		if(lamp == null) // lamp can't be null
			throw new NullPointerException(); // throw exception
		this.listOfLamp.add(lamp); // add to list
	}

	public synchronized int size() { // get size of lamp list
		return this.listOfLamp.size(); // return list size
	}
	
	public synchronized Lamp getLampAtIndex(int index) { // get a lamp by using index
		return this.listOfLamp.get(index); // return lamp
	}

	public synchronized Lamp getLampByLampIndex(int lindex) { // get a lamp bu using lamp index
		Lamp l; // lamp variable
		for(int i = 0; i < this.listOfLamp.size(); i++) // for all element in list
			if((l = this.listOfLamp.get(i)).getIndex() == lindex) // if lamp index == key
				return l; // then return that
		return null; // lamp index not exists
	}

	public synchronized Lamp removeLampAtIndex(int index) { // remove lamp at index
		return this.listOfLamp.remove(index); // remove from list
	}

}
