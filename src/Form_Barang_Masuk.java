/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.*;
import DatabaseConnection.DatabaseConnection;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Vector;
import java.text.ParseException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.JFileChooser;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JFrame;


/**
 *
 * @author USER
 */
public class Form_Barang_Masuk extends javax.swing.JFrame {

    /**
     * Creates new form Form_Barang_Masuk
     */
    public Form_Barang_Masuk() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        tampilkanDataMasuk();
        loadTotalData();
        setupComboBoxListener();
        setupMasukFieldListener();
        jTable1.setDefaultEditor(Object.class, null);
        
        jTable1.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = jTable1.getSelectedRow();
            if (selectedRow != -1) {
                Object value = jTable1.getValueAt(selectedRow, 0);
            }
        }
        });
        
        //kode untuk combo box
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
        new String[] { "Pilihan 1", "Pilihan 2", "Pilihan 3" }
        ));

        // Contoh 2: Mengisi ComboBox dari Database (MySQL)
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_pabrik_helm", "root", "");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT kode_barang FROM data_barang");

            // Buat model kosong terlebih dahulu
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

            // Tambahkan data dari ResultSet ke model
            while (rs.next()) {
                model.addElement(rs.getString("kode_barang"));
            }

            // Set model ke jComboBox1
            jComboBox1.setModel(model);
            jComboBox1.setSelectedIndex(-1);

            // Tutup koneksi
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            }
    }
    private void setupComboBoxListener() {
    jComboBox1.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedKodeBarang = (String) jComboBox1.getSelectedItem();
            if (selectedKodeBarang != null && !selectedKodeBarang.isEmpty()) {
                isiFieldOtomatis(selectedKodeBarang);
            }
        }
    });
    }

    // Method untuk mengisi field otomatis
    private void isiFieldOtomatis(String kodeBarang) {
        try {
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT nama_barang, nama_supplier, kategori, satuan, harga, stok FROM data_barang WHERE kode_barang = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, kodeBarang);
            ResultSet rs = stmt.executeQuery();
        
            if (rs.next()) {
                // Isi field dengan data dari database
                jTextField3.setText(rs.getString("nama_barang"));
                jTextField4.setText(rs.getString("nama_supplier"));
                jTextField5.setText(rs.getString("kategori"));
                jTextField6.setText(rs.getString("satuan"));
                jTextField7.setText(rs.getString("harga"));
                jTextField8.setText(rs.getString("stok"));
            
                // Reset field masuk dan perhitungan
                jTextField9.setText("");
                jTextField10.setText("");
                jTextField11.setText("");
            } else {
                // Kosongkan field jika tidak ditemukan
            
                JOptionPane.showMessageDialog(this, "Data barang tidak ditemukan.");
            }
        
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error mengambil data barang: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void tampilkanDataMasuk(){
        try{
            Connection conn = DatabaseConnection.connect();
            String sql = "SELECT kode_transaksi, tanggal, kode_barang, nama_barang, nama_supplier, kategori, stok, satuan, harga, total_masuk, total_harga, total_stok FROM barang_masuk";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Kode Transaksi");
            model.addColumn("Tanggal");
            model.addColumn("Kode Barang");
            model.addColumn("Nama Barang");
            model.addColumn("Nama Supplier");
            model.addColumn("Kategori");
            model.addColumn("Stok");
            model.addColumn("Satuan");
            model.addColumn("Harga");
            model.addColumn("Total Masuk");
            model.addColumn("Total Harga");
            model.addColumn("Total Stok");
            
            while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString("kode_transaksi"),
                rs.getString("tanggal"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("nama_supplier"),
                rs.getString("kategori"),
                rs.getInt("stok"),
                rs.getString("satuan"),
                rs.getDouble("harga"),
                rs.getInt("total_masuk"),
                rs.getDouble("total_harga"),
                rs.getInt("total_stok"),
                });
            }
            jTable1.setModel(model);
            jTable1.clearSelection();
            jTextField13.setText("");
            jTextField14.setText("");
            jTextField15.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private int TotalData(){
        int total = 0;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.connect();
            stmt = conn.createStatement();
            String sql = "SELECT COUNT(*) AS total FROM barang_masuk";
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
        jTextField12.setText(String.valueOf(total));
    }
    
    private void bersihkan(){
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField6.setText("");
        jTextField7.setText("");
        jTextField8.setText("");
        jTextField9.setText("");
        jTextField10.setText("");
        jTextField11.setText("");
        jComboBox1.setSelectedIndex(-1);
        
        jTable1.clearSelection();
        jTextField13.setText("");
        jTextField14.setText("");
        jTextField15.setText("");
    }
    
    private String generateKodeTransaksi() {
    try {
        Connection conn = DatabaseConnection.connect();
        // Cek jumlah data yang ada
        String countSql = "SELECT COUNT(*) AS total FROM barang_masuk";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(countSql);
        
        int total = 0;
        if (rs.next()) {
            total = rs.getInt("total");
        }
        
        // Jika tidak ada data, mulai dari KLR001
        if (total == 0) {
            return "MSK001";
        } else {
            // Ambil kode terakhir dari database
            String lastCodeSql = "SELECT kode_transaksi FROM barang_masuk ORDER BY kode_transaksi DESC LIMIT 1";
            ResultSet lastCodeRs = stmt.executeQuery(lastCodeSql);
            
            if (lastCodeRs.next()) {
                String lastCode = lastCodeRs.getString("kode_transaksi");
                // Ekstrak angka dari kode terakhir
                int lastNumber = Integer.parseInt(lastCode.substring(3));
                // Format dengan leading zeros
                return String.format("MSK%03d", lastNumber + 1);
            }
        }
        
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error generate kode transaksi: " + e.getMessage());
        e.printStackTrace();
    }
    
    return "MSK001"; // fallback
    }
    
    private void updateStokBarangMasuk(String kode_barang, int total_masuk) {
    try (Connection conn = DatabaseConnection.connect()) {
        // Ambil stok saat ini
        String sqlSelect = "SELECT stok FROM data_barang WHERE kode_barang = ?";
        PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
        stmtSelect.setString(1, kode_barang);
        ResultSet rs = stmtSelect.executeQuery();
        
        int stok = 0;
        if (rs.next()) {
            stok = rs.getInt("stok");
        }
        
        // Hitung stok baru
        int total_stok = stok + total_masuk;
        
        // Update stok
        String sqlUpdate = "UPDATE data_barang SET stok = ? WHERE kode_barang = ?";
        PreparedStatement stmtUpdate = conn.prepareStatement(sqlUpdate);
        stmtUpdate.setInt(1, total_stok);
        stmtUpdate.setString(2, kode_barang);
        stmtUpdate.executeUpdate();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error update stok: " + e.getMessage());
        e.printStackTrace();
    }
}

    private void calculateTotalStok() {
    if (!jTextField8.getText().isEmpty() && !jTextField11.getText().isEmpty()) {
        try {
            // Ambil nilai stok dan keluar
            int stok = jTextField8.getText().isEmpty() ? 0 : Integer.parseInt(jTextField8.getText());
            int keluar = jTextField11.getText().isEmpty() ? 0 : Integer.parseInt(jTextField11.getText());
        
            // Hitung total stok
            int totalStok = stok + keluar;
        
            // Update field total stok
            jTextField10.setText(String.valueOf(totalStok));
        
            // Hitung total harga jika harga tersedia
            if (!jTextField7.getText().isEmpty()) {
                double harga = Double.parseDouble(jTextField7.getText());
                double totalHarga = harga * keluar;
                jTextField9.setText(String.valueOf(totalHarga));
            }
        } catch (NumberFormatException e) {
            // Tangani jika input bukan angka
            jTextField9.setText("Error");
            jTextField10.setText("Error");
        } 
    }else {
        jTextField9.setText("");
        jTextField10.setText("");
    }
    }

    private void setupMasukFieldListener() {
    jTextField11.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            calculateTotalStok();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            calculateTotalStok();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            calculateTotalStok();
        }
    });
    }
    
    private void exportToExcel(JTable table, File file) {
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            for (int i = 0; i < table.getColumnCount(); i++) {
                bw.write(table.getColumnName(i) + "\t");
            }
            bw.newLine();

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    Object value = table.getValueAt(i, j);
                    bw.write((value == null ? "" : value.toString()) + "\t");
                }
                bw.newLine();
            }

            bw.close();
            fw.close();

            JOptionPane.showMessageDialog(null, "Data berhasil diekspor ke Excel.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal ekspor: " + e.getMessage());
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

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextField12 = new javax.swing.JTextField();
        jTextField13 = new javax.swing.JTextField();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField16 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1275, 537));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel1.setText("DATA BARANG MASUK");

        jSeparator1.setForeground(new java.awt.Color(51, 51, 51));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Form Data Barang Masuk"));

        jLabel2.setText("Kode Transaksi");

        jLabel3.setText("Tanggal Masuk");

        jLabel4.setText("Kode Barang");

        jLabel5.setText("Nama Barang");

        jLabel6.setText("Nama Supplier");

        jLabel7.setText("Kategori");

        jLabel8.setText("Satuan");

        jLabel9.setText("Harga");

        jTextField1.setEditable(false);

        jLabel10.setText("Stok Barang");

        jTextField3.setEditable(false);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.setSelectedIndex(-1);
        jComboBox1.setMinimumSize(new java.awt.Dimension(64, 22));

        jTextField4.setEditable(false);

        jTextField5.setEditable(false);

        jTextField6.setEditable(false);

        jTextField7.setEditable(false);

        jTextField8.setEditable(false);
        jTextField8.setPreferredSize(new java.awt.Dimension(75, 28));

        jLabel11.setText("Masuk");

        jLabel12.setText("Total Harga");

        jTextField9.setEditable(false);

        jLabel13.setText("Total Stok");

        jTextField10.setEditable(false);
        jTextField10.setPreferredSize(new java.awt.Dimension(75, 28));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(16, 16, 16)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(25, 25, 25)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField1)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField4)
                            .addComponent(jTextField5)
                            .addComponent(jTextField6)
                            .addComponent(jTextField7)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(39, 39, 39)
                                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel12)
                                            .addGap(87, 87, 87))
                                        .addComponent(jTextField9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel14.setText("Total Barang");

        jTextField12.setEditable(false);
        jTextField12.setMinimumSize(new java.awt.Dimension(70, 22));
        jTextField12.setPreferredSize(new java.awt.Dimension(70, 24));

        jTextField13.setEditable(false);

        jTextField14.setEditable(false);

        jTextField15.setEditable(false);
        jTextField15.setMinimumSize(new java.awt.Dimension(70, 22));

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
        jScrollPane1.setViewportView(jTable1);

        jButton1.setBackground(new java.awt.Color(0, 51, 255));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Search");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Cari Barang");

        jButton2.setBackground(new java.awt.Color(0, 51, 255));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 51, 255));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Edit");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(0, 51, 255));
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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

        jButton6.setBackground(new java.awt.Color(102, 102, 102));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("New");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel16.setText("Tanggal Awal : Tgl/Bln/Thn");

        jLabel17.setText("Tanggal Akhir : Tgl/Bln/Thn");

        jButton7.setBackground(new java.awt.Color(0, 51, 255));
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Print");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(255, 51, 51));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Dashboard");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

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
                            .addComponent(jLabel1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(12, 12, 12)
                                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 351, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1))
                                    .addComponent(jLabel15)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel16))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton7))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton8)
                                .addGap(18, 18, 18)
                                .addComponent(jButton6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton6)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton3)
                    .addComponent(jButton2)
                    .addComponent(jButton8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton7)
                                .addGap(14, 14, 14))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       // Generate kode transaksi baru
        String newKode = generateKodeTransaksi();
        bersihkan();
        jTextField1.setText(newKode);
        
    
        // Set fokus ke field tanggal
        jTextField2.requestFocus();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String kode_transaksi = jTextField1.getText().trim();
        String tanggal = jTextField2.getText().trim();
        String kode_barang = (String) jComboBox1.getSelectedItem();
        String nama_barang = jTextField3.getText().trim();
        String nama_supplier = jTextField4.getText().trim();
        String kategori = jTextField5.getText().trim(); 
        String satuan = jTextField6.getText().trim();
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        int stok = Integer.parseInt(jTextField8.getText().trim());
        int total_masuk = Integer.parseInt(jTextField11.getText().trim());
        int total_stok = Integer.parseInt(jTextField10.getText().trim());
        Double total_harga = Double.parseDouble(jTextField9.getText().trim());
        
        if (tanggal.isEmpty() || jComboBox1.getSelectedIndex() == -1 || total_masuk == 0){
            JOptionPane.showMessageDialog(this, "Semua field wajib diisi!");
            return;
        }
        
        if (!tanggal.matches("\\d{2}/\\d{2}/\\d{4}")) {
        JOptionPane.showMessageDialog(this, "Format tanggal harus DD/MM/YYYY");
        return;
        }
        
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "INSERT INTO barang_masuk (kode_transaksi, tanggal, kode_barang, nama_barang, nama_supplier, kategori, satuan, harga, stok, total_masuk, total_harga, total_stok) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, kode_transaksi);
            stmt.setString(2, tanggal);
            stmt.setString(3, kode_barang);
            stmt.setString(4, nama_barang);
            stmt.setString(5, nama_supplier);
            stmt.setString(6, kategori);
            stmt.setString(7, satuan);
            stmt.setDouble(8, harga);
            stmt.setInt(9,stok );
            stmt.setInt(10, total_masuk);
            stmt.setDouble(11, total_harga);
            stmt.setInt(12,  total_stok);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data Barang Masuk berhasil disimpan.");
            bersihkan();
            tampilkanDataMasuk();
            updateStokBarangMasuk(kode_barang, total_masuk);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
        loadTotalData();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int selectedRow = jTable1.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
        "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            String kode_transaksi = jTable1.getValueAt(selectedRow, 0).toString(); // ambil ID di kolom 0
            String kode_barang = jTable1.getValueAt(selectedRow, 2).toString();
            int total_masuk = Integer.parseInt(jTable1.getValueAt(selectedRow, 9).toString());
            
            try (Connection conn = DatabaseConnection.connect();
                PreparedStatement stmt = conn.prepareStatement("DELETE FROM barang_masuk WHERE kode_transaksi = ?")) {
                stmt.setString(1, kode_transaksi);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this, "Data berhasil dihapus.");
                tampilkanDataMasuk(); // refresh tabel
                updateStokBarangMasuk(kode_barang, - total_masuk); // Mengurangi stok

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        loadTotalData();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Klik dua kali data yang ingin diedit.");
            return;
        }

        String kode_transaksi = jTextField1.getText().trim();
        String tanggal = jTextField2.getText().trim();
        String kode_barang = (String) jComboBox1.getSelectedItem();
        String nama_barang = jTextField3.getText().trim();
        String nama_supplier = jTextField4.getText().trim();
        String kategori = jTextField5.getText().trim(); 
        String satuan = jTextField6.getText().trim();
        Double harga = Double.parseDouble(jTextField7.getText().trim());
        int stok = Integer.parseInt(jTextField8.getText().trim());
        int total_masuk = Integer.parseInt(jTextField11.getText().trim());
        int total_stok = Integer.parseInt(jTextField10.getText().trim());
        Double total_harga = Double.parseDouble(jTextField9.getText().trim());
    
        // Set data ke form (sesuaikan nama field dengan form Anda)
        jTextField1.setText(kode_transaksi);
        jTextField2.setText(tanggal);
        jComboBox1.setSelectedItem(kode_barang);
        jTextField3.setText(nama_barang);
        jTextField4.setText(nama_supplier);
        jTextField5.setText(kategori);
        jTextField6.setText(satuan);
        jTextField7.setText(String.valueOf(harga));
        jTextField8.setText(String.valueOf(stok));
        jTextField10.setText(String.valueOf(total_stok));
        jTextField11.setText(String.valueOf(total_masuk));
        jTextField9.setText(String.valueOf(total_harga));
        
        try (Connection conn = DatabaseConnection.connect()) {
            String sql = "UPDATE barang_masuk SET tanggal=?, kode_barang=?, nama_barang=?, nama_supplier=?, kategori=?, satuan=?, harga=?, stok=?, total_masuk=?, total_harga=?, total_stok=? WHERE kode_transaksi=?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, tanggal);
            stmt.setString(2, kode_barang);
            stmt.setString(3, nama_barang);
            stmt.setString(4, nama_supplier);
            stmt.setString(5, kategori);
            stmt.setString(6, satuan);
            stmt.setDouble(7, harga);
            stmt.setInt(8,stok );
            stmt.setInt(9, total_masuk);
            stmt.setDouble(10, total_harga);
            stmt.setInt(11,  total_stok);
            stmt.setString(12, kode_transaksi);
            stmt.executeUpdate();
                   
                   
            bersihkan();
            tampilkanDataMasuk();
            updateStokBarangMasuk(kode_barang, total_masuk);
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal: " + e.getMessage());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        tampilkanDataMasuk();
        bersihkan();
        jTextField16.setText("");
        jTextField17.setText("");
        jTextField18.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       String keyword = jTextField16.getText().trim();

        try (Connection conn = DatabaseConnection.connect()) {
        String sql = "SELECT * FROM barang_masuk WHERE kode_transaksi LIKE ? OR tanggal LIKE ? OR kode_barang LIKE ? OR nama_barang LIKE ? OR nama_supplier LIKE ? OR kategori LIKE ? OR stok LIKE ? OR satuan LIKE ? OR harga LIKE ? OR total_masuk LIKE ? OR total_stok LIKE ? OR total_harga LIKE ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ps.setString(3, "%" + keyword + "%");
        ps.setString(4, "%" + keyword + "%");
        ps.setString(5, "%" + keyword + "%");
        ps.setString(6, "%" + keyword + "%");
        ps.setString(7, "%" + keyword + "%");
        ps.setString(8, "%" + keyword + "%");
        ps.setString(9, "%" + keyword + "%");
        ps.setString(10, "%" + keyword + "%");
        ps.setString(11, "%" + keyword + "%");
        ps.setString(12, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Nama Supplier");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Satuan");
        model.addColumn("Harga");
        model.addColumn("Total Masuk");
        model.addColumn("Total Harga");
        model.addColumn("Total Stok");
        
        while (rs.next()) {
            model.addRow(new Object[] {
                rs.getString("kode_transaksi"),
                rs.getString("tanggal"),
                rs.getString("kode_barang"),
                rs.getString("nama_barang"),
                rs.getString("nama_supplier"),
                rs.getString("kategori"),
                rs.getInt("stok"),
                rs.getString("satuan"),
                rs.getDouble("harga"),
                rs.getInt("total_masuk"),
                rs.getDouble("total_harga"),
                rs.getInt("total_stok"),
                });
        }

        jTable1.setModel(model);
        jTable1.clearSelection();
        bersihkan();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
       if (evt.getClickCount() == 1){
            int row = jTable1.getSelectedRow();
            int col = 0;
    
            if (row >= 0) {
            Object kode_transaksi = jTable1.getValueAt(row, 0);
            Object nama_barang = jTable1.getValueAt(row, 3);
            Object total_keluar = jTable1.getValueAt(row, 9);
            jTextField13.setText(kode_transaksi.toString());
            jTextField14.setText(nama_barang.toString());
            jTextField15.setText(total_keluar.toString());
            }
        }
        
        if (evt.getClickCount() == 2){
            int row =jTable1.getSelectedRow();
            
            
            String kode_transaksi = jTable1.getValueAt(row, 0).toString();
            String tanggal = jTable1.getValueAt(row, 1).toString();
            String kode_barang = jTable1.getValueAt(row, 2).toString();
            String nama_barang = jTable1.getValueAt(row, 3).toString();
            String nama_supplier = jTable1.getValueAt(row, 4).toString();
            String kategori = jTable1.getValueAt(row, 5).toString();
            String stok = jTable1.getValueAt(row, 6).toString();
            String satuan = jTable1.getValueAt(row, 7).toString();
            String harga = jTable1.getValueAt(row, 8).toString();
            String total_masuk = jTable1.getValueAt(row, 9).toString();
            String total_harga = jTable1.getValueAt(row, 10).toString();
            String total_stok = jTable1.getValueAt(row, 11).toString();
            
            // Set data ke form (sesuaikan nama field dengan form Anda)
            jTextField1.setText(kode_transaksi);
            jTextField2.setText(tanggal);
            jComboBox1.setSelectedItem(kode_barang);
            jTextField3.setText(nama_barang);
            jTextField4.setText(nama_supplier);
            jTextField5.setText(kategori);
            jTextField6.setText(satuan);
            jTextField7.setText(harga);
            jTextField8.setText(stok);
            jTextField10.setText(total_stok);
            jTextField11.setText(total_masuk);
            jTextField9.setText(total_harga);
            }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        File fileToSave = null;

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Simpan sebagai");
        chooser.setSelectedFile(new File("data_barang_keluar.xls"));

        int userSelection = chooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = chooser.getSelectedFile();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            try {
                Date tglAwal = sdf.parse(jTextField17.getText());
                Date tglAkhir = sdf.parse(jTextField18.getText());

                // Filter dan export...
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                DefaultTableModel filteredModel = new DefaultTableModel();

                for (int i = 0; i < model.getColumnCount(); i++) {
                    filteredModel.addColumn(model.getColumnName(i));
                }

                for (int i = 0; i < model.getRowCount(); i++) {
                    String tglStr = model.getValueAt(i, 1).toString();
                    Date tglRow = sdf.parse(tglStr);

                    if ((tglRow.equals(tglAwal) || tglRow.after(tglAwal))
                            && (tglRow.equals(tglAkhir) || tglRow.before(tglAkhir))) {

                        Vector row = new Vector();
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            row.add(model.getValueAt(i, j));
                        }
                        filteredModel.addRow(row);
                    }
                }

                JTable tableFiltered = new JTable(filteredModel);
                exportToExcel(tableFiltered, fileToSave); // sekarang fileToSave dikenal

            } catch (ParseException e) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah!");
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        new Dashboard().setVisible(true);
        dispose();
    }//GEN-LAST:event_jButton8ActionPerformed

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
            java.util.logging.Logger.getLogger(Form_Barang_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Form_Barang_Masuk.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Form_Barang_Masuk().setVisible(true);
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
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration//GEN-END:variables
}
