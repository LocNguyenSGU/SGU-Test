package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.Map;

public class Major {
    @Expose
    private short MajorID;
    @Expose
    private String Name;
    @Expose
    private String Description;
    @Expose
    private int isDeleted;

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

//    public void validate(){
//        validateName();
//    }
//    private void validateName(){
//        if(this.Name == null || this.Name.isBlank()){
//            throw new ValidateException(Map.of("Tên", "Tên chuyên ngành không được để trống"));
//        }
//    }
    public short getMajorID() {
        return MajorID;
    }

    public void setMajorID(short majorID) {
        MajorID = majorID;
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
