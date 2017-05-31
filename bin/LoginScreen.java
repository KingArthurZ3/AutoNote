package bin;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;


/**
 * Creates GUI and submission keys for inputting login info
 */
public class LoginScreen
{

    /**
     * constructor method for initializes login screen gui
     */
    public LoginScreen()
    {
        final JFrame frame = new JFrame( "Login to Autonote" );
        frame.setDefaultCloseOperation( 3 );

        // creates username text field and text label
        final JLabel userText = new JLabel( "Username: " );
        final JTextArea username = new JTextArea();
        frame.getContentPane().add( userText );
        frame.getContentPane().add( username );
        username.setLineWrap( true );
        username.setSize( new Dimension( 200, 20 ) );
        username.setVisible( true );
        username.setEditable( true );
        userText.setVisible( true );
        userText.setAlignmentX( 0.5f );
        frame.getContentPane().setLayout( new BoxLayout( frame.getContentPane(), 1 ) );

        // creates password text field and text label
        final JLabel passText = new JLabel( "Password: " );
        final JPasswordField password = new JPasswordField( 10 );
        password.setSize( new Dimension( 200, 20 ) );
        frame.getContentPane().add( passText );
        frame.getContentPane().add( password );
        password.setVisible( true );
        password.setEditable( true );
        passText.setVisible( true );
        passText.setAlignmentX( 0.5f );
        frame.getContentPane().setLayout( new BoxLayout( frame.getContentPane(), 1 ) );

        // creates button to submit login info
        final JButton loginButton = new JButton( "Login" );
        loginButton.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( final ActionEvent evt )
            {
                try
                {
                    // TODO add the information sender to find users using
                    // networking

                }
                catch ( Exception ex )
                {
                    ex.printStackTrace();
                }
            }
        } );
        frame.add( loginButton );
        loginButton.setAlignmentX( 0.5f );

        // initializes the frame to visible
        frame.setVisible( true );
        frame.pack();
        frame.setSize( 300, 250 );
        frame.setLocationRelativeTo( null );

    }

}
