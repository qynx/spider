package xyz.zix.spider.control.adapter;

public interface VoAdapter<D, V> {

    D toD(V v);

    V toV(D d);

    D iToD(V v);

    V iToV(D d);

}
