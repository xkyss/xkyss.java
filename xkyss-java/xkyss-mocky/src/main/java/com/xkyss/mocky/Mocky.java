package com.xkyss.mocky;

import com.xkyss.mocky.base.objects.Filler;
import com.xkyss.mocky.base.objects.Froms;
import com.xkyss.mocky.base.seq.IntSeq;
import com.xkyss.mocky.base.seq.LongSeq;
import com.xkyss.mocky.base.seq.Seq;
import com.xkyss.mocky.base.text.*;
import com.xkyss.mocky.base.time.LocalDateTimes;
import com.xkyss.mocky.base.time.LocalDates;
import com.xkyss.mocky.base.time.LocalTimes;
import com.xkyss.mocky.base.types.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class Mocky {
    private final Random random;

    private final Froms froms;

    private final Bools bools;
    private final Chars chars;
    private final Ints ints;
    private final Longs longs;
    private final Doubles doubles;
    private final Floats floats;
    private final Primes primes;

    private final Dicts dicts;
    private final Hashes hashes;
    private final Ids ids;

    private final LocalDates localDates;
    private final LocalTimes localTimes;
    private final LocalDateTimes localDateTimes;

    public Mocky() {
        this(ThreadLocalRandom.current());
    }

    public Mocky(Random random) {
        this.random = random;

        this.froms = new Froms(random);

        this.ints = new Ints(random);
        this.longs = new Longs(random);
        this.doubles = new Doubles(random);
        this.floats = new Floats(random);
        this.bools = new Bools(random, doubles);
        this.chars = new Chars(random, froms);
        this.primes = new Primes(froms);

        this.dicts = new Dicts(random);
        this.hashes = new Hashes(random, new Strings(random));
        this.ids = new Ids();

        this.localDates = new LocalDates(ints, longs);
        this.localTimes = new LocalTimes(random);
        this.localDateTimes = new LocalDateTimes(localDates, localTimes);
    }

    public Froms froms() {
        return froms;
    }

    public <T> Filler<T> filler(Supplier<T> supplier) {
        return new Filler<>(supplier);
    }

    public Ints ints() {
        return ints;
    }

    public Longs longs() {
        return longs;
    }

    public Doubles doubles() {
        return doubles;
    }

    public Floats floats() {
        return floats;
    }

    public Bools bools() {
        return bools;
    }

    public Chars chars() {
        return chars;
    }

    public Primes primes() {
        return primes;
    }

    public IntSeq intSeq() {
        return new IntSeq();
    }

    public LongSeq longSeq() {
        return new LongSeq();
    }

    public <T> Seq<T> seq(Iterable<T> iterable) {
        return Seq.fromIterable(iterable);
    }

    public <T> Seq<T> seq(T[] array) {
        return Seq.fromArray(array);
    }

    public Strings strings() {
        return new Strings(random);
    }

    public Dicts dicts() {
        return dicts;
    }

    public Hashes hashes() {
        return hashes;
    }

    public Ids ids() {
        return ids;
    }

    public Regex regex(String exp) {
        return new Regex(exp);
    }

    public LocalDates localDates() {
        return localDates;
    }

    public LocalTimes localTimes() {
        return localTimes;
    }

    public LocalDateTimes localDateTimes() {
        return localDateTimes;
    }
}
