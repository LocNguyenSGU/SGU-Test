package service;

import DTO.MajorDTO;
import DTO.TestDTO;
import entity.Major;
import entity.Test;
import payload.response.Response;
import repository.MajorRepository;
import repository.TestRepo2Loc;

public class TestService2Loc {
    private TestRepo2Loc testRepo2Loc;
    public Response getAllTestWithoutPaging(){
        Response response = new Response();
        testRepo2Loc = new TestRepo2Loc();
        try {
            TestDTO testDTO = testRepo2Loc.getAllTestWithoutPaging();
            response.setData(testDTO);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response deleteTestByTestID(int testID) {
        Response response = new Response();
        testRepo2Loc = new TestRepo2Loc();
        try {
            testRepo2Loc.deleteTestByTestID(testID);
            response.setData("Xoa thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getTestById(int id){
        Response response = new Response();
        testRepo2Loc = new TestRepo2Loc();
        try {
            Test test = testRepo2Loc.getTestById(id);
            response.setData(test);
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
