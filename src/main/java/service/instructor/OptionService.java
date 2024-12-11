package service.instructor;


import DTO.OptionDTO;
import entity.Option;
import payload.response.Response;
import repository.OptionRepository;
import repository.QuestionBankRepository;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 17/04/2024 10:21 SA
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


public class OptionService {

    public static Response createOption(String title, int questionID) {
        Response response = new Response();
        try {
            response.setData(OptionRepository.createOption(title, questionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response updateOption(String title, int optionID) {
        Response response = new Response();

        try {
            response.setData(OptionRepository.updateOption(title, optionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response deleteOption(int optionID) {
        Response response = new Response();

        try {
            response.setData(OptionRepository.deleteOption(optionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response getOptionsByQuestionID(String questionID) {
        Response response = new Response();

        try {
            OptionDTO options = OptionRepository.getOptionsByQuestionID(questionID);
            response.setData(options);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response deleteOptionsByQuestionID(String questionID) {
        Response response = new Response();

        try {
            response.setData(OptionRepository.deleteOptionsByQuestionID(questionID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response createBackupOption(int backupQuestionID, String title) {
        Response response = new Response();

        try {
            response.setData(OptionRepository.createBackupOption(backupQuestionID, title));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }


    public Response createOptionExcel(String excelFilePath){
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();
        System.out.println("chay toi response cua createQuestionExcel1 ");
        try {
            System.out.println("chay toi response cua createQuestionExcel2 ");
            optionRepository.createOptionExcel(excelFilePath);
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
    public Response createOption_loc(int questionId, String title) {
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();

        try {
            optionRepository.createOption_loc(questionId, title);
            response.setData("Tao moi option thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }
    public Response createOption_loc(int optionID,int questionId, String title) {
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();

        try {
            optionRepository.createOption_loc(optionID,questionId, title);
            response.setData("Tao moi option thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }
    public Response updateOption(Option option) {
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();
        try {
            optionRepository.updateOption(option);
            response.setData("update option thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }
    public Response deleteAllOptionByQuestionId(int questionId) {
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();

        try {
            optionRepository.deleteAllOptionByQuestionId(questionId);
            response.setData("delete all option thanh cong");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getIDOptionMax() {
        Response response = new Response();
        OptionRepository optionRepository = new OptionRepository();
        try {
            response.setData(optionRepository.getIDOptionMax());
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