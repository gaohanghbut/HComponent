package cn.hang.ioc;

import java.util.Comparator;

/**
 * Created by hang.gao on 2014/8/23.
 */
public class ScopeComparator implements Comparator<Scope> {

    public static ScopeComparator getInstance() {
        return ScopeComparatorHolder.instance;
    }

    private static final class ScopeComparatorHolder {
        private static final ScopeComparator instance = new ScopeComparator();
    }

    private ScopeComparator() {
    }
    @Override
    public int compare(Scope o1, Scope o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o1 == o2) {
            return 0;
        }
        if (o1 == Scope.SINGLETON) {
            return 1;
        }
        return -1;
    }
}
