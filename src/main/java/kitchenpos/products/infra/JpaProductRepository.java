package kitchenpos.products.infra;

import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends ProductRepository, JpaRepository<Product, UUID> {
}
