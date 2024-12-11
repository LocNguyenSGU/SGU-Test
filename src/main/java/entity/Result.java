package entity;

import com.google.gson.annotations.Expose;
import entity.Enum.ExaminationStatus;

import entity.Enum.ResultStatus;
import exception.ValidateException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Result {
    @Expose
    private int ResultID;
    @Expose
    private byte TotalCorrect = 0;
    @Expose
    private byte TotalIncorrect = 0;
    @Expose
    private double TotalPoint = 0;
    @Expose
    private ResultStatus Status;
    @Expose
    private LocalDateTime DateStartParticipate;
    @Expose
    private ExaminationStatus TestStatus;
    @Expose
    private LocalDateTime DateEndParticipate;
    @Expose
    private List<ResultDetails> resultDetails;
    @Expose
    private int StudentID;
    @Expose
    private int TestID;
    @Expose
    private String Duration;
    @Expose
    private String SubjectName;
    @Expose
    private LocalDateTime DateStart;
    @Expose
    private LocalDateTime DateEnd;
    @Expose
    private int NumberOfQuestion;
    @Expose
    private String Description;
    @Expose
    private double TestPoint;
    @Expose
    private int SubjectID;

    public int getSubjectID() {
        return SubjectID;
    }

    public void setSubjectID(int subjectID) {
        SubjectID = subjectID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getNumberOfQuestion() {
        return NumberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        NumberOfQuestion = numberOfQuestion;
    }

    public void validate() {
        validateStatus();
        validateStudentID();
        validateTestID();
    }

    private void validateStatus() {
        if (this.Status == null) {
            throw new ValidateException(Map.of("Trạng thái", "Trạng thái không được để trống"));
        }
    }

    private void validateStudentID() {
        if (this.StudentID == 0) {
            throw new ValidateException(Map.of("Mã học sinh", "Mã học sinh không được để trống"));
        }
    }

    private void validateTestID() {
        if (this.StudentID == 0) {
            throw new ValidateException(Map.of("Mã bài thi", "Mã bài thi không được để trống"));
        }
    }

    public int getResultID() {
        return ResultID;
    }

    public void setResultID(int resultID) {
        ResultID = resultID;
    }

    public byte getTotalCorrect() {
        return TotalCorrect;
    }

    public void setTotalCorrect(byte totalCorrect) {
        TotalCorrect = totalCorrect;
    }

    public byte getTotalIncorrect() {
        return TotalIncorrect;
    }

    public void setTotalIncorrect(byte totalIncorrect) {
        TotalIncorrect = totalIncorrect;
    }

    public double getTotalPoint() {
        return TotalPoint;
    }

    public void setTotalPoint(double totalPoint) {
        TotalPoint = totalPoint;
    }

    public ResultStatus getStatus() {
        return Status;
    }

    public void setStatus(ResultStatus status) {
        Status = status;
    }

    public LocalDateTime getDateStartParticipate() {
        return DateStartParticipate;
    }

    public void setDateStartParticipate(LocalDateTime dateStartParticipate) {
        DateStartParticipate = dateStartParticipate;
    }

    public List<ResultDetails> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetails> resultDetails) {
        this.resultDetails = resultDetails;
    }

    public LocalDateTime getDateEndParticipate() {
        return DateEndParticipate;
    }

    public void setDateEndParticipate(LocalDateTime dateEndParticipate) {
        DateEndParticipate = dateEndParticipate;
    }

    public int getStudentID() {
        return StudentID;
    }

    public void setStudentID(int studentID) {
        StudentID = studentID;
    }

    public int getTestID() {
        return TestID;
    }

    public void setTestID(int testID) {
        TestID = testID;
    }

    public String getSubjectName() {
        return SubjectName;
    }

    public void setSubjectName(String subjectName) {
        SubjectName = subjectName;
    }

    public LocalDateTime getDateStart() {
        return DateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        DateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        DateEnd = dateEnd;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public ExaminationStatus getTestStatus() {
        return TestStatus;
    }

    public void setTestStatus(ExaminationStatus testStatus) {
        TestStatus = testStatus;
    }

    public double getTestPoint() {
        return TestPoint;
    }

    public void setTestPoint(double testPoint) {
        TestPoint = testPoint;
    }
}
