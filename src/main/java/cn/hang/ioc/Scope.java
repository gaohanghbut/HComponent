package cn.hang.ioc;

import com.google.common.primitives.Ints;

/**
 * bean 的范围，可以是singleton或者prototype
 *
 * @author hang.gao
 */
public enum Scope implements Comparable<Scope> {

    SINGLETON(0), PROTOTYPE(1);

    int code;

    Scope(int code) {
        this.code = code;
    }

    public String toString() {
        return this.name().toLowerCase();
    }

}
