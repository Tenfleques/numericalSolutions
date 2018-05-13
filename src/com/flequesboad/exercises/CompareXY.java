package com.flequesboad.exercises;

import java.util.Comparator;

public class CompareXY implements Comparator<XY> {
    @Override
    public int compare(XY e1, XY e2) {
        return e1.getY().compareTo(e2.getY());
    }
}

