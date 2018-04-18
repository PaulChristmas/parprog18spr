package ru.spbstu.telematics.java.Intro;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.charset.Charset;

public class Intro 
{
	private final static String root = System.getProperty("user.dir") + File.separator;
	private String encoding = "";
	
	Intro (String encoding) {
		this.encoding = encoding;
	}
	
    public String getRev(String file) throws IOException {
    	StringBuilder sb = new StringBuilder();
    	Files.lines(Paths.get(root + file), Charset.forName(encoding)).forEach(l -> {sb.append(l + "\n");});
    	if (sb.length() > 0) {
    		sb.setLength(sb.length() - 1);
    	}
    	return sb.reverse().toString();
    }
}
