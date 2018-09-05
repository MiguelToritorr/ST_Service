/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Views;


import Controller.controllerP;
import Models.Conexion;
import Models.Detallevref;
import Models.Personas;
import static Models.Personas.celdaRfc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import Models.Vehiculo;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.RowFilter;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Sakai
 */
public class conClientes extends javax.swing.JFrame {

    private static void Actualizar(String nombre, String apellido, String direccion, int codpostal, String tel, String rfc) {

    }

    /**
     * Creates new form conClientes
     */
    controllerP c = null;
    public conClientes() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
        showData();
        popUpTable();
    }

    public ArrayList<Personas> listpersona(){
        DefaultTableModel model = (DefaultTableModel)jTClientes.getModel();
        ArrayList<Personas> listapersona = new ArrayList<Personas>();
        Personas persona;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from personas where tipo='cliente'";
        Statement st;
        ResultSet rs;
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            
            while(rs.next()){
                persona = new Personas();
                persona.setRfc(rs.getString(1));
                persona.setNombre(rs.getString(2));
                persona.setApellido(rs.getString(3));
                persona.setDireccion(rs.getString(4));
                persona.setCodpostal(rs.getInt(5));
                persona.setTel(rs.getString(6));
                listapersona.add(persona);
            }
            
            model = (DefaultTableModel)jTClientes.getModel();
            model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if(e.getType() == TableModelEvent.UPDATE){ 
                        String nombre = null;
                        String apellido = null;
                        String direccion = null;
                        int codpostal = 0;
                        String tel = null;
                        String rfc = null;
                        int i = jTClientes.getSelectedRow();
                        MouseEvent event = null;
                        Point j = jTClientes.getPopupLocation(event);
                        TableModel model = jTClientes.getModel();
                        celdaRfc = model.getValueAt(i, 0).toString();
                        if(celdaRfc.isEmpty()){
                            JOptionPane.showMessageDialog(null, "No hay datos para modificar.\n"
                            + "Por favor, seleccione un registro de la tabla.\n"+ "..."+celdaRfc, "Error en la operación",
                            JOptionPane.ERROR_MESSAGE );
                        }else{
                            PreparedStatement prest = null;
                            int col = e.getColumn();
                            
//                            int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea modificar los datos actuales?"+celdaRfc);
//                            if(confirmar == JOptionPane.YES_OPTION){
                                Connection conexion = null;
                                try {
                                    Connection conn = Conexion.ConnectDB();
                                    //String Ssql = "UPDATE personas SET nombre='"+jTClientes.getValueAt(i, 1)+"', apellido='"+jTClientes.getValueAt(i, 2)+"', direccion='"+jTClientes.getValueAt(i, 3)+"', codpostal='"+jTClientes.getValueAt(i, 4)+"', tel='"+jTClientes.getValueAt(i, 5)+"',"
                                    //+ "WHERE rfc='"+celdaRfc+"'";
                                    
                                    if(col == 1){
                                    prest = conn.prepareStatement("UPDATE personas SET nombre='"+jTClientes.getValueAt(i, col)+"'"
                                    + "WHERE rfc='"+celdaRfc+"'");
                                    }
                                    if(col == 2){
                                    prest = conn.prepareStatement("UPDATE personas SET apellido='"+jTClientes.getValueAt(i, col)+"'"
                                    + "WHERE rfc='"+celdaRfc+"'");
                                    }
                                    if(col == 3){
                                    prest = conn.prepareStatement("UPDATE personas SET direccion='"+jTClientes.getValueAt(i, col)+"'"
                                    + "WHERE rfc='"+celdaRfc+"'");
                                    }
                                    if(col == 4){
                                    prest = conn.prepareStatement("UPDATE personas SET codpostal='"+jTClientes.getValueAt(i, col)+"'"
                                    + "WHERE rfc='"+celdaRfc+"'");
                                    }
                                    if(col == 5){
                                    prest = conn.prepareStatement("UPDATE personas SET tel='"+jTClientes.getValueAt(i, col)+"'"
                                    + "WHERE rfc='"+celdaRfc+"'");
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
                            //}
                        }
                    }
                }
            });
        }catch(Exception e){}
        return listapersona;
    }
    
    public void showData(){
        ArrayList<Personas> listaPersona = listpersona();
        DefaultTableModel model = (DefaultTableModel)jTClientes.getModel();
        Object[] columna = new Object[6];
        for(int i = 0; i < listaPersona.size(); i++){
            columna[0] = listaPersona.get(i).getRfc();
            columna[1] = listaPersona.get(i).getNombre();
            columna[2] = listaPersona.get(i).getApellido();
            columna[3] = listaPersona.get(i).getDireccion();
            columna[4] = listaPersona.get(i).getCodpostal();
            columna[5] = listaPersona.get(i).getTel();
            model.addRow(columna); 
            
        }

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    public void clearjTClient(){
        DefaultTableModel model = (DefaultTableModel) jTClientes.getModel();
        int b = jTClientes.getRowCount()-1;
        for (int i = b; i >= 0; i--) {           
        model.removeRow(model.getRowCount()-1);
        } 
        
    }

    public void popUpTable(){

        JPopupMenu jpopUp = new JPopupMenu();
        JMenuItem jmenu1 = new JMenuItem("Vehiculos", new ImageIcon(getClass().getResource("/Views/nissan-juke-side-v.png")));
        JMenuItem jmenu2 = new JMenuItem("Agregar vehiculo", new ImageIcon(getClass().getResource("/Views/nissan-juke-side-v.png")));
        JMenuItem jmenu3 = new JMenuItem("Eliminar este registro de cliente", new ImageIcon(getClass().getResource("/Views/Trash-Can.png")));
        JMenuItem jmenu4 = new JMenuItem("Historial de compras de refacciones", new ImageIcon(getClass().getResource("/Views/Trash-Can.png")));
        jmenu1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jTClientes.getSelectedRow();
                MouseEvent event = null;
                Point j = jTClientes.getPopupLocation(event);
                TableModel model = jTClientes.getModel();
                celdaRfc = model.getValueAt(i, 0).toString();
                //testing validating if client exist
                 Vehiculo vehiculo;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from vehiculo where rfc='"+celdaRfc+"'";
        Statement st;
        ResultSet rs;
                try {
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                    if(rs.next()){
                        conVehiculosCliente cvc = new conVehiculosCliente();
                        cvc.setVisible(true);
                    }else{
                        JOptionPane.showMessageDialog(null, "El cliente seleccionado no cuenta con vehiculo registrado: " + JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                   // Logger.getLogger(conClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
        
                //end testing
                
                
            }
        });
        jmenu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                MouseEvent event = null;
                Point j = jTClientes.getPopupLocation(event);
                TableModel model = jTClientes.getModel();
                int i = jTClientes.getSelectedRow();
                celdaRfc = model.getValueAt(i, 0).toString();
                regVehiculo rv = new regVehiculo();
                rv.setVisible(true);
            }
        });
        jmenu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TableModel model = jTClientes.getModel();
                int i = jTClientes.getSelectedRow();
                celdaRfc = model.getValueAt(i, 0).toString();
                //testing validating if client exist
                 Personas personas;
        Connection conn = Conexion.ConnectDB();
        String query = "select * from personas where rfc='"+celdaRfc+"'";
        Statement st;
        ResultSet rs;
                try {
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                    if(rs.next()){
                        int confirmar = JOptionPane.showConfirmDialog(null, "Al eleiminar el registro del cliente con RFC: "+celdaRfc+ "\n Se debe tomar en cuenta que se \n"+
                                "eliminaran todos los datos relacionados \n" +"¿Desea eliminar el registro seleccionado?");
                        if(confirmar == JOptionPane.YES_OPTION){
                            PreparedStatement cs = conn.prepareStatement("delete from personas where rfc = '"+celdaRfc+"' && tipo = 'cliente'");
                            
                            if(cs.executeUpdate() > 0){
                                JOptionPane.showMessageDialog(null, "Se han eliminado los datos pertenecientes al RFC/codigo Cliente:. \n"+celdaRfc, "Operación Exitosa!!!", 
                                JOptionPane.INFORMATION_MESSAGE);
                                clearjTClient();
                                showData();
                            }else{
                                JOptionPane.showMessageDialog(null, "No se ha podido realizar eliminacion de los datos\n"
                                + "Inténtelo nuevamente. \n", "Error en la operación!!", 
                                JOptionPane.ERROR_MESSAGE);
                            }
                            //
                            
                    }
                    }
                    
                } catch (SQLException ex) {
                   // Logger.getLogger(conClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
        
                //end testing
            }
        });
        jmenu4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = jTClientes.getSelectedRow();
                MouseEvent event = null;
                Point j = jTClientes.getPopupLocation(event);
                TableModel model = jTClientes.getModel();
                celdaRfc = model.getValueAt(i, 0).toString();
                //testing validating if client exist
                 Detallevref detallevref;
        Connection conn = Conexion.ConnectDB();
        String query = "select rfc from personas where rfc = '"+celdaRfc+"'";
        //String query = "select facturavref.idfacturavref, fechavref, nombre, apellido,  refacciones.codRefaccion,descripcion, precio, cantidadref, (refacciones.preciopublico* cantidadref) as subtotal,((refacciones.preciopublico*cantidadref) + ((refacciones.preciopublico* cantidadref) * 0.16)) as total from personas inner join facturavref on personas.rfc=facturavref.rfc inner join detallevref on detallevref.idfacturavref=facturavref.idfacturavref inner join refacciones on detallevref.codRefaccion= refacciones.codRefaccion where rfc = '"+Personas.celdaRfc+"'";
        Statement st;
        ResultSet rs;
                try {
                    st = conn.createStatement();
                    rs = st.executeQuery(query);
                    if(rs.next()){
                        conVentaRef cvr = new conVentaRef();
                        cvr.setVisible(true);
                    }else{
                        JOptionPane.showMessageDialog(null, "El cliente seleccionado no cuenta con compra de refacciones: " + JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                   // Logger.getLogger(conClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        jpopUp.add(jmenu1);
        jpopUp.add(jmenu2);
        jpopUp.add(jmenu3);
        jpopUp.add(jmenu4);
        jTClientes.setComponentPopupMenu(jpopUp);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTClientes = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFilterClients = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(525, 415));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(53, 100, 148));
        jPanel1.setPreferredSize(new java.awt.Dimension(142, 352));

        jButton1.setText("Servicios");

        jButton2.setText("Ventas");

        jButton3.setText("Reportes");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RFC/CURP", "Nombre", "Apellido", "Direccion", "C. Postal", "Telefono"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTClientes.setFillsViewportHeight(true);
        jTClientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTClientes);
        jTClientes.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTClientes.getColumnModel().getColumnCount() > 0) {
            jTClientes.getColumnModel().getColumn(0).setResizable(false);
            jTClientes.getColumnModel().getColumn(0).setPreferredWidth(40);
            jTClientes.getColumnModel().getColumn(1).setResizable(false);
            jTClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTClientes.getColumnModel().getColumn(2).setResizable(false);
            jTClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTClientes.getColumnModel().getColumn(3).setResizable(false);
            jTClientes.getColumnModel().getColumn(3).setPreferredWidth(285);
            jTClientes.getColumnModel().getColumn(4).setResizable(false);
            jTClientes.getColumnModel().getColumn(4).setPreferredWidth(40);
            jTClientes.getColumnModel().getColumn(5).setResizable(false);
            jTClientes.getColumnModel().getColumn(5).setPreferredWidth(50);
        }

        jLabel1.setText("Ingresar Nombre del cliente:");

        jTextFilterClients.setToolTipText("");
        jTextFilterClients.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFilterClientsKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jTextFilterClients, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFilterClients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );

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
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
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
TableRowSorter trsClients;
    private void jTextFilterClientsKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFilterClientsKeyTyped
        // TODO add your handling code here:
        jTextFilterClients.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                trsClients.setRowFilter(RowFilter.regexFilter("(?i)"+ jTextFilterClients.getText(), 1,2));
            }    
        });
        DefaultTableModel model = (DefaultTableModel)jTClientes.getModel();
        trsClients = new TableRowSorter(model);
        jTClientes.setRowSorter(trsClients);
    }//GEN-LAST:event_jTextFilterClientsKeyTyped
//
//        jTClientes.addMouseMotionListener (new MouseMotionListener() {
//            private Point point;
//            @Override
//            public void mouseMoved (MouseEvent me) {
//   int row = jTClientes.columnAtPoint(me.getPoint());
//   if (row >=0)
//         changeSelection (row, 0, true, true);
//}
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//            }
//
//            private void changeSelection(int row, int i, boolean b, boolean b0) {
//            }
//
//
//        });
    

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
            java.util.logging.Logger.getLogger(conClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(conClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(conClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(conClientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new conClientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTable jTClientes;
    private javax.swing.JTextField jTextFilterClients;
    // End of variables declaration//GEN-END:variables
}
