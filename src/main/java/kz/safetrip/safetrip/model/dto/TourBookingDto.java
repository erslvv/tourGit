package kz.safetrip.safetrip.model.dto;

import kz.safetrip.safetrip.enumeration.TourBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourBookingDto {
    private Long id;
    private Long userId;
    private String userEmail;
    private Long tourId;
    private String tourTitle;
    private String fullName;
    private String phoneNumber;
    private String contactEmail;
    private String notes;
    private TourBookingStatus status;
    private String ticketCode;
    private LocalDateTime createdAt;
}
