/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz2.e_zbrinjavanje;

/**
 *
 * @author elvis
 */
public class VoziloStatistika
{

    public int dajBrojSpremnika()
    {
        return brojSpremnika;
    }
    public void PovecajBrojSpremnika()
    {
        this.brojSpremnika++;
    }

    public int dajBrojMjesta()
    {
        return brojMjesta;
    }
    public void PovecajBrojMjesta(int novihMjesta)
    {
        this.brojMjesta+=novihMjesta;
    }

    public int dajBrojOdlazakaNaDeponij()
    {
        return brojOdlazakaNaDeponij;
    }
    public void PovecajBrojOdlazakaNaDeponij()
    {
        this.brojOdlazakaNaDeponij++;
    }

    public float dajUkupnuKolicinuOtpada()
    {
        return ukupnaKolicinaOtpada;
    }
    public void PovecajUkupnuKolicinuOtpada(float novaKolicina)
    {
        this.ukupnaKolicinaOtpada+=novaKolicina;
    }
    
    
    private int brojSpremnika, brojMjesta, brojOdlazakaNaDeponij;
    private float ukupnaKolicinaOtpada;
    
    
    public void ResetAll()
    {
        brojSpremnika=0;
        brojMjesta=0;
        brojOdlazakaNaDeponij = 0;
        ukupnaKolicinaOtpada=(float) 0.0;
    }
    public VoziloStatistika()
    {
        ResetAll();
    }
}
