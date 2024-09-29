package gui;

import entity.Doctor;
import test.InitCall;

import javax.swing.*;
import java.awt.*;

public class DoctorGui extends JFrame implements InitCall {

    private Doctor doctor;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JButton appointmentsButton;
    private JButton reportsButton;

    public DoctorGui(Doctor doctor) {
        this.doctor = doctor;

    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Doktor Menü");
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Daha düzenli bir yerleşim için GridBagLayout kullanıyoruz
        panel.setBackground(new Color(240, 248, 255)); // Daha açık bir arka plan rengi

        // Doktorun adı ve soyadı
        nameLabel = new JLabel("Ad: " + doctor.getName());
        surnameLabel = new JLabel("Soyad: " + doctor.getSurname());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        surnameLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Butonlar
        appointmentsButton = new JButton("Randevularım");
        reportsButton = new JButton("Raporlarım");

        // Butonların stil ayarları
        customizeButton(appointmentsButton);
        customizeButton(reportsButton);

        // Butonlara tıklama olayları ekleyebilirsiniz
        appointmentsButton.addActionListener(e -> {

            AppointmentFromDoctorGui appointmentFromDoctorGui = new AppointmentFromDoctorGui(doctor);
            appointmentFromDoctorGui.initWindow();

        });

        reportsButton.addActionListener(e -> {
            ReportFromDoctorGui reportFromDoctorGui = new ReportFromDoctorGui(doctor);
            reportFromDoctorGui.initWindow();
        });

        // GridBagConstraints ile bileşenlerin konumunu ayarlama
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Boşluk eklemek için

        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 0; // 0. satır
        panel.add(nameLabel, gbc);

        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 1; // 1. satır
        panel.add(surnameLabel, gbc);

        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 2; // 2. satır
        panel.add(appointmentsButton, gbc);

        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 3; // 3. satır
        panel.add(reportsButton, gbc);

        return panel;
    }

    // Buton stil ayarları
    private void customizeButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(70, 130, 180)); // Buton rengi
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // İç boşluk
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // İmleç ayarı
    }
}
