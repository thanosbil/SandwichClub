package com.example.thanos.sandwichclub.utils;

import com.example.thanos.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class JsonUtils {


    public static Sandwich parseSandwichJson(String json) {

        Sandwich sandwich = new Sandwich();
        List<String> alsoKnown = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();

        try{
            JSONObject jsonSandwich = new JSONObject(json);
            JSONObject jsonName = new JSONObject(jsonSandwich.getString("name"));



            JSONArray jsonAlsoKnown = jsonName.getJSONArray("alsoKnownAs");
            for(int i=0; i<jsonAlsoKnown.length(); i++){
                alsoKnown.add(i, jsonAlsoKnown.getString(i));
            }

            JSONArray jsonIngredients = jsonSandwich.getJSONArray("ingredients");
            for(int i=0; i<jsonIngredients.length(); i++){
                ingredients.add(i, jsonIngredients.getString(i));
            }

            sandwich.setMainName(jsonName.getString("mainName"));
            sandwich.setAlsoKnownAs(alsoKnown);
            sandwich.setIngredients(ingredients);
            sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));
            sandwich.setDescription(jsonSandwich.getString("description"));
            sandwich.setImage(jsonSandwich.getString("image"));

        }catch (JSONException ex){
            ex.printStackTrace();
        }
        return sandwich;
    }

}
