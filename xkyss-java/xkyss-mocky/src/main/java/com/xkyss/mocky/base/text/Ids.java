package com.xkyss.mocky.base.text;

import com.xkyss.mocky.abstraction.MockUnit;

import java.util.UUID;

public class Ids implements MockUnit<String> {

    @Override
    public String get() {
        MockUnit<String> mu = uuid();
        return mu.get();
    }

    public MockUnit<String> uuid() {
        return () -> UUID.randomUUID().toString();
    }
}
