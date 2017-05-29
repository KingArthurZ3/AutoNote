package bin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import com.darkprograms.speech.microphone.MicrophoneAnalyzer;


public class Note
{
    private String noteName;

    private int noSound;

    private Set<String> historyVocabSet = new HashSet<String>();

    private TreeMap<String, Integer> historyWordFrequency = new TreeMap<String, Integer>();

    private ArrayList<String> historyVocabInitial = new ArrayList(
        Arrays.asList( "war", "battle", "bill", "act", "rights", "compromise", "government", "famine" ) );

    private Set<String> scienceVocabSet = new HashSet<String>();

    private TreeMap<String, Integer> scienceWordFrequency = new TreeMap<String, Integer>();

    private ArrayList<String> scienceVocabInitial = new ArrayList( Arrays.asList( "chemistry", "diffusion", "law" ) );

    private ArrayList<String> points;

    MicrophoneAnalyzer loc;

    SpeechTest speech;

    File apushVocab = new File( "APUSHList.txt" );

    PrintWriter writer;

    BufferedWriter docWriter;


    /**
     * @param name
     *            the name of the note
     * @param mic
     *            the mic currently being used
     * @param speech
     *            the speech program used to filter speech
     */
    public Note( String name, MicrophoneAnalyzer mic, SpeechTest speech )
    {
        noteName = name;
        noSound = 3;
        historyVocabSet = new TreeSet<String>();
        scienceVocabSet = new TreeSet<String>();
        points = new ArrayList<String>();
        loc = mic;
        this.speech = speech;
        historyVocabSet.addAll( historyVocabInitial );
        scienceVocabSet.addAll( scienceVocabInitial );

        try
        {
            writer = new PrintWriter( new FileWriter( "historyNote.txt" ) );
        }
        catch ( IOException e )
        {
            System.out.println( "the file either cannot be created or has not been instantiated correctly" );
        }
        docWriter = new BufferedWriter( writer );

        this.initHistoryList();
    }


    private void initHistoryList()
    {
        Scanner scan;
        try
        {
            scan = new Scanner( apushVocab );
            while ( scan.hasNextLine() )
            {
                historyVocabInitial.add( scan.nextLine() );
            }
        }
        catch ( FileNotFoundException e )
        {
            System.out.println( "Sorry, your file cannot be found" );
        }

    }


    /**
     * returns the entire history vocab list
     */
    public void getHistoryList()
    {
        for ( String res : historyVocabInitial )
        {
            System.out.println( res );
        }
    }


    /**
     * Precondition: user must specify the subject to add to adds a word to the
     * a specified vocab list
     * 
     * @param result
     *            the result to be added to
     * @param subject
     *            the subject specified by the user
     */
    public void addVocabList( String result, String subject )
    {

        if ( subject.toLowerCase().equals( "history" ) )
        {
            historyVocabSet.add( result );
        }
        else if ( subject.toLowerCase().equals( "science" ) )
        {
            scienceVocabSet.add( result );
        }
        else
        {
            return;
        }

    }


    /**
     * Returns the name of the note
     * 
     * @return noteName name of note
     */
    public String getName()
    {
        return this.noteName;
    }


    public void addPoint( String input )
    {
        Set<String> VocabSet = new HashSet<String>();
        if ( speech.getSubject().toLowerCase() != "" )
        {
            if ( speech.getSubject().toLowerCase().equals( "science" ) )
            {
                VocabSet = scienceVocabSet;
            }
            else
            {
                VocabSet = historyVocabSet;
            }
        }

        String[] words = input.split( "\\W+" );
        String add = "";
        for ( String s : words )
        {
            if ( !VocabSet.contains( s ) && s.length() > 4 )
            {
                add += s + " ";
                VocabSet.add( s );
            }

        }
        points.add( add );
    }


    /**
     * writes notes to file
     * @param text the most recent notes said
     * @throws IOException if file writer cannot writer to file
     */
    public void createNotes( String text ) throws IOException
    {
        String output = text;
        // these method calls adjust the indent and increments the word
        // frequency counter
        if ( text != null && text.equals( "q" ) )
        {

            writer.close();
            return;
        }
        output = this.formatAdjustWithWords( text );
//        System.out.println("before adding output: " + output);
        writer.append('\n' + output);
        writer.flush();
        
//        System.out.println( "the latest word added is: " + output );

//         this.changeWordFrequency( text );

    }


    /**
     * gets the text in speech
     * 
     * @return the text in speech that has just been said
     */
    public String getText()
    {
        return speech.getText();
    }


    /**
     * adjusts the indent of the note by volume
     * 
     * @param text
     *            the text in the speech
     * @return the speech said with indents if needed
     */
    public String formatAdjustWithVolume( String text )
    {
        int micLevel = loc.getAudioVolume();
        if ( micLevel > 60 )
        {
            return "" + speech.getText();
        }
        else if ( micLevel > 30 )
        {
            return "    " + speech.getText();
        }
        else
            return "        " + speech.getText();
    }


    /**
     * adjust the indent by priority key words, default to volume if no key
     * words are there
     * 
     * @param text
     *            the text said by the user
     * @return the formatted string of words with indents if needed
     */
    public String formatAdjustWithWords( String text )
    {
        Set<String> VocabSet = new HashSet<String>();

        if ( speech.getSubject().toLowerCase() != "" )
        {
            if ( speech.getSubject().toLowerCase().equals( "science" ) )
            {
                VocabSet = scienceVocabSet;
            }
            else
            {
                VocabSet = historyVocabSet;
            }
        }

        for ( String comp : VocabSet )
        {
            if ( text.toLowerCase().contains( comp.toLowerCase() ) )
            {
                return "" + speech.getText();
            }
        }
        return formatAdjustWithVolume( text );
    }


    /**
     * changes the number of times the word has been said
     * 
     * @param result
     *            the treemap adjusted to the number of times a word has been
     *            said
     */
    public void changeWordFrequency( String result )
    {
        String currSubject = "history";
        Set<String> VocabSet = new HashSet<String>();
        TreeMap<String, Integer> wordFrequency = new TreeMap<String, Integer>();
        if ( speech.getSubject().toLowerCase() != "" )
        {
            if ( speech.getSubject().toLowerCase().equals( "science" ) )
            {
                currSubject = "science";
                VocabSet = scienceVocabSet;
                wordFrequency = scienceWordFrequency;
            }
            else
            {
                currSubject = "history";
                VocabSet = historyVocabSet;
                wordFrequency = historyWordFrequency;
            }
        }

        for ( String res : result.split( " " ) )
        {
            if ( wordFrequency.containsKey( res ) )
            {
                wordFrequency.replace( res, wordFrequency.get( res ) + 1 );
                if ( wordFrequency.get( res ) >= 3 )
                {
                    VocabSet.add( res );
                }
            }
            else
            {
                wordFrequency.put( res, 1 );
            }
        }
        if ( currSubject.equals( "science" ) )
        {
            scienceVocabSet = VocabSet;
            scienceWordFrequency = wordFrequency;
        }
        else
        {
            historyVocabSet = VocabSet;
            historyWordFrequency = wordFrequency;
        }

    }

}
