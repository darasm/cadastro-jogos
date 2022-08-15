package com.dontclosethedoor.game.repository;

import com.dontclosethedoor.game.model.CategoryGame;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<CategoryGame, Long> {
}
