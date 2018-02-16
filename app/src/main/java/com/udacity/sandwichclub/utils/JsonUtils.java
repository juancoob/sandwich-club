package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonSandwich = new JSONObject(json);
        JSONObject jsonSandwichName = jsonSandwich.getJSONObject("name");
        JSONArray alsoKnownNames = jsonSandwichName.getJSONArray("alsoKnownAs");
        JSONArray ingredients = jsonSandwich.getJSONArray("ingredients");
        List<String> nameList = new ArrayList<>();
        List<String> ingredientList = new ArrayList<>();
        for (int sandwichNameIterator = 0; sandwichNameIterator < alsoKnownNames.length(); sandwichNameIterator++) {
            nameList.add(String.valueOf(alsoKnownNames.get(sandwichNameIterator)));
        }
        for (int ingredientsInterator = 0; ingredientsInterator < ingredients.length(); ingredientsInterator++) {
            ingredientList.add(String.valueOf(ingredients.get(ingredientsInterator)));
        }
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(jsonSandwichName.getString("mainName"));
        sandwich.setAlsoKnownAs(nameList);
        sandwich.setPlaceOfOrigin(jsonSandwich.getString("placeOfOrigin"));
        sandwich.setDescription(jsonSandwich.getString("description"));
        sandwich.setImage(jsonSandwich.getString("image"));
        sandwich.setIngredients(ingredientList);
        return sandwich;
    }
}
