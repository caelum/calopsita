package br.com.caelum.calopsita.repository;

public interface BaseRepository<T> {
    void add(T t);

    void update(T t);

    void remove(T t);
}
