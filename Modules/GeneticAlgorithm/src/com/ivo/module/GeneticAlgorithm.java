package com.ivo.module;

import com.ivo.utils.BinaryUtil;
import com.ivo.utils.Config;
import com.ivo.utils.Function;
import com.ivo.utils.Parameters;
import com.ivo.utils.RandomUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author IOAdmin
 */
public class GeneticAlgorithm implements BaseGenetic {

    // all results
    private final List<GeneticResult> results;
    
    private int countDead;
    private int countSurvivor;
    private int countMutation;
    private int countSelectionChildren;
    private int countSelectionParent;
    private Chromosome bestIndividual;
    private double resultBestIndividual;
    
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
    
    // init function and constraints
    private final Function function;
    
    
    public GeneticAlgorithm(String goalFunction, String[] constraints, String[] constraintsWithOutCondition) {
        setParameters();
        
        function = new Function(goalFunction, constraints, constraintsWithOutCondition);
        
        this.results = new ArrayList<>();
        
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
    public List<GeneticResult> solve() {
        createPopulation();
        
        do {
            selection();
            
            crossover();
            
            mutation();
            
            setCurrentGeneration();
            
            setInformationAboutGeneration();
        } while (curGenerations++ < max_generations);
        
        return results;
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
        
        double result = function.getValueGoalFunction(args);
        result += function.isInBoundsStaticPenalty(args);
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
        countMutation = 0;
        
        for (Chromosome ch : childrens) {
            rnd = RandomUtil.getRandomRangeDouble(0, 1);
            if (rnd <= mutation_percent) {
                //System.out.println("b "+Arrays.toString(BinaryUtil.binaryArrToNumberArr(chrom.getChromosomes())));
                mutate(ch);
                countMutation++;
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
        Double[] args;
        
        countSelectionParent = 0;
        countSelectionChildren = 0;
        countSurvivor = 0;
        
        int nextGenCounter = 0;
        
        nextGenerationGenePool = new Chromosome[population_count];
        
        for (int i = 0; i < population_count; i++) {
            args = BinaryUtil.binaryArrToNumberArr(childrens[i].getChromosomes());
            if (function.isInBounds(args)) {
                nextGenerationGenePool[nextGenCounter] = childrens[i];
                nextGenCounter++;
                countSelectionChildren++;
            }
        }
        
        for (int i = nextGenCounter; i < population_count; i++) {
            args = BinaryUtil.binaryArrToNumberArr(selectionBestParents[i].getChromosomes());
            if (nextGenCounter >= population_count) {
                break;
            }
            if (function.isInBounds(args)) {
                nextGenerationGenePool[nextGenCounter] = selectionBestParents[i];
                nextGenCounter++;
                countSelectionParent++;
            }
        }
        
        int i = nextGenCounter;
        while (i < population_count) {
            nextGenerationGenePool[nextGenCounter] = curGenePool[i];
            nextGenCounter++;
            i++;
            countSurvivor++;
        }
        
        /*System.out.println("======================================");
        System.out.println("child=" + countSelectionChildren + ", " + 
                "parent=" + countSelectionParent + ", " + "surviour=" + countSurvivor);
        System.out.println("======================================");*/
        
        return nextGenerationGenePool;
        
        // SELECTION FROM PARENTS/2 and CHILDRENS/2
        /*for (int i = 0; i < population_count / 2; i++) {
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
        return nextGenerationGenePool;*/
    }
    
    private Chromosome getBestIndividual() {
        Chromosome res = curGenePool[0];
        
        Double[] args = BinaryUtil.binaryArrToNumberArr(curGenePool[0].getChromosomes());
        double r;
        double max = function.getValueGoalFunction(args);

        for (int i = 0; i < population_count; i++) {
            args = BinaryUtil.binaryArrToNumberArr(curGenePool[i].getChromosomes());
            r = function.getValueGoalFunction(args);
            if (function.isInBounds(args)) {
                if (max < r) {
                    max = r;
                    res = curGenePool[i];
                }
            }
        }

        // set results
        bestIndividual = new Chromosome();
        bestIndividual.setChromosomes(res.getChromosomes());
        resultBestIndividual = max;
        
        return res;
    }

    private void setInformationAboutGeneration() {
        // set result about best Individuals
        getBestIndividual();
        
        GeneticResult gresult = new GeneticResult();
        
        // calc count dead
        countDead = countSelectionChildren + countSelectionParent;
        
        // set results
        gresult.setCurrentGeneration(curGenerations);
        gresult.setCountDead(countDead);
        gresult.setCountSurvivor(countSurvivor);
        gresult.setCountMutation(countMutation);
        gresult.setCountSelectionChildren(countSelectionChildren);
        gresult.setCountSelectionParent(countSelectionParent);
        gresult.setBestIndividual(bestIndividual);
        
        // set args doubles
        Double[] argsDouble = BinaryUtil.binaryArrToNumberArr(bestIndividual.getChromosomes());
        gresult.setPointsDoubleBestIndividual(argsDouble);
        
        // set args integers
        Integer[] argsInt = new Integer[arg_size];
        for (int i = 0; i < argsDouble.length; i++) {
            int value = argsDouble[i].intValue();
            argsInt[i] = new Integer(value);
        }
        gresult.setPointsIntBestIndividual(argsInt);
        
        // set result best individual
        gresult.setResultBestIndividual(resultBestIndividual);
        
        results.add(gresult);
    }
}
