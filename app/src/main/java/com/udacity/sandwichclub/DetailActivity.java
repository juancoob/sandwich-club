package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView alsoKnownAs, ingredients, placeOfOrigin, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        alsoKnownAs = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        placeOfOrigin = findViewById(R.id.origin_tv);
        description = findViewById(R.id.description_tv);
        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "We had a problem parsing the JSON", Toast.LENGTH_SHORT).show();
            Log.d("JSON", "We had a problem parsing the JSON: " + e);
        }

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        StringBuilder alsoKnownAsBuilder = new StringBuilder();
        StringBuilder ingredientsBuilder = new StringBuilder();
        for(int nameIterator = 0; nameIterator < sandwich.getAlsoKnownAs().size(); nameIterator++) {
            alsoKnownAsBuilder.append(sandwich.getAlsoKnownAs().get(nameIterator));
            // Only add comma if we don't reach the end
            if(nameIterator + 1 != sandwich.getAlsoKnownAs().size()) {
                alsoKnownAsBuilder.append(", ");
            }
        }
        for(int ingredientIterator = 0; ingredientIterator < sandwich.getIngredients().size(); ingredientIterator++) {
            ingredientsBuilder.append(sandwich.getIngredients().get(ingredientIterator));
            if(ingredientIterator + 1 != sandwich.getIngredients().size()) {
                ingredientsBuilder.append(", ");
            }
        }
        alsoKnownAs.setText(alsoKnownAsBuilder.toString().isEmpty() ? "--" : alsoKnownAsBuilder.toString());
        ingredients.setText(ingredientsBuilder.toString().isEmpty() ? "--" : ingredientsBuilder.toString());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin().isEmpty() ? "--" : sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription().isEmpty() ? "--" : sandwich.getDescription());
    }
}
