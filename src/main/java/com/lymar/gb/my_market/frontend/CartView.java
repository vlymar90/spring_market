package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.service.CartService;
import com.lymar.gb.my_market.service.MailService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

@Route("cart")
public class CartView extends VerticalLayout {
    private final Grid<Product> grid = new Grid<>(Product.class);
    private final CartService cartService;
    private final MailService mailService;

    public CartView(CartService cartService,
                    MailService mailService) {
        this.cartService = cartService;
        this.mailService = mailService;
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

        grid.addColumn(new ComponentRenderer<>(item -> {
            var plusButton = new Button("+", i -> {
                cartService.increaseProductCount(item);
                grid.getDataProvider().refreshItem(item);
            });

            var minusButton = new Button("-", i -> {
                cartService.decreaseProductCount(item);
                grid.getDataProvider().refreshItem(item);
            });

            var deleteButton = new Button("Удалить", items -> {
                for (Product p : cartService.getProducts()) {
                    cartService.deleteProduct(p);
                }
            });

            return new HorizontalLayout(plusButton, minusButton, deleteButton);
        }));
    }

    private HorizontalLayout groupButton() {
        HorizontalLayout hl = new HorizontalLayout();
        hl.setPadding(true);
        Button linkMain = new Button("На главную", items -> {
            UI.getCurrent().navigate("main");
        });

        Button buy = new Button("Купить", items -> {
           cartService.makeOrder();
           mailService.sendSimpleEmail("7ua.lymar2014@gmail.com",
                   "your order in spring shop",
                   Product.converterListToString(cartService.getProducts()).toString());
        });

        hl.add(linkMain, buy);
        return hl;
    }
}
