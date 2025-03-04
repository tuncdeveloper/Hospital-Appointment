package repository;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentDb extends BaseDb{

    public void newAppointmentDb(Appointment appointment){

        Connection connection = super.getConnectDb().getConnection();

        String query = "INSERT INTO appointments (appointment_date , appointment_time , doctor_id_fk , patient_id_fk) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1,appointment.getAppointmentDate());
            preparedStatement.setTime(2,appointment.getAppointmentTime());
            preparedStatement.setInt(3,appointment.getDoctorIdFk());
            preparedStatement.setInt(4,appointment.getPatientIdFk());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public ArrayList<Appointment> showAppointmentFromPatient(int idPatientFk){

        ArrayList<Appointment> list = new ArrayList<>();

        Connection connection = super.getConnectDb().getConnection();
        String query = "SELECT * FROM appointments WHERE patient_id_fk = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPatientFk);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setDoctorIdFk(rs.getInt("doctor_id_fk"));
                appointment.setPatientIdFk(rs.getInt("patient_id_fk"));


                list.add(appointment);

            }

                preparedStatement.close();
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    public void deleteAppointmentDb(int idAppointment){

        Connection connection = super.getConnectDb().getConnection();

        String query = "DELETE FROM appointments WHERE appointment_id= ?";
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idAppointment);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Appointment> showAppointmentFromDoctor(int idDoctorFk){

        ArrayList<Appointment> list = new ArrayList<>();

        Connection connection = super.getConnectDb().getConnection();
        Doctor doctor = new Doctor();
        String query = "SELECT * FROM appointments WHERE doctor_id_fk = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idDoctorFk);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Appointment appointment = new Appointment();

                appointment.setAppointmentId(rs.getInt("appointment_id"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setDoctorIdFk(rs.getInt("doctor_id_fk"));
                appointment.setPatientIdFk(rs.getInt("patient_id_fk"));


                list.add(appointment);

            }

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }



    public ArrayList<Appointment> showAppointmentControl(Date date, Time time, int idDoctorFk) {
        ArrayList<Appointment> appointments = new ArrayList<>();

        try {
            String sql = "SELECT * FROM appointments WHERE appointment_date = ? AND appointment_time = ? AND doctor_id_fk = ?";
            PreparedStatement statement = super.getConnectDb().getConnection().prepareStatement(sql);
            statement.setDate(1, date);
            statement.setTime(2, time);
            statement.setInt(3, idDoctorFk);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(resultSet.getInt("appointment_id"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                appointment.setDoctorIdFk(resultSet.getInt("doctor_id_fk"));
                appointment.setPatientIdFk(resultSet.getInt("patient_id_fk"));

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }



    public void updateAppointmentDb(Appointment appointment) {
        Connection connection = super.getConnectDb().getConnection();
        String query = "UPDATE appointments SET appointment_date = ?, appointment_time = ? WHERE appointment_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, appointment.getAppointmentDate());
            preparedStatement.setTime(2, appointment.getAppointmentTime());
            preparedStatement.setInt(3, appointment.getAppointmentId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException("Randevu güncellenemedi: " + e.getMessage(), e);
        }
    }




}
