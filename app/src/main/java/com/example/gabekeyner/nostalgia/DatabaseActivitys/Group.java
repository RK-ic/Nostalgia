package com.example.gabekeyner.nostalgia.DatabaseActivitys;

/**
 * Created by GabeKeyner on 10/12/2016.
 */

public class Group {

    public String groupTitle;
    public int numPeople;

    @Override
    public String toString() {
        return "Group{" +
                "groupTitle='" + groupTitle + '\'' +
                ", numPeople=" + numPeople +
                '}';
    }
}
