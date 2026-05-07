package NurseSearch.backendAPI.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import NurseSearch.backendAPI.dto.NurseRequest;
import NurseSearch.backendAPI.entity.Appointment;
import NurseSearch.backendAPI.entity.Nurse;
import NurseSearch.backendAPI.service.AppointmentService;
import NurseSearch.backendAPI.service.ListingService;
import NurseSearch.backendAPI.service.NurseService;
import NurseSearch.backendAPI.service.ReviewService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/provider")
public class ProviderUiController {

    @Autowired
    private NurseService nurseService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("nurseRequest", new NurseRequest());
        return "provider/signup";
    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute NurseRequest nurseRequest,
                               HttpSession session,
                               Model model) {
        try {
            Nurse saved = nurseService.registerNurse(nurseRequest);

            session.setAttribute("nurseId", saved.getUserId());
            session.setAttribute("nurseName", saved.getFirstName());

            return "redirect:/provider/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nurseRequest", nurseRequest);
            return "provider/signup";
        }
    }

    @GetMapping("/login")
    public String showLogin() {
        return "provider/login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {
        try {
            Nurse nurse = nurseService.getNurseByEmail(email);

            String stored = nurse.getPasswordHash();
            boolean match = stored.equals(password) || stored.equals("hashed_" + password);

            if (!match) {
                model.addAttribute("error", "Incorrect password.");
                return "provider/login";
            }

            nurse.setLoginCount(nurse.getLoginCount() == null ? 1 : nurse.getLoginCount() + 1);
            nurse.setLastLoginAt(java.time.LocalDateTime.now());
            nurseService.updateNurse(nurse.getUserId(), nurse);

            session.setAttribute("nurseId", nurse.getUserId());
            session.setAttribute("nurseName", nurse.getFirstName());

            return "redirect:/provider/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("error", "No nurse account found with that email.");
            return "provider/login";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        Nurse nurse = nurseService.getNurseById(nurseId)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + nurseId));

        model.addAttribute("nurse", nurse);
        model.addAttribute("nurseName", session.getAttribute("nurseName"));

        model.addAttribute("pendingAppts",
                appointmentService.getAppointmentsByNurseAndStatus(nurseId, Appointment.AppointmentStatus.PENDING));

        model.addAttribute("confirmedAppts",
                appointmentService.getAppointmentsByNurseAndStatus(nurseId, Appointment.AppointmentStatus.CONFIRMED));

        model.addAttribute("completedAppts",
                appointmentService.getAppointmentsByNurseAndStatus(nurseId, Appointment.AppointmentStatus.COMPLETED));

        model.addAttribute("declinedAppts",
                appointmentService.getAppointmentsByNurseAndStatus(nurseId, Appointment.AppointmentStatus.DECLINED));

        return "provider/dashboard";
    }

    @PostMapping("/appointments/{appointmentId}/status")
    public String updateAppointmentStatus(@PathVariable Long appointmentId,
                                          @RequestParam String status,
                                          HttpSession session,
                                          RedirectAttributes ra) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        try {
            Appointment.AppointmentStatus newStatus =
                    Appointment.AppointmentStatus.valueOf(status.toUpperCase());

            appointmentService.updateStatus(appointmentId, newStatus);
            ra.addFlashAttribute("successMsg", "Appointment status updated.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMsg", "Could not update appointment: " + e.getMessage());
        }

        return "redirect:/provider/dashboard";
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        Nurse nurse = nurseService.getNurseById(nurseId)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + nurseId));

        model.addAttribute("nurse", nurse);
        model.addAttribute("reviews", reviewService.getReviewsForNurse(nurseId));
        model.addAttribute("nurseName", session.getAttribute("nurseName"));

        return "provider/profile";
    }

    @GetMapping("/profile/edit")
    public String editProfile(HttpSession session, Model model) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        Nurse nurse = nurseService.getNurseById(nurseId)
                .orElseThrow(() -> new RuntimeException("Nurse not found with id: " + nurseId));

        model.addAttribute("nurse", nurse);
        model.addAttribute("nurseName", session.getAttribute("nurseName"));

        return "provider/edit-profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute Nurse nurseDetails,
                                HttpSession session,
                                RedirectAttributes ra) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        try {
            Nurse updated = nurseService.updateNurse(nurseId, nurseDetails);
            session.setAttribute("nurseName", updated.getFirstName());
            ra.addFlashAttribute("successMsg", "Profile updated successfully.");
        } catch (RuntimeException e) {
            ra.addFlashAttribute("errorMsg", "Could not update profile: " + e.getMessage());
        }

        return "redirect:/provider/profile";
    }

    @GetMapping("/listings")
    public String listings(@RequestParam(required = false) String specialty,
                           @RequestParam(required = false) Double maxBudget,
                           HttpSession session,
                           Model model) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        model.addAttribute("listings", listingService.filterOpenListings(specialty, maxBudget));
        model.addAttribute("specialty", specialty);
        model.addAttribute("maxBudget", maxBudget);
        model.addAttribute("nurseName", session.getAttribute("nurseName"));

        return "provider/listings";
    }

    @GetMapping("/reviews")
    public String reviews(HttpSession session, Model model) {
        Long nurseId = (Long) session.getAttribute("nurseId");
        if (nurseId == null) return "redirect:/provider/login";

        model.addAttribute("reviews", reviewService.getReviewsForNurse(nurseId));
        model.addAttribute("nurseName", session.getAttribute("nurseName"));

        return "provider/reviews";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}