    package repository;

    import entity.Doctor;

    import javax.print.Doc;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;

    public class DoctorDb extends BaseDb{


        public void newDoctorDb(Doctor doctor) {
            Connection connection = super.getConnectDb().getConnection(); // Bağlantıyı alıyoruz
            String query = "INSERT INTO doctors (name, surname,specialty,hospital_name,password) VALUES (?,?,?,?,?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, doctor.getName());
                preparedStatement.setString(2, doctor.getSurname());
                preparedStatement.setString(3,doctor.getSpecialty());
                preparedStatement.setString(4,doctor.getHospitalName());
                preparedStatement.setString(5,doctor.getPassword());

                preparedStatement.executeUpdate();



                System.out.println("ekleme basarili");
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException("Doktor ekleme sırasında bir hata oluştu: " + e.getMessage());
            }
        }


        public void deleteDoctorDb(Doctor doctor) {
            Connection connection = super.getConnectDb().getConnection();

            try {
                // Raporları sil
                String queryReport = "DELETE FROM reports WHERE id_doctor_fk = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryReport)) {
                    preparedStatement.setInt(1, doctor.getId());
                    preparedStatement.executeUpdate();
                }

                // Randevuları sil
                String queryAppointment = "DELETE FROM Appointments WHERE id_doctor_fk = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryAppointment)) {
                    preparedStatement.setInt(1, doctor.getId());
                    preparedStatement.executeUpdate();
                }

                // Doktoru sil
                String query = "DELETE FROM doctors WHERE id_doctor = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setInt(1, doctor.getId());
                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Başarıyla silinmiştir");
                    } else {
                        System.out.println("Doktor gözükmüyor");
                    }
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


        public Doctor findDoctorDb(String password){

            Connection connection = super.getConnectDb().getConnection();

            String query = "SELECT * FROM doctors WHERE password = ?";
            Doctor doctor = new Doctor();

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1,password);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    doctor.setId(rs.getInt("id_doctor"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    doctor.setHospitalName(rs.getString("hospital_name"));
                    doctor.setPassword(rs.getString("password"));

                }

                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return doctor;

        }

        public ArrayList<Doctor> showDoctorList(){

            ArrayList<Doctor> list = new ArrayList<>();

            Connection connection = super.getConnectDb().getConnection();

            String query = "SELECT  id_doctor,name , surname , specialty,hospital_name FROM doctors";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Doctor doctor = new Doctor();
                    doctor.setId(rs.getInt("id_doctor"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    doctor.setHospitalName(rs.getString("hospital_name"));
                    list.add(doctor);
                }


                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }


        public Doctor selectedWithIdDoctorDb(int idDoctor) {
            Doctor doctor = new Doctor();

            Connection connection = super.getConnectDb().getConnection();
            String query = "SELECT * FROM doctors WHERE id_doctor = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idDoctor);  // Burada id'yi sorguya ekliyoruz
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    doctor.setId(rs.getInt("id_doctor"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    doctor.setHospitalName(rs.getString("hospital_name"));
                    doctor.setPassword(rs.getString("password"));
                }

                rs.close();
                preparedStatement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return doctor;
        }


        public ArrayList<Doctor> showDoctorFromAppointmentFromReportDb(int idDoctorFk){
            ArrayList<Doctor> list = new ArrayList<>();

            Connection connection = super.getConnectDb().getConnection();
            String query = "SELECT * FROM doctors WHERE id_doctor = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,idDoctorFk);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Doctor doctor = new Doctor();

                    doctor.setId(rs.getInt("id_doctor"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setHospitalName(rs.getString("hospital_name"));
                    doctor.setSpecialty(rs.getString("specialty"));
                    list.add(doctor);

                }


                rs.close();
                preparedStatement.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return list;
        }


    }
