package bin;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;


/**
 * Speech Test that contains GUI, microphone, and GSpeech duplex, which handles
 * bulk of speech to text processing
 */
public class SpeechTest
{
    // long string used to store everything that was said
    private String long_text = "Begin";

    // short string used to store the new text
    private String short_text = "";

    // subject of the note
    private String subject = "";

    // microphone analyzer instance
    final MicrophoneAnalyzer mic;

    // speech duplex instance
    final GSpeechDuplex duplex;


    /**
     * gets the text currently said
     * 
     * @return the text the most recently said text
     */
    public String getText()
    {
        return long_text;
    }


    /**
     * sets the subject of the note
     * 
     * @param sub
     *            subject of the note
     */
    public void setSubject( String sub )
    {
        subject = sub;
    }


    /**
     * updates the long_text to the most recently said text
     * 
     * @param text
     *            the new text
     */
    public void updateText( String text )
    {

        Scanner scan = new Scanner( text );

        while ( scan.hasNextLine() )
        {
            long_text = scan.nextLine();
        }

    }


    /**
     * returns the subject of the note
     * 
     * @return subject of the note
     */
    public String getSubject()
    {
        if ( !subject.equals( "" ) )
        {
            return subject;
        }
        return "";
    }


    /**
     * returns the microphone analyzer instance
     * 
     * @return mic instance
     */
    public MicrophoneAnalyzer getMic()
    {
        return mic;
    }


    /**
     * returns the speech duplex instance
     * 
     * @return duplex the speech duplex
     */
    public GSpeechDuplex getDuplex()
    {
        return duplex;
    }


    /**
     * Constructor with gui that displays speech
     */
    public SpeechTest()
    {
        mic = new MicrophoneAnalyzer( FLACFileWriter.FLAC );
        duplex = new GSpeechDuplex( "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw" );
        duplex.setLanguage( "en" );

        final JFrame frame = new JFrame( "AutoNote" );
        frame.setDefaultCloseOperation( 3 );
        // lets user input subject text
        final JTextArea subjectArea = new JTextArea();
        subjectArea.setEditable( true );
        subjectArea.setLineWrap( true );
        subjectArea.setMaximumSize( new Dimension( 200, 50 ) );
        subjectArea.setVisible( true );

        final JTextArea response = new JTextArea();
        response.setEditable( false );
        response.setWrapStyleWord( true );
        response.setLineWrap( true );
        final JButton record = new JButton( "Record" );
        final JButton stop = new JButton( "Stop" );
        stop.setEnabled( false );
        record.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( final ActionEvent evt )
            {
                try
                {
                    duplex.recognize( mic.getTargetDataLine(), mic.getAudioFormat() );
                    record.setEnabled( false );
                    stop.setEnabled( true );
                }
                catch ( Exception ex )
                {
                    ex.printStackTrace();
                }
            }
        } );
        stop.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed( final ActionEvent arg0 )
            {
                mic.close();
                record.setEnabled( true );
                stop.setEnabled( false );
            }
        } );
        final JLabel infoText = new JLabel(
            "<html><div style=\"text-align: center;\">Just hit record and watch your voice be translated into text.\n<br>Only English is supported by this demo, but the full API supports dozens of languages.<center></html>",
            0 );

        frame.getContentPane().add( subjectArea );
        frame.getContentPane().add( infoText );
        infoText.setAlignmentX( 0.5f );
        final JScrollPane scroll = new JScrollPane( response );
        frame.getContentPane().setLayout( new BoxLayout( frame.getContentPane(), 1 ) );
        frame.getContentPane().add( scroll );
        final JPanel recordBar = new JPanel();
        frame.getContentPane().add( recordBar );
        recordBar.setLayout( new BoxLayout( recordBar, 0 ) );
        recordBar.add( record );
        recordBar.add( stop );
        frame.setVisible( true );
        frame.pack();
        frame.setSize( 500, 500 );
        frame.setLocationRelativeTo( null );

        // Listen for changes in the text
        subjectArea.getDocument().addDocumentListener( new DocumentListener()
        {
            public void changedUpdate( DocumentEvent e )
            {
                update();
            }


            public void removeUpdate( DocumentEvent e )
            {
                update();
            }


            public void insertUpdate( DocumentEvent e )
            {
                update();
            }


            public void update()
            {
                subject = subjectArea.getText();
            }
        } );

        // adds the response listener
        duplex.addResponseListener( (GSpeechResponseListener)new GSpeechResponseListener()
        {
            String old_text = "";


            public void onResponse( final GoogleResponse gr )
            {
                String output = "";
                output = gr.getResponse();
                if ( gr.getResponse() == null )
                {
                    this.old_text = response.getText();
                    if ( this.old_text.contains( "(" ) )
                    {
                        this.old_text = this.old_text.substring( 0, this.old_text.indexOf( 40 ) );
                    }
                    // System.out.println("Paragraph Line Added");
                    this.old_text = String.valueOf( response.getText() ) + "\n";
                    this.old_text = this.old_text.replace( ")", "" ).replace( "( ", "" );
                    response.setText( this.old_text );
                    return;
                }
                if ( output.contains( "(" ) )
                {
                    output = output.substring( 0, output.indexOf( 40 ) );
                }
                if ( !gr.getOtherPossibleResponses().isEmpty() )
                {
                    output = String.valueOf( output ) + " (" + gr.getOtherPossibleResponses().get( 0 ) + ")";
                }
                response.setText( "" );
                response.append( this.old_text );
                response.append( output );

                updateText( old_text );

                // System.out.println("long_text has been changed to: " +
                // long_text);
                // System.out.println("getText method returns: " + getText());

            }

        } );
    }
}