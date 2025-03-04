package repository;

import entity.Admin;
import entity.Gender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AdminDb extends BaseDb {



    public void addAdminDb(Admin admin){

        Connection connection = super.getConnectDb().getConnection();
        String query = "INSERT INTO admin (name,surname,password,gender) VALUES (?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,admin.getName());
            preparedStatement.setString(2,admin.getSurname());
            preparedStatement.setString(3,admin.getPassword());
            preparedStatement.setString(4,admin.getGender().name());

            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    public ArrayList<Admin> loginAdminDb(String password){

        ArrayList<Admin> arrayList = new ArrayList<>();
        Admin admin = new Admin();

        Connection connection = super.getConnectDb().getConnection();
        String query = "SELECT * FROM admin WHERE password = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,password);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                admin.setId(rs.getInt("admin_id"));
                admin.setName(rs.getString("name"));
                admin.setSurname(rs.getString("surname"));
                admin.setPassword(rs.getString("password"));
                admin.setGender(Gender.valueOf(rs.getString("gender").trim()));

                arrayList.add(admin);
            }

            rs.close();
            preparedStatement.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return arrayList;
    }



}
