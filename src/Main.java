import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        switch (utils.SelectOption()) {
            case 1 -> utils.Encode();
            case 2 -> utils.Decode();
            case 3 -> System.exit(1);
        }
    }
}


class utils{

    public static void Encode(){
        String directory = "./files/ToEncode";
        String file = utils.SearchFile(directory);
        String text = utils.readTextFromFile(file, directory);

        HuffmanTree huffmanTree = new HuffmanTree(null);
        try{
            utils.writeToFile(huffmanTree.compress(text,file), "./files/ToDecode/" + file + "bin");
            System.out.println("File saved!");
        } catch( IOException e){
            System.out.println("Can't be saved. Check the directory or the name of the file.");
        }

    }
    public static void Decode() {
        String directory = "./files/ToDecode";
        String data = "./files/data";
        String file = utils.SearchFile(directory);
        String text = utils.readTextFromFile(file, directory);
        file = file.substring(0,file.length()-3);
        String dataFile = utils.readTextFromFile("dt-" + file, data);
        try{
            utils.writeToFile(Helper.Decompress(text,dataFile),"./files/Decompressed/" + "decompressed-" + file);
            System.out.println("File saved!");
        } catch (IOException e){
            System.out.println("Can't be saved. Check the directory or the name of the file.");
        }
    }
    public static int SelectOption(){
        Scanner readTxt = new Scanner(System.in);
        System.out.print("What you want to do?: \n\n\t1. Encode.\n\t2. Decode.\n\t3. Exit.\n\nSelect option: ");
        int result = readTxt.nextInt();
        System.out.println();
        if(result > 3 || result < 1) result = SelectOption();
        return result;
    }

    private static Set<String> listFilesUsingJavaIO(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static String SearchFile(String dir){
        Scanner readText = new Scanner(System.in);
        List<String> fileList = new ArrayList<>();
        Set<String> files = listFilesUsingJavaIO(dir);
        int e ,i = 0;
        if(files.isEmpty()){
            System.out.println("You don't have files here.\nTry adding files...");
            System.exit(-1);
        }
        System.out.println("Select the file you need: ");
        for (String file : files) {
            fileList.add(i,file);
            System.out.println(i+1 + ". " + file);
            i++;
        }

        do {
            System.out.print("\nSelect option: ");
            e = readText.nextInt();
            System.out.println();
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
                text.append(line).append("\n");
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