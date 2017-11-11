package com.ivo.module;

import com.ivo.beans.ResultBean;
import com.ivo.utils.RoundUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplex method Outputs a description to each iteration
 *
 * @author IOAdmin
 */
public class SimplexMethod {

    private final List<ResultBean> resultList;    // result on each iteration
    private double[] argsFunc;
    private String[][] namesRowsAndCols;
    private double[] resultX;   // result values x

    // table without basis vars
    private double[][] table;
    // free constant in the goal function
    private final double freeMemberC;

    // size m - cols, n - rows
    private int colCount, rowCount;

    // basis
    private List<Integer> basis;

    /**
     *
     * @param source - contains last column values: 1 or -1 dependens from (max
     * or min) and condition (>=, <=)
     */
    public SimplexMethod(double[][] source, double freeMemberC) {
        this.freeMemberC = freeMemberC;
        
        resultList = new ArrayList<>();

        // get canonical form source
        double[][] canonicalSource = toCanonical(source);

        // set args goal function
        setArgsFunc(canonicalSource);

        initSimplexTable(canonicalSource);
        // getMathModel - give old source for parse
        setResultCurrentIter(namesRowsAndCols,
                getMathModel(source) + "Принимая свободные переменные равными 0, получаем первый опорный план:");

        // search optimal plan
        selectionSupportingPlan();
    }

    public List<ResultBean> calculate(double[] result) {
        iterations();
        getResultsX();

        return resultList;
    }
    
    private double[][] toCanonical(double[][] source) {
        colCount = source.length;
        rowCount = source[0].length - 1;

        double[][] result = new double[colCount][rowCount];
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                result[i][j] = source[i][j] * source[i][rowCount];
            }
        }

        return result;
    }
    
    private void initSimplexTable(double[][] source) {
        colCount = source.length;
        rowCount = source[0].length;

        table = new double[colCount][rowCount + colCount - 1];
        basis = new ArrayList<>();
        resultX = new double[rowCount - 1];

        // create basis
        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (j < rowCount) {
                    table[i][j] = source[i][j];
                } else {
                    table[i][j] = 0;
                }
            }
            // add coefficient 1 before basis var in row
            if ((rowCount + i) < table[i].length) {
                table[i][rowCount + i] = 1;
                basis.add(rowCount + i);
            }
        }

        rowCount = table[0].length;

        // create the table of description for zerow iteration
        namesRowsAndCols = new String[table.length + 1][table[0].length + 1];
        namesRowsAndCols[0][0] = "Базис";
        namesRowsAndCols[0][1] = "B";
        namesRowsAndCols[namesRowsAndCols.length - 1][0] = "F(X)";

        int countArgs = source[0].length;
        for (int i = 1; i < namesRowsAndCols.length - 1; i++) {
            namesRowsAndCols[i][0] = "x" + (countArgs++);
        }

        for (int i = 2; i < namesRowsAndCols[0].length; i++) {
            namesRowsAndCols[0][i] = "x" + (i - 1);
        }

        for (int i = 1; i < namesRowsAndCols.length; i++) {
            for (int j = 1, k = i - 1; j < namesRowsAndCols[0].length; j++) {
                namesRowsAndCols[i][j] = String.valueOf(table[k][j - 1]);
            }
        }
    }
    
    private void selectionSupportingPlan() {
        while (!isOptimal()) {
            int mainRow = -1;
            int mainCol = -1;

            for (int i = 0; i < rowCount; i++) {
                if (table[i][0] < 0) {
                    mainRow = i;
                    break;
                }
            }

            mainCol = 1;
            for (int i = 2; i < colCount; i++) {
                if (table[mainRow][mainCol] > table[mainRow][i]) {
                    mainCol = i;
                }
            }

            String description = "Среди свободных членов bi имеются отрицательные значения, "
                    + "следовательно, полученный базисный"
                    + System.lineSeparator()
                    + "план не является опорным."
                    + System.lineSeparator()
                    + "Выполняем преобразования симплексной таблицы:"
                    + System.lineSeparator()
                    + "Пересчет симплекс-таблицы: вместо переменной "
                    + namesRowsAndCols[mainRow + 1][0]
                    + " в план войдет переменная "
                    + namesRowsAndCols[0][mainCol + 1]
                    + ".";

            updateTable(mainRow, mainCol);
            setResultCurrentIter(namesRowsAndCols, description);
            doubleValuesToString();
            setResultCurrentIter(namesRowsAndCols, "Получаем новую симплекс таблицу");
        }
    }

    private boolean isOptimal() {
        for (int i = 0; i < colCount; i++) {
            if (table[i][0] < 0) {
                return false;
            }
        }
        return true;
    }

    private void iterations() {
        int currentIter = 0;
        int mainCol, mainRow;
        String description;

        while (!isItEnd()) {
            mainCol = findMainCol();
            mainRow = findMainRow(mainCol);

            basis.set(mainRow, mainCol);

            double mainElement = table[mainRow][mainCol];

            description = (currentIter == 0) ? "Переходим к основному алгоритму симплекс-метода."
                    + System.lineSeparator() : "";

            description += "Итерация " + (currentIter++) + "." + System.lineSeparator()
                    + " 1. Проверка критерия оптимальности: текущий опорный план не оптимален, так как в индексной строке"
                    + System.lineSeparator()
                    + "     находятся отрицательные коэффициенты."
                    + System.lineSeparator()
                    + " 2. Определение новой базисной переменной: в качестве ведущего берется столбец, соответствующий "
                    + System.lineSeparator()
                    + "     переменной x" + mainCol + ", так как это наибольший коэффициент по модулю("
                    + table[table.length - 1][mainCol] + ")."
                    + System.lineSeparator()
                    + " 3. Определение новой свободной переменной: вычисляются значения Di по строкам как частное от "
                    + System.lineSeparator()
                    + "     деления: bi / ai" + mainCol + " и из них выбирается наименьшее("
                    + (table[mainRow][0] / table[mainRow][mainCol]) + "). "
                    + System.lineSeparator()
                    + "     Следовательно, " + (mainRow + 1) + "-ая строка является ведущей."
                    + System.lineSeparator()
                    + "     Разрешающий элемент равен (" + mainElement + ")"
                    + System.lineSeparator()
                    + " и находится на пересечении ведущего столбца и ведущей строки."
                    + System.lineSeparator();

            setResultCurrentIter(namesRowsAndCols, description);

            String oldGorizontallyX = namesRowsAndCols[mainRow + 1][0];
            
            updateTable(mainRow, mainCol);

            description = "4. Пересчет симплекс-таблицы: вместо переменной "
                    + oldGorizontallyX
                    + " в план войдет переменная "
                    + namesRowsAndCols[0][mainCol + 1]
                    + "."
                    + System.lineSeparator()
                    + "    Каждый элемент разрешающей строки делим на значение разрешающего элемента и получаем"
                    + System.lineSeparator()
                    + "    новые данные. Остальные элементы таблицы пересчитываем по правилу прямоугольника."
                    + System.lineSeparator();
            
            setResultCurrentIter(namesRowsAndCols, description);
            
            doubleValuesToString();
            
            setResultCurrentIter(namesRowsAndCols, description);
        }

        description = "Итерация "
                + (currentIter)
                + "."
                + System.lineSeparator()
                + " 1. Проверка критерия оптимальности: текущий опорный план оптимален, так как в индексной строке"
                + System.lineSeparator()
                + "     нет отрицательных значений."
                + System.lineSeparator()
                + System.lineSeparator()
                + "Окончательный вариант симплекс-таблицы";

        setResultCurrentIter(namesRowsAndCols.clone(), description);
    }

    private boolean isItEnd() {
        boolean flag = true;

        for (int j = 1; j < rowCount; j++) {
            if (table[colCount - 1][j] < 0) {
                flag = false;
                break;
            }
        }

        return flag;
    }

    private int findMainCol() {
        int mainCol = 1;

        for (int j = 2; j < rowCount; j++) {
            if (table[colCount - 1][j] < table[colCount - 1][mainCol]) {
                mainCol = j;
            }
        }

        return mainCol;
    }

    private int findMainRow(int mainCol) {
        int mainRow = 0;

        for (int i = 0; i < colCount - 1; i++) {
            if (table[i][mainCol] > 0) {
                mainRow = i;
                break;
            }
        }

        for (int i = mainRow + 1; i < colCount - 1; i++) {
            if ((table[i][mainCol] > 0) && ((table[i][0] / table[i][mainCol]) < (table[mainRow][0] / table[mainRow][mainCol]))) {
                mainRow = i;
            }
        }

        return mainRow;
    }
    
    private void updateTable(int mainRow, int mainCol) {
        double mainElem = table[mainRow][mainCol];

        double[][] newTable = new double[colCount][rowCount];

        for (int i = 0; i < colCount; i++) {
            for (int j = 0, k = i + 1; j < rowCount; j++) {
                if (i == mainRow) {
                    newTable[mainRow][j] = RoundUtil.round(table[mainRow][j] / mainElem, 3);
                    namesRowsAndCols[k][j + 1] = table[mainRow][j] + "/" + mainElem;
                } else {
                    newTable[i][j] = RoundUtil.round(table[i][j] - (table[i][mainCol] * table[mainRow][j]) / mainElem, 3);
                    namesRowsAndCols[k][j + 1] = table[i][j] + "-("
                            + table[i][mainCol] + "*" + table[mainRow][j]
                            + ")/" + mainElem;
                }
            }
        }

        table = newTable;
        // replace x from gorizontal row to vertical row
        namesRowsAndCols[mainRow + 1][0] = namesRowsAndCols[0][mainCol + 1];
    }

    private void setResultCurrentIter(String[][] arr, String description) {
        String[][] clone = new String[arr.length][arr[0].length];
        
        for (int i = 0; i < arr.length; i++) {
            System.arraycopy(arr[i], 0, clone[i], 0, arr[0].length);
        }
        
        resultList.add(new ResultBean(clone, description));
    }

    private void setArgsFunc(double[][] source) {
        colCount = source.length;
        rowCount = source[0].length;

        argsFunc = new double[rowCount - 1];

        // Math.abs() for
        // since the canonical form can make negative elements
        for (int i = 0; i < rowCount - 1; i++) {
            argsFunc[i] = Math.abs(source[colCount - 1][i + 1]);
        }
    }
    
    public double[] getResultsX() {
        for (int i = 0; i < resultX.length; i++) {
            for (int j = 1; j < namesRowsAndCols.length; j++) {
                String x = "x" + (i + 1);
                if (namesRowsAndCols[j][0].equals(x)) {
                    resultX[i] = Double.parseDouble(namesRowsAndCols[j][1]);
                }
            }
        }

        return resultX;
    }

    public double getResultGoalFunc() {
        double value = 0;
        for (int i = 0; i < argsFunc.length; i++) {
            value += argsFunc[i] * resultX[i];
        }

        value += freeMemberC;
        return value;
    }
    
    private String getMathModel(double[][] source) {
        final int colCount = source.length;
        final int rowCount = source[0].length;

        String description = "Решение прямой задачи линейного программирования симплексным методом, с использованием"
                + System.lineSeparator()
                + "симплексной таблицы."
                + System.lineSeparator()
                + "Требуется определить значение целевой функции F(X) = "
                + source[colCount - 1][1]
                + "x" + 1;

        for (int i = 2; i < rowCount - 1; i++) {
            description += (source[colCount - 1][i] >= 0 ? " + " : " - ") 
                    + Math.abs(source[colCount - 1][i]) + "x" + (i);
        }

        if (freeMemberC != 0) {
            description += (freeMemberC > 0 ? " + " : " - ") + freeMemberC;
        }

        description += (source[colCount - 1][rowCount - 1] == -1 ? " => max" : " => min") + " при"
                + System.lineSeparator()
                + " следующих условиях-ограничениях:"
                + System.lineSeparator();

        // out constraints
        for (int i = 0; i < colCount - 1; i++) {
            description += " " + source[i][1] + "x1";
            for (int j = 2; j < rowCount - 1; j++) {    // last col for signs
                if (source[i][j] != 0) {
                    description += (source[i][j] >= 0 ? " + " : " - ") + source[i][j] + "x" + (j);
                }
            }
            description += " " + (source[i][rowCount - 1] == 1 ? " <= " : " >= ") + source[i][0]
                    + System.lineSeparator();
        }

        return description;
    }
    
    private void doubleValuesToString() {
        for (int i = 1; i < colCount + 1; i++) {
            for (int j = 1, k = i - 1; j < rowCount + 1; j++) {
                namesRowsAndCols[i][j] = String.valueOf(table[k][j - 1]);
            }
        }
    }
}
