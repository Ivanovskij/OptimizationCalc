package com.ivo.utils;

import com.ivo.ParserExecute;

/**
 *
 * @author IOAdmin
 */
public class Function {

    public static final String func = "21 * x1 + 18 * x2 + 16 * x3 + 17.5 * x4";
    
    private String goalFunction;
    private String[] constrains;
    private String[] constraintsWithOutCondition;
    
    private ParserExecute parser; 

    public Function(String goalFunction, String[] constrains, String[] constraintsWithOutCondition) {
        this.goalFunction = goalFunction;
        this.constrains = constrains;
        this.constraintsWithOutCondition = constraintsWithOutCondition;
    }
    
    public double getValueGoalFunction(Double[] args) {
        double result = new ParserExecute(func, args).execute();
        return result;
    }  
    
    public double isInBoundsStaticPenalty(Double[] args) {
        int inBound;
        double valueConstraintWithoutCondition = 0;
        double result = 0;
        
        for (int i = 0; i < constrains.length; i++) {
            inBound = (int) new ParserExecute(constrains[i], args).execute();
            if (inBound != 1) {
                valueConstraintWithoutCondition = new ParserExecute(constraintsWithOutCondition[i], args).execute();
                result -= Math.pow(valueConstraintWithoutCondition, 2) + 1_000_000;
            }
        }
        
        return result;
    }
    
    public boolean isInBounds(Double[] args) {
        int result;
        
        for (int i = 0; i < constrains.length; i++) {
            result = (int) new ParserExecute(constrains[i], args).execute();
            if (result != 1) {
                return false;
            }
        }
        
        return true;
    }
    
    /* ================================================= */
    public String getGoalFunction() {
        return goalFunction;
    }

    public void setGoalFunction(String goalFunction) {
        this.goalFunction = goalFunction;
    }

    public String[] getConstrains() {
        return constrains;
    }

    public void setConstrains(String[] constrains) {
        this.constrains = constrains;
    }

    public String[] getConstraintsWithOutCondition() {
        return constraintsWithOutCondition;
    }

    public void setConstraintsWithOutCondition(String[] constraintsWithOutCondition) {
        this.constraintsWithOutCondition = constraintsWithOutCondition;
    }

//    public static final double f(Double[] args) {
//        return 21 * args[0] + 18 * args[1] + 16 * args[2] + 17.5 * args[3];
//    }

    
    // метод динамических штрафов
//    public static double insideBounds(String in, int[] args, int curGeneration) {
//        double result = 0;
//        
//        double c = 0.5;
//        double a = 2, b = 2;
//
//        double R = Math.pow(c * curGeneration, a);
//
//        
//        if (!(8 * args[0] + 7 * args[1] + 5 * args[2] + 9 * args[3] <= 22)) {
//            result += -1 * R * Math.pow(8 * args[0] + 7 * args[1] + 5 * args[2] + 9 * args[3], b);
//        }
//        if (!(8 * args[0] + 9 * args[1] + 7 * args[2] + 8 * args[3] <= 25)) {
//            result += -1 * R * Math.pow(8 * args[0] + 9 * args[1] + 7 * args[2] + 8 * args[3], b);
//        }
//        if (!(10 * args[0] + 9* args[1] + 9 * args[2] + 7 * args[3] <= 38)) {
//            result += -1 * R * Math.pow(10 * args[0] + 9* args[1] + 9 * args[2] + 7 * args[3], b);
//        }
//        if (!(10 * args[0] + 11 * args[1] +  11 * args[2] + 6 * args[3] <= 30)) {
//            result += -1 * R * Math.pow(10 * args[0] + 11 * args[1] +  11 * args[2] + 6 * args[3], b);
//        }
//        if (!(args[0] >= 0)) {
//            result += -1 * R * Math.pow(args[0], b);
//        }
//        if (!(args[1] >= 0)) {
//            result += -1 * R * Math.pow(args[1], b);
//        }
//        if (!(args[2] >= 0)) {
//            result += -1 * R * Math.pow(args[2], b);
//        }
//        if (!(args[3] >= 0)) {
//            result += -1 * R * Math.pow(args[3], b);
//        }
//    
//        return result;
//        
//    }
    
   /* private static boolean bound5(Double[] args) {
        for (int i = 0; i < args.length; i++) {
            if (!(args[i] >= 0)) {
                return false;
            }
        }
        return true;
    }*/
    
//    public static boolean insideBounds(String in, Double[] args) {
//        return 8 * args[0] + 7 * args[1] + 5 * args[2] + 9 * args[3] <= 22 && 
//                8 * args[0] + 9 * args[1] + 7 * args[2] + 8 * args[3] <= 25 &&
//                10 * args[0] + 9* args[1] + 9 * args[2] + 7 * args[3] <= 38 &&
//                10 * args[0] + 11 * args[1] +  11 * args[2] + 6 * args[3] <= 30 &&
//                bound5(args);
//    } 

    
    // method static penalty
//    public static double insideBounds(Double[] args) {
//        double result = 0;
//        
//        double x1 = args[0],
//                x2 = args[1],
//                x3 = args[2],
//                x4 = args[3];
//        
//        boolean[] bounds = {
//            8 * x1 + 7 * x2 + 5 * x3 + 9 * x4 <= 22,
//            8 * x1 + 9 * x2 + 7 * x3 + 8 * x4 <= 25,
//            10 * x1 + 9 * x2 + 9 * x3 + 7 * x4 <= 38,
//            10 * x1 + 11 * x2 +  11 * x3 + 6 * x4 <= 30
//        };
//        
//        double[] boundsRight = {
//            8 * x1 + 7 * x2 + 5 * x3 + 9 * x4,
//            8 * x1 + 9 * x2 + 7 * x3 + 8 * x4,
//            10 * x1 + 9 * x2 + 9 * x3 + 7 * x4,
//            10 * x1 + 11 * x2 +  11 * x3 + 6 * x4
//        };
//        
//        for (int i = 0; i < bounds.length; i++) {
//            if (bounds[i] == false) {
//                double res = boundsRight[i];
//                result -= Math.pow(res, 2) + 1_000_000;
//            }
//        }
//        
//        boolean[] boundsZero = {
//            b1(args),
//            b2(args),
//            b3(args),
//            b4(args),
//        };
//        
//        for (int i = 0; i < boundsZero.length; i++) {
//            if (boundsZero[i] == false) {
//                result -= Math.pow(args[i], 2) + 1_000_000;
//            }
//        }
//        
//        return result;
//    }
//    
//    private static boolean b1(Double[] args) {
//        return (args[0] >= 0);
//    }
//    private static boolean b2(Double[] args) {
//        return (args[1] >= 0);
//    }
//    private static boolean b3(Double[] args) {
//        return (args[2] >= 0);
//    }
//    private static boolean b4(Double[] args) {
//        return (args[3] >= 0);
//    }
    
   /* public static double insideBounds(Double[] args) {
        double result = 0;
        
        double x1 = args[0],
                x2 = args[1];
        
        boolean[] bounds = {
            3 * x1 + 3 * x2 <= 15,
            2 * x1 + 6 * x2 <= 18,
            4 * x1 + 0 * x2 <= 16,
            1 * x1 + 2 * x2 <= 8,
            x1 >= 0,
            x2 >= 0,
        };
        
        double[] boundsRight = {
            3 * x1 + 3 * x2,
            2 * x1 + 6 * x2,
            4 * x1 + 0 * x2,
            1 * x1 + 2 * x2,
            x1,
            x2
        };
        
        for (int i = 0; i < bounds.length; i++) {
            if (bounds[i] == false) {
                double res = boundsRight[i];
                result -= Math.pow(res, 2) + 1_000_000;
            }
        }
        
        return result;
    }
    
    public static boolean insideBounds(String in, Double[] args) {
        double x1 = args[0],
                x2 = args[1];
        return 3 * x1 + 3 * x2 <= 15 &&
            2 * x1 + 6 * x2 <= 18 &&
            4 * x1 + 0 * x2 <= 16 &&
            1 * x1 + 2 * x2 <= 8&&
            x1 >= 0&&
            x2 >= 0;
    } */
}
