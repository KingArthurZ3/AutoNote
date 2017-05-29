package bin;

import java.io.*;
import java.net.*;

/**
 * Class that provides a server instance, which is capable of sending files to client classes
 */
public class Server 
{

  	// socket port number
    public final static int SOCKET_PORT = 13267;  // you may change this
  
  	// directory of the file to be sent
    public final static String FILE_TO_SEND = "/Users/arthur/AutoNoteTest/historyNote.txt";  // you may change this
  
  	/**
     * Run method that sends the specified file from the send location to the specified receiver location
     */
    public void run() throws IOException 
    {
        //FileInputStream fis = null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        ServerSocket servsock = null;
        Socket sock = null;
        try 
        {
          servsock = new ServerSocket(SOCKET_PORT);
            System.out.println("Waiting...");
            try 
            {
              sock = servsock.accept();
              System.out.println("Accepted connection : " + sock);
              // send file
              File myFile = new File (FILE_TO_SEND);
              byte [] mybytearray  = new byte [(int)myFile.length()];
              //fis = new FileInputStream(myFile);
              bis = new BufferedInputStream(new FileInputStream(myFile));
              bis.read(mybytearray, 0, mybytearray.length);
              os = sock.getOutputStream();
              System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
              os.write(mybytearray, 0, mybytearray.length);
              os.flush();
              System.out.println("Done.");
            }
            finally 
            {
              if (bis != null) bis.close();
              if (os != null) os.close();
              if (sock!=null) sock.close();
            }
        }
        finally 
        {
          if (servsock != null) servsock.close();
        }
    }
}