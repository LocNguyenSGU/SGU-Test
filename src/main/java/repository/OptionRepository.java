package repository;

import DTO.OptionDTO;
import config.MysqlConfig;
import entity.Option;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OptionRepository {
    private static final Connection connection = MysqlConfig.getConnection();

    public static OptionDTO getOptionsByQuestionID(String questionID) {
        OptionDTO optionDTO = new OptionDTO();

        String getOptionsQuery = "SELECT * FROM `option` WHERE questionid = ? AND isDeleted = false";
        String countOptionsQuery = "SELECT COUNT(*) FROM `option` WHERE questionid = ? AND isDeleted = false";

        List<Object> parameters = new ArrayList<>();
        parameters.add(questionID);

        try (
                PreparedStatement getOptionsStatement = connection.prepareStatement(getOptionsQuery);
                PreparedStatement countOptionsStatement = connection.prepareStatement(countOptionsQuery)) {
            for (int i = 0; i < parameters.size(); i++) {
                getOptionsStatement.setObject(i + 1, parameters.get(i));
                countOptionsStatement.setObject(i + 1, parameters.get(i));
            }

            ResultSet getOptionsResult = getOptionsStatement.executeQuery();
            ResultSet countOptionsResult = countOptionsStatement.executeQuery();

            List<Option> options = new ArrayList<>();
            while (getOptionsResult.next()) {
                Option option = new Option();
                option.setOptionID(getOptionsResult.getInt("optionid"));
                option.setQuestionID(getOptionsResult.getInt("questionid"));
                option.setTitle(getOptionsResult.getString("title"));
                options.add(option);
            }

            optionDTO.setOptions(options);
            if (countOptionsResult.next()) {
                optionDTO.setTotalOptions(countOptionsResult.getInt(1));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return optionDTO;
    }

    public static int createOption(String title, int questionID) {
        String query = "INSERT INTO `option` (title, questionid) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, title);
            statement.setInt(2, questionID);

            statement.executeUpdate();

            // return the id of the created option
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }

    public static boolean updateOption(String title, int optionID) {
        String query = "UPDATE `option` SET title = ? WHERE optionid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, title);
            statement.setInt(2, optionID);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteOption(int optionID) {
        // Simply set isDeleted to true
        String setDeletedQuery = "UPDATE `option` SET isDeleted = true WHERE optionid = ?";

        try (PreparedStatement setDeletedStm = connection.prepareStatement(setDeletedQuery)) {
            setDeletedStm.setInt(1, optionID);

            return setDeletedStm.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean deleteOptionsByQuestionID(String questionID) {
        String query = "UPDATE `option` SET isDeleted = true WHERE questionid = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, questionID);

            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int createBackupOption(int backupQuestionID, String title) {
        String query = "INSERT INTO `backupoption` (backupquestionid, title) VALUES (?, ?)";

        try (PreparedStatement statement = connection
                .prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, backupQuestionID);
            statement.setString(2, title);

            statement.executeUpdate();

            // return the id of the created option
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return -1;
    }
    public void createOption_loc(int optionId, int questionId, String title) throws SQLException {
        try {
            String sql = "insert into option (optionId,questionid, title) values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, optionId);
            preparedStatement.setInt(2, questionId);
            preparedStatement.setString(3, title);

            System.out.println("Cau lenh insert option la: " + sql);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Loi o ham createOption_loc (option repository)" + e);
        }
    }
    public void createOption_loc(int questionId, String title) throws SQLException {
       try {
           String sql = "insert into option (questionid, title) values (?, ?)";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, questionId);
           preparedStatement.setString(2, title);

           System.out.println("Cau lenh insert option la: " + sql);
           preparedStatement.executeUpdate();
       } catch (Exception e) {
           System.out.println("Loi o ham createOption_loc (option repository)" + e);
       }
    }

    public void updateOption(Option option) throws SQLException {
        String sql = "update option set questionid = ?, set title = ? where optionid = ?";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, option.getOptionID());
        preparedStatement.setInt(2, option.getQuestionID());
        preparedStatement.setString(3, option.getTitle());

        System.out.println("Cau lenh update option la: " + sql);
        preparedStatement.executeUpdate();
    }

    public void deleteAllOptionByQuestionId(int questionId) throws SQLException, SQLException {
        String sql = "delete from option where questionId = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, questionId);

        System.out.println("cau lenh delete option la: " + sql);
        preparedStatement.executeUpdate();
    }

    public boolean isCellColored(Cell cell) {
        CellStyle cellStyle = cell.getCellStyle();
        short colorIdx = cellStyle.getFillForegroundColor();
        return colorIdx != 0;
    }

    public void createOptionExcel(String excelFilePath) throws IOException, SQLException {
        FileInputStream inputStream = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet firstSheet = workbook.getSheetAt(1);
        Iterator<Row> rowIterator = firstSheet.iterator();
        String sql = "insert into option (optionid, questionid, title) values (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        int count = 0;
        int batchSize = 20;
        rowIterator.next(); // skip the header row
        while (rowIterator.hasNext()) {
            Row nextRow = rowIterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            while (cellIterator.hasNext()) {
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex) {
                    case 0:
                        int optionid = (int) nextCell.getNumericCellValue();
                        System.out.println("optionid: " + optionid);
                        preparedStatement.setInt(1, optionid);
                        break;
                    case 1:
                        int questionid = (int) nextCell.getNumericCellValue();
                        System.out.println("questionid: " + questionid);
                        preparedStatement.setInt(2, questionid);
                        break;
                    case 2:
                        if (isCellColored(nextCell)) {
                            System.out.println("Cell is colored");
                        } else {
                            System.out.println("Cell is not colored");
                        }
                        if (nextCell.getCellType() == CellType.NUMERIC) {
                            double numericValue = nextCell.getNumericCellValue();
                            String stringValue = String.valueOf(numericValue);
                            preparedStatement.setString(3, stringValue);
                        } else if (nextCell.getCellType() == CellType.STRING) {
                            String title = nextCell.getStringCellValue();
                            System.out.println("title: " + title);
                            preparedStatement.setString(3, title);
                        }
                        break;
                }
            }
            preparedStatement.addBatch();
            if (++count % batchSize == 0) {
                preparedStatement.executeBatch();
            }
        }
        workbook.close();
        preparedStatement.executeBatch();
    }
    public int getIDOptionMax() {
        int max = 0;
        try {
            String sql = "select max(optionID) from option";
            PreparedStatement preparedStatement = MysqlConfig.getConnection().prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            max = resultSet.getInt(1);
            return max;
        }
        catch(Exception e) {
            System.out.println("Loi o ham getIDOptionMax optionRepo" + e);
        }
        return max;
    }
}
