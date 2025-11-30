package org.limbusnoe.jpa.repository;

import org.limbusnoe.jpa.models.PageCompletion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PageCompletionRepository extends JpaRepository<PageCompletion, Long> {
    List<PageCompletion> findByStudentId(Long studentId);
    List<PageCompletion> findByPageId(UUID pageId);

    Optional<PageCompletion> findFirstByStudentIdAndPageId(Long studentId, UUID pageId);

    boolean existsByStudentIdAndPageId(Long studentId, UUID pageId);
}
