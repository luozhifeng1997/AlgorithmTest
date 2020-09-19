package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    static class TimeNode{
        private Long value;
        private TimeNode next;
        private TimeNode lastNode;

        public TimeNode getLastNode() {
            return lastNode;
        }

        public void setLastNode(TimeNode lastNode) {
            this.lastNode = lastNode;
        }

        public TimeNode(Long value, TimeNode next) {
            this.value = value;
            this.next = next;
        }
        public TimeNode(Long value) {
            this.value = value;
        }

        public Long getValue() {
            return value;
        }
        public void setValue(Long value) {
            this.value = value;
        }
        public TimeNode getNext() {
            return next;
        }
        public void setNext(TimeNode next) {
            this.next = next;
        }
    }
    private final static ThreadLocal<TimeNode> TIME_STAP=new ThreadLocal<>();
    public static void recordTimeStap(){
        TimeNode firstNode = TIME_STAP.get();
        if(firstNode==null){
            TIME_STAP.set(new TimeNode(System.currentTimeMillis()));
            return;
        }
        TimeNode current = firstNode;
        for(;;){
            if(current.getNext()==null){
                TimeNode newNode=new TimeNode(System.currentTimeMillis());
                current.setNext(newNode);
                firstNode.setLastNode(newNode);
                break;
            }
            current=current.getNext();
        }
    } 
    public static void consoleTimeStapLength(){
        SimpleDateFormat sdf=new SimpleDateFormat("mm分ss秒SSS毫秒");
        recordTimeStap();
        TimeNode firstNode = TIME_STAP.get();
        TimeNode lastNode = firstNode.getLastNode();
        long timeLength = lastNode.getValue()-firstNode.getValue();
        System.out.println("时间差是："+sdf.format(new Date(timeLength)));
    }
    public static void destoryTimeStap(){
        TIME_STAP.remove();
    }
}
