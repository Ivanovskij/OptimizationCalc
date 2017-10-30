package com.ivo.module;

import java.util.List;

/**
 *
 * @author IOAdmin
 */
public interface BaseGenetic {

    List<Chromosome> solve();
    
    void createPopulation();

    void selection();
    
    void crossover();
    
    void mutation();

    void setCurrentGeneration(Chromosome[] generation);
    
}
