package DTO;

import entity.Employee;
import entity.Major;

public class InfoEmployeeAndMajorDTO {
    Major major;
    Employee employee;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
