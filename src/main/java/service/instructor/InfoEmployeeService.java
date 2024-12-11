package service.instructor;

import DTO.InfoEmployeeAndMajorDTO;
import payload.response.Response;
import repository.InfoEmployeeRepository;

import java.sql.Connection;

public class InfoEmployeeService {
    private InfoEmployeeRepository infoEmployeeRepository;
    public Response getInfoEmployeeAndMajorDTOByIDEmployee(int e_id) {
        Response response = new Response();
        try {
            infoEmployeeRepository = new InfoEmployeeRepository();
            InfoEmployeeAndMajorDTO infoEmployeeAndMajorDTO = infoEmployeeRepository.getInfoEmployeeAndMajorDTOByIDEmployee(e_id);
            response.setData(infoEmployeeAndMajorDTO);
            response.setMessage("Thanh cong");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
            System.out.println("Loi ham getInfoEmployeeAndMajorDTOByIDEmployee service" + e);
        }
        return response;
    }
}
