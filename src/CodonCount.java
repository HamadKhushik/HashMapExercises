import edu.duke.FileResource;

import java.util.HashMap;

/**
 * Write a program to find out how many times each codon occurs in a strand of DNA based
 * on reading frames. A strand of DNA is made up of the symbols C, G, T, and A. A codon is
 * three consecutive symbols in a strand of DNA such as ATT or TCC. A reading frame is a way
 * of dividing a strand of DNA into consecutive codons. Consider the following strand of
 * DNA = “CGTTCAAGTTCAA”.
 * <p>
 * There are three reading frames.
 * <p>
 * The first reading frame starts at position 0 and has the codons: “CGT”, “TCA”, “AGT” and “TCA”.
 * Here TCA occurs twice and the others each occur once.
 * The second reading frame starts at position 1 (ignoring the first C character) and has the
 * codons: “GTT”, “CAA”, “GTT”, “CAA”. Here both GTT and CAA occur twice.
 * The third reading frame starts at position 2 (ignoring the first two characters CG) and has
 * the codons: “TTC”, “AAG”, “TTC”. Here TTC occurs twice and AAG occurs once.
 * A map of DNA codons to the number times each codon appears in a reading frame would be
 * helpful in solving this problem.
 */

public class CodonCount {

    private HashMap<String, Integer> map;

    private CodonCount() {
        map = new HashMap<String, Integer>();
    }

    public void buildCodonMap(int start, String dna) {
        map.clear();
        for (int i = start; i < dna.length() - 2; i += 3) {
            String codon = dna.substring(i, i + 3);
            if (!map.containsKey(codon)) {
                map.put(codon, 1);
            } else {
                map.put(codon, map.get(codon) + 1);
            }

        }
    }

    public String getMostCommonCodon() {
        String mostCommon = "";
        int max = 0;
        for (String codon : map.keySet()) {
            if (max < map.get(codon)) {
                max = map.get(codon);
                mostCommon = codon;
            }
        }
        return mostCommon;
    }

    public void printCodonCounts(int start, int end) {
        for (String codon : map.keySet()) {
            if (map.get(codon) >= start && map.get(codon) <= end) {
                System.out.println(codon + '\t' + map.get(codon));
            }
        }
    }

    public void tester() {
        FileResource fr = new FileResource();
        String dna = fr.asString();
        dna = dna.trim();
        dna = dna.toUpperCase();
        System.out.println(dna);
        buildCodonMap(0, dna);
        String commonCodon = getMostCommonCodon();
        System.out.println("most common codon is " + commonCodon + " with count " + map.get(commonCodon));
        System.out.println("counts of codons between 1 and 5 inclusive are ");
        printCodonCounts(1, 5);
    }

    public static void main(String[] args) {
        CodonCount cc = new CodonCount();
        cc.tester();
    }

}
