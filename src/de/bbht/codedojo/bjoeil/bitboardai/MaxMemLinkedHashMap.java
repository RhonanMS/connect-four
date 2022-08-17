package de.bbht.codedojo.bjoeil.bitboardai;

import java.util.LinkedHashMap;
import java.util.Map;

public class MaxMemLinkedHashMap<K,V> extends LinkedHashMap<K,V> {

    private int maxSizeEntries;

    public MaxMemLinkedHashMap(int maxSizeEntries) {
        super();
        this.maxSizeEntries = maxSizeEntries;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() >= maxSizeEntries;
    }
}
