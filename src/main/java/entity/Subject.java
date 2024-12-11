package entity;

import com.google.gson.annotations.Expose;
import exception.ValidateException;

import java.util.Map;

public class Subject {
    @Expose
    private short SubjectID;
    @Expose
    private String Name;
    @Expose
    private String Description;
    @Expose
    private short MajorID;
    @Expose
    private String MajorName;
//    public void validate(){
//        validateName();
//        validateMajorID();
//    }
//    private void validateName(){
//        if(this.Name == null || this.Name.isBlank()){
//            throw new ValidateException(Map.of("Tên", "Tên môn học không được để trống"));
//        }
//    }
    private void validateMajorID(){
        if(this.MajorID == 0){
            throw new ValidateException(Map.of("Mã ngành","Mã ngành không được để trống"));
        }
    }

    public String getMajorName() {
        return MajorName;
    }

    public void setMajorName(String majorName) {
        MajorName = majorName;
    }

    public short getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(short subjectID) {
        SubjectID = subjectID;
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

    public short getMajorID() {
        return MajorID;
    }

    public void setMajorID(short majorID) {
        MajorID = majorID;
    }
    
    public String getSubjectName() {
        return Name;
    }
    
}
