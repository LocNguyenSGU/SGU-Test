package entity;

import exception.ValidateException;

import java.sql.Date;
import java.util.Map;
import com.google.gson.annotations.Expose;
public class Employee {
    @Expose
    private int EmployeeID;
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
    @Expose
    private short RoleID;
    @Expose
    private String RoleName;

    public void validate(){
        validateFirstName();
        validateLastName();
        validateDateOfBirth();
        validateMajorID();
        validateRoleID();
        validateGender();
        validateEmail();
        validatePhone();
        validatePassword();
    }
    private void validateGender(){
        if(this.Gender == null){
            throw new ValidateException(Map.of("Giới tính","Giới tính không được để trống"));
        }
    }
    private void validateRoleID(){
        if(this.RoleID == 0){
            throw new ValidateException(Map.of("Mã Quyền","Mã quyền không được để trống"));
        }
    }
    private void validateMajorID(){
        if(this.MajorID == 0){
            throw new ValidateException(Map.of("Mã ngành","Mã ngành không được để trống"));
        }
    }
    private void validateDateOfBirth(){
        if(this.DateOfBirth == null){
            throw new ValidateException(Map.of("Ngày sinh","Ngày sinh không được để trống"));
        }
    }
    private void validateFirstName(){
        if(this.FirstName == null || this.FirstName.isBlank()){
            throw new ValidateException(Map.of("Họ", "Họ không được để trống"));
        }
    }
    private void validateLastName(){
        if(this.LastName == null || this.LastName.isBlank()){
            throw new ValidateException(Map.of("Tên", "Tên không được để trống"));
        }
    }
    private void validatePhone(){
        if(this.Phone == null || this.Phone.isBlank()){
            throw new ValidateException(Map.of("Sô điện thoai", "Sô điện thoại không được để trống"));
        }
    }
    private void validateEmail(){
        if(this.Email == null || this.Email.isBlank()){
            throw new ValidateException(Map.of("Email", "Email không được để trống"));
        }
    }
    private void validatePassword(){
        if(this.Password == null || this.Password.isBlank()){
            throw new ValidateException(Map.of("Password", "Password không được để trống"));
        }
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int employeeID) {
        EmployeeID = employeeID;
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

    public short getRoleID() {
        return RoleID;
    }

    public void setRoleID(short roleID) {
        RoleID = roleID;
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

    public String RoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String MajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }
}
