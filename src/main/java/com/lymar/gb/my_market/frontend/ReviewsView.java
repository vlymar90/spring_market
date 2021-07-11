package com.lymar.gb.my_market.frontend;

import com.lymar.gb.my_market.config.security.CustomPrincipal;
import com.lymar.gb.my_market.config.security.SecurityUtils;
import com.lymar.gb.my_market.entity.Product;
import com.lymar.gb.my_market.entity.Reviews;
import com.lymar.gb.my_market.repository.ReviewsRepository;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@Route("review")
public class ReviewsView extends VerticalLayout {
    private final ReviewsRepository reviewRepository;
    private final Authentication authentication;

    private final Product product;

    public ReviewsView(ReviewsRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
        this.authentication = SecurityContextHolder.getContext().getAuthentication();
        this.product = (Product) ComponentUtil.getData(UI.getCurrent(), "product");
        if (this.product == null) {
            UI.getCurrent().navigate("");
        } else {
            var reviews = reviewRepository.findByProductId(product.getId());
            if (SecurityUtils.isAdmin(authentication)) {
                initView(reviews);
            } else {
                List<Reviews> list = new ArrayList<>();
                for (int i = 0; i < reviews.size(); i++) {
                    Reviews review = reviews.get(i);
                    if (review != null && review.getIs_moderated()) {
                        list.add(review);
                    }
                }
                initView(list);
            }
        }
    }

    private void initView(List<Reviews> reviews) {
        TextArea editableTextArea = new TextArea();
        editableTextArea.setSizeFull();
        var saveReviewButton = new Button("Сохранить отзыв", event -> {
            Reviews review = new Reviews();
            review.setProduct(product);
            review.setUser(((CustomPrincipal) authentication.getPrincipal()).getUser());
            review.setText(editableTextArea.getValue());
            review.setIs_moderated(false);
            reviewRepository.save(review);

            Notification.show("Ваш отзыв успешно сохранён");
        });
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        for (Reviews review : reviews) {
            TextArea textArea = new TextArea(review.getUser().getFIO());
            textArea.setValue(review.getText() != null ? review.getText() : "");
            textArea.setSizeFull();
            if(SecurityUtils.isAdmin(authentication)) {
                HorizontalLayout hl = new HorizontalLayout();
                Button button = new Button("Сохранить и изменить", i -> {
                    Reviews reviewsDB = reviewRepository.findById(review.getId()).orElseThrow();
                    reviewsDB.setText(textArea.getValue());
                    reviewsDB.setIs_moderated(true);
                    reviewRepository.save(reviewsDB);
                });

                hl.setSizeFull();
                button.setHeightFull();
                hl.add(textArea, button);
                add(hl);
            }
            else {
                textArea.setReadOnly(true);
                add(textArea);
            }
        }
        add(editableTextArea, saveReviewButton);
    }
}
