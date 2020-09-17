package Tshop;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Assignment")
public class Assignment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long assignmentId;
    private Long reservationId;
    private Long bookId;
    private String agencyName;

    @PostPersist
    public void onPostPersist(){
        ReservationAccepted reservationAccepted = new ReservationAccepted();
        BeanUtils.copyProperties(this, reservationAccepted);
        reservationAccepted.setReservationId(this.getReservationId());
        reservationAccepted.setAgencyname(this.getAgencyName());
        reservationAccepted.setStatus("예약접수완료");
        reservationAccepted.publishAfterCommit();
    }
    @PostRemove
    public void onPostRemove(){
        ReservationCanceled reservationCanceled = new ReservationCanceled();
        BeanUtils.copyProperties(this, reservationCanceled);
        reservationCanceled.setStatus("예약취소완료");
        reservationCanceled.publishAfterCommit();
    }

    public Long getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Long assignmentId) {
        this.assignmentId = assignmentId;
    }
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }


    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }





}
