/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;

import Controller.controllerP;
import Models.Conexion;
import Models.Personas;
import static Models.Personas.celdaRfc;
import static Models.Vehiculo.celdaNumSerie;
import Models.Vehiculo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
//import Views.conClientes.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
//import javax.swing.JOptionPane;
//import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
//import javax.swing.table.TableModel;

/**
 *
 * @author Sakai
 */
public class conVehiculosCliente extends javax.swing.JFrame {
    controllerP c = null;

    /**
     * Creates new form conVehiculosCliente
     */
    public conVehiculosCliente() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        c = new controllerP();
        popUpTable();
        showDataV();
        showDataC();
    }
    
    public ArrayList<Vehiculo> listVCliente(){
        DefaultTableModel model = (DefaultTableModel)jTableVC.getModel();
        ArrayList<Vehiculo> listaVehiculoPersona = new ArrayList<Vehiculo>();
        Vehiculo vehiculo;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from vehiculo where rfc='"+celdaRfc+"'";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                vehiculo = new Vehiculo();
                vehiculo.setNumserie(rs.getString(1));
                vehiculo.setColor(rs.getString(2));
                vehiculo.setAnio(rs.getInt(3));
                vehiculo.setTipo(rs.getString(4));
                listaVehiculoPersona.add(vehiculo);
                
            }
            //Starts update method
            model = (DefaultTableModel)jTableVC.getModel();
            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if(e.getType() == TableModelEvent.UPDATE){
                        String color = null;
                        int anio = 0;
                        String tipo = null;
                        int i = jTableVC.getSelectedRow();
                        MouseEvent event = null;
                        Point j = jTableVC.getPopupLocation(event);
                        TableModel model = jTableVC.getModel();
                        celdaNumSerie = model.getValueAt(i, 0).toString();
                        if(celdaNumSerie.isEmpty()){
                            JOptionPane.showMessageDialog(null, "No hay datos para modificar.\n"
                            + "Por favor, seleccione un registro de la tabla.\n"+ "..."+celdaNumSerie, "Error en la operación",
                            JOptionPane.ERROR_MESSAGE );
                        }else{
                            PreparedStatement prestV = null;
                            int col = e.getColumn();
                             Connection conexion = null;
                                try {
                                    Connection conn = Conexion.ConnectDB();
                                    //String Ssql = "UPDATE personas SET nombre='"+jTClientes.getValueAt(i, 1)+"', apellido='"+jTClientes.getValueAt(i, 2)+"', direccion='"+jTClientes.getValueAt(i, 3)+"', codpostal='"+jTClientes.getValueAt(i, 4)+"', tel='"+jTClientes.getValueAt(i, 5)+"',"
                                    //+ "WHERE rfc='"+celdaRfc+"'";
                                    
                                    if(col == 1){
                                    prestV = conn.prepareStatement("UPDATE vehiculo SET color='"+jTableVC.getValueAt(i, col)+"'"
                                    + "WHERE numserie='"+celdaNumSerie+"'");
                                    }
//                                    if(col == 4){
//                                    prestV = conn.prepareStatement("UPDATE vehiculo SET rfc ='"+jTableVC.getValueAt(i, col)+"'"
//                                    + "WHERE numserie='"+celdaNumSerie+"'");
//                                    }
                                    prestV.executeUpdate();
                                    if(prestV.executeUpdate() > 0){
                                        if(col == 5){
                                        JOptionPane.showMessageDialog(null, "Los datos han sido modificados con éxito", "Operación Exitosa", 
                                        JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }else{
                                        JOptionPane.showMessageDialog(null, "No se ha podido realizar la actualización de los datos\n"
                                        + "Inténtelo nuevamente.", "Error en la operación", 
                                        JOptionPane.ERROR_MESSAGE);
                                    }
                                } catch (SQLException e1) {}
                        }
                    }
                }
            });
            //End Update method
        }catch(Exception e){
            
        }
        return listaVehiculoPersona;
    }
        
            
    public void showDataV(){
        ArrayList<Vehiculo> listVehiculoCliente = listVCliente();
        DefaultTableModel model = (DefaultTableModel)jTableVC.getModel();
        Object[] columna = new Object[4];
        for(int i = 0; i < listVehiculoCliente.size(); i++){
            columna[0] = listVehiculoCliente.get(i).getNumserie();
            columna[1] = listVehiculoCliente.get(i).getColor();
            columna[2] = listVehiculoCliente.get(i).getAnio();
            columna[3] = listVehiculoCliente.get(i).getTipo();
            model.addRow(columna);
            
            
        }

    }
     public void showDataC(){
        //Start testing to show name of clients
                Personas persona;
        Connection conn = Conexion.ConnectDB();
        String query = "select rfc, nombre, apellido, tel from personas where rfc='"+celdaRfc+"'";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                String rfc = rs.getString("rfc");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String tel = rs.getString("tel");
                jTextField1.setText(rfc);
                jTextField2.setText(nombre);
                jTextField3.setText(apellido);
                jTextField4.setText(tel);
            }
        }catch(Exception e){}
        //Ends Method
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableVC = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(53, 100, 148));

        jButton1.setText("Servicios");

        jButton2.setText("Ventas");

        jButton3.setText("Reportes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(109, 109, 109)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(109, Short.MAX_VALUE))
        );

        jTableVC.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Num. Serie", "Color", "Modelo", "Tipo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableVC);

        jButton4.setText("Regresar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Salir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Datos Propietario:");

        jLabel2.setText("RFC:");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Apellido:");

        jLabel5.setText("Telefono:");

        jLabel6.setBackground(new java.awt.Color(240, 240, 1));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Views/hombre-de-negocios (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                            .addComponent(jTextField3))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                            .addComponent(jTextField4))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(19, 19, 19))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton5ActionPerformed

    public void clearjTClient(){
        DefaultTableModel model = (DefaultTableModel) jTableVC.getModel();
        int b = jTableVC.getRowCount()-1;
        for (int i = b; i >= 0; i--) {           
        model.removeRow(model.getRowCount()-1);
        }
    }
    
    public void popUpTable(){
            JPopupMenu jpopUp = new JPopupMenu();
        JMenuItem jmenuItem1 = new JMenuItem("Servicio", new ImageIcon(getClass().getResource("/Views/Servicios.png")));
        JMenuItem jmenuItem2 = new JMenuItem("Historial de Servicios", new ImageIcon(getClass().getResource("/Views/nissan-juke-side-v.png")));
        JMenuItem jmenuItem3 = new JMenuItem("Eliminar este registro de vehiculo", new ImageIcon(getClass().getResource("/Views/Trash-Can.png")));
        
        jmenuItem1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) { 
                    int i = jTableVC.getSelectedRow();
                    MouseEvent event = null;
                    Point j = jTableVC.getPopupLocation(event);
                    TableModel model = jTableVC.getModel();
                    celdaRfc = model.getValueAt(i, 0).toString();
                    celdaNumSerie = model.getValueAt(i, 0).toString();
                    RegServ rServ = new RegServ();
                    rServ.setVisible(true);
                }

//end testing  
             
            });
        
        jmenuItem2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
            }
                
            });
        jmenuItem3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    TableModel model = jTableVC.getModel();
                    int i = jTableVC.getSelectedRow();
                celdaNumSerie = model.getValueAt(i, 0).toString();
                //testing validating if client exist
                 Vehiculo vehiculo;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from vehiculo where numserie='"+celdaNumSerie+"'";
        Statement st;
        ResultSet rs;
                try {
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                    if(rs.next()){
                        int confirmar = JOptionPane.showConfirmDialog(null, "Al eliminar el registro del vehiculo con numero: "+celdaNumSerie+ "\n Se debe tomar en cuenta que se \n"+
                                "eliminaran todos los datos relacionados \n" +"¿Desea eliminar el registro seleccionado?");
                        if(confirmar == JOptionPane.YES_OPTION){
                            PreparedStatement cs = conn.prepareStatement("delete from vehiculo where numserie = '"+celdaNumSerie+"'");
                            
                            if(cs.executeUpdate() > 0){
                                JOptionPane.showMessageDialog(null, "Se han eliminado los datos pertenecientes al vehiculo: \n"+celdaNumSerie, "Operación Exitosa!!!", 
                                JOptionPane.INFORMATION_MESSAGE);
                                clearjTClient();
                                showDataV();
                            }else{
                                JOptionPane.showMessageDialog(null, "No se ha podido realizar eliminacion de los datos\n"
                                + "Inténtelo nuevamente. \n", "Error en la operación!!", 
                                JOptionPane.ERROR_MESSAGE);
                            }     
                    }
                    }
                } catch (SQLException ex) {
                   // Logger.getLogger(conClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
                //end testing
                }
            });
        
        jpopUp.add(jmenuItem1);
        jpopUp.add(jmenuItem2);
        jpopUp.add(jmenuItem3);
        jTableVC.setComponentPopupMenu(jpopUp);
}
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
            java.util.logging.Logger.getLogger(conVehiculosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(conVehiculosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(conVehiculosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(conVehiculosCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new conVehiculosCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableVC;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
