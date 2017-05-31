package bin;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.Test;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.microphone.MicrophoneAnalyzer;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;


/**
 * Contains junit tests for all methods used in this application
 */
public class JUAutoNote
{

    // Note
    MicrophoneAnalyzer loc = new MicrophoneAnalyzer( FLACFileWriter.FLAC );

    String noteName = "APUSHNote";

    SpeechTest speech = new SpeechTest();


    /**
     * Tests the Note class constructor
     */
    @Test
    public void NoteConstructor()
    {
        Note note = new Note( noteName, loc, speech );
        assertTrue( "Microphone should be on", note.speech.getMic() != null );
        assertEquals( "APUSHNote", note.getName() );
        // assertEquals(note.formatAdjustWithWords( "" ), " Begin");

    }


    /**
     * Tests the Note class getHistorySet() method
     */
    @Test
    public void NoteGetHistorySet()
    {
        Note note = new Note( noteName, loc, speech );
        assertTrue( "Vocab list should not be empty", !note.getHistorySet().isEmpty() );
        assertTrue( "Famine is an original entry", note.getHistorySet().contains( "famine" ) );
    }


    /**
     * Tests the Note class addVocabList() method
     */
    @Test
    public void NoteAddVocabList()
    {
        Note note = new Note( noteName, loc, speech );
        note.addVocabList( new LinkedList<String>( Arrays.asList( "industry", "war machine", "rifle" ) ), "math" );
        assertFalse( "industry is not an entry", note.getHistorySet().contains( "industry" ) );
        note.addVocabList( new LinkedList<String>( Arrays.asList( "industry", "war machine", "rifle" ) ), "history" );
        assertTrue( "industry is an entry", note.getHistorySet().contains( "industry" ) );
        assertTrue( "rifle is an entry", note.getHistorySet().contains( "rifle" ) );
    }


    /**
     * Tests the Note class getName() method
     */
    @Test
    public void NoteGetName()
    {
        Note note = new Note( noteName, loc, speech );
        assertEquals( "APUSHNote", note.getName() );
    }


    /**
     * Tests the Note class getText() method
     */
    @Test
    public void NoteGetText()
    {
        Note note = new Note( noteName, loc, speech );
        assertEquals( "Begin", note.getText() );
        note.speech.updateText( "World War II" );
        assertEquals( "World War II", note.getText() );
    }


    /**
     * Tests the Note class createNotes() method
     * 
     * @throws IOException
     *             if the list files are not found
     */
    @Test
    public void NoteCreateNotes() throws IOException
    {
        Note note = new Note( noteName, loc, speech );
        note.speech.updateText( "Today we will be talking about World War II" );
        note.createNotes( note.speech.getText() );
        BufferedReader fin = new BufferedReader( new FileReader( "historyNote.txt" ) );
        String s = "";
        while ( s.isEmpty() )
        {
            s = fin.readLine();
        }
        assertEquals( "        Today we will be talking about World War II", s );
        fin.close();
    }


    /**
     * Tests the Note class formatAdjustWithWords() method
     * 
     * @throws IOException
     *             if input files are not found
     */
    @Test
    public void NoteFormatAdjustWithWords() throws IOException
    {
        Note note = new Note( noteName, loc, speech );
        note.speech.updateText( "Today we will be talking about World War II" );
        note.speech.setSubject( "History" );
        String output = note.formatAdjustWithWords( note.speech.getText() );
        assertEquals( "Today we will be talking about World War II", output );

        note.speech.updateText( "Today we will be talking about lime juice" );
        note.speech.setSubject( "History" );
        output = note.formatAdjustWithWords( note.speech.getText() );
        assertEquals( "        Today we will be talking about lime juice", output );
    }


    /**
     * Tests the Note class changeWordFrequency() method
     */
    @Test
    public void NoteChangeWordFrequency()
    {
        Note note = new Note( noteName, loc, speech );
        note.speech.setSubject( "History" );
        note.speech.updateText( "Today we will be talking about global intiatives" );
        note.changeWordFrequency( note.speech.getText() );
        note.speech.updateText( "Global issues drive national governments to change their foreign policies" );
        note.changeWordFrequency( note.speech.getText() );
        assertFalse( "Vocabset does not contain the word global", note.getHistorySet().contains( "global" ) );
        note.speech.updateText( "Global issues include environmental trends, trading regulations, and terrorism" );
        note.changeWordFrequency( note.speech.getText() );
        assertTrue( "Vocabset has the word global", note.getHistorySet().contains( "global" ) );
    }


    // SpeechTest

    /**
     * Tests the SpeechTest class constructor
     */
    @Test
    public void SpeechTestConstructor()
    {
        SpeechTest speech = new SpeechTest();
        assertNotNull( "mic should not be null", speech.getMic() );
        assertTrue( "Defualt language should be english", speech.getDuplex().getLanguage().equals( "en" ) );
    }


    /**
     * Tests the SpeechTest class getText() method
     */
    @Test
    public void SpeechTestGetText()
    {
        SpeechTest speech = new SpeechTest();
        assertEquals( "Begin", speech.getText() );
    }


    /**
     * Tests the SpeechTest class updateText() method
     */
    @Test
    public void SpeechTestUpdateText()
    {
        SpeechTest speech = new SpeechTest();
        speech.updateText( "We will now begin class" );
        assertEquals( "We will now begin class", speech.getText() );
    }


    /**
     * Tests the SpeechTest class getSubject() method
     */
    @Test
    public void SpeechTestGetSubject()
    {
        SpeechTest speech = new SpeechTest();
        assertEquals( "", speech.getSubject() );
    }


    /**
     * Tests the SpeechTest class setSubject() method
     */
    @Test
    public void SpeechTestSetSubject()
    {
        SpeechTest speech = new SpeechTest();
        speech.setSubject( "science" );
        assertEquals( "science", speech.getSubject() );
    }

    // testSpeech

    // LoginScreen


    // Client
    /**
     * Tests the Client class getServerAddress() method to verify the correct
     * server address
     */
    @Test
    public void ClientGetServerAddress()
    {
        Client client = new Client();
        assertEquals( "172.18.240.1", client.getServerAddress() );
    }


    /**
     * Tests the Client class getFilePath() method to verify the correct file
     * path
     */
    @Test
    public void ClientGetFilePath()
    {
        Client client = new Client();
        assertEquals( "c:/Users/Bill/Dropbox/recv.jpg", client.getFilePath() );
    }


    /**
     * Tests the Client class run() method to verify that the file is
     * successfully received
     * 
     * @throws IOException
     *             if files are not found
     */
    @Test
    public void ClientRun() throws IOException
    {
        assertNotNull( "recv.jpg should exist", new File( "c:/Users/Bill/Dropbox/recv.jpg" ) );
    }


    // Server
    /**
     * Tests the Server class run() method to verify that the file sources
     * exists
     */
    @Test
    public void ServerRun()
    {
        assertNotNull( "recv.jpg should exist",
            new File( "c:/Users/azhang122/Dropbox/workspaceAPCS/AutoNoteTest/historyNote.txt" ) );
    }

}
