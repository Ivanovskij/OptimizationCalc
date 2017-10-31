package com.ivo.module;

import com.ivo.utils.BinaryUtil;
import com.ivo.utils.Config;
import com.ivo.utils.Function;
import com.ivo.utils.Parameters;
import com.ivo.utils.RandomUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticAlgorithm implements BaseGenetic {

    private final List<Chromosome> bestIndividuals;
    
    // The gene pool current population
    private Chromosome[] curGenePool;
    // The gene pool of the following populations
    private Chromosome[] nextGenerationGenePool;
    // Selected best parents
    private final Chromosome[] selectionBestParents;
    // childrens after crossover and mutation
    private final Chromosome[] childrens;
    
    // counter current generation
    private int curGenerations;
    
    private int population_count;
    private int max_generations;
    private int gene_size;
    private int arg_size;
    private double mutation_percent;
    
    
    public GeneticAlgorithm() {
        setParameters();
        
        this.bestIndividuals = new ArrayList<>();
        
        this.curGenePool = new Chromosome[population_count];
        this.nextGenerationGenePool = new Chromosome[population_count];
        this.selectionBestParents = new Chromosome[population_count];
        this.childrens = new Chromosome[population_count];
       
        curGenerations = 0;
    }

    private void setParameters() {
        population_count = Parameters.get(Config.POPULATION_COUNT).asInteger();
        max_generations = Parameters.get(Config.MAX_GENERATIONS).asInteger();
        gene_size = Parameters.get(Config.GENE_SIZE).asInteger();
        arg_size = Parameters.get(Config.ARG_SIZE).asInteger();
        mutation_percent = Parameters.get(Config.MUTATION_PERCENT).asDouble();
    }
    
    @Override
    public List<Chromosome> solve() {
        createPopulation();
        
        do {
            selection();
            
            crossover();
            
            mutation();
            
            setCurrentGeneration();
            
            getBestIndividual();
        } while (curGenerations++ < max_generations);
        
        return bestIndividuals;
    }

    @Override
    public void createPopulation() {
        for (int i = 0; i < population_count; i++) {
            curGenePool[i] = new Chromosome();
            curGenePool[i].createRandomGenes();
        }
    }

    @Override
    public void selection() {
        tournamentSelection();
    }
    
    private void tournamentSelection() {
        int rndFirst, rndSecond;
        
        Chromosome first, second;
        double resFirst, resSecond;
        
        for (int i = 0; i < population_count; i++) {
            rndFirst = RandomUtil.getRandomRangeInt(0, population_count - 1);
            do {
                rndSecond = RandomUtil.getRandomRangeInt(0, population_count - 1);
            } while (rndFirst == rndSecond);
            
            first = curGenePool[rndFirst];
            second = curGenePool[rndSecond];
            
            resFirst = fitness_function(first);
            resSecond = fitness_function(second);
            
            if (resFirst > resSecond) {
                selectionBestParents[i] = first;
            } else {
                selectionBestParents[i] = second;
            } 
        }
    }
    
    private double fitness_function(Chromosome ch) {
        Double[] args = BinaryUtil.binaryArrToNumberArr(ch.getChromosomes());
        
        double result = Function.f(args);
        result += Function.insideBounds(args);
        return result;
    }

    @Override
    public void crossover() {
        int rndFirst, rndSecond;
        
        Chromosome first, second;
        
        for (int i = 0; i < population_count; i++) {
            rndFirst = RandomUtil.getRandomRangeInt(0, population_count - 1);
            
            do {
                rndSecond = RandomUtil.getRandomRangeInt(0, population_count - 1);
            } while (rndFirst == rndSecond);
            
            first = selectionBestParents[rndFirst];
            second = selectionBestParents[rndSecond];
            
            childrens[i] = PanmixiaCross(first, second);
        }
    }
    
    private Chromosome PanmixiaCross(Chromosome first, Chromosome second) {
        Chromosome children = new Chromosome();
        
        String[] xFirst = first.getChromosomes();
        String[] xSecond = second.getChromosomes();
        
        String[] resChildren = new String[arg_size];
        StringBuilder buffer;
        
        int crossLine;
        
        for (int i = 0; i < arg_size; i++) {
            buffer = new StringBuilder();
            crossLine = getRandomCrossLine();
            for (int j = 0; j < gene_size; j++) {
                buffer.append(' ');
                if (j <= crossLine) {
                    buffer.setCharAt(j, xSecond[i].charAt(j));
                } else {
                    buffer.setCharAt(j, xFirst[i].charAt(j));
                }
            }
            resChildren[i] = buffer.toString();
        }
        
        children.setChromosomes(resChildren);
        return children;
    }
    
    private int getRandomCrossLine() {
        return RandomUtil.getRandomRangeInt(0, gene_size);
    }

    @Override
    public void mutation() {
        double rnd;
        for (Chromosome ch : childrens) {
            rnd = RandomUtil.getRandomRangeDouble(0, 1);
            if (rnd <= mutation_percent) {
                //System.out.println("b "+Arrays.toString(BinaryUtil.binaryArrToNumberArr(chrom.getChromosomes())));
                mutate(ch);
                //System.out.println("a" + Arrays.toString(BinaryUtil.binaryArrToNumberArr(chrom.getChromosomes())));
            }
        }
    }
    
    private void mutate(Chromosome chromosome) {
        String[] args = chromosome.getChromosomes();
        
        StringBuffer resultChromosome;
        
        for (int i = 0; i < args.length; i++) {
            resultChromosome = new StringBuffer(args[i]);
            for (int j = 0; j < gene_size; j++) {
                double rnd = RandomUtil.getRandomRangeDouble(0, 1);
                if (rnd <= mutation_percent) {
                    char oldValueX1 = args[i].charAt(j);
                    char newValueX1 = oldValueX1 == '1' ? '0' : '1';
                    resultChromosome.setCharAt(j, newValueX1);
                }
            }
            args[i] = resultChromosome.toString();
        }
        
        chromosome.setChromosomes(args);
    }

    @Override
    public void setCurrentGeneration() {
        curGenePool = selectionBestGeneration();
    }
    
    private Chromosome[] selectionBestGeneration() {
        int rndFirst, rndSecond;
        
        Chromosome first, second;
        double resFirst, resSecond;
        
        
        // SELECTION Idndividuals who inside bounds
        /*Chromosome ind;
        
        int p_counter = 0,
                c_counter = 0,
                null_counter = 0;
        
        int nextGenCounter = 0;
        
        nextGenerationGenePool = new Chromosome[population_count];
        
        for (int i = 0; i < population_count; i++) {
            ind = childrens[i];
            if (inside(ind)) {
                nextGenerationGenePool[nextGenCounter] = childrens[i];
                nextGenCounter++;
                p_counter++;
            }
        }
        
        for (int i = nextGenCounter; i < population_count; i++) {
            ind = selectionBestParents[i];
            if (nextGenCounter >= population_count) {
                break;
            }
            if (inside(ind)) {
                nextGenerationGenePool[nextGenCounter] = selectionBestParents[i];
                nextGenCounter++;
                c_counter++;
            }
        }
        
        int i = nextGenCounter;
        while (i < population_count) {
            nextGenerationGenePool[nextGenCounter] = childrens[i];
            nextGenCounter++;
            i++;
            null_counter++;
        }
        
        System.out.println("======================================");
        System.out.println("p=" + p_counter + ", " + "c=" + c_counter + ", " + "null=" + null_counter);
        System.out.println("======================================");
        
        return nextGenerationGenePool;*/
        
        // SELECTION FROM PARENTS/2 and CHILDRENS/2
        for (int i = 0; i < population_count / 2; i++) {
            rndFirst = RandomUtil.getRandomRangeInt(0, population_count - 1);
            do {
                rndSecond = RandomUtil.getRandomRangeInt(0, population_count - 1);
            } while (rndFirst == rndSecond);
            
            first = selectionBestParents[rndFirst];
            second = selectionBestParents[rndSecond];
            
            resFirst = fitness_function(first);
            resSecond = fitness_function(second);
            
            if (resFirst > resSecond) {
                nextGenerationGenePool[i] = first;
            } else {
                nextGenerationGenePool[i] = second;
            } 
        }
        
        
        for (int i = population_count / 2; i < population_count; i++) {
            rndFirst = RandomUtil.getRandomRangeInt(0, population_count - 1);
            do {
                rndSecond = RandomUtil.getRandomRangeInt(0, population_count - 1);
            } while (rndFirst == rndSecond);
            
            first = childrens[rndFirst];
            second = childrens[rndSecond];
            
            resFirst = fitness_function(first);
            resSecond = fitness_function(second);
            
            if (resFirst > resSecond) {
                nextGenerationGenePool[i] = first;
            } else {
                nextGenerationGenePool[i] = second;
            } 
        }
        return nextGenerationGenePool;
    }
    
    private boolean inside(Chromosome c) {
        Double[] a = BinaryUtil.binaryArrToNumberArr(c.getChromosomes());
        return Function.insideBounds("", a);
    }
    
    private Chromosome getBestIndividual() {
        Chromosome res = curGenePool[0];
        
        Double[] args = BinaryUtil.binaryArrToNumberArr(curGenePool[0].getChromosomes());
        double r;
        double max = Function.f(args);

        for (int i = 0; i < population_count; i++) {
            args = BinaryUtil.binaryArrToNumberArr(curGenePool[i].getChromosomes());
            r = Function.f(args);
            if (Function.insideBounds("",args)) {
                if (max < r) {
                    max = r;
                    res = curGenePool[i];
                }
            }
        }
 

        System.out.println("X = " + Arrays.toString(BinaryUtil.binaryArrToNumberArr(res.getChromosomes())));
        System.out.println("*****************************Best Individual[" + curGenerations + "] = " + max);

        return res;
    }
}
