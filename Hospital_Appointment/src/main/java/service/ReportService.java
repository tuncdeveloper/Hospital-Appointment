package service;

import entity.Report;
import repository.ConnectDb;
import repository.ReportDb;

import java.util.ArrayList;

public class ReportService {

    ReportDb reportDb = new ReportDb();


    public void addReport(Report report){
        reportDb.newReport(report);
    }


    public ArrayList<Report> listFromPatient(int idReportFk){
        return reportDb.showReportFromPatient(idReportFk);
    }

    public ArrayList<Report> listFromDoctor(int idDoctorFk){
        return reportDb.showReportFromDoctor(idDoctorFk);
    }

    public void deleteReport(int idReport){
        reportDb.deleteReportDb(idReport);
    }

    public void updateReport(Report report){
            reportDb.updateReportDb(report);
    }


}
