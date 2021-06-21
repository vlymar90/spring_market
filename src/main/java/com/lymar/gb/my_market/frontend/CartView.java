package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends VerticalLayout {
    private final Grid<Product> grid = new Grid<>(Product.class);
    private final CartService cartService;

    public CartView(CartService cartService) {
        this.cartService = cartService;


        initCartGrid();
        add(grid, groupButton());
    }

    private void initCartGrid() {
        var products = cartService.getProducts();

        grid.setItems(products);
        grid.setColumns("name", "count");
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<Product> dataProvider = DataProvider.ofCollection(products);
        grid.setDataProvider(dataProvider);

//        grid.addColumn(new ComponentRenderer<>(item -> {
//            var plusButton = new Button("+", i -> {
//                cartService.increaseProductCount(item);
//                grid.getDataProvider().refreshItem(item);
//            });
//
//            var minusButton = new Button("-", i -> {
//                cartService.decreaseProductCount(item);
//                grid.getDataProvider().refreshItem(item);
//            });
//
//            return new HorizontalLayout(plusButton, minusButton);
//        }));
    }

    private HorizontalLayout groupButton() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setPadding(true);
        Button linkMain = new Button("На главную", items -> {
            UI.getCurrent().navigate("main");
        });

        Button buy = new Button("Купить", items -> {
           cartService.makeOrder();
        });

        hl.add(linkMain, buy);
        return hl;
    }
}
