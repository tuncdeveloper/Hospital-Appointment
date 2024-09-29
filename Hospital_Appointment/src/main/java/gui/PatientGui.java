package gui;

import entity.Patient;
import test.InitCall;

import javax.swing.*;
import java.awt.*;

public class PatientGui extends JFrame implements InitCall {

    private Patient registerPatient; // Hasta bilgileri

    public PatientGui(Patient patient) {
        this.registerPatient = patient;
        initWindow(); // Pencereyi başlat
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Hasta Menü");
        setSize(600, 400);
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        // Ana panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // BorderLayout ile düzenleyin

        // Üst panel: Hasta bilgileri
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(0, 2)); // Bilgileri iki sütunlu düzen ile göster

        // Hasta bilgilerini ekleyin
        topPanel.add(new JLabel("Adı:"));
        topPanel.add(new JLabel(registerPatient.getName()));

        topPanel.add(new JLabel("Soyadı:"));
        topPanel.add(new JLabel(registerPatient.getSurname()));

        topPanel.add(new JLabel("Doğum Tarihi:"));
        topPanel.add(new JLabel(registerPatient.getBirthDate().toString()));

        topPanel.add(new JLabel("Cinsiyet:"));
        topPanel.add(new JLabel(registerPatient.getGender()));

        topPanel.add(new JLabel("Telefon:"));
        topPanel.add(new JLabel(registerPatient.getPhoneNumber()));

        topPanel.add(new JLabel("Adres:"));
        topPanel.add(new JLabel(registerPatient.getAddress()));

        topPanel.add(new JLabel("TC No:"));
        topPanel.add(new JLabel(registerPatient.getTc()));

        // Üst paneli ana panele ekleyin
        panel.add(topPanel, BorderLayout.NORTH);

        // Butonları içeren panel: Alt panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Akış düzeni ile butonları hizalayın

        // Butonlar
        JButton randevuAlButton = new JButton("Randevu Al");
        JButton randevularimButton = new JButton("Randevularım");
        JButton raporlarimButton = new JButton("Raporlarım");
        JButton kisiGuncelleButton = new JButton("Kişi Güncelle");

        // Butonlara tıklama olayları ekleyin
        randevuAlButton.addActionListener(e -> {
            TakeAppointmentPatientGui takeAppointmentPatientGui = new TakeAppointmentPatientGui(registerPatient);
            takeAppointmentPatientGui.initWindow();
        });

        randevularimButton.addActionListener(e -> {
            AppointmentFromPatientGui appointmentFromPatientGui = new AppointmentFromPatientGui(registerPatient);
            appointmentFromPatientGui.initWindow();
        });

        raporlarimButton.addActionListener(e -> {
            ReportFromPatientGui reportFromPatientGui = new ReportFromPatientGui(registerPatient);
            reportFromPatientGui.initWindow();
        });

        kisiGuncelleButton.addActionListener(e -> {
            UpdatePatientGui updatePatientGui = new UpdatePatientGui(registerPatient, this); // this referansı ile PatientGui'yi ilet
            updatePatientGui.initWindow();
        });

        // Butonları panelde ekleyin
        buttonPanel.add(randevuAlButton);
        buttonPanel.add(randevularimButton);
        buttonPanel.add(raporlarimButton);
        buttonPanel.add(kisiGuncelleButton); // Kişi Güncelle butonunu ekleyin

        // Buton panelini ana panele ekleyin
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Arka plana bir boşluk ekleyin
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        return panel;
    }

    // Hasta bilgilerini güncelleyen metot
    public void updatePatientInfo(Patient updatedPatient) {
        this.registerPatient = updatedPatient;

        // Bileşenlere erişip, güncellenen bilgileri ayarla
        JPanel topPanel = (JPanel) ((JPanel) getContentPane().getComponent(0)).getComponent(0); // Üst paneli al
        Component[] components = topPanel.getComponents(); // Üst panelin bileşenlerini al

        // Adı, soyadı, telefon vb. bilgileri güncelle
        ((JLabel) components[1]).setText(registerPatient.getName());
        ((JLabel) components[3]).setText(registerPatient.getSurname());
        ((JLabel) components[5]).setText(registerPatient.getBirthDate().toString());
        ((JLabel) components[7]).setText(registerPatient.getGender());
        ((JLabel) components[9]).setText(registerPatient.getPhoneNumber());
        ((JLabel) components[11]).setText(registerPatient.getAddress());
        ((JLabel) components[13]).setText(registerPatient.getTc());
    }

}
