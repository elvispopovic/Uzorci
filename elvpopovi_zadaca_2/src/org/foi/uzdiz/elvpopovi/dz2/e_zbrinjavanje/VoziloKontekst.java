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
public class VoziloKontekst 
{

    public int dajTrenutnuUlicu()
    {
        return trenutnaUlica;
    }

    public int dajTrenutniSpremnik()
    {
        return trenutniSpremnik;
    }
    public float dajPopunjenost()
    {
        return popunjenost;
    }
    public void PovecajTrenutnuUlicu()
    {
        trenutniSpremnik = 0;
        trenutnaUlica++;
    }
    public void PovecajTrenutniSpremnik()
    {
        trenutniSpremnik++;
    }

    public boolean postaviPotrebnoTrazenje()
    {
        return potrebnoPraznjenje;
    }

    public void Isprazni()
    {
        this.potrebnoPraznjenje = false;
        this.popunjenost = (float)0.0;
    }
    public void PovecajPopunjenost(float dopuna)
    {
        this.popunjenost+=dopuna;
    }
    
    public void SetPotrebnoPraznjenje()
    {
        this.potrebnoPraznjenje = true;
    }
        
    public void ResetAll()
    {
        brojacOdvoza=0;
        trenutnaUlica = 0;
        trenutniSpremnik = 0;
        potrebnoPraznjenje = false;
        popunjenost = 0.0f;
    }
    public int dajBrojacOdvoza()
    {
        return brojacOdvoza;
    }
    public void postaviBrojacOdvoza(int brojOdvoza)
    {
        this.brojacOdvoza = brojOdvoza;
    }
    public void SmanjiBrojacOdvoza()
    {
        this.brojacOdvoza--;
    }
    private int trenutnaUlica, trenutniSpremnik;
    private boolean potrebnoPraznjenje;
    private float popunjenost;
    private int brojacOdvoza;
    public VoziloKontekst()
    {
        ResetAll();
    }
    
}
