package topica.linh.thread.bai1;

/**
 * Class trash to store lamp that status == off
 * @author ljnk975
 *
 */
public class Trash extends Store { // class trash extends store to re use code

	@Override
	public void addALamp(Lamp lamp) { // add a lamp to list
		if(lamp == null) // lamp can't be null
			throw new NullPointerException(); // throw exception
		if(lamp.getStatus() != Lamp.Status.off) // only off lamp
			throw new IllegalArgumentException("Only allow off lamp to trash"); // throw exception
		super.addALamp(lamp); // call super to add lamp
	}

}
