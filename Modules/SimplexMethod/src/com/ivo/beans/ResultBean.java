package com.ivo.beans;

/**
 *
 * @author IOAdmin
 */
public class ResultBean {

    private String[][] iterResults;   // iterations results
    private int countRow;             // need for out in web app
    private int countCol;             // need for out in web app  
    private String description;       // iteration description

    public ResultBean() {
    }

    public ResultBean(String[][] iterResults, String discourse) {
        this.iterResults = iterResults;
        this.countCol = iterResults.length;
        this.countRow = iterResults[0].length;
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

    public int getCountRow() {
        return countRow;
    }

    public int getCountCol() {
        return countCol;
    }
}
