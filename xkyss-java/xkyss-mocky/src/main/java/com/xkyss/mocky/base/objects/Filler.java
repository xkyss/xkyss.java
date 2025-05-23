package com.xkyss.mocky.base.objects;

import com.xkyss.mocky.abstraction.MockUnit;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.apache.commons.lang3.Validate.notNull;

public class Filler<T> implements MockUnit<T> {

    private final Supplier<T> supplier;

    @SuppressWarnings("rawtypes")
    private final Map<BiConsumer, Function> setters = new LinkedHashMap<>();

    public Filler(Supplier<T> supplier) {
        notNull(supplier, "supplier is null");
        this.supplier = supplier;
    }

    @Override
    public T get() {
        T o = supplier.get();

        //noinspection unchecked
        setters.forEach((k, v) -> k.accept(o, v.apply(o)));
        return o;
    }

    public <R> Filler<T> setter(BiConsumer<T, R> setter, MockUnit<R> mockUnit) {
        notNull(setter, "setter");
        notNull(mockUnit, "mockUnit");

        setters.put(setter, o -> mockUnit.get());
        return this;
    }

    public <R> Filler<T> setter(BiConsumer<T, R> setter, Function<T, R> function) {
        notNull(setter, "setter");
        notNull(function, "function");

        setters.put(setter, function);
        return this;
    }

    public <R> Filler<T> constant(BiConsumer<T, R> setter, R constant) {
        notNull(setter, "setter");
        notNull(constant, "constant");

        setters.put(setter, o -> constant);
        return this;
    }
}
