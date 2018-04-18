package ru.spbstu.telematics.java.LinkedList;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.Objects;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class LinkedListTest {

	private java.util.LinkedList<String> sample = new java.util.LinkedList<String>();
	private LinkedList<String> my = new LinkedList<String>();
	private final int size = 1000;
	
	private boolean checkEqual() {
		try {
			Iterator<String> it = sample.iterator();
			Iterator<String> myIt = my.iterator();

			while (it.hasNext()) {
				String s = it.next();
				String s2 = myIt.next();
				if (!Objects.equals(s, s2)) {
					return false;
				}
			}
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	private void fillUp() {
		for (int i = 0; i < size; i++) {
			String s = getString(new Random().nextInt(6) + 5);
			sample.add(s);
			my.add(s);
		}
	}
	
	private void clear() {
		sample = new java.util.LinkedList<String>();
		my = new LinkedList<String>();
	}
	
    private String getString(int size){
    	Random r = new Random();
    	final IntStream alph = r.ints(size, 97, 122);
    	// 1/10 probability that null returns 
    	boolean nul = r.nextInt(10) == 1;
        return nul? null : alph.mapToObj(i -> (char) i)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
	
	public LinkedListTest() {
		
	}

	@Test
	public void init() {
		fillUp();
		assertTrue("Init fail", checkEqual());
		System.out.println("Init OK");
		clear();
	}
	
	@Test
	public void cleanUp() {
		fillUp();
		Iterator<String> it = sample.iterator();
		Iterator<String> myIt = my.iterator();
		while (it.hasNext()) {
			it.next();
			myIt.next();
			
			it.remove();
			myIt.remove();
		}
		assertTrue("Cleanup fail", checkEqual() && my.isEmpty());
		System.out.println("Cleanup OK");
		clear();
	}
	
	@Test
	public void remove() {
		fillUp();
		// first element
		for (int i = 0; i < 3; i++) {
			my.remove();
			sample.remove();
		}
		assertTrue("remove() fail", checkEqual());
		
		Random r = new Random();
		for (int i = 0; i < 5; i++) {
			int index = r.nextInt(my.size());
			my.remove(index);
			sample.remove(index);
		}
		my.remove(0);
		sample.remove(0);
		
		my.remove(my.size() - 1);
		sample.remove(sample.size() - 1);
		
		assertTrue("remove(i) fail", checkEqual());
		
		for (int i = 0; i < 3; i++) {
			String o = sample.get(r.nextInt(my.size()));
			my.remove(o);
			sample.remove(o);
		}
		
		assertTrue("remove(o) fail", checkEqual());
		
		clear();
		
		try {
			my.remove();
		} catch (Exception e) {
			System.out.println("Rm OK");
			return;
		}
		System.out.println("Rm ne OK");
		
	}
	
	@Test
	public void get() {
		try {
			my.get(0);
			System.out.println("Get ne OK");
		} catch (Exception e) {
			// ok
		}
		fillUp();
		
		Random r = new Random();
		int inds[] = new int[20];
		for (int i = 0; i < 20; i++) {
			int ind = r.nextInt(my.size());
			assertTrue("get fail", Objects.equals(sample.get(ind), my.get(ind))); 
			inds[i] = ind;
		}
		
		for (int i = 0; i < 20; i++) {
			my.remove(inds[i]);
			sample.remove(inds[i]);
		}
		
		for (int i = 0; i < 20; i++) {
			int ind = r.nextInt(my.size());
			assertTrue("get fail", Objects.equals(sample.get(ind), my.get(ind)));
		}
		
		System.out.println("Get OK");
		
	}
	
	public void x() {
		init();
		cleanUp();
		remove();
		get();
	}

}
