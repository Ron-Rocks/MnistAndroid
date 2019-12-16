package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nex3z.fingerpaintview.FingerPaintView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private Button detect;
    private Button clear;
    private TextView classView,prediction;
    private FingerPaintView fpv;
    private Classifier mclassifier;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fpv = findViewById(R.id.fpv_paint);
        detect = findViewById(R.id.detectButton);
        clear = findViewById(R.id.clearButton);
        classView = findViewById(R.id.classText);
        prediction = findViewById(R.id.probText);

        try {
            mclassifier = new Classifier(this);
        } catch (IOException e) {
            classView.setText("Not Found");
            System.out.println("------------------------------------------");
            System.out.println(e);

        }

        detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bm = fpv.exportToBitmap(mclassifier.imgSize,mclassifier.imgSize);

                Resulter res = mclassifier.classify(bm);

                classView.setText("Number : " + res.getIndex()+" ");
                prediction.setText("Probability : "+res.getProb()+" ");

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fpv.clear();
                classView.setText("Number : ");
                prediction.setText("Probability : ");
            }
        });

    }
}
