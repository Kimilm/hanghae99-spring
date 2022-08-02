package com.sparta.selectshop.repository;

import com.sparta.selectshop.models.ApiUseTime;
import com.sparta.selectshop.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}
