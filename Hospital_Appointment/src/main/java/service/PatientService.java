package service;

import com.github.javafaker.Faker;
import entity.Patient;
import entity.Gender;
import repository.PatientDb;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PatientService {

    private PatientDb patientDb;
    private Faker faker;
    private Random random;

    public PatientService() {
        this.faker = new Faker();
        this.random = new Random();
        this.patientDb = new PatientDb();
    }

    public void addPatient(Patient patient) {
        patientDb.newPatientDb(patient);
    }

    public Patient selectedPatient(String tc) {
        return patientDb.FindPatientDb(tc);  // Sadece TC ile hasta bulunuyor
    }

    public Patient selectedWithIdPatient(int id) {
        return patientDb.selectedWithIdPatientDb(id);
    }

    public ArrayList<Patient> showPatient() {
        return patientDb.showPatientListDb();
    }

    public void deletePatient(Patient patient) {
        patientDb.deletePatientDb(patient);
    }

    public ArrayList<Patient> showPatientTcNameSurname(String tc, String name, String surname) {
        return patientDb.showListNameSurname(tc, name, surname);
    }

    // 11 haneli TC numarası üretiyor
    public String generatedTC() {
        // 10000000000 ile 99999999999 arasında rastgele TC üretmek
        return String.valueOf(faker.number().numberBetween(10000000000L, 99999999999L));
    }

    public boolean controlRandom(String sayi) {
        boolean flag = true;

        for (String rdmControl : patientDb.showListPatientTC()) {
            if (sayi.equals(rdmControl)) {
                flag = false;
                return flag;
            }
        }

        return flag;
    }

    public String randomTC() {
        String rdm = generatedTC();

        if (controlRandom(rdm)) {
            return rdm;
        } else {
            System.out.println("benzer sayi : " + rdm);
            return null;
        }
    }

    public void updatePatient(Patient patient) {
        patientDb.updatePatientDb(patient);
    }

    public ArrayList<Patient> showPatientFromAppointmentFromReport(int idPatientFk) {
        return patientDb.showPatinentFromAppointmentFromReportDb(idPatientFk);
    }

    // 40 adet rastgele hasta oluşturuyor
    public List<Patient> generateRandomPatients() {
        List<Patient> patients = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            Patient patient = createRandomPatient();
            patients.add(patient);
            patientDb.newPatientDb(patient);
        }

        return patients;
    }

    // Rastgele hasta oluşturuyor
    private Patient createRandomPatient() {
        Patient patient = new Patient();

        // Rastgele isim, soyisim, şifre
        patient.setName(faker.name().firstName());
        patient.setSurname(faker.name().lastName());
        patient.setPassword(faker.internet().password(8, 12));
        patient.setGender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE);

        // 05 ile başlayan telefon numarası
        String phoneNumber = "+0905" + faker.number().digits(9);  // 9 haneli rastgele bir sayı ve 05 ile başlatıyoruz
        patient.setPhoneNumber(phoneNumber);

        String address = new Faker(new Locale("tr")).address().fullAddress();  // Türkçe adres oluşturuluyor
        patient.setAddress(address);

        // Rastgele doğum tarihi
        LocalDate birthDate = faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        patient.setBirthDate(birthDate);

        // Rastgele TC numarası
        String tc = randomTC();
        patient.setTc(tc);

        return patient;
    }
}
