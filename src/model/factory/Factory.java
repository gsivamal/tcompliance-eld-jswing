package model.factory;

public abstract class Factory {

    private int count = 0;


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
