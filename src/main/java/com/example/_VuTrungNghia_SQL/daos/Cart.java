package com.example._VuTrungNghia_SQL.daos;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Data
public class Cart {
    private List<Item> cartItems = new ArrayList<>();
    public void addItems(Item item) {
        boolean isExist = cartItems.stream()
                .filter(i -> Objects.equals(i.getBookId(),
                        item.getBookId()))
                .findFirst()
                .map(i -> {
                    i.setQuantity(i.getQuantity() +
                            item.getQuantity());
                    return true;
                })
                .orElse(false);
        if (!isExist) {
            cartItems.add(item);
        }
    }
    public void removeItems(Long bookId) {
        cartItems.removeIf(item -> Objects.equals(item.getBookId(),
                bookId));
    }
    public void updateItems(Long bookId, int quantity) {
        cartItems.stream()
                .filter(item -> Objects.equals(item
                        .getBookId(), bookId))
                .forEach(item -> item.setQuantity(quantity));
    }
}
