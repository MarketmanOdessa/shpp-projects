package com.shpp.p2p.cs.sburlachenko.assignment6.tm;

import static java.lang.Math.abs;

public class ToneMatrixLogic {
    /**
     * Given the contents of the tone matrix, returns a string of notes that should be played
     * to represent that matrix.
     *
     * @param toneMatrix The contents of the tone matrix.
     * @param column     The column number that is currently being played.
     * @param samples    The sound samples associated with each row.
     * @return A sound sample corresponding to all notes currently being played.
     */
    public static double[] matrixToMusic(boolean[][] toneMatrix, int column, double[][] samples) {
        double[] result = new double[ToneMatrixConstants.sampleSize()];
        double[] soundWave;
        double[] sumSoundWaves = new double[samples[0].length];
        for (int row = 0; row < toneMatrix.length; row++) {
            if (toneMatrix[row][column]) {
                soundWave = samples[row];
                for (int i = 0; i < soundWave.length; i++) {
                    sumSoundWaves[i] = sumSoundWaves[i] + soundWave[i];
                }
            }
        }
        double maxIntensity = 1;
        for (double sumSoundWave : sumSoundWaves) {
            double a = abs(sumSoundWave);
            if (a > maxIntensity) maxIntensity = a;
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = sumSoundWaves[i]/maxIntensity;
        }
        return result;
    }
}