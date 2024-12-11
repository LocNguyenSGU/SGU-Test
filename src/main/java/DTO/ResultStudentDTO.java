package DTO;
import entity.Question;
import entity.ResultStudent;
import java.util.List;
public class ResultStudentDTO {
    private List<ResultStudent> ResultList;
    private int TotalPages;

    public List<ResultStudent> ResultList() {
        return ResultList;
    }

    public void setResultList(List<ResultStudent> resultList) {
        ResultList = resultList;
    }

    public int TotalPages() {
        return TotalPages;
    }

    public void setTotalPages(int totalPages) {
        TotalPages = totalPages;
    }
}
