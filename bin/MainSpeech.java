package bin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * Contains all GUIS and has all classes, runs everything
 */
public class MainSpeech
{

    /**
     * previous text spoken by user
     */
    static String oldText;

    /**
     * most recent text said by user
     */
    static String newText;


    /**
     * Main method for running all of the guis, creating notes, and sending
     * files
     * 
     * @throws IOException
     *             when files cannot be created or accessed
     */
    public static void main( String[] args ) throws IOException
    {
        SpeechTest speech = new SpeechTest();
        LoginScreen login = new LoginScreen();
        ServerGUI sGUI = new ServerGUI();
        Note historyNote = new Note( "test", speech.getMic(), speech );

        String condition = "";

        System.out.println( "Enter q to quit" );
        oldText = "";
        newText = "";

        while ( !condition.equals( "q" ) )
        {

            long var = System.currentTimeMillis() / 1000;
            double var2 = var % 2;

            if ( var2 == 1.0 )
            {
                if ( !historyNote.getText().equals( newText ) )
                {
                    System.out.println( "history note was: " + historyNote.getText() );
                    System.out.println( "newText was: " + newText );
                    newText = historyNote.getText();
                    System.out.println( "newText update is: " + newText );
                    System.out.println( "" );
                }

                if ( !newText.equals( oldText ) )
                {

                    historyNote.createNotes( newText );
                    oldText = newText;
                    // System.out.println("updated oldText: " + oldText);
                }

                if ( speech.getSubject().equals( "q" ) )
                {
                    System.out.println( "before end loop" );
                    break;
                }
                System.out.print( "" );
                System.out.print( "" );

            }
        }
        historyNote.createNotes( "q" );
        System.out.println( "ended program" );

    }

}