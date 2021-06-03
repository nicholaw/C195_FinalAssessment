package io;

import java.io.*;

public class ApplicationWriter
{
    public static void writeText(String text)
    {
		try(var f = new File(IOConstants.LOGIN_ATTEMPT_DESTINATION);
			var fw = new FileWriter(f, true);
			var bw = new BufferedWriter(fw)) {
			bw.write(text);
			bw.newLine();
		} catch(IOException e) {
			e.printStackTrace();
		}
    }//writeText
}