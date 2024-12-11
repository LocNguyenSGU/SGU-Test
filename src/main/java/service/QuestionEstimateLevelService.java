package service;

import DTO.Estimate;
import entity.Exam;
import payload.response.Response;
import repository.ExamRepository;
import repository.QuestionEstimateLevelRepository;
import repository.QuestionRepository;

import java.util.List;

public class QuestionEstimateLevelService {
    private QuestionEstimateLevelRepository questionEstimateLevelRepository = new QuestionEstimateLevelRepository();
    private QuestionRepository questionRepository = new QuestionRepository();
    private ExamRepository examRepository = new ExamRepository();
    public Response estimate(){
        Response response = new Response();
        try {
        Exam exam = examRepository.findExamNearestCompleted();
        List<Estimate> estimates = questionEstimateLevelRepository.getAnalystLevelQuestions(exam.getExamID());
        for(Estimate estimate : estimates){
            questionRepository.updateLevelQuestion(estimate.getQuestionID(),estimate.getPercentage());
        }
        response.setData("Cập nhật độ khó thành công ");
        response.setStatusCode(200);
        response.setMessage("Thành công");
        }catch(Exception e){
           response.setData(e.getMessage());
           response.setStatusCode(400);
           response.setMessage("Thất bại");
        }
        return response;
    }
}
