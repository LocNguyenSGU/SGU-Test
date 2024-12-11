package service;

import DTO.StudentPointDTO;
import DTO.TestDTO;
import entity.BackupOption;
import entity.Result;
import entity.ResultDetails;
import entity.StudentPoint;
import payload.response.Response;
import repository.*;

import java.util.List;

public class ResultService {
    private ResultRepository resultRepository = new ResultRepository();
    private ResultDetailsRepository resultDetailsRepository = new ResultDetailsRepository();
    public Response getResultByStudentID(int StudentID){
        Response response = new Response();
        try {
            Result result = resultRepository.getResultByStudentID(StudentID);
            response.setData(result);
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
    public Response updateResultStatus(int ResultID,String status){
        Response response = new Response();
        try {
            resultRepository.updateResultStatus(ResultID,status);
            response.setData("Cập nhật trạng thái thành công");
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
    public Response getResultByStudentIDAndTestID(int StudentID,int TestID){
        Response response = new Response();
        try {
            Result result = resultRepository.getResultByStudentIDAndTestID(StudentID,TestID);
            result.setResultDetails(resultDetailsRepository.getAllResultDetailsByResultID(result.getResultID()).getResultDetails());
            response.setData(result);
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
    public Response getResultByStudentIDAndExamID(int StudentID, int ExamID){
        Response response = new Response();
        try {
            response.setData(resultRepository.getResultByStudentIDAndExamID(StudentID,ExamID));
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
    public Response getResultByStudentIDAndTestIDExportDoc(int StudentID,int ExamID){
        Response response = new Response();
        try {
            List<Result> results = resultRepository.getResultByStudentIDAndExamID(StudentID,ExamID);
            response.setData(results);
            response.setStatusCode(200);
            response.setMessage("Thành công");
        }catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất bại");
        }
        return response;
    }
}
