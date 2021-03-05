package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
}
