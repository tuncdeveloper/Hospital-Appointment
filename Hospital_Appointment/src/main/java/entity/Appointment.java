package entity;

import java.sql.Date;
import java.sql.Time;

public class Appointment {


    private Integer appointmentId;
    private Date appointmentDate;
    private Time appointmentTime;
    private Integer doctorIdFk;
    private Integer patientIdFk;


    public Appointment(){

    }

    public Appointment(Integer appointmentId, Date appointmentDate, Time appointmentTime, Integer doctorIdFk, Integer patientIdFk) {
        this.appointmentId = appointmentId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.doctorIdFk = doctorIdFk;
        this.patientIdFk = patientIdFk;
    }

    public int getDoctorIdFk() {
        return doctorIdFk;
    }

    public void setDoctorIdFk(int doctorIdFk) {
        this.doctorIdFk = doctorIdFk;
    }

    public int getPatientIdFk() {
        return patientIdFk;
    }

    public void setPatientIdFk(int patientIdFk) {
        this.patientIdFk = patientIdFk;
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

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    public String toString() {
        return "  Randevu tarihi = " + appointmentDate +
                " - Randevu saati = " + appointmentTime;

    }
}
