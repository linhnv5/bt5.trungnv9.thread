package topica.linh.thread.bai2;

/**
 * Class trash to store lamp that status == off
 * @author ljnk975
 *
 */
public class Trash extends Store { // class trash extends store to re use code

	@Override
	public synchronized void addALamp(Lamp lamp) { // add a lamp to list
		if(lamp == null) // lamp can't be null
			throw new NullPointerException(); // throw exception
		if(lamp.getStatus() != Lamp.Status.off && lamp.getStatus() != Lamp.Status.repair) // only off lamp or repair lamp
			throw new IllegalArgumentException("Only allow off or repair lamp to trash"); // throw exception
		super.addALamp(lamp); // call super to add lamp
	}

}
