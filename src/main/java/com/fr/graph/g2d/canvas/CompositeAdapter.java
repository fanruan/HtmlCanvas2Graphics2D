package com.fr.graph.g2d.canvas;

import java.awt.AlphaComposite;

public enum CompositeAdapter {
    SRC_OVER("source-over", AlphaComposite.SRC_OVER),
    SRC_ATOP("source-atop", AlphaComposite.SRC_ATOP),
    DST_OVER("destination-over", AlphaComposite.DST_OVER),
    DST_OUT("destination-out", AlphaComposite.DST_OUT),
    XOR("xor", AlphaComposite.XOR);

    private String type;

    private int composite;

    CompositeAdapter(String type, int composite){
        this.type = type;
        this.composite = composite;
    }

    public String getType() {
        return this.type;
    }

    private static CompositeAdapter[] types;

    public static int parse(String type){
        if(types == null){
            types = CompositeAdapter.values();
        }

        for(CompositeAdapter composite : types){
            if(composite.getType().equals(type)){
                return composite.composite;
            }
        }

        return ContextAdapter.MISMATCH;
    }

    public static String valueOf(int composite){
        if(types == null){
            types = CompositeAdapter.values();
        }

        for(CompositeAdapter compositeAdapter : types){
            if(compositeAdapter.composite == composite){
                return compositeAdapter.type;
            }
        }
        return "source-over";
    }
}
