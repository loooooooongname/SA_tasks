package sa_task;

public interface Subject {
    /**
     * 
     * @param observer
     */
    public void attach(Observer observer);
    /**
     * 
     * @param observer
     */
    public void detach(Observer observer);
    /**
     * 
     */
    public void notify(String message);
}