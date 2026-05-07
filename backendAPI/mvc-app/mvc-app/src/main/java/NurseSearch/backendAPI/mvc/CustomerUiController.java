package NurseSearch.backendAPI.mvc;

import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Appointment.AppointmentStatus;
import NurseSearch.backendAPI.entity.Customer;
import NurseSearch.backendAPI.entity.Listing;
import NurseSearch.backendAPI.entity.Review;
import NurseSearch.backendAPI.service.AppointmentService;
import NurseSearch.backendAPI.service.CustomerService;
import NurseSearch.backendAPI.service.ListingService;
import NurseSearch.backendAPI.service.NurseService;
import NurseSearch.backendAPI.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerUiController {

    @Autowired private CustomerService customerService;
    @Autowired private ListingService listingService;
    @Autowired private NurseService nurseService;
    @Autowired private AppointmentService appointmentService;
    @Autowired private ReviewService reviewService;

    // ── US-CUST-001: Account Creation & Login ────────────────────────

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer/signup";
    }

    @PostMapping("/signup")
    public String handleSignup(Customer customer, Model model) {
        if (customerService.emailExists(customer.getEmail())) {
            model.addAttribute("error", "An account with that email already exists.");
            model.addAttribute("customer", customer);
            return "customer/signup";
        }
        Customer saved = customerService.createCustomer(customer);
        return "redirect:/customer/signup/success?name=" + saved.getFirstName() + "&id=" + saved.getUserId();
    }

    @GetMapping("/signup/success")
    public String signupSuccess(@RequestParam String name, @RequestParam Long id,
                                HttpSession session, Model model) {
        session.setAttribute("customerId", id);
        session.setAttribute("customerName", name);
        model.addAttribute("name", name);
        return "customer/signup-success";
    }

    @GetMapping("/login")
    public String showLogin(Model model) { return "customer/login"; }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email, @RequestParam String password,
                              HttpSession session, Model model) {
        Customer customer = customerService.getCustomerByEmail(email);
        if (customer == null) {
            model.addAttribute("error", "No account found with that email.");
            return "customer/login";
        }
        String stored = customer.getPasswordHash();
        boolean match = stored.equals(password) || stored.equals("hashed_" + password);
        if (!match) {
            model.addAttribute("error", "Incorrect password.");
            return "customer/login";
        }
        session.setAttribute("customerId", customer.getUserId());
        session.setAttribute("customerName", customer.getFirstName());
        return "redirect:/customer/dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ── US-CUST-002: Search & Filter Nurses ─────────────────────────

    @GetMapping("/search")
    public String searchNurses(@RequestParam(required = false) String specialty,
                               @RequestParam(required = false) String city,
                               @RequestParam(required = false) String language,
                               @RequestParam(required = false) Double maxRate,
                               @RequestParam(required = false) String experienceLevel,
                               HttpSession session, Model model) {
        boolean hasFilter = notBlank(specialty) || notBlank(city) || notBlank(language)
                || maxRate != null || notBlank(experienceLevel);
        var nurses = hasFilter
                ? nurseService.searchNurses(specialty, city, language, maxRate, experienceLevel)
                : nurseService.getAllNurses();
        model.addAttribute("nurses", nurses);
        model.addAttribute("resultCount", nurses.size());
        model.addAttribute("specialty", specialty);
        model.addAttribute("city", city);
        model.addAttribute("language", language);
        model.addAttribute("maxRate", maxRate);
        model.addAttribute("experienceLevel", experienceLevel);
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "customer/search";
    }

    // ── US-CUST-003: View Nurse Profile ─────────────────────────────

    @GetMapping("/nurses/{id}")
    public String viewNurseProfile(@PathVariable Long id, HttpSession session, Model model) {
        return nurseService.getNurseById(id).map(nurse -> {
            List<Review> reviews = reviewService.getReviewsForNurse(id);
            model.addAttribute("nurse", nurse);
            model.addAttribute("reviews", reviews);
            model.addAttribute("customerId", session.getAttribute("customerId"));
            model.addAttribute("customerName", session.getAttribute("customerName"));
            return "customer/nurse-profile";
        }).orElse("redirect:/customer/search");
    }

    // ── US-CUST-004: Request Appointment Booking ─────────────────────

    @GetMapping("/nurses/{nurseId}/book")
    public String showBookingForm(@PathVariable Long nurseId, HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        return nurseService.getNurseById(nurseId).map(nurse -> {
            model.addAttribute("nurse", nurse);
            model.addAttribute("listings", listingService.getListingsByCustomerId(customerId));
            model.addAttribute("customerName", session.getAttribute("customerName"));
            return "customer/book-appointment";
        }).orElse("redirect:/customer/search");
    }

    @PostMapping("/nurses/{nurseId}/book")
    public String handleBooking(@PathVariable Long nurseId,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                                @RequestParam(required = false) String careDetails,
                                @RequestParam(required = false) Long listingId,
                                HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        try {
            Appointment appt = appointmentService.createAppointment(
                    customerId, nurseId, listingId, dateTime, careDetails);
            return "redirect:/customer/booking/success?appointmentId=" + appt.getAppointmentId();
        } catch (Exception e) {
            return nurseService.getNurseById(nurseId).map(nurse -> {
                model.addAttribute("nurse", nurse);
                model.addAttribute("error", "Booking failed: " + e.getMessage());
                model.addAttribute("listings", listingService.getListingsByCustomerId(customerId));
                model.addAttribute("customerName", session.getAttribute("customerName"));
                return "customer/book-appointment";
            }).orElse("redirect:/customer/search");
        }
    }

    @GetMapping("/booking/success")
    public String bookingSuccess(@RequestParam Long appointmentId, HttpSession session, Model model) {
        if (session.getAttribute("customerId") == null) return "redirect:/customer/login";
        appointmentService.getAppointmentById(appointmentId)
                .ifPresent(a -> model.addAttribute("appointment", a));
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "customer/booking-success";
    }

    // ── US-CUST-005: View Booking Status ────────────────────────────

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";

        model.addAttribute("listings",       listingService.getListingsByCustomerId(customerId));
        model.addAttribute("pendingAppts",   appointmentService.getAppointmentsByCustomerAndStatus(customerId, AppointmentStatus.PENDING));
        model.addAttribute("confirmedAppts", appointmentService.getAppointmentsByCustomerAndStatus(customerId, AppointmentStatus.CONFIRMED));
        model.addAttribute("completedAppts", appointmentService.getAppointmentsByCustomerAndStatus(customerId, AppointmentStatus.COMPLETED));
        model.addAttribute("cancelledAppts", appointmentService.getAppointmentsByCustomerAndStatus(customerId, AppointmentStatus.CANCELLED));
        model.addAttribute("declinedAppts",  appointmentService.getAppointmentsByCustomerAndStatus(customerId, AppointmentStatus.DECLINED));
        model.addAttribute("customerName",   session.getAttribute("customerName"));
        return "customer/dashboard";
    }

    // ── US-CUST-006: Create Job Listing ─────────────────────────────

    @GetMapping("/listing/new")
    public String showCreateListing(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        model.addAttribute("customerName", session.getAttribute("customerName"));
        model.addAttribute("listing", new Listing());
        return "customer/create-listing";
    }

    @PostMapping("/listing/new")
    public String handleCreateListing(Listing listing, HttpSession session) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        listingService.createListing(customerId, listing);
        return "redirect:/customer/listing/success";
    }

    @GetMapping("/listing/success")
    public String listingSuccess(HttpSession session, Model model) {
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "customer/listing-success";
    }

    // ── US-CUST-007: Rate & Review Nurse ────────────────────────────

    @GetMapping("/appointments/{appointmentId}/review")
    public String showReviewForm(@PathVariable Long appointmentId, HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        return appointmentService.getAppointmentById(appointmentId).map(appt -> {
            if (!appt.getCustomer().getUserId().equals(customerId))
                return "redirect:/customer/dashboard";
            if (appt.getStatus() != AppointmentStatus.COMPLETED)
                return "redirect:/customer/dashboard";
            if (Boolean.TRUE.equals(appt.getReviewedByCustomer()))
                return "redirect:/customer/dashboard";
            model.addAttribute("appointment", appt);
            model.addAttribute("customerName", session.getAttribute("customerName"));
            return "customer/write-review";
        }).orElse("redirect:/customer/dashboard");
    }

    @PostMapping("/appointments/{appointmentId}/review")
    public String handleReview(@PathVariable Long appointmentId,
                               @RequestParam Integer rating,
                               @RequestParam(required = false) String comment,
                               HttpSession session,
                               RedirectAttributes ra) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        try {
            reviewService.createReview(customerId, appointmentId, rating, comment);
            ra.addFlashAttribute("successMsg", "Your review was submitted. Thank you!");
        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Could not submit review: " + e.getMessage());
        }
        return "redirect:/customer/dashboard";
    }

    // ── US-CUST-008: Cancel Appointment ─────────────────────────────

    @PostMapping("/appointments/{appointmentId}/cancel")
    public String cancelAppointment(@PathVariable Long appointmentId,
                                    HttpSession session,
                                    RedirectAttributes ra) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        appointmentService.getAppointmentById(appointmentId).ifPresent(appt -> {
            if (appt.getCustomer().getUserId().equals(customerId)
                    && (appt.getStatus() == AppointmentStatus.PENDING
                     || appt.getStatus() == AppointmentStatus.CONFIRMED)) {
                appointmentService.cancelAppointment(appointmentId);
            }
        });
        ra.addFlashAttribute("successMsg", "Appointment cancelled successfully.");
        return "redirect:/customer/dashboard";
    }

    // ── Profile ──────────────────────────────────────────────────────

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long customerId = (Long) session.getAttribute("customerId");
        if (customerId == null) return "redirect:/customer/login";
        customerService.getCustomerById(customerId).ifPresent(c -> model.addAttribute("customer", c));
        model.addAttribute("customerName", session.getAttribute("customerName"));
        return "customer/profile";
    }

    private boolean notBlank(String s) { return s != null && !s.isBlank(); }
}
