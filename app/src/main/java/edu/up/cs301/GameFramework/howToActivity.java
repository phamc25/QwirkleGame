package edu.up.cs301.GameFramework;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.up.cs301.qwirklegame.R;

public class howToActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_how_to);

        Button continueButton = findViewById(R.id.how_to_play_continue);
        if (continueButton != null) {
            continueButton.setOnClickListener(v -> finish()); // Return to the previous activity
        }
    }
}

/**
 * External Citation
 *
 * Problem: getting the how-to page to work
 * Source: https://www.youtube.com/watch?v=xJOauqxIn_k, Chatgpt,
 * https://stackoverflow.com/questions/7991393/how-to-switch-between-screens, https://www.youtube.com/watch?v=8icK8L9Qq6o&t=928s
 *
 * Date: 12/4/24
 */