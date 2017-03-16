package model.factory;

import java.util.HashMap;

public abstract class Factory<T> {

    private int count = 0;
    protected HashMap<Integer, T> cachedObjects = new HashMap<Integer, T>();

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount(){
        count++;
    }

    public void decrementCount(){
        count--;
    }
}
