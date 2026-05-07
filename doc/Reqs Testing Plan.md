**Project Name:** Nurse Search
**Version:** 1.0
**Date:** 2026-05-07
**Purpose:**  This document outlines comprehensive test scenarios for each functional requirement (user story) in the Nurse Search system.

## Actors
- Provider P: Nurse (healthcare provider seeking patients)
- Customer C: Patient/Family member (seeking in-home nursing care)
- Service S: Appointment (scheduled care session between customer and nurse)

---

## Use Cases

#### 1. Customer: US-CUST-001 — Register and manage profile
1. Customer C1 navigates to `/customer/signup` and creates a new account with
   first name, last name, email, phone, and password.
2. C1 is automatically logged in and redirected to the customer dashboard.
3. C1 navigates to their profile and clicks **Update Profile**.
4. C1 updates their phone number and city, then saves.
5. C1 confirms the green success banner appears and the updated values are shown.
6. C1 exits (logs out).

---

#### 2. Customer: US-CUST-002 — Search and filter nurses, US-CUST-003 — View nurse profile
1. C1 logs in and navigates to **Find Nurses**.
2. C1 sees all available nurses displayed with no filters applied.
3. C1 types "Geriatric Care" in the specialty search box and clicks **Search**.
4. C1 confirms only nurses with Geriatric Care specialty are shown and the
   result count updates.
5. C1 opens the sidebar, selects "Senior" experience level, and sets max rate to $50,
   then clicks **Apply Filters**.
6. C1 confirms the results are further narrowed to Senior-level Geriatric Care nurses
   at or below $50/hr.
7. C1 clicks on nurse P1's card and views their full profile including specialty,
   bio, languages spoken, hourly rate, hours of operation, and reviews.
8. C1 exits.

---

#### 3. Customer: US-CUST-004 — Request appointment booking, US-CUST-005 — View booking status
1. C1 logs in and navigates to nurse P1's profile.
2. C1 clicks **Book Appointment**.
3. C1 selects a future date and time, enters care details "Post-surgery wound care",
   and clicks **Send Booking Request**.
4. C1 confirms the booking success page shows nurse P1's name and status **Pending**.
5. C1 navigates to **My Bookings** (dashboard).
6. C1 clicks the **Pending** tab and confirms appointment S1 is listed with P1's
   name and the selected date.
7. C1 exits.

---

#### 4. Customer: US-CUST-006 — Create job listing
1. C1 logs in and navigates to the dashboard.
2. C1 clicks **+ Create Job Listing**.
3. C1 fills in specialty "Wound Care", start date, duration 14 days, budget $30/hr,
   language "English", and requirements "Evening availability preferred".
4. C1 clicks **Publish Listing**.
5. C1 confirms the success page appears.
6. C1 navigates back to the dashboard and clicks the **My Listings** tab.
7. C1 confirms the new listing appears with status **OPEN**.
8. C1 exits.

---

#### 5. Customer: US-CUST-008 — Cancel appointment
1. C1 logs in and navigates to **My Bookings**.
2. C1 clicks the **Pending** tab and sees appointment S1.
3. C1 clicks **Cancel Request** and confirms the dialog.
4. C1 confirms the green banner "Appointment cancelled successfully" appears.
5. C1 confirms the **Pending** tab is now empty.
6. C1 clicks the **Cancelled** tab and confirms S1 appears with a Cancelled badge.
7. C1 exits.

---

#### 6. Provider: US-PROV-001 — Register and update nursing profile
1. Nurse P1 navigates to `/provider/signup` and creates a profile with first name,
   last name, email, password, specialty "Geriatric Care", experience level "Senior",
   hourly rate $45, city "Greensboro", languages "English, Spanish",
   bio, license number, hours of operation "Mon-Fri 8am-6pm", and internship
   availability checked.
2. P1 is redirected to the provider dashboard.
3. P1 navigates to **Profile** and clicks **Update Profile**.
4. P1 updates their bio and hourly rate, then saves.
5. P1 confirms the updated values appear on the profile page.
6. P1 exits.

---

#### 7. Provider: US-PROV-002 — Browse and filter customer listings, US-PROV-003 — Internship availability, US-PROV-004 — Hours of operation
1. P1 logs in and clicks **Open Listings** in the navbar.
2. P1 confirms the listing posted by C1 in use case 4 is visible with specialty,
   budget, start date, and requirements shown.
3. P1 types "Wound Care" in the specialty filter and clicks **Apply Filters**.
4. P1 confirms only Wound Care listings are shown.
5. P1 clicks **Apply for this Listing** on C1's listing.
6. P1 confirms the modal opens showing C1's contact email and a pre-filled message.
7. P1 navigates to **Edit Profile** and confirms the internship availability
   checkbox reflects the value set during signup.
8. P1 confirms hours of operation "Mon-Fri 8am-6pm" are shown on their public profile.
9. P1 exits.

---

#### 8. Provider + Customer: US-CUST-004 — Booking, US-CUST-005 — View booking status (Confirmed), US-CUST-007 — Rate and review nurse
1. C2 logs in (second customer account) and books an appointment S2 with nurse P1,
   entering date, time, and care details.
2. P1 logs in (separate browser) and navigates to their dashboard **Pending** tab.
3. P1 confirms appointment S2 appears with C2's name and care details.
4. P1 clicks **Confirm** on appointment S2.
5. P1 confirms the green banner "Appointment confirmed successfully" appears and
   S2 moves to the **Upcoming** tab.
6. C2 refreshes their dashboard and confirms S2 has moved from **Pending** to
   the **Upcoming** tab.
7. Appointment S2 is manually set to status **COMPLETED** in the database.
8. C2 navigates to the **Completed** tab on their dashboard.
9. C2 confirms S2 appears with a **Write Review** button.
10. C2 clicks **Write Review**, selects 5 stars, writes "Sarah was professional
    and very caring", and clicks **Submit Review**.
11. C2 confirms the green banner "Your review was submitted. Thank you!" appears.
12. C2 confirms the button on S2 now shows **Review Submitted ✓**.
13. P1 navigates to their **Profile** page and confirms the review appears with
    the 5-star rating and the comment.
14. P1 confirms their average rating has been updated to reflect the new review.
15. C2 and P1 exit.

