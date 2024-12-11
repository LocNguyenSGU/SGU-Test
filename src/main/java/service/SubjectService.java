package service;

import DTO.RoleDTO;
import entity.Role;
import entity.Subject;
import payload.response.Response;
import repository.SubjectRepository;

public class SubjectService {
    private static SubjectRepository subjectRepository = new SubjectRepository();

    public Response getAllSubject(int page, int size, String search) {
        Response response = new Response();
        try {
            response.setData(subjectRepository.getAllSubject(page, size, search));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Response getAllSubjects(int page, int size, String search, short majorID) {
        Response response = new Response();
        try {
            response.setData(SubjectRepository.getAllSubjects(page, size, search, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getAllSubjectWithoutPagination() {
        Response response = new Response();
        try {
            System.out.println("Đã vào getAll");
            response.setData(subjectRepository.getAllSubjectWithoutPagination());
            System.out.println("Đã truy cập Respository");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            System.out.println("Đã vào getAll");
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public static Subject getSubjectByID(int subjectID) {
        return SubjectRepository.getSubjectByID(subjectID);
    }

    public Response getSubjectById(short id) {
        Response response = new Response();
        try {
            response.setData(subjectRepository.getSubjectById(id));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public Response createSubject(String name, String description, short MajorID) {
        Response response = new Response();
        try {
            Subject subject = new Subject();
            subject.setName(name);
            subject.setMajorID(MajorID);
            subject.setDescription(description);
            // subject.validate();

            subjectRepository.createSubject(subject);
            response.setData("Tạo thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response updateSubject(short id, String name, String description, short MajorID) {
        Response response = new Response();
        try {
            Subject subject = new Subject();
            subject.setSubjectID(id);
            subject.setName(name);
            subject.setDescription(description);
            subject.setMajorID(MajorID);
            // subject.validate();

            subjectRepository.updateSubject(subject);
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

    public Response deleteSubject(short id) {
        Response response = new Response();
        try {
            subjectRepository.deleteSubject(id);
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

    public Response getAllNameSubject() {
        Response response = new Response();
        subjectRepository = new SubjectRepository();
        try {
            response.setData(subjectRepository.getAllNameSubject());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getSpectrum(short id) {
        Response response = new Response();
        subjectRepository = new SubjectRepository();
        try {
            response.setData(SubjectRepository.getSpectrum(id));
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
