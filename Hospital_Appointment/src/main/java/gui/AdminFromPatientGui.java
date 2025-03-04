package gui;

import entity.Appointment;
import entity.Doctor;
import entity.Patient; // Hastalar için entity sınıfını ekleyin
import entity.Report;
import service.AppointmentService;
import service.DoctorService;
import service.PatientService;
import service.ReportService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFromPatientGui extends JFrame implements InitCall {

    PatientService patientService = new PatientService();
    AppointmentService appointmentService = new AppointmentService();
    ReportService reportService = new ReportService();
    DoctorService doctorService = new DoctorService();

    private DefaultListModel<Patient> patientListModel = new DefaultListModel<>();
    private JList<Patient> patientJList;

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Hastalar");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Hastaları DefaultListModel'e ekleyin
        List<Patient> patients = patientService.showPatient();
        for (Patient patient : patients) {
            patientListModel.addElement(patient);
        }

        // JList oluşturun
        patientJList = new JList<>(patientListModel);
        JScrollPane scrollPane = new JScrollPane(patientJList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Butonlar
        JButton viewDetailsButton = new JButton("Detayları Görüntüle");
        JButton addButton = new JButton("Ekle");
        JButton deleteButton = new JButton("Sil");
        JButton backButton = new JButton("Geri"); // Geri butonu

        // Geri butonunun aksiyonu
        backButton.addActionListener(e -> {
            // Ana menüye dön veya başka bir işlem yap
            dispose(); // Bu pencereyi kapat
        });

        // Detayları görüntüle butonunun aksiyonu
        viewDetailsButton.addActionListener(e -> {
            Patient selectedPatient = patientJList.getSelectedValue();
            if (selectedPatient != null) {
                showPatientDetailsDialog(selectedPatient);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen detaylarını görüntülemek için bir hasta seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ekle butonunun aksiyonu
        addButton.addActionListener(e -> {
            MenuGui menuGui = new MenuGui();
            menuGui.showPatientRegisterForm();
            dispose();
        });

        // Sil butonunun aksiyonu
        deleteButton.addActionListener(e -> {
            Patient selectedPatient = patientJList.getSelectedValue();
            if (selectedPatient != null) {
                patientService.deletePatient(selectedPatient); // Hastayı silme
                patientListModel.removeElement(selectedPatient); // JList'ten çıkar
                JOptionPane.showMessageDialog(null, selectedPatient + " kişisi başarılı bir şekilde silinmiştir.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir hasta seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewDetailsButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton); // Geri butonunu ekle
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }




    private void showPatientDetailsDialog(Patient selectedPatient) {
        JDialog dialog = new JDialog(this, "Hasta Detayları", true);
        dialog.setSize(400, 600);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialogPanel.setBackground(Color.LIGHT_GRAY);

        // Hasta bilgilerini göster
        dialogPanel.add(new JLabel("Ad: " + selectedPatient.getName()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("Soyad: " + selectedPatient.getSurname()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("TC: " + selectedPatient.getTc()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(new JLabel("Doğum Tarihi: " + selectedPatient.getBirthDate()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        dialogPanel.add(new JLabel("Cinsiyet: " + selectedPatient.getGender()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(new JLabel("Telefon No: " + selectedPatient.getPhoneNumber()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        dialogPanel.add(new JLabel("Adres: " + selectedPatient.getAddress()));
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Randevuları listele
        DefaultListModel<String> appointmentListModel = new DefaultListModel<>();
        for (Appointment appointment : appointmentService.showAppointmentFromPatient(selectedPatient.getId())) {
            String doctorNames = "";
            List<Doctor> doctors = doctorService.showDoctorFromAppointmentFromReport(appointment.getDoctorIdFk());

            for (Doctor doctor : doctors) {
                doctorNames += doctor.getName() + " " + doctor.getSurname() + ", ";
            }

            if (!doctorNames.isEmpty()) {
                doctorNames = doctorNames.substring(0, doctorNames.length() - 2);
            } else {
                doctorNames = "Yok";
            }

            appointmentListModel.addElement("Doctor: " + doctorNames + " => " + appointment.toString());
        }
        JList<String> appointmentList = new JList<>(appointmentListModel);
        appointmentList.setBackground(Color.WHITE);
        dialogPanel.add(new JLabel("Randevular:"));
        dialogPanel.add(new JScrollPane(appointmentList));

        // Raporları listele
        DefaultListModel<String> reportListModel = new DefaultListModel<>();
        for (Report report : reportService.listFromPatient(selectedPatient.getId())) {

            String reportDoctorNames = "";
            List<Doctor> reportDoctors = doctorService.showDoctorFromAppointmentFromReport(report.getDoctorIdFk());

            for (Doctor doctor : reportDoctors) {
                reportDoctorNames += doctor.getName() + " " + doctor.getSurname() + ", ";
            }

            if (!reportDoctorNames.isEmpty()) {
                reportDoctorNames = reportDoctorNames.substring(0, reportDoctorNames.length() - 2);
            } else {
                reportDoctorNames = "Yok";
            }

            reportListModel.addElement("Doctor: " + reportDoctorNames + " => " + report.toString());
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



}
