/**
 * Database.java
 *
 * This class contains the methods the readers and writers will use
 * to coordinate access to the database. Access is coordinated using semaphores.
 */

package sincrolecescri;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Database implements RWLock
{
   // the number of active readers
   private int readerCount;

   Semaphore mutex;  // controls access to readerCount
   Semaphore db;     // controls access to the database

   public Database() {
      readerCount = 0;

      mutex = new Semaphore(1);
      db = new Semaphore(1);
   }

    @Override
   public void acquireReadLock(int readerNum) {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

      ++readerCount;

      // if I am the first reader tell all others
      // that the database is being read
      if (readerCount == 1)
         try {
            db.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

      System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
      mutex.release();
   }

    @Override
   public void releaseReadLock(int readerNum) {
        try {
            mutex.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

      --readerCount;

      // if I am the last reader tell all others
      // that the database is no longer being read
      if (readerCount == 0)
         db.release();

      System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);

      mutex.release();
   }

    @Override
   public void acquireWriteLock(int writerNum) {
        try {
            db.acquire();
        } catch (InterruptedException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
      System.out.println("writer " + writerNum + " is writing.");
   }

    @Override
   public void releaseWriteLock(int writerNum) {
      System.out.println("writer " + writerNum + " is done writing.");
      db.release();
   }


}
