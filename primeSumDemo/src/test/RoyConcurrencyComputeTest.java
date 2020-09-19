package test;

import utils.NummberUtils;
import utils.PrimeUtils;
import utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoyConcurrencyComputeTest {

    public static void main(String[] args) throws Exception {
        try {
            TimeUtils.recordTimeStap();
            System.out.println("素数和为："+ PrimeUtils.computeRangePrimeSum(500000));
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            TimeUtils.consoleTimeStapLength();
            TimeUtils.destoryTimeStap();
        }
    }
}
