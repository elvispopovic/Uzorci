/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.i_MVC;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
/**
 * Controller klasa MVC uzorka
 * @author elvis
 */
public class MVCController extends MVCObserver
{
    private MVCView view;
    private MVCModelSucelje model; //model je dispecer
    private int brg, brd;
    private int brojRedakaIspisa;
    private Scanner inScan;
    private boolean komandniMod;
    
    public MVCController(int brg, int brd)
    {
        Parametri parametri = Parametri.getInstance();
        inScan = new Scanner(System.in,"CP"+parametri.DajVrijednost("kodnaStranica")); 
        brojRedakaIspisa = 0;
        this.brg = brg;
        this.brd = brd;
        komandniMod = false;
    }
    /**
     * Inicijalizra se kontroler MVC uzorka
     * @param model model MVC uzorka 
     * @param view view MVC uzorka
     */
    public void InicijalizirajMV(MVCModelSucelje model, MVCView view)
    {
        if(this.model != null)
            model.OdjaviMVC(this);
        this.model = model;
        this.view  = view;
        this.model.PrikljuciMVC(this);
    }
    /**
     * Prazna metoda, ovdje samo zbog kompatifilnosti sa MVCObserver sučeljem
     */
    public void ObradiDogadjaj()
    {
        
    }
    /**
     * Osvježavanje koje se jednako tako šalje i Viewu
     * @param cekanje da li se čeka potvrda korisnika
     */
    @Override
    public void Osvjezi(boolean cekanje)
    {
        ArrayList<String> redciIspisa = model.DohvatiPodatkeMVC();
        if(redciIspisa != null)
            brojRedakaIspisa = redciIspisa.size();
        Prikazi(cekanje);
        if(cekanje == false)
            view.prikaziKomandniDio("");
    }  
    /**
     * Poziva se kada je MVC u stanju izvršavanja korisničkih komandi
     * Tada nema logičkih podjela, već se izvršava sve što komanda radi bez da se konzultira korisnik 
     * Kada se obavi sve, obavještava se korisnik da može pregledavati rezultat
     */
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
        komandniMod = true;
        do
        {
            view.prikaziKomandniDio("Unesite naredbu... (za izlaz koristite IZLAZ;)");
            System.out.print("\033[1;36m");
            komanda = inScan.nextLine(); 
            System.out.print("\033[0m");
            view.prikaziKomandniDio("\033[1;36m"+komanda);
            razdvojeno = komanda.split(Pattern.quote(";"));
            model.KomandaMVC(razdvojeno);
        }while(!razdvojeno[0].toUpperCase().equals("IZLAZ"));
        komandniMod = false;
    }
    /**
     * Prikazivanje obuhvaća poziv viewa da zaista sve prikaže na ekranu, red po red
     * dok se ne popuni broj redaka određen sa --brg parametrom
     * @param cekanje da li se čeka korisnički unos
     */
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
    /**
     * Čekanje korisničkog unosa
     * @param ostatak koliko je preostalo redaka za ispis
     * @return true ako se ispisuje dalje, false ako korisnik želi preskočiti ispis ili logičku cjelinu
     */
    private boolean nastavak(int ostatak)
    {
        String s;
        if((ostatak<=0 || brojRedakaIspisa<brg) && komandniMod)
            return true;
        do
        {
            view.prikaziKomandniDio("\033[1;34mZa nastavak unesite \033[1;33mN\033[1;34m ili \033[1;33mn\033[1;34m,"
                    + " za preskakanje ispisa \033[1;33mP\033[1;34m ili \033[1;33mp\033[1;34m.");
            System.out.print("\033[1;34m");
            s = inScan.nextLine(); 
            System.out.print("\033[0m");
            view.prikaziKomandniDio(s);
            if(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"))
            {
                view.prikaziKomandniDio("\033[1;31mNeispravan unos.");
            }
        } while(!s.toUpperCase().equals("P")&&!s.toUpperCase().equals("N"));
        if(s.toUpperCase().equals("P"))
        {
            view.ObrisiPrezentacijskiDio();
            return false;   
        }
        return true;
    }
}
