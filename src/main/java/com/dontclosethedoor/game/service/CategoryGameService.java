package com.dontclosethedoor.game.service;

import com.dontclosethedoor.game.model.CategoryGame;
import com.dontclosethedoor.game.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryGameService {

    private final ICategoryRepository repository;

    public CategoryGame searchCategoryById(Long id){
        return repository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

}
