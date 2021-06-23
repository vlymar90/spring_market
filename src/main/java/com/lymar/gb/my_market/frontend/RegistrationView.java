package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.entity.Users;
import com.lymar.gb.my_market.repository.UserRepository;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public RegistrationView(UserRepository userRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        name = new TextField();
        lastName = new TextField();
        secondName = new TextField();
        password = new PasswordField();
        login = new TextField();
        phone = new TextField();
        email = new TextField();
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        initPage();
        addButton();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
    }

    private void addButton() {
        Button buttonSave = new Button("Регистрация", i -> {
            Users users = new Users();
            String passEncode = bCryptPasswordEncoder.encode(password.getValue());
            users.setLogin(login.getValue());
            users.setName(name.getValue());
            users.setLast_name(lastName.getValue());
            users.setSecond_name(secondName.getValue());
            users.setPhone(phone.getValue());
            users.setPassword(passEncode);
            users.setRole("user");
            userRepository.save(users);
            Notification.show("Учётная запись добавлена успешно");
            UI.getCurrent().navigate("login");
        });
        add(buttonSave);
    }

    private void initPage() {
        layoutWithFormItems.addFormItem(name, "First name");
        layoutWithFormItems.addFormItem(lastName, "Last name");
        layoutWithFormItems.addFormItem(secondName, "Second name");
        layoutWithFormItems.addFormItem(password, "password");
        layoutWithFormItems.addFormItem(login, "login");
        layoutWithFormItems.addFormItem(email, "E-mail");
        layoutWithFormItems.addFormItem(phone, "Phone");
        add(layoutWithFormItems);
    }
}
