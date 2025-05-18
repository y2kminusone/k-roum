package com.kroum.kroum.repository;

import com.kroum.kroum.entity.PlaceLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceLanguageRepository extends JpaRepository<PlaceLanguage, Long> {
}