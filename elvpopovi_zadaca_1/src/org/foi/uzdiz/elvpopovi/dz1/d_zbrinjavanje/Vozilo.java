/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje;

import java.util.ArrayList;
import org.foi.uzdiz.elvpopovi.dz1.b_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz1.c_komuna.Ulica;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.Ispisivanje;
import org.foi.uzdiz.elvpopovi.dz1.f_podrska.RandomGenerator;

/**
 *
 * @author elvis
 */
public class Vozilo
{
    String naziv;
    int vrsta, tip, ciklusaOdvoz;
    int brojSpremnika, brojMjesta, brojOdlazakaNaDeponij;
    float ukupnaKolicinaOtpada;
    
    private boolean napunjen;
    private String[] vozaci;
    private float popunjenost;
    private int nosivost, brojacOdvoza;
    private ArrayList<Ulica> dodijeljeneUlice;
    private ArrayList<ArrayList<Spremnik>> dodijeljeniSpremnici;
    private int trenutnaUlica, trenutniSpremnik;
    public ArrayList<Ulica> getDodijeljeneUlice() 
    {
        return dodijeljeneUlice;
    }

    public ArrayList<ArrayList<Spremnik>> getDodijeljeniSpremnici() 
    {
        return dodijeljeniSpremnici;
    }

    public void setDodijeljeneUlice(ArrayList<Ulica> ulice) 
    {
        dodijeljeneUlice = ulice;
        //ispisiUlice();
    }
    public void setDodijeljeniSpremnici(ArrayList<ArrayList<Spremnik>> spremnici)
    {
        dodijeljeniSpremnici = spremnici;
        //ispisiSpremnike();
    }

    public String getNaziv() 
    {
        return naziv;
    }

    public String[] getVozaci() 
    {
        return vozaci;
    }

    public int getNosivost() 
    {
        return nosivost;
    }

    public float getPopunjenost() 
    {
        return popunjenost;
    }
    public int getBrojSpremnika()
    {
        return brojSpremnika;
    }
    public int getBrojMjesta()
    {
        return brojMjesta;
    }
    public float getKolicinaOtpada()
    {
        return ukupnaKolicinaOtpada;
    }
    public int getBrojOdlazakaNaDeponij()
    {
        return brojOdlazakaNaDeponij;
    }

    public int getVrsta() 
    {
        return vrsta;
    }
    public int getTip() 
    {
        return tip;
    }
    public Vozilo(int vrsta)
    {
        this.vrsta = vrsta;
    }
    public Vozilo(Vozilo original, String naziv, int tip, String[] vozaci, int nosivost)
    {
        this.naziv = naziv;
        this.vrsta = original.getVrsta();
        this.tip = tip;
        this.nosivost=nosivost;
        this.vozaci = new String[vozaci.length];
        
        System.arraycopy(this.vozaci,0,vozaci,0,vozaci.length);
        popunjenost=(float) 0.0;
        trenutnaUlica=0;
        trenutniSpremnik=0;
        napunjen = false;
        
        brojacOdvoza=0;
        brojSpremnika=0;
        brojMjesta=0;
        brojOdlazakaNaDeponij = 0;
        float ukupnaKolicinaOtpada=(float) 0.0;
    }
    //vraca true kad zavrsi sve spremnike
    public boolean IsprazniSpremnik()
    {
        Parametri parametri = Parametri.getInstance();
        RandomGenerator rnd = RandomGenerator.getInstance();
        Ispisivanje ispis = Ispisivanje.getInstance();
        parametri.DajVrijednost("brojDecimala");
        int brojDecimala = parametri.DajVrijednost("brojDecimala");
        if(trenutnaUlica>=dodijeljeneUlice.size())
        {
            if(popunjenost==0.0)
            {
                return true;
            }
            else
            {
                napunjen = true;
                if(parametri.DajVrijednost("ispis")==0)
                    ispis.Ispisi("Vozilo "+naziv+" je obavilo sve ulice i odlazi poluprazno na deponij.");
                brojOdlazakaNaDeponij++;
                return false;
            }
        }
        ArrayList<Spremnik> spremnici = dodijeljeniSpremnici.get(trenutnaUlica);
        if(trenutniSpremnik>=spremnici.size())
        {
            trenutnaUlica++;
            trenutniSpremnik=0;
            return false;
        }
        Spremnik spremnik = spremnici.get(trenutniSpremnik);
        if(popunjenost+spremnik.getKolicinaOtpada()>(float)nosivost)
        {
            napunjen = true;
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo "+naziv+" je napunjeno pa odlazi na deponij istovariti otpad.");
            brojOdlazakaNaDeponij++;
            return false;
        }
        if(spremnik.getKolicinaOtpada()>0)
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo "+naziv+" preuzima "+rnd.round(spremnik.getKolicinaOtpada(),brojDecimala)+" kg otpada.");
            brojSpremnika++;
            brojMjesta+=spremnik.DajKorisnike().size();
            ukupnaKolicinaOtpada+=spremnik.getKolicinaOtpada();
            popunjenost += spremnik.IsprazniSpremnik();
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("   otpad u vozilu: "+rnd.round(popunjenost,brojDecimala)+" kg, preostalo: "+rnd.round((float)nosivost-popunjenost,brojDecimala)+" kg");
        }
        else
            try
            {
                if(parametri.DajVrijednost("ispis")==0)
                    ispis.Ispisi("Vozilo "+naziv+" čeka u ulici "+spremnik.DajKorisnike().get(0).getUlica().Naziv());
            }
            catch(Exception e)
            {
                if(parametri.DajVrijednost("ispis")==0)
                    ispis.Ispisi("Vozilo "+naziv+" čeka kod praznog spremnika.");
            }
        trenutniSpremnik++;
        return false;
    }
    public boolean ProvjeriNapunjenost(int ciklusaOdvoz)
    {
        this.ciklusaOdvoz = ciklusaOdvoz;
        if(napunjen)
        {
            napunjen = false;
            popunjenost = (float) 0.0;
            brojacOdvoza=ciklusaOdvoz;
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean ProvjeriOdvoz()
    {
        Ispisivanje ispis = Ispisivanje.getInstance();
        Parametri parametri = Parametri.getInstance();
        if(brojacOdvoza>0)
        {
            brojacOdvoza--;
            return false;
        }
        else
        {
            if(parametri.DajVrijednost("ispis")==0)
                ispis.Ispisi("Vozilo "+naziv+" se vraća prazno sa deponija otpada.");
            return true;
        }
    }
    
    
    //testiranje
    private void ispisiUlice()
    {
        System.out.println("Vozilo "+naziv+":");
        for(Ulica i:dodijeljeneUlice)
            System.out.print(" "+i.Naziv()+";");
        System.out.println("");
    }
    private void ispisiSpremnike()
    {
        System.out.println("Vozilo "+naziv+", vrsta: "+vrsta);
        for(int j=0; j<dodijeljeneUlice.size(); j++)
        {
            System.out.println("   Ulica: "+dodijeljeneUlice.get(j).Naziv());
            for(Spremnik i:dodijeljeniSpremnici.get(j))
                System.out.println("      Spremnik: "+i.getId()+", vrsta: "+i.getVrstaOtpada()+", tip: "+i.getVrstaSpremnika()+", kolicina otpada: "+i.getKolicinaOtpada());
        }     
    }

}
