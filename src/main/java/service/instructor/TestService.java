package service.instructor;


import entity.Test;
import payload.response.Response;
import repository.TestRepository;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 04/04/2024 3:35 CH
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class TestService {
    private static final TestRepository testRepository = new TestRepository();

    public static Response getAllTests(int page, int size, String search, short majorID){
        Response response = new Response();
        try {
            response.setData(testRepository.getAllTests(page, size, search, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response getFilteredTests(int page, int size, String examId, String search,
                                            short majorID){
        Response response = new Response();
        try {
            response.setData(testRepository.getFilteredTests(page, size, examId, search, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }


    public static Test getTestByID(String testID) {
        return TestRepository.getTestByID(testID);
    }

    public static Response createTest(int subjectID, int examID, String testDescription,
                                      String testDuration, String testStartTime, String testEndTime,
                                      String testStatus, int numberOfQuestions, double totalPoint) {
        Response response = new Response();

        try {
            response.setData(TestRepository.createTest(subjectID, examID, testDescription,
                    testDuration, testStartTime, testEndTime, testStatus, numberOfQuestions, totalPoint));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response createTestDetails(int testID, int backupQuestionID) {
        Response response = new Response();

        try {
            response.setData(TestRepository.createTestDetails(testID, backupQuestionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response getResultsByTestID(String testID) {
        Response response = new Response();
        try {
            response.setData(TestRepository.getResultsByTestID(testID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getDataToDrawBarChart() {
        Response response = new Response();
        try {
            response.setData(testRepository.getDataToDrawBarChart());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }
    public Response getNumberTest() {
        Response response = new Response();
        try {
            response.setData(testRepository.getNumberTest());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getListResultOfStudentByTest(int testID) {
        Response response = new Response();
        try {
            response.setData(testRepository.getListResultOfStudentByTest(testID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getTestAndQuestionInTestByIdTest(int testID) {
        Response response = new Response();
        try {
            response.setData(testRepository.getTestAndQuestionInTestByIdTest(testID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response insertTestToTest(Test test, int fromTestId) {
        Response response = new Response();
        try {
            testRepository.insertTestToTest(test, fromTestId);
            response.setData("Insert test thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getIDTestMax() {
        Response response = new Response();
        try {
            int max = testRepository.getIDTestMax();
            response.setData(max);
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
