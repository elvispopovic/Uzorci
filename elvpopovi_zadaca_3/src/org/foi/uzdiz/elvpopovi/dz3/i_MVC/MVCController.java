/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_MVC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class MVCController extends MVCObserver
{
    private MVCView view;
    private MVCModelSucelje model; //model je dispecer
    private int brg, brd;
    private int brojRedakaIspisa;
    private Scanner inScan;
    boolean komandniMod;
    
    public MVCController(int brg, int brd)
    {
        inScan = new Scanner(System.in); 
        brojRedakaIspisa = 0;
        komandniMod=false;
        this.brg = brg;
        this.brd = brd;
    }
    
    public void InicijalizirajMV(MVCModelSucelje model, MVCView view)
    {
        if(this.model != null)
            model.OdjaviMVC(this);
        this.model = model;
        this.view  = view;
        this.model.PrikljuciMVC(this);
    }
    
    public void ObradiDogadjaj()
    {
        
    }
    
    @Override
    public void Osvjezi()
    {
        ArrayList<String> redciIspisa = model.DohvatiPodatkeMVC();
        if(redciIspisa != null)
            brojRedakaIspisa = redciIspisa.size();
        Prikazi(true);
    }  
    
    public void KomandniMod()
    {
        String komanda;
        String[] razdvojeno;
        ArrayList<String> parametri = new ArrayList<>();
        ArrayList<String> redciIspisa = model.DohvatiPodatkeMVC();
        if(redciIspisa != null)
        {
            brojRedakaIspisa = redciIspisa.size();
            if(brojRedakaIspisa>brg)
                Prikazi(true);
            else
                Prikazi(false);
        }
        komandniMod=true;
        do
        {
            view.prikaziKomandniDio("Unesi naredbu...");
            komanda = inScan.nextLine(); 
            view.prikaziKomandniDio(komanda);
            razdvojeno = komanda.split(Pattern.quote(";"));
            model.KomandaMVC(razdvojeno);
        }while(!razdvojeno[0].toUpperCase().equals("IZLAZ"));
        komandniMod=false;
    }
    
    private void Prikazi(boolean cekanje)
    {
        int j;
        int i = 0;
        while(i<brojRedakaIspisa)
        {
            i+=view.prikaziPrezentacijskiDio(i);
            int ostatak=brojRedakaIspisa-i;
            if(cekanje && !nastavak(ostatak))
                break;
        }
        String[] komanda = new String[1];
        komanda[0]="OBRISI_PODATKE";
        model.KomandaMVC(komanda);
        brojRedakaIspisa = 0;
    }
    
    
    private boolean nastavak(int ostatak)
    {
        String s;
        if(komandniMod && ostatak<brg)
            return true;
        do
        {
            view.prikaziKomandniDio("Za nastavak unesi N ili n, za preskakanje ispisa P ili p.");
            s = inScan.nextLine(); 
            view.prikaziKomandniDio(s);
            if(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"))
                view.prikaziKomandniDio("Neispravan unos.");
        } while(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"));
        if(s.toUpperCase().equals("P"))
        {
            view.ObrisiPrezentacijskiDio();
            return false;   
        }
        return true;
    }
}
