//package action;
//
//import java.util.List;
//
//public class SumAction{
//    public List<Integer> nums;
//    private Integer startIndex;
//    private Integer endIndex;
//    public SumAction(List<Integer> nums, Integer startIndex, Integer endIndex) {
//        this.nums = nums;
//        this.startIndex = startIndex;
//        this.endIndex = endIndex;
//    }
//    /**
//     * 每个线程计算自己分区内哪些是合数
//     */
//    public int startTask() {
//        for (int i = startIndex; i <= endIndex; i++) {
//            if (i == 1||i==0) {
//                continue;
//            }
//            for (int j = 2;j<=i/2; j++) {
//                if (i % j == 0) {
//                    nums.add(i);
//                    break;
//                }
//            }
//        }
//    }
//}
