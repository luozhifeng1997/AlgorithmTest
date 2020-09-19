package utils;

import action.ComputeAction;
import action.RemoveAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class PrimeUtils {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(8);
    public static final int TASK_SPILT_LENGTH = 20000;
    public static Long computeRangePrimeSum(int maxNum) throws Exception {
        return getRangeAllNums(maxNum).parallelStream().filter(item->isprime(item)).mapToLong(Long::longValue).sum();
    }
    /**
     * 获取范围内的所有数（list形式）
     * @param maxNum
     * @return
     */
    public static List<Long> getRangeAllNums(int maxNum) {
        List<Long> allNums = new ArrayList<>(maxNum);
        for (long i = 1; i <= maxNum; i++) {
            allNums.add(i);
        }
        return allNums;
    }
    public  static boolean isprime(long number){
        for(long i=2;i<=Math.sqrt(number);i++){
            if(number%i==0){
                return false;
            }
        }
        return true;
    }
}

