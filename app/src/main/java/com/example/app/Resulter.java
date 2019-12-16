package com.example.app;

public class Resulter {
    private int index;
    private float prob;

    public Resulter(float[] probs){
        index = argmax(probs);
        prob = probs[index];
        System.out.println(probs);
    }
    public int getIndex(){
        return index;
    }
    public float getProb(){
        return prob;
    }

    private static int argmax(float[] probs) {
        int maxIdx = -1;
        float maxProb = 0.0f;
        for (int i = 0; i < probs.length; i++) {
            if (probs[i] > maxProb) {
                maxProb = probs[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}
