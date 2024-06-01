package com.example.colortiles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int darkColor;
    int brightColor;
    View[][] tiles = new View[4][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources r = getResources();
        darkColor = r.getColor(R.color.dark);
        brightColor = r.getColor(R.color.bright);

        // Заполнение массива tiles ссылками на объекты
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int resId = getResources().getIdentifier("t" + i + j, "id", getPackageName());
                tiles[i][j] = findViewById(resId);
            }
        }

        // Генерация случайной стартовой позиции
        generateRandomStartPosition();
    }

    // Метод для генерации случайной стартовой позиции
    private void generateRandomStartPosition() {
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int randomColor = random.nextInt(2);

                if (randomColor == 0) {
                    tiles[i][j].setBackgroundResource(R.drawable.dark_border);
                } else {
                    tiles[i][j].setBackgroundResource(R.drawable.bright_border);
                }
            }
        }
    }

    // Метод для изменения цвета элемента
    public void changeColor(View v) {
        Drawable brightDrawable = ContextCompat.getDrawable(this, R.drawable.bright_border);
        Drawable darkDrawable = ContextCompat.getDrawable(this, R.drawable.dark_border);

        Drawable currentDrawable = v.getBackground();
        if (currentDrawable != null) {
            if (currentDrawable.getConstantState().equals(brightDrawable.getConstantState())) {
                v.setBackgroundResource(R.drawable.dark_border);
            } else if (currentDrawable.getConstantState().equals(darkDrawable.getConstantState())) {
                v.setBackgroundResource(R.drawable.bright_border);
            }
        }
    }

    // Метод обработки нажатия на элемент
    public void onClick(View v) {

        String tag = v.getTag().toString();
        int x = Integer.parseInt(tag.substring(0, 1));
        int y = Integer.parseInt(tag.substring(1, 2));

        // Изменить цвет с таким же x и y
        changeColor(v);
        for (int i = 0; i < 4; i++) {
            changeColor(tiles[x][i]);
            changeColor(tiles[i][y]);
        }

        // Проверка на выигрыш
        if (isGameWon()) {
            Toast.makeText(this, "Поздравляем! Вы выиграли!", Toast.LENGTH_SHORT).show();
        }
    }

    // Метод для проверки, выиграна ли игра
    private boolean isGameWon() {
        Drawable firstTileBackground = tiles[0][0].getBackground();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Drawable currentTileBackground = tiles[i][j].getBackground();

                if (!currentTileBackground.getConstantState().equals(firstTileBackground.getConstantState())) {
                    return false;
                }
            }
        }

        return true;
    }


}