import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    private HuffmanNode root;

    public HuffmanTree(HuffmanNode root) {
        this.root = root;
    }

    public void generateCodes(HuffmanNode node, String code, Map<Character, String> codes) {
        if (node == null) {
            return;
        }

        if (node.character != '\0') {
            codes.put(node.character, code);
        }

        generateCodes(node.left, code + "0", codes);
        generateCodes(node.right, code + "1", codes);
    }

    public PriorityQueue<HuffmanNode> getArrOfCharacters(String text){
        Map<Character, Integer> frequencyMap = new HashMap<>();

        for (char c : text.toCharArray()) {
            //if(c == ' ') continue;
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }

        return pq;
    }

    public HuffmanNode root(PriorityQueue<HuffmanNode> pq, Map<Character,Integer[]> costMap){

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            //System.out.println(left.character + ": " + left.frequency);
            HuffmanNode right = pq.poll();
            //System.out.println(right.character + ": " + right.frequency);

            assert right != null;
            HuffmanNode z = new HuffmanNode('\0', left.frequency + right.frequency);
            z.left = left;
            z.right = right;
            //System.out.println("z node : " + z.frequency);

            pq.offer(z);
            if(left.character != '\0'){
                costMap.put(left.character,new Integer[]{left.frequency,0});
            }
            if(right.character != '\0'){
                costMap.put(right.character,new Integer[]{right.frequency,0});
            }
        }

        return pq.poll();
    }

    public void showCost(Map<Character,Integer[]> costMap,Map<Character, String> codes){
        System.out.println("\nFrequency table of characters and binary value: ");
        for (Map.Entry<Character, String> entry : codes.entrySet()) {
            Integer[] valueActual = costMap.get(entry.getKey());
            String bin = entry.getValue();
            costMap.put(entry.getKey(), new Integer[]{valueActual[0],bin.length()});
            System.out.println(entry.getKey() + ": " + valueActual[0] + " and bin value: " + bin);
        }

        System.out.println("\nTotal cost of the Huffman tree: ");
        int sum = 0;
        for(Map.Entry<Character,Integer[]> entry : costMap.entrySet()){
            Integer[] valueNow = entry.getValue();
            sum += (valueNow[0]*valueNow[1]);
            System.out.print(valueNow[0] + "*" + valueNow[1] + " + ");
        }
        System.out.println("0 = " + sum);
    }
    public static void saveCodes(Map<Character, String> codes, String fileName, int size) throws IOException {
        String dataPath = "./files/data";
        BufferedWriter writer = new BufferedWriter(new FileWriter(dataPath + "/" + "dt-" + fileName));

        String codesText = String.valueOf(size);
        for (Map.Entry<Character, String> entry : codes.entrySet())
            codesText += "\n" + entry.getKey() + entry.getValue();
        writer.write(codesText);
        writer.close();
    }

    public String compress(String text, String fileName) {

        PriorityQueue<HuffmanNode> pq = getArrOfCharacters(text);
        Map<Character, Integer[]> costMap = new HashMap<>();

        HuffmanNode huffmanRoot = root(pq, costMap);
        this.root = huffmanRoot;

        Map<Character, String> codes = new HashMap<>();
        generateCodes(huffmanRoot, "", codes);

        showCost(costMap,codes);


        System.out.println("\n--------------------------\nStart of binary conversion: ");
        StringBuilder compressedText = new StringBuilder();
        String base = "";
        String helper = "";
        char nxtChar; int i;
        for (char c : text.toCharArray()){
            if(codes.get(c) == null) continue;
            base += codes.get(c);
        }
        //System.out.print(base);
        //System.out.println();

        int timesTo = base.length()/8;
        //System.out.println("\n\nTimes to conversion: " +timesTo);
        for(i = 0; i < timesTo*8; i+=8){
            //System.out.print(base.substring(i,i+8));
            nxtChar = (char)Integer.parseInt(base.substring(i,i+8),2);
            //String newAgain = Integer.toBinaryString(nxtChar);
            //for(int in = 0; in < 8-newAgain.length(); in++) System.out.print("0");
            //System.out.print(Integer.toBinaryString(nxtChar));
            compressedText.append(nxtChar);
        }
        int residue = base.length() % 8;
        //System.out.println("Residue: " +residue);
        if(residue > 0){
            for(int j = 0; j < 8 - residue; j++){
                helper += '1';
            }
            //System.out.println(base.substring(i,i+residue)+helper);
            nxtChar = (char)Integer.parseInt(base.substring(i,i+residue) + helper,2);
            compressedText.append(nxtChar);
        }

        System.out.println("\n--------------------------\nEnd of binary conversion. Saving file... ");
        try{
            saveCodes(codes,fileName,base.length());
        } catch(IOException err){
            System.out.println("Couldn't save file. \n" + err.getMessage());
        }
        return compressedText.toString();
    }
}