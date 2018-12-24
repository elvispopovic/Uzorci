/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_podrska;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 *
 * @author elvis
 */
public class Ispisivanje 
{
    private static volatile Ispisivanje INSTANCA;
    private Ispisivanje() {}
    public static Ispisivanje getInstance(String staza) 
    { 
        if (INSTANCA == null) 
            synchronized(Ispisivanje.class) 
            { 
                if (INSTANCA == null) 
                {
                    INSTANCA = new Ispisivanje(); 
                    INSTANCA.staza = staza;
                    INSTANCA.aktivan=true;
                    if(INSTANCA.kreirajIzlaz()==false)
                        INSTANCA.aktivan=false;
                    if(INSTANCA.aktivan==true)
                        INSTANCA.Ispisi("Kreiran je ispisivač.");
                }
            } 
        return INSTANCA; 
    }
    public static Ispisivanje getInstance()
    {
        return INSTANCA;
    }
    public void Ispisi(String ispis)
    {
        Ispisi(ispis,true);
    }
    public void Ispisi(String ispis, boolean novaLinija)
    {
        if(novaLinija)
            System.out.println(ispis);
        else
            System.out.print(ispis+" ");
        if(aktivan)
            try
            {
                pisac = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(staza, true), "UTF-8"));
                if(novaLinija)
                    pisac.write(ispis+System.lineSeparator());
                else
                    pisac.write(ispis+" ");
                pisac.close();
            }
            catch(IOException e)
            {
                System.err.println("Greška prilikom pokušaja upisivanja u izlaznu datoteku.");
            }
    }
    
    private boolean kreirajIzlaz()
    {
        File file = new File(staza);
        try
        {
            if (file.exists())
                file.delete();
            file.createNewFile();
        }
        catch(IOException e)
        {
            System.err.println("Nije moguće kreirati datoteku za ispis izlaza.");
            return false;
        }
        return true;
    }
    private String staza;
    private BufferedWriter pisac;
    private boolean aktivan;
}
