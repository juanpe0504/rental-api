/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author JuanPe
 */
public class Rentals_DB_Handler {
  
  public Rentals_DB_Handler(){}
  String filename = "BD_Rentals/rentals.json";
  private String dataRentals = 
"{\n" +
"  \"rentals_type\": [\n" +
"    {\n" +
"      \"tarifa\": 5.0,\n" +
"      \"descuento\": 0.0,\n" +
"      \"description\": \"Por hora\",\n" +
"      \"id\": 1\n" +
"    },\n" +
"    {\n" +
"      \"tarifa\": 20.0,\n" +
"      \"descuento\": 0.0,\n" +
"      \"description\": \"Por dia\",\n" +
"      \"id\": 2\n" +
"    },\n" +
"    {\n" +
"      \"tarifa\": 60.0,\n" +
"      \"descuento\": 0.0,\n" +
"      \"description\": \"Por semana\",\n" +
"      \"id\": 3\n" +
"    },\n" +
"    {\n" +
"      \"tarifa\": 1.0,\n" +
"      \"descuento\": 30.0,\n" +
"      \"description\": \"Familiar\",\n" +
"      \"id\": 4\n" +
"    }\n" +
"  ],\n" +
"  \"rentals\": [\n" +
"    {\n" +
"      \"total\": 200.0,\n" +
"      \"timing\": 200,\n" +
"      \"description\": \"Este es el elemnto 1\",\n" +
"      \"id_type\": 1,\n" +
"      \"id\": 1\n" +
"    },\n" +
"    {\n" +
"      \"total\": 200.0,\n" +
"      \"timing\": 200,\n" +
"      \"description\": \"Este es el elemnto 2\",\n" +
"      \"id_type\": 1,\n" +
"      \"id\": 2\n" +
"    },\n" +
"    {\n" +
"      \"total\": 200.0,\n" +
"      \"timing\": 200,\n" +
"      \"description\": \"Este es el elemnto 3\",\n" +
"      \"id_type\": 1,\n" +
"      \"id\": 3\n" +
"    },\n" +
"    {\n" +
"      \"total\": 100.0,\n" +
"      \"timing\": 20,\n" +
"      \"description\": \"Esto es una nueva Rentals\",\n" +
"      \"id_type\": 1,\n" +
"      \"id\": 11,\n" +
"      \"id_parent\": -1\n" +
"    }\n" +
"  ]\n" +
"}";  
  
  public JSONObject getData() throws FileNotFoundException, JSONException, IOException{
    validateFolder();
    JSONObject jsonObject = parseJSONFile(filename);    
    return jsonObject;
  }
  
  public JSONObject addRentals(Rentals rent) throws FileNotFoundException, JSONException, IOException{
    validateFolder();
    JSONObject jsonObject = parseJSONFile(filename);
    ((JSONArray)jsonObject.get("rentals")).put(rent.toJSON());
    jsonObject.put("autoIncrementId", rent.id + 1);
    escribirArchivo(jsonObject.toString(2)); 
    return rent.toJSON();
  }
  
  public JSONObject updateRentals(int pos, Rentals rent) throws FileNotFoundException, JSONException, IOException{
    
    if (pos < 0 ) return new JSONObject();
    
    validateFolder();
    JSONObject jsonFull =  parseJSONFile(filename);
    ((JSONObject)((JSONArray)jsonFull.get("rentals")).get(pos))
            .put("id_parent", rent.id_parent)
            .put("description",rent.description)
            .put("id_type", rent.id_type)
            .put("total",rent.total)
            .put("timing", rent.timing);
    escribirArchivo(jsonFull.toString(2));    
    
    return jsonFull;
  }
  
  public JSONObject deleteRentals(ArrayList<Integer> pos) throws FileNotFoundException, JSONException, IOException{
    
    if (pos.size() < 0 ) return new JSONObject();
    
    validateFolder();
    JSONObject jsonFull =  parseJSONFile(filename);
    for (Integer po : pos) {
      ((JSONArray)jsonFull.get("rentals")).remove(po);
    }    
    escribirArchivo(jsonFull.toString(2));    
    
    return jsonFull;
  }
  
  public static JSONObject parseJSONFile(String filename) throws JSONException, IOException {
      String content = new String(Files.readAllBytes(Paths.get(filename)));
      return new JSONObject(content);
  }
  
  private void validateFolder() throws IOException{
    Path path = Paths.get("BD_Rentals");
    if (Files.notExists(path)) {
        Files.createDirectories(path);
        path = Paths.get(filename);
        Files.createFile(path);
        escribirArchivo(dataRentals);
    }
  }
  
  
  private void escribirArchivo(String text) throws IOException{
  
    BufferedWriter bw_componente = null;
    try {
      
      bw_componente = new BufferedWriter(new FileWriter(filename, false));
      bw_componente.write(text);
    } catch (IOException ex) {
      Logger.getLogger(Rentals_DB_Handler.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      try {
        bw_componente.close();
      } catch (IOException ex) {
        Logger.getLogger(Rentals_DB_Handler.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
  }
  

  
}
