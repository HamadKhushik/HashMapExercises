import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Write a program to determine which words occur in the greatest
 * number of files, and for each word, which files they occur in.
 * <p>
 * To solve this problem, you will create a map of words to the names of files they are
 * in. That is, you will map a String to an ArrayList of Strings. Then you can determine
 * which ArrayList value is the largest (has the most filenames) and its key is thus,
 * a word that is in the most number of files.
 */

public class WordsInFiles {

    private HashMap<String, ArrayList<String>> wordsH;

    private WordsInFiles() {
        wordsH = new HashMap();
    }

    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {
            word = word.trim();
            ArrayList<String> fileName = new ArrayList<String>();
            String file = f.getName();  //can be outside the Loop
            if (!wordsH.containsKey(word)) {
                fileName.add(file);
                wordsH.put(word, fileName);
            } else {
                fileName = wordsH.get(word);
                if (!fileName.contains(file)) {
                    fileName.add(file);
                }
            }
        }
    }

    private void buildWordFileMap() {
        wordsH.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    private int maxNumber() {
        int max = 0;
        for (String word : wordsH.keySet()) {
            ArrayList<String> filename = wordsH.get(word);
            if (filename.size() > max) {
                max = filename.size();
            }
        }
        return max;
    }

    private ArrayList<String> wordsInNumFiles(int number) { //This method returns an ArrayList of words that appear in exactly 'number' files
        ArrayList<String> wordsInF = new ArrayList<>();
        for (String s : wordsH.keySet()) {
            if (number == wordsH.get(s).size()) {
                wordsInF.add(s);
            }
        }
        return wordsInF;
    }

    private void printFilesIn(String word) {
        System.out.println(wordsH.get(word));
    }

    public void tester() {
        buildWordFileMap();
        int max = maxNumber();
        System.out.println("The maximun number of files a word appears is " + max);
        ArrayList<String> maxWords = wordsInNumFiles(max);
        System.out.println("Thw words that appear in max number of files are " + maxWords.size());
        for (String word : maxWords) {
            System.out.println(word + '\t' + wordsH.get(word));
        }
        /*System.out.println("the whole map" + '\n');
        for (String word : wordsH.keySet()) {
            System.out.println(word + '\t' + wordsH.get(word));
        }*/
        System.out.println("red" + '\t' + wordsH.get("red"));
    }

    public static void main(String[] args) {
        WordsInFiles wif = new WordsInFiles();
        wif.tester();
    }

}
