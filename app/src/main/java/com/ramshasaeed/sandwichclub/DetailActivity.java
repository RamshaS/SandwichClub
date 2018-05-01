package com.ramshasaeed.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramshasaeed.sandwichclub.model.Sandwich;
import com.ramshasaeed.sandwichclub.utils.JsonUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private TextView mTvDescription, mTvIngredients, mTvAlsoknown, mTvOrigin;
    private RelativeLayout mRlDescription, mRlIngredients, mRlAlsoKnown, mRlOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mTvDescription = findViewById(R.id.description_tv);
        mTvIngredients = findViewById(R.id.ingredients_tv);
        mTvAlsoknown = findViewById(R.id.also_known_tv);
        mTvOrigin = findViewById(R.id.origin_tv);

        mRlAlsoKnown = findViewById(R.id.rl_alsoknownas);
        mRlDescription = findViewById(R.id.rl_description);
        mRlIngredients = findViewById(R.id.rl_ingredients);
        mRlOrigin = findViewById(R.id.rl_placeoforigin);


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
                .error(android.R.drawable.ic_dialog_alert)
                .placeholder(android.R.drawable.progress_indeterminate_horizontal)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!sandwich.getDescription().isEmpty()) {
            show(R.id.rl_description);
            mTvDescription.setText(sandwich.getDescription());

        } else {
            hide(R.id.rl_description);
        }
        if (!sandwich.getPlaceOfOrigin().isEmpty()) {
            //this is a new branch
            show(R.id.rl_placeoforigin);
            mTvOrigin.setText(sandwich.getPlaceOfOrigin());
        } else {
            hide(R.id.rl_placeoforigin);
        }
        if (sandwich.getIngredients().size() != 0) {
            show(R.id.rl_ingredients);
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                mTvIngredients.append(sandwich.getIngredients().get(i));
                if(i != sandwich.getIngredients().size()-1){
                    mTvIngredients.append(", ");
                }
            }
        } else {
            hide(R.id.rl_ingredients);
        }
        if (sandwich.getAlsoKnownAs().size() != 0) {
            show(R.id.rl_alsoknownas);
            for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
                mTvAlsoknown.append(sandwich.getAlsoKnownAs().get(i));
                if(i != sandwich.getAlsoKnownAs().size()-1){
                    mTvAlsoknown.append(", ");
                }
            }
        } else {
            hide(R.id.rl_alsoknownas);
        }
    }

    private void show(int rl) {
        switch (rl){
            case R.id.rl_alsoknownas:
                mRlAlsoKnown.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_description:
                mRlDescription.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_ingredients:
                mRlIngredients.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_placeoforigin:
                mRlOrigin.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void hide(int mRl) {
        switch (mRl){
            case R.id.rl_alsoknownas:
                mRlAlsoKnown.setVisibility(View.GONE);
                break;
            case R.id.rl_description:
                mRlDescription.setVisibility(View.GONE);
                break;
            case R.id.rl_ingredients:
                mRlIngredients.setVisibility(View.GONE);
                break;
            case R.id.rl_placeoforigin:
                mRlOrigin.setVisibility(View.GONE);
                break;
        }
    }
}
