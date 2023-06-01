import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String directory = "./files/ToDecode";
        System.out.println("Hello world!");
        String file = utils.SearchFile(directory);
        String text = utils.readTextFromFile(file, directory);
        //System.out.println(text);

        HuffmanTree huffmanTree = new HuffmanTree(null);
        System.out.println(huffmanTree.compress(text));

    }
}


class utils{

    public static String readFile(String fileName){
        StringBuilder text = new StringBuilder();
        try{

            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append("\n");
            }
            reader.close();
        }  catch (IOException err){
            System.out.println(err.getMessage());
            System.exit(2);
        }
        return text.toString();
    }

    private static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static String SearchFile(String dir){
        Scanner readText = new Scanner(System.in);
        System.out.println("Select the file you need: ");
        List<String> fileList = new ArrayList<String>();
        Set<String> files = listFilesUsingJavaIO(dir);
        int e = 0 ,i = 0;
        for (String file : files) {
            fileList.add(i,file);
            System.out.println(i+1 + ". " + file);
            i++;
        }

        do {
        e = readText.nextInt();
        } while(e == 0);

        try{
        return fileList.get(e-1);
        } catch(IndexOutOfBoundsException err){
            System.out.println(err.getMessage() + ". Try again.");
        }

        return SearchFile(dir);  //recursion para que se les acabe la memoria por chistosos
    }

    public static String readTextFromFile(String file, String path){
        try{
            StringBuilder text = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader(path+"/"+file));

            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line).append(" ");
            }

            reader.close();
            return text.toString();
        } catch (IOException err){
            System.out.println(err.getMessage());
            System.out.println("Try again.");
        }
        return "nope";//readTextFromFile(file);
    }
}