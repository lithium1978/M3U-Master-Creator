package lithium1978.m3uMasterCreator.fileInputOutput;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;

import javax.swing.filechooser.FileSystemView;

import static java.nio.file.StandardOpenOption.*;
import java.nio.file.*;
import java.io.*;
 

public class FileLogger{
     
    public static void logData (String name){
            	
        	byte data[] = name.getBytes();
            Path p = Paths.get(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"/logfile.txt");
         
            try (OutputStream out = new BufferedOutputStream(
              Files.newOutputStream(p, CREATE, APPEND))) {
              out.write(data, 0, data.length);
            } catch (IOException x) {
              System.err.println(x);
            }
           finally{
        	  System.err.println("test");
           }
    }
}