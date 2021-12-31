package com.lk.sql.parser.type.inference;

import com.lk.sql.parser.utils.Preconditions;

import java.util.Objects;
import java.util.Optional;

/**
 * @author lukai
 * @creare 2021-12-31 14:57
 */
public final class ConstantArgumentCount implements ArgumentCount {

    private static final int OPEN_INTERVAL = -1;

    private final int minCount;

    private final int maxCount;

    private ConstantArgumentCount(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public static ConstantArgumentCount of(int count) {
        Preconditions.checkArgument(count >= 0);
        return new ConstantArgumentCount(count, count);
    }

    public static ConstantArgumentCount between(int minCount, int maxCount) {
        Preconditions.checkArgument(minCount <= maxCount);
        Preconditions.checkArgument(minCount >= 0);
        return new ConstantArgumentCount(minCount, maxCount);
    }

    public static ConstantArgumentCount from(int minCount) {
        Preconditions.checkArgument(minCount >= 0);
        return new ConstantArgumentCount(minCount, OPEN_INTERVAL);
    }

    public static ConstantArgumentCount to(int maxCount) {
        Preconditions.checkArgument(maxCount >= 0);
        return new ConstantArgumentCount(0, maxCount);
    }

    public static ConstantArgumentCount any() {
        return new ConstantArgumentCount(0, OPEN_INTERVAL);
    }

    @Override
    public boolean isValidCount(int count) {
        return count >= minCount && (maxCount == OPEN_INTERVAL || count <= maxCount);
    }

    @Override
    public Optional<Integer> getMinCount() {
        return Optional.of(minCount);
    }

    @Override
    public Optional<Integer> getMaxCount() {
        if (maxCount == OPEN_INTERVAL) {
            return Optional.empty();
        }
        return Optional.of(maxCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConstantArgumentCount that = (ConstantArgumentCount) o;
        return minCount == that.minCount && maxCount == that.maxCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minCount, maxCount);
    }
}

