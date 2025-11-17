package com.scrumsim.repository;

import com.scrumsim.model.Story;
import java.util.List;

public interface StoryRepository {

    List<Story> findAll();

    Story findById(String id);

    void save(Story story);

    boolean existsByTitle(String title);
}
