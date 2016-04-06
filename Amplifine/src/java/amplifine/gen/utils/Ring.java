package amplifine.gen.utils;

import java.util.Iterator;

public class Ring implements Iterable<Integer>, Iterator<Integer> {

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

    public Iterator<Integer> iterator() {
        return this;
    }

    public boolean hasNext() {
        if (currentValue > maxValue) {
            currentValue = baseValue;
            return false;
        } else {
            return true;
        }
    }

    public Integer next() {
        int tmp = currentValue;
        ++currentValue;

        return tmp;
    }

    public int size() {
        return Math.abs(maxValue - baseValue);
    }

    @Deprecated
    public int nextInt() {

        if (currentValue > maxValue) {
            currentValue = baseValue;
        }

        int tmp = currentValue;
        ++currentValue;

        return tmp;

    }

}
