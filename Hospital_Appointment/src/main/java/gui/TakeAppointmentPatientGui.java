package gui;

import com.toedter.calendar.JDateChooser; // JDateChooser kütüphanesini ekliyoruz
import entity.Appointment;
import entity.Patient;
import entity.Doctor; // Doctor sınıfını içe aktarıyoruz
import service.AppointmentService;
import service.DoctorService; // DoctorService sınıfını içe aktarıyoruz
import test.InitCall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class TakeAppointmentPatientGui extends JFrame implements InitCall {

    private JComboBox<String> timeComboBox;
    private JComboBox<Doctor> doctorComboBox; // Doktor seçimi için JComboBox
    private JDateChooser dateChooser;
    AppointmentService appointmentService = new AppointmentService();
    DoctorService doctorService = new DoctorService(); // DoctorService nesnesi
    Appointment appointment = new Appointment();
    Patient foundPatient;
    ArrayList<Appointment> appointments = new ArrayList<>();

    public TakeAppointmentPatientGui(Patient patient){
        this.foundPatient = patient;
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Randevu Al");
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Arka plan rengi

        // Tarih Seçici (Takvim)
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setMinSelectableDate(new java.util.Date()); // Bugünden önceki tarihleri seçemez

        // Saat Seçici
        timeComboBox = new JComboBox<>();
        populateTimeSlots();

        // Doktor Seçici
        doctorComboBox = new JComboBox<>();
        populateDoctorList(); // Doktorları doldur

        // Randevu Al Butonu
        JButton takeAppointmentButton = new JButton("Randevu Al");

        takeAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    Appointment appointment = new Appointment(); // Yeni randevu nesnesi oluştur

                    // Saat seçim kontrolü
                    String selectedTimeString = (String) timeComboBox.getSelectedItem();
                    if (selectedTimeString == null) {
                        JOptionPane.showMessageDialog(null, "Saat Seçilmedi!");
                        return;
                    }

                    Time selectedTime;
                    try {
                        selectedTime = Time.valueOf(selectedTimeString + ":00");
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(null, "Geçersiz saat formatı!");
                        return;
                    }

                    // Tarih seçim kontrolü
                    java.sql.Date selectedDate;
                    if (dateChooser.getDate() != null) {
                        selectedDate = new java.sql.Date(dateChooser.getDate().getTime());
                    } else {
                        JOptionPane.showMessageDialog(null, "Tarih Seçilmedi!");
                        return;
                    }

                    // Doktor seçim kontrolü
                    Doctor selectedDoctor = (Doctor) doctorComboBox.getSelectedItem();
                    if (selectedDoctor == null) {
                        JOptionPane.showMessageDialog(null, "Doktor Seçilmedi!");
                        return;
                    }
                    appointment.setAppointmentDate(selectedDate);
                    appointment.setAppointmentTime(selectedTime);
                    // Randevu kontrolü
                    boolean isAvailable = appointmentService.controlDate(selectedDate, selectedTime, selectedDoctor.getId(), appointment);
                    if (isAvailable) {
                        // Randevu ayarla

                        appointmentService.addAppointment(appointment, selectedDoctor.getId(), foundPatient.getId());
                        JOptionPane.showMessageDialog(null, "Randevu başarıyla alındı: " + selectedDate + " " + selectedTime + " Doktor: " + selectedDoctor.getName());
                    }else {
                        JOptionPane.showMessageDialog(null, "Bu tarih ve saat için doktorun başka bir randevusu var!");

                    }
            }
        });

        JButton backAppointmentButton = new JButton("Geri");

        backAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // GridBagConstraints ile bileşenlerin konumunu ayarlama
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Boşluk eklemek için

        // Tarih Seçici
        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 0; // 0. satır
        panel.add(new JLabel("Tarih Seçin:"), gbc);

        gbc.gridx = 1; // 1. sütun
        gbc.gridy = 0; // 0. satır
        panel.add(dateChooser, gbc);

        // Saat Seçici
        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 1; // 1. satır
        panel.add(new JLabel("Saat Seçin:"), gbc);

        gbc.gridx = 1; // 1. sütun
        gbc.gridy = 1; // 1. satır
        panel.add(timeComboBox, gbc);

        // Doktor Seçici
        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 2; // 2. satır
        panel.add(new JLabel("Doktor Seçin:"), gbc);

        gbc.gridx = 1; // 1. sütun
        gbc.gridy = 2; // 2. satır
        panel.add(doctorComboBox, gbc);

        // Randevu Al Butonu
        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 3; // 3. satır
        gbc.gridwidth = 2; // Butonun iki sütunu kaplaması için
        panel.add(takeAppointmentButton, gbc);

        gbc.gridx = 0; // 0. sütun
        gbc.gridy = 4; // 4. satır
        gbc.gridwidth = 2; // Butonun iki sütunu kaplaması için
        panel.add(backAppointmentButton, gbc);

        return panel;
    }

    // Yarım saat aralıkları ile saat dilimlerini dolduran metot
    private void populateTimeSlots() {
        for (int hour = 8; hour < 18; hour++) { // 08:00 - 17:30 saatleri arası
            timeComboBox.addItem(String.format("%02d:00", hour));
            timeComboBox.addItem(String.format("%02d:30", hour));
        }
    }

    // Doktor listesini dolduran metot
    private void populateDoctorList() {
        List<Doctor> doctors = doctorService.showDoctor(); // Tüm doktorları al
        for (Doctor doctor : doctors) {
            doctorComboBox.addItem(doctor); // Doctor nesnesini ekle
        }
    }


}
