package class_define;

public interface Subject {
    /**
     * ���Ӷ�����
     * @param observer
     */
    public void attach(Observer observer, Observer observer2);
    /**
     * ɾ��������
     * @param observer
     */
    public void detach(Observer observer, Observer observer2);
    /**
     * ֪ͨ�����߸�����Ϣ
     */
//    public void notify(String message);
}
