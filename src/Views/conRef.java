/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Models.Conexion;
import Models.Refacciones;
import static Models.Refacciones.celdaCodRef;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sakai
 */
public class conRef extends javax.swing.JFrame {

    /**
     * Creates new form conRef
     */
    public conRef() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        showDataRef();
    }
    
    public ArrayList<Refacciones> listRefacciones(){
        DefaultTableModel model = (DefaultTableModel)jTRef.getModel();
        ArrayList<Refacciones> listRef = new ArrayList<Refacciones>();
        Refacciones refacciones;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from refacciones";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                refacciones = new Refacciones();
                refacciones.setCodRefaccion(rs.getString(1));
                refacciones.setDescripcion(rs.getString(2));
                refacciones.setPreciopublico(rs.getFloat(3));
                refacciones.setPreciomayoreo(rs.getFloat(4));
                refacciones.setVcompatibles(rs.getString(5));
                refacciones.setStock(rs.getInt(6));
                listRef.add(refacciones);
            }
            model = (DefaultTableModel)jTRef.getModel();
            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if(e.getType() == TableModelEvent.UPDATE){
                        int i = jTRef.getSelectedRow();
                        MouseEvent event = null;
                        Point j = jTRef.getPopupLocation(event);
                        TableModel model = jTRef.getModel();
                        celdaCodRef = model.getValueAt(i, 0).toString();
                        if(celdaCodRef.isEmpty()){
                            JOptionPane.showMessageDialog(null, "No hay datos para modificar.\n"
                            + "Por favor, seleccione un registro de la tabla.\n"+ "..."+celdaCodRef, "Error en la operación",
                            JOptionPane.ERROR_MESSAGE );
                        }else{
                            PreparedStatement prest = null;
                            int col = e.getColumn();
                                Connection conexion = null;
                                try {
                                    Connection conn = Conexion.ConnectDB();
                                    
                                    if(col == 1){
                                    prest = conn.prepareStatement("UPDATE refacciones SET descripcion='"+jTRef.getValueAt(i, col)+"'"
                                    + "WHERE codRefaccion='"+celdaCodRef+"'");
                                    }
                                    if(col == 2){
                                    prest = conn.prepareStatement("UPDATE refacciones SET preciopublico='"+jTRef.getValueAt(i, col)+"'"
                                    + "WHERE codRefaccion='"+celdaCodRef+"'");
                                    }
                                    if(col == 3){
                                    prest = conn.prepareStatement("UPDATE refacciones SET preciomayoreo='"+jTRef.getValueAt(i, col)+"'"
                                    + "WHERE codRefaccion='"+celdaCodRef+"'");
                                    }
                                    if(col == 4){
                                    prest = conn.prepareStatement("UPDATE refacciones SET vcompatibles='"+jTRef.getValueAt(i, col)+"'"
                                    + "WHERE codRefaccion='"+celdaCodRef+"'");
                                    }
                                    if(col == 5){
                                    prest = conn.prepareStatement("UPDATE refacciones SET stock='"+jTRef.getValueAt(i, col)+"'"
                                    + "WHERE codRefaccion='"+celdaCodRef+"'");
                                    }
                                    prest.executeUpdate();
                                    if(prest.executeUpdate() > 0){
                                        if(col == 5){
                                        JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa", 
                                        JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                                        + "Inténtelo nuevamente.", "Error en la operación", 
                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (SQLException e1) {
                                    JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                                    + "Inténtelo nuevamente.\n"
                                    + "Error: "+e, "Error en la operación",JOptionPane.ERROR_MESSAGE);
                                }finally{
                                    if(conexion!=null){
                                        try {
                                            conexion.close();
                                        } catch (SQLException e1) {
                                            JOptionPane.showMessageDialog(null, "Error al intentar cerrar la conexión."
                                            + "Error: "+e, "Error en la operación", JOptionPane.ERROR_MESSAGE);
                                        }
            
                                    }
                                }
                        }
                    }
                }
            });
        }catch(Exception e){}
        return listRef;
    }
    
    public void showDataRef(){
        ArrayList<Refacciones> listRef = listRefacciones();
        DefaultTableModel model = (DefaultTableModel)jTRef.getModel();
        Object[] columna = new Object[6];
        for(int i = 0; i < listRef.size(); i++){
            columna[0] = listRef.get(i).getCodRefaccion();
            columna[1] = listRef.get(i).getDescripcion();
            columna[2] = listRef.get(i).getPreciopublico();
            columna[3] = listRef.get(i).getPreciomayoreo();
            columna[4] = listRef.get(i).getVcompatibles();
            columna[5] = listRef.get(i).getStock();
            model.addRow(columna);   
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFilter = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTRef = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(53, 100, 148));

        jButton2.setText("Servicios");

        jButton3.setText("Ventas");

        jButton4.setText("Reportes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jLabel1.setText("Ingrese refaccion a buscar");

        jTextFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFilterKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(jTextFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTRef.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num. Refaccion", "Descripcion", "Precio publico", "Precio Mayoreo", "compatibilidad", "Almacen"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTRef.setFillsViewportHeight(true);
        jScrollPane1.setViewportView(jTRef);
        if (jTRef.getColumnModel().getColumnCount() > 0) {
            jTRef.getColumnModel().getColumn(0).setResizable(false);
            jTRef.getColumnModel().getColumn(1).setResizable(false);
            jTRef.getColumnModel().getColumn(2).setResizable(false);
            jTRef.getColumnModel().getColumn(3).setResizable(false);
            jTRef.getColumnModel().getColumn(4).setResizable(false);
            jTRef.getColumnModel().getColumn(5).setResizable(false);
        }

        jButton5.setText("Principal");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Salir");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton6ActionPerformed
 TableRowSorter trs;
    private void jTextFilterKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFilterKeyTyped
        // TODO add your handling code here:
        
        jTextFilter.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent e) {
                trs.setRowFilter(RowFilter.regexFilter("(?i)"+jTextFilter.getText(), 1));
            }
        });
        DefaultTableModel model = (DefaultTableModel)jTRef.getModel();
        trs = new TableRowSorter(model);
        jTRef.setRowSorter(trs);
    }//GEN-LAST:event_jTextFilterKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(conRef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(conRef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(conRef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(conRef.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new conRef().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTRef;
    private javax.swing.JTextField jTextFilter;
    // End of variables declaration//GEN-END:variables
}
