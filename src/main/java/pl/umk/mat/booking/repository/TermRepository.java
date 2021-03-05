package pl.umk.mat.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.model.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
}
