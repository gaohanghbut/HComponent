package cn.hang.core.util;

/**
 * Created by hang.gao on 2014/8/24.
 */
public class Pair<First, Second> {
    private final First first;
    private final Second second;

    public Pair(First first, Second second) {
        this.first = first;
        this.second = second;
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }
}
