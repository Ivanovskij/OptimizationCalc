package com.ivo.module;

import com.ivo.utils.BinaryUtil;
import com.ivo.utils.Config;
import com.ivo.utils.Parameters;
import com.ivo.utils.RandomUtil;
import java.io.Serializable;

/**
 *
 * @author IOAdmin
 */
public class Chromosome implements Serializable {

    private String[] chromosomes;
    private double result;
    
    public Chromosome() {
    }
    
    public Chromosome(String[] chromosomes, double result) {
        this.chromosomes = chromosomes;
        this.result = result;
    }
    
    
    public void createRandomGenes() {
        final int arg_size = Parameters.get(Config.ARG_SIZE).asInteger();
        final int gene_size = Parameters.get(Config.GENE_SIZE).asInteger();
        
        StringBuilder chromosomeBinary;
        final String[] res = new String[arg_size];
        
        for (int i = 0; i < arg_size; i++) {
            chromosomeBinary = new StringBuilder();
            for (int k = 0; k < gene_size; k++) {
                char rndGen = RandomUtil.getRandomRangeInt(0, 1) == 0 ? '0' : '1';
                chromosomeBinary.append(' ');
                chromosomeBinary.setCharAt(k, rndGen);
            }
            res[i] = chromosomeBinary.toString();
        }

        setChromosomes(res);
    }
    
    /*public void createRandomGenes() {
        final int arg_size = Parameters.get(Config.ARG_SIZE).asInteger();
        final int xMin = Parameters.get(Config.X_MIN).asInteger();
        final int xMax = Parameters.get(Config.X_MAX).asInteger();
        
        final Double[] res = new Double[arg_size];
        
        for (int i = 0; i < arg_size; i++) {
            double rand = RandomUtil.getRandomRangeDouble(xMin, xMax);
            res[i] = rand;
        }

        String[] binary = BinaryUtil.doubleArrToBinaryArr(res);
        setChromosomes(binary);
    }*/
    
    
    // *************************************

    public String[] getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(String[] chromosomes) {
        this.chromosomes = chromosomes;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }
    
    @Override
    public String toString() {
        StringBuilder buffChromosomes = new StringBuilder();
        for (String choromosome : chromosomes) {
            double x = BinaryUtil.binaryToNumber(choromosome);
            buffChromosomes.append(x).append(", ");
        }
        return "chromosomes=" + buffChromosomes + " result=" + result;
    }
    
}
