/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

/**
 *
 * @author JuanPe
 */
public class RentalsType {
   int id;
   String description;
   double  tarifa;
   double  descuento;
   
   public RentalsType(){}
   
  public RentalsType(int id, String description, double tarifa, double descuento) {
    this.id = id;
    this.description = description;
    this.tarifa = tarifa;
    this.descuento = descuento;
  }
  
  public void set(){
  
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public double getTarifa() {
    return tarifa;
  }

  public void setTarifa(double tarifa) {
    this.tarifa = tarifa;
  }

  public double getDescuento() {
    return descuento;
  }

  public void setDescuento(double descuento) {
    this.descuento = descuento;
  }
  
  
   
   
}
