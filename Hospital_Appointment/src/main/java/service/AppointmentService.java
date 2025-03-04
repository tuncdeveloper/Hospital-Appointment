package service;

import entity.Appointment;
import repository.AppointmentDb;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;

public class AppointmentService {

    private AppointmentDb appointmentDb = new AppointmentDb();


    public void addAppointment(Appointment appointment){
        appointmentDb.newAppointmentDb(appointment);
    }

    public ArrayList<Appointment> showAppointmentFromPatient(int idPatientFk){

         return appointmentDb.showAppointmentFromPatient(idPatientFk);

        }

     public ArrayList<Appointment> showAppointmentFromDoctor(int idDoctorFk){
        return appointmentDb.showAppointmentFromDoctor(idDoctorFk);
     }

    public void deleteAppointment(int idAppointment){

         appointmentDb.deleteAppointmentDb(idAppointment);
    }


    public boolean controlDate(Date date, Time time, int idDoctorFk, Appointment newAppointment) {
        boolean flag = true;

        // Mevcut randevuları kontrol ediyoruz
        ArrayList<Appointment> existingAppointments = appointmentDb.showAppointmentControl(date, time, idDoctorFk);

        for (Appointment appointment : existingAppointments) {
            if (newAppointment.getAppointmentDate().equals(appointment.getAppointmentDate()) &&
                    newAppointment.getAppointmentTime().equals(appointment.getAppointmentTime()) &&
                    idDoctorFk == appointment.getDoctorIdFk()) {

                // Çakışma durumu
                System.out.println("Randevu çakışıyor.");
                flag = false;
                break;
            }
        }

        return flag;
    }


    public void updateAppointment(Appointment appointment){
        appointmentDb.updateAppointmentDb(appointment);
    }




}
