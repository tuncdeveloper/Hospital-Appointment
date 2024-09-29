package service;

import entity.Patient;
import repository.ConnectDb;
import repository.PatientDb;

import java.util.ArrayList;
import java.util.Random;

public class PatientService {

    PatientDb patientDb = new PatientDb();

    public void addPatient(Patient patient){
        patientDb.newPatientDb(patient);
    }

    public Patient selectedPatient(String tc) {
        return patientDb.FindPatientDb(tc);  // Sadece TC ile hasta bulunuyor
    }


    public Patient selectedWithIdPatient(int id){
        return patientDb.selectedWithIdPatientDb(id);
    }

    public ArrayList<Patient> showPatient(){
        return patientDb.showPatientListDb();
    }

    public void deletePatient(Patient patient){
        patientDb.deletePatientDb(patient);
    }

    public ArrayList<Patient> showPatientTcNameSurname(String tc,String name,String surname){
      return   patientDb.showListNameSurname(tc,name,surname);
    }


    public int generatedTC(){
        Random random = new Random();
        return random.nextInt(100000000);
    }

    public boolean controlRandom(String sayi){

        boolean flag = true;


        for (String rdmControl : patientDb.showListPatientTC()) {

            if (sayi.equals(rdmControl)) {
                flag = false;
                return flag;
            }

        }


        return flag ;
    }

    public String randomTC(){

        int rdm = generatedTC();
        String strRdm = String.valueOf(rdm);

        if (controlRandom(strRdm)) {
            return strRdm;
        }else{
            System.out.println("benzer sayi : "+rdm);
            return null;
        }

    }

    public void updatePatient(Patient patient){
         patientDb.updatePatientDb(patient);
    }


    public ArrayList<Patient> showPatientFromAppointmentFromReport(int idPatientFk){
        return patientDb.showPatinentFromAppointmentFromReportDb(idPatientFk);
    }



}
