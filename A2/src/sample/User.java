package sample;

public interface User {

    //This will set a user id
    public void setId(String id);

    //This will return a user id
    public String getId();

    public long getCreationTime();

    public long getLastUpdateTime();
    public void setLastUpdateTime(long time);
}
