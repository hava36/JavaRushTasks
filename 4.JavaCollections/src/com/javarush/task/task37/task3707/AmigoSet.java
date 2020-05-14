package com.javarush.task.task37.task3707;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E,Object> map;

    public AmigoSet() {

        map = new HashMap<E,Object>();

    }

    public AmigoSet(Collection<? extends E> collection) {
        this.map = new HashMap<>(Math.max((int) (collection.size() / .75f) + 1, 16));
        addAll(collection);
    }


    private void writeObject(ObjectOutputStream s) throws IOException {

        s.defaultWriteObject();

        s.writeObject(map.size());
        for (E e:map.keySet()) {
            s.writeObject(e);
        }

        s.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        s.writeObject(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {

        s.defaultReadObject();

        int size = (int) s.readObject();

        Set<E> set = new HashSet<>();

        for (int i = 0; i<size; i++) {
            set.add((E) s.readObject());
        }

        int capacity = (int) s.readObject();
        float loadFactor = (float)s.readObject();

        map = new HashMap<>(capacity, loadFactor);
        for (E e: set) {
            map.put(e, PRESENT);
        }

    }

    @Override
    public boolean add(E e) {
        return map.put(e, PRESENT) == null;
    }

    @Override
    public Iterator iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o, map.get(o));
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Object clone() {

        AmigoSet<E> amigoSet = new AmigoSet<>();

        try {
            amigoSet.addAll(this);
            amigoSet.map.putAll((Map) this.map.clone());
        }
        catch (Exception e){
            throw new InternalError(e);
        }

        return amigoSet;
    }

}
