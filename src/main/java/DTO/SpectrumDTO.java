package DTO;
import com.google.gson.annotations.Expose;
import java.util.List;
public class SpectrumDTO {
    @Expose
    private List<Double> point;
    @Expose
    private List<String> Subject;

    public List<Double> getpoint() {
        return point;
    }

    public void setPoint(List<Double> point) {
        this.point = point;
    }

    public List<String> getSubject() {
        return Subject;
    }

    public void setSubject(List<String> subject) {
        Subject = subject;
    }
}
