import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String directory = "./files/ToEncode";
        System.out.println("Hello world!");
        String file = utils.SearchFile(directory);
        String text = utils.readTextFromFile(file, directory);

        HuffmanTree huffmanTree = new HuffmanTree(null);
        try{
            utils.writeToFile(huffmanTree.compress(text), "./files/ToDecode/" + file + "bin");
            System.out.println("File saved!");
        } catch( IOException e){
            System.out.println("Can't be saved. Check the directory or the name of the file.");
        }

    }
}


class utils{

    private static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static String SearchFile(String dir){
        Scanner readText = new Scanner(System.in);
        System.out.println("Select the file you need: ");
        List<String> fileList = new ArrayList<>();
        Set<String> files = listFilesUsingJavaIO(dir);
        int e ,i = 0;
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

        return SearchFile(dir);  //Evil recursive
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
            System.exit(2);
        }
        return " ";
    }

    public static void writeToFile(String Text, String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(Text);
        writer.close();
    }
}