package com.qrgenerator.cardmatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Card> cards = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = findViewById(R.id.cards);
        final ToggleButton stopStart = findViewById(R.id.stop_start);
        Button reset = findViewById(R.id.reset);

        //retrieve the images and sort them randomly into an arraylist
        retrieveCards();
        shuffleCards();
        final CardAdapter adapter = new CardAdapter(MainActivity.this,R.layout.card_layout,cards);
        gridView.setAdapter(adapter);

        stopStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!adapter.getWon()) {
                    ToggleButton t = (ToggleButton) v;
                    adapter.setPlaying(t.isChecked());
                }
                else {
                    stopStart.setChecked(false);
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setPlaying(false);
                adapter.setWon(false);
                stopStart.setChecked(false);
                cards.clear();
                retrieveCards();
                shuffleCards();
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setWonListener(new WonListener() {
            @Override
            public void onWon() {
                stopStart.setChecked(false);
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Congratulations...You Won!!!")
                        .create().show();
            }
        });
    }

    public void retrieveCards() {
        cards.add(new Card(R.drawable.ace_clubs,1));
        cards.add(new Card(R.drawable.two_clubs,2));
        cards.add(new Card(R.drawable.three_clubs,3));
        cards.add(new Card(R.drawable.four_clubs,4));
        cards.add(new Card(R.drawable.five_clubs,5));
        cards.add(new Card(R.drawable.six_clubs,6));
        cards.add(new Card(R.drawable.seven_clubs,7));
        cards.add(new Card(R.drawable.eight_clubs,8));
        cards.add(new Card(R.drawable.nine_clubs,9));
        cards.add(new Card(R.drawable.ten_clubs,10));
        cards.add(new Card(R.drawable.jack_clubs,11));
        cards.add(new Card(R.drawable.queen_clubs,12));
        cards.add(new Card(R.drawable.king_clubs,13));

        cards.add(new Card(R.drawable.ace_hearts,1));
        cards.add(new Card(R.drawable.two_hearts,2));
        cards.add(new Card(R.drawable.three_hearts,3));
        cards.add(new Card(R.drawable.four_hearts,4));
        cards.add(new Card(R.drawable.five_hearts,5));
        cards.add(new Card(R.drawable.six_hearts,6));
        cards.add(new Card(R.drawable.seven_hearts,7));
        cards.add(new Card(R.drawable.eight_hearts,8));
        cards.add(new Card(R.drawable.nine_hearts,9));
        cards.add(new Card(R.drawable.ten_hearts,10));
        cards.add(new Card(R.drawable.jack_hearts,11));
        cards.add(new Card(R.drawable.queen_hearts,12));
        cards.add(new Card(R.drawable.king_hearts,13));

        cards.add(new Card(R.drawable.ace_spades,1));
        cards.add(new Card(R.drawable.two_spades,2));
        cards.add(new Card(R.drawable.three_spades,3));
        cards.add(new Card(R.drawable.four_spades,4));
        cards.add(new Card(R.drawable.five_spades,5));
        cards.add(new Card(R.drawable.six_spades,6));
        cards.add(new Card(R.drawable.seven_spades,7));
        cards.add(new Card(R.drawable.eight_spades,8));
        cards.add(new Card(R.drawable.nine_spades,9));
        cards.add(new Card(R.drawable.ten_spades,10));
        cards.add(new Card(R.drawable.jack_spades,11));
        cards.add(new Card(R.drawable.queen_spades,12));
        cards.add(new Card(R.drawable.king_spades,13));

        cards.add(new Card(R.drawable.ace_diamonds,1));
        cards.add(new Card(R.drawable.two_diamonds,2));
        cards.add(new Card(R.drawable.three_diamonds,3));
        cards.add(new Card(R.drawable.four_diamonds,4));
        cards.add(new Card(R.drawable.five_diamonds,5));
        cards.add(new Card(R.drawable.six_diamonds,6));
        cards.add(new Card(R.drawable.seven_diamonds,7));
        cards.add(new Card(R.drawable.eight_diamonds,8));
        cards.add(new Card(R.drawable.nine_diamonds,9));
        cards.add(new Card(R.drawable.ten_diamonds,10));
        cards.add(new Card(R.drawable.jack_diamonds,11));
        cards.add(new Card(R.drawable.queen_diamonds,12));
        cards.add(new Card(R.drawable.king_diamonds,13));
    }

    public void shuffleCards() {
        int size = cards.size();
        for (int i=0; i<size; i++) {
            Card current = cards.get(i);
            int random = (int) Math.floor(Math.random() * 52);
            Card replacement = cards.get(random);
            cards.set(i,replacement);
            cards.set(random,current);
        }
    }

}
