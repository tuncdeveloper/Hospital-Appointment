package service;

import com.github.javafaker.Faker;
import entity.Doctor;
import entity.Gender;
import entity.HospitalEnum;
import entity.SpecialtyEnum;
import repository.DoctorDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoctorService {
    private DoctorDb doctorDb;
    private Faker faker;
    private Random random;

    public DoctorService() {
        this.doctorDb = new DoctorDb();
        this.faker = new Faker();
        this.random = new Random();
    }

    private final String[] specialties = {
            "KARDIYOLOG",
            "DERMATOLOG",
            "NOROLOG",
            "COCUK",
            "ORTOPEDIST",
            "PSIKIYATRIST",
            "GASTROENTEROLOG",
            "GENEL_CERRAH",
            "GOZ",
            "KBB",
            "FIZIYOTERAPIST",
            "DAHILIYE",
            "KADIN_DOGUM_UZMANI",
            "ENDOKRINOLOG",
            "NEFROLOG",
            "RADYOLOG",
            "BEYIN_CERRAHI"
    };

    private final String[] hospitalNames = {
            "ACIBADEM_HASTANESI",
            "MEDICANA_SAGLIK_GRUBU",
            "AMERIKAN_HASTANESI",
            "KOC_UNIVERSITESI_HASTANESI",
            "LIV_HOSPITAL",
            "BASKENT_UNIVERSITESI_HASTANESI",
            "ISTANBUL_UNIVERSITESI_CERRAHPASA",
            "KARTAL_KOZYATAGI_DEVLET_HASTANESI",
            "ATATURK_EGITIM_VE_ARASTIRMA_HASTANESI",
            "CAPA_TIP_FAKULTESI_HASTANESI"
    };



    public void addDoctor(Doctor doctor) {
        doctorDb.newDoctorDb(doctor);
    }

    public void deleteDoctor(Doctor doctor) {
        doctorDb.deleteDoctorDb(doctor);
    }

    public Doctor foundDoctor(String password) {
        return doctorDb.findDoctorDb(password);
    }

    public Doctor selectetWithIdDoctor(int id) {
        return doctorDb.selectedWithIdDoctorDb(id);
    }

    public ArrayList<Doctor> showDoctor() {
        return doctorDb.showDoctorList();
    }

    public ArrayList<Doctor> showDoctorFromAppointmentFromReport(int idDoctorFk) {
        return doctorDb.showDoctorFromAppointmentFromReportDb(idDoctorFk);
    }

    // 20 rastgele doktor oluştur ve veritabanına ekle
    public List<Doctor> generateRandomDoctors() {
        List<Doctor> doctors = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            Doctor doctor = createDoctor();
            doctors.add(doctor);
            doctorDb.newDoctorDb(doctor); // Veritabanına ekle


        }

        return doctors;
    }

    // Rastgele doktor oluştur
    private Doctor createDoctor() {
        Doctor doctor = new Doctor();

        // Rastgele isim, soyisim, şifre
        doctor.setName(faker.name().firstName());
        doctor.setSurname(faker.name().lastName());
        doctor.setPassword(faker.internet().password(8, 12));

        // Rastgele cinsiyet
        doctor.setGender(random.nextBoolean() ? Gender.MALE : Gender.FEMALE);

        // Rastgele uzmanlık alanı ve hastane adı
        doctor.setSpecialty(SpecialtyEnum.valueOf(specialties[random.nextInt(specialties.length)]));
        doctor.setHospitalName(HospitalEnum.valueOf(hospitalNames[random.nextInt(hospitalNames.length)]));

        return doctor;
    }
}
