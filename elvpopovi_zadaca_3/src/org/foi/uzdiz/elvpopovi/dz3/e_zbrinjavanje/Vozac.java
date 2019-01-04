/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz3.j_podrska.IdGenerator;

/**
 *
 * @author elvis
 */
public class Vozac
{
    private final int id;
    private String ime;
    private byte godisnjiBolovanje;
    private VoziloSucelje pridruzenoVozilo;
    public Vozac(String ime)
    {
        IdGenerator idGen = IdGenerator.getInstance();
        this.ime = ime;
        this.id = idGen.dajId();
    }
    public int DajId()
    {
        return id;
    }
    public String DajIme()
    {
        return ime;
    }
    public void PridruziVozilo(VoziloSucelje vozilo)
    {
        pridruzenoVozilo = vozilo;
    }
    public VoziloSucelje DajPridruzenoVozilo()
    {
        return pridruzenoVozilo;
    }
    public void UkloniPridruzenoVozilo()
    {
        pridruzenoVozilo = null;
    }
    public void PostaviGodisnji()
    {
        godisnjiBolovanje = 1;
        nadomjestiTrenutnogVozaca();
    }
    public void PostaviBolovanje()
    {
        godisnjiBolovanje = 2;
        nadomjestiTrenutnogVozaca();
    }
    private void nadomjestiTrenutnogVozaca()
    {
        if(pridruzenoVozilo!=null && pridruzenoVozilo.DajTrenutnogVozaca()!=null)
        {
            if(pridruzenoVozilo.DajTrenutnogVozaca().DajId()==this.id)
                pridruzenoVozilo.UkloniTrenutnogVozaca();
        }
    }
    
    public void PostaviRadno()
    {
        godisnjiBolovanje = 0;
    }
    public boolean JeLiGodisnji()
    {
        if(godisnjiBolovanje == 1)
            return true;
        else
            return false;
    }
    public boolean JeLiBolovanje()
    {
        if(godisnjiBolovanje == 2)
            return true;
        else
            return false;
    }
}
