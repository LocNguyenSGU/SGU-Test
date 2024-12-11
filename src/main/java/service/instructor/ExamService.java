package service.instructor;


import payload.response.Response;
import repository.ExamRepository;

/**
 * Author: Kiet Mai Tran Tuan
 * Created: 04/04/2024 3:43 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class ExamService {
    private final ExamRepository examRepository = new ExamRepository();
    

    public static Response getAllExams(int page, int size, String search, String status) {
        Response response = new Response();
        try {
            response.setData(ExamRepository.getAllExams(page, size, search, status));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

}
