package class_define;

public interface Subject {
    /**
     * 增加订阅者
     * @param observer
     */
    public void attach(Observer observer, Observer observer2);
    /**
     * 删除订阅者
     * @param observer
     */
    public void detach(Observer observer, Observer observer2);
    /**
     * 通知订阅者更新消息
     */
//    public void notify(String message);
}
