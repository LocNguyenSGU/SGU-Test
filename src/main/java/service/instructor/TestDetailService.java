package service.instructor;

import payload.response.Response;
import repository.TestDetailsRepository;

public class TestDetailService {
    private final TestDetailsRepository testDetailsRepository = new TestDetailsRepository();
    public Response insertTestIntoTestDetail(int testId, int backupQuestionId){
        Response response = new Response();
        try {
            testDetailsRepository.insertTestIntoTestDetail(testId, backupQuestionId);
            response.setData("insert tu test vo testDetail thanh cong (insertTestIntoTestDetail)");
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
