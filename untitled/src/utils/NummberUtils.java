package utils;

import action.ComputeAction;
import action.RemoveAction;
import test.RoyOneThreadComputeTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class NummberUtils {
    public static final ExecutorService executorService = Executors.newFixedThreadPool(8);
    public static final long TASK_SPILT_LENGTH = 2000;
    public static Long computeRangePrimeSum(Long maxNum) throws Exception {
        /**
         * 获取范围内的所有基数
         */
        List<Long> rangeAllNums = getRangeAllNums(maxNum);
        /**
         * 并发判断获取范围内的所有合
         * 因为合数容易快速过半判断，并整理成Map方便批量的是实现并发删除
         */
        Map<Long, Long> needDeleteCompositeNums = getRangeCompositeNumsByMap(maxNum);
        /**
         * 利用map实现并发删除，每个线程负责一部分删除
         */
        List<Long> primeNums = corruentDelele(rangeAllNums, needDeleteCompositeNums);
        return primeNums.parallelStream().mapToLong(item -> item).sum();
    }

    /**
     * 获取范围内的所有数（list形式）
     * @param maxNum
     * @return
     */
    public static List<Long> getRangeAllNums(Long maxNum) {
        List<Long> allNums = new ArrayList((int) maxNum.longValue());
        for (long i = 1; i <= maxNum; i++) {
            allNums.add(i);
        }
        return allNums;
    }
    /**
     * 获取范围内所有合数（map形式）
     * @param maxNum
     * @return
     * @throws Exception
     */
    public static Map<Long, Long> getRangeCompositeNumsByMap(Long maxNum) throws Exception {
        return getRangeCompositeNums(maxNum).parallelStream().distinct().collect(Collectors.toMap(item -> item.longValue(), item -> item.longValue()));
    }
    /**
     * 获取范围内所有合数（list形式）
     * @param maxNum
     * @return
     */
    public static List<Long> getRangeCompositeNums(Long maxNum) throws Exception {
        /**
         * 【start】——————线程任务计数器
         */
        CountDownLatch countDownLatch = new CountDownLatch((int)(maxNum / TASK_SPILT_LENGTH));
        /**
         * 单线程———————分配任务
         */
        List<Long> compositeNums = new Vector<>();
        List<ComputeAction> computeActions = new ArrayList<>();
        for (long i = 0; i <= maxNum - TASK_SPILT_LENGTH; i = i + TASK_SPILT_LENGTH) {
            computeActions.add(new ComputeAction(compositeNums, i, i + TASK_SPILT_LENGTH));
        }
        /**
         * 多线程——————执行任务
         */
        computeActions.forEach(item -> {
            executorService.submit(new Runnable() {
                public void run() {
                    item.startTask();
                    countDownLatch.countDown();
                }
            });
        });
        /**
         * 【end】——————所有线程任务结束后才退出阻塞
         */
        countDownLatch.await();
        return compositeNums;
    }

    private static List<Long> corruentDelele(List<Long> allNums, Map<Long, Long> needRemove) throws Exception {
        /**
         * 【start】——————线程任务计数器
         */
        CountDownLatch countDownLatch = new CountDownLatch((int)(allNums.size() / TASK_SPILT_LENGTH - 1));
        List<RemoveAction> actions = new ArrayList<>();
        /**
         * 单线程———————分配任务
         */
        for (long i = 0; i <= allNums.size(); i = i + TASK_SPILT_LENGTH) {
            long startIndex = i;
            long endIndex = i + TASK_SPILT_LENGTH;
            actions.add(new RemoveAction(needRemove, allNums, startIndex, endIndex));
        }
        /**
         * 多线程——————执行任务
         */
        actions.forEach(item -> {
            executorService.submit(new Runnable() {
                public void run() {
                    item.startTask();
                    countDownLatch.countDown();
                }
            });
        });
        /**
         * 【end】——————所有线程任务结束后才退出阻塞
         */
        countDownLatch.await();
        return allNums.parallelStream().filter(item->item.longValue()!=0).collect(Collectors.toList());
    }
}

