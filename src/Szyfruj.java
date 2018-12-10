import java.util.Random;
import Library.Matma;
import java.util.Vector;

public class Szyfruj {

    private BigInteger p;
    private BigInteger q;
    public byte[] tekstJawny;
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
        }while(!(a.mod(cztery).equals(trzy)));

        return a;
    }

    public BigInteger[] szyfruj()
    {

        int i=0,j=0,blockSize=256;
        m=new BigInteger[(tekstJawny.length/blockSize)+1];

        byte[] pom=new byte[blockSize];

        tekst=new String("Ala ma kota");
        System.arraycopy(tekst.getBytes(),0,pom,0,tekst.getBytes().length);
        int tekstLength=tekst.getBytes().length;
        while(tekstJawny.length-i >= blockSize-tekstLength)
        {
            System.arraycopy(tekstJawny, i, pom, tekstLength, blockSize-tekstLength);
            //BigInteger pom2=new BigInteger(pom);
            m[j]=new BigInteger(pom);
            i=i+(blockSize-tekstLength);
            j++;
        }
        System.arraycopy(tekstJawny,i,pom,tekstLength,tekstJawny.length-i);
        m[j]=new BigInteger(pom);
        j++;
        //System.out.println(m[0]);
        /*byte[] tabB=m[0].toByteArray();
        String tabS=new String(tabB);
        System.out.println(tabS);*/
        BigInteger[] c=new BigInteger[j];
        // m = new BigInteger(tekstJawny);

        do {
            p = getPrime();
            q = getPrime();
            if(p.compareTo(q) == -1)
            {
                BigInteger pom2;
                pom2 = p;
                p = q;
                q = pom2;
            }
            n = p.multiply(q);
        } while (p.equals(q) || m[0].compareTo(n) != -1);
        //System.out.println("P:"+p);
        for(int k=0; k<j; k++) {

            c[k] = m[k].pow(2).mod(n);
        }
        return c;
    }

    public byte[] deszyfruj(BigInteger[] zaszyfr)
    {
        BigInteger x_p, x_q, y_p1, y_p2, y_q1, y_q2, m_1, m_2, m_3, m_4;
        byte[] result= new byte[zaszyfr.length*256];
        for(int i=0; i<zaszyfr.length;i++) {


            x_p = zaszyfr[i].mod(p);
            x_q = zaszyfr[i].mod(q);
            y_p1 = new BigInteger(String.valueOf(x_p));

            y_p1 = x_p.modPow(p.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)), p);
            y_p2 = p.subtract(y_p1);

            y_q1 = x_q.modPow(q.add(BigInteger.valueOf(1)).divide(BigInteger.valueOf(4)), q);
            y_q2 = q.subtract(y_q1);

            BigInteger[] uv=chinol();

            m_1 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p1)).mod(n);
            m_2 = p.multiply(uv[0]).multiply(y_q1).subtract(q.multiply(uv[1]).multiply(y_p2)).mod(n);
            m_3 = m_1.multiply((BigInteger.valueOf(-1))).mod(n);
            m_4 = m_2.multiply((BigInteger.valueOf(-1))).mod(n);
            //m_2 = m_3.multiply(BigInteger.valueOf(-1)).mod(n);
            //m_4 = m_1.multiply((BigInteger.valueOf(-1))).mod(n);

            byte[] m1B=m_1.toByteArray();
            byte[] m2B=m_2.toByteArray();
            byte[] m3B=m_3.toByteArray();
            byte[] m4B=m_4.toByteArray();

            String m1S=new String(m_1.toByteArray());
            String m2S=new String(m_2.toByteArray());
            String m3S=new String(m_3.toByteArray());
            String m4S=new String(m_4.toByteArray());

            /*System.out.println("M1:"+m_1);
            System.out.println("M2:"+m_2);
            System.out.println("M3:"+m_3);
            System.out.println("M4:"+m_4);*/

            if (sprawdzanko(m_1)) System.arraycopy(m_1.toByteArray(), 0, result, i * 256,  m_1.toByteArray().length);
            if (sprawdzanko(m_2)) System.arraycopy(m_2.toByteArray(), 0, result, i * 256,  m_2.toByteArray().length);
            if (sprawdzanko(m_3)) System.arraycopy(m_3.toByteArray(), 0, result, i * 256,  m_3.toByteArray().length);
            if (sprawdzanko(m_4)) System.arraycopy(m_4.toByteArray(), 0, result, i * 256,  m_4.toByteArray().length);

        }
        return result;


    }

    public BigInteger[] chinol(){

        BigInteger d = new BigInteger(String.valueOf(p));
        BigInteger e = new BigInteger(String.valueOf(q));
        BigInteger u, v;

        /*if(d.compareTo(e) == -1)
        {
            BigInteger pom;
            pom = d;
            d = e;
            e = pom;
        }*/

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

        for(int i = 0; i < q.size()-1; i++)
        {
            P.addElement(P.elementAt(i+1).multiply(q.elementAt(i)).add(P.elementAt(i)));
            Q.addElement(Q.elementAt(i+1).multiply(q.elementAt(i)).add(Q.elementAt(i)));
        }

        u = Q.lastElement();
        v = P.lastElement();

        if(q.size()%2 == 0)
        {
            u = u.multiply(BigInteger.valueOf(-1));
            v = v.multiply(BigInteger.valueOf(-1));
        }

        BigInteger[] result=new BigInteger[2];
        //result = p.multiply(u).multiply(b).subtract(this.q.multiply(v).multiply(a)).mod(n);
        result[0]=u;
        result[1]=v;
        return result;
    }


    public boolean sprawdzanko(BigInteger m)
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