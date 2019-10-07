package services;

import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class JsonServices {

    public static ArrayList<JSONObject> getFormattedResult(ResultSet resultSet) {
        ArrayList<JSONObject> resList = new ArrayList<JSONObject>();
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            ArrayList<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            while (resultSet.next()) {
                JSONObject jsonObject = new JSONObject();
                for (int i = 1; i <= columnCount; i++) {
                    String key = columnNames.get(i - 1);
                    String value = resultSet.getString(i);
                    if (value == null) {
                        value = "null";
                    }
                    jsonObject.put(key, value);
                }
                resList.add(jsonObject);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return resList;
    }
}
