package DTO;

import com.google.gson.annotations.Expose;
import entity.DataToDrawPieChart;
import entity.Question;


import java.util.List;

import javax.xml.crypto.Data;


public class QuestionBankDTO {

    private List<Question> questions;
    private List<DataToDrawPieChart> dataToDrawPieCharts;
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<DataToDrawPieChart> getDataToDrawPieCharts() {
        return dataToDrawPieCharts;
    }

    public void setDataToDrawPieCharts(List<DataToDrawPieChart> dataToDrawPieCharts) {
        this.dataToDrawPieCharts = dataToDrawPieCharts;
    }
    public int getNumberQuestion() {
        return questions.size();
    }
}
