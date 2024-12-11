package service;

import DTO.SettingInitDTO;
import controller.student.StudentController;
import entity.Student;
import repository.StudentPointRepository;
import repository.StudentRepository;

import java.util.List;

public class SettingInitialResultAndResultDetailsForStudentByMajorIDService {
    private StudentRepository studentRepository = new StudentRepository();
    private StudentPointRepository studentPointRepository = new StudentPointRepository();
    public void settingInit(int ExamID){
        for(Student student : studentRepository.getAllStudentForSettingInitResult().getStudents()){
            System.out.println(student.getStudentID());
            studentPointRepository.settingInitResultForStudentWithRandomTest(ExamID,student.getMajorID(),student.getStudentID());
        }
    }
}
