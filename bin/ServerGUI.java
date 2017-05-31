package bin;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;


/**
 * Creates a GUI for the server class, allowing easy sending of files
 */
public class ServerGUI
{
    private JFrame mainFrame;

    private JLabel headerLabel;

    private JLabel statusLabel;

    private JPanel controlPanel;

    Server serve;

    Client cli;


    /**
     * Constructor method that calls prepareGUI()
     */
    public ServerGUI()
    {
        prepareGUI();
    }


    /**
     * main method that creates and displays the GUI
     */
    public static void main( String[] args )
    {
        ServerGUI swingControlDemo = new ServerGUI();
        swingControlDemo.showEventDemo();
    }


    /**
     * Creates a server and client instance and sets up the GUI
     */
    private void prepareGUI()
    {
        serve = new Server();
        cli = new Client();
        mainFrame = new JFrame( "Exchange Files" );
        mainFrame.setSize( 400, 400 );
        mainFrame.setLayout( new GridLayout( 3, 1 ) );

        headerLabel = new JLabel( "", JLabel.CENTER );
        statusLabel = new JLabel( "What would you like to do?", JLabel.CENTER );
        statusLabel.setSize( 350, 100 );

        mainFrame.addWindowListener( new WindowAdapter()
        {
            public void windowClosing( WindowEvent windowEvent )
            {
                System.exit( 0 );
            }
        } );
        controlPanel = new JPanel();
        controlPanel.setLayout( new FlowLayout() );

        mainFrame.add( headerLabel );
        mainFrame.add( controlPanel );
        mainFrame.add( statusLabel );
        mainFrame.setVisible( true );
        showEventDemo();
    }


    /**
     * Sets up the send and receive buttons to register actions
     */
    private void showEventDemo()
    {
        headerLabel.setText( "Send/Receive Files" );

        JButton okButton = new JButton( "send" );
        JButton submitButton = new JButton( "receive" );

        okButton.setActionCommand( "send" );
        submitButton.setActionCommand( "receive" );

        okButton.addActionListener( new ButtonClickListener() );
        submitButton.addActionListener( new ButtonClickListener() );

        controlPanel.add( okButton );
        controlPanel.add( submitButton );

        mainFrame.setVisible( true );
    }


    /**
     * Listener class that performs actions when the JButtons are activated
     */
    private class ButtonClickListener implements ActionListener
    {
        /**
         * Implemented action performed method that sends the file by calling
         * server's run method
         */
        public void actionPerformed( ActionEvent e )
        {
            String command = e.getActionCommand();

            if ( command.equals( "send" ) )
            {

                try
                {
                    serve.run();
                    statusLabel.setText( "File sent" );
                }
                catch ( IOException e1 )
                {
                    statusLabel.setText( "File could not be sent" );
                }

            }
            else
            {

                try
                {
                    cli.run();
                    statusLabel.setText( "File received" );
                }
                catch ( IOException e1 )
                {
                    statusLabel.setText( "File could not be found" );
                }
                // statusLabel.setText("Cancel Button clicked.");
            }
        }
    }
}
