package ru.spbstu.telematics.java.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> implements Iterable<T> {

	public LinkedList() {

	}

	private static class Element<T> {
		T value = null;
		Element<T> next = null;
		Element<T> prev = null;

		Element(T value, Element<T> prev, Element<T> next) {
			this.value = value;
			this.next = next;
			this.prev = prev;
		}
	}

	private int capacity = 0;
	private Element<T> first, last;

	public int size() {
		return capacity;
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}

	private T cut(Element<T> unit) {
		final T element = unit.value;
		final Element<T> next = unit.next;
		final Element<T> prev = unit.prev;

		if (next == null) {
			last = prev;
		} else {
			next.prev = prev;
			unit.next = null;
		}

		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
			unit.prev = null;
		}

		unit.value = null;
		capacity--;
		return element;
	}

	public boolean contains(Object o) {
		for (Element<T> cur = first; cur != null; cur = cur.next) {
			if (Objects.equals(cur.value, o)) {
				return true;
			}
		}
		return false;
	}

	public boolean add(T e) {
		Element<T> oldLast = last;
		Element<T> newLast = new Element<>(e, oldLast, null);
		if (first == null) {
			first = newLast;
		} else {
			oldLast.next = newLast;
		}
		last = newLast;
		capacity++;
		return true;
	}

	public T get(int index) {
		if (index < 0 || index >= capacity)
			throw new IndexOutOfBoundsException();
		Element<T> cur = first;
		// find the nearest tail
		if (index < (capacity >> 1)) {
			for (int i = 0; i < index; i++) {
				cur = cur.next;
			}
		} else {
			cur = last;
			for (int i = capacity - 1; i > index; i--) {
				cur = cur.prev;
			}
		}
		return cur.value;
	}

	public boolean remove(Object o) {
		for (Element<T> cur = first; cur != null; cur = cur.next) {
			if (Objects.equals(cur.value, o)) {
				cut(cur);
				return true;
			}
		}
		return false;
	}
	
	public T remove() {
        Element<T> unit = first;
        if (isEmpty())
            throw new NoSuchElementException();
        return cut(unit);
	}

	public T remove(int index) {
		if (index < 0 || index >= capacity)
			throw new IndexOutOfBoundsException();

		Element<T> cur = first;
		if (index < (capacity >> 1)) {
			for (int i = 0; i < index; i++) {
				cur = cur.next;
			}
		} else {
			cur = last;
			for (int i = capacity - 1; i > index; i--) {
				cur = cur.prev;
			}
		}
		return cut(cur);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (Element<T> x = first; x != last; x = x.next) {
			sb.append(x.value + ", ");
		}
		return sb.append(last.value + "]").toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Element<T> current = null;

			@Override
			public boolean hasNext() {
				return capacity > 0 && current != last;
			}

			@Override
			public T next() {
				if (!hasNext())
					throw new IllegalStateException();
				if (current == null) {
					current = first;
				} else {
					current = current.next;
				}
				return (T) current.value;
			}

			public void remove() {
				Element<T> reserve = current.prev;
				cut(current);
				current = reserve;
			}
		};
	}

}
