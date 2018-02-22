package com.example.thanos.sandwichclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.example.thanos.sandwichclub.model.Sandwich;
import com.example.thanos.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView alsoKnownTextView;
    TextView originTextView;
    TextView  descriptionTextView;
    TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownTextView = findViewById(R.id.also_known_tv);
        originTextView = findViewById(R.id.origin_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
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

    private void populateUI(Sandwich s) {
        String separator = ", ";
        List<String> alsoKnown = s.getAlsoKnownAs();
        List<String> ingredients = s.getIngredients();

        StringBuilder allKnownNames = new StringBuilder();

        for (int i=0; i<alsoKnown.size(); i++){
            allKnownNames.append(alsoKnown.get(i));

            if(i < (alsoKnown.size() - 1))
                allKnownNames.append(separator);
        }

        StringBuilder allIngredients = new StringBuilder();

        for (int i=0; i < ingredients.size(); i++){
            allIngredients.append(ingredients.get(i));

            if(i < (ingredients.size() - 1))
                allIngredients.append(separator);
        }

        descriptionTextView.setText(s.getDescription());
        ingredientsTextView.setText(allIngredients);

        if((allKnownNames.toString()).trim().length() > 0){
            alsoKnownTextView.setText(allKnownNames);
        }else {
            alsoKnownTextView.setText("-");
        }

        if (s.getPlaceOfOrigin().trim().length() > 0){
            originTextView.setText(s.getPlaceOfOrigin());
        }else {
            originTextView.setText("-");
        }

    }
}
