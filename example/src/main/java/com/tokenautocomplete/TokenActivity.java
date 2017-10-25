package com.tokenautocomplete;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.tokenautocomplete.model.Chip;
import com.tokenautocomplete.model.ChipInterface;

import java.util.List;
import java.util.Random;

/**
 * Demo or sample activity
 */
public class TokenActivity extends Activity implements TokenCompleteTextView.TokenListener<ChipInterface> {
    // our completion view which extends TokenCompleteTextView
    ContactsCompletionView completionView;
    // the list which should be shown as suggestion
    ChipInterface[] people;
    ArrayAdapter<ChipInterface> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = new ChipInterface[] {
                new Chip(this, "first", "firstInfo", "First Name"),
                new Chip(this, "second", "secondInfo", "Second Name"),
                new Chip(this, "third", "thirdInfo", "Third Name"),
                new Chip(this, "fourth", "fourthInfo", "Fourth Name"),
                new Chip(this, "fifth", "fifthInfo", "Fifth Name"),
                new Chip(this, "sixth", "sixInfo", "Sixth Name"),
        };

        adapter = new FilteredArrayAdapter<ChipInterface>(this, R.layout.person_layout, people) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    convertView = l.inflate(R.layout.person_layout, parent, false);
                }

                ChipInterface p = getItem(position);
                ((TextView)convertView.findViewById(R.id.name)).setText(p.getDisplayName());
                ((TextView)convertView.findViewById(R.id.email)).setText(p.getEmailAddress());

                return convertView;
            }

            @Override
            protected boolean keepObject(ChipInterface person, String mask) {
                mask = mask.toLowerCase();
                return person.getDisplayName().toLowerCase().startsWith(mask)
                        || person.getEmailAddress().toLowerCase().startsWith(mask);
            }
        };

        completionView = (ContactsCompletionView)findViewById(R.id.searchView);
        completionView.setAdapter(adapter);
        completionView.setTokenListener(this);
        completionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select);

        Button removeButton = (Button)findViewById(R.id.removeButton);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<ChipInterface> people = completionView.getObjects();
                if (people.size() > 0) {
                    completionView.removeObject(people.get(people.size() - 1));
                }
            }
        });

        Button addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                completionView.addObject(people[rand.nextInt(people.length)]);
            }
        });
    }

    private void updateTokenConfirmation() {
        StringBuilder sb = new StringBuilder("Current tokens:\n");
        for (Object token: completionView.getObjects()) {
            sb.append(token.toString());
            sb.append("\n");
        }

        ((TextView)findViewById(R.id.tokens)).setText(sb);
    }


    @Override
    public void onTokenAdded(ChipInterface token) {
        ((TextView)findViewById(R.id.lastEvent)).setText("Added: " + token.getEmailAddress());
        updateTokenConfirmation();
    }

    @Override
    public void onTokenRemoved(ChipInterface token) {
        ((TextView)findViewById(R.id.lastEvent)).setText("Removed: " + token.getEmailAddress());
        updateTokenConfirmation();
    }

    @Override
    public void onTextChanged(String newText) {

    }
}
