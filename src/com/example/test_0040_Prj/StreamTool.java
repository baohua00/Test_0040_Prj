package com.example.test_0040_Prj;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamTool {
	public static byte[] read(InputStream inputStream) throws Exception{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len =0;
		while ((len = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		inputStream.close();
		return baos.toByteArray();
	}
}
