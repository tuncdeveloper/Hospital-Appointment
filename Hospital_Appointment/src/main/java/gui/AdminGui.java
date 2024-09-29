package gui;

import entity.Admin;
import service.AdminService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;

public class AdminGui extends JFrame implements InitCall {

    private Admin admin;
    AdminService adminService = new AdminService();

    public AdminGui(Admin admin) {
        this.admin = admin;
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Admin");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Butonları oluştur
        JButton doctorButton = new JButton("Doktor Bilgileri");
        JButton patientButton = new JButton("Hasta Bilgileri");
        JButton addAdminButton = new JButton("Admin Ekle");

        // Butonların görünümünü güzelleştir
        doctorButton.setPreferredSize(new Dimension(200, 50));
        patientButton.setPreferredSize(new Dimension(200, 50));
        addAdminButton.setPreferredSize(new Dimension(200, 50));

        // Butonların arka plan rengini ve kenarlarını ayarla
        doctorButton.setBackground(Color.BLUE);
        doctorButton.setForeground(Color.WHITE);
        patientButton.setBackground(Color.GREEN);
        patientButton.setForeground(Color.WHITE);
        addAdminButton.setBackground(Color.ORANGE);
        addAdminButton.setForeground(Color.WHITE);

        // Butonları panelde konumlandır
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        panel.add(doctorButton, gbc);

        gbc.gridy = 1;
        panel.add(patientButton, gbc);

        gbc.gridy = 2;
        panel.add(addAdminButton, gbc);

        // Doktor bilgileri butonuna aksiyon ekleyin
        doctorButton.addActionListener(e -> openDoctorGui());

        // Hasta bilgileri butonuna aksiyon ekleyin
        patientButton.addActionListener(e -> openPatientGui());

        // Admin ekleme butonuna aksiyon ekleyin
        addAdminButton.addActionListener(e -> showAddAdminDialog());

        panel.setBackground(Color.LIGHT_GRAY);
        return panel;
    }


    private void openDoctorGui() {
        AdminFromDoctorGui adminFromDoctorGui = new AdminFromDoctorGui(); // AdminFromDoctorGui'yi aç
        adminFromDoctorGui.initWindow();
    }
    private void openPatientGui() {
        AdminFromPatientGui adminFromPatientGui = new AdminFromPatientGui(); // AdminFromDoctorGui'yi aç
        adminFromPatientGui.initWindow();
    }
    private void showAddAdminDialog() {
        JDialog dialog = new JDialog(this, "Admin Ekle", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();

        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);
        dialogPanel.add(surnameLabel);
        dialogPanel.add(surnameField);
        dialogPanel.add(passwordLabel);
        dialogPanel.add(passwordField);

        JButton saveButton = new JButton("Kaydet");
        JButton cancelButton = new JButton("İptal");
        dialogPanel.add(saveButton);
        dialogPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || surname.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Lütfen tüm alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            } else {
                Admin newAdmin = new Admin();
                newAdmin.setName(name);
                newAdmin.setSurname(surname);
                newAdmin.setPassword(password);

                adminService.setAdmin(newAdmin);

                JOptionPane.showMessageDialog(dialog, newAdmin.getName() + " başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose(); // Diyalog penceresini kapat
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }
}
