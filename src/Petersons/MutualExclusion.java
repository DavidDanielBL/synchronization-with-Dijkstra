/**
 * MutualExclusion.java
 * 
 * This interface is to be implmented by each solution to the critical section
 * problem.
 */
package Petersons;
public interface MutualExclusion {
	public void entrySection(int turn);

	public void exitSection(int turn);

}
