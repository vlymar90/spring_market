package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Users;
import com.lymar.gb.my_market.service.UserService;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;
import java.util.List;

@Route("users")
public class UsersView extends VerticalLayout {
    private Grid<Users> usersGrid = new Grid<>(Users.class);
    private final UserService userService;
    private TextField phone;
    private List<Users> usersList;

    UsersView(UserService userService) {
       this.userService = userService;
       this.phone = new TextField("Введите номер телефона");
       usersList = userService.findUsers(phone.getValue());
       initPage();

    }

    private void initPage() {
        removeAll();
        initUserGrid();
        add(search(), usersGrid);
    }

    private void initUserGrid() {
        if(usersList != null) {
            usersGrid.setItems(usersList);
            usersGrid.setColumns("name", "last_name", "second_name", "phone", "login", "email");
            usersGrid.setSelectionMode(Grid.SelectionMode.MULTI);
            ListDataProvider<Users> dataProvider = DataProvider.ofCollection(usersList);
            usersGrid.setDataProvider(dataProvider);
            actionsUser();
        }
    }

    private void actionsUser() {

        usersGrid.addColumn(new ComponentRenderer<>(items -> {
//            Button removeButton = new Button("Удалить", i -> {
//                ComponentUtil.setData(UI.getCurrent(), "users", items);
//                Users users = (Users) ComponentUtil.getData(UI.getCurrent(), "users");
//                userService.deleteUser(users.getId());
//                initPage();
//            });

            ComboBox<String> roles = new ComboBox<>();
            roles.setItems(new ArrayList<>(List.of("USER", "ADMIN", "MANAGER")));
            ComponentUtil.setData(UI.getCurrent(), "users", items);
            Users users = (Users) ComponentUtil.getData(UI.getCurrent(), "users");
            roles.setValue(users.getRole());

            Button changeRole = new Button("назначить", i -> {
                ComponentUtil.setData(UI.getCurrent(), "users", items);
                Users user = (Users) ComponentUtil.getData(UI.getCurrent(), "users");
                Users userDB = userService.findById(user.getId());
                userDB.setRole(roles.getValue());
                userService.updateUser(userDB);
            });
            HorizontalLayout horizontalLayout = new HorizontalLayout();
            horizontalLayout.add(changeRole, roles);
            return new HorizontalLayout(horizontalLayout);
        }));
    }

    private HorizontalLayout search() {
        Button button = new Button("поиск", items -> {
            usersList = userService.findUsers(phone.getValue());
            initPage();
        });
         return new HorizontalLayout(phone, button);
    }

    private HorizontalLayout navigation() {
        Button main = new Button("Главная", i -> {
            UI.getCurrent().navigate("main");
        });
        Button review = new Button("Отзывы", i -> {
            UI.getCurrent().navigate("review");
        });
        return new HorizontalLayout(main, review);
    }
}
