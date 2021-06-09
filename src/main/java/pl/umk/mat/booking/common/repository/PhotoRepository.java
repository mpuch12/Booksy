package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
