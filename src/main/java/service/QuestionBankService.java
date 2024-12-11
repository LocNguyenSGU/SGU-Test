package service;

import config.MysqlConfig;
import entity.Major;
import entity.Question;
import exception.RetrieveException;
import payload.response.Response;
import repository.MajorRepository;
import repository.QuestionBankRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

import javax.print.DocFlavor.READER;

public class QuestionBankService {
    private QuestionBankRepository questionBankRepository;
    public Response getAllQuestion() {
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            response.setData(questionBankRepository.getAllQuestionBank());
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
    public Response getQuestionById(int id){
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            response.setData(questionBankRepository.getQuestionById(id));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createQuestion(String title, double point, int employeeId, short subjectId, int correctAnswer){
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            Question question = new Question();
            question.setTitle(title);
            question.setPoint(point);
            question.setEmployeeID(employeeId);
            question.setSubjectID(subjectId);
            question.setCorrectAnswer(correctAnswer);

            response.setData(question);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response deleteQuestion(int id){
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            questionBankRepository.deleteQuestion(id);
            response.setData("Cập nhật thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getDataQuestionPieChart() {
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            response.setData(questionBankRepository.getDataQuestionPieChart());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response updateQuestion(Question question) {
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            questionBankRepository.updateQuestion(question);
            response.setData("Cap nhat thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getIDQuestionMax() {
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            response.setData(questionBankRepository.getIDQuestionMax());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response updateCorrectAnswer(int correctAnswer, int questionID) {
        Response response = new Response();
        questionBankRepository = new QuestionBankRepository();
        try {
            questionBankRepository.updateCorrectAnswer(correctAnswer, questionID);
            response.setData("Cap nhat thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
}