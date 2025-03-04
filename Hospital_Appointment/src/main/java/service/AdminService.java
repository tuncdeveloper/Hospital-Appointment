package service;

import entity.Admin;
import repository.AdminDb;

import java.util.ArrayList;

public class AdminService {

    private AdminDb adminDb;

    public AdminService(){
        this.adminDb=new AdminDb();
    }

    public void addAdmin(Admin admin){
        adminDb.addAdminDb(admin);
    }

    public ArrayList<Admin> loginAdmin(String password){
        return adminDb.loginAdminDb(password);
    }
}
