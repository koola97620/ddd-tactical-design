package kitchenpos.menus.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;

import java.util.*;
import java.util.stream.Collectors;

public class NewInMemoryMenuRepository implements MenuRepository {
    private final Map<UUID, Menu> menus = new HashMap<>();

    @Override
    public Menu save(final Menu menu) {
        menus.put(menu.getId(), menu);
        return menu;
    }

    @Override
    public Optional<Menu> findById(final UUID id) {
        return Optional.ofNullable(menus.get(id));
    }

    @Override
    public List<Menu> findAll() {
        return new ArrayList<>(menus.values());
    }

    @Override
    public List<Menu> findAllByIdIn(final List<UUID> ids) {
        return menus.values()
            .stream()
            .filter(menu -> ids.contains(menu.getId()))
            .collect(Collectors.toList());
    }

    @Override
    public List<Menu> findAllByProductId(final UUID productId) {
        return menus.values()
            .stream()
            .filter(menu -> menu.getMenuProductList().stream().anyMatch(menuProduct -> menuProduct.getNewProduct().getId().equals(productId)))
            .collect(Collectors.toList());
    }
}
