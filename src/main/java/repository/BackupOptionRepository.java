package repository;

import config.MysqlConfig;
import entity.BackupOption;
import entity.Option;
import exception.RetrieveException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BackupOptionRepository {
    public List<BackupOption> getAllOptionsByBackupQuestionID(int BackupQuestionID){
        Connection connection = MysqlConfig.getConnection();
        List<BackupOption> backupOptions = new ArrayList<>();
        try {
            String getOptions = "SELECT * FROM BackupOption WHERE BackupQuestionID = ? ORDER BY OptionID ASC";
            PreparedStatement options_statement = connection.prepareStatement(getOptions);
            options_statement.setInt(1,BackupQuestionID);
            ResultSet options_resultSet = options_statement.executeQuery();
            while(options_resultSet.next()){
                BackupOption backupOption = new BackupOption();
                backupOption.setBackupOptionID(options_resultSet.getInt("OptionID"));
                backupOption.setTitle(options_resultSet.getString("Title"));
                backupOptions.add(backupOption);
            };
            return backupOptions;
        } catch (Exception e) {
            System.err.println("Error : query data unsuccessfully ,function ( getAllBackupOptionByQuestionID ), Details : "+e.getMessage());
            throw new RetrieveException((Map.of("Lỗi : ","Không thể tìm kiếm thông tin")));
        }finally {
            try{
                connection.close();
            }catch (Exception e){
                System.err.println("Error : can't close connection ,function ( getAllBackupOptionByTestID ) , Details : "+e.getMessage());
            }
        }
    }
}
