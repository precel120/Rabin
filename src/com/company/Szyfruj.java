package com.company;

import java.util.Random;


public class Szyfruj {

    private byte[] klucz;
    private byte[] tekstJawny;

    public Szyfruj(byte[] tekstJawny)
    {
        this.tekstJawny=tekstJawny;
        klucz=new byte[this.tekstJawny.length];
        Random rnd = new Random();
        rnd.nextBytes(klucz);
    }

    public byte[] szyfruj()
    {
        byte[] cipher=new byte [tekstJawny.length];

        for(int i=0; i<tekstJawny.length; i++)
        {
            cipher[i]=(byte) (tekstJawny[i]^klucz[i]);
        }

        return cipher;
    }

    public byte[] deszyfruj(byte[] bytes)
    {
        //String txt_po;
        byte[] nocipher=new byte [bytes.length];
        for(int i=0; i<bytes.length; i++)
        {
            nocipher[i]=(byte) (bytes[i]^klucz[i]);
        }
        //txt_po = new String(nocipher);


        return nocipher;
    }

}
