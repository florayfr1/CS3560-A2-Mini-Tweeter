package sample;

public class UserGroup implements User{
    private String groupId;
    private String id;
    private long creationTime;
    private long lastUpdateTime;

    public UserGroup(String id){
        groupId = id;
        creationTime = System.currentTimeMillis();
        lastUpdateTime = creationTime;
    }

    @Override
    public void setId(String id) {
        groupId = id;
    }

    @Override
    public String getId() {
        return groupId;
    }

    @Override
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
