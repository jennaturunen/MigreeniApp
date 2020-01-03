package com.example.migreeni;

/**
 * Uusi_merkinta class defines the object which is used to save the migraine note
 */
public class Uusi_merkinta {


    private String paivamaara;
    private String aika;
    private String laake;
    private String kipu;
    private String lisatiedot;

    /**
     * Uusi_merkinta constructor sets the values of new migraine entry
     * @param pvm
     * @param aika
     * @param laake
     * @param kipu
     * @param lisatiedot
     */
    public Uusi_merkinta(String pvm, String aika, String laake, String kipu, String lisatiedot) {
        this.paivamaara = pvm;
        this.aika = aika;
        this.laake = laake;
        this.kipu = kipu;
        this.lisatiedot = lisatiedot;
    }

    public String getPaivamaara(){
        return this.paivamaara;
    }
    public String getAika(){
        return this.aika;
    }
    public String getLaake(){
        return this.laake;
    }
    public String getLisatiedot(){
        return this.lisatiedot;
    }
    public String getKipu(){
        return this.kipu;
    }

    public String toString() {
        return this.paivamaara;
    }
}
