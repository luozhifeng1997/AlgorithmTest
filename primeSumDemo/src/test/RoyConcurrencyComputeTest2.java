package test;

import utils.NummberUtils;
import utils.PrimeUtils;
import utils.TimeUtils;

public class RoyConcurrencyComputeTest2 {

    public static void main(String[] args) throws Exception {
        try {
            TimeUtils.recordTimeStap();
            System.out.println("素数和为："+ NummberUtils.computeRangePrimeSum((long)500000));
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            TimeUtils.consoleTimeStapLength();
            TimeUtils.destoryTimeStap();
        }
        Long end=System.currentTimeMillis();
    }
}
