package com.ivo.web.modules.simplex.controllers;


import com.ivo.module.SimplexMethod;
import com.ivo.web.modules.simplex.beans.ArgsBean;
import com.ivo.web.modules.simplex.beans.FunctionBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author IOAdmin
 */
@ManagedBean
@SessionScoped
public class ConstraintsController implements Serializable {

    private List<Integer> argsList;
    private List<Integer> constraintsList;
    
    // constraints
    private final List<ArgsBean> argValues = new ArrayList<>();
    // goal func
    private final List<FunctionBean> funcValues = new ArrayList<>();
    private double freeMemberC;
    
    // condition
    private String maxOrMin = "min";
    
    private int countArgs;
    private int countConstraints;
    
    // results
    private StringBuilder answer = new StringBuilder();
    
    /**
     * Creates a new instance of ConstraintsBean
     */
    public ConstraintsController() {
    }
    
    public String handleFirstStep() {
        if (countArgs != 0 && countConstraints != 0) {
            return "step2";
        }
        return "error_page";
    }
    
    public String handleSecondStep() {
        if (!argValues.isEmpty()) {
            answer.append("Решим прямую задачу линейного программирования симплексным методом, "
                    + "с использованием симплексной таблицы").append("<br/>");
            
            Double[] values = new Double[argValues.get(0).getValues().length];
 
            // CREATE A IN TABLE VALUES
            double[] result = new double[countArgs];
            // read constraints value
            // + 1 goal function
            // + 1 valueCondition
            double[][] table = new double[countConstraints + 1][countArgs + 1];
            
            ArgsBean argBean;
            for (int i = 0; i < argValues.size(); i++) {
                argBean = argValues.get(i);
                if (maxOrMin.equals("min")) {
                    answer.append("Определим минимальное значение целевой функции F(X) = ")
                            .append(getStringGoalFunc())
                            .append(" при следующих условиях-ограничений.")
                            .append(getStringConstraints());
                    values = new Double[argBean.getValues().length];
                    if (argBean.getCondition().equals("≤")) {
                        for (int j = 0; j < argBean.getValues().length; j++) {
                            values[j] = -(argBean.getValues()[j]);
                        }
                    } else {
                        for (int j = 0; j < argBean.getValues().length; j++) {
                            values[j] = argBean.getValues()[j];
                        }
                    }
                }
                if (maxOrMin.equals("max")) {
                    values = new Double[argBean.getValues().length];
                    if (argBean.getCondition().equals("≥")) {
                        for (int j = 0; j < argBean.getValues().length; j++) {
                            values[j] = -(argBean.getValues()[j]);
                        }
                    } else {
                        for (int j = 0; j < argBean.getValues().length; j++) {
                            values[j] = argBean.getValues()[j];
                        }
                    }
                }
                
                table[i][0] = argValues.get(i).getValueCondition();
                for (int j = 0; j < values.length; j++) {
                    table[i][j + 1] = values[j];
                }
            }
            
            // read the goal func values
            int n = table.length - 1;
            table[n][0] = 0;
            for (int i = 0; i < funcValues.size(); i++) {
                if (maxOrMin.equals("max")) {
                    table[n][i+1] = -(funcValues.get(i).getValue());
                } else {
                   table[n][i+1] = funcValues.get(i).getValue(); 
                }
            }
            
            for (int i = 0; i < table.length; i++) {
                for (int j = 0; j < table[i].length; j++) {
                    System.out.print(table[i][j] + " ");
                } 
                System.out.println("");
            }
            
            SimplexMethod s = new SimplexMethod(table);
            s.calculate(result);
            
            double answer = 0;
            double valueFunc = 0;
            for (int i = 0; i < result.length; i++) {
                answer += result[i] * funcValues.get(i).getValue();
            }
            
            System.out.println("F = " + (answer + freeMemberC));
            return "step3";
        }
        return "error";
    }
    
    private String getStringGoalFunc() {
        StringBuilder goalFunc = new StringBuilder();
        for (FunctionBean bean : funcValues) {
            goalFunc.append(bean.getValue()).append("x" + bean.getNameArg()).append("+");
        }
        goalFunc.append(freeMemberC);
        
        return goalFunc.toString();
    }
    
    private String getStringConstraints() {
        StringBuilder constraints = new StringBuilder();
        StringBuilder tmpconstraints;
        for (ArgsBean bean : argValues) {
            tmpconstraints = new StringBuilder();
            Double[] values = bean.getValues();
            for (int i = 0; i < values.length; i++) {
                tmpconstraints.append(values[i]).append("x").append(i+1).append("+");
            }
            constraints.append(tmpconstraints.toString().substring(0, tmpconstraints.toString().length() - 1));
            constraints.append(" = ").append(bean.getValueCondition()).append("\r\n");
        }
 
        return constraints.toString();
    }

    public int getCountArgs() {
        return countArgs;
    }

    public void setCountArgs(int countArgs) {
        this.countArgs = countArgs;
        
        argsList = new ArrayList<>();
        for (int i = 0; i < countArgs; i++) {
            argsList.add(i);
        }
    }

    public int getCountConstraints() {
        return countConstraints;
    }

    public void setCountConstraints(int countConstraints) {
        this.countConstraints = countConstraints;
        
        constraintsList = new ArrayList<>();
        for (int i = 0; i < countConstraints; i++) {
            constraintsList.add(i);
        }
    }

    public List<Integer> getArgsList() {
        return argsList;
    }

    public List<Integer> getConstraintsList() {
        return constraintsList;
    }
    
    public List<ArgsBean> setValuesArgs() {
        if (!argValues.isEmpty()) {
            argValues.clear();
        }
        for (int i = 0; i < countConstraints; i++) {
            argValues.add(new ArgsBean());
        }
        return argValues;
    }

    public List<ArgsBean> getArgsValues() {
        return argValues;
    }
    
    public List<FunctionBean> setValuesFunc() {
        if (!funcValues.isEmpty()) {
            funcValues.clear();
        }
        // называем переменные
        // x1, x2, x3 ... xn
        for (int i = 0; i < countArgs; i++) {
            FunctionBean f = new FunctionBean();
            f.setNameArg(i + 1);
            funcValues.add(f);
        }
        return funcValues;
    }

    public List<FunctionBean> getFuncValues() {
        return funcValues;
    }

    public double getFreeMemberC() {
        return freeMemberC;
    }

    public void setFreeMemberC(double freeMemberC) {
        this.freeMemberC = freeMemberC;
    }

    public String getMaxOrMin() {
        return maxOrMin;
    }

    public void setMaxOrMin(String maxOrMin) {
        this.maxOrMin = maxOrMin;
    }

    public StringBuilder getAnswer() {
        return answer;
    }

    public void setAnswer(StringBuilder answer) {
        this.answer = answer;
    }
}
