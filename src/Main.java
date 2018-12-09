import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

public class Main {
    public static void main(String[] args){

        String tekst=new String("Prosze dzialaj");
        byte[] tekstB=tekst.getBytes();
        Szyfruj slowo=new Szyfruj(tekstB);
        System.out.println("Wiadomosc: "+tekst);
        /*BigInteger[] tab=slowo.chinol(BigInteger.valueOf(8),BigInteger.valueOf(35));
        System.out.println("u="+tab[0]);
        System.out.println("v="+tab[1]);*/

        //System.out.println("tekst jawny: " + slowo.tekstJawny.length/256+1);
        /*tekst=new String("Ala ma kota");
        int tekstLength=tekst.getBytes().length;*/
        BigInteger[] dzialaj=slowo.szyfruj();
        //String tab=new String(dzialaj[0].toByteArray());
       byte[] dzialajPliska=slowo.deszyfruj(dzialaj);
        String musiDzialac=new String(dzialajPliska);
        System.out.println("Odszyfrowana wiadomosc: "+ musiDzialac);
    }
}
