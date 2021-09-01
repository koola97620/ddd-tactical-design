package kitchenpos.products.tobe.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price implements Comparable {
    @Column(name = "price", nullable = false)
    private BigDecimal _price;

    protected Price() {
    }

    public Price(final BigDecimal price) {
        validate(price);
        this._price = price;
    }

    private void validate(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 필수고, 0 이상이어야 합니다");
        }
    }

    public BigDecimal offer() {
        return _price;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (Objects.isNull(o) || getClass() != o.getClass()) {
            return false;
        }

        final Price price = (Price) o;
        return _price.equals(price._price);
    }

    @Override
    public int hashCode() {
        return _price.hashCode();
    }

    @Override
    public int compareTo(final Object o) {
        final Price price = (Price) o;
        return _price.compareTo(price._price);
    }
}
