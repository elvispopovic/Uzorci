/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

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
    public int dajBrojPunjenjaPogona()
    {
        return brojPunjenjaPogona;
    }
    public void PovecajBrojPunjenjaPogona()
    {
        this.brojPunjenjaPogona++;
    }

    public float dajUkupnuKolicinuOtpada()
    {
        return ukupnaKolicinaOtpada;
    }
    public void PovecajUkupnuKolicinuOtpada(float novaKolicina)
    {
        this.ukupnaKolicinaOtpada+=novaKolicina;
    }
    
    
    private int brojSpremnika, brojMjesta, brojOdlazakaNaDeponij, brojPunjenjaPogona;
    private float ukupnaKolicinaOtpada;
    
    
    public void ResetAll()
    {
        brojSpremnika=0;
        brojMjesta=0;
        brojOdlazakaNaDeponij = 0;
        brojPunjenjaPogona = 0;
        ukupnaKolicinaOtpada=(float) 0.0;
    }
    public VoziloStatistika()
    {
        ResetAll();
    }
}
