package com.ramshasaeed.sandwichclub.utils;


import com.ramshasaeed.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();

        try {
            JSONObject sandwichjson =  new JSONObject(json);
            JSONObject mainnameJson = sandwichjson.getJSONObject("name");
            sandwich.setMainName(mainnameJson.getString("mainName"));
            JSONArray alsoKnownAs = mainnameJson.getJSONArray("alsoKnownAs");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length();i++){
                list.add(alsoKnownAs.getString(i));
            }
            sandwich.setAlsoKnownAs(list);
            sandwich.setPlaceOfOrigin(sandwichjson.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichjson.getString("description"));
            sandwich.setImage(sandwichjson.getString("image"));
            JSONArray ingredientsArray = sandwichjson.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i<ingredientsArray.length();i++){
                ingredientsList.add(ingredientsArray.getString(i));
            }
            sandwich.setIngredients(ingredientsList);
            sandwich.setImage(sandwichjson.getString("image"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }
}
