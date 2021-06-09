package pl.umk.mat.booking.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umk.mat.booking.common.model.Term;

public interface TermRepository extends JpaRepository<Term, Long> {
}
