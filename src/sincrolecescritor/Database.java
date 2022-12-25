

/**
 * Database.java
 *
 * This class contains the methods the readers and writers will use
 * to coordinate access to the database.
 * Access is coordinated using Java synchronization.
 *
 */
package sincrolecescritor;

public class Database implements RWLock
{
   // the number of active readers
   private int readerCount; //para saber la cantidad de lectores activos

   // flag to indicate whether the database is in use
   private boolean dbWriting;//para indicar quien tiene realiza proceso

   public Database()
   {
      readerCount = 0;//contador del lector
      dbWriting = false;//inicializamos en falso la realizacionde procesos
   }

   // reader will call this when they start reading
   public synchronized void acquireReadLock(int readerNum)
   {
      while (dbWriting == true) //Mientras estan escribiendo
      {
         try { wait(); } //Se debe esperar
         catch(InterruptedException e) { }
      }

      ++readerCount;//Aumenamos cantidad de lectores

      System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
   }

   // reader will call this when they finish reading
   public synchronized void releaseReadLock(int readerNum)
   {
      --readerCount;

      // if I am the last reader tell all others
      // that the database is no longer being read
      if (readerCount == 0)///Si el contador se redujo a 0
	 notify(); //envia la señal

      System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);
   }

   // writer will call this when they start writing
    public synchronized void acquireWriteLock(int writerNum) {
      while (readerCount > 0 || dbWriting == true) { // si existe lector alguno
         try { wait(); } //se debe esperar
         catch(InterruptedException e) {}
      }

      // once there are either no readers or writers
      // indicate that the database is being written
      dbWriting = true; // se activo escritura

      System.out.println("writer " + writerNum + " is writing.");
   }

   // writer will call this when they start writing
   public synchronized void releaseWriteLock(int writerNum)
   {
      dbWriting = false;//se libero colocando al booleano en falso

      System.out.println("writer " + writerNum + " is done writing.");

	/**
	 * This must be notifyAll()  as there may be more than
	 * one waiting reader to read the database and we must
	 * notify ALL of them.
	 */
      notifyAll();
   }
}
