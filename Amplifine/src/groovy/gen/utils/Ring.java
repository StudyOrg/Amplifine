package gen.utils;

public class Ring {

    private int maxValue;
    private int baseValue;
    private int currentValue;

    public Ring(int baseValue, int maxValue) {

        this.baseValue = baseValue;
        this.maxValue = maxValue;
        this.currentValue = baseValue;

    }

    public Ring(int baseValue, int maxValue, int beginFrom) {

        this.baseValue = baseValue;
        this.maxValue = maxValue;
        this.currentValue = beginFrom;

    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int size() {
        return Math.abs(maxValue - baseValue);
    }

    public int nextInt() {

        if( currentValue > maxValue ) {
            currentValue = baseValue;
        }

        int tmp = currentValue;
        ++currentValue;

        return tmp;

    }

}
