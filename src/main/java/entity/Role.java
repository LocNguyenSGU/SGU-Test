package entity;

import exception.ValidateException;

import java.util.Map;

public class Role {
    private short RoleID;
    private String Name;
    private String Description;

    public void validate(){
        validateName();
    }
    private void validateName(){
        if(this.Name == null || this.Name.isBlank()){
            throw new ValidateException(Map.of("Tên", "Tên quyền không được để trống"));
        }
    }
    public short getRoleID() {
        return RoleID;
    }

    public void setRoleID(short roleID) {
        RoleID = roleID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

}
