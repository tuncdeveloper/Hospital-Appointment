package gui;

import entity.Patient;
import service.PatientService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;

public class UpdatePatientGui extends JFrame implements InitCall {

    private Patient registerPatient;
    PatientService patientService = new PatientService();
    private PatientGui patientGui;

    public UpdatePatientGui(Patient patient, PatientGui patientGui) {
        this.registerPatient = patient;
        this.patientGui = patientGui;
        initWindow();
    }

    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Kişi Güncelle");
        setSize(400, 300);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        // Hasta bilgilerini güncelleme için alanlar
        panel.add(new JLabel("Adı:"));
        JTextField nameField = new JTextField(registerPatient.getName());
        panel.add(nameField);

        panel.add(new JLabel("Soyadı:"));
        JTextField surnameField = new JTextField(registerPatient.getSurname());
        panel.add(surnameField);

        panel.add(new JLabel("Telefon:"));
        JTextField phoneField = new JTextField(registerPatient.getPhoneNumber());
        panel.add(phoneField);

        panel.add(new JLabel("Adres:"));
        JTextField addressField = new JTextField(registerPatient.getAddress());
        panel.add(addressField);

        // Kaydet butonu
        JButton saveButton = new JButton("Kaydet");
        saveButton.addActionListener(e -> {
            // Bilgileri güncelle
            registerPatient.setName(nameField.getText());
            registerPatient.setSurname(surnameField.getText());
            registerPatient.setPhoneNumber(phoneField.getText());
            registerPatient.setAddress(addressField.getText());

            patientService.updatePatient(registerPatient);

            // Hasta GUI'sini güncelle
            patientGui.updatePatientInfo(registerPatient);

            // Güncelleme sonrası pencereyi kapat
            JOptionPane.showMessageDialog(this, "Bilgiler başarıyla güncellendi!");
            dispose(); // Pencereyi kapat
        });

        // İptal butonu
        JButton cancelButton = new JButton("İptal");
        cancelButton.addActionListener(e -> {
            // Pencereyi kapat
            dispose(); // Pencereyi kapat
        });

        // Butonları panelde ekle
        panel.add(saveButton);
        panel.add(cancelButton);

        return panel;
    }
}
