package test;

import utils.TimeUtils;


public class RoyOneThreadComputeTest {
    public  static boolean isprime(long number){
        for(long i=2;i<=Math.sqrt(number);i++){
            if(number%i==0){
                return false;
            }
        }
        return true;
    }
    public static long computeRangeSum(int max){
        long sum=3;
        for(long j=3;j<=max;j++){
            if(isprime(j)==true){
                sum+=j;
            }
        }
        return sum;
    }
    public static void main(String[] arg){
        try {
            TimeUtils.recordTimeStap();
            System.out.println("素数和为："+computeRangeSum( 500000));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            TimeUtils.consoleTimeStapLength();
            TimeUtils.destoryTimeStap();
        }

    }
}
