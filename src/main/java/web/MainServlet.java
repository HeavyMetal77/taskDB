package web;

import DAO.EmpDao;
import org.json.JSONObject;
import services.JsonServices;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class MainServlet extends HttpServlet {
    ResultSet resultSet = null;
    EmpDao empDao = null;

    @Override
    public void init() throws ServletException {
        super.init();
        empDao = new EmpDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = getAction(req);
        resultSet = empDao.getResultSet();
        ArrayList<JSONObject> jsonObjects = JsonServices.getFormattedResult(resultSet);

        PrintWriter writer = resp.getWriter();
//        writer.write(jsonObjects.toString());
//        ObjectMapper objectMapper = new ObjectMapper();

        String[] names = JSONObject.getNames(jsonObjects.get(0));
        writer.write(Arrays.toString(names) + "\n");
        for (int i = 0; i < jsonObjects.size(); i++) {
            for (int j = 1; j <= jsonObjects.get(i).length(); j++) {
                JSONObject jsonObject = jsonObjects.get(i);
                String val = jsonObject.getString(names[j - 1]) + " ";
                writer.write(val);
            }
            writer.write("\n");
        }
    }

    private String getAction(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return requestURI.substring(req.getContextPath().length());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}

