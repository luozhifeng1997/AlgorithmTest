package action;

import java.util.List;
import java.util.Map;

public class RemoveAction {
    private Map<Long, Long> needRemove;
    private volatile List<Long> allNums;
    private long startIndex;
    private long endIndex;

    public RemoveAction(Map<Long, Long> needRemove, List<Long> allNums, long startIndex, long endIndex) {
        this.needRemove = needRemove;
        this.allNums = allNums;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    /**
     * 每个线程删除自己分区内存在的合数
     */
    public void startTask() {
        for(long i=startIndex;i<endIndex;i++){
            if(needRemove.containsKey(allNums.get((int)i))){
                allNums.set((int)i,(long)0);
            }
        }
    }

}
