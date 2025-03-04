package entity;

public abstract class Person {

    private Integer id;
    private String name;
    private String surname;
    private String password;
    private Gender gender;
    //private Gender gender ;

    public Person () {

    }

    public Person(Integer id, String name, String surname, String password, Gender gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.gender = gender;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }



}
