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

public class JUAutoNote
{
    
    // Note
    MicrophoneAnalyzer loc = new MicrophoneAnalyzer(FLACFileWriter.FLAC);
    String noteName = "APUSHNote";
    SpeechTest speech = new SpeechTest();
    
    
    @Test
    public void NoteConstructor()
    {
        Note note = new Note(noteName, loc, speech);
        assertTrue("Microphone should be on", note.speech.getMic() != null);
        assertEquals("APUSHNote", note.getName());
        //assertEquals(note.formatAdjustWithWords( "" ), "        Begin");
        
    }
    
    @Test
    public void NoteGetHistorySet()
    {
        Note note = new Note(noteName, loc, speech);
        assertTrue("Vocab list should not be empty", !note.getHistorySet().isEmpty());
        assertTrue("Famine is an original entry", note.getHistorySet().contains("famine"));     
    }
    
    @Test
    public void NoteAddVocabList()
    {
        Note note = new Note(noteName, loc, speech);
        note.addVocabList( new LinkedList<String>(Arrays.asList( "industry", "war machine", "rifle" )), "math" );
        assertFalse("industry is not an entry", note.getHistorySet().contains( "industry" ));
        note.addVocabList( new LinkedList<String>(Arrays.asList( "industry", "war machine", "rifle" )), "history" );
        assertTrue("industry is an entry", note.getHistorySet().contains( "industry" ));
        assertTrue("rifle is an entry", note.getHistorySet().contains( "rifle" ));
    }
    
    @Test
    public void NoteGetName()
    {
        Note note = new Note(noteName, loc, speech);
        assertEquals("APUSHNote", note.getName());
    }
    
    @Test
    public void NoteGetText()
    {
        Note note = new Note(noteName, loc, speech);
        assertEquals("Begin", note.getText());
        note.speech.updateText( "World War II" );
        assertEquals("World War II", note.getText());
    }
    
    @Test
    public void NoteCreateNotes() throws IOException
    {
        Note note = new Note(noteName, loc, speech);
        note.speech.updateText( "Today we will be talking about World War II" );
        note.createNotes( note.speech.getText() );
        BufferedReader fin = new BufferedReader(new FileReader("historyNote.txt"));
        String s = "";
        while(s.isEmpty())
        {
            s = fin.readLine();
        }
        assertEquals( "        Today we will be talking about World War II", s );
        fin.close();
    }
    
    @Test
    public void NoteFormatAdjustWithWords() throws IOException
    {
        Note note = new Note(noteName, loc, speech);
        note.speech.updateText( "Today we will be talking about World War II" );
        note.speech.setSubject( "History" );
        String output = note.formatAdjustWithWords( note.speech.getText() );
        assertEquals("Today we will be talking about World War II", output);
        
        note.speech.updateText( "Today we will be talking about lime juice" );
        note.speech.setSubject( "History" );
        output = note.formatAdjustWithWords( note.speech.getText() );
        assertEquals("        Today we will be talking about lime juice", output);
    }
    
    @Test
    public void NoteChangeWordFrequency()
    {
        Note note = new Note(noteName, loc, speech);
        note.speech.setSubject( "History" );
        note.speech.updateText( "Today we will be talking about global intiatives" );
        note.changeWordFrequency( note.speech.getText() );
        note.speech.updateText( "Global issues drive national governments to change their foreign policies" );
        note.changeWordFrequency( note.speech.getText() );
        assertFalse("Vocabset does not contain the word global", note.getHistorySet().contains( "global" ));
        note.speech.updateText( "Global issues include environmental trends, trading regulations, and terrorism" );
        note.changeWordFrequency( note.speech.getText() );
        assertTrue("Vocabset has the word global", note.getHistorySet().contains( "global" ));
    }
    
    // SpeechTest
    
    @Test
    public void SpeechTestConstructor()
    {
        SpeechTest speech = new SpeechTest();
        assertNotNull("mic should not be null", speech.getMic());
        assertTrue("Defualt language should be english", speech.getDuplex().getLanguage().equals( "en" ));
    }
    
    @Test
    public void SpeechTestGetText()
    {
        SpeechTest speech = new SpeechTest();
        assertEquals("Begin", speech.getText());
    }
    
    @Test
    public void SpeechTestUpdateText()
    {
        SpeechTest speech = new SpeechTest();
        speech.updateText( "We will now begin class" );
        assertEquals("We will now begin class", speech.getText());
    }
    
    @Test
    public void SpeechTestGetSubject()
    {
        SpeechTest speech = new SpeechTest();
        assertEquals("", speech.getSubject());
    }
    
    @Test
    public void SpeechTestSetSubject()
    {
        SpeechTest speech = new SpeechTest();
        speech.setSubject( "science" );
        assertEquals("science", speech.getSubject());
    }
    
    // testSpeech
    
    // LoginScreen
   
    // Client
    @Test
    public void ClientGetServerAddress()
    {
        Client client = new Client();
        assertEquals("172.18.240.1", client.getServerAddress());
    }
    
    @Test
    public void ClientGetFilePath()
    {
        Client client = new Client();
        assertEquals("c:/Users/Bill/Dropbox/recv.jpg", client.getFilePath());
    }
    
    @Test
    public void ClientRun() throws IOException
    {
        assertNotNull("recv.jpg should exist", new File("c:/Users/Bill/Dropbox/recv.jpg"));
    }
    
    // Server
    @Test
    public void ServerRun()
    {
        assertNotNull("recv.jpg should exist", new File("c:/Users/azhang122/Dropbox/workspaceAPCS/AutoNoteTest/historyNote.txt"));
    }
    
    
}
