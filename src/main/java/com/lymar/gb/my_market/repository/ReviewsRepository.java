package com.lymar.gb.my_market.repository;

import com.lymar.gb.my_market.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
//    Reviews getReviews(Long id);
    List<Reviews> findByProductId(Long id);
}
