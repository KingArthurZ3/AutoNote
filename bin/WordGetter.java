package bin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;


public class WordGetter
{
    static File file = new File( "listVocab.txt" );
    

    public static void main( String[] args )
    {
        TreeSet<String> VocabSet = new TreeSet<String>();
        ArrayList<String> result = new ArrayList<String>();
        try
        {            
            int i = 0;
            int spaceIndex;
            Scanner scan = new Scanner( file );
            while ( scan.hasNextLine() )
            {
                String word = scan.nextLine();
                result.add( i, word );
                spaceIndex = result.get( i ).indexOf( " " )+1;
                result.set( i, result.get( i ).substring(spaceIndex) );
                VocabSet.add( result.get( i ) );
                i++;
            }
            for(String res: VocabSet ){
                System.out.println(res);
            }
        }
        catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        }
    }
}
