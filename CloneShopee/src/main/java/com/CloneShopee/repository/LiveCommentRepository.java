package com.CloneShopee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CloneShopee.models.LiveComment;

public interface LiveCommentRepository extends JpaRepository<LiveComment, Integer> {

    List<LiveComment> findByLiveId(Integer liveId);
}
