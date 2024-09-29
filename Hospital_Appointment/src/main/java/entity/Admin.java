package entity;

public class Admin extends Person{

    private String gender;
    public Admin() {
    }


    public Admin(int id, String name, String surname, String password,String gender) {
        super(id, name, surname, password);
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
