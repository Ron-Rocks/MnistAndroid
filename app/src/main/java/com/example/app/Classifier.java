package com.example.app;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Classifier {
    public static int imgSize = 28;
    private ByteBuffer imgData;
    private int classes = 10;
    private String modelName = "model.tflite";
    private Interpreter interpretor;
    private Interpreter.Options options = new Interpreter.Options();
    private int[] imgPixels = new int[imgSize*imgSize];
    private float[][] results = new float[1][classes];

    public Classifier(Activity activity) throws IOException{

            interpretor = new Interpreter(loadModel(activity),options);
            imgData = ByteBuffer.allocateDirect(4*imgSize*imgSize*1);

            imgData.order(ByteOrder.nativeOrder());




    }
        public Resulter classify(Bitmap bitmap){
        results = new float[1][classes];
            convertBitmapToByteBuffer(bitmap);
            interpretor.run(imgData,results);

            return new Resulter(results[0]);
        }
    public MappedByteBuffer loadModel(Activity activity) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelName);

        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());

        FileChannel fileChannel = inputStream.getChannel();

        long startOffset = fileDescriptor.getStartOffset();

        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    private void convertBitmapToByteBuffer(Bitmap bitmap) {

        if (imgData == null) {

            return;

        }
        imgData.rewind();
        bitmap.getPixels(imgPixels, 0, bitmap.getWidth(), 0, 0,

                bitmap.getWidth(), bitmap.getHeight());



        int pixel = 0;

        for (int i = 0; i < imgSize; ++i) {

            for (int j = 0; j < imgSize; ++j) {

                int value = imgPixels[pixel++];

                imgData.putFloat(convertPixel(value));

            }

        }

    }
    private static float convertPixel(int color) {

        return (255 - (((color >> 16) & 0xFF) * 0.299f

                + ((color >> 8) & 0xFF) * 0.587f

                + (color & 0xFF) * 0.114f)) / 255.0f;

    }

}
