package de.Modex.arctice.skyblock.utils;

public class Tuple<A, B> {

    private A a;
    private B b;

    public Tuple(A object, B object1) {
        this.a = object;
        this.b = object1;
    }

    public A a() {
        return this.a;
    }

    public B b() {
        return this.b;
    }
}