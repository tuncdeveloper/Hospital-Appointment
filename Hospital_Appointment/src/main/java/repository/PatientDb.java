package repository;

import entity.Patient;

import java.sql.*;
import java.util.ArrayList;

public class PatientDb extends BaseDb {

    public void newPatientDb(Patient patient){

        Connection connection = super.getConnectDb().getConnection();

        String query = "INSERT INTO patients (name,surname,birth_date,gender,phone_number,address,password,tc) VALUES (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,patient.getName());
            preparedStatement.setString(2,patient.getSurname());
            preparedStatement.setDate(3, Date.valueOf(patient.getBirthDate()));
            preparedStatement.setString(4, patient.getGender());
            preparedStatement.setString(5,patient.getPhoneNumber());
            preparedStatement.setString(6, patient.getAddress());
            preparedStatement.setString(7,patient.getPassword());
            preparedStatement.setString(8,patient.getTc());

            int control = preparedStatement.executeUpdate();
        
            if (control > 0) {
                    System.out.println("Başarılı bir şekiilde eklendş Tc ' si = "+patient.getTc()+" ismi = "+patient.getName());

                System.out.println("hasta başarılı bir şekilde eklendi");
            }else {
                System.out.println("Sorun ılluştu");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        public Patient FindPatientDb(String tc){

            Connection connection = super.getConnectDb().getConnection();

            Patient patient = new Patient();

            String query = "SELECT * FROM patients WHERE tc = ?";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,tc);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPassword(rs.getString("password"));
                    patient.setTc(rs.getString("tc"));


                }

                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return patient;
        }


        public ArrayList<String> showListPatientTC(){

            ArrayList<String> list = new ArrayList<>();


            Connection connection = super.getConnectDb().getConnection();
            String query = "SELECT tc FROM patients";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){

                    Patient patient = new Patient();
                    patient.setTc(rs.getString("tc"));
                    list.add(patient.getTc());

                }
                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }


        public Patient selectedWithIdPatientDb(int idPatient){

            Connection connection = super.getConnectDb().getConnection();
            String query ="SELECT * FROM patients WHERE id_patient = ?";
            Patient patient = new Patient();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,idPatient);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPassword(rs.getString("password"));
                    patient.setTc(rs.getString("tc"));
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return patient;
        }

        public void updatePatientDb(Patient patient){

        Connection connection = super.getConnectDb().getConnection();

        String query = "UPDATE patients SET name = ? , surname = ? , phone_number = ?,address = ? , password = ?  WHERE id_patient = ? ";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,patient.getName());
                preparedStatement.setString(2,patient.getSurname());
                preparedStatement.setString(3,patient.getPhoneNumber());
                preparedStatement.setString(4,patient.getAddress());
                preparedStatement.setString(5,patient.getPassword());
                preparedStatement.setInt(6,patient.getId());

                preparedStatement.executeUpdate();


                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        public ArrayList<Patient> showPatinentFromAppointmentFromReportDb(int idPatientFk){

            ArrayList<Patient> list = new ArrayList<>();
            Patient patient = new Patient();
            Connection connection = super.getConnectDb().getConnection();
            String query = "SELECT * FROM patients WHERE id_patient = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,idPatientFk);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPassword(rs.getString("password"));
                    patient.setTc(rs.getString("tc"));

                    list.add(patient);

                }

                rs.close();
                preparedStatement.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }

        public ArrayList<Patient> showPatientListDb(){

            ArrayList<Patient> list = new ArrayList<>();

            Connection connection = super.getConnectDb().getConnection();
            String query = "SELECT * FROM patients";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Patient patient = new Patient();

                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPassword(rs.getString("password"));
                    patient.setTc(rs.getString("tc"));

                    list.add(patient);
                }

                rs.close();
                preparedStatement.close();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }


    public void deletePatientDb(Patient patient) {
        Connection connection = super.getConnectDb().getConnection();

        // Önce randevuları sil
        String deleteAppointmentsQuery = "DELETE FROM appointments WHERE id_patient_fk = ?";
        try {
            PreparedStatement appointmentStatement = connection.prepareStatement(deleteAppointmentsQuery);
            appointmentStatement.setInt(1, patient.getId());
            appointmentStatement.executeUpdate();
            appointmentStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Randevu silme işlemi sırasında hata: " + e.getMessage());
        }

        // Sonra raporları sil
        String deleteReportsQuery = "DELETE FROM reports WHERE id_patient_fk = ?";
        try {
            PreparedStatement reportStatement = connection.prepareStatement(deleteReportsQuery);
            reportStatement.setInt(1, patient.getId());
            reportStatement.executeUpdate();
            reportStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException("Rapor silme işlemi sırasında hata: " + e.getMessage());
        }

        // Son olarak hastayı sil
        String query = "DELETE FROM patients WHERE id_patient = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, patient.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


        public ArrayList<Patient> showListNameSurname(String tc,String name , String surname){

            ArrayList<Patient> list = new ArrayList<>();

            Connection connection = super.getConnectDb().getConnection();

            String query ="SELECT * FROM patients WHERE tc = ? AND name = ? AND surname = ?";



            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,tc);
                preparedStatement.setString(2,name);
                preparedStatement.setString(3,surname);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("id_patient"));
                    patient.setName(rs.getString("name"));
                    patient.setSurname(rs.getString("surname"));
                    patient.setBirthDate(rs.getDate("birth_date").toLocalDate());
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setAddress(rs.getString("address"));
                    patient.setPassword(rs.getString("password"));
                    patient.setTc(rs.getString("tc"));
                    list.add(patient);

                }


                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }




}
