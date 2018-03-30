package com.example.user.games4days;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.laborator1.R;

public class StorePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_page);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#c7c8e4"));

        //preiau numele si imaginea jocului
        Intent intent = getIntent();
        String gameText = intent.getStringExtra("gameText");
        String gameImage = intent.getStringExtra("gameImage");

        //pun informatiile pe ecran
        //pun textul
        TextView gameTextView = findViewById(R.id.gameTextView);
        gameTextView.setText(gameText);

        //pun (prima) imagine
        ImageView gameImageView = findViewById(R.id.gameImageView);
        int id = getResources().getIdentifier(gameImage, "drawable", getPackageName());
        gameImageView.setImageResource(id);

        //redimensionez ImageView in functie de TextView si setez imaginea potrivita
        gameTextView.measure(0, 0);
        LinearLayout.LayoutParams lp_imageView;

        //modific dimensiunea in functie de lansdcape, si adaug alta imagine daca e cazul
        int orientation = getResources().getConfiguration().orientation;
        switch(orientation)
        {
            case Configuration.ORIENTATION_PORTRAIT:
                lp_imageView = new LinearLayout.LayoutParams(200, gameTextView.getMeasuredHeight() + 100);
                gameImageView.setLayoutParams(lp_imageView);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                lp_imageView = new LinearLayout.LayoutParams(200, gameTextView.getMeasuredHeight() + 50);
                gameImageView.setLayoutParams(lp_imageView);

                ImageView gameImageView2 = findViewById(R.id.gameImageView2);
                gameImageView2.setImageResource(id);
                gameImageView2.setLayoutParams(lp_imageView);
                break;
            default:
                break;
        }
    }

    //creez meniul
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //creez functionalitatea pentru optiunile din meniu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                break;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "This is my application!",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.help:
                Toast.makeText(getApplicationContext(), "Press exit to exit :)",
                        Toast.LENGTH_LONG).show();
                break;
            case R.id.exit:
                this.finishAffinity();
                break;
            default:
                return false;
        }
        return true;
    }
}
