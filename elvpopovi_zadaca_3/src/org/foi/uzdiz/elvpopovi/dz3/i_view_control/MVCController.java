/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_view_control;

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
    
    public MVCController(int brg, int brd)
    {
        inScan = new Scanner(System.in); 
        brojRedakaIspisa = 0;
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
        do
        {
            view.prikaziKomandniDio("Unesi naredbu...");
            komanda = inScan.nextLine(); 
            view.prikaziKomandniDio(komanda);
            razdvojeno = komanda.split(Pattern.quote(";"));
            if(razdvojeno.length>1) //komanda sa parametrima vozila
                parametri = new ArrayList<>(Arrays.asList(razdvojeno[1].split(Pattern.quote(","))));  
            model.KomandaMVC(razdvojeno);
        }while(!razdvojeno[0].toUpperCase().equals("IZLAZ"));
    }
    
    private void Prikazi(boolean cekanje)
    {
        int j;
        int i = 0;
        while(i<brojRedakaIspisa)
        {
            i+=view.prikaziPrezentacijskiDio(i);
            if(cekanje && !nastavak())
                break;
        }
        String[] komanda = new String[1];
        komanda[0]="OBRISI_PODATKE";
        model.KomandaMVC(komanda);
        brojRedakaIspisa = 0;
    }
    
    
    private boolean nastavak()
    {
        String s;
        do
        {
            view.prikaziKomandniDio("Za nastavak unesi N ili n, za preskakanje ispisa P ili p.");
            s = inScan.nextLine(); 
            view.prikaziKomandniDio(s);
            if(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"))
                view.prikaziKomandniDio("Neispravan unos.");
        } while(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"));
        if(s.toUpperCase().equals("P"))
            return false;             
        return true;
    }
}
