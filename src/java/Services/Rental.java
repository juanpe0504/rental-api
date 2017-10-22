/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Models.Rentals;
import Models.RentalsType;
import Models.Rentals_DB_Handler;
import Models.RequestParam;
import Models.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author JuanPe
 */
@Path("Rental")
public class Rental {

  @Context
  private UriInfo context;
  /**
   * Creates a new instance of Rental
   */
  
  public Rental() {
       
  }

  /**
   * Retrieves representation of an instance of Services.Rental
   * @return an instance of java.lang.String
   */
  @GET
  @Produces("application/json")
  public String getJson() {
    //TODO return proper representation object
    throw new UnsupportedOperationException();
  }
  
  /****************
   * RENTALS TYPE *
   ****************/
  
  /*    
    Obtener los Rentals Types posibles
  **/
  @GET
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentalsTypes")
  public String getRentalsTypes() throws JSONException, IOException{  
    JSONObject json = new Rentals_DB_Handler().getData();
    return json.get("rentals_type").toString();
  }
  
  /*
    Obtener los Rentals por tipo 
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentalsTypesById")
  public RentalsType getRentalsTypesById(RequestParam params) throws JSONException, IOException{  
    
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals_type");
    RentalsType rentType = new RentalsType();
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id_type, (int)((JSONObject)json.get(i)).get("id"))){
        JSONObject obj = ((JSONObject)json.get(i));
        rentType = new RentalsType((int)obj.get("id"), (String)obj.get("description"), (double)Double.parseDouble(obj.get("tarifa").toString()) , (double)Double.parseDouble(obj.get("descuento").toString()));
        break;
      }        
    }    
    return rentType;
  }
  
  
  /***********
   * RENTALS *
   ***********/
  
  /*
    Obtener los Rentals posibles
  **/
  @GET
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentals")
  public String getRentals() throws JSONException, IOException{  
    JSONObject json = new Rentals_DB_Handler().getData();
    return json.get("rentals").toString();
  }
  
  /*
    Obtener los Rentals por tipo 
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentalsByType")
  public ArrayList<Rentals> getRentalsByType(RequestParam params) throws JSONException, IOException{  
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    ArrayList<Rentals> rentOutput = new ArrayList<>();
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id, (int)((JSONObject)json.get(i)).get("id_type"))){
        JSONObject obj = ((JSONObject)json.get(i));
        Rentals rent = new Rentals((int)obj.get("id"), (int)obj.get("id_parent"), (String)obj.get("description"), (int)obj.get("id_type"), (double)Double.parseDouble(obj.get("total").toString()), (int)obj.get("timing"));
        rentOutput.add(rent);
      }        
    }    
    return rentOutput;
  }
  
  /*
    Obtener los Rentals por tipo 
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentalsByParent")
  public ArrayList<Rentals> getRentalsByParent(RequestParam params) throws JSONException, IOException{  
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    ArrayList<Rentals> rentOutput = new ArrayList<>();
    JSONArray json_salida = new JSONArray();
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id_parent, (int)((JSONObject)json.get(i)).get("id_parent"))){
        JSONObject obj = ((JSONObject)json.get(i));
        Rentals rent = new Rentals((int)obj.get("id"), (int)obj.get("id_parent"), (String)obj.get("description"), (int)obj.get("id_type"), (double)Double.parseDouble(obj.get("total").toString()), (int)obj.get("timing"));
        rentOutput.add(rent);
      }        
    }    
    return rentOutput;
  }
  
  /*
    Crear nuevo Rental
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("createRentals")
  public String createRentals(RequestParam params) throws JSONException, IOException{  
    
    if (params.id_type_parent < 0 || params.id_type_parent > 4 ||
        params.id_type < 0 || params.id_type > 4) return new Response("Los id_types deben estar entre 1 y 4").toString();
    
    int id = new Rentals_DB_Handler().getData().getInt("autoIncrementId");
    RentalsType rentType = getRentalsTypesById(params); 
    double total = rentType.getTarifa()*params.timing;
    double descuento = total * (rentType.getDescuento()/100);
    
    //si es una renta familiar
    if(params.id_type_parent == 4 && params.id_parent < 0){
      int id_padre = id;
      //se crea el padre
      Rentals rent = new Rentals(
            id,params.id_parent, params.description,params.id_type_parent, 
            (total-descuento),params.timing);
      
      String str = new Rentals_DB_Handler().addRentals(rent).toString();
      id++;
      //se crea 
      rent = new Rentals(
            id,id_padre, params.description,params.id_type, 
            (total-descuento),params.timing);
      
      return new Rentals_DB_Handler().addRentals(rent).toString();
      
    }else{
      Rentals rent = new Rentals(
            id,params.id_parent, params.description,params.id_type, 
            (total-descuento),params.timing);
      
      if (params.id_parent > 0){
        
        String str = new Rentals_DB_Handler().addRentals(rent).toString();
        
        RequestParam par = new RequestParam();
        par.id_parent = params.id_parent;
        String str2 = updateRentalsFamiliar(par);
        return str;
      }else{
        return new Rentals_DB_Handler().addRentals(rent).toString();
      }
      
    
    }
    
    
  }
  
  /*
    Actualizar Rental
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("updateRentals")
  public String updateRentals(RequestParam params) throws JSONException, IOException{  
    RentalsType rentType = getRentalsTypesById(params);   
    double total = rentType.getTarifa()*params.timing;
    double descuento = total * (rentType.getDescuento()/100);
    Rentals rent = new Rentals(
            params.id,params.id_parent, params.description,params.id_type, 
            (total-descuento),params.timing);
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    int pos = -1;
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id, (int)((JSONObject)json.get(i)).get("id"))){
        pos = i; break;
      }        
    }   
      
    return new Rentals_DB_Handler().updateRentals(pos, rent).toString();
  }
  
  
  /*
    Actualizar Rental
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("updateRentalsFamiliar")
  public String updateRentalsFamiliar(RequestParam params) throws JSONException, IOException{  
    
    double total = 0;
    ArrayList<Rentals> rents = getRentalsByParent(params);
    for (Rentals rent : rents) {
      RequestParam par = new RequestParam();
      par.id_type = rent.id_type;
      RentalsType rentType = getRentalsTypesById(par); 
      total += (rentType.getTarifa()*rent.timing) - (total * (rentType.getDescuento()/100));
      
    }
    
    Rentals rent = new Rentals(
            params.id_parent,-1, "",4,total,0);
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    int pos = -1;
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id_parent, (int)((JSONObject)json.get(i)).get("id"))){
        pos = i; break;
      }        
    }   
      
    return new Rentals_DB_Handler().updateRentals(pos, rent).toString();
  }
  
  /*
    Obtener Rental by Id
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getRentalsById")
  public Rentals getRentalsById(RequestParam params) throws JSONException, IOException{  
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    Rentals rent = new Rentals();
    for (int i = 0; i < json.length(); i++) {
      if (Objects.equals(params.id, (int)((JSONObject)json.get(i)).get("id"))){
        JSONObject obj = ((JSONObject)json.get(i));
        rent = new Rentals((int)obj.get("id"), (int)obj.get("id_parent"), (String)obj.get("description"), (int)obj.get("id_type"), (double)Double.parseDouble(obj.get("total").toString()), (int)obj.get("timing"));
        break;
      }        
    }    
    return rent;
  }
  
  /*
    Eliminar Rental by Id
  **/
  @POST
  @Consumes("application/json")
  @Produces("application/json")
  @Path("deleteRentalsById")
  public String deleteRentalsById(RequestParam params) throws JSONException, IOException{  
    JSONArray json = (JSONArray) new Rentals_DB_Handler().getData().get("rentals");
    ArrayList<Integer> pos = new ArrayList<>();
    for (int i = json.length()-1; i >= 0; i--) {
      if (Objects.equals(params.id, (int)((JSONObject)json.get(i)).get("id")) ||
          Objects.equals(params.id, (int)((JSONObject)json.get(i)).get("id_parent"))    ){
        pos.add(i);
      }        
    }    
    
    return new Rentals_DB_Handler().deleteRentals(pos).toString();
  }
  
  
  
  /*@GET
  @Consumes("application/json")
  @Produces("application/json")
  @Path("getResponse")
  public Response getResponse(){      
    System.out.println(this.bd_rental.get(this.bd_rental.size()-1).getId());
    return new Response(this.bd_rental.toString());
  }*/
  

  /**
   * PUT method for updating or creating an instance of Rental
   * @param content representation for the resource
   * @return an HTTP response with content of the updated or created resource.
   */
  @PUT
  @Consumes("application/json")
  public void putJson(String content) {
  }
}
