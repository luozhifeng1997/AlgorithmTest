package action;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ComputeAction {
    public List<Long> nums;
    private Long startIndex;
    private Long endIndex;

    public ComputeAction(List<Long> nums, Long startIndex, Long endIndex) {
        this.nums = nums;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    /**
     * 每个线程计算自己分区内哪些是合数
     */
    public void startTask() {
        for (long i = startIndex; i <= endIndex; i++) {
            if (i == 1||i==0) {
                continue;
            }
            for (int j = 2;j<=i/2; j++) {
                if (i % j == 0) {
                    nums.add(i);
                    break;
                }
            }
        }

    }
}
