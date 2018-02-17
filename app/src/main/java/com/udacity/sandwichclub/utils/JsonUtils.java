package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String KEY_NAME = "name";
    public static final String KEY_ALSO_KNOW_AS = "alsoKnownAs";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_PLACE_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject jsonSandwich = new JSONObject(json);
        JSONObject jsonSandwichName = jsonSandwich.optJSONObject(KEY_NAME);
        JSONArray alsoKnownNames = jsonSandwichName.optJSONArray(KEY_ALSO_KNOW_AS);
        JSONArray ingredients = jsonSandwich.optJSONArray(KEY_INGREDIENTS);
        List<String> nameList = new ArrayList<>();
        List<String> ingredientList = new ArrayList<>();
        for (int sandwichNameIterator = 0; sandwichNameIterator < alsoKnownNames.length(); sandwichNameIterator++) {
            nameList.add(alsoKnownNames.optString(sandwichNameIterator));
        }
        for (int ingredientsInterator = 0; ingredientsInterator < ingredients.length(); ingredientsInterator++) {
            ingredientList.add(ingredients.optString(ingredientsInterator));
        }
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(jsonSandwichName.optString(KEY_MAIN_NAME));
        sandwich.setAlsoKnownAs(nameList);
        sandwich.setPlaceOfOrigin(jsonSandwich.optString(KEY_PLACE_ORIGIN));
        sandwich.setDescription(jsonSandwich.optString(KEY_DESCRIPTION));
        sandwich.setImage(jsonSandwich.optString(KEY_IMAGE));
        sandwich.setIngredients(ingredientList);
        return sandwich;
    }
}
