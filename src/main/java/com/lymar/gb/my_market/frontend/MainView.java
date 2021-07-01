package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.entity.filter.ProductFilter;
import com.lymar.gb.my_market.repository.ProductRepository;
import com.lymar.gb.my_market.service.CartService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route("main")
public class MainView extends VerticalLayout  {
    private final Grid<Product> grid = new Grid<>(Product.class);
    private ProductRepository productRepository;
    private CartService cartService;
    private Map<String, String> filterMap;

    public MainView(ProductRepository productRepository,
                    CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
        filterMap = new HashMap<>();
        initPage();
    }

    private void initPage() {
        removeAll();
        initProductGrid();
        add(grid, addFormFilter(), groupButton());
    }

    private void initProductGrid() {
        List<Product> products = productRepository.findAll(new ProductFilter(filterMap).getSpecification());
        grid.setItems(products);
        grid.setColumns("name", "price", "count");
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

    private FormLayout addFormFilter() {
        FormLayout nameLayout = new FormLayout();
        TextField minPrice = new TextField("minPrice");
        TextField maxPrice = new TextField("maxPrice");
        TextField name = new TextField("name");
        Button setFilter = new Button("Показать", item -> {
            filterMap.put(minPrice.getLabel(), minPrice.getValue());
            filterMap.put(maxPrice.getLabel(), maxPrice.getValue());
            filterMap.put(name.getLabel(), name.getValue());
            initPage();
        });

        nameLayout.add(minPrice, maxPrice, name, setFilter);
        nameLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px", 1),
                new FormLayout.ResponsiveStep("600px", 2),
                new FormLayout.ResponsiveStep("700px", 4));
        return nameLayout;
    }
}
