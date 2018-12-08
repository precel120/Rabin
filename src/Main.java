import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){

        String tekst=new String("Prosze dzialajaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaddddddddddddddddddddddddddddddddddddddddddddddddddddddddssssssssssssssssssssssssssssssssssssssssdddd");
        byte[] tekstB=tekst.getBytes();
        Szyfruj slowo=new Szyfruj(tekstB);
        //System.out.println("tekst jawny: " + slowo.tekstJawny.length/256+1);
        /*tekst=new String("Ala ma kota");
        int tekstLength=tekst.getBytes().length;*/
        BigInteger[] dzialaj=slowo.szyfruj();
        //String tab=new String(dzialaj[0].toByteArray());
        byte[] dzialajPliska=slowo.deszyfruj(dzialaj);
        String musiDzialac=new String(dzialajPliska);
        //System.out.println(dzialajPliska[1]);
    }
}
