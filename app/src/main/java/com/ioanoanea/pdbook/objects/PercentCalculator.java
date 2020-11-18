package com.ioanoanea.pdbook.objects;

public class PercentCalculator {

    /** calculate what percent of a number is another number **/
    public int getPercent(double nr, double from){
        // calculate percent of a part form integer
        return (int) ((nr/from)*100);
    }


    /** calculate a value of a percent from a number **/
    public int getNumberPercent(double percent, double from){
        return (int) ((percent/100)*from);
    }

}
