package kitchenpos.menus.domain;

import kitchenpos.common.domain.Price;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.menus.exception.MenuProductExceptionMessage.ILLEGAL_QUANTITY;

@Table(name = "menu_product")
@Entity
public class MenuProduct {
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long seq;

    @ManyToOne
    @JoinColumn(
            name = "product_id",// 에러발생해서 주석처리 Table [menu_product] contains physical column name [product_id] referred to by multiple logical column names: [product_id], [productId]
            columnDefinition = "binary(16)",
            foreignKey = @ForeignKey(name = "fk_menu_product_to_product")
    )
    private NewProduct newProduct;

    @Column(name = "quantity", nullable = false)
    private long quantity;

    public MenuProduct() {
    }

    private MenuProduct(NewProduct newProduct, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ILLEGAL_QUANTITY);
        }
        this.newProduct = newProduct;
        this.quantity = quantity;
    }

    private MenuProduct(Long seq, NewProduct newProduct, long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(ILLEGAL_QUANTITY);
        }
        this.seq = seq;
        this.newProduct = newProduct;
        this.quantity = quantity;
    }

    public static MenuProduct create(NewProduct product, long quantity) {
        return new MenuProduct(product, quantity);
    }

    public static MenuProduct create(Long seq, NewProduct product, long quantity) {
        return new MenuProduct(seq, product, quantity);
    }

    public Price calculateTotalPrice() {
        return newProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    public NewProduct getNewProduct() {
        return newProduct;
    }

    public long getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuProduct that = (MenuProduct) o;
        return Objects.equals(seq, that.seq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq);
    }

    public UUID getProductId() {
        return newProduct.getId();
    }
}
