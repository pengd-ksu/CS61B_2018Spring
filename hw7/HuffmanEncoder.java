import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char ch : inputSymbols) {
            if (frequencyTable.containsKey(ch)) {
                frequencyTable.put(ch, frequencyTable.get(ch) + 1);
            } else {
                frequencyTable.put(ch, 1);
            }
        }
        return frequencyTable;
    }

    // To save some time, the following code is from:
    // https://sp18.datastructur.es/materials/hw/hw7/hw7
    public static void main(String[] args) {
        // Read the file as 8 bit symbols.
        char[] inputSymbols = FileUtils.readFile(args[0]);
        // Build frequency table.
        Map<Character, Integer> frequencyTable = buildFrequencyTable(inputSymbols);
        //Use frequency table to construct a binary decoding trie.
        BinaryTrie root = new BinaryTrie(frequencyTable);
        // Write the binary decoding trie to the .huf file.
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(root);
        // (optional: write the number of symbols to the .huf file)
        ow.writeObject(inputSymbols.length);
        // Use binary trie to create lookup table for encoding.
        Map<Character, BitSequence> lookupTable = root.buildLookupTable();
        // Create a list of bitsequences.
        List<BitSequence> bitSequences = new ArrayList<>();
        /* For each 8 bit symbol:
         * Lookup that symbol in the lookup table.
         * Add the appropriate bit sequence to the list of bitsequences.*/
        for (char ch : inputSymbols) {
            bitSequences.add(lookupTable.get(ch));
        }
        // Assemble all bit sequences into one huge bit sequence.
        BitSequence result = BitSequence.assemble(bitSequences);
        // Write the huge bit sequence to the .huf file.
        ow.writeObject(result);

    }
}
