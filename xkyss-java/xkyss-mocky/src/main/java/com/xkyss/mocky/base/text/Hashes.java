package com.xkyss.mocky.base.text;

import com.xkyss.mocky.abstraction.MockUnit;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Hashes implements MockUnit<String> {

    private static final Integer HASHED_STRING_SIZE = 128;

    private final Strings strings;
    private final Random random;
    private final List<MockUnit<String>> suppliers;

    public Hashes(Random random, Strings strings) {
        this.random = random;
        this.strings = strings;
        this.suppliers = Arrays.asList(md2(), md5(), sha1(), sha256(), sha384(), sha512());
    }


    @Override
    public String get() {
        int idx = random.nextInt(suppliers.size());
        return suppliers.get(idx).get();
    }

    private MockUnit<String> supplier(UnaryOperator<String> digester) {
        return strings.size(HASHED_STRING_SIZE).map(digester);
    }

    public MockUnit<String> md2() {
        return supplier(DigestUtils::md2Hex);
    }

    public MockUnit<String> md5() {
        return supplier(DigestUtils::md5Hex);
    }

    public MockUnit<String> sha1() {
        return supplier(DigestUtils::sha1Hex);
    }

    public MockUnit<String> sha256() {
        return supplier(DigestUtils::sha256Hex);
    }

    public MockUnit<String> sha384() {
        return supplier(DigestUtils::sha384Hex);
    }

    public MockUnit<String> sha512() {
        return supplier(DigestUtils::sha512Hex);
    }
}
