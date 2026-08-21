package com.example.myapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends Activity {

    EditText[] players = new EditText[7];
    EditText[] b = new EditText[7];
    TextView[] c = new TextView[7];

    EditText d1;

    TextView e1;
    TextView f1;

    Button themeButton;
    Button resetButton;

    LinearLayout mainLayout;
    TextView tabTitle;

    LinearLayout tableContainer;
    LinearLayout d1Card;
    LinearLayout e1Card;
    LinearLayout f1Card;

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
                true
        );

        initializeViews();

        setupListeners();

        applyTheme();

        calculate();
    }


    private void initializeViews() {

        mainLayout = findViewById(R.id.mainLayout);

        tabTitle = findViewById(R.id.tabTitle);

        themeButton = findViewById(R.id.themeButton);

        resetButton = findViewById(R.id.resetButton);

        tableContainer = findViewById(R.id.tableContainer);

        d1Card = findViewById(R.id.d1Card);

        e1Card = findViewById(R.id.e1Card);

        f1Card = findViewById(R.id.f1Card);

        d1 = findViewById(R.id.d1);

        e1 = findViewById(R.id.e1);

        f1 = findViewById(R.id.f1);


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

        TextWatcher watcher = new TextWatcher() {

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
            public void afterTextChanged(
                    Editable s) {
            }
        };


        for (EditText item : b) {

            item.addTextChangedListener(watcher);
        }


        d1.addTextChangedListener(watcher);


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


        e1.setText(
                String.valueOf(E1)
        );


        int[] C = new int[7];


        // C1

        if (B[0] == 0 || E1 == 0) {

            C[0] = 0;

        } else {

            C[0] = trunc(
                    ((double) B[0] / E1) * D1
            );
        }


        // C2

        int sumB2B7 =
                B[1]
                        + B[2]
                        + B[3]
                        + B[4]
                        + B[5]
                        + B[6];


        if (B[1] == 0 || sumB2B7 == 0) {

            C[1] = 0;

        } else {

            C[1] = trunc(
                    ((double) B[1] / sumB2B7)
                            * (D1 - C[0])
            );
        }


        // C3

        int sumB3B7 =
                B[2]
                        + B[3]
                        + B[4]
                        + B[5]
                        + B[6];


        if (B[2] == 0 || sumB3B7 == 0) {

            C[2] = 0;

        } else {

            C[2] = trunc(
                    ((double) B[2] / sumB3B7)
                            * (
                            D1
                                    - C[0]
                                    - C[1]
                    )
            );
        }


        // C4

        int sumB4B7 =
                B[3]
                        + B[4]
                        + B[5]
                        + B[6];


        if (B[3] == 0 || sumB4B7 == 0) {

            C[3] = 0;

        } else {

            C[3] = trunc(
                    ((double) B[3] / sumB4B7)
                            * (
                            D1
                                    - C[0]
                                    - C[1]
                                    - C[2]
                    )
            );
        }


        // C5

        int sumB5B7 =
                B[4]
                        + B[5]
                        + B[6];


        if (B[4] == 0 || sumB5B7 == 0) {

            C[4] = 0;

        } else {

            C[4] = trunc(
                    ((double) B[4] / sumB5B7)
                            * (
                            D1
                                    - C[0]
                                    - C[1]
                                    - C[2]
                                    - C[3]
                    )
            );
        }


        // C6

        int sumB6B7 =
                B[5]
                        + B[6];


        if (B[5] == 0 || sumB6B7 == 0) {

            C[5] = 0;

        } else {

            C[5] = trunc(
                    ((double) B[5] / sumB6B7)
                            * (
                            D1
                                    - C[0]
                                    - C[1]
                                    - C[2]
                                    - C[3]
                                    - C[4]
                    )
            );
        }


        // C7

        if (B[6] == 0) {

            C[6] = 0;

        } else {

            C[6] = trunc(
                    ((double) B[6] / B[6])
                            * (
                            D1
                                    - C[0]
                                    - C[1]
                                    - C[2]
                                    - C[3]
                                    - C[4]
                                    - C[5]
                    )
            );
        }


        // Show C1:C7

        for (int i = 0; i < 7; i++) {

            c[i].setText(
                    String.valueOf(C[i])
            );
        }


        /*
         * F1 = D1 / E1
         *
         * هر منفی = پول دستگاه ها / جمع منفی ها
         *
         * Result is displayed with 2 decimal places.
         */

        double harManfi = 0.0;


        if (E1 != 0) {

            harManfi =
                    (double) D1 / E1;
        }


        f1.setText(
                String.format(
                        Locale.US,
                        "%.2f",
                        harManfi
                )
        );
    }


    private void resetValues() {

        // Clear Player

        for (EditText player : players) {

            player.setText("");
        }


        // Clear Manfi

        for (EditText value : b) {

            value.setText("");
        }


        // Clear D1

        d1.setText("");


        // Reset outputs

        for (TextView value : c) {

            value.setText("0");
        }


        e1.setText("0");

        f1.setText("0.00");
    }


    private void toggleTheme() {

        darkMode = !darkMode;


        preferences
                .edit()
                .putBoolean(
                        "dark_mode",
                        darkMode
                )
                .apply();


        applyTheme();
    }


    private void applyTheme() {

        int backgroundColor;
        int primaryTextColor;
        int secondaryTextColor;
        int cellColor;
        int alternateCellColor;
        int borderColor;
        int cardColor;
        int titleColor;


        if (darkMode) {

            backgroundColor =
                    Color.rgb(7, 14, 29);

            primaryTextColor =
                    Color.rgb(245, 247, 255);

            secondaryTextColor =
                    Color.rgb(145, 153, 178);

            cellColor =
                    Color.rgb(25, 34, 56);

            alternateCellColor =
                    Color.rgb(30, 41, 67);

            borderColor =
                    Color.rgb(55, 67, 92);

            cardColor =
                    Color.rgb(29, 42, 68);

            titleColor =
                    Color.rgb(105, 220, 145);

            themeButton.setText("☀");

        } else {

            backgroundColor =
                    Color.rgb(245, 247, 252);

            primaryTextColor =
                    Color.rgb(25, 30, 45);

            secondaryTextColor =
                    Color.rgb(105, 112, 130);

            cellColor =
                    Color.WHITE;

            alternateCellColor =
                    Color.rgb(244, 246, 250);

            borderColor =
                    Color.rgb(210, 215, 225);

            cardColor =
                    Color.WHITE;

            titleColor =
                    Color.rgb(20, 115, 65);

            themeButton.setText("☾");
        }


        // Main background

        mainLayout.setBackgroundColor(
                backgroundColor
        );


        // Main title

        tabTitle.setTextColor(
                primaryTextColor
        );


        // Table

        tableContainer.setBackground(
                createRoundedBackground(
                        cellColor,
                        borderColor,
                        28,
                        2
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

            header.setTextColor(
                    Color.WHITE
            );

            header.setBackground(
                    createRoundedBackground(
                            Color.rgb(40, 100, 232),
                            Color.rgb(45, 112, 245),
                            0,
                            1
                    )
            );
        }


        // Player cells

        for (int i = 0; i < 7; i++) {

            players[i].setTextColor(
                    primaryTextColor
            );

            players[i].setHintTextColor(
                    secondaryTextColor
            );

            players[i].setBackground(
                    createRoundedBackground(
                            i % 2 == 0
                                    ? cellColor
                                    : alternateCellColor,
                            borderColor,
                            0,
                            1
                    )
            );
        }


        // Manfi cells

        for (int i = 0; i < 7; i++) {

            b[i].setTextColor(
                    primaryTextColor
            );

            b[i].setHintTextColor(
                    secondaryTextColor
            );

            b[i].setBackground(
                    createRoundedBackground(
                            i % 2 == 0
                                    ? cellColor
                                    : alternateCellColor,
                            borderColor,
                            0,
                            1
                    )
            );
        }


        // Mablagh cells

        for (int i = 0; i < 7; i++) {

            c[i].setTextColor(
                    primaryTextColor
            );

            c[i].setBackground(
                    createRoundedBackground(
                            i % 2 == 0
                                    ? cellColor
                                    : alternateCellColor,
                            borderColor,
                            0,
                            1
                    )
            );
        }


        // Titles

        TextView d1Title =
                (TextView) d1Card.getChildAt(0);

        TextView e1Title =
                (TextView) e1Card.getChildAt(0);

        TextView f1Title =
                (TextView) f1Card.getChildAt(0);


        d1Title.setTextColor(
                titleColor
        );

        e1Title.setTextColor(
                titleColor
        );

        f1Title.setTextColor(
                titleColor
        );


        // D1

        d1.setTextColor(
                primaryTextColor
        );

        d1.setHintTextColor(
                secondaryTextColor
        );


        // E1

        e1.setTextColor(
                primaryTextColor
        );


        // F1

        f1.setTextColor(
                primaryTextColor
        );


        // D1 Card

        d1Card.setBackground(
                createRoundedBackground(
                        cardColor,
                        Color.rgb(45, 110, 240),
                        26,
                        2
                )
        );


        // E1 Card

        e1Card.setBackground(
                createRoundedBackground(
                        cardColor,
                        borderColor,
                        26,
                        2
                )
        );


        // F1 Card

        f1Card.setBackground(
                createRoundedBackground(
                        cardColor,
                        borderColor,
                        26,
                        2
                )
        );


        // D1 input

        d1.setBackground(
                createRoundedBackground(
                        cardColor,
                        Color.rgb(45, 110, 240),
                        18,
                        1
                )
        );


        // Theme button

        themeButton.setTextColor(
                primaryTextColor
        );
    }


    private GradientDrawable createRoundedBackground(
            int backgroundColor,
            int borderColor,
            float radius,
            int strokeWidth) {

        GradientDrawable drawable =
                new GradientDrawable();

        drawable.setColor(
                backgroundColor
        );

        drawable.setCornerRadius(
                radius
        );

        drawable.setStroke(
                strokeWidth,
                borderColor
        );

        return drawable;
    }
}
