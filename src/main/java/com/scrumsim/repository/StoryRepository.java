package com.scrumsim.repository;

import com.scrumsim.model.Story;
import com.scrumsim.model.StoryStatus;
import java.util.List;

public interface StoryRepository {

    List<Story> findAll();

    Story findById(String id);

    void save(Story story);

    boolean existsByTitle(String title);

    List<Story> findAllSortedByPriority();

    List<Story> findAllSortedByOrderIndex();

    List<Story> findAllSortedByRank();

    List<Story> findAllSortedByBusinessValue();

    List<Story> findAllSortedByUrgency();

    List<Story> findAllSortedByEffort();

    List<Story> findAllSortedByPoints();

    List<Story> findAllSortedByPriorityDescending();

    List<Story> findAllSortedByBusinessValueDescending();

    List<Story> findAllByStatusSortedByPriority(StoryStatus status);
}