import java.util.Random;
import Library.DuzeLiczby;
import java.util.Vector;

public class Szyfruj {

    private DuzeLiczby p;
    private DuzeLiczby q;
    public byte[] tekstJawny;
    private DuzeLiczby n;
    private DuzeLiczby[] m;
    private String tekst;

    public Szyfruj(byte[] tekstJawny)
    {
        this.tekstJawny=tekstJawny;
    }

    public DuzeLiczby getPrime() {

        DuzeLiczby a;
        Random rnd = new Random();
        DuzeLiczby trzy=DuzeLiczby.valueOf(3);
        DuzeLiczby cztery=DuzeLiczby.valueOf(4);
        do{
            a = DuzeLiczby.probablePrime(1024,rnd);
        }while(!(a.mod(cztery).equals(trzy)));

        return a;
    }

    public DuzeLiczby[] szyfruj()
    {

        int i=0,j=0,blockSize=256;


        byte[] pom=new byte[blockSize];

        tekst=new String("Ala ma kota");
        System.arraycopy(tekst.getBytes(),0,pom,0,tekst.getBytes().length);
        int tekstLength=tekst.getBytes().length;

        m=new DuzeLiczby[(tekstJawny.length/(blockSize-tekstLength))+1];
        while(tekstJawny.length-i >= blockSize-tekstLength)
        {
            System.arraycopy(tekstJawny, i, pom, tekstLength, blockSize-tekstLength);
            m[j]=new DuzeLiczby(pom);
            i=i+(blockSize-tekstLength);
            j++;
        }
        System.arraycopy(tekstJawny,i,pom,tekstLength,tekstJawny.length-i);
        m[j]=new DuzeLiczby(pom);
        j++;
        DuzeLiczby[] c=new DuzeLiczby[j];

        do {
            p = getPrime();
            q = getPrime();
            if(p.compareTo(q) == -1)
            {
                DuzeLiczby pom2;
                pom2 = p;
                p = q;
                q = pom2;
            }
            n = p.multiply(q);
        } while (p.equals(q) || m[0].compareTo(n) != -1);
        for(int k=0; k<j; k++) {

            c[k] = m[k].pow(2).mod(n);
        }
        return c;
    }

    public byte[] deszyfruj(DuzeLiczby[] zaszyfr)
    {
        DuzeLiczby x_p, x_q, y_p1, y_p2, y_q1, y_q2, m_1, m_2, m_3, m_4;
        byte[] result= new byte[zaszyfr.length*256];
        for(int i=0; i<zaszyfr.length;i++) {


            x_p = zaszyfr[i].mod(p);
            x_q = zaszyfr[i].mod(q);
            y_p1 = new DuzeLiczby(String.valueOf(x_p));

            y_p1 = x_p.modPow(p.add(DuzeLiczby.valueOf(1)).divide(DuzeLiczby.valueOf(4)), p);
            y_p2 = p.subtract(y_p1);

            y_q1 = x_q.modPow(q.add(DuzeLiczby.valueOf(1)).divide(DuzeLiczby.valueOf(4)), q);
            y_q2 = q.subtract(y_q1);

            DuzeLiczby[] uv=chinol();

            m_1 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p1)).mod(n);
            m_2 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p2)).mod(n);
            m_3 = m_1.multiply((DuzeLiczby.valueOf(-1))).mod(n);
            m_4 = m_2.multiply((DuzeLiczby.valueOf(-1))).mod(n);

            if (sprawdzanko(m_1)) System.arraycopy(m_1.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_1.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_2)) System.arraycopy(m_2.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_2.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_3)) System.arraycopy(m_3.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_3.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_4)) System.arraycopy(m_4.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_4.toByteArray().length-tekst.getBytes().length);
        }
        return result;
    }

    public DuzeLiczby[] chinol(){

        DuzeLiczby d = new DuzeLiczby(String.valueOf(p));
        DuzeLiczby e = new DuzeLiczby(String.valueOf(q));
        DuzeLiczby u, v;

        Vector<DuzeLiczby> P = new Vector<>();
        Vector<DuzeLiczby> Q = new Vector<>();
        Vector<DuzeLiczby> q = new Vector<>();

        P.addElement(DuzeLiczby.valueOf(0));
        P.addElement(DuzeLiczby.valueOf(1));
        Q.addElement(DuzeLiczby.valueOf(1));
        Q.addElement(DuzeLiczby.valueOf(0));

        while(e.compareTo(DuzeLiczby.valueOf(0)) != 0) {
            q.addElement(d.divide(e));

            DuzeLiczby pom = new DuzeLiczby(String.valueOf(d));
            d = e;
            e = pom.mod(e);
        }

        for(int i = 0; i < q.size()-1; i++)
        {
            P.addElement(P.elementAt(i+1).multiply(q.elementAt(i)).add(P.elementAt(i)));
            Q.addElement(Q.elementAt(i+1).multiply(q.elementAt(i)).add(Q.elementAt(i)));
        }

        u = Q.lastElement();
        v = P.lastElement();

        if(q.size()%2 == 0)
        {
            u = u.multiply(DuzeLiczby.valueOf(-1));
            v = v.multiply(DuzeLiczby.valueOf(-1));
        }

        DuzeLiczby[] result=new DuzeLiczby[2];
        result[0]=u;
        result[1]=v;
        return result;
    }

    public boolean sprawdzanko(DuzeLiczby m)
    {
        byte[] textArray = tekst.getBytes();
        byte[] mArray = m.toByteArray();
        for(int l = 0; l < textArray.length; l++)
        {
            if(mArray[l] != textArray[l])
            {
                return false;
            }
        }
        return true;
    }
}