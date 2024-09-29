package repository;

import entity.Doctor;
import entity.Patient;
import entity.Report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ReportDb extends BaseDb {


    public void newReport(Report report){

        Connection connection = super.getConnectDb().getConnection();

        String query = "INSERT INTO reports (report_date,report_time,report_content,id_doctor_fk,id_patient_fk) VALUES (?,?,?,?,?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1,report.getReportDate());
            preparedStatement.setTime(2,report.getReportTime());
            preparedStatement.setString(3,report.getReportContent());
            preparedStatement.setInt(4,report.getIdDoctorFk());
            preparedStatement.setInt(5,report.getIdPatientFk());
            preparedStatement.executeUpdate();


            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Report> showReportFromPatient(int idPatientFk){

        ArrayList<Report> list = new ArrayList<>();
        Patient patient = new Patient();
        Connection connection = super.getConnectDb().getConnection();
        String query = "SELECT * FROM reports WHERE id_patient_fk= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPatientFk);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Report report = new Report();

                report.setIdReport(rs.getInt("id_report"));
                report.setReportTime(rs.getTime("report_time"));
                report.setReportDate(rs.getDate("report_date"));
                report.setReportContent(rs.getString("report_content"));
                report.setIdDoctorFk(rs.getInt("id_doctor_fk"));

                list.add(report);
                patient.setReports(list);
            }



            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            return list;
    }

    public ArrayList<Report> showReportFromDoctor(int idDoctorFk){

        ArrayList<Report> list = new ArrayList<>();
        Doctor doctor = new Doctor();
        Connection connection = super.getConnectDb().getConnection();
        String query = "SELECT * FROM reports WHERE id_doctor_fk= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idDoctorFk);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Report report = new Report();

                report.setIdReport(rs.getInt("id_report"));
                report.setReportTime(rs.getTime("report_time"));
                report.setReportDate(rs.getDate("report_date"));
                report.setReportContent(rs.getString("report_content"));
                report.setIdPatientFk(rs.getInt("id_patient_fk"));

                list.add(report);
                doctor.setReports(list);
            }



            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public void deleteReportDb(int idReport){

        Connection connection = super.getConnectDb().getConnection();

        String query = "DELETE FROM reports WHERE id_report = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idReport);
            preparedStatement.executeUpdate();


            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateReportDb(Report report){

        Connection connection = super.getConnectDb().getConnection();
        // Sorguyu güncelledik: SET kısmını ve WHERE şartlarını düzenledik.
        String query = "UPDATE reports SET report_time = ?, report_date = ?, report_content = ? WHERE id_report = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // Burada gerekli tüm parametreleri ekliyoruz
            preparedStatement.setTime(1, report.getReportTime());
            preparedStatement.setDate(2, report.getReportDate());
            preparedStatement.setString(3, report.getReportContent());
            preparedStatement.setInt(4, report.getIdReport()); // Bu id_report'ı WHERE koşuluna ekliyoruz

            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
