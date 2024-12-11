package DTO;

import com.google.gson.annotations.Expose;
import entity.ResultDetails;

import java.util.List;

public class ResultDetailsDTO {
    @Expose
    private List<ResultDetails> resultDetails;

    public List<ResultDetails> getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(List<ResultDetails> resultDetails) {
        this.resultDetails = resultDetails;
    }
}
