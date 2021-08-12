package com.example.exam.persistance;

import com.example.exam.domain.CustomExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CustomExtensionRepository extends JpaRepository<CustomExtension, Long> {

    CustomExtension save(CustomExtension customExtension);

    Optional<CustomExtension> findByName(String name);

    List<CustomExtension> findAll();

    void deleteBySeq(Long seq);
}
