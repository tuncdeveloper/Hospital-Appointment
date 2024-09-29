package service;

import entity.Doctor;
import repository.ConnectDb;
import repository.DoctorDb;

import javax.print.Doc;
import java.sql.Connection;
import java.util.ArrayList;

public class DoctorService {

    private DoctorDb doctorDb ;

    public DoctorService () {
        doctorDb =  new DoctorDb() ;
    }

    public void addDoctor(Doctor doctor){

        doctorDb.newDoctorDb(doctor);
    }

    public void deleteDoctor(Doctor doctor){

        doctorDb.deleteDoctorDb(doctor);

    }

    public Doctor foundDoctor(String password){

        return doctorDb.findDoctorDb(password);
    }

    public Doctor selectetWithIdDoctor(int id){
        return doctorDb.selectedWithIdDoctorDb(id);
    }


    public ArrayList<Doctor> showDoctor(){

        return doctorDb.showDoctorList();
    }

    public ArrayList<Doctor> showDoctorFromAppointmentFromReport(int idDoctorFk){
        return doctorDb.showDoctorFromAppointmentFromReportDb(idDoctorFk);
    }



}
