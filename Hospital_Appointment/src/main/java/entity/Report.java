package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Report {

    private int idReport;
    private Date reportDate;
    private Time reportTime;
    private String reportContent;
    private int idDoctorFk;
    private int idPatientFk;
    public Report(){

    }

    public Report(int idReport, Date reportDate, Time reportTime, String reportContent, int idDoctorFk, int idPatientFk) {
        this.idReport = idReport;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.reportContent = reportContent;
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

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Time getReportTime() {
        return reportTime;
    }

    public void setReportTime(Time reportTime) {
        this.reportTime = reportTime;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    @Override
    public String toString() {
        return "  Rapor tarihi = " + reportDate +
                " - Rapor saati = " + reportTime ;
    }
}
