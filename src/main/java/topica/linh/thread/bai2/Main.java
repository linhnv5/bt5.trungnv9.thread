package topica.linh.thread.bai2;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Clas main to test multithread
 * @author ljnk975
 *
 */
public class Main { // main class

	private static Store store; // store to save lamp
	private static Trash trash; // trash to remove lamp

	private static boolean createRunning; // flags to control store thread
	private static boolean removeRunning; // flags to control trash thread
	private static boolean repairRunning; // flags to control repair thread

	public static final int MAX_RAND_LAMP = 20;
	public static final int CREATE_SLEEP  = 100;
	public static final int REMOVE_SLEEP  = 200;
	public static final int REPAIR_SLEEP  = 200;
	
	public static final int RUNNING_TIME  = 30000;

	/**
	 * Thread1-Runnable to create lamp
	 * @author ljnk975
	 *
	 */
	static class LampCreate implements Runnable { // lamp create

		@Override
		public void run() { // thread entry point
			ThreadLocalRandom r = ThreadLocalRandom.current(); // create a random to generator random value
			while(createRunning) { // when running
				try {
					int number = 1+r.nextInt(MAX_RAND_LAMP); // number random from 1 to 20
					System.out.println("Creating "+number+" lamp"); // info create lamp
					for(int i = 0; i < number; ++i) {  // create number lamp
						Lamp.Status type = new Lamp.Status[] {Lamp.Status.off, Lamp.Status.on, Lamp.Status.repair}[r.nextInt(3)]; // random type
						Lamp l = new Lamp(type); // create a lamp
						store.addALamp(l); // add to store
						System.out.println("Add lamp index="+l.getIndex()+" status="+l.getStatus()); // info of create lamp
					}
					Thread.sleep(CREATE_SLEEP); // sleep
				} catch (InterruptedException e) { // catch interrupt exception to stop thread
					System.out.println("Thread create stop"); // info that stop thread
					createRunning = false; // use thread.interrupt() to stop that dame thing
				} catch (Exception e) { // handle all exception to keep thread running
					e.printStackTrace(); // print error
				} 
			}
		}

	}

	/**
	 * Thread2-Runnable to remove off lamp
	 * @author ljnk975
	 *
	 */
	static class LampRemover implements Runnable { // lamp remove
		@Override
		public void run() { // thread entry point
			while(removeRunning) { // when running
				try {
					System.out.println("Remove check"); // info remove
					for(int i = 0; i < store.size(); ++i) {  // for all of store
						Lamp l = store.getLampAtIndex(i); // get lam at index i
						if(l.getStatus() == Lamp.Status.off || l.getStatus() == Lamp.Status.repair) { // check if off status or repair
							store.removeLampAtIndex(i--); // remove from store and decrement i to get next lamp
							trash.addALamp(l); // add to trash
							System.out.println("Remove lamp index="+l.getIndex()); // info of remove lamp
						}
					}
					Thread.sleep(REMOVE_SLEEP); // sleep
				} catch (InterruptedException e) { // catch interrupt exception to stop thread
					System.out.println("Thread remove stop"); // info that stop thread
					removeRunning = false; // use thread.interrupt() to stop that dame thing
				} catch(Exception e) { // catch all exception to avoid crash thread
					e.printStackTrace(); // print error
				}
			}
		}
	}

	/**
	 * Thread3-Runnable to repair lamp
	 * @author ljnk975
	 *
	 */
	static class LampRepairer implements Runnable {
		@Override
		public void run() { // thread entry point
			ThreadLocalRandom r = ThreadLocalRandom.current(); // random
			while(repairRunning) { // when running
				try {
					System.out.println("Repair check"); // info repairer
					while(trash.size() > 0) { // for all elements in trash
						Lamp l = trash.removeLampAtIndex(0); // get first lamp in trash
						if(l.getStatus() == Lamp.Status.repair) { // check if repair status
							store.addALamp(l); // add to store
							System.out.println("Restore repair lamp index="+l.getIndex()); // info of repair lamp
						} else if(r.nextInt(2) == 1) { // if off lamp and random=1 then add to store 
							l.setStatus(Lamp.Status.repair); // set status is repair
							store.addALamp(l); // add to store
							System.out.println("Restore off lamp index="+l.getIndex()); // info of repair lamp
						} else
							System.out.println("Remove off lamp index="+l.getIndex()); // info of repair lamp
					}
					Thread.sleep(REPAIR_SLEEP); // sleep
				} catch (InterruptedException e) { // catch interrupt exception to stop thread
					System.out.println("Thread repair stop"); // info that stop thread
					repairRunning = false; // use thread.interrupt() to stop that dame thing
				} catch(Exception e) { // catch all exception to avoid crash thread
					e.printStackTrace(); // print error
				}
			}
		}
	}

	public static void main(String[] args) { // main function
		// create store and trash
		store = new Store();
		trash = new Trash();

		// set running flags
		createRunning = true;
		removeRunning = true;
		repairRunning = true;

		// run three thread
		Thread create, remove, repair; // three thread control
		(create = new Thread(new LampCreate())).start(); // start thread 1
		(remove = new Thread(new LampRemover())).start(); // start thread 2
		(repair = new Thread(new LampRepairer())).start(); // start thread 3

		try {
			Thread.sleep(RUNNING_TIME); // sleep for thread running
		} catch (InterruptedException e1) {
		}

		create.interrupt(); remove.interrupt(); repair.interrupt(); // stop all thread

		try {
			create.join(); remove.join(); repair.join(); // wait 3 threads to die
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
