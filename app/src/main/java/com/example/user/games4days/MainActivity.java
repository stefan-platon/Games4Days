package com.example.user.games4days;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    String imageToSaveName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#c7c8e4"));

        SharedPreferences pref = getSharedPreferences("my_prefs", 0);
        boolean autoRotateScreen = pref.getBoolean("auto_rotate_screen", true);
        String luminosity = pref.getString("get_luminosity", "50");
        Toast.makeText(MainActivity.this, "Rotation: " + autoRotateScreen + "\nLuminosity: " + luminosity,
                Toast.LENGTH_SHORT).show();

        //vectorul de produse
        String[] produse = new String[] { "Bioshock",
                "Bioshock 2",
                "Bioshock Infinite",
                "Castlevania: Lords of Shadow",
                "Castlevania: Lords of Shadow 2",
                "Dark Souls",
                "Dark Souls 2",
                "Dark Souls 3",
                "Dead Space",
                "Dead Space 2",
                "Deus Ex: Human Revolution",
                "Deus Ex: Mankind Divided",
                "Hollow Knight",
                "Metro 2033",
                "Metro: Last Light",
                "Middle Earth: Shadow of Mordor",
                "Nier: Automata",
                "Nier: Gestalt",
                "Ori and the Blind Forest",
                "Shovel Knight"
        };

        //populez ListView cu produse
        ArrayAdapter<String> itemsAdapter =
                new ArrayAdapter<>(this, R.layout.text_view, produse) ;
        final ListView listView = findViewById(R.id.list_view);
        listView.setAdapter(itemsAdapter);

        final TextView text = findViewById(R.id.text_view_list);
        final ImageView gameImage = findViewById(R.id.gameImageView);

        //cand selectez un element din lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //setez textul din TextView
                int price = 30;
                text.setText("Price: " + price * (i + 1) + '\n' +
                            "Genre: Action" + i + '\n' +
                            "Release date: " + i + ".11.11");

                //redimensionez ListView in functie de TextView
                ConstraintLayout.LayoutParams lp_listView = (ConstraintLayout.LayoutParams) listView.getLayoutParams();
                text.measure(0, 0);
                lp_listView.setMargins(0, lp_listView.topMargin,0, text.getMeasuredHeight());
                listView.setLayoutParams(lp_listView);

                //redimensionez ImageView in functie de TextView si setez imaginea potrivita
                text.measure(0, 0);
                LinearLayout.LayoutParams lp_imageView;
                gameImage.setImageResource(R.drawable.deus_ex_human_revolution);
                //salvez imaginea pentru reincarcare
                imageToSaveName = "deus_ex_human_revolution";

                //verific orientarea ecranului si afisez al doilea ImageView daca e cazul
                int orientation = getResources().getConfiguration().orientation;
                switch(orientation)
                {
                    case Configuration.ORIENTATION_PORTRAIT:
                        lp_imageView = new LinearLayout.LayoutParams(150, text.getMeasuredHeight());
                        gameImage.setLayoutParams(lp_imageView);
                        break;
                    case Configuration.ORIENTATION_LANDSCAPE:
                        lp_imageView = new LinearLayout.LayoutParams(150, text.getMeasuredHeight());

                        final ImageView gameImage2 = findViewById(R.id.gameImageView2);
                        gameImage2.setLayoutParams(lp_imageView);
                        gameImage2.setImageResource(R.drawable.deus_ex_human_revolution);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    protected void saveFileToInternalStorage() {
        FileOutputStream fos;
        try {
            fos = openFileOutput("internal_file.txt", Context.MODE_PRIVATE);
            Random r = new Random();
            int randNr = r.nextInt(80 - 65) + 65;
            fos.write(String.valueOf(randNr).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void loadFileFromInternalStorage() {
        FileInputStream fis = null;
        try {
            fis = this.openFileInput("internal_file.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if(fis != null) {
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this, sb, Toast.LENGTH_LONG).show();
        }
    }

    public void goToStorePage(View view) {
        //verific daca a fost selectat un element
        if (imageToSaveName != null) {
            Intent intent = new Intent(this, StorePageActivity.class);

            final TextView text = findViewById(R.id.text_view_list);
            //textul de trimis (numele jocului)
            CharSequence gameText = text.getText();
            intent.putExtra("gameText", gameText);

            //imaginea de trimis (imaginea jocului)
            String gameImage = imageToSaveName;
            intent.putExtra("gameImage", gameImage);

            startActivity(intent);
        }
    }

    public void insertItem(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.main_alert_box);
        dialog.setTitle("Hello my friend !");

        Button cancelButton = dialog.findViewById(R.id.mainAlertBoxButtonCancel);
        // if button is clicked, close the custom dialog
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button submitButton = dialog.findViewById(R.id.mainAlertBoxButtonSubmit);
        // if button is clicked, insert a new item
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textFromUser = dialog.findViewById(R.id.mainAlertBoxGetText);
                if (textFromUser.getText().toString().equals("ok"))
                    Toast.makeText(MainActivity.this, "Totul e ok !",
                            Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Ceva nu e ok !",
                            Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //cand nu ating ListView, revin la starea initiala
    @SuppressLint("SetTextI18n")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //resetez TextView
        TextView text = findViewById(R.id.text_view_list);
        text.setText("Additional info");
        text.setPadding(0, 0, 0, 0);

        //resestez ListView
        ListView listView = findViewById(R.id.list_view);
        ConstraintLayout.LayoutParams lp_listView = (ConstraintLayout.LayoutParams) listView.getLayoutParams();
        text.measure(0, 0);
        lp_listView.setMargins(0, lp_listView.topMargin,0, text.getMeasuredHeight());
        listView.setLayoutParams(lp_listView);

        //resestez ImageView
        ImageView gameImage = findViewById(R.id.gameImageView);
        LinearLayout.LayoutParams lp_imageView = new LinearLayout.LayoutParams(0, 0);
        gameImage.setLayoutParams(lp_imageView);
        gameImage.setImageResource(0);
        imageToSaveName = null;

        //verific orientarea ecranului si resetez al doilea ImageView daca e cazul
        int orientation = getResources().getConfiguration().orientation;
        switch(orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
                ImageView gameImage2 = findViewById(R.id.gameImageView2);
                gameImage2.setLayoutParams(lp_imageView);
                gameImage2.setImageResource(0);
                break;
            default:
                break;
        }
        return false;
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
                break;
            case R.id.settings:
                startActivity(new Intent(this, PrefActivity.class));
                break;
            case R.id.sensors:
                startActivity(new Intent(this, SensorsActivity.class));
                break;
            case R.id.saveToFile:
                saveFileToInternalStorage();
                break;
            case R.id.loadFromFile:
                loadFileFromInternalStorage();
                break;
            case R.id.about:
                Toast.makeText(getApplicationContext(), "This is my application!",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.help:
                Toast.makeText(getApplicationContext(), "Press exit to exit :)",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                this.finishAffinity();
                break;
            default:
                return false;
        }
        return true;
    }

    //salvez informatiile importante
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        final TextView text = findViewById(R.id.text_view_list);

        //salvez textul
        CharSequence textToSave = text.getText();
        savedInstanceState.putCharSequence("savedText", textToSave);

        //salvez imaginea daca exista
        if (imageToSaveName != null) {
            String imageToSave = imageToSaveName;
            savedInstanceState.putString("imageResource", imageToSave);
        }
    }

    //incarc informatiile importante
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        final TextView text = findViewById(R.id.text_view_list);
        final ImageView gameImage = findViewById(R.id.gameImageView);

        //pun textul in TextView
        CharSequence userText = savedInstanceState.getCharSequence("savedText");
        text.setText(userText);

        //repun imaginea in memorie (in caz ca exista)
        imageToSaveName = savedInstanceState.getString("imageResource");
        if (imageToSaveName != null) {
            int id = getResources().getIdentifier(imageToSaveName, "drawable", getPackageName());
            //pun imaginea in ImageView si redimensionez
            gameImage.setImageResource(id);
            text.measure(0, 0);
            LinearLayout.LayoutParams lp_imageView = new LinearLayout.LayoutParams(150, text.getMeasuredHeight());
            gameImage.setLayoutParams(lp_imageView);

            //verific orientarea ecranului si afisez al doilea ImageView daca e cazul
            int orientation = getResources().getConfiguration().orientation;
            switch(orientation)
            {
                case Configuration.ORIENTATION_LANDSCAPE:
                    final ImageView gameImage2 = findViewById(R.id.gameImageView2);
                    gameImage2.setImageResource(id);
                    gameImage2.setLayoutParams(lp_imageView);
                    break;
                default:
                    break;
            }
        }

        //redimensionez ListView in functie de TextView
        final ListView listView = findViewById(R.id.list_view);
        ConstraintLayout.LayoutParams lp_listView = (ConstraintLayout.LayoutParams) listView.getLayoutParams();
        text.measure(0, 0);
        lp_listView.setMargins(0, lp_listView.topMargin,0, text.getMeasuredHeight());
        listView.setLayoutParams(lp_listView);
    }
}
