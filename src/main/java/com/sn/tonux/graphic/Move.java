package com.sn.tonux.graphic;

/**
 *
 * @author tonux
 */
public class Move {

    private int disc;
    private int origin;
    private int destination;

    public Move(int disc, int origin, int destination) {
        this.disc = disc;
        this.origin = origin;
        this.destination = destination;
    }

    public int getDisc() {
        return disc;
    }

    public void setDisc(int disc) {
        this.disc = disc;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getOrigin() {
        return origin;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }
}
