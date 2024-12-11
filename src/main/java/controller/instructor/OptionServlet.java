package controller.instructor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import config.LocalDateTimeAdapter;
import payload.response.Response;
import service.instructor.OptionService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


/**
 * Author: Kiet Mai Tran Tuan
 * Created: 26/04/2024 9:55 SA
 * Project name: Java_SGU
 * “Family is where life begins and love never ends.”
 */


@WebServlet(name = "OptionServlet",
        urlPatterns = {"/instructor/option/create", "/instructor/option/update", "/instructor" +
                "/option/create-backup" })
public class OptionServlet extends HttpServlet {
    private final Gson gson = new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String contextPath = req.getServletPath();

        switch (contextPath) {
            case "/instructor/option/create":
                JsonObject jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                String title = jsonObject.get("title").getAsString();
                int subjectID = jsonObject.get("questionID").getAsInt();

                // Create question
                Response response = OptionService.createOption(title, subjectID);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/option/update":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                title = jsonObject.get("title").getAsString();
                int optionID = jsonObject.get("optionID").getAsInt();

                // Create question
                response = OptionService.updateOption(title, optionID);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/option/delete":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                optionID = jsonObject.get("optionID").getAsInt();

                // Delete option
                response = OptionService.deleteOption(optionID);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            case "/instructor/option/create-backup":
                jsonObject = new JsonParser().parse(req.getReader()).getAsJsonObject();

                // Get data from request
                int backupQuestionID = jsonObject.get("backupQuestionID").getAsInt();
                title = jsonObject.get("title").getAsString();

                // Create backup option
                response = OptionService.createBackupOption(backupQuestionID, title);

                // Response
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                resp.getWriter().write(gson.toJson(response));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                break;
        }


    }
}
