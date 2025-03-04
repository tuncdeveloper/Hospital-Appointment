package entity;

import java.util.ArrayList;

public class Doctor extends Person{

    private SpecialtyEnum specialty;
    private HospitalEnum hospitalName;



    public Doctor() {
    }

    public Doctor(SpecialtyEnum specialty, HospitalEnum hospitalName) {
        this.specialty = specialty;
        this.hospitalName = hospitalName;
    }

    public Doctor(Integer id, String name, String surname, String password, Gender gender, SpecialtyEnum specialty, HospitalEnum hospitalName) {
        super(id, name, surname, password, gender);
        this.specialty = specialty;
        this.hospitalName = hospitalName;
    }

    public SpecialtyEnum getSpecialty() {
        return specialty;
    }

    public void setSpecialty(SpecialtyEnum specialty) {
        this.specialty = specialty;
    }

    public HospitalEnum getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(HospitalEnum hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Override
    public String toString() {
        return getName() + " " + getSurname() + " - " + getSpecialty();
    }

}
