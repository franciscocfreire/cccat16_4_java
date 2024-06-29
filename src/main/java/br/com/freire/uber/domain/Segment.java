package br.com.freire.uber.domain;

import lombok.Getter;

@Getter
public class Segment {

    private final Coord to;
    private final Coord from;

    public Segment(Coord to, Coord from){
        this.to = to;
        this.from = from;
    }
}
