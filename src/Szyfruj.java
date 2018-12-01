import java.util.Random;
import java.math.BigInteger;

public class Szyfruj {

    private BigInteger p;
    private BigInteger q;
    private byte[] tekstJawny;

    public Szyfruj(byte[] tekstJawny){
        this.tekstJawny=tekstJawny;
    }

    public BigInteger szyfruj()
    {
        Random rnd = new Random();
        BigInteger czfurka=BigInteger.valueOf(4);
        do{
            p = BigInteger.probablePrime(1024,rnd);
            q = BigInteger.probablePrime(1024,rnd);
        }while(p!=q && p.mod(czfurka).equals(3) && q.mod(czfurka).equals(3));

        BigInteger n = p.multiply(q);
        BigInteger m = new BigInteger(tekstJawny);
        BigInteger c;
        c=m.multiply(m).mod(n);

        return c;
    }

    public BigInteger deszyfruj(BigInteger bandyckaJazda)
    {

        return nocipher;
    }

}

