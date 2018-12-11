import java.util.Random;
import Library.CalkowicieBig;
import java.util.Vector;

public class Szyfruj {

    private CalkowicieBig p;
    private CalkowicieBig q;
    public byte[] tekstJawny;
    private CalkowicieBig n;
    private CalkowicieBig[] m;
    private String tekst;

    public Szyfruj(byte[] tekstJawny)
    {
        this.tekstJawny=tekstJawny;
    }

    public CalkowicieBig getPrime() {

        CalkowicieBig a;
        Random rnd = new Random();
        CalkowicieBig trzy=CalkowicieBig.valueOf(3);
        CalkowicieBig cztery=CalkowicieBig.valueOf(4);
        do{
            a = CalkowicieBig.probablePrime(1024,rnd);
        }while(!(a.mod(cztery).equals(trzy)));

        return a;
    }

    public CalkowicieBig[] szyfruj()
    {

        int i=0,j=0,blockSize=256;


        byte[] pom=new byte[blockSize];

        tekst=new String("Ala ma kota");
        System.arraycopy(tekst.getBytes(),0,pom,0,tekst.getBytes().length);
        int tekstLength=tekst.getBytes().length;

        m=new CalkowicieBig[(tekstJawny.length/(blockSize-tekstLength))+1];
        while(tekstJawny.length-i >= blockSize-tekstLength)
        {
            System.arraycopy(tekstJawny, i, pom, tekstLength, blockSize-tekstLength);
            m[j]=new CalkowicieBig(pom);
            i=i+(blockSize-tekstLength);
            j++;
        }
        System.arraycopy(tekstJawny,i,pom,tekstLength,tekstJawny.length-i);
        m[j]=new CalkowicieBig(pom);
        j++;
        CalkowicieBig[] c=new CalkowicieBig[j];

        do {
            p = getPrime();
            q = getPrime();
            if(p.compareTo(q) == -1)
            {
                CalkowicieBig pom2;
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

    public byte[] deszyfruj(CalkowicieBig[] zaszyfr)
    {
        CalkowicieBig x_p, x_q, y_p1, y_p2, y_q1, y_q2, m_1, m_2, m_3, m_4;
        byte[] result= new byte[zaszyfr.length*256];
        for(int i=0; i<zaszyfr.length;i++) {


            x_p = zaszyfr[i].mod(p);
            x_q = zaszyfr[i].mod(q);
            y_p1 = new CalkowicieBig(String.valueOf(x_p));

            y_p1 = x_p.modPow(p.add(CalkowicieBig.valueOf(1)).divide(CalkowicieBig.valueOf(4)), p);
            y_p2 = p.subtract(y_p1);

            y_q1 = x_q.modPow(q.add(CalkowicieBig.valueOf(1)).divide(CalkowicieBig.valueOf(4)), q);
            y_q2 = q.subtract(y_q1);

            CalkowicieBig[] uv=chinol();

            m_1 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p1)).mod(n);
            m_2 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p2)).mod(n);
            m_3 = m_1.multiply((CalkowicieBig.valueOf(-1))).mod(n);
            m_4 = m_2.multiply((CalkowicieBig.valueOf(-1))).mod(n);

            if (sprawdzanko(m_1)) System.arraycopy(m_1.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_1.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_2)) System.arraycopy(m_2.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_2.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_3)) System.arraycopy(m_3.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_3.toByteArray().length-tekst.getBytes().length);
            if (sprawdzanko(m_4)) System.arraycopy(m_4.toByteArray(), tekst.getBytes().length, result, i * (256-tekst.getBytes().length),  m_4.toByteArray().length-tekst.getBytes().length);
        }
        return result;
    }

    public CalkowicieBig[] chinol(){

        CalkowicieBig d = new CalkowicieBig(String.valueOf(p));
        CalkowicieBig e = new CalkowicieBig(String.valueOf(q));
        CalkowicieBig u, v;

        Vector<CalkowicieBig> P = new Vector<>();
        Vector<CalkowicieBig> Q = new Vector<>();
        Vector<CalkowicieBig> q = new Vector<>();

        P.addElement(CalkowicieBig.valueOf(0));
        P.addElement(CalkowicieBig.valueOf(1));
        Q.addElement(CalkowicieBig.valueOf(1));
        Q.addElement(CalkowicieBig.valueOf(0));

        while(e.compareTo(CalkowicieBig.valueOf(0)) != 0) {
            q.addElement(d.divide(e));

            CalkowicieBig pom = new CalkowicieBig(String.valueOf(d));
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
            u = u.multiply(CalkowicieBig.valueOf(-1));
            v = v.multiply(CalkowicieBig.valueOf(-1));
        }

        CalkowicieBig[] result=new CalkowicieBig[2];
        result[0]=u;
        result[1]=v;
        return result;
    }

    public boolean sprawdzanko(CalkowicieBig m)
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