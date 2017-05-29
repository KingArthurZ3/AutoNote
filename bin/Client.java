package bin;

import java.io.*;
import java.net.*;

public class Client 
{
    
    public final static int SOCKET_PORT = 13267;     // socket port we use
    public final static String SERVER = "127.0.0.1";  // localhost 
    public final static String FILE_TO_RECEIVED = "c:/Users/bzhu136/Dropbox/recv.jpg";  

    public final static int FILE_SIZE = 1000022386; 
    
    /**
     * returns the address of the server / local host
     * 
     * @return SERVER address
     */
    public String getServerAddress()
    {
        return SERVER;
    }
    
    /**
     * returns the file path of the download file
     * 
     * @return FILE_TO_RECEIVED file path
     */
    public String getFilePath()
    {
        return FILE_TO_RECEIVED;
    }

    /**
     * Downloads specified file from the server, directed to the designated path
     * 
     * @throws IOException
     */
    public void run() throws IOException 
    {
        int bytesRead;
        int current = 0;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        Socket sock = null;
        try 
        {
          
          sock = new Socket(SERVER, SOCKET_PORT);
          System.out.println("Connecting...");

          byte [] mybytearray  = new byte [FILE_SIZE];
          InputStream is = sock.getInputStream();
          fos = new FileOutputStream(FILE_TO_RECEIVED);
          bos = new BufferedOutputStream(fos);
          bytesRead = is.read(mybytearray,0,mybytearray.length);
          current = bytesRead;

          do 
          {
              bytesRead = is.read(mybytearray, current, (mybytearray.length-current));
              if(bytesRead >= 0)
                  {
                      current += bytesRead;
                  }
          } while(bytesRead > -1);

          bos.write(mybytearray, 0 , current);
          bos.flush();
          System.out.println("File " + FILE_TO_RECEIVED + " downloaded (" + current + " bytes read)");
        }
        finally 
        {
          if (fos != null) fos.close();
          if (bos != null) bos.close();
          if (sock != null) sock.close();
        }
    }

}