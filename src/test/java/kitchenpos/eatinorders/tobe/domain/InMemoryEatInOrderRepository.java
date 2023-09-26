package kitchenpos.eatinorders.tobe.domain;


import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.domain.EatInOrderTable;
import kitchenpos.eatinorders.domain.OrderStatus;

import java.util.*;

public class InMemoryEatInOrderRepository implements EatInOrderRepository {
    private final Map<UUID, EatInOrder> orders = new HashMap<>();

    @Override
    public EatInOrder save(final EatInOrder order) {
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<EatInOrder> findById(final UUID id) {
        return Optional.ofNullable(orders.get(id));
    }

    @Override
    public List<EatInOrder> findAll() {
        return new ArrayList<>(orders.values());
    }

    @Override
    public boolean existsByOrderTableIdAndStatusNot(final UUID orderTableId, final OrderStatus status) {
        return orders.values()
                .stream()
                .anyMatch(order -> order.getOrderTableId().equals(orderTableId) && order.getStatus() != status);
    }
}
