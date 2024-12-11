package DTO;

import entity.Role;

import java.util.List;

public class RoleDTO {
    private int totalPages;
    private List<Role> roles;

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
