package sample;

public interface UserStatisticVistor {
    public double visitUserTotal(UserTotal userTotal);
    public double visitGroupTotal(GroupTotal groupTotal);
    public double visitMessageTotal(MessageTotal messageTotal);
    public double visitPositivePercentage(PositivePercentage positivePercentage);
}
