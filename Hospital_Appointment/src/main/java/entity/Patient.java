package entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Patient extends Person{

    private LocalDate birthDate;
    private String phoneNumber;
    private String address;
    private String tc;

    public Patient() {
    }


    public Patient(LocalDate birthDate, String phoneNumber, String address, String tc) {
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.tc = tc;
    }

    public Patient(Integer id, String name, String surname, String password, Gender gender, LocalDate birthDate, String phoneNumber, String address, String tc) {
        super(id, name, surname, password, gender);
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.tc = tc;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
         return getName() + " " + getSurname();
    }
}
