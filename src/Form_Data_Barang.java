/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.*;
import DatabaseConnection.DatabaseConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
/**
 *
 * @author USER
 */
public class Form_Data_Barang extends javax.swing.JFrame {

    /**
     * Creates new form Form_Data_Barang
     */
    public Form_Data_Barang() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tampilkanDataBarang();
        loadTotalData();
        setupComboBoxListener();
        jTable1.setDefaultEditor(Object.class, null);
        
        jTable1.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow == -1) {
                jTextField2.setText(""); // Clear jika tidak ada yang dipilih
            } else {
                Object value = jTable1.getValueAt(selectedRow, 0);
                jTextField2.setText(value != null ? value.toString() : "");
            }
        }
        });
        
        //kode untuk combo box
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(
        new String[] { "Pilihan 1", "Pilihan 2", "Pilihan 3" }
        ));

        // Contoh 2: Mengisi ComboBox dari Database (MySQL)
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_pabrik_helm", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nama_supplier FROM data_supplier");

            // Buat model kosong terlebih dahulu
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Tambahkan data dari ResultSet ke model
            while (rs.next()) {
                model.addElement(rs.getString("nama_supplier"));
            }

            // Set model ke jComboBox1
            jComboBox3.setModel(model);
            jComboBox3.setSelectedIndex(-1);

            // Tutup koneksi
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            }
    }
    
    private void setupComboBoxListener() {
    jComboBox3.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectednama_supplier = (String) jComboBox1.getSelectedItem();
            if (selectednama_supplier != null && !selectednama_supplier.isEmpty()) {
                isiFieldOtomatis(selectednama_supplier);
            }
        }
    });
    }

    // Method untuk mengisi field otomatis
    private void isiFieldOtomatis(String nama_supplier) {
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT nama_supplier FROM data_supplier WHERE nama_supplier=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama_supplier);
            ResultSet rs = stmt.executeQuery();
        
            } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error mengambil data barang: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void tampilkanDataBarang(){
        try{
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT kode_barang, nama_barang, kategori, satuan, stok, harga, nama_supplier, deskripsi FROM data_barang";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kode Barang");
            model.addColumn("Nama Barang");
            model.addColumn("Kategori");
            model.addColumn("Satuan");
            model.addColumn("Stok");
            model.addColumn("Harga");
            model.addColumn("Nama Supplier");
            model.addColumn("Deskripsi");
            while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("kategori"),
                rs.getString("satuan"), 
                rs.getInt("stok"),
                rs.getDouble("harga"),
                rs.getString("nama_supplier"),
                rs.getString("deskripsi")
                });
            }
        jTable1.setModel(model);
        jTable1.clearSelection();
        jTextField2.setText("");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }
    
    private void bersihkan(){
        jTextField4.setText("");
        jTextField6.setText("");
        jComboBox1.setSelectedIndex(-1);
        jComboBox2.setSelectedIndex(-1);
        jTextField7.setText("");
        jTextField8.setText("");
        jComboBox3.setSelectedIndex(-1);
        jTextField4.setEnabled(true);
    }
    
    private int TotalData(){
        int total = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.connect();
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) AS total FROM data_barang";
            rs = stmt.executeQuery(sql);
            
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return total;
    }
    
    private void loadTotalData(){
        int total = TotalData();
        jTextField1.setText(String.valueOf(total));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField5 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField7 = new javax.swing.JTextField();
        jComboBox3 = new javax.swing.JComboBox<>();
        jTextField8 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        jTextField5.setText("jTextField5");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1275, 537));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("DATA BARANG");

        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        jLabel2.setText("Total Barang");

        jTextField1.setEditable(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Delete");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(0, 51, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Edit");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 255));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Refresh");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 255));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Search");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Data Barang"));

        jLabel3.setText("Kode Barang");

        jLabel4.setText("Nama Barang");

        jLabel5.setText("Katogori");

        jLabel6.setText("Satuan");

        jLabel7.setText("Harga");

        jLabel8.setText("Supplier");

        jLabel9.setText("Deskripsi");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bahan Baku", "Komponen", "Bahan Kimia", "Aksesoris", "Tools", "Packaging", "Spare Part" }));
        jComboBox1.setSelectedIndex(-1);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lembar", "Pcs", "Meter", "Set", "Liter", "Roll" }));
        jComboBox2.setSelectedIndex(-1);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.setSelectedIndex(-1);
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox3ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(0, 51, 255));
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Add Data");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(24, 24, 24)
                        .addComponent(jTextField4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField7)
                            .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, 182, Short.MAX_VALUE)
                            .addComponent(jTextField6)
                            .addComponent(jTextField8))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTextField8))
                .addGap(18, 18, 18)
                .addComponent(jButton5)
                .addContainerGap())
        );

        jLabel10.setText("Cari Data");

        jButton6.setBackground(new java.awt.Color(255, 51, 51));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Dashboard");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(0, 921, Short.MAX_VALUE))
                            .addComponent(jScrollPane3)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jButton4))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(200, 200, 200)
                        .addComponent(jButton6)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        
        String kode_barang = jTextField4.getText().trim();
        String nama_barang = jTextField6.getText().trim();
        String kategori = (String) jComboBox1.getSelectedItem();
        String satuan = (String) jComboBox2.getSelectedItem();
        int stok = 0;
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        String nama_supplier = (String) jComboBox3.getSelectedItem();
        String deskripsi = jTextField8.getText().trim();
        
        if (kode_barang.isEmpty() || nama_barang.isEmpty() || jComboBox1.getSelectedIndex() == -1 || jComboBox2.getSelectedIndex() == -1 || jTextField7.getText().isEmpty() || nama_supplier.isEmpty() || jComboBox3.getSelectedIndex() == -1 || deskripsi.isEmpty()){
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }
        
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO data_barang (kode_barang, nama_barang, kategori, satuan, stok, harga, nama_supplier, deskripsi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kode_barang);
            stmt.setString(2, nama_barang);
            stmt.setString(3, kategori);
            stmt.setString(4, satuan);
            stmt.setInt(5, stok);
            stmt.setDouble(6, harga);
            stmt.setString(7, nama_supplier);
            stmt.setString(8, deskripsi);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data barang berhasil disimpan.");
            bersihkan();
            tampilkanDataBarang();
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        
        loadTotalData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
        "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String kode_barang = jTable1.getValueAt(selectedRow, 0).toString(); // ambil ID di kolom 0

            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM data_barang WHERE kode_barang = ?")) {
                stmt.setString(1, kode_barang);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                tampilkanDataBarang(); // refresh tabel

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        loadTotalData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String keyword = jTextField3.getText().trim();

        try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT * FROM data_barang WHERE kode_barang LIKE ? OR nama_barang LIKE ? OR kategori LIKE ? OR satuan LIKE ? OR stok LIKE ? OR harga LIKE ? OR nama_supplier LIKE ? OR deskripsi LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ps.setString(4, "%" + keyword + "%");
        ps.setString(5, "%" + keyword + "%");
        ps.setString(6, "%" + keyword + "%");
        ps.setString(7, "%" + keyword + "%");
        ps.setString(8, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Satuan");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Supplier");
        model.addColumn("Deskripsi");
        
         while (rs.next()) {
            model.addRow(new Object[] {
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("kategori"),
                rs.getString("satuan"),
                rs.getInt("stok"),
                rs.getDouble("harga"),
                rs.getString("nama_supplier"),
                rs.getString("deskripsi")
            });
        }

        jTable1.setModel(model);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tampilkanDataBarang();
        bersihkan();
        jTextField3.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Klik dua kali data yang ingin diedit.");
            return;
        }

        String kode_barang = jTextField4.getText().trim();
        String nama_barang = jTextField6.getText().trim();
        String kategori = (String) jComboBox1.getSelectedItem();
        String satuan = (String) jComboBox2.getSelectedItem();
        int stok = (int) jTable1.getValueAt(selectedRow, 4);
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        String nama_supplier = (String) jComboBox3.getSelectedItem();
        String deskripsi = jTextField8.getText().trim();
    
        // Set data ke form (sesuaikan nama field dengan form Anda)
        jTextField4.setEnabled(false);
        jTextField4.setText(kode_barang);
        jTextField6.setText(nama_barang);
        jComboBox1.setSelectedItem(kategori);
        jComboBox2.setSelectedItem(satuan);
        jTextField7.setText(String.valueOf(harga));
        jComboBox3.setSelectedItem(nama_supplier);
        jTextField8.setText(deskripsi);
    
        // Disable ID field agar tidak bisa diedit
        jTextField1.setEnabled(false);
        
        try (Connection conn = DatabaseConnection.connect()) {
                String sql = "UPDATE data_barang SET nama_barang=?, kategori=?, satuan=?, stok=?, harga=?, nama_supplier=?, deskripsi=? WHERE kode_barang=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
            
                   stmt.setString(1, nama_barang);
                   stmt.setString(2, kategori);
                   stmt.setString(3, satuan);
                   stmt.setInt(4, stok);
                   stmt.setDouble(5, harga);
                   stmt.setString(6, nama_supplier);
                   stmt.setString(7, deskripsi);
                   stmt.setString(8, kode_barang);
                   stmt.executeUpdate();
                   
                   
                   bersihkan();
                   tampilkanDataBarang();
                   jTextField1.setEnabled(true);
            }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
            }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox3ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        new Dashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 1){
            int row = jTable1.getSelectedRow();
            int col = 0;
    
            if (row >= 0) {
            Object value = jTable1.getValueAt(row, col);
            jTextField2.setText(value.toString());
            }
        }
        
        if (evt.getClickCount() == 2){
            int row =jTable1.getSelectedRow();
            
            
            String kode_barang = jTable1.getValueAt(row, 0).toString();
            String nama_barang = jTable1.getValueAt(row, 1).toString();
            String kategori = jTable1.getValueAt(row, 2).toString();
            String satuan = jTable1.getValueAt(row, 3).toString();
            Double harga = Double.parseDouble(jTable1.getValueAt(row, 5).toString());
            String nama_supplier = jTable1.getValueAt(row, 6).toString();
            String deskripsi = jTable1.getValueAt(row, 7).toString();
            
            // Set data ke form (sesuaikan nama field dengan form Anda)
            jTextField4.setEnabled(false);
            jTextField4.setText(kode_barang);
            jTextField6.setText(nama_barang);
            jComboBox1.setSelectedItem(kategori);
            jComboBox2.setSelectedItem(satuan);
            jTextField7.setText(String.valueOf(harga));
            jComboBox3.setSelectedItem(nama_supplier);
            jTextField8.setText(deskripsi);
            }
    }//GEN-LAST:event_jTable1MouseClicked

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
            java.util.logging.Logger.getLogger(Form_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Data_Barang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Data_Barang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
