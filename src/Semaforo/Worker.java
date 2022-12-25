/**
 * Worker.java
 * 
 * This thread is used to demonstrate the operation of a semaphore.
 * 
 */
package Semaforo;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Worker implements Runnable {

	private Semaphore sem;

	private String name;

    public Worker(Semaphore sem, String name) {
        this.name = name;
		this.sem = sem;
    }

	
   

  

	public void run() {
		while (true) {
            try {
                sem.acquire();
                MutualExclusionUtilities.criticalSection(name);
                sem.release();
                MutualExclusionUtilities.nonCriticalSection(name);
            } catch (InterruptedException ex) {
                Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
            }
		}
	}

}


