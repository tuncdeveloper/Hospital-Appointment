package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {


    private int appointment_id;
    private Date appointmentDate;
    private Time appointmentTime;
    private int idDoctorFk;
    private int idPatientFk;


    public Appointment(){

    }

    public Appointment(int appointment_id, Date appointmentDate, Time appointmentTime, int idDoctorFk, int idPatientFk) {
        this.appointment_id = appointment_id;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.idDoctorFk = idDoctorFk;
        this.idPatientFk = idPatientFk;
    }

    public int getIdDoctorFk() {
        return idDoctorFk;
    }

    public void setIdDoctorFk(int idDoctorFk) {
        this.idDoctorFk = idDoctorFk;
    }

    public int getIdPatientFk() {
        return idPatientFk;
    }

    public void setIdPatientFk(int idPatientFk) {
        this.idPatientFk = idPatientFk;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public Time getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Time appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(int appointment_id) {
        this.appointment_id = appointment_id;
    }

    @Override
    public String toString() {
        return "  Randevu tarihi = " + appointmentDate +
                " - Randevu saati = " + appointmentTime;

    }
}
