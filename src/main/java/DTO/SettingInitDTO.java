package DTO;

public class SettingInitDTO {
    private int TestID;
    private short MajorID;
    private int ExamID;
    private short SubjectID;

    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public short getMajorID() {
        return MajorID;
    }

    public void setMajorID(short majorID) {
        MajorID = majorID;
    }

    public int getExamID() {
        return ExamID;
    }

    public void setExamID(int examID) {
        ExamID = examID;
    }

    public short getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(short subjectID) {
        SubjectID = subjectID;
    }
}
