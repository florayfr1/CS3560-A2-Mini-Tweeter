package sample;


import java.util.Arrays;

public class PositivePercentage implements UserStatistic {

    private String[] positiveWords;

    public PositivePercentage()
    {
        positiveWords = new String[]{"good", "nice", "useful", "interesting",
                "cheerful", "effective", "best", "great", "excellent", "perfect"};
    }

    @Override
    public double accept(UserStatisticVistor visitor) {
        return visitor.visitPositivePercentage(this);
    }

    public String[] getPositiveWords() {
        return positiveWords;
    }
}
