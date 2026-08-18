package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText[] players = new EditText[7];
    EditText[] b = new EditText[7];
    TextView[] c = new TextView[7];

    EditText d1;
    TextView e1;

    Button themeButton;
    Button resetButton;

    LinearLayout mainLayout;
    TextView tabTitle;

    SharedPreferences preferences;

    boolean darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(
                "app_settings",
                Context.MODE_PRIVATE
        );

        darkMode = preferences.getBoolean(
                "dark_mode",
                false
        );

        initializeViews();

        createTableBorders();

        setupListeners();

        applyTheme();

        calculate();
    }


    private void initializeViews() {

        mainLayout = findViewById(R.id.mainLayout);
        tabTitle = findViewById(R.id.tabTitle);

        themeButton = findViewById(R.id.themeButton);
        resetButton = findViewById(R.id.resetButton);

        d1 = findViewById(R.id.d1);
        e1 = findViewById(R.id.e1);

        players[0] = findViewById(R.id.player1);
        players[1] = findViewById(R.id.player2);
        players[2] = findViewById(R.id.player3);
        players[3] = findViewById(R.id.player4);
        players[4] = findViewById(R.id.player5);
        players[5] = findViewById(R.id.player6);
        players[6] = findViewById(R.id.player7);

        b[0] = findViewById(R.id.b1);
        b[1] = findViewById(R.id.b2);
        b[2] = findViewById(R.id.b3);
        b[3] = findViewById(R.id.b4);
        b[4] = findViewById(R.id.b5);
        b[5] = findViewById(R.id.b6);
        b[6] = findViewById(R.id.b7);

        c[0] = findViewById(R.id.c1);
        c[1] = findViewById(R.id.c2);
        c[2] = findViewById(R.id.c3);
        c[3] = findViewById(R.id.c4);
        c[4] = findViewById(R.id.c5);
        c[5] = findViewById(R.id.c6);
        c[6] = findViewById(R.id.c7);
    }


    private void setupListeners() {

        TextWatcher calculationWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(
                    CharSequence s,
                    int start,
                    int count,
                    int after) {
            }

            @Override
            public void onTextChanged(
                    CharSequence s,
                    int start,
                    int before,
                    int count) {

                calculate();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };


        for (EditText editText : b) {

            editText.addTextChangedListener(
                    calculationWatcher
            );
        }


        d1.addTextChangedListener(
                calculationWatcher
        );


        themeButton.setOnClickListener(
                v -> toggleTheme()
        );


        resetButton.setOnClickListener(
                v -> resetValues()
        );
    }


    private int getNumber(EditText editText) {

        String value = editText
                .getText()
                .toString()
                .trim();

        if (value.isEmpty()) {
            return 0;
        }

        try {

            return Integer.parseInt(value);

        } catch (NumberFormatException e) {

            return 0;
        }
    }


    private int trunc(double value) {

        return (int) value;
    }


    private void calculate() {

        int[] B = new int[7];

        for (int i = 0; i < 7; i++) {

            B[i] = getNumber(b[i]);
        }


        int D1 = getNumber(d1);


        // E1 = SUM(B1:B7)

        int E1 = 0;

        for (int value : B) {

            E1 += value;
        }

        e1.setText(String.valueOf(E1));


        int[] C = new int[7];


        // C1
        //
        // =IF(B1=0,0,TRUNC((B1/E1)*D1,0))

        if (B[0] == 0 || E1 == 0) {

            C[0] = 0;

        } else {

            C[0] = trunc(
                    ((double) B[0] / E1) * D1
            );
        }


        // C2
        //
        // =IF(B2=0,0,
        // TRUNC((B2/SUM(B2:B7))*(D1-SUM(C1:C1)),0))

        int sumB2B7 =
                B[1] +
                B[2] +
                B[3] +
                B[4] +
                B[5] +
                B[6];

        if (B[1] == 0 || sumB2B7 == 0) {

            C[1] = 0;

        } else {

            C[1] = trunc(
                    ((double) B[1] / sumB2B7)
                            * (D1 - C[0])
            );
        }


        // C3
        //
        // =IF(B3=0,0,
        // TRUNC((B3/SUM(B3:B7))*(D1-SUM(C1:C2)),0))

        int sumB3B7 =
                B[2] +
                B[3] +
                B[4] +
                B[5] +
                B[6];

        if (B[2] == 0 || sumB3B7 == 0) {

            C[2] = 0;

        } else {

            C[2] = trunc(
                    ((double) B[2] / sumB3B7)
                            * (D1 - C[0] - C[1])
            );
        }


        // C4
        //
        // =IF(B4=0,0,
        // TRUNC((B4/SUM(B4:B7))*(D1-SUM(C1:C3)),0))

        int sumB4B7 =
                B[3] +
                B[4] +
                B[5] +
                B[6];

        if (B[3] == 0 || sumB4B7 == 0) {

            C[3] = 0;

        } else {

            C[3] = trunc(
                    ((double) B[3] / sumB4B7)
                            * (D1
                            - C[0]
                            - C[1]
                            - C[2])
            );
        }


        // C5
        //
        // =IF(B5=0,0,
        // TRUNC((B5/SUM(B5:B7))*(D1-SUM(C1:C4)),0))

        int sumB5B7 =
                B[4] +
                B[5] +
                B[6];

        if (B[4] == 0 || sumB5B7 == 0) {

            C[4] = 0;

        } else {

            C[4] = trunc(
                    ((double) B[4] / sumB5B7)
                            * (D1
                            - C[0]
                            - C[1]
                            - C[2]
                            - C[3])
            );
        }


        // C6
        //
        // =IF(B6=0,0,
        // TRUNC((B6/SUM(B6:B7))*(D1-SUM(C1:C5)),0))

        int sumB6B7 =
                B[5] +
                B[6];

        if (B[5] == 0 || sumB6B7 == 0) {

            C[5] = 0;

        } else {

            C[5] = trunc(
                    ((double) B[5] / sumB6B7)
                            * (D1
                            - C[0]
                            - C[1]
                            - C[2]
                            - C[3]
                            - C[4])
            );
        }


        // C7
        //
        // =IF(B7=0,0,
        // TRUNC((B7/SUM(B7:B7))*(D1-SUM(C1:C6)),0))

        if (B[6] == 0) {

            C[6] = 0;

        } else {

            C[6] = trunc(
                    ((double) B[6] / B[6])
                            * (D1
                            - C[0]
                            - C[1]
                            - C[2]
                            - C[3]
                            - C[4]
                            - C[5])
            );
        }


        // Display C1:C7

        for (int i = 0; i < 7; i++) {

            c[i].setText(
                    String.valueOf(C[i])
            );
        }
    }


    private void resetValues() {

        // Player cells become empty again

        for (EditText player : players) {

            player.setText("");
        }


        // Manfi cells become empty again

        for (EditText value : b) {

            value.setText("");
        }


        // D1 becomes empty

        d1.setText("");


        // C1:C7 = 0

        for (TextView value : c) {

            value.setText("0");
        }


        // E1 = 0

        e1.setText("0");
    }


    private void toggleTheme() {

        darkMode = !darkMode;

        preferences
                .edit()
                .putBoolean("dark_mode", darkMode)
                .apply();

        applyTheme();
    }


    private void applyTheme() {

        int backgroundColor;
        int textColor;
        int hintColor;
        int cellColor;
        int borderColor;


        if (darkMode) {

            backgroundColor = Color.rgb(25, 25, 25);
            textColor = Color.WHITE;
            hintColor = Color.rgb(170, 170, 170);
            cellColor = Color.rgb(40, 40, 40);
            borderColor = Color.rgb(100, 100, 100);

            themeButton.setText("☀");

        } else {

            backgroundColor = Color.WHITE;
            textColor = Color.BLACK;
            hintColor = Color.rgb(120, 120, 120);
            cellColor = Color.WHITE;
            borderColor = Color.rgb(80, 80, 80);

            themeButton.setText("☾");
        }


        mainLayout.setBackgroundColor(
                backgroundColor
        );


        tabTitle.setTextColor(
                textColor
        );


        // Apply to all Player cells

        for (EditText player : players) {

            player.setTextColor(textColor);
            player.setHintTextColor(hintColor);
            player.setBackground(
                    createCellBackground(
                            cellColor,
                            borderColor
                    )
            );
        }


        // Apply to Manfi cells

        for (EditText value : b) {

            value.setTextColor(textColor);
            value.setHintTextColor(hintColor);
            value.setBackground(
                    createCellBackground(
                            cellColor,
                            borderColor
                    )
            );
        }


        // Apply to Mablagh cells

        for (TextView value : c) {

            value.setTextColor(textColor);
            value.setBackground(
                    createCellBackground(
                            cellColor,
                            borderColor
                    )
            );
        }


        // D1

        d1.setTextColor(textColor);
        d1.setHintTextColor(hintColor);
        d1.setBackground(
                createCellBackground(
                        cellColor,
                        borderColor
                )
        );


        // E1

        e1.setTextColor(textColor);
        e1.setBackground(
                createCellBackground(
                        cellColor,
                        borderColor
                )
        );


        // Table headers

        TextView headerPlayer =
                findViewById(R.id.headerPlayer);

        TextView headerManfi =
                findViewById(R.id.headerManfi);

        TextView headerMablagh =
                findViewById(R.id.headerMablagh);


        TextView[] headers = {
                headerPlayer,
                headerManfi,
                headerMablagh
        };


        for (TextView header : headers) {

            header.setTextColor(textColor);

            header.setBackground(
                    createCellBackground(
                            cellColor,
                            borderColor
                    )
            );
        }


        // Other labels

        ViewGroup root =
                findViewById(R.id.mainLayout);

        updateLabels(
                root,
                textColor
        );
    }


    private void updateLabels(
            ViewGroup parent,
            int textColor) {

        for (int i = 0;
             i < parent.getChildCount();
             i++) {

            View child =
                    parent.getChildAt(i);

            if (child instanceof TextView
                    && !(child instanceof EditText)
                    && child != tabTitle) {

                ((TextView) child)
                        .setTextColor(textColor);
            }


            if (child instanceof ViewGroup) {

                updateLabels(
                        (ViewGroup) child,
                        textColor
                );
            }
        }
    }


    private GradientDrawable createCellBackground(
            int backgroundColor,
            int borderColor) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                backgroundColor
        );

        drawable.setStroke(
                1,
                borderColor
        );

        return drawable;
    }


    private void createTableBorders() {

        // Borders are applied by applyTheme()
    }
}
