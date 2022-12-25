/*
 * NewJFrame.java
 *
 * Created on 6 de noviembre de 2009, 9:53
 */

package Petersons;

import javax.swing.DefaultListModel;

/**
 *
 * @author  red
 */
public class Peterson extends javax.swing.JFrame {
    private DefaultListModel entrada;
    private DefaultListModel salida;
    /** Creates new form NewJFrame */
    public Peterson() {
        initComponents();
         MutualExclusion algorithm = new Petersons();
          entrada = new DefaultListModel();
            jList1.setModel(entrada);
            salida = new DefaultListModel();
            jList2.setModel(salida);

	Thread first = new Thread(new Worker("Worker 0", 0, algorithm));
	Thread second = new Thread(new Worker("Worker 1", 1, algorithm));

      first.start();
      second.start();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Metodo de sincronizacion de Peterson");

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jLabel1.setText("DENTRO");

        jLabel2.setText("AFUERA");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(155, 155, 155))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(142, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Peterson().setVisible(true);
            }
        });
    }
    
    public class Petersons implements MutualExclusion
 {
   private volatile int turn;
   private volatile boolean flag0;
   private volatile boolean flag1;

   public Petersons() {
	flag0 = false;
	flag1 = false;

	turn = 0;
   }

   public void entrySection(int t) {
		int other = 1 - t;

		if (t == 0) {
			turn = other;
			flag0 = true;
			while ( (flag1 == true) && (turn == other) )
				Thread.yield();
		}
		else {
			turn = other;
			flag1 = true;
			while ( (flag0 == true) && (turn == other) )
				Thread.yield();
		}
   }

   public void exitSection(int t) {
		if(t == 0)
			flag0 = false;
		else
			flag1 = false;
   }
}

    public class Worker implements Runnable
{
	private String name;
	private int id;
   	private MutualExclusion mutex;

   public Worker(String name, int id, MutualExclusion mutex) {
      this.name = name;
      this.id = id;
      this.mutex = mutex;
   }

   public void run() {
      while (true) {
         mutex.entrySection(id);
         MutualExclusionUtilities.criticalSection(name);
          entrada.addElement(name + " en zona critica");
         mutex.exitSection(id);
         MutualExclusionUtilities.nonCriticalSection(name);
          salida.addElement(name + " salio de zona critica");
      }
   }


}
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
    
}
