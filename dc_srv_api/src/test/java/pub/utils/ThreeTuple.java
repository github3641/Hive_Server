package pub.utils;

import org.javatuples.Tuple;

public class ThreeTuple<E,T,K> extends Tuple {

    private E e;
    private T t;
    private K k;

    public ThreeTuple(E tableName, T partColumn, K other) {
        this.e = tableName;
        this.t = partColumn;
        this.k = other;
    }

    public E getTableName() {
        return e;
    }

    public void setTableName(E e) {
        this.e = e;
    }

    public T getPartColumn() {
        return t;
    }

    public void setPartColumn(T t) {
        this.t = t;
    }

    public K getOther() {
        return k;
    }

    public void setOther(K k) {
        this.k = k;
    }

    @Override
    public int getSize() {
        return 3;
    }
}
