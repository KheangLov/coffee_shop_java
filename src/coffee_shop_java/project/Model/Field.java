/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coffee_shop_java.project.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ASUS
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Field {
    private String name;
    private double qty, price, total;
    private int waiting_number;
}
