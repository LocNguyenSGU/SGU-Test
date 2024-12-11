package repository;

import DTO.RoleDTO;
import config.MysqlConfig;
import entity.Role;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleRepository {
     public RoleDTO getAllRole(int page, int size, String search){
          Connection connection = MysqlConfig.getConnection();
          RoleDTO roleDTO = new RoleDTO();
          try {
               String total_pages = "SELECT COUNT(*) FROM Role ";

               String getRoles = "SELECT * FROM Role LIMIT ?,? ";

               if(search!=null){
                    total_pages += "WHERE Name = ?";
                    getRoles += "WHERE Name = ?";
               }
               PreparedStatement total_pages_statement = connection.prepareStatement(total_pages);

               total_pages_statement.setString(1,search);

               ResultSet total_pages_resultSet = total_pages_statement.executeQuery();

               roleDTO.setTotalPages(total_pages_resultSet.getInt(1)/size);

               List<Role> roles = new ArrayList<>();


               PreparedStatement roles_statement = connection.prepareStatement(getRoles);

               roles_statement.setInt(1,(page-1)*size);
               roles_statement.setInt(2,size);
               roles_statement.setString(3,search);

               ResultSet roles_resultSet = roles_statement.executeQuery();

               while(roles_resultSet.next()){
                    Role role = new Role();
                    role.setRoleID(roles_resultSet.getShort("RoleID"));
                    role.setName(roles_resultSet.getString("Name"));
                    role.setDescription(roles_resultSet.getString("Description"));
                    roles.add(role);
               }

               roleDTO.setRoles(roles);
          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( getAllRole ), Details : "+e.getMessage());
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( getAllRole ) , Details : "+e.getMessage());
               }
          }
          return roleDTO;
     }
     public RoleDTO getAllRoleWithouPagination(){
          Connection connection = MysqlConfig.getConnection();
          RoleDTO roleDTO = new RoleDTO();
          try {
               String getRoles = "SELECT * FROM Role ";
               List<Role> roles = new ArrayList<>();
               PreparedStatement roles_statement = connection.prepareStatement(getRoles);
               ResultSet roles_resultSet = roles_statement.executeQuery();
               while(roles_resultSet.next()){
                    Role role = new Role();
                    role.setRoleID(roles_resultSet.getShort("RoleID"));
                    role.setName(roles_resultSet.getString("Name"));
                    role.setDescription(roles_resultSet.getString("Description"));
                    roles.add(role);
               }
               roleDTO.setRoles(roles);
               return roleDTO;
          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( getAllRoleWithoupagination ), Details : "+e.getMessage());
               throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( getAllRole ) , Details : "+e.getMessage());
               }
          }
     }
     public Role getRoleById(short id){
          Connection connection = MysqlConfig.getConnection();
          Role role = new Role();
          try {
               String getRole = "SELECT DISTINCT FROM Role WHERE RoleID = ?";

               PreparedStatement roles_statement = connection.prepareStatement(getRole);

               roles_statement.setShort(1,id);

               ResultSet roles_resultSet = roles_statement.executeQuery();

               role.setRoleID(roles_resultSet.getShort("RoleID"));
               role.setName(roles_resultSet.getString("Name"));
               role.setDescription(roles_resultSet.getString("description"));

               return role;
          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( getRoleById ), Details : "+e.getMessage());
               throw new RetrieveException((Map.of("Quyền","Không thể tìm kiếm thông tin")));
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( getRoleById ) , Details : "+e.getMessage());
               }
          }
     }
     public void createRole(Role role){
          Connection connection = MysqlConfig.getConnection();
          try {
               String createRole = "INSERT INTO Role (Name, Description) VALUES (?,?)";

               PreparedStatement create_role_statement = connection.prepareStatement(createRole);

               create_role_statement.setString(1,role.getName());
               create_role_statement.setString(2,role.getDescription());

               create_role_statement.executeUpdate();
          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( createRole ), Details : "+e.getMessage());
               throw new RetrieveException((Map.of("Quyền","Không thể tạo quyền")));
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( createRole ) , Details : "+e.getMessage());
               }
          }
     }
     public void updateRole(Role role){
          Connection connection = MysqlConfig.getConnection();
          try {
               String updateRole = "UPDATE Role SET Name = ? , Description = ? WHERE RoleID = ?";

               PreparedStatement update_role_statement = connection.prepareStatement(updateRole);

               update_role_statement.setString(1,role.getName());
               update_role_statement.setString(2,role.getDescription());
               update_role_statement.setShort(3,role.getRoleID());

               update_role_statement.executeUpdate();
          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( updateRole ), Details : "+e.getMessage());
               throw new RetrieveException((Map.of("Quyền","Không thể cập nhật quyền")));
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( updateRole ) , Details : "+e.getMessage());
               }
          }
     }
     public void deleteRole(short id){
          Connection connection = MysqlConfig.getConnection();
          try {
//             Remove role
               String deleteRole = "DELETE FROM Role WHERE RoleID = ?";

               PreparedStatement remove_role_statement = connection.prepareStatement(deleteRole);
               remove_role_statement.setShort(1,id);
               remove_role_statement.executeUpdate();

          }catch (Exception e){
               System.err.println("Error : query data unsuccessfully ,function ( updateRole ), Details : "+e.getMessage());
               throw new RetrieveException((Map.of("Lỗi : ","Không thể xóa quyền")));
          }finally {
               try{
                    connection.close();
               }catch (Exception e){
                    System.err.println("Error : can't close connection ,function ( updateRole ) , Details : "+e.getMessage());
               }
          }
     }
}
