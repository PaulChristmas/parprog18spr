package ru.spbstu.telematics.java.Intro;

import java.io.File;
import java.util.Random;
import java.io.IOException;
import java.io.PrintWriter;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntroTest {

	private Intro r = null;
	
	IntroTest() {
	}

	IntroTest(String encoding) {
		r = new Intro(encoding);

	}

	@Test
	public void extension() {
		String s = null;
		try {
			s = r.getRev("example.in");
		} catch (Exception e) {
			System.err.println("Test NE OK");
			return;
		}
		assertEquals("Wrong String", s, "01987\nxyx\n 54321");
		System.out.println("Test OK");
	}

	@Test
	public void empty() {
		String s = null;
		try {
			s = r.getRev("empty.txt");
		} catch (Exception e) {
			System.err.println("Empty Test NE OK");
			return;
		}
		assertEquals("Wrong String", s, "");
		System.out.println("Empty Test OK");
	}

	@Test(expected = Exception.class)
	public void noFile() {
		try {
			r.getRev("exams_passed.txt");
		} catch (IOException e) {
			System.out.println("IO Test OK");
			return;
		}
		System.err.println("IO Test NE OK");
	}

	@Test
	public void bigRandom() {
		try {
			PrintWriter out = new PrintWriter(new File("random.txt"));
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < 10000; i++) {
				StringBuilder sb = new Random().ints(1000, 97, 122).mapToObj(c -> (char) c).collect(StringBuilder::new,
						StringBuilder::append, StringBuilder::append);
				result.append(sb + "\n");
				out.println(sb.toString());
			}
			out.close();
			
	    	if (result.length() > 0) {
	    		result.setLength(result.length() - 1);
	    	}

			String s = r.getRev("random.txt");
			assertEquals("Wrong String", s, result.reverse().toString());
			System.out.println("Random Test OK");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Random Test NE OK");
		}

	}
}
