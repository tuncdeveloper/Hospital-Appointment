package gui;

import entity.Patient;
import entity.Report;
import service.DoctorService;
import service.ReportService;
import test.InitCall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ReportFromPatientGui extends JFrame implements InitCall {

    private Patient patient;
    private ReportService reportService; // Rapor servisi
    private JList<String> reportList; // Raporları göstermek için JList
    DoctorService doctorService = new DoctorService();

    public ReportFromPatientGui(Patient patient) {
        this.patient = patient;
        this.reportService = new ReportService(); // Rapor servisini başlatıyoruz
    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Raporlar");
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE); // Arka plan rengi beyaz

        // Raporları gösteren liste
        ArrayList<Report> reports = reportService.listFromPatient(patient.getId()); // Hastanın raporlarını alıyoruz
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Report report : reports) {
            String doctorName = doctorService.selectetWithIdDoctor(report.getIdDoctorFk()).getName().toUpperCase(); // Doktor ismi büyük harf
            String doctorSurname = doctorService.selectetWithIdDoctor(report.getIdDoctorFk()).getSurname().toUpperCase(); // Doktor soyismi büyük harf
            String doctorSpecialty = doctorService.selectetWithIdDoctor(report.getIdDoctorFk()).getSpecialty();
            // Doktor ismi ve soyadı üstte, diğer bilgiler altta olacak şekilde formatlandı
            String reportEntry = String.format("<html><b>%s %s</b><br/>Bölüm: %s<br/>Tarih: %s<br/>Saat: %s<br/>Rapor: %s</html>",
                    doctorName, doctorSurname, doctorSpecialty, report.getReportDate(), report.getReportTime(), report.getReportContent());
            listModel.addElement(reportEntry);
        }

        reportList = new JList<>(listModel);
        reportList.setFont(new Font("Arial", Font.PLAIN, 14)); // Yazı tipi
        reportList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font("Arial", Font.PLAIN, 14)); // Genel yazı tipi
                return label;
            }
        });

        JScrollPane scrollPane = new JScrollPane(reportList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Kenar boşluğu
        panel.add(scrollPane, BorderLayout.CENTER);

        // Geri butonu
        JButton backButton = new JButton("Geri");
        backButton.setBackground(Color.RED); // Arka plan rengi kırmızı
        backButton.setForeground(Color.WHITE); // Yazı rengi beyaz
        backButton.setFont(new Font("Arial", Font.BOLD, 16)); // Yazı tipi ve boyutu
        backButton.setFocusPainted(false); // Odaklanma çerçevesi yok
        backButton.setBorder(BorderFactory.createLineBorder(Color.RED)); // Kenar rengi
        backButton.setPreferredSize(new Dimension(100, 40)); // Buton boyutu
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Geçerli pencereyi kapat
                // Burada geri yönlendirme işlemi ekleyebilirsiniz
            }
        });

        // Butonu ortalamak için bir panel oluştur
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Butonu ortala
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH); // Butonu panelin altına ekliyoruz
        return panel;
    }
}
