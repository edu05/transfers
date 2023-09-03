package org.example.transfersv6;

public interface Inbox<E> {

    void add(E e);
    E poll();


}
