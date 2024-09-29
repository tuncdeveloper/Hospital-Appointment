package gui;

import entity.Doctor;
import entity.Patient;
import entity.Report;
import service.DoctorService;
import service.PatientService;
import service.ReportService;
import test.InitCall;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReportFromDoctorGui extends JFrame implements InitCall {

    private final Doctor doctor;
    private final ReportService reportService = new ReportService();
    private final DefaultListModel<String> reportListModel = new DefaultListModel<>();
    private ArrayList<Report> reports; // Raporları tutacak bir liste
    PatientService patientService = new PatientService();

    public ReportFromDoctorGui(Doctor doctor) {
        this.doctor = doctor;
        loadReports(); // Raporları yükle
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Doktor Raporları");
        setSize(400, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Başlık
        JLabel titleLabel = new JLabel("Doktor Raporları", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(titleLabel, BorderLayout.NORTH);

        // Raporları gösterecek bir JList oluşturuyoruz
        JList<String> reportList = new JList<>(reportListModel);
        reportList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(reportList);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Butonlar için bir panel oluşturma
        JPanel buttonPanel = new JPanel(new FlowLayout());

        // Rapor Sil butonu
        JButton deleteReportButton = new JButton("Rapor Sil");
        deleteReportButton.setBackground(Color.RED);
        deleteReportButton.setForeground(Color.WHITE);
        deleteReportButton.setFocusPainted(false);
        deleteReportButton.setBorderPainted(false);

        // ActionListener kullanılarak rapor silme işlemi
        deleteReportButton.addActionListener(e -> {
            int selectedIndex = reportList.getSelectedIndex(); // Seçilen indeksi al
            if (selectedIndex != -1) {
                // Seçilen raporun ID'sini alın
                Report selectedReport = reports.get(selectedIndex);
                int reportId = selectedReport.getIdReport();

                // Raporu veritabanından sil
                reportService.deleteReport(reportId);

                // Listeden ve reports listesinden sil
                reportListModel.remove(selectedIndex);
                reports.remove(selectedIndex);

                // Eğer liste boşsa kullanıcıyı bilgilendirin
                if (reportListModel.isEmpty()) {
                    reportListModel.addElement("Hiç rapor yok.");
                }

                JOptionPane.showMessageDialog(this, "Rapor silindi.");
            } else {
                JOptionPane.showMessageDialog(this, "Silmek için bir rapor seçin.");
            }
        });

        buttonPanel.add(deleteReportButton);

        // Rapor Ekle butonu
        JButton addReportButton = new JButton("Rapor Ekle");
        addReportButton.setBackground(Color.RED);
        addReportButton.setForeground(Color.WHITE);
        addReportButton.setFocusPainted(false);
        addReportButton.setBorderPainted(false);


        addReportButton.addActionListener(e -> {
            // Hasta adı, soyadı ve TC için bir panel oluştur
            JPanel patientInputPanel = new JPanel(new GridLayout(3, 2));
            JTextField patientNameField = new JTextField();
            JTextField patientSurnameField = new JTextField();
            JTextField patientTcField = new JTextField();

            // Panel bileşenleri ekleniyor
            patientInputPanel.add(new JLabel("Hasta Adı:"));
            patientInputPanel.add(patientNameField);
            patientInputPanel.add(new JLabel("Hasta Soyadı:"));
            patientInputPanel.add(patientSurnameField);
            patientInputPanel.add(new JLabel("Hasta TC:"));
            patientInputPanel.add(patientTcField);

            // Dialog açma
            int patientOption = JOptionPane.showConfirmDialog(this, patientInputPanel, "Hasta Bilgileri", JOptionPane.OK_CANCEL_OPTION);
            if (patientOption != JOptionPane.OK_OPTION) return;

            // Hasta bilgilerini kontrol et
            String patientName = patientNameField.getText().trim();
            String patientSurname = patientSurnameField.getText().trim();
            String patientTc = patientTcField.getText().trim();

            Patient foundPatient = null; // Bulunan hasta
            for (Patient patient : patientService.showPatientTcNameSurname(patientTc, patientName, patientSurname)) {
                if (patient != null) {
                    foundPatient = patient; // Hasta bulundu
                    break; // İlk bulunan hasta ile döngüden çık
                }
            }

            if (foundPatient == null) {
                JOptionPane.showMessageDialog(this, "Hasta bulunamadı veya bilgileri yanlış.");
                return;
            } else {
                JOptionPane.showMessageDialog(this, foundPatient.getName() + " adlı hasta bulundu");

                // Rapor ekleme penceresi
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                JTextField reportContentField = new JTextField();
                JFormattedTextField dateField = createDateField();
                JFormattedTextField timeField = createTimeField();

                inputPanel.add(new JLabel("Rapor İçeriği:"));
                inputPanel.add(reportContentField);
                inputPanel.add(new JLabel("Rapor Tarihi (YYYY-MM-DD):"));
                inputPanel.add(dateField);
                inputPanel.add(new JLabel("Rapor Saati (HH:MM):"));
                inputPanel.add(timeField);

                int option = JOptionPane.showConfirmDialog(this, inputPanel, "Rapor Ekle", JOptionPane.OK_CANCEL_OPTION);
                if (option != JOptionPane.OK_OPTION) return;

                // Rapor ekleme mantığı burada...
                String reportContent = reportContentField.getText();
                if (reportContent.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Rapor içeriği boş olamaz.");
                    return;
                }

                // Tarih ve saat kontrolü
                String reportDateStr = dateField.getText();
                String reportTimeStr = timeField.getText();
                if (!isValidDate(reportDateStr) || !isValidTime(reportTimeStr)) {
                    JOptionPane.showMessageDialog(this, "Tarih veya saat formatı hatalı. Lütfen doğru formatta girin.");
                    return;
                }

                try {
                    // Tarih ve saat dönüştürme işlemleri...
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(reportDateStr);
                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    Date parsedTime = timeFormat.parse(reportTimeStr);
                    Time sqlTime = new Time(parsedTime.getTime());

                    // Yeni rapor ekleme
                    Report newReport = new Report();
                    newReport.setIdDoctorFk(doctor.getId());
                    newReport.setIdPatientFk(foundPatient.getId()); // Bulunan hastanın ID'si
                    newReport.setReportContent(reportContent);
                    newReport.setReportDate(sqlDate);
                    newReport.setReportTime(sqlTime);

                    // Veritabanına ekle
                    reportService.addReport(newReport);
                    // Listeyi güncelle
                    String formattedReport = String.format("<html><b style='font-size:20px;'>%s %s</b><br>Tarih: %s, Saat: %s, Rapor: %s</html>",
                            foundPatient.getName().toUpperCase(), foundPatient.getSurname().toUpperCase(), newReport.getReportDate(), newReport.getReportTime(), newReport.getReportContent());

                    reportListModel.addElement(formattedReport);
                    JOptionPane.showMessageDialog(this, "Rapor başarıyla eklendi.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Tarih veya saat formatı hatalı. Lütfen doğru formatta girin.");
                }
            }
        });



        // Rapor Güncelle butonu
        JButton updateReportButton = new JButton("Rapor Güncelle");
        updateReportButton.setBackground(Color.RED);
        updateReportButton.setForeground(Color.WHITE);
        updateReportButton.setFocusPainted(false);
        updateReportButton.setBorderPainted(false);

        // ActionListener kullanılarak rapor güncelleme işlemi
        updateReportButton.addActionListener(e -> {
            int selectedIndex = reportList.getSelectedIndex(); // Seçilen indeksi al
            if (selectedIndex != -1) {
                Report selectedReport = reports.get(selectedIndex);

                // Rapor içeriği, tarih ve saat girişi için bir panel oluştur
                JPanel inputPanel = new JPanel(new GridLayout(3, 2));
                JTextField reportContentField = new JTextField(selectedReport.getReportContent());
                JFormattedTextField dateField = createDateField();
                dateField.setText(selectedReport.getReportDate().toString()); // Tarih
                JFormattedTextField timeField = createTimeField();
                timeField.setText(selectedReport.getReportTime().toString()); // Saat

                // Panel bileşenleri ekleniyor
                inputPanel.add(new JLabel("Rapor İçeriği:"));
                inputPanel.add(reportContentField);
                inputPanel.add(new JLabel("Rapor Tarihi (YYYY-MM-DD):"));
                inputPanel.add(dateField);
                inputPanel.add(new JLabel("Rapor Saati (HH:MM):"));
                inputPanel.add(timeField);

                // Dialog açma
                int option = JOptionPane.showConfirmDialog(this, inputPanel, "Rapor Güncelle", JOptionPane.OK_CANCEL_OPTION);
                if (option != JOptionPane.OK_OPTION) return;

                // Rapor içeriğini kontrol et
                String reportContent = reportContentField.getText();
                if (reportContent.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Rapor içeriği boş olamaz.");
                    return;
                }

                // Tarih ve saat formatlarını kontrol et
                String reportDateStr = dateField.getText();
                String reportTimeStr = timeField.getText();
                if (!isValidDate(reportDateStr)) {
                    JOptionPane.showMessageDialog(this, "Tarih formatı hatalı. Lütfen YYYY-MM-DD formatında girin.");
                    return;
                }
                if (!isValidTime(reportTimeStr)) {
                    JOptionPane.showMessageDialog(this, "Saat formatı hatalı. Lütfen HH:MM formatında girin.");
                    return;
                }

                try {
                    // String'i java.sql.Date'e dönüştürme
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date parsedDate = dateFormat.parse(reportDateStr); // String to util.Date
                    java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime()); // util.Date to sql.Date

                    // String'i java.sql.Time'a dönüştürme
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                    Date parsedTime = timeFormat.parse(reportTimeStr); // String to util.Date
                    Time sqlTime = new Time(parsedTime.getTime()); // util.Date to sql.Time

                    // Rapor güncellemelerini yap
                    selectedReport.setReportContent(reportContent); // Güncellenen içerik
                    selectedReport.setReportDate(sqlDate); // Güncellenen tarih
                    selectedReport.setReportTime(sqlTime); // Güncellenen saat

                    // Veritabanında güncelle
                    reportService.updateReport(selectedReport);

                    // Listeyi güncelle
                    String patientName = patientService.selectedWithIdPatient(selectedReport.getIdPatientFk()).getName();
                    String patientSurname = patientService.selectedWithIdPatient(selectedReport.getIdPatientFk()).getSurname();

                    // Raporu biçimlendirilmiş olarak ekleyin
                    String formattedReport = String.format("<html><b style='font-size:20px;'>%s %s</b><br>Tarih: %s, Saat: %s, Rapor: %s</html>",
                            patientName.toUpperCase(), patientSurname.toUpperCase(), selectedReport.getReportDate(), selectedReport.getReportTime(), selectedReport.getReportContent());

                    reportListModel.set(selectedIndex, formattedReport);

                    JOptionPane.showMessageDialog(this, "Rapor başarıyla güncellendi.");
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(this, "Tarih veya saat formatı hatalı. Lütfen doğru formatta girin.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Güncellemek için bir rapor seçin.");
            }
        });

        buttonPanel.add(addReportButton);
        buttonPanel.add(updateReportButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Geri Butonu
        JButton backButton = new JButton("Geri");
        backButton.setBackground(Color.RED);
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);

// ActionListener kullanılarak geri butonu işlevi
        backButton.addActionListener(e -> {
            // Pencereyi kapat
            dispose();
        });
        buttonPanel.add(updateReportButton);

// Geri butonu paneline ekleniyor
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadReports() {
        reports = reportService.listFromDoctor(doctor.getId());
        reportListModel.clear();

        if (reports.isEmpty()) {
            reportListModel.addElement("Hiç rapor yok.");
        } else {
            for (Report report : reports) {
                Patient patient = patientService.selectedWithIdPatient(report.getIdPatientFk());
                String formattedReport = String.format("<html><b style='font-size:20px;'>%s %s</b><br>Tarih: %s, Saat: %s, Rapor: %s</html>",
                        patient.getName().toUpperCase(), patient.getSurname().toUpperCase(), report.getReportDate(), report.getReportTime(), report.getReportContent());
                reportListModel.addElement(formattedReport);
            }
        }
    }

    // Method to create a date input field with a specific format
    private JFormattedTextField createDateField() {
        try {
            MaskFormatter dateFormatter = new MaskFormatter("####-##-##");
            dateFormatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            throw new RuntimeException("Error creating date field", e);
        }
    }

    // Method to create a time input field with a specific format
    private JFormattedTextField createTimeField() {
        try {
            MaskFormatter timeFormatter = new MaskFormatter("##:##");
            timeFormatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(timeFormatter);
        } catch (ParseException e) {
            throw new RuntimeException("Error creating time field", e);
        }
    }

    // Method to validate the date format
    private boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Method to validate the time format
    private boolean isValidTime(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        sdf.setLenient(false);
        try {
            sdf.parse(timeStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
