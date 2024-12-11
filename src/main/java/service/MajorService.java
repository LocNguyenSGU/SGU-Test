package service;

import DTO.MajorDTO;
import entity.Major;
import payload.response.Response;
import repository.MajorRepository;

public class MajorService {
    private MajorRepository majorRepository;
    public Response getAllMajorWithoutPaging(){
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            MajorDTO majorDTO = majorRepository.getAllMajorWithoutPaging();
            response.setData(majorDTO);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getAllMajor(int page, int size, String search){
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            MajorDTO majorDTO = majorRepository.getAllMajor(page,size,search);
            response.setData(majorDTO);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getMajorById(short id){
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            Major major = majorRepository.getMajorById(id);
            response.setData(major);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createMajor(String name,String description){
        System.out.println("Da chay bo ham response creatMajor");
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            Major major = new Major();
            major.setName(name);
            major.setDescription(description);
//            major.validate();

            majorRepository.createMajor(major);
            response.setData("Tạo thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response updateMajor(short id,String name,String description){
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            Major major = new Major();
            major.setMajorID(id);
            major.setName(name);
            major.setDescription(description);
//            major.validate();

            majorRepository.updateMajor(major);
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
    public Response deleteMajor(short id){
        Response response = new Response();
        majorRepository = new MajorRepository();
        try {
            majorRepository.deleteMajor(id);
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