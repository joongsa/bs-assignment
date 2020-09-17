package Tshop;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AssignmentRepository extends PagingAndSortingRepository<Assignment, Long >{
    Optional<Assignment> findByReservationId(Long reservationId);
}