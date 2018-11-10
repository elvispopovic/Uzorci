/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdiz.elvpopovi.dz1.d_zbrinjavanje;

import org.foi.uzdiz.elvpopovi.dz1.a_glavna.InicijalizacijaPodatakaProduct;

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
                max[0] = (float)podaci.getParametri().DajVrijednost("maliStaklo");
                max[1] = (float)podaci.getParametri().DajVrijednost("srednjiStaklo");
                max[2] = (float)podaci.getParametri().DajVrijednost("velikiStaklo");
                break;
            case 1: 
                max[0] = (float)podaci.getParametri().DajVrijednost("maliPapir");
                max[1] = (float)podaci.getParametri().DajVrijednost("srednjiPapir");
                max[2] = (float)podaci.getParametri().DajVrijednost("velikiPapir");
                break;
            case 2: 
                max[0] = (float)podaci.getParametri().DajVrijednost("maliMetal");
                max[1] = (float)podaci.getParametri().DajVrijednost("srednjiMetal");
                max[2] = (float)podaci.getParametri().DajVrijednost("velikiMetal");
                break;
            case 3:
                max[0] = (float)podaci.getParametri().DajVrijednost("maliBio");
                max[1] = (float)podaci.getParametri().DajVrijednost("srednjiBio");
                max[2] = (float)podaci.getParametri().DajVrijednost("velikiBio");
                break;
            default: 
                max[0] = (float)podaci.getParametri().DajVrijednost("maliMješano");
                max[1] = (float)podaci.getParametri().DajVrijednost("srednjiMješano");
                max[2] = (float)podaci.getParametri().DajVrijednost("velikiMješano");
        }
        min[0] = (float)podaci.getParametri().DajVrijednost("maliMin")/100f*max[0];
        min[1] = (float)podaci.getParametri().DajVrijednost("srednjiMin")/100f*max[1];
        min[2] = (float)podaci.getParametri().DajVrijednost("velikiMin")/100f*max[2];
    }

    public float[] getMin() 
    {
        return min;
    }

    public float[] getMax() 
    {
        return max;
    }
    private float[] min;
    private float[] max;
}
