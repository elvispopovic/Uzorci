/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.g_upravljanje;

import org.foi.uzdiz.elvpopovi.dz3.c_podaci.Parametri;
import org.foi.uzdiz.elvpopovi.dz3.f_dinamika.SimulacijaSucelje;

/**
 *
 * @author elvis
 */
public class LanacTerminalni implements LanacKomandiApstraktni
{
    private final SimulacijaSucelje simulacija;
    private Parametri parametri;
    
    public LanacTerminalni(SimulacijaSucelje simulacija)
    {
        this.simulacija = simulacija;
        parametri = Parametri.getInstance();
    }
    
    @Override
    public void ObradiKomandu(String[] komanda)
    {
        simulacija.Ispisi("\033[1;35mNaredba \""+komanda[0]+"\" nije prepoznata.");
        simulacija.Ispisi("\033[1;32mNaredbe:");
        simulacija.Ispisi("  OBRADI;podrucje;lista_vozla");
        simulacija.Ispisi("  PRIPREMI;lista_vozla");
        simulacija.Ispisi("  KRENI [broj];");
        simulacija.Ispisi("  KVAR;lista_vozila");
        simulacija.Ispisi("  KONTROLA;lista_vozila");
        simulacija.Ispisi("  ISPRAZNI;lista_vozila");
        simulacija.Ispisi("  STATUS;");
        simulacija.Ispisi("  GODIŠNJI ODMOR;lista_vozača");
        simulacija.Ispisi("  BOLOVANJE;lista_vozača");
        simulacija.Ispisi("  OTKAZ;lista_vozača");
        simulacija.Ispisi("  PREUZMI;vozač;[vozilo;]");
        simulacija.Ispisi("  NOVI;lista_vozača");
        simulacija.Ispisi("  VOZAČI;");
        simulacija.Ispisi("  IZLAZ;");
        simulacija.Ispisi("Program radi sa kodnom stranicom unosa CP"+parametri.DajVrijednost("kodnaStranica"));
        simulacija.Ispisi("Kodnu stranicu možete promijeniti naredbom chcp "+parametri.DajVrijednost("kodnaStranica"));
        simulacija.Ispisi("Također možete koristiti unos naredbi bez hrvatskih znakova, npr. GODISNJI ODMOR");
    }

    @Override
    public void DodajSljedbenika(LanacKomandiApstraktni sljedbenik)
    {

    }
    
}
