package com.xkyss.mocky.base.text;

import com.xkyss.mocky.abstraction.MockUnit;
import com.xkyss.mocky.base.objects.Froms;

import java.util.Random;
import java.util.stream.Collectors;

import static com.xkyss.mocky.contant.Alphabets.HEXA_STR;
import static com.xkyss.mocky.contant.Alphabets.SPECIAL_CHARACTERS_STR;
import static com.xkyss.mocky.contant.MockConsts.INPUT_PARAMETER_NOT_NULL;
import static com.xkyss.mocky.contant.MockConsts.SIZE_BIGGER_THAN_ZERO_STRICT;
import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;

public class Strings implements MockUnit<String> {

    private final Random random;
    private final MockUnit<Integer> sizeUnit;

    public Strings(Random random) {
        this(random, () -> random.nextInt(64));
    }

    protected Strings(Random random, int size) {
        this(random, () -> size);
    }

    protected Strings(Random random, MockUnit<Integer> sizeUnit) {
        this.random = random;
        this.sizeUnit = sizeUnit;
    }

    @Override
    public String get() {
        return random(sizeUnit.get(), 0, 0, true, true, null, random);
    }

    protected int getSize() {
        return sizeUnit.get();
    }

    public Strings size(int size) {
        isTrue(size>0, SIZE_BIGGER_THAN_ZERO_STRICT);
        return new Strings(random, size);
    }

    public Strings size(MockUnit<Integer> sizeUnit) {
        notNull(sizeUnit, INPUT_PARAMETER_NOT_NULL, "sizeUnit");
        return new Strings(random, sizeUnit);
    }

    public MockUnit<String> numbers() {
        return () -> random(getSize(), 0, 0, false, true, null, random);
    }

    public MockUnit<String> letters() {
        return () -> random(getSize(), 0, 0, true, false, null, random);
    }


    public MockUnit<String> alphaNumeric() {
        return () -> random(getSize(), 0, 0, true, true, null, random);
    }

    public MockUnit<String> hex() {
        return () -> new Froms(random).from(HEXA_STR)
            .stream()
            .limit(getSize())
            .collect(Collectors.joining());
    }

    public MockUnit<String> specialChars() {
        return () -> new Froms(random).from(SPECIAL_CHARACTERS_STR)
            .stream()
            .limit(getSize())
            .collect(Collectors.joining());
    }
}
