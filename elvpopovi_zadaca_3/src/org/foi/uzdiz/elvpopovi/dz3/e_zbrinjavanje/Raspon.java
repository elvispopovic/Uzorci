/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz3.e_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz3.b_buideri.InicijalizacijaPodatakaProduct;

/**
 *
 * @author elvis
 */
public class Raspon
{
   
    public Raspon(InicijalizacijaPodatakaProduct podaci, int tip) 
    {
        if(podaci==null)
            return;
        min = new float[3]; 
        max = new float[3];
        switch(tip)
        {
            case 0: 
                max[0] = (float)podaci.dajParametre().DajVrijednost("maliStaklo");
                max[1] = (float)podaci.dajParametre().DajVrijednost("srednjiStaklo");
                max[2] = (float)podaci.dajParametre().DajVrijednost("velikiStaklo");
                break;
            case 1: 
                max[0] = (float)podaci.dajParametre().DajVrijednost("maliPapir");
                max[1] = (float)podaci.dajParametre().DajVrijednost("srednjiPapir");
                max[2] = (float)podaci.dajParametre().DajVrijednost("velikiPapir");
                break;
            case 2: 
                max[0] = (float)podaci.dajParametre().DajVrijednost("maliMetal");
                max[1] = (float)podaci.dajParametre().DajVrijednost("srednjiMetal");
                max[2] = (float)podaci.dajParametre().DajVrijednost("velikiMetal");
                break;
            case 3:
                max[0] = (float)podaci.dajParametre().DajVrijednost("maliBio");
                max[1] = (float)podaci.dajParametre().DajVrijednost("srednjiBio");
                max[2] = (float)podaci.dajParametre().DajVrijednost("velikiBio");
                break;
            default: 
                max[0] = (float)podaci.dajParametre().DajVrijednost("maliMješano");
                max[1] = (float)podaci.dajParametre().DajVrijednost("srednjiMješano");
                max[2] = (float)podaci.dajParametre().DajVrijednost("velikiMješano");
        }
        min[0] = (float)podaci.dajParametre().DajVrijednost("maliMin")/100f*max[0];
        min[1] = (float)podaci.dajParametre().DajVrijednost("srednjiMin")/100f*max[1];
        min[2] = (float)podaci.dajParametre().DajVrijednost("velikiMin")/100f*max[2];
    }

    public float[] dajMinimum() 
    {
        return min;
    }

    public float[] dajMaksimum() 
    {
        return max;
    }
    private float[] min;
    private float[] max;
}
