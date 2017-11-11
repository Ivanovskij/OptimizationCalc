package com.ivo.beans;

/**
 *
 * @author IOAdmin
 */
public class ResultBean {

    private String[][] iterResults;   // iterations results
    private String description;       // iteration description

    public ResultBean() {
    }

    public ResultBean(String[][] iterResults, String discourse) {
        this.iterResults = iterResults;
        this.description = discourse;
    }

    public String[][] getIterResults() {
        return iterResults;
    }

    public void setIterResults(String[][] iterResults) {
        this.iterResults = iterResults;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
