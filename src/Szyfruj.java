import java.util.Random;
import java.math.BigInteger;
import java.util.Vector;

public class Szyfruj {

    private BigInteger p;
    private BigInteger q;
    private byte[] tekstJawny;
    private BigInteger n;
    private BigInteger[] m;
    private String tekst;

    public Szyfruj(byte[] tekstJawny){
        this.tekstJawny=tekstJawny;
    }

    public BigInteger getPrime() {

        BigInteger a;
        Random rnd = new Random();
        BigInteger trzy=BigInteger.valueOf(3);
        BigInteger cztery=BigInteger.valueOf(4);
        do{
            a = BigInteger.probablePrime(1024,rnd);
        }while(a.mod(cztery).equals(trzy));

        return a;
    }

    public BigInteger[] szyfruj()
    {
        BigInteger[] c;
        int i=0,j=0;
        m=new BigInteger[tekstJawny.length/256+1];

        byte[] pom= new byte [256];

        tekst=new String("Ala ma kota");
        pom=tekst.getBytes();
        int tekstLength=tekst.getBytes().length;
        while(tekstJawny.length-i-tekstLength>0)
        {
            System.arraycopy(tekstJawny, i, pom, tekstLength, 256-tekstLength);
            m[j]=new BigInteger(pom);
            i+=tekstLength;
            j++;
        }
        System.arraycopy(tekstJawny,i,pom,tekstLength,tekstJawny.length-i);
        m[j]=new BigInteger(pom);
        j++;
        c=new BigInteger[j];
       // m = new BigInteger(tekstJawny);

        do {
            p = getPrime();
            q = getPrime();

            n = p.multiply(q);
        } while (p.equals(q) || !(m[0].compareTo(n) == -1));

        for(int k=0; k<j; k++) {

            c[k] = m[k].multiply(m[k]).mod(n);
        }
        return c;
    }

    public BigInteger deszyfruj(BigInteger[] zaszyfr)
    {
        BigInteger x_p, x_q, y_p1, y_p2, y_q1, y_q2, m_1, m_2, m_3, m_4, zmienna = BigInteger.valueOf(0);
        byte[] g_1, g_2, g_3, g_4;

        for(int i = 0; i < zaszyfr.length; i++)
        {
            zmienna = zmienna.add(zaszyfr[i]);              //??
        }
        x_p = zmienna.mod(p);
        x_q = zmienna.mod(q);
        y_p1 = new BigInteger(String.valueOf(x_p));

        y_p1 = x_p.modPow(p.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)), p);
        y_p2 = p.subtract(y_p1);

        y_q1 = x_q.modPow(q.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)), q);
        y_q2 = q.subtract(y_q1);

        m_1 = chinol(y_p1, y_q1);
        m_3 = chinol(y_p1, y_q2);
        m_2 = m_3.multiply(BigInteger.valueOf(-1)).mod(n);
        m_4 = m_1.multiply((BigInteger.valueOf(-1))).mod(n);

        g_1 = m_1.toByteArray();
        g_2 = m_2.toByteArray();
        g_3 = m_3.toByteArray();
        g_4 = m_4.toByteArray();

        byte[] textArray = tekst.getBytes();

        for(int l = 1; l < tekst.length(); l++)
        {
            if(g_1[l] == textArray[l])
            {

            }

        }

        return dobre(m);                                        // :)))
    }

    public BigInteger chinol(BigInteger a, BigInteger b){

        BigInteger d = new BigInteger(String.valueOf(p));
        BigInteger e = new BigInteger(String.valueOf(q));
        BigInteger u, v;

        if(d.compareTo(e) == -1)
        {
            BigInteger pom;
            pom = d;
            d = e;
            e = pom;
        }

        Vector<BigInteger> P = new Vector<>();
        Vector<BigInteger> Q = new Vector<>();
        Vector<BigInteger> q = new Vector<>();

        P.addElement(BigInteger.valueOf(0));
        P.addElement(BigInteger.valueOf(1));
        Q.addElement(BigInteger.valueOf(1));
        Q.addElement(BigInteger.valueOf(0));

        while(e.compareTo(BigInteger.valueOf(0)) != 0) {
            q.addElement(d.divide(e));

            BigInteger pom = new BigInteger(String.valueOf(d));
            d = e;
            e = pom.mod(e);
        }

        for(int i = 0; i < q.size(); i++)
        {
            P.addElement(P.elementAt(i+1).multiply(q.elementAt(i)).add(P.elementAt(i)));
            Q.addElement(Q.elementAt(i+1).multiply(q.elementAt(i)).add(Q.elementAt(i)));
        }

        u = Q.lastElement();
        v = P.lastElement();

        if(q.size()%2 == 1)
        {
            u = u.multiply(BigInteger.valueOf(-1));
            v = v.multiply(BigInteger.valueOf(-1));
        }

        BigInteger result;
        result = p.multiply(u).multiply(b).subtract(this.q.multiply(v).multiply(a)).mod(n);

        return result;
    }

}