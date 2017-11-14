package com.ivo.web.modules.genetic.beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class GeneticParamsBean implements Serializable {

    private String xmin;
    private String xmax;
    
    private String max_generations;
    private String population_count;
    
    private String arg_size;

    public GeneticParamsBean() {
    }

    public String getXmin() {
        return xmin;
    }

    public void setXmin(String xmin) {
        this.xmin = xmin;
    }

    public String getXmax() {
        return xmax;
    }

    public void setXmax(String xmax) {
        this.xmax = xmax;
    }

    public String getMax_generations() {
        return max_generations;
    }

    public void setMax_generations(String max_generations) {
        this.max_generations = max_generations;
    }

    public String getPopulation_count() {
        return population_count;
    }

    public void setPopulation_count(String population_count) {
        this.population_count = population_count;
    }

    public String getArg_size() {
        return arg_size;
    }

    public void setArg_size(String arg_size) {
        this.arg_size = arg_size;
    }
}
