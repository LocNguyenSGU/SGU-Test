package DTO;

import com.google.gson.annotations.Expose;
import entity.DataToDrawBarChart;
import entity.Question;
import entity.Test;

import java.util.List;

public class TestDTO {
    private List<Question> questionList;
    @Expose
    private List<DataToDrawBarChart> dataToDrawBarCharts;
    @Expose
    private int totalPages;
    @Expose
    private List<Test> tests;
    @Expose
    private Test test;
    @Expose
    private int numberTest; // so luong cua bai kiem tra

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<DataToDrawBarChart> getDataToDrawBarCharts() {
        return dataToDrawBarCharts;
    }

    public void setDataToDrawBarCharts(List<DataToDrawBarChart> dataToDrawBarCharts) {
        this.dataToDrawBarCharts = dataToDrawBarCharts;
    }

    public int getNumberTest() {
        return numberTest;
    }

    public void setNumberTest(int numberTest) {
        this.numberTest = numberTest;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
