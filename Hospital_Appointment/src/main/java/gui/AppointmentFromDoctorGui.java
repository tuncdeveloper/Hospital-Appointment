package gui;

import entity.Appointment;
import entity.Doctor;
import service.AppointmentService;
import service.PatientService;
import test.InitCall;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AppointmentFromDoctorGui extends JFrame implements InitCall {

    private AppointmentService appointmentService = new AppointmentService(); // AppointmentService nesnesi
    private Doctor doctor; // Doktor nesnesi
    private ArrayList<Appointment> appointments; // Randevu listesi
    PatientService patientService = new PatientService();

    public AppointmentFromDoctorGui(Doctor doctor) {
        this.doctor = doctor; // Constructor'da doktoru al
        appointments = appointmentService.showAppointmentFromDoctor(doctor.getId()); // Randevuları al
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Randevularım");
        setSize(500, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245)); // Arka plan rengi

        // Randevuları JList'e eklemek için bir model oluşturuyoruz
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Appointment appointment : appointments) {
            String name = patientService.selectedWithIdPatient(appointment.getIdPatientFk()).getName();
            String surname = patientService.selectedWithIdPatient(appointment.getIdPatientFk()).getSurname();
            listModel.addElement(("Hasta: " + name + " " + surname +
                    " Tarih: " + appointment.getAppointmentDate() +
                    " Saat: " + appointment.getAppointmentTime()));
        }

        // Randevuları gösterecek bir JList oluşturuyoruz
        JList<String> appointmentList = new JList<>(listModel);
        appointmentList.setFont(new Font("Arial", Font.PLAIN, 14)); // Yazı tipi
        appointmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tekli seçim modu
        appointmentList.setVisibleRowCount(10); // Görünen satır sayısı

        // Özelleştirilmiş hücre görüntüleyici
        appointmentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                String[] parts = value.toString().split("Tarih:"); // Hasta adı ve tarihi ayır

                if (parts.length == 2) {
                    String patientInfo = parts[0].trim(); // Hasta adı
                    String dateInfo = "Tarih:" + parts[1].trim(); // Tarih ve saat

                    // Hasta adını kalın ve büyük harf yap
                    label.setText("<html><b>" + patientInfo + "</b><br>" + dateInfo + "</html>");
                }

                // Seçilen öğeyi vurgulamak için
                if (isSelected) {
                    label.setBackground(list.getSelectionBackground());
                    label.setForeground(list.getSelectionForeground());
                } else {
                    label.setBackground(list.getBackground());
                    label.setForeground(list.getForeground());
                }

                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(appointmentList);

        // Butonlar için bir panel oluşturuyoruz
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Butonları ortala
        buttonPanel.setBackground(new Color(245, 245, 245)); // Arka plan rengi

        // Geri butonu
        JButton backButton = new JButton("Geri");
        backButton.setBackground(new Color(70, 130, 180)); // Buton arka plan rengi
        backButton.setForeground(Color.WHITE); // Buton yazı rengi
        backButton.setFont(new Font("Arial", Font.BOLD, 12)); // Buton yazı tipi
        backButton.setPreferredSize(new Dimension(100, 25)); // Buton boyutu
        backButton.addActionListener(e -> dispose()); // Geri butonuna tıklandığında pencereyi kapat

        // Randevuyu sil butonu
        JButton deleteButton = new JButton("Randevuyu Sil");
        deleteButton.setBackground(new Color(220, 20, 60)); // Sil butonu arka plan rengi
        deleteButton.setForeground(Color.WHITE); // Buton yazı rengi
        deleteButton.setFont(new Font("Arial", Font.BOLD, 12)); // Buton yazı tipi
        deleteButton.setPreferredSize(new Dimension(100, 25)); // Buton boyutu

        // Sil butonu aksiyonu
        deleteButton.addActionListener(e -> {
            int selectedIndex = appointmentList.getSelectedIndex();
            if (selectedIndex != -1) {
                // Seçilen randevunun ID'sini alın
                Appointment selectedAppointment = appointments.get(selectedIndex);
                int appointmentId = selectedAppointment.getAppointment_id();

                // Randevuyu sil
                appointmentService.deleteAppointment(appointmentId);

                // Listeden silinmiş randevuyu kaldır
                listModel.remove(selectedIndex);
                appointments.remove(selectedIndex); // appointments listesinden de kaldır

                // Eğer liste boşsa kullanıcıyı bilgilendirin
                if (listModel.isEmpty()) {
                    listModel.addElement("Hiç randevunuz yok.");
                }

                JOptionPane.showMessageDialog(this, "Randevu silindi."); // Kullanıcıya bilgi ver
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen silmek için bir randevu seçin.");
            }
        });

        // Randevu güncelleme butonu
        JButton updateButton = new JButton("<html>Randevu<br>Güncelle</html>");
        updateButton.setBackground(new Color(50, 205, 50)); // Güncelleme butonu arka plan rengi
        updateButton.setForeground(Color.WHITE); // Buton yazı rengi
        updateButton.setFont(new Font("Arial", Font.BOLD, 12)); // Buton yazı tipi
        updateButton.setPreferredSize(new Dimension(100, 25)); // Buton boyutu

        // Güncelleme butonu aksiyonu
        updateButton.addActionListener(e -> {
            int selectedIndex = appointmentList.getSelectedIndex();
            if (selectedIndex != -1) {
                Appointment selectedAppointment = appointments.get(selectedIndex);
                JPanel updatePanel = new JPanel(new GridLayout(0, 1));

                JFormattedTextField dateField = createDateField();
                dateField.setText(selectedAppointment.getAppointmentDate().toString());
                JFormattedTextField timeField = createTimeField();
                timeField.setText(selectedAppointment.getAppointmentTime().toString());

                updatePanel.add(new JLabel("Randevu Tarihi (YYYY-MM-DD):"));
                updatePanel.add(dateField);
                updatePanel.add(new JLabel("Randevu Saati (HH:MM):"));
                updatePanel.add(timeField);

                int result = JOptionPane.showConfirmDialog(this, updatePanel, "Randevu Güncelle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (result == JOptionPane.OK_OPTION) {
                    try {
                        LocalDate updatedDate = LocalDate.parse(dateField.getText().trim());
                        LocalTime updatedTime = LocalTime.parse(timeField.getText().trim());

                        // Randevu tarihini ve saatini ayarlayın
                        selectedAppointment.setAppointmentDate(Date.valueOf(updatedDate));
                        selectedAppointment.setAppointmentTime(Time.valueOf(updatedTime));

                        // Tarih ve saat kontrolü
                        if (appointmentService.controlDate(Date.valueOf(updatedDate), Time.valueOf(updatedTime), doctor.getId(), selectedAppointment)) {
                            appointmentService.updateAppointment(selectedAppointment);
                            String name = patientService.selectedWithIdPatient(selectedAppointment.getIdPatientFk()).getName();
                            String surname = patientService.selectedWithIdPatient(selectedAppointment.getIdPatientFk()).getSurname();
                            listModel.set(selectedIndex, "Hasta: " + name + " " + surname +
                                    " Tarih: " + selectedAppointment.getAppointmentDate() +
                                    " Saat: " + selectedAppointment.getAppointmentTime());

                            JOptionPane.showMessageDialog(this, "Randevu güncellendi.");

                        } else {
                            JOptionPane.showMessageDialog(this, "Çakışan randevu var.");
                        }

                    } catch (DateTimeParseException ex) {
                        JOptionPane.showMessageDialog(this, "Geçersiz tarih formatı. Lütfen YYYY-MM-DD formatında giriniz.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Lütfen güncellemek için bir randevu seçin.");
            }
        });

        // Butonları buttonPanel'e ekliyoruz
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        // ScrollPane ve buton panelini ana panele ekliyoruz
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    // Tarih alanı için bir JFormattedTextField oluşturur
    private JFormattedTextField createDateField() {
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            return new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JFormattedTextField();
    }

    // Saat alanı için bir JFormattedTextField oluşturur
    private JFormattedTextField createTimeField() {
        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            return new JFormattedTextField(timeFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new JFormattedTextField();
    }
}
