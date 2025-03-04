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

        String query = "INSERT INTO reports (report_date,report_time,report_content,doctor_id_fk,patient_id_fk) VALUES (?,?,?,?,?)";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1,report.getReportDate());
            preparedStatement.setTime(2,report.getReportTime());
            preparedStatement.setString(3,report.getReportContent());
            preparedStatement.setInt(4,report.getDoctorIdFk());
            preparedStatement.setInt(5,report.getPatientIdFk());
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
        String query = "SELECT * FROM reports WHERE patient_id_fk= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPatientFk);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Report report = new Report();

                report.setReportId(rs.getInt("report_id"));
                report.setReportTime(rs.getTime("report_time"));
                report.setReportDate(rs.getDate("report_date"));
                report.setReportContent(rs.getString("report_content"));
                report.setDoctorIdFk(rs.getInt("doctor_id_fk"));
                report.setPatientIdFk(rs.getInt("patient_id_fk"));

                list.add(report);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

            return list;
    }

    public ArrayList<Report> showReportFromDoctor(int idDoctorFk){

        ArrayList<Report> list = new ArrayList<>();
        Connection connection = super.getConnectDb().getConnection();
        String query = "SELECT * FROM reports WHERE doctor_id_fk= ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idDoctorFk);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                Report report = new Report();

                report.setReportId(rs.getInt("report_id"));
                report.setReportTime(rs.getTime("report_time"));
                report.setReportDate(rs.getDate("report_date"));
                report.setReportContent(rs.getString("report_content"));
                report.setDoctorIdFk(rs.getInt("doctor_id_fk"));
                report.setPatientIdFk(rs.getInt("patient_id_fk"));

                list.add(report);
            }



            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public void deleteReportDb(int idReport){

        Connection connection = super.getConnectDb().getConnection();

        String query = "DELETE FROM reports WHERE report_id = ?";
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
        String query = "UPDATE reports SET report_time = ?, report_date = ?, report_content = ? , doctor_id_fk = ? , patient_id_fk  WHERE id_report = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setTime(1, report.getReportTime());
            preparedStatement.setDate(2, report.getReportDate());
            preparedStatement.setString(3, report.getReportContent());
            preparedStatement.setInt(4, report.getDoctorIdFk()); // Bu id_report'ı WHERE koşuluna ekliyoruz
            preparedStatement.setInt(5,report.getPatientIdFk());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
