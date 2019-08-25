package topica.linh.thread.bai1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * // Class lamp model a lamp can on or off
 * @author ljnk975
 *
 */
public class Lamp { // Class lamp

	public enum Status { // status of lamp
		on, off // on and off
	};

	private Status status; // status
	private int index; // index

	private static AtomicInteger indexAuto = new AtomicInteger(0); // to get index auto increment

	public Lamp() { // default constructor
		this.status = Status.off; // off status
		this.index = indexAuto.getAndAdd(1); // and index auto
	}

	public Lamp(Status status) { // constructor with specific status
		this.status = status; // assign status
		this.index = indexAuto.getAndAdd(1); // And index auto
	}

	public Status getStatus() { // getter status
		return status; // return status
	}

	public void setStatus(Status status) { // set that status
		this.status = status; // set status
	}

	public int getIndex() { // get index
		return index; // return index
	}

	@Override
	public int hashCode() { // override hashcode to improve find performance
		return this.index; // hash = this index
	}

	@Override
	public boolean equals(Object obj) {  // override equals to improve find performance
		return this.index == ((Lamp)obj).index; // compare index
	}

}
