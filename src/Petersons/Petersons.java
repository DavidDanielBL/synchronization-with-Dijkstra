/**
 * Petersons.java
 *
 * This program implements strict alternation as a means of handling synchronization.
 *
 * Note - Using an array for the two flag variables would be preferable, however  we must
 * declare the data as volatile and volatile does not extend to arrays.
 */

package Petersons;
 public class Petersons implements MutualExclusion
 {
   private volatile int turn;
   private volatile boolean flag0;
   private volatile boolean flag1;

   public Petersons() {
	flag0 = false;//indicar si un proceso está listo para entrar a su sección 
	flag1 = false; //indicar si un proceso está  listo para entrar a su sección 

	turn = 0;//indica a quién le toca entrar en su sección crítica.
   }

   public void entrySection(int t) { //si id =0 entonces other = 1
		int other = 1 - t;   //si id =1 entonces other = 0

		if (t == 0) {
			turn = other;
			flag0 = true;
			while ( (flag1 == true) && (turn == other) )
				Thread.yield();//cede el proceso al other
		}
		else {
			turn = other;
			flag1 = true;
			while ( (flag0 == true) && (turn == other) )
				Thread.yield(); //cede el proceso al other
		}
   }

   public void exitSection(int t) {
		if(t == 0)
			flag0 = false;
		else
			flag1 = false;
   }
}
