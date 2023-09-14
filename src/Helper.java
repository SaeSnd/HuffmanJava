import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
// HOLA ESTE ES UN COMENTARIO
public class Helper {
    public static String Decompress(String text, String dataFile) throws IOException {
        String textCompressed = "", helper = "", numTxt = "", textDecompressed = "";
        HashMap<String,Character> codes = new HashMap<>();
        int i = 0;
        for(char e : dataFile.toCharArray()){
            if(e == '\n') break;
            numTxt += e; i++;
        }
        System.out.println("Size: " + numTxt);
        int size = Integer.parseInt(numTxt);
        dataFile = dataFile.substring(i+1);
        int times = 0;
        for(char e: dataFile.toCharArray()){
            if(e == '\n'){
                times++;
                if(times == 1){
                    codes.put(helper.substring(1),helper.toCharArray()[0]);
                    helper = "";
                } else {
                    times = 0;
                    helper += e;
                }
            } else{
                times = 0;
                helper +=e;
            }
        }
        for (Map.Entry<String, Character> entry : codes.entrySet())
            System.out.println(entry.getKey() + ": [" + entry.getValue() + "].");

        for(char e : text.toCharArray()){
            helper = Integer.toString(e,2);
            for(int in = 0; in < 8-helper.length(); in++) textCompressed += "0";
            textCompressed += helper;
        }
        helper = "";
        textCompressed = textCompressed.substring(0,size);
        for(char e : textCompressed.toCharArray()){
            helper += e;
            if(codes.get(helper) == null) continue;
            textDecompressed += codes.get(helper);
            helper = "";
        }
        textDecompressed = textDecompressed.substring(0,textDecompressed.length()-1);
        System.out.println("\n--------------------------\nEnd of character conversion. Saving file... ");
        return textDecompressed;
    }
}
