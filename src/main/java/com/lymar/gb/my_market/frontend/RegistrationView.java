package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Users;
import com.lymar.gb.my_market.service.UserService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("registration")
public class RegistrationView extends VerticalLayout {
    private final FormLayout layoutWithFormItems = new FormLayout();
    private TextField name;
    private TextField lastName;
    private TextField secondName;
    private PasswordField password;
    private TextField login;
    private TextField phone;
    private TextField email;
    private UserService userService;

    public RegistrationView(UserService userService) {
        name = new TextField();
        lastName = new TextField();
        secondName = new TextField();
        password = new PasswordField();
        login = new TextField();
        phone = new TextField();
        email = new TextField();
        this.userService = userService;
        initPage();
        addButton();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void addButton() {
        Button buttonSave = new Button("Регистрация", i -> {
            boolean hasError = false;
            if (!phone.getValue().matches("\\d+")) {
                Notification.show("Телефон должен состоять из цифр");
                hasError = true;
            }

            if (!name.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Имя должено состоять из букв");
                hasError = true;
            }

            if (!lastName.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Имя должено состоять из букв");
                hasError = true;
            }

            if (!secondName.getValue().matches("[а-яА-Я]+")) {
                Notification.show("Имя должено состоять из букв");
                hasError = true;
            }

            if (hasError) {
                return;
            } else {
                Users user = userService.saveUser(
                        phone.getValue(),
                        login.getValue(),
                        password.getValue(),
                        email.getValue(),
                        name.getValue(),
                        secondName.getValue(),
                        lastName.getValue());
                Notification.show("Учётная запись добавлена успешно");
                UI.getCurrent().navigate("login");
            }
        });
        add(buttonSave);
    }

    private void initPage() {
        layoutWithFormItems.addFormItem(name, "Имя");
        layoutWithFormItems.addFormItem(lastName, "Фамилия");
        layoutWithFormItems.addFormItem(secondName, "Отчество");
        layoutWithFormItems.addFormItem(password, "Пароль");
        layoutWithFormItems.addFormItem(login, "Логин");
        layoutWithFormItems.addFormItem(email, "E-mail");
        layoutWithFormItems.addFormItem(phone, "Телефон");
        add(layoutWithFormItems);
    }
}
