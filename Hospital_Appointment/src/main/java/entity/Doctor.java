package entity;

import java.util.ArrayList;

public class Doctor extends Person{

    private String specialty;
    private String hospitalName;
    private ArrayList<Appointment> appointments;
    private ArrayList<Report>reports;


    public Doctor() {
    }


    public Doctor(String name, String surname, String password, String specialty, String hospitalName) {
        super(name, surname, password);
        this.specialty = specialty;
        this.hospitalName = hospitalName;
    }

    public Doctor(int id, String name, String surname, String password, String specialty, String hospitalName, ArrayList<Appointment> appointments, ArrayList<Report> reports) {
        super(id, name, surname, password);
        this.specialty = specialty;
        this.hospitalName = hospitalName;
        this.appointments = appointments;
        this.reports = reports;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
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
        return getName() + " " + getSurname() + " - " + getSpecialty();
    }

}
