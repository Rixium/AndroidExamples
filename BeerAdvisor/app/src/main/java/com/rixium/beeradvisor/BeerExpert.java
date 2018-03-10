package com.rixium.beeradvisor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Potte on 10/03/2018.
 */

public class BeerExpert {

    public List<String> getBrands(String selectedColour) {
        // Create a new list to store our brands.
        List<String> brands = new ArrayList<String>();

        // Depending on our selected colour.
        switch(selectedColour) {
            //Populate the brand list accordingly.
            case "Amber":
                brands.add("Jack Amber");
                brands.add("Red Moose");
                break;
            default:
                brands.add("Jail Pale Ale");
                brands.add("Gout Stout");
                break;
        }

        // Finally, return the created list.
        return brands;
    }

}
