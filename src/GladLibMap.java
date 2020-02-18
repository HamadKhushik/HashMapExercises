import edu.duke.FileResource;
import edu.duke.URLResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * HashMap version*
 * You should also have a data folder with several files. This program should generate a
 * story using the file madtemplate.txt, which is also in the data folder. This program
 * creates a story by replacing placeholder words such as <noun> by looking for a random
 * word of that type. This approach uses multiple private ArrayLists, one for each type of
 * word, to store each type of replacement. For example, one ArrayList stores different nouns.
 * These nouns are initially read in from a file called noun.txt and stored in the ArrayList
 * named nounList. Whenever the templated word <noun> is found in the story, a random noun from
 * the nounList is used in place of <noun>.
 */

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> checkWord;
    private ArrayList<String> categoriesUsed;

    private Random myRandom;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";

    public GladLibMap() {
        myMap = new HashMap<>();
        categoriesUsed = new ArrayList<>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source) {
        initializeFromSource(source);
        myRandom = new Random();
        myMap = new HashMap<>();
    }

    private void initializeFromSource(String source) {
        String[] categories = {"adjective", "noun", "color", "country", "name", "animal", "timeframe", "verb", "fruit"};
        for (String cat : categories) {
            ArrayList<String> words = new ArrayList<String>(readIt(source + '/' + cat + ".txt"));
            myMap.put(cat, words);
        }
        checkWord = new ArrayList<String>();
    }

    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if (label.equals("number")) {
            return "" + myRandom.nextInt(50) + 5;
        }
        if (myMap.containsKey(label)) {
            if (!categoriesUsed.contains(label)) {
                categoriesUsed.add(label);
            }
            return randomFrom(myMap.get(label));
        }
        return "**UNKNOWN**";
    }

    private String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if (first == -1 || last == -1) {
            return w;
        }
        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String sub = getSubstitute(w.substring(first + 1, last));

        if (!checkWord.contains(sub)) {
            checkWord.add(sub);
        }
        while (checkWord.indexOf(sub) != -1) {
            sub = getSubstitute(w.substring(first + 1, last));
        }
        return prefix + sub + suffix;
    }

    private void printOut(String s, int lineWidth) {
        int charsWritten = 0;
        for (String w : s.split("\\s+")) {
            if (charsWritten + w.length() > lineWidth) {
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
    }

    private String fromTemplate(String source) {
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String source) {
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        }
        return list;
    }

    private int totalWordsInMap() {
        int totalWords = 0;
        for (String s : myMap.keySet()) {
            totalWords += myMap.get(s).size();
        }
        return totalWords;
    }

    private int totalWordsConsidered() {
        int wordsConsidered = 0;
        System.out.println(categoriesUsed);
        for (String s : categoriesUsed) {
            wordsConsidered += myMap.get(s).size();
        }
        return wordsConsidered;
    }

    public void makeStory() {
        checkWord.clear();
        categoriesUsed.clear();
        System.out.println("\n");
        String story = fromTemplate("data/dataLong/madtemplate3.txt");
        printOut(story, 60);
        int tot = totalWordsInMap();
        System.out.println(" \nTotal words that were possible to pick from " + tot);
        int wordsC = totalWordsConsidered();
        System.out.println("Total words considered were " + wordsC);

    }

    public static void main(String[] args) {
        GladLibMap gl = new GladLibMap();
        gl.makeStory();
    }


}

