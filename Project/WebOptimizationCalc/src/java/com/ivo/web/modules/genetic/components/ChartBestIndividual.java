package com.ivo.web.modules.genetic.components;

import com.ivo.module.GeneticResult;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

/**
 *
 * @author IOAdmin
 */
public class ChartBestIndividual implements Serializable {

    private LineChartModel lineModel;
    private List<GeneticResult> resultPoints;

    public ChartBestIndividual() {
    }

    public ChartBestIndividual(List<GeneticResult> resultPoints) {
        this.resultPoints = resultPoints;
        
        createLineModels();
    }

    private void createLineModels() {
        lineModel = initCategoryModel();
        lineModel.setTitle("График лучших особей в каждом поколении");
        lineModel.setLegendPosition("e");
        lineModel.getAxes().put(AxisType.X, new CategoryAxis("Поколение"));
        
        Axis yAxis = lineModel.getAxis(AxisType.Y);
        yAxis.setLabel("Особи");
        
        // set axis min, max
        double min = resultPoints.stream().min(new Comparator<GeneticResult>() {
            @Override
            public int compare(GeneticResult g1, GeneticResult g2) {
                return Double.compare(g1.getResultBestIndividual(), g2.getResultBestIndividual());
            }
        }).get().getResultBestIndividual();
        
        double max = resultPoints.stream().max(new Comparator<GeneticResult>() {
            @Override
            public int compare(GeneticResult g1, GeneticResult g2) {
                return Double.compare(g1.getResultBestIndividual(), g2.getResultBestIndividual());
            }
        }).get().getResultBestIndividual();
        
        // inc bounds, for normally view result
        min -= 5;
        max += 5;
        
        yAxis.setMin(min);
        yAxis.setMax(max);
    }

    private LineChartModel initCategoryModel() {
        LineChartModel model = new LineChartModel();
        
        ChartSeries x_Generation = new ChartSeries();
        x_Generation.setLabel("Значение особи");
        
        int step = 4;
        
        // values
        for (int i = 0; i < resultPoints.size(); i += step) {
            double resultBestInd = resultPoints.get(i).getResultBestIndividual();
            x_Generation.set(i, resultBestInd);
        }

        model.addSeries(x_Generation);
        
        return model;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }
}
