package sample;

public class MessageTotal implements UserStatistic {

    @Override
    public double accept(UserStatisticVistor visitor) {
        return visitor.visitMessageTotal(this);
    }


}
