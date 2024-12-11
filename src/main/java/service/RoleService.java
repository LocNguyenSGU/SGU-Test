package service;

import DTO.RoleDTO;
import entity.Role;
import payload.response.Response;
import repository.EmployeeRepository;
import repository.RoleRepository;

public class RoleService {
    private EmployeeRepository employeeRepository = new EmployeeRepository();
    private RoleRepository roleRepository = new RoleRepository();
    public Response getAllRole(int page, int size, String search){
        Response response = new Response();
        try {
            RoleDTO roleDTO = roleRepository.getAllRole(page,size,search);
            response.setData(roleDTO);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getAllRole(){
        Response response = new Response();
        try {
            RoleDTO roleDTO = roleRepository.getAllRoleWithouPagination();
            response.setData(roleDTO);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response getRoleById(short id){
        Response response = new Response();
        try {
            Role role = roleRepository.getRoleById(id);
            response.setData(role);
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        }catch (Exception e){
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public Response createRole(String name,String description){
        Response response = new Response();
        try {
            Role role = new Role();
            role.setName(name);
            role.setDescription(description);
//            role.validate();

            roleRepository.createRole(role);
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
    public Response updateRole(short id,String name,String description) {
        Response response = new Response();
        try {
            Role role = new Role();
            role.setRoleID(id);
            role.setName(name);
            role.setDescription(description);
//            role.validate();

            roleRepository.updateRole(role);
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
    public Response deleteRole(short id){
        Response response = new Response();
        try {
            employeeRepository.deleteRoleFromEmployee(id);
            roleRepository.deleteRole(id);
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
