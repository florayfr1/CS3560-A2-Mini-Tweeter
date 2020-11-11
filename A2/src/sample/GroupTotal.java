package sample;

public class GroupTotal implements UserStatistic {

    @Override
    public double accept(UserStatisticVistor visitor) {
        return visitor.visitGroupTotal(this);
    }

}
