package gui;

import entity.*;
import service.AppointmentService;
import service.DoctorService;
import service.PatientService;
import service.ReportService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFromDoctorGui extends JFrame implements InitCall {

     DoctorService doctorService = new DoctorService();
     AppointmentService appointmentService = new AppointmentService();
     ReportService reportService = new ReportService();
     PatientService patientService = new PatientService();
    private DefaultListModel<Doctor> doctorListModel = new DefaultListModel<>();
    private JList<Doctor> doctorJList;

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Doktorlar");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initBackButton(JPanel buttonPanel) {
        JButton backButton = new JButton("Geri");
        backButton.addActionListener(e -> {
            dispose(); // Mevcut pencereyi kapat
        });
        buttonPanel.add(backButton);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout()); // Ana panel için BorderLayout kullanıyoruz

        // Doktor bilgilerini almak için DoctorService kullanın
        List<Doctor> doctors = doctorService.showDoctor(); // Doktor listesini alın

        // Doktorları DefaultListModel'e ekleyin
        for (Doctor doctor : doctors) {
            doctorListModel.addElement(doctor);
        }

        // JList oluşturun
        doctorJList = new JList<>(doctorListModel);
        JScrollPane scrollPane = new JScrollPane(doctorJList);
        panel.add(scrollPane, BorderLayout.CENTER); // Tabloyu panelin ortasına ekle

        // Butonlar
        JButton addButton = new JButton("Doktor Ekle");
        JButton removeButton = new JButton("Doktor Sil");
        JButton viewDetailsButton = new JButton("Detayları Görüntüle"); // Yeni buton

        // Butonların aksiyonları
        addButton.addActionListener(e -> showAddDoctorDialog());
        removeButton.addActionListener(e -> removeSelectedDoctor());
        viewDetailsButton.addActionListener(e -> {
            Doctor selectedDoctor = doctorJList.getSelectedValue(); // Seçilen doktoru al
            if (selectedDoctor != null) {
                showDoctorDetailsDialog(selectedDoctor); // Seçilen doktorun detaylarını göster
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen detaylarını görüntülemek için bir doktor seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(viewDetailsButton); // Detayları görüntüle butonunu ekle
        initBackButton(buttonPanel); // Geri butonunu ekle
        panel.add(buttonPanel, BorderLayout.SOUTH); // Butonları panelin altına ekle

        return panel;
    }

    private void showAddDoctorDialog() {
        JDialog dialog = new JDialog(this, "Doktor Ekle", true);
        dialog.setSize(300, 250); // Yüksekliği artırdım
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridLayout(5, 2, 10, 10)); // Satır sayısını 5 yaptım

        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = new JTextField();
        JLabel specialtyLabel = new JLabel("Uzmanlık:");
        JTextField specialtyField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField(); // Şifre alanı

        dialogPanel.add(nameLabel);
        dialogPanel.add(nameField);
        dialogPanel.add(surnameLabel);
        dialogPanel.add(surnameField);
        dialogPanel.add(specialtyLabel);
        dialogPanel.add(specialtyField);
        dialogPanel.add(passwordLabel); // Şifre etiketini ekle
        dialogPanel.add(passwordField); // Şifre alanını ekle

        JButton saveButton = new JButton("Kaydet");
        JButton cancelButton = new JButton("İptal");
        dialogPanel.add(saveButton);
        dialogPanel.add(cancelButton);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String specialty = specialtyField.getText().trim();
            String password = new String(passwordField.getPassword()).trim(); // Şifreyi al

            if (name.isEmpty() || surname.isEmpty() || specialty.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Lütfen tüm alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            } else {
                Doctor newDoctor = new Doctor();
                newDoctor.setName(name);
                newDoctor.setSurname(surname);
                newDoctor.setSpecialty(SpecialtyEnum.valueOf(specialty));
                newDoctor.setPassword(password); // Doktor nesnesine şifreyi ekle
                newDoctor.setHospitalName(HospitalEnum.LIV_HOSPITAL);

                doctorListModel.addElement(newDoctor); // Yeni doktoru listeye ekle
                doctorService.addDoctor(newDoctor);
                JOptionPane.showMessageDialog(this, newDoctor.getName() + " başarıyla kaydedildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

                dialog.dispose(); // Diyalog penceresini kapat
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    private void showDoctorDetailsDialog(Doctor selectedDoctor) {
        JDialog dialog = new JDialog(this, "Doktor Detayları", true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialogPanel.setBackground(Color.LIGHT_GRAY);

// Doktor bilgilerini göster
        dialogPanel.add(new JLabel("Ad: " + selectedDoctor.getName()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("Soyad: " + selectedDoctor.getSurname()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("Uzmanlık: " + selectedDoctor.getSpecialty()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("Hastane: " + selectedDoctor.getHospitalName()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));

// Randevuları listele
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        for (Appointment appointment : appointmentService.showAppointmentFromDoctor(selectedDoctor.getId())) {
            // Randevu bilgilerini al
            String patientNames = "";
            List<Patient> patients = patientService.showPatientFromAppointmentFromReport(appointment.getPatientIdFk()); // Hastaları al

            for (Patient patient : patients) {
                patientNames += patient.getName() + " " + patient.getSurname() + ", "; // Hastaların isimlerini birleştir
            }

            if (!patientNames.isEmpty()) {
                patientNames = patientNames.substring(0, patientNames.length() - 2); // Son virgülü kaldır
            } else {
                patientNames = "Yok";
            }

            appointmentListModel.addElement("Hasta : " + patientNames + " => " + appointment.toString());
        }
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setBackground(Color.WHITE);
        dialogPanel.add(new JLabel("Randevular:"));
        dialogPanel.add(new JScrollPane(appointmentList));

// Raporları listele
        DefaultListModel<String> reportListModel = new DefaultListModel<>();
        for (Report report : reportService.listFromDoctor(selectedDoctor.getId())) {
            // Rapor bilgilerini al
            String reportPatientNames = "";
            List<Patient> reportPatients = patientService.showPatientFromAppointmentFromReport(report.getPatientIdFk()); // Raporun hastalarını al

            for (Patient patient : reportPatients) {
                reportPatientNames += patient.getName() + " " + patient.getSurname() + ", "; // Hastaların isimlerini birleştir
            }

            if (!reportPatientNames.isEmpty()) {
                reportPatientNames = reportPatientNames.substring(0, reportPatientNames.length() - 2); // Son virgülü kaldır
            } else {
                reportPatientNames = "Yok";
            }

            reportListModel.addElement("Hasta : " + reportPatientNames + " => " + report.toString());
        }
        JList<String> reportList = new JList<>(reportListModel);
        reportList.setBackground(Color.WHITE);
        dialogPanel.add(new JLabel("Raporlar:"));
        dialogPanel.add(new JScrollPane(reportList));

// Geri butonu ekle
        JButton backButton = new JButton("Geri");
        backButton.addActionListener(e -> dialog.dispose());
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(backButton);

        dialog.add(dialogPanel);
        dialog.setVisible(true);

    }






    private void removeSelectedDoctor() {
        Doctor selectedDoctor = doctorJList.getSelectedValue();

        if (selectedDoctor != null) {
            doctorListModel.removeElement(selectedDoctor); // Seçilen doktoru sil
            doctorService.deleteDoctor(selectedDoctor);
            JOptionPane.showMessageDialog(this, selectedDoctor.getName() + " başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir doktor seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }
}
