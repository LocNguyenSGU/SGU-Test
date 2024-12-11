package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.sql.Date;
import java.util.Map;

public class Student {
    @Expose
    private int StudentID;
    @Expose
    private String FirstName;
    @Expose
    private String LastName;
    @Expose
    private String Phone;
    @Expose
    private String Email;
    @Expose
    private String Password;
    @Expose
    private Date DateOfBirth;
    @Expose
    private Boolean Gender;
    @Expose
    private short MajorID;
    @Expose
    private String MajorName;


    public void validate(){
        validateField("Giới tính", Gender);
        validateField("Mã ngành", MajorID);
        validateField("Ngày sinh", DateOfBirth);
        validateField("Họ", FirstName);
        validateField("Tên", LastName);
        validateField("Sô điện thoai", Phone);
        validateField("Email", Email);
        validateField("Password", Password);
    }

    private void validateField(String fieldName, Object fieldValue){
        if(fieldValue == null || (fieldValue instanceof String && ((String) fieldValue).isBlank())){
            throw new ValidateException(Map.of(fieldName, fieldName + " không được để trống"));
        }
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public Boolean getGender() {
        return Gender;
    }

    public void setGender(Boolean gender) {
        Gender = gender;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public short getMajorID() {
        return MajorID;
    }

    public void setMajorID(short majorID) {
        MajorID = majorID;
    }
}