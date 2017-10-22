/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import org.json.JSONObject;

/**
 *
 * @author JuanPe
 */
 public class Rentals {
  public int id;
  public int id_parent;    
  public String description;
  public int id_type;
  public double total;
  public int timing;

  public Rentals() { }  

  public Rentals(int id, int id_parent, String description, int id_type, double total, int timing) {
    this.id = id;
    this.id_parent = id_parent;
    this.description = description;
    this.id_type = id_type;
    this.total = total;
    this.timing = timing;
  }   
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId_parent() {
    return id_parent;
  }

  public void setId_parent(int id_parent) {
    this.id_parent = id_parent;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getId_type() {
    return id_type;
  }

  public void setId_type(int id_type) {
    this.id_type = id_type;
  }

  public double getTotal() {
    return total;
  }

  public void setTotal(double total) {
    this.total = total;
  }

  public int getTiming() {
    return timing;
  }

  public void setTiming(int timing) {
    this.timing = timing;
  }

  @Override
  public String toString() {
    return "{ \"id\" : " + id + ", \"id_parent\" : " + id_parent + ", \"description\" : " + description + ", \"id_type\" : " + id_type + ", \"total\" : " + total + ", \"timing\" : " + timing + '}';
  }
  
  public JSONObject toJSON() {
    JSONObject main = new JSONObject();
    main.put("id", id);
    main.put("id_parent", id_parent);
    main.put("description",description);
    main.put("id_type", id_type);
    main.put("total",total);
    main.put("timing", timing);
    
    
    return main;
  }
 
}
