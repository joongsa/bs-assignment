package Tshop;

import Tshop.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PolicyHandler{
    @Autowired
    AssignmentRepository assignmentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationRequested_ReservationAccept(@Payload ReservationRequested reservationRequested){

        if(reservationRequested.isMe()){
            System.out.println("##### listener ReservationAccept : " + reservationRequested.toJson());
            Assignment assignment = new Assignment();
            assignment.setReservationId(reservationRequested.getReservationId());
            assignment.setAgencyName("대리점_" + (int)((Math.random()*10000)%10));
            assignment.setBookId(reservationRequested.getBookId());
            assignmentRepository.save(assignment);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverReservationCancelRequested_ReservationCancel(@Payload ReservationCancelRequested reservationCancelRequested){

        if(reservationCancelRequested.isMe()){
            System.out.println("##### listener ReservationCancel : " + reservationCancelRequested.toJson());
            //Assignment assignment = new Assignment();
            //assignment.setReservationId(reservationCancelRequested.getReservationId());
            Optional<Assignment> optionalAssignment = assignmentRepository.findByReservationId(reservationCancelRequested.getReservationId());
            Assignment assignment = optionalAssignment.orElseGet(Assignment::new);
            assignmentRepository.delete(assignment);
        }
    }


}
