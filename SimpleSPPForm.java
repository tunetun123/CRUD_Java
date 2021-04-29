/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mod13_5190411485;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Cursor;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Fathur
 */
public class SimpleSPPForm extends javax.swing.JFrame {

    /**
     * Creates new form SimpleSPPForm
     */
    StringBuilder sb;
    DefaultTableModel model;
    String tabel_klik = "";

    public SimpleSPPForm() {
        initComponents();

    }

    public SimpleSPPForm(String username) {
        initComponents();

        rbTF.setActionCommand("Teknik Informatika");
        rbTI.setActionCommand("Teknik Industri");

        buttonGroup1 = new ButtonGroup();
        buttonGroup1.add(rbTF);
        buttonGroup1.add(rbTI);

        lblUser.setText(username);
        lblLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        bttnPrint.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        bttnReset.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        sb = new StringBuilder();
        
        model = (DefaultTableModel) jTable1.getModel();
        jTable1.setModel(model);
        this.loadDataToTable();
    }
    
    public void loadDataToTable()
    {
        DefaultTableModel dm = (DefaultTableModel)jTable1.getModel();
        
        while(dm.getRowCount() > 0) {
            dm.removeRow(0);
        }
        
        try {
            Connection conn = KoneksiDB.getKoneksi();
            Statement s = conn.createStatement();
            
            String query = "SELECT * FROM data_mahasiswa";
            ResultSet result = s.executeQuery(query);
            
            while(result.next()) {
                Object[] obj = new Object[7];
                obj[0] = result.getString("nama_mhs");
                obj[1] = result.getString("nim_mhs");
                obj[2] = 1900+ result.getDate("angkatan_msuk").getYear();
                obj[3] = result.getString("semester");
                obj[4] = result.getString("prodi");
                obj[5] = result.getInt("sks_mhs");
                obj[6] = result.getInt("id_mhs");
                
                model.addRow(obj);
            }
            result.close();
            s.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            tabel_klik = "";
            // jTable1.removeColumn(jTable1.getColumnModel().getColumn(6));
            jTable1.getColumnModel().getColumn(6).setMinWidth(0);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(0);
        }
    }
    
    

    public void printAll() throws NimHarusAngkaException, NimHarusPositifException, NimHarus10Digit, SemesterHarusValid 
    {
        long nim;
        int lenNim;
        int smt;
        try {
            nim = Long.parseLong(txtNIM.getText());
            
            // mendapatkan length dari txtNIM
            String txt = txtNIM.getText();
            lenNim = txt.length();
            
            // mengkonversi txtSemester ke integer
            smt = Integer.parseInt(txtSemester.getText());

        } catch (NumberFormatException e) {
            throw new NimHarusAngkaException();
        }
        
        if (nim < 0) {
            throw new NimHarusPositifException();
        } else if (lenNim > 10) {
            throw new NimHarus10Digit();
        } else if ((smt<0) || (smt>14)) {
            throw new SemesterHarusValid();
        }

        sb.append("***** I N F O   S P P   M A H A S I S W A *****");
        sb.append("\nNama Mahasiswa\t : " + txtNama.getText());
        sb.append("\nNIM\t\t : " + txtNIM.getText());
        sb.append("\nAngkatan\t : " + cmbAngkatan.getSelectedItem());
        sb.append("\nSemester\t : " + txtSemester.getText());
        sb.append("\nProgram Studi\t : " + buttonGroup1.getSelection().getActionCommand());
        sb.append("\nTotal sks\t : " + txtSKS.getText());
        sb.append("\n======================================");
        this.printSPP();
        sb.append("\n\n");

        taPrint.setText(sb.toString());
    }

    public void clearAll() {
        txtNama.setText("");
        txtNIM.setText("");
        cmbAngkatan.setSelectedIndex(0);
        txtSemester.setText("");
        txtSKS.setText("");
        buttonGroup1.clearSelection();
        taPrint.setText("");
        sb.setLength(0);
    }

    public double sppTetap() {
        double sppTetap;

        if ((cmbAngkatan.getSelectedItem() == "2018") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 2050000;
        } else if ((cmbAngkatan.getSelectedItem() == "2019") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 2150000;
        } else if ((cmbAngkatan.getSelectedItem() == "2020") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 2250000;
        } else if ((cmbAngkatan.getSelectedItem() == "2018") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 1650000;
        } else if ((cmbAngkatan.getSelectedItem() == "2019") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 1700000;
        } else if ((cmbAngkatan.getSelectedItem() == "2020") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppTetap = 1750000;
        } else {
            return sppTetap = 0;
        }
    }

    public double sppVariable() {
        double sppVar;

        if ((cmbAngkatan.getSelectedItem() == "2018") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 170000;
        } else if ((cmbAngkatan.getSelectedItem() == "2019") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 180000;
        } else if ((cmbAngkatan.getSelectedItem() == "2020") && ("Teknik Informatika".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 190000;
        } else if ((cmbAngkatan.getSelectedItem() == "2018") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 165000;
        } else if ((cmbAngkatan.getSelectedItem() == "2019") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 175000;
        } else if ((cmbAngkatan.getSelectedItem() == "2020") && ("Teknik Industri".equals(buttonGroup1.getSelection().getActionCommand()))) {
            return sppVar = Double.parseDouble(txtSKS.getText()) * 185000;
        } else {
            return sppVar = 0;
        }
    }

    public double hitungTotalSPP() {
        return sppTetap() + sppVariable();
    }

    public void printSPP() {
        sb.append("\nSPP Tetap\t : Rp." + String.format("%,.2f", this.sppTetap()));
        sb.append("\nSPP Variable\t : Rp." + String.format("%,.2f", this.sppVariable()));
        sb.append("\n\t\t-------------------------- +");
        sb.append("\nTotal SPP\t : Rp." + String.format("%,.2f", this.hitungTotalSPP()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        txtNIM = new javax.swing.JTextField();
        cmbAngkatan = new javax.swing.JComboBox<>();
        txtSemester = new javax.swing.JTextField();
        rbTF = new javax.swing.JRadioButton();
        rbTI = new javax.swing.JRadioButton();
        txtSKS = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        bttnPrint = new javax.swing.JButton();
        bttnReset = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        taPrint = new javax.swing.JTextArea();
        lblUser = new javax.swing.JLabel();
        lblLogout = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        bttnUpdate = new javax.swing.JButton();
        bttnDelete = new javax.swing.JButton();
        txtCoba = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SPP Form");

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("Simple SPP Form");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel2.setText("Selamat Datang,");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel3.setText("Nama Mahasiswa");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("NIM");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Angkatan");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel6.setText("Semester");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel7.setText("Program Studi");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel8.setText("Jumlah SKS Diambil");

        txtNama.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        txtNIM.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        cmbAngkatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2018", "2019", "2020" }));
        cmbAngkatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAngkatanActionPerformed(evt);
            }
        });

        txtSemester.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        rbTF.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rbTF.setText("Teknik Informatika");

        rbTI.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        rbTI.setText("Teknik Industri");

        txtSKS.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel9.setText("SKS");

        bttnPrint.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bttnPrint.setText("Print");
        bttnPrint.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttnPrintMouseClicked(evt);
            }
        });

        bttnReset.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bttnReset.setText("Reset");
        bttnReset.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttnResetMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel10.setText("Simple SPP Print");

        taPrint.setColumns(20);
        taPrint.setRows(5);
        jScrollPane1.setViewportView(taPrint);

        lblUser.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        lblUser.setText("jLabel11");

        lblLogout.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblLogout.setText("Logout");
        lblLogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLogoutMouseClicked(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Mahasiswa", "NIM", "Angkatan", "Semester", "Program Studi", "Jumlah SKS", "id_mhs"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        bttnUpdate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bttnUpdate.setText("Update");
        bttnUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttnUpdateMouseClicked(evt);
            }
        });

        bttnDelete.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        bttnDelete.setText("Delete");
        bttnDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bttnDeleteMouseClicked(evt);
            }
        });

        txtCoba.setText("jTextField1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE))
                .addContainerGap(22, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNama)
                                            .addComponent(txtNIM)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtSemester, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(cmbAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(txtSKS, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel9))
                                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                                        .addComponent(rbTF)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(rbTI)))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(bttnPrint)
                                                .addGap(18, 18, 18)
                                                .addComponent(bttnReset)
                                                .addGap(18, 18, 18)
                                                .addComponent(bttnUpdate)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(bttnDelete)
                                                .addGap(53, 53, 53))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblLogout)
                                        .addGap(171, 171, 171))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtCoba, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(79, 79, 79)))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblUser)
                    .addComponent(txtCoba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNIM, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbAngkatan, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(txtSemester, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbTF)
                    .addComponent(rbTI))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSKS, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bttnPrint)
                    .addComponent(bttnReset)
                    .addComponent(bttnUpdate)
                    .addComponent(bttnDelete))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 43, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bttnPrintMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnPrintMouseClicked
        // TODO add your handling code here:
        try {
            this.printAll();
        } catch (NimHarusAngkaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NimHarusPositifException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (NimHarus10Digit e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        } catch (SemesterHarusValid e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        
        try {
            Connection conn = KoneksiDB.getKoneksi();
            
            String query = "INSERT INTO data_mahasiswa " 
                    + "(nama_mhs,nim_mhs,angkatan_msuk,semester,prodi,sks_mhs) " 
                    + "VALUES(?,?,?,?,?,?)";
            
            PreparedStatement p = conn.prepareStatement(query);
            
            p.setString(1, txtNama.getText());
            p.setString(2, txtNIM.getText());
            p.setString(3, cmbAngkatan.getSelectedItem().toString());
            p.setString(4, txtSemester.getText());
            p.setString(5, buttonGroup1.getSelection().getActionCommand());
            p.setString(6, txtSKS.getText());
            
            // p.executeUpdate();
            
            int rowsInserted = p.executeUpdate();
            if(rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            p.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Data Gagal Disimpan", "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            this.loadDataToTable();
        }
    }//GEN-LAST:event_bttnPrintMouseClicked

    private void bttnResetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnResetMouseClicked
        // TODO add your handling code here:
        this.clearAll();
    }//GEN-LAST:event_bttnResetMouseClicked

    private void lblLogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLogoutMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        new LoginForm().setVisible(true);
    }//GEN-LAST:event_lblLogoutMouseClicked

    private void cmbAngkatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAngkatanActionPerformed
        // TODO add your handling code here:
//        if (cmbAngkatan.getSelectedItem() == "2020") {
//            txtSemester.setText("I");
//        } else if (cmbAngkatan.getSelectedItem() == "2019") {
//            txtSemester.setText("III");
//        } else if (cmbAngkatan.getSelectedItem() == "2018") {
//            txtSemester.setText("V");
//        }
    }//GEN-LAST:event_cmbAngkatanActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        
        int row = jTable1.getSelectedRow();
        tabel_klik = (jTable1.getModel().getValueAt(row, 6).toString());
        txtCoba.setText(tabel_klik);
        
        txtNama.setText(jTable1.getModel().getValueAt(row, 0).toString());
        txtNIM.setText(jTable1.getModel().getValueAt(row, 1).toString());
        cmbAngkatan.setSelectedItem(jTable1.getModel().getValueAt(row, 2).toString());
        txtSemester.setText(jTable1.getModel().getValueAt(row, 3).toString());
        
        String row_prodi = jTable1.getModel().getValueAt(row, 4).toString();
        
        if(row_prodi.equals("Teknik Informatika")) {
            rbTF.setSelected(true);
        } else {
            rbTI.setSelected(true);
        }
        
        txtSKS.setText(jTable1.getModel().getValueAt(row, 5).toString());   
    }//GEN-LAST:event_jTable1MouseClicked

    private void bttnUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnUpdateMouseClicked
        // TODO add your handling code here:
        if(!tabel_klik.equals("")){
            try {
                Connection conn = KoneksiDB.getKoneksi();

                String query = "UPDATE data_mahasiswa "
                        + "SET nama_mhs=?, nim_mhs=?, angkatan_msuk=?, semester=?, prodi=?, sks_mhs=? "
                        + "WHERE id_mhs=?";

                PreparedStatement p = conn.prepareStatement(query);

                p.setString(1, txtNama.getText());
                p.setString(2, txtNIM.getText());
                p.setString(3, cmbAngkatan.getSelectedItem().toString());
                p.setString(4, txtSemester.getText());
                p.setString(5, buttonGroup1.getSelection().getActionCommand());
                p.setString(6, txtSKS.getText());
                p.setString(7, tabel_klik);

                int rowsUpdate = p.executeUpdate();
                if(rowsUpdate > 0) {
                    JOptionPane.showMessageDialog(this, "An existing user was updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
                }
            
                p.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Proses Update Data Gagal..!!", "Update Gagal", JOptionPane.ERROR_MESSAGE);
            } finally {
                this.loadDataToTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Anda belum memilih data mahasiswa yang akan diupdate", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }//GEN-LAST:event_bttnUpdateMouseClicked

    private void bttnDeleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bttnDeleteMouseClicked
        // TODO add your handling code here:
        if(!tabel_klik.equals("")){
            try {
                Connection conn = KoneksiDB.getKoneksi();
                
                String query = "DELETE FROM data_mahasiswa "
                        + "WHERE id_mhs=?";
                
                PreparedStatement p = conn.prepareStatement(query);
                
                p.setString(1, tabel_klik);
                
                int rowsDeleted = p.executeUpdate();
                
                if(rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "An existing user was deleted successfully!", "Delete Success", JOptionPane.INFORMATION_MESSAGE);
                }
                
                p.close();
            } catch(SQLException e) {
                JOptionPane.showMessageDialog(this, "Proses Delete Data Gagal..!!", "Delete Gagal", JOptionPane.ERROR_MESSAGE);
            } finally {
                this.loadDataToTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Anda belum memilih data mahasiswa yang akan dihapus", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_bttnDeleteMouseClicked

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windows".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(SimpleSPPForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(SimpleSPPForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(SimpleSPPForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(SimpleSPPForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new SimpleSPPForm().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bttnDelete;
    private javax.swing.JButton bttnPrint;
    private javax.swing.JButton bttnReset;
    private javax.swing.JButton bttnUpdate;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbAngkatan;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblLogout;
    private javax.swing.JLabel lblUser;
    private javax.swing.JRadioButton rbTF;
    private javax.swing.JRadioButton rbTI;
    private javax.swing.JTextArea taPrint;
    private javax.swing.JTextField txtCoba;
    private javax.swing.JTextField txtNIM;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtSKS;
    private javax.swing.JTextField txtSemester;
    // End of variables declaration//GEN-END:variables
}
