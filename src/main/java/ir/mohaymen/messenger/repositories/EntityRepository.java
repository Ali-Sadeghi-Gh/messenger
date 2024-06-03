package ir.mohaymen.messenger.repositories;

import ir.mohaymen.messenger.entities.EntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<EntityEntity, Long> {
}
