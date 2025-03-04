package gui;

import entity.Appointment;
import entity.Patient;
import service.AppointmentService;
import service.DoctorService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AppointmentFromPatientGui extends JFrame implements InitCall {

    AppointmentService appointmentService = new AppointmentService(); // AppointmentService nesnesi
    private Patient patient;
    DoctorService doctorService = new DoctorService();

    public AppointmentFromPatientGui(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Randevular");
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Arka plan rengi

        // Randevuları alıyoruz
        ArrayList<Appointment> appointments = appointmentService.showAppointmentFromPatient(patient.getId());

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (Appointment appointment : appointments) {
            String doctorName = doctorService.selectetWithIdDoctor(appointment.getDoctorIdFk()).getName().toUpperCase(); // Doktor ismi büyük harf
            String doctorSurname = doctorService.selectetWithIdDoctor(appointment.getDoctorIdFk()).getSurname().toUpperCase(); // Doktor soyismi büyük harf
            String doctorSpecialty = String.valueOf(doctorService.selectetWithIdDoctor(appointment.getDoctorIdFk()).getSpecialty());
            // Doktor ismi ve soyadı üstte, diğer bilgiler altta olacak şekilde formatlandı
            String appointmentEntry = String.format("<html><b>%s %s</b><br/>Bölüm: %s<br/>Tarih: %s<br/>Saat: %s</html>",
                    doctorName, doctorSurname, doctorSpecialty, appointment.getAppointmentDate(), appointment.getAppointmentTime());
            listModel.addElement(appointmentEntry);
        }

        // Randevuları gösterecek bir JList oluşturuyoruz
        JList<String> appointmentList = new JList<>(listModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14)); // Yazı tipi
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tekli seçim modu
        appointmentList.setVisibleRowCount(10); // Görünen satır sayısı
        appointmentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Arial", Font.PLAIN, 14)); // Genel yazı tipi
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(appointmentList);

        // Butonlar için bir panel oluşturuyoruz
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245)); // Arka plan rengi

        // Geri butonu
        JButton backButton = new JButton("Geri");
        backButton.setBackground(new Color(70, 130, 180)); // Buton arka plan rengi
        backButton.setForeground(Color.WHITE); // Buton yazı rengi
        backButton.setFont(new Font("Arial", Font.BOLD, 14)); // Buton yazı tipi
        backButton.addActionListener(e -> dispose()); // Geri butonuna tıklandığında pencereyi kapat

        // Sil butonu (Randevuyu silmek için)
        JButton deleteButton = new JButton("Randevuyu Sil");
        deleteButton.setBackground(new Color(220, 20, 60)); // Sil butonu arka plan rengi
        deleteButton.setForeground(Color.WHITE); // Buton yazı rengi
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14)); // Buton yazı tipi

        // Sil butonu aksiyonu
        deleteButton.addActionListener(e -> {
            int selectedIndex = appointmentList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Seçilen randevunun ID'sini alın
                Appointment selectedAppointment = appointments.get(selectedIndex);
                int appointmentId = selectedAppointment.getAppointmentId();

                // Randevuyu sil
                appointmentService.deleteAppointment(appointmentId);

                // Listeden silinmiş randevuyu kaldır
                listModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir randevu seçin.");
            }
        });

        // Butonları buton paneline ekliyoruz
        buttonPanel.add(backButton);
        buttonPanel.add(deleteButton);

        // Panelin merkezine JList'i, altına buton panelini ekliyoruz
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
}
