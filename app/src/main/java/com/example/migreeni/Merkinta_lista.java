package com.example.migreeni;

import java.util.ArrayList;
import java.util.List;

/**
 * Merkinta_lista class is a singleton which contains our data in a arraylist
 *
 */
class Merkinta_lista {
    private static final Merkinta_lista ourInstance = new Merkinta_lista();

    static Merkinta_lista getInstance() {
        return ourInstance;
    }

    private List<Uusi_merkinta> merkinnat;

    private Merkinta_lista() {
        merkinnat = new ArrayList<>();
    }

    /**
     * getMerkinnat method returns the Arraylist containing Uusi_merkinta objects
     * @return Arraylist<Uusi_merkinta>
     */
    public ArrayList<Uusi_merkinta> getMerkinnat(){
        return (ArrayList<Uusi_merkinta>) merkinnat;
    }

    /**
     * setMerkinnat method is used to replace the Arraylist in singleton
     * @param list
     */
    public void setMerkinnat(ArrayList<Uusi_merkinta> list){
        this.merkinnat = list;
    }
    /**
     * clearMerkinnat method is used to reset the Arraylist in singleton
     *
     */
    public void clearMerkinnat() {this.merkinnat = new ArrayList<>();}

}
