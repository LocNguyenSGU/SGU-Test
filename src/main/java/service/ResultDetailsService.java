package service;

import DTO.ResultDetailsDTO;
import DTO.RoleDTO;
import payload.response.Response;
import repository.ResultDetailsRepository;
import repository.ResultRepository;

public class ResultDetailsService {
    private ResultRepository resultRepository = new ResultRepository();
    private ResultDetailsRepository resultDetailsRepository = new ResultDetailsRepository();
    public Response getAllResultDetailsByResultID(int ResultID){
        Response response = new Response();
        try {
            response.setData(resultDetailsRepository.getAllResultDetailsByResultID(ResultID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response updateResultDetails(int ResultID,int QuestionID,int ChooseOption){
        Response response = new Response();
        try {
            resultDetailsRepository.updateResultDetails(ResultID,QuestionID,ChooseOption);
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
}
