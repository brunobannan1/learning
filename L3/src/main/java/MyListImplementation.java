import java.util.*;


public class MyListImplementation<T> implements List<T> {

    private final int DEF_SIZE = 20;
    private int pointer;
    private int size;
    private Object[] array;

    public MyListImplementation() {
        this.pointer = 0;
        this.size = DEF_SIZE;
        this.array = new Object[size];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < size && array[currentIndex] != null;
            }

            @Override
            public T next() {
                return (T) array[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    @Override
    public boolean add(T t) {
        add(array.length - 1, t);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {

        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public T get(int index) {
        indexInsideArray(index);
        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    private int newCapacity(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = array.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (array == new Object[20])
                return Math.max(DEF_SIZE, minCapacity);
            if (minCapacity < 0) // overflow
                throw new OutOfMemoryError();
            return minCapacity;
        }
        return (newCapacity - (Integer.MAX_VALUE - 8) <= 0)
                ? newCapacity
                : -1;
    }

    @Override
    public void add(int index, T element) {
        indexInsideArray(index);
        final int s;
        Object[] elementData;
        if ((s = size) == (elementData = this.array).length)
            elementData = Arrays.copyOf(array, array.length + 1);
        System.arraycopy(elementData, index,
                elementData, index + 1,
                s - index);
        elementData[index] = element;
        size = s + 1;
        /*Object[] temp = this.array;
        if(this.size == array.length) {
            temp = Arrays.copyOf(temp,temp.length+1);
        }
        this.pointer++;
        System.arraycopy(temp, index,
                temp, index + 1,
                this.size - index);
        this.array = temp;
        this.array[index] = element;
        this.size++;*/

    }

    public void indexInsideArray(int index) {
        if (index > this.size || index < 0)
            throw new IndexOutOfBoundsException("index: " + index + " is out of size: " + this.size);
    }

    @Override
    public T remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }
}