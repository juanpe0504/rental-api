/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author JuanPe
 */
public class RequestParam {
    @XmlElement public int id;
    @XmlElement public int id_type;
    @XmlElement public String description;
    @XmlElement public int timing;
    @XmlElement public int id_parent;
    @XmlElement public int id_type_parent;
    
}
