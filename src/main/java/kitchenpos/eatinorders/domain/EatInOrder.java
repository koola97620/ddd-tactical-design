package kitchenpos.eatinorders.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static kitchenpos.eatinorders.exception.OrderExceptionMessage.*;

@Table(name = "orders")
@Entity
public class EatInOrder {
    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    private UUID id;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    private LocalDateTime orderDateTime;

    @Embedded
    private EatInOrderLineItems orderLineItems;

    @Column(name = "order_table_id", columnDefinition = "binary(16)")
    private UUID orderTableId;

    public EatInOrder() {
    }

    public EatInOrder(UUID id, OrderType orderType, OrderStatus orderStatus, LocalDateTime orderDateTime, EatInOrderLineItems eatInOrderLineItems, UUID orderTableId) {
        this.id = id;
        this.type = orderType;
        this.status = orderStatus;
        this.orderDateTime = orderDateTime;
        this.orderLineItems = eatInOrderLineItems;
        this.orderTableId = orderTableId;
    }

    public static EatInOrder create(UUID id, OrderStatus orderStatus, LocalDateTime orderDateTime, EatInOrderLineItems eatInOrderLineItems, UUID orderTable) {
        return new EatInOrder(id, OrderType.EAT_IN, orderStatus, orderDateTime, eatInOrderLineItems, orderTable);
    }

    public void accept() {
        if (this.status != OrderStatus.WAITING) {
            throw new IllegalStateException(ORDER_STATUS_NOT_WAITING);
        }
        this.status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (this.status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException(ORDER_STATUS_NOT_ACCEPTED);
        }
        this.status = OrderStatus.SERVED;
    }

    public void complete() {
        if (this.status != OrderStatus.SERVED) {
            throw new IllegalStateException(ORDER_STATUS_NOT_SERVED);
        }
        this.status = OrderStatus.COMPLETED;
    }

    public UUID getId() {
        return id;
    }

    public OrderType getType() {
        return type;
    }


    public OrderStatus getStatus() {
        return status;
    }


    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }


    public UUID getOrderTableId() {
        return orderTableId;
    }

    public EatInOrderLineItems getOrderLineItems() {
        return orderLineItems;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EatInOrder that = (EatInOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
