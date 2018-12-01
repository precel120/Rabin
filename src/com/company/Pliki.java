package com.company;

import java.io.*;
import java.nio.file.Files;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Pliki {
   public byte[] readFile (String pathFile)
    {
        File file = new File(pathFile);
        byte[] fileContent = new byte[(int) file.length()];
        FileInputStream fin = null;
        String evr = null;
        try{
        fin = new FileInputStream(file);
        fin.read(fileContent);
        //evr = new String(fileContent);
        }catch (FileNotFoundException e){
            System.out.print("Blad");
        }
        catch (IOException e){
            System.out.print("tez zle");
        }

        return fileContent;
    }
    public void writeFile(String pathfile, byte[] cipher) throws IOException{
       Path path = Paths.get(pathfile);

           Files.write(path, cipher);
    }
}

 /*   public String readFile (String pathFile)
    {
        String evr= new String();
        Path sciezka = Paths.get(pathFile);
        try{
            byte[] tab = Files.readAllBytes(sciezka);
            evr = new String(tab,"UTF-8");
        }catch (FileNotFoundException e){
            System.out.print("blad kurwa");
        }
        catch (IOException e){
            System.out.print("tez chujowo");
        }
        return evr;
        }
        */