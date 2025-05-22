package com.xkyss.mocky.base.time;


import com.xkyss.mocky.abstraction.MockUnit;

import java.time.LocalTime;
import java.util.Random;

import static com.xkyss.core.util.Validate.notNull;
import static com.xkyss.mocky.contant.MockConsts.LOWER_DATE_SMALLER_THAN_UPPER_DATE;
import static org.apache.commons.lang3.Validate.isTrue;

public class LocalTimes implements MockUnit<LocalTime> {
    private final Random random;

    public LocalTimes(Random random) {
        this.random = random;
    }

    @Override
    public LocalTime get() {
        // 默认生成一天中的任意时间，精确到纳秒
        return LocalTime.of(
            random.nextInt(24), // 随机小时
            random.nextInt(60), // 随机分钟
            random.nextInt(60), // 随机秒
            random.nextInt(1_000_000_000) // 随机纳秒
        );
    }

    public MockUnit<LocalTime> between(LocalTime lowerTime, LocalTime upperTime) {
        notNull(lowerTime, "lowerTime");
        notNull(upperTime, "upperTime");
        isTrue(lowerTime.isBefore(upperTime),
            LOWER_DATE_SMALLER_THAN_UPPER_DATE,
            "lower", lowerTime,
            "upper", upperTime);

        return () -> {
            // 计算时间范围内的总纳秒数
            long startNano = lowerTime.toNanoOfDay();
            long endNano = upperTime.toNanoOfDay();
            long randomNano = startNano + random.nextInt((int) (endNano - startNano + 1));

            // 将随机纳秒数转换为 LocalTime
            return LocalTime.ofNanoOfDay(randomNano);
        };
    }

    public MockUnit<LocalTime> thisHour() {
        return () -> {
            // 获取当前小时的起始时间
            LocalTime now = LocalTime.now();
            int currentHour = now.getHour();
            return LocalTime.of(
                currentHour, // 当前小时
                random.nextInt(60), // 随机分钟
                random.nextInt(60), // 随机秒
                random.nextInt(1_000_000_000) // 随机纳秒
            );
        };
    }

    public MockUnit<LocalTime> thisMinute() {
        return () -> {
            // 获取当前分钟的起始时间
            LocalTime now = LocalTime.now();
            int currentHour = now.getHour();
            int currentMinute = now.getMinute();
            return LocalTime.of(
                currentHour, // 当前小时
                currentMinute, // 当前分钟
                random.nextInt(60), // 随机秒
                random.nextInt(1_000_000_000) // 随机纳秒
            );
        };
    }
}