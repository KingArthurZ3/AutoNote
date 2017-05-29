package bin;

import java.net.ServerSocket;

public class PortFinder
{
    private int finalPort;
    private String ip = null;
    
    public PortFinder()
    {
    }
    public void available() {
        ServerSocket s = null;
        try
        {
            s = new ServerSocket(0); 
        }
        catch (Exception e)
        {
            System.out.println( "No available ports" );
        }
        finalPort = s.getLocalPort();
        System.out.println("listening on port: " + s.getLocalPort());
    }
    
    public int getPort()
    {
        return finalPort;
    }
}
