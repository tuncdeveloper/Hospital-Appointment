package entity;

import java.time.LocalDate;
import java.util.ArrayList;

public class Patient extends Person{

    private LocalDate birthDate;
    private String gender;
    private String phoneNumber;
    private String address;
    private String tc;
    private ArrayList<Appointment> appointments;
    private ArrayList<Report>reports;


    public Patient() {
    }



    public Patient(int id, String name, String surname, String password, LocalDate birthDate, String gender, String phoneNumber, String address, String tc, ArrayList<Appointment> appointments, ArrayList<Report> reports) {
        super(id, name, surname, password);
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.tc = tc;
        this.appointments = appointments;
        this.reports = reports;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public void setReports(ArrayList<Report> reports) {
        this.reports = reports;
    }

    @Override
    public String toString() {
         return getName() + " " + getSurname();
    }
}
