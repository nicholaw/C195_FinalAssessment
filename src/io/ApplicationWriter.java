package io;

import java.io.*;

public class ApplicationWriter
{
    private File dest;

    public ApplicationWriter()
    {
        dest = new File(IOConstants.LOGIN_ATTEMPT_DESTINATION);
    }//constructor

    public void writeText(String str)
    {
            try(var fw = new FileWriter(dest);
                var bw = new BufferedWriter(fw);
                //var pw = new PrintWriter(bw)
            ){
                //pw.println(str);
                bw.append(str);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}