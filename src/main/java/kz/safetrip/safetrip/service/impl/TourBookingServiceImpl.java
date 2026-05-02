package kz.safetrip.safetrip.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.safetrip.safetrip.enumeration.TourBookingStatus;
import kz.safetrip.safetrip.model.dto.TourBookingDto;
import kz.safetrip.safetrip.model.entity.Tour;
import kz.safetrip.safetrip.model.entity.TourBooking;
import kz.safetrip.safetrip.model.entity.User;
import kz.safetrip.safetrip.repository.TourBookingRepository;
import kz.safetrip.safetrip.repository.TourRepository;
import kz.safetrip.safetrip.repository.UserRepository;
import kz.safetrip.safetrip.service.TourBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TourBookingServiceImpl implements TourBookingService {

    private final TourBookingRepository tourBookingRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TourBookingDto create(Long userId, TourBookingDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));
        Tour tour = tourRepository.findById(dto.getTourId())
                .orElseThrow(() -> new EntityNotFoundException("Tour not found: " + dto.getTourId()));

        if (tourBookingRepository.existsByUserIdAndTourId(userId, dto.getTourId())) {
            throw new IllegalStateException("You already booked this tour.");
        }

        if (tour.getCapacity() != null) {
            long bookedSeats = tourBookingRepository.countByTourId(dto.getTourId());
            if (bookedSeats >= tour.getCapacity()) {
                throw new IllegalStateException("No seats left for this tour.");
            }
        }

        TourBooking booking = new TourBooking();
        booking.setUser(user);
        booking.setTour(tour);
        booking.setFullName(dto.getFullName());
        booking.setPhoneNumber(dto.getPhoneNumber());
        booking.setContactEmail(
                dto.getContactEmail() == null || dto.getContactEmail().isBlank() ? user.getEmail() : dto.getContactEmail()
        );
        booking.setPeopleCount(1);
        booking.setNotes(dto.getNotes());
        booking.setStatus(TourBookingStatus.BOOKED);
        booking.setTicketCode(generateTicketCode(userId, dto.getTourId()));

        return toDto(tourBookingRepository.save(booking));
    }

    @Override
    public List<TourBookingDto> getCurrentUserBookings(Long userId) {
        return tourBookingRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<TourBookingDto> getAll() {
        return tourBookingRepository.findAll()
                .stream()
                .map(this::toDto)
                .sorted((left, right) -> right.getCreatedAt().compareTo(left.getCreatedAt()))
                .toList();
    }

    @Override
    @Transactional
    public void cancel(Long userId, Long bookingId) {
        TourBooking booking = tourBookingRepository.findById(bookingId);
        if (booking == null) {
            throw new EntityNotFoundException("Booking not found: " + bookingId);
        }
        if (!booking.getUser().getId().equals(userId)) {
            throw new IllegalStateException("You can cancel only your own booking.");
        }
        tourBookingRepository.deleteById(bookingId);
    }

    private TourBookingDto toDto(TourBooking booking) {
        return TourBookingDto.builder()
                .id(booking.getId())
                .userId(booking.getUser().getId())
                .userEmail(booking.getUser().getEmail())
                .tourId(booking.getTour().getId())
                .tourTitle(booking.getTour().getTitle())
                .fullName(booking.getFullName())
                .phoneNumber(booking.getPhoneNumber())
                .contactEmail(booking.getContactEmail())
                .notes(booking.getNotes())
                .status(booking.getStatus())
                .ticketCode(booking.getTicketCode())
                .createdAt(booking.getCreatedAt())
                .build();
    }

    private String generateTicketCode(Long userId, Long tourId) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return "ST-" + tourId + "-" + userId + "-" + timestamp;
    }
}
