package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Data;

import java.io.*;

public class GsonHelper {


    public static Object getDataByJson(String jsonFile, Class c) throws FileNotFoundException
    {

        Gson gson = new GsonBuilder().create();

        try {
            Reader reader = new FileReader(jsonFile);
            Object res = gson.fromJson(reader, c);
            return res;
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("file note found");
        }
    }

    public static void saveDataInJson(Data data) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        //for verify
        //String json = gson.toJson(data);
        //System.out.println(json);

        try (FileWriter writer = new FileWriter(Utils.CONFIG_FILE)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
