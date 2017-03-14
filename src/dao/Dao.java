package dao;

public interface Dao<T,L> {
    void add(T item);
    void update(T item);
    void remove(T item);
    T get(int id);
    L getAll();
}
