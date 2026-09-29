package com.agacostays.restaurant.service;

import com.agacostays.restaurant.dto.request.*;
import com.agacostays.restaurant.dto.response.MenuItemResponse;
import java.util.List;

public interface MenuService {
    MenuItemResponse create(Long branchId, CreateMenuItemRequest request);
    MenuItemResponse get(Long branchId, Long menuItemId);
    List<MenuItemResponse> list(Long branchId, MenuSearchRequest request);
    MenuItemResponse update(Long menuItemId, UpdateMenuItemRequest request);
    void delete(Long menuItemId);
    MenuItemResponse updateAvailability(Long menuItemId, boolean availability);
}
