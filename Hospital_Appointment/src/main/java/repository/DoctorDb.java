    package repository;

    import entity.Doctor;
    import entity.Gender;
    import entity.HospitalEnum;
    import entity.SpecialtyEnum;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;

    public class DoctorDb extends BaseDb{


        public void newDoctorDb(Doctor doctor) {
            Connection connection = super.getConnectDb().getConnection(); // Bağlantıyı alıyoruz

            // Bağlantı kontrolü
            if (connection == null) {
                throw new RuntimeException("Veritabanı bağlantısı sağlanamadı.");
            }

            String query = "INSERT INTO doctors (name, surname, password, gender, specialty, hospital_name) VALUES (?,?,?,?,?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, doctor.getName());
                preparedStatement.setString(2, doctor.getSurname());
                preparedStatement.setString(3, doctor.getPassword());
                preparedStatement.setString(4, doctor.getGender().name().trim());  // Enum value'ını string'e dönüştürme
                preparedStatement.setString(5, String.valueOf(doctor.getSpecialty()).trim());
                preparedStatement.setString(6, String.valueOf(doctor.getHospitalName()).trim());

                preparedStatement.executeUpdate();

                System.out.println("Doktor başarıyla eklendi.");
                preparedStatement.close();
            } catch (SQLException e) {
                // Detaylı hata mesajı ver
                throw new RuntimeException("Doktor ekleme sırasında bir hata oluştu: " + e.getMessage(), e);
            }
        }



        public void deleteDoctorDb(Doctor doctor) {
            Connection connection = super.getConnectDb().getConnection();

            try {
                // Raporları sil
                String queryReport = "DELETE FROM reports WHERE doctor_id_fk = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryReport)) {
                    preparedStatement.setInt(1, doctor.getId());
                    preparedStatement.executeUpdate();
                }

                // Randevuları sil
                String queryAppointment = "DELETE FROM Appointments WHERE doctor_id_fk = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(queryAppointment)) {
                    preparedStatement.setInt(1, doctor.getId());
                    preparedStatement.executeUpdate();
                }

                // Doktoru sil
                String query = "DELETE FROM doctors WHERE doctor_id = ?";
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
                    doctor.setId(rs.getInt("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setGender(Gender.valueOf(rs.getString("gender").trim()));
                    doctor.setPassword(rs.getString("password"));
                    doctor.setSpecialty(SpecialtyEnum.valueOf(rs.getString("specialty").trim()));
                    doctor.setHospitalName(HospitalEnum.valueOf(rs.getString("hospital_name").trim()));

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

            String query = "SELECT * FROM doctors";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Doctor doctor = new Doctor();
                    doctor.setId(rs.getInt("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setGender(Gender.valueOf(rs.getString("gender").trim()));
                    doctor.setSpecialty(SpecialtyEnum.valueOf(rs.getString("specialty").trim()));
                    doctor.setHospitalName(HospitalEnum.valueOf(rs.getString("hospital_name").trim()));
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
            String query = "SELECT * FROM doctors WHERE doctor_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, idDoctor);  // Burada id'yi sorguya ekliyoruz
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()) {
                    doctor.setId(rs.getInt("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setGender(Gender.valueOf(rs.getString("gender").trim()));
                    doctor.setPassword(rs.getString("password"));
                    doctor.setSpecialty(SpecialtyEnum.valueOf(rs.getString("specialty").trim()));
                    doctor.setHospitalName(HospitalEnum.valueOf(rs.getString("hospital_name").trim()));
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
            String query = "SELECT * FROM doctors WHERE doctor_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1,idDoctorFk);
                ResultSet rs = preparedStatement.executeQuery();

                while (rs.next()){
                    Doctor doctor = new Doctor();

                    doctor.setId(rs.getInt("doctor_id"));
                    doctor.setName(rs.getString("name"));
                    doctor.setSurname(rs.getString("surname"));
                    doctor.setGender(Gender.valueOf(rs.getString("gender").trim()));
                    doctor.setHospitalName(HospitalEnum.valueOf(rs.getString("hospital_name").trim()));
                    doctor.setSpecialty(SpecialtyEnum.valueOf(rs.getString("specialty").trim()));
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
