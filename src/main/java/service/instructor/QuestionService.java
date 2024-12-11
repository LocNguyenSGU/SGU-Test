package service.instructor;


import entity.Question;
import payload.response.Response;
import repository.QuestionRepository;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 03/04/2024 3:49 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class QuestionService {

    public static Response createQuestion(String title, int subjectID, String level, double point) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.createQuestion(title, subjectID, level, point));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response deleteQuestion(String questionID) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.deleteQuestion(questionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response getQuestionsByTestID(String testID) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.getQuestionsByTestID(testID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response getQuestionsBySubjectId(int subjectId) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.getQuestionsBySubjectId(subjectId));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response createBackup(String questionID, double point, String title) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.createBackup(questionID, point, title));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response updateBackup(int backupQuestionID, int correctAnswer) {
        Response response = new Response();

        try {
            response.setData(QuestionRepository.updateBackup(backupQuestionID, correctAnswer));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public Response getAllQuestions(int page, int size, int majorID) {
        Response response = new Response();
        try {
            response.setData(QuestionRepository.getAllQuestions(page, size, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getFilteredQuestions(int page, int size, String subjectID, String level,
                                         String search, int majorID) {
        Response response = new Response();
        try {
            response.setData(QuestionRepository.getFilteredQuestions(page, size, subjectID, level,
                    search, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public Question getQuestionByID(String questionID) {
        return QuestionRepository.getQuestionByID(questionID);
    }


    public Response updateQuestion(String questionID, String title, String subjectID, String level,
                                   double point, int correctAnswer) {
        Response response = new Response();
        try {
            response.setData(QuestionRepository.updateQuestion(questionID, title, subjectID, level,
                    point, correctAnswer));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createQuestion(Question question){
        Response response = new Response();
        QuestionRepository questionRepository = new QuestionRepository();
        try {
            questionRepository.createQuestion(question);
            response.setData("Them cau hoi thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createQuestionExcel(String excelFilePath, int employeeId){
        Response response = new Response();
        QuestionRepository questionRepository = new QuestionRepository();
        System.out.println("chay toi response cua createQuestionExcel1 ");
        try {
            System.out.println("chay toi response cua createQuestionExcel2 ");
            System.out.println("id_E duoc them la: " + employeeId);
            questionRepository.createQuestionExcel(excelFilePath, employeeId);
            response.setData("Them cau hoi tu excel thanh cong");
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
