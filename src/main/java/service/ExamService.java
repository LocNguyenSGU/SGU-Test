package service;

import DTO.ExamDTO;
import entity.Enum.ExaminationStatus;
import entity.Exam;
import entity.Major;
import repository.EmployeeRepository;
import repository.ExamRepository;
import repository.MajorRepository;
import repository.TestRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import DTO.TestDTO;
import payload.response.Response;
import java.util.List;

public class ExamService {
    private ExamRepository examRepository;
    private Response response;
    private TestRepository testRepository;

    public Response getAllExam(int page, int size, String search, String status) {
        response = new Response();
        examRepository = new ExamRepository();
        try {
            ExamDTO examDTO = examRepository.getAllExam(page, size, search, status);
            response.setData(examDTO);
            response.setStatusCode(200);
            response.setMessage("Thành công");
            System.out.println("set DTO thanh cong");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }

    public Response getAllExamsWithoutPaging() {
        response = new Response();
        examRepository = new ExamRepository();
        try {
            ExamDTO examDTO = examRepository.getAllExamsWithoutPaging();
            response.setData(examDTO);
            response.setStatusCode(200);
            response.setMessage("Thành công");
            System.out.println("set DTO thanh cong");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }

    public Response getAllExamWithoutPageable(String status) {
        response = new Response();
        examRepository = new ExamRepository();
        try {
            ExamDTO examDTO = examRepository.getAllExamWithoutPageable(status);
            response.setData(examDTO);
            response.setStatusCode(200);
            response.setMessage("Thành công");
            System.out.println("set DTO thanh cong");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }

    public Response getExamWithLessOfEqualThanTwoWeeks(short MajorID) {
        response = new Response();
        examRepository = new ExamRepository();
        testRepository = new TestRepository();
        try {
            Exam exam = examRepository.getExamWithLessOfEqualThanTwoWeeks();
            response.setData(exam);
            response.setStatusCode(200);
            response.setMessage("Thành công");
            System.out.println("set DTO thanh cong");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }

    public Response createExam(String name, LocalDateTime dataStart, LocalDateTime dateEnd, ExaminationStatus status) {
        Response response = new Response();
        examRepository = new ExamRepository();
        try {
            Exam exam = new Exam();
            exam.setName(name);
            exam.setDateStart(dataStart);
            exam.setDateEnd(dateEnd);
            exam.setStatus(status);
            // exam.validate();
            examRepository.createExam(exam);
            response.setData("Tạo thành công");
            response.setStatusCode(200);
            response.setMessage("Thành công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getExamById(int examId) {
        Response response = new Response();
        examRepository = new ExamRepository();
        try {
            Exam exam = examRepository.getExamById(examId);
            response.setData(exam);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;

    }

    public Response updateExam(int examId, String name, LocalDateTime dateStart, LocalDateTime dateEnd,
            ExaminationStatus status) {
        Response response = new Response();
        examRepository = new ExamRepository();
        System.out.println("da vo hàm update mong muon");
        try {
            Exam exam = new Exam();
            exam.setExamID(examId);
            System.out.println("EXAM: " + exam.getExamID());
            exam.setName(name);
            System.out.println("NAME: " + exam.getName());

            exam.setDateStart(dateStart);
            System.out.println("DATE S: " + exam.getDateStart());

            exam.setDateEnd(dateEnd);
            System.out.println("DATE E: " + exam.getDateEnd());
            exam.setStatus(status);
            System.out.println("NAME: " + exam.getStatus());

            // exam.validate();

            examRepository.updateExam(exam);
            response.setData("Cập nhật thành công");
            response.setStatusCode(200);
            response.setMessage("Thành công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response deleteExam(int id) {
        Response response = new Response();
        examRepository = new ExamRepository();
        try {
            examRepository.deleteExam(id);
            response.setData("Cập nhật thành công");
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
