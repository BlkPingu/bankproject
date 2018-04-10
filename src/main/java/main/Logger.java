/**
 * Created by Tobias on 18/01/18.
 */
package main;

import java.io.*;

public class Logger {

    public String fileName = "";
    Writer writer = null;

    private Logger(){

    }
    public void log(String textToLog){
        if(writer == null){
            try {
                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(fileName), "utf-8"));
            }
            catch(IOException ex){

            }
        }

        try {
            writer.write(textToLog + System.lineSeparator());
        } catch (IOException ex) {
            // report
        }
    }
    private static Logger einzigeLoggerInstance = new Logger();

    public static Logger getLogger(){
        return einzigeLoggerInstance;
    }

}
