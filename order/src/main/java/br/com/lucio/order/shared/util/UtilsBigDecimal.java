package br.com.lucio.order.shared.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;

@UtilityClass
public class UtilsBigDecimal {

    public static BigDecimal get(BigDecimal value){
        return get(value, 2);
    }

    public static BigDecimal get(BigDecimal value, int round){
        return Optional.ofNullable(value).orElse(BigDecimal.ZERO).setScale(round, RoundingMode.HALF_UP);
    }

    public static BigDecimal somar(BigDecimal... values) {
        BigDecimal result = BigDecimal.ZERO;
        for (int i = 0; i < values.length; i++) {
            result = result.add(values[i] == null ? BigDecimal.ZERO : values[i]);
        }
        return result;
    }

    public static BigDecimal subtract(BigDecimal value, BigDecimal valueSubtract) {
        value = Objects.isNull(value) ? BigDecimal.ZERO : value;
        valueSubtract = Objects.isNull(valueSubtract) ? BigDecimal.ZERO : valueSubtract;
        return value.subtract(valueSubtract);
    }

    public static BigDecimal parseBigDecimal(Double value, int round) {
        if (Objects.nonNull(value)) {
            return get(BigDecimal.valueOf(value), round);
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal parseBigDecimalNoRound(Double value) {
        if (Objects.nonNull(value)) {
            return BigDecimal.valueOf(value);
        }
        return BigDecimal.ZERO;
    }

    public static boolean equals(BigDecimal value1, BigDecimal value2, int round) {
        return value1.setScale(round, RoundingMode.HALF_UP).equals(value2.setScale(round, RoundingMode.HALF_UP));
    }

    public static Double parseDouble(BigDecimal value) {
        Double valueDouble = 0.0;
        if (Objects.nonNull(value)) {
            valueDouble = value.doubleValue();
        }
        return valueDouble;
    }

}
