package local.ln.repositories;

import local.ln.entities.LinkEntity;
import local.ln.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LinkRepository extends JpaRepository<LinkEntity, Long> {

    public LinkEntity findBySuffix(String suffix);

    public List<LinkEntity> findByUser(UserEntity user);
}
