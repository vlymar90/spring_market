package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.repository.ProductRepository;
import com.lymar.gb.my_market.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("main")
public class MainView extends VerticalLayout  {
    private final Grid<Product> grid = new Grid<>(Product.class);
    private ProductRepository productRepository;
    private CartService cartService;

    public MainView(ProductRepository productRepository,
                    CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
        initPage();
    }

    private void initPage() {
        initProductGrid();
        add(grid, groupButton());
    }

    private void initProductGrid() {
        List<Product> products = productRepository.findAll();
        grid.setItems(products);
        grid.setColumns("name", "count");
        grid.setSizeUndefined();
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        ListDataProvider<Product> dataProvider = DataProvider.ofCollection(products);
        grid.setDataProvider(dataProvider);
    }

    private HorizontalLayout groupButton() {
        HorizontalLayout hl = new HorizontalLayout();
        Button linkCart =  new Button("корзина", item -> {
            UI.getCurrent().navigate("cart");
        });

        Button addCart = new Button("Добавить в корзину", item -> {
            cartService.addProduct(grid.getSelectedItems());
            Notification.show("Товар успешно добавлен");
        });
        hl.add(linkCart, addCart);
        return hl;
    }
}
