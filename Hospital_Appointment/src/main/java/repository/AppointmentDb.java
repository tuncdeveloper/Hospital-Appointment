package repository;

import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Report;

import java.sql.*;
import java.util.ArrayList;
import java.util.SplittableRandom;

public class AppointmentDb extends BaseDb{

    public void newAppointmentDb(Appointment appointment,int idDoctorFk,int idPatientFk){

        Connection connection = super.getConnectDb().getConnection();

        String query = "INSERT INTO appointments (appointment_date , appointment_time , id_doctor_fk , id_patient_fk) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1,appointment.getAppointmentDate());
            preparedStatement.setTime(2,appointment.getAppointmentTime());
            preparedStatement.setInt(3,idDoctorFk);
            preparedStatement.setInt(4,idPatientFk);
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public ArrayList<Appointment> showAppointmentFromPatient(int idPatientFk){

        ArrayList<Appointment> list = new ArrayList<>();

        Connection connection = super.getConnectDb().getConnection();
        Patient patient = new Patient();
        String query = "SELECT * FROM appointments WHERE id_patient_fk = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idPatientFk);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Appointment appointment = new Appointment();

                appointment.setAppointment_id(rs.getInt("id_appointment"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setIdDoctorFk(rs.getInt("id_doctor_fk"));


                list.add(appointment);
                patient.setAppointments(list);

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

        String query = "DELETE FROM appointments WHERE id_appointment= ?";
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
        String query = "SELECT * FROM appointments WHERE id_doctor_fk = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,idDoctorFk);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()){
                Appointment appointment = new Appointment();

                appointment.setAppointment_id(rs.getInt("id_appointment"));
                appointment.setAppointmentTime(rs.getTime("appointment_time"));
                appointment.setAppointmentDate(rs.getDate("appointment_date"));
                appointment.setIdPatientFk(rs.getInt("id_patient_fk"));


                list.add(appointment);
                doctor.setAppointments(list);

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
            String sql = "SELECT * FROM appointments WHERE appointment_date = ? AND appointment_time = ? AND id_doctor_fk = ?";
            PreparedStatement statement = super.getConnectDb().getConnection().prepareStatement(sql);
            statement.setDate(1, date);
            statement.setTime(2, time);
            statement.setInt(3, idDoctorFk);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointment_id(resultSet.getInt("id_appointment"));
                appointment.setAppointmentDate(resultSet.getDate("appointment_date"));
                appointment.setAppointmentTime(resultSet.getTime("appointment_time"));
                appointment.setIdDoctorFk(resultSet.getInt("id_doctor_fk"));
                appointment.setIdPatientFk(resultSet.getInt("id_patient_fk"));

                appointments.add(appointment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }



    public void updateAppointmentDb(Appointment appointment) {
        Connection connection = super.getConnectDb().getConnection();
        String query = "UPDATE appointments SET appointment_date = ?, appointment_time = ? WHERE id_appointment = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, appointment.getAppointmentDate());
            preparedStatement.setTime(2, appointment.getAppointmentTime());
            preparedStatement.setInt(3, appointment.getAppointment_id());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException("Randevu güncellenemedi: " + e.getMessage(), e);
        }
    }




}
