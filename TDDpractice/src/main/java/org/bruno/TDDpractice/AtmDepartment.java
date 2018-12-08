package org.bruno.TDDpractice;

import org.bruno.TDDpractice.interfaces.Iterable;
import org.bruno.TDDpractice.interfaces.Iterator;

import java.util.ArrayList;

public class AtmDepartment implements Iterable {

    private ArrayList<Atm> listAtm;
    private ArrayList<Atm> listSubscribers;
    private long cash;

    public AtmDepartment(ArrayList<Atm> listAtm) {
        this.listAtm = listAtm;
        if(!listAtm.isEmpty()) {
            this.cash = acquireSum();
        } else this.cash = 0;
    }

    public void addAtm(Atm atm) {
        this.listAtm.add(atm);
        subscribe(atm);
    }

    public ArrayList<Atm> getListAtm() {
        return this.listAtm;
    }

    public long acquireSum() {
        Iterator<Atm> it = createIterator();
        long sum = 0;
        while(it.hasMore()) {
            sum = sum + it.getNext().getCash();
        }
        return sum;
    }

    public void subscribe(Atm atm) {
        if(listSubscribers == null) this.listSubscribers = new ArrayList<>();
        listSubscribers.add(atm);
    }

    public void unsubscribe(Atm atm) {
        listSubscribers.remove(atm);
    }

    public void notificate() {
        Iterator<Atm> it = createIterator();
        while (it.hasMore()){
            it.getNext().notificate();
        }
    }

    @Override
    public Iterator createIterator() {
        return new AtmDepartmentIteratorImpl<>(this.listAtm);
    }

    public void restoreAtmsState() {
        notificate();
    }

    public class AtmDepartmentIteratorImpl<T> implements Iterator<T> {

        private ArrayList<T> list;
        private int pointer;

        public AtmDepartmentIteratorImpl(ArrayList<T> list) {
            this.list = list;
        }

        @Override
        public T getNext() {
            if(hasMore())
                pointer++;
            return list.get(pointer-1);
        }

        @Override
        public boolean hasMore() {
            return (list.size() > pointer);
        }
    }
}
