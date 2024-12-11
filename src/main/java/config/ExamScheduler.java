package config;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
import service.SettingInitialResultAndResultDetailsForStudentByMajorIDService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener(value = "Listener")
public class ExamScheduler implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    private final SettingInitialResultAndResultDetailsForStudentByMajorIDService settingInitialService = new SettingInitialResultAndResultDetailsForStudentByMajorIDService();
    private void scheduleNextUpdate() {
        Connection connection = MysqlConfig.getConnection();
        try {
            // Kiểm tra xem có dữ liệu trong cơ sở dữ liệu hay không
            String checkInprogress = "SELECT ExamID,DateEnd FROM Exam WHERE Status = 'IN_PROGRESS'";
            PreparedStatement checkInprogressstatement = connection.prepareStatement(checkInprogress);
            ResultSet checkInprogressresultSet = checkInprogressstatement.executeQuery();
            if (checkInprogressresultSet.next()) {
                long nextScheduledTime = checkInprogressresultSet.getTimestamp("DateEnd").getTime();
                System.err.println(checkInprogressresultSet.getTimestamp("DateEnd").toLocalDateTime());
                scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(new ExamCheckerTask(checkInprogressresultSet.getInt("ExamID"),"IN_PROGRESS"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }else {
                try {
                    String checkNotStart = "SELECT ExamID,DateStart FROM Exam WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                    PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                    ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                    if (checkNotStartresultSet.next()) {
                        long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new ExamCheckerTask(checkNotStartresultSet.getInt("ExamID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }else{
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new ExamCheckerTask(0,null), 1, TimeUnit.MINUTES);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduleNextUpdate();
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        AbandonedConnectionCleanupThread.checkedShutdown();
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
    private class ExamCheckerTask implements Runnable {
        private int examid;
        private String Status;

        public ExamCheckerTask(int examid, String status) {
            this.examid = examid;
            Status = status;
        }

        @Override
        public void run() {
            Connection connection = MysqlConfig.getConnection();
            if(examid == 0){
                try {
                    String checkNotStart = "SELECT ExamID,DateStart FROM Exam WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                    PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                    ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                    if (checkNotStartresultSet.next()) {
                        long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                        // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                        scheduler.shutdown();
                        scheduler = null;
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new ExamCheckerTask(checkNotStartresultSet.getInt("ExamID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }else{
                        scheduler.shutdown();
                        scheduler = null;
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new ExamCheckerTask(0,null), 1, TimeUnit.MINUTES);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                if(Status.equals("NOT_STARTED")){
                    settingInitialService.settingInit(examid);
                    try {
                        String updateNotStartToInProgress = "UPDATE Exam SET Status = 'IN_PROGRESS' WHERE ExamID = ?";
                        PreparedStatement updateNotStartToInProgressstatement = connection.prepareStatement(updateNotStartToInProgress);
                        updateNotStartToInProgressstatement.setInt(1,examid);
                        updateNotStartToInProgressstatement.executeUpdate();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    try {
                        String checkNotStart = "SELECT DateEnd FROM Exam WHERE ExamID = ?";
                        PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                        checkNotStartstatement.setInt(1,examid);
                        ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                        checkNotStartresultSet.next();
                            long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateEnd").getTime();
                            // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                            scheduler.shutdown();
                            scheduler = null;
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.schedule(new ExamCheckerTask(examid,"IN_PROGRESS"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String updateInProgressToCompleted = "UPDATE Exam SET Status = 'COMPLETED' WHERE ExamID = ?";
                        PreparedStatement updateInProgressToCompletedstatement = connection.prepareStatement(updateInProgressToCompleted);
                        updateInProgressToCompletedstatement.setInt(1,examid);
                        updateInProgressToCompletedstatement.executeUpdate();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    try {
                        String checkNotStart = "SELECT ExamID,DateStart FROM Exam WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                        PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                        ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                        if (checkNotStartresultSet.next()) {
                            long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                            // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                            scheduler.shutdown();
                            scheduler = null;
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.schedule(new ExamCheckerTask(checkNotStartresultSet.getInt("ExamID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                        }else{
                            scheduler.shutdown();
                            scheduler = null;
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.schedule(new ExamCheckerTask(0,null), 1, TimeUnit.MINUTES);
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            try {
                connection.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
