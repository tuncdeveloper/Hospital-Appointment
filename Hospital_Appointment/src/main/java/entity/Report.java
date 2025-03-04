package entity;

import java.sql.Date;
import java.sql.Time;

public class Report {

    private int reportId;
    private Date reportDate;
    private Time reportTime;
    private String reportContent;
    private Integer doctorIdFk;
    private Integer patientIdFk;

    public Report(){

    }

    public Report(int reportId, Date reportDate, Time reportTime, String reportContent, Integer doctorIdFk, Integer patientIdFk) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.reportContent = reportContent;
        this.doctorIdFk = doctorIdFk;
        this.patientIdFk = patientIdFk;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Integer getDoctorIdFk() {
        return doctorIdFk;
    }

    public void setDoctorIdFk(Integer doctorIdFk) {
        this.doctorIdFk = doctorIdFk;
    }

    public Integer getPatientIdFk() {
        return patientIdFk;
    }

    public void setPatientIdFk(Integer patientIdFk) {
        this.patientIdFk = patientIdFk;
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


    @Override
    public String toString() {
        return "  Rapor tarihi = " + reportDate +
                " - Rapor saati = " + reportTime ;
    }
}
