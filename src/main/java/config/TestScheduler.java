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

@WebListener
public class TestScheduler implements ServletContextListener {
    private ScheduledExecutorService scheduler;
    private void scheduleNextUpdate() {
        Connection connection = MysqlConfig.getConnection();
        try {
            // Kiểm tra xem có dữ liệu trong cơ sở dữ liệu hay không
            String checkInprogress = "SELECT TestID,DateEnd FROM Test WHERE Status = 'IN_PROGRESS'";
            PreparedStatement checkInprogressstatement = connection.prepareStatement(checkInprogress);
            ResultSet checkInprogressresultSet = checkInprogressstatement.executeQuery();
            if (checkInprogressresultSet.next()) {
                long nextScheduledTime = checkInprogressresultSet.getTimestamp("DateEnd").getTime();

                System.out.println(nextScheduledTime);

                scheduler = Executors.newSingleThreadScheduledExecutor();
                scheduler.schedule(new TestScheduler.TestCheckerTask(checkInprogressresultSet.getInt("TestID"),"IN_PROGRESS"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
            }else {
                try {
                    String checkNotStart = "SELECT TestID,DateStart FROM Test WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                    PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                    ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                    if (checkNotStartresultSet.next()) {
                        long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new TestScheduler.TestCheckerTask(checkNotStartresultSet.getInt("TestID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }else{
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new TestScheduler.TestCheckerTask(0,null), 1, TimeUnit.MINUTES);
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
    private class TestCheckerTask implements Runnable {
        private int testid;
        private String Status;

        public TestCheckerTask(int testid, String status) {
            this.testid = testid;
            Status = status;
        }

        @Override
        public void run() {
            Connection connection = MysqlConfig.getConnection();
            if(testid == 0){
                try {
                    String checkNotStart = "SELECT TestID,DateStart FROM Test WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                    PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                    ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                    if (checkNotStartresultSet.next()) {
                        long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                        // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                        scheduler.shutdown();
                        scheduler = null;
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new TestScheduler.TestCheckerTask(checkNotStartresultSet.getInt("TestID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }else{
                        scheduler.shutdown();
                        scheduler = null;
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new TestScheduler.TestCheckerTask(0,null), 1, TimeUnit.MINUTES);
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                if(Status.equals("NOT_STARTED")){
                    try {
                        String updateNotStartToInProgress = "UPDATE Test SET Status = 'IN_PROGRESS' WHERE TestID = ?";
                        PreparedStatement updateNotStartToInProgressstatement = connection.prepareStatement(updateNotStartToInProgress);
                        updateNotStartToInProgressstatement.setInt(1,testid);
                        updateNotStartToInProgressstatement.executeUpdate();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    try {
                        String checkNotStart = "SELECT DateEnd FROM Test WHERE TestID = ?";
                        PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                        checkNotStartstatement.setInt(1,testid);
                        ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                        checkNotStartresultSet.next();
                        long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateEnd").getTime();
                        // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                        scheduler.shutdown();
                        scheduler = null;
                        scheduler = Executors.newSingleThreadScheduledExecutor();
                        scheduler.schedule(new TestScheduler.TestCheckerTask(testid,"IN_PROGRESS"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }else{
                    try {
                        String updateInProgressToCompleted = "UPDATE Test SET Status = 'COMPLETED' WHERE TestID = ?";
                        PreparedStatement updateInProgressToCompletedstatement = connection.prepareStatement(updateInProgressToCompleted);
                        updateInProgressToCompletedstatement.setInt(1,testid);
                        updateInProgressToCompletedstatement.executeUpdate();
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    try {
                        String checkNotStart = "SELECT TestID,DateStart FROM Test WHERE DateStart > NOW() ORDER BY DateStart ASC LIMIT 1";
                        PreparedStatement checkNotStartstatement = connection.prepareStatement(checkNotStart);
                        ResultSet checkNotStartresultSet = checkNotStartstatement.executeQuery();
                        if (checkNotStartresultSet.next()) {
                            long nextScheduledTime = checkNotStartresultSet.getTimestamp("DateStart").getTime();
                            // Đặt lịch cho việc kiểm tra tiếp theo dựa trên thời gian gần nhất
                            scheduler.shutdown();
                            scheduler = null;
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.schedule(new TestScheduler.TestCheckerTask(checkNotStartresultSet.getInt("TestID"),"NOT_STARTED"), nextScheduledTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                        }else{
                            scheduler.shutdown();
                            scheduler = null;
                            scheduler = Executors.newSingleThreadScheduledExecutor();
                            scheduler.schedule(new TestScheduler.TestCheckerTask(0,null), 1, TimeUnit.MINUTES);
                        }
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                }
            }
            try {
                connection.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
