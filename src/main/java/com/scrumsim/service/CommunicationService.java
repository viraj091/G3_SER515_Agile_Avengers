package com.scrumsim.service;

import com.scrumsim.model.CommunicationEntry;
import java.util.List;

public interface CommunicationService {

    void record(String stakeholderName, String message);

    List<CommunicationEntry> getAll();
}