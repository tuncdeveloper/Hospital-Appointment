package gui;

import entity.Admin;
import entity.Doctor;
import entity.Gender;
import entity.Patient;
import service.AdminService;
import service.DoctorService;
import service.PatientService;
import test.InitCall;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class MenuGui extends JFrame implements InitCall {

    PatientService patientService = new PatientService();
    DoctorService doctorService = new DoctorService();
    AdminService adminService = new AdminService();

    Patient patient = new Patient();

    public MenuGui() {

    }

    @Override
    public void initWindow() {
        JPanel panel = initPanel();
        add(panel);
        setTitle("Menü");
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public JPanel initPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(173, 216, 230)); // Açık mavi arka plan

        JLabel welcomeLabel = new JLabel("HOŞGELDİNİZ", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 102, 204));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 50)));
        panel.add(welcomeLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JButton adminLoginButton = createStyledButton("Admin Giriş");
        panel.add(adminLoginButton);

        adminLoginButton.addActionListener(e -> showAdminLoginForm());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton patientRegisterButton = createStyledButton("Hasta Kayıt");
        panel.add(patientRegisterButton);
        patientRegisterButton.addActionListener(e -> showPatientRegisterForm());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton patientLoginButton = createStyledButton("Hasta Giriş");
        panel.add(patientLoginButton);
        patientLoginButton.addActionListener(e -> showPatientLoginForm());

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Doktor Giriş Butonu Eklendi
        JButton doctorLoginButton = createStyledButton("Doktor Giriş");
        panel.add(doctorLoginButton);
        doctorLoginButton.addActionListener(e -> showDoctorLoginForm());

        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setMaximumSize(new Dimension(200, 40));
        button.setBackground(new Color(0, 102, 204));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
    private void showAdminLoginForm() {
        JDialog loginDialog = new JDialog(this, "Admin Giriş", true);
        loginDialog.setSize(300, 150);
        loginDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(173, 216, 230));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel passwordLabel = new JLabel("Şifre:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Giriş");
        JButton cancelButton = new JButton("İptal");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        loginButton.addActionListener(e -> {
            String inputPassword = new String(passwordField.getPassword()).trim();

            // Admin doğrulama işlemi
            ArrayList<Admin> foundAdmins = adminService.loginAdmin(inputPassword);
            boolean isAuthenticated = false;

            for (Admin foundAdmin : foundAdmins) {
                if (inputPassword.equals(foundAdmin.getPassword())) {
                    isAuthenticated = true;

                    JOptionPane.showMessageDialog(null, "Hoşgeldiniz "+foundAdmin.getName());
                    loginDialog.dispose();
                    AdminGui adminGui = new AdminGui(foundAdmin);
                    adminGui.initWindow();
                    break; // Doğru şifre bulunduğunda döngüyü kır
                }
            }

            if (!isAuthenticated) {
                JOptionPane.showMessageDialog(null, "Geçersiz şifre", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> loginDialog.dispose());

        loginDialog.add(panel);
        loginDialog.setVisible(true);
    }




    public void showPatientRegisterForm() {
        JDialog registerDialog = new JDialog(this, "Hasta Kayıt", true);
        registerDialog.setSize(350, 450);
        registerDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2, 10, 10));

        JLabel nameLabel = new JLabel("Ad:");
        JTextField nameField = new JTextField();
        JLabel surnameLabel = new JLabel("Soyad:");
        JTextField surnameField = new JTextField();
        JLabel birthDateLabel = new JLabel("Doğum Tarihi:");
        JFormattedTextField birthDateField = createBirthDateField();

        JLabel genderLabel = new JLabel("Cinsiyet:");
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JRadioButton maleButton = new JRadioButton("MALE");
        JRadioButton femaleButton = new JRadioButton("FEMALE");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);

        JLabel phoneLabel = new JLabel("Telefon Numarası:");
        JFormattedTextField phoneField = createPhoneNumberField();
        JLabel addressLabel = new JLabel("Adres:");
        JTextField addressField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(surnameLabel);
        panel.add(surnameField);
        panel.add(birthDateLabel);
        panel.add(birthDateField);
        panel.add(genderLabel);
        panel.add(genderPanel);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(addressLabel);
        panel.add(addressField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton saveButton = new JButton("Kaydet");
        JButton cancelButton = new JButton("İptal");
        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String surname = surnameField.getText().trim();
            String birthDate = birthDateField.getText().trim();
            String gender = maleButton.isSelected() ? "FEMALE" : femaleButton.isSelected() ? "FEMALE" : "";
            String phone = phoneField.getText().trim();
            String address = addressField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || surname.isEmpty() || birthDate.isEmpty() || gender.isEmpty() ||
                    phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog, "Lütfen tüm boş alanları doldurunuz.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    LocalDate parsedBirthDate = LocalDate.parse(birthDate, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    patient.setName(name);
                    patient.setSurname(surname);
                    patient.setBirthDate(parsedBirthDate);
                    patient.setGender(Gender.valueOf(gender));
                    patient.setPhoneNumber(phone);
                    patient.setAddress(address);
                    patient.setPassword(password);
                    patient.setTc(patientService.randomTC());

                    patientService.addPatient(patient);
                    JOptionPane.showMessageDialog(registerDialog, name + " kişisi başarılı bir şekilde kaydedilmiştir.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    registerDialog.dispose(); // Pencereyi kapat
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(registerDialog, "Lütfen geçerli bir doğum tarihi girin (dd.MM.yyyy).", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> registerDialog.dispose());

        registerDialog.add(panel);
        registerDialog.setVisible(true);
    }

    private void showPatientLoginForm() {
        JDialog loginDialog = new JDialog(this, "Hasta Giriş", true);
        loginDialog.setSize(300, 200);
        loginDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel tcLabel = new JLabel("TC:");
        JTextField tcField = new JTextField();
        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(tcLabel);
        panel.add(tcField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Giriş");
        JButton cancelButton = new JButton("İptal");

        panel.add(loginButton);
        panel.add(cancelButton);

        loginButton.addActionListener(e -> {
            String inputTC = tcField.getText().trim();
            String inputPassword = new String(passwordField.getPassword()).trim();

            Patient foundPatient = patientService.selectedPatient(inputTC);

            if (foundPatient != null && inputTC.equals(foundPatient.getTc()) && inputPassword.equals(foundPatient.getPassword())) {
                JOptionPane.showMessageDialog(null,"Hoşgeldiniz " + foundPatient.getName());

                SwingUtilities.invokeLater(() -> {
                    PatientGui patientGui = new PatientGui(foundPatient);
                    patientGui.initWindow();
                    patientGui.requestFocus(); // Pencereyi odakla
                });

                loginDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Kişi bulunamadı");
            }
        });

        cancelButton.addActionListener(e -> loginDialog.dispose());

        loginDialog.add(panel);
        loginDialog.setVisible(true);
    }

    private void showDoctorLoginForm() {
        JDialog loginDialog = new JDialog(this, "Doktor Giriş", true);
        loginDialog.setSize(300, 200);
        loginDialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));


        JLabel passwordLabel = new JLabel("Şifre:");
        JPasswordField passwordField = new JPasswordField();


        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Giriş");
        JButton cancelButton = new JButton("İptal");

        panel.add(loginButton);
        panel.add(cancelButton);

        // Doktor giriş butonuna aksiyon ekleyin
        loginButton.addActionListener(e -> {
            String inputPassword = new String(passwordField.getPassword()).trim();

            Doctor foundDoctor = doctorService.foundDoctor(inputPassword);


            if (inputPassword.equals(foundDoctor.getPassword())) {



                JOptionPane.showMessageDialog(null, "Hoşgeldiniz Doktor "+foundDoctor.getName());

                SwingUtilities.invokeLater(() -> {
                    DoctorGui doctorGui = new DoctorGui(foundDoctor);
                    doctorGui.initWindow();
                    doctorGui.requestFocus();
                });
                loginDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Geçersiz şifre", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> loginDialog.dispose());

        loginDialog.add(panel);
        loginDialog.setVisible(true);
    }

    private JFormattedTextField createBirthDateField() {
        try {
            MaskFormatter dateFormatter = new MaskFormatter("##.##.####");
            dateFormatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(dateFormatter);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private JFormattedTextField createPhoneNumberField() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("+090##########");
            formatter.setPlaceholderCharacter('_'); // Boş yerler "_" ile gözükecek
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JFormattedTextField phoneField = new JFormattedTextField(formatter);
        phoneField.setColumns(10);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        return phoneField;
    }

}
