import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pliki {

    public String readFile(String pathFile) {
        String evr = new String();
        String error = new String();
        //Path sciezka = Paths.get(pathFile);
       // byte[] trescByte = new byte[ ]
       // FileInputStream inStream

        String data="";


        try {
            //byte[] tab = Files.readAllBytes(sciezka);
            //evr = new String(tab, "UTF-8");
            data=new String(Files.readAllBytes(Paths.get(pathFile)));
        } catch (FileNotFoundException var5) {
            error = "Błąd";
            return error;
        } catch (IOException var6) {
            error = "zła ścieżka";
            return error;
        }

       // return evr;
        return data;
    }
    public void writeFile(String pathFile, byte[] cipher) throws IOException {

        Path path = Paths.get(pathFile);
            Files.write(path, cipher);




    }
}
