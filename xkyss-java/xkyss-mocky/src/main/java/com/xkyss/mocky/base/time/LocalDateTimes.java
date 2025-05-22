package com.xkyss.mocky.base.time;

import com.xkyss.mocky.abstraction.MockUnit;

import java.time.LocalDateTime;

public class LocalDateTimes implements MockUnit<LocalDateTime> {

    private final LocalDates localDates;
    private final LocalTimes localTimes;

    public LocalDateTimes(LocalDates localDates, LocalTimes localTimes) {
        this.localDates = localDates;
        this.localTimes = localTimes;
    }

    @Override
    public LocalDateTime get() {
        return LocalDateTime.of(localDates.get(), localTimes.get());
    }
}
