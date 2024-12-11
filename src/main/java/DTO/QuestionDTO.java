package DTO;

import com.google.gson.annotations.Expose;
import entity.Question;

import java.util.List;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 03/04/2024 3:56 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class QuestionDTO {
    @Expose
    private int totalQuestions;
    @Expose
    private List<Question> questions;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}