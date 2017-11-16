package com.ivo.module;

import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class GeneticResult implements Serializable {

    private int currentGeneration;
    private int countDead;
    private int countSurvivor;
    private int countMutation;
    private int countSelectionChildren;
    private int countSelectionParent;
    private Chromosome bestIndividual;
    private Integer[] pointsIntBestIndividual;
    private Double[] pointsDoubleBestIndividual;
    private double resultBestIndividual;

    public GeneticResult() {
    }

    public int getCurrentGeneration() {
        return currentGeneration;
    }

    public void setCurrentGeneration(int currentGeneration) {
        this.currentGeneration = currentGeneration;
    }

    public int getCountDead() {
        return countDead;
    }

    public void setCountDead(int countDead) {
        this.countDead = countDead;
    }

    public int getCountSurvivor() {
        return countSurvivor;
    }

    public void setCountSurvivor(int countSurvivor) {
        this.countSurvivor = countSurvivor;
    }

    public int getCountMutation() {
        return countMutation;
    }

    public void setCountMutation(int countMutation) {
        this.countMutation = countMutation;
    }

    public int getCountSelectionChildren() {
        return countSelectionChildren;
    }

    public void setCountSelectionChildren(int countSelectionChildren) {
        this.countSelectionChildren = countSelectionChildren;
    }

    public int getCountSelectionParent() {
        return countSelectionParent;
    }

    public void setCountSelectionParent(int countSelectionParent) {
        this.countSelectionParent = countSelectionParent;
    }

    public Chromosome getBestIndividual() {
        return bestIndividual;
    }

    public void setBestIndividual(Chromosome bestIndividual) {
        this.bestIndividual = bestIndividual;
    }

    public double getResultBestIndividual() {
        return resultBestIndividual;
    }

    public void setResultBestIndividual(double resultBestIndividual) {
        this.resultBestIndividual = resultBestIndividual;
    }

    public Integer[] getPointsIntBestIndividual() {
        return pointsIntBestIndividual;
    }

    public void setPointsIntBestIndividual(Integer[] pointsIntBestIndividual) {
        this.pointsIntBestIndividual = pointsIntBestIndividual;
    }

    public Double[] getPointsDoubleBestIndividual() {
        return pointsDoubleBestIndividual;
    }

    public void setPointsDoubleBestIndividual(Double[] pointsDoubleBestIndividual) {
        this.pointsDoubleBestIndividual = pointsDoubleBestIndividual;
    }
}
