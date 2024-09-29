package service;

import entity.Admin;
import repository.AdminDb;
import repository.ConnectDb;

import java.util.ArrayList;

public class AdminService {

    AdminDb adminDb = new AdminDb();

    public void setAdmin(Admin admin){
        adminDb.addAdminDb(admin);
    }

    public ArrayList<Admin> loginAdmin(String password){
        return adminDb.loginAdminDb(password);
    }
}
