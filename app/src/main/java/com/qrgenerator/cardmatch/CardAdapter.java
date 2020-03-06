package com.qrgenerator.cardmatch;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CardAdapter extends ArrayAdapter<Card> {
    private ArrayList<Card> cards;
    private int[] flipped = {-1,-1};
    private int nFlipped = 0;
    private Context context;
    private int resource;
    private boolean playing = false;
    private boolean won = false;
    private int pairs = 0;
    private WonListener wonListener;

    public CardAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Card> cards) {
        super(context, resource, cards);
        this.cards = cards;
        this.context = context;
        this.resource = resource;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean getWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }

    public void setWonListener(WonListener listener) {
        wonListener = listener;
    }

    @Override
    public int getCount() {
        return cards.size();
    }

    @NonNull
    @Override
    public Card getItem(int position) {
        return cards.get(position);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,parent,false);
        }
        ImageView imageView = convertView.findViewById(R.id.grid_card);
        final Card currentCard = getItem(position);
        boolean matched = currentCard.isMatched();
        if (flipped[0] == position || flipped[1] == position) {
            imageView.setImageResource(currentCard.image);
        }
        else if (matched) {
            imageView.setImageResource(R.drawable.blank);
        }
        else {
            imageView.setImageResource(R.drawable.back);
        }

        if (!matched) {
            imageView.setOnClickListener(getCardFlipListener(position));
        }
        else {
            imageView.setOnClickListener(null);
        }

        return convertView;
    }

    public View.OnClickListener getCardFlipListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFlipped = flipped[0] == position ||
                        flipped[1] == position;
                if (playing) {
                    if (nFlipped == 0) {
                        flipped[0] = position;
                        nFlipped++;
                        notifyDataSetChanged();
                    }
                    else if (!isFlipped && nFlipped == 1) {
                        flipSecondCard(position);
                    }
                }
                else {
                    Toast.makeText(context,"Sorry, you're not currently playing",
                            Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public void flipSecondCard(int position) {
        flipped[1] = position;
        nFlipped++;
        notifyDataSetChanged();
        Thread flipThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    compareCards();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        flipThread.start();
    }

    public void compareCards() {
            Card first = getItem(flipped[0]);
            Card second = getItem(flipped[1]);
            if (first.faceValue == second.faceValue) {
                pairs++;
                first.setMatched(true);
                second.setMatched(true);
                if (pairs == 26) {
                    won = true;
                    playing = false;
                }
            }

            flipped[0] = -1;
            flipped[1] = -1;
            nFlipped = 0;
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                    if (won && wonListener != null) {
                        wonListener.onWon();
                    }
                }
            });
    }
}
