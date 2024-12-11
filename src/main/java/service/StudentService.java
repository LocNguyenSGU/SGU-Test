package service;

import entity.Student;
import payload.response.Response;
import repository.StudentRepository;

import java.sql.Date;

public class StudentService {
    private StudentRepository studentRepository = new StudentRepository();

    public static Response getResultsByStudentId(int studentId) {
        Response response = new Response();
        try {
            response.setData(StudentRepository.getResultsByStudentIdKiet(studentId));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public static Response getAllStudents(int page, int size, String majorID) {
        Response response = new Response();

        try {
            response.setData(StudentRepository.getAllStudentsKiet(page, size, majorID));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }

        return response;
    }

    public Response getAllStudentWithoutPagination() {
        System.out.println("Da vao function");
        Response response = new Response();
        try {
            response.setData(studentRepository.getAllStudentWithoutPagination());
            response.setMessage("Thành công");
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getStudentById(int id) {
        Response response = new Response();
        try {
            response.setData(studentRepository.getStudentById(id));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response createStudent(String firstname, String lastname, String phone, String password, String email,
            Boolean gender, Date birthdate, short majorid) {
        Response response = new Response();
        try {
            Student student = new Student();
            student.setPhone(phone);
            student.setGender(gender);
            student.setPassword(password);
            student.setFirstName(firstname);
            student.setLastName(lastname);
            student.setEmail(email);
            student.setMajorID(majorid);
            student.setDateOfBirth(birthdate);
            // student.validate();

            studentRepository.createStudent(student);
            response.setData("Tạo thành công");
            response.setStatusCode(200);
            response.setMessage("Tạo thành công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Email bị trùng");
        }
        return response;
    }

    public Response updateStudent(int studentid, String firstname, String lastname, String phone, Boolean gender,
            Date birthdate, short majorid) {
        Response response = new Response();
        try {
            Student student = new Student();
            student.setStudentID(studentid);
            student.setPhone(phone);
            student.setGender(gender);
            student.setFirstName(firstname);
            student.setLastName(lastname);
            student.setMajorID(majorid);
            student.setDateOfBirth(birthdate);
            // student.validate();

            studentRepository.updateStudent(student);
            response.setData("Cập nhật thành công");
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Cập nhật thông tin Thất Bại");
        }
        return response;
    }

    public Response deleteStudent(int id) {
        Response response = new Response();
        try {
            studentRepository.deleteStudent((short) id);
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

    public Response getFilteredStudents(int page, int size, String majorID, String search) {
        Response response = new Response();
        try {
            response.setData(StudentRepository.getFilteredStudentsKiet(page, size, majorID, search));

            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public static Response getFilteredStudentsKiet(int page, int size, String majorID, String search) {
        Response response = new Response();
        try {
            response.setData(StudentRepository.getFilteredStudentsKiet(page, size, majorID, search));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }
    public static Response getStudentById(short id) {
        Response response = new Response();
        try {
            response.setData(StudentRepository.getStudentByIdKiet(id));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response createStudent(String firstname, String lastname, String phone, String password,
            String email, boolean gender, Date birthdate, short majorid,
            short roleid) {
        Response response = new Response();
        try {
            Student student = new Student();
            student.setPhone(phone);
            student.setGender(gender);
            student.setPassword(password);
            student.setFirstName(firstname);
            student.setLastName(lastname);
            student.setEmail(email);
            student.setMajorID(majorid);
            student.setDateOfBirth(birthdate);
            student.validate();

            StudentRepository.createStudentKiet(student);
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

    public Response updateStudent(int studentid, String firstname, String lastname, String phone,
            String password, String email, boolean gender, Date birthdate,
            short majorid) {
        Response response = new Response();
        try {
            Student student = new Student();
            student.setStudentID(studentid);
            student.setPhone(phone);
            student.setGender(gender);
            student.setFirstName(firstname);
            student.setLastName(lastname);
            student.setPassword(password);
            student.setEmail(email);
            student.setMajorID(majorid);
            student.setDateOfBirth(birthdate);
            student.validate();

            StudentRepository.updateStudentKiet(student);
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

    public Response deleteStudent(short id) {
        Response response = new Response();
        try {
            StudentRepository.deleteStudentKiet(id);
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

    public Response getNumberStudent() {
        Response response = new Response();
        try {
            response.setData(StudentRepository.getNumberStudentKiet());
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response getResultStudentFormId(short id) {
        Response response = new Response();
        try {
            response.setData(studentRepository.getResultStudentFormId(id));
            response.setStatusCode(200);
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response checkEmailHasAlready(String email) {
        Response response = new Response();
        try {
            response.setData(studentRepository.checkEmailHasAlready(email));
            response.setStatusCode(200);
            response.setMessage("Thành Công");
        } catch (Exception e) {
            response.setData(e.getMessage());
            response.setStatusCode(400);
            response.setMessage("Thất Bại");
        }
        return response;
    }

    public Response authenticateStudentByEmailAndPassword(String email, String password) {
        Response response = new Response();
        int num = studentRepository.checkEmailAndPassword(email, password);
        System.out.println(num);
        if (num == 0) {
            response.setMessage("Email không tồn tại!");
            response.setStatusCode(400);
            response.setData(0);
            return response;
        } else if (num == 1) {
            response.setMessage("Mật khẩu không chính xác!");
            response.setStatusCode(400);
            response.setData(1);
            return response;
        } else {
            response.setData(studentRepository.getStudentByEmailAndPassword(email, password));
            response.setStatusCode(200);
            response.setMessage("Đăng nhập thành công!");
            return response;
        }
    }
}
