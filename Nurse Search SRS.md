
# Requirements – Starter Template

**Project Name:** Nurse Search \
**Team:** Abenezer Shiferaw and Allen Holthz\
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-02-12

---

## 1. Overview
**Vision.** One or two sentences: who this is for, the core problem, and the outcome.
Nurse Search will connect elderly individuals and patients requiring in-home care with qualified nurses, making it easy to find the right caregiver for you and your needs. The customer can filter based on specialty, experience, language spoken, and rate, and confirm and schedule a booking.

**Glossary** Terms used in the project
- **Term 1:** Customer: Individual seeking an in-home caregiver 
- **Term 2:** Provider: In-home caregiver/nurse
- **Term 3:** Bookings: A scheduled appointment between customer and provider
- **Term 4:** Specialty: Specific area of nursing (wound care, rehab, medication management)
- **Term 5:** Listings: A job posting by the customers with description of the work.


**Primary Users / Roles.**
- **Customer (Patient: )** — Find and book nurses who match the requirements they need.
- **Provider ( Nurse:  )** — Discover jobs and accept or decline if it aligns with their skills and rate.

**Scope (this semester).**
- Account creation and authentication for both customer and provider
- Nurse profile creation with specialty, experience, and rate information
- Search and filter functionality for customers to find nurses
- Booking system
- Appointment acceptance or decline
- Calendar view showing upcoming appointments
- Customer creating listing 
- Rating and review system

**Out of scope (deferred).**
- Payment processing and transaction handling
- Background check verification system.
- Real-time chat between customers and nurses.

> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)

### 2.1 Customer Stories
- **US‑CUST‑001 — <Account creation and login>**  
  _Story:_ As a customer, I want to create an account and log in, so that I can access the app to search for nurses. 
  _Acceptance:_
  ```gherkin
  Scenario: Customer successfully creates account
    Given I am on the registration page
    When I enter my name, email, phone number, and password
    And I submit the registration form
    Then my account is created
    And I am logged in to the platform
    And I am redirected to the customer dashboard
  ```

- **US‑CUST‑002 — <Search and filter Nurse>**  
  _Story:_  As a customer, I want to search and filter nurses by specialty, experience level, etc., so that I can find a nurse who meets my specific care needs and budget.
  _Acceptance:_
  ```gherkin
  Scenario: Customer filters nurses by specialty and price range
    Given I am logged in as a customer
    When I navigate to the nurse search page
    And I select "Geriatric Care" as the specialty
    And I set the price range to $25-$40 per hour
    And I click "Search"
    Then I see a list of nurses matching my criteria
    And each result shows the nurse's name, specialty, experience, and rate
  ```
- **US‑CUST‑003 — <View Nurse profile>**  
  _Story:_ As a customer, I want to view detailed nurse profiles including qualifications, experience, ratings, and reviews, so that I can make an informed decision about which nurse to book.
  _Acceptance:_
  ```gherkin
  Scenario: Customer views complete nurse profile
    Given I am viewing search results for nurses
    When I click on a nurse's profile
    Then I see their full profile
  ```

- **US‑CUST‑004 — <Request Appointment Booking>**  
  _Story:_  As a customer, I want to request an appointment with a nurse for a specific date and time, so that I can schedule the care I need.
  _Acceptance:_
  ```gherkin
  Scenario: Customer requests a booking
    Given I am viewing a nurse's profile
    When I select a date and time from their available slots
    And I provide details about the care needed
    And I submit the booking request
    Then the nurse receives a notification of my request
    And I see a confirmation that my request was sent
    And the booking appears as "Pending" in my dashboard
  ```
- **US‑CUST‑005 — <View Booking Status>**  
  _Story:_  As a customer, I want to view the status of my booking requests and confirmed appointments, so that I can track my scheduled care and pending requests.
  _Acceptance:_
  ```gherkin
  Scenario: Customer checks booking status
    Given I am logged in as a customer
    When I navigate to "My Bookings"
    Then I see all my bookings organized by status:
      | Pending requests |
      | Confirmed appointments |
      | Completed appointments |
      | Declined requests |
    And each booking shows the nurse name, date, time, and current status
  ```

- **US‑CUST‑006 — <Create Job Listing>**  
  _Story:_  As a customer, I want to create a job listing describing my care needs, so that nurses can find and apply to opportunities that match their skills.
  _Acceptance:_
  ```gherkin
  Scenario: Customer creates a job listing
    Given I am logged in as a customer
    When I navigate to "Post a Job"
    And I enter the required care details:
      | specialty needed |
      | start date |
      | duration of care |
      | hourly budget |
      | additional requirements |
    And I submit the listing
    Then my listing is published
    And nurses can view and apply to my listing
    And I receive a confirmation
  ```
- **US‑CUST‑007 — <Rate and Review Nurse>**  
  _Story:_ As a customer, I want to rate and review a nurse after a completed appointment, so that I can share my experience and help other customers make informed decisions.
  _Acceptance:_
  ```gherkin
  Scenario: Customer submits a review after appointment
    Given I have a completed appointment with a nurse
    When I navigate to my completed bookings
    And I select "Write Review" for that appointment
    And I provide a rating from 1-5 stars
    And I write a text review
    And I submit the review
    Then the review is published on the nurse's profile
    And the nurse's average rating is updated
  ```

- **US‑CUST‑008 — <Cancel Appointment>**  
  _Story:_  As a customer, I want to cancel a confirmed appointment, so that I can adjust my schedule when plans change.
  _Acceptance:_
  ```gherkin
  Scenario: Customer cancels an upcoming appointment
    Given I have a confirmed appointment
    When I navigate to "My Bookings"
    And I select the appointment to cancel
    And I click "Cancel Appointment"
    And I confirm the cancellation
    Then the appointment status changes to "Cancelled"
    And the nurse receives a cancellation notification
    And the time slot becomes available again in the nurse's calendar
  ```

### 2.2 Provider Stories
- **US‑PROV‑001 — <short title>**  
  _Story:_ As a provider, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑PROV‑002 — <short title>**  
  _Story:_ As a provider, I want … so that …  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```



## 3. Non‑Functional Requirements (make them measurable)
- **Performance:** Page load time should not exceed 5 seconds; the app should support multiple devices logged in at the same time.
- **Availability/Reliability:** Platform should be available 95% of the time.
- **Security/Privacy:** The app should be secure and encrypted.
- **Usability:** The UI should be easy to use and similar to other apps so that elderly people can use it easily.
---

## 4. Assumptions, Constraints, and Policies
- Assumptions- Nurses are licensed and certified; both the customer and provider provide accurate information.
- Policies- Users must be 18 or above; nurses must accept booking within 48 hours; users can only review nurses after a booking is complete.
- Constraints- We have only this semester to finish the app.
---

## 5. Milestones (course‑aligned)
- **M2 Requirements** — this file + stories opened as issues. 
- **M3 High‑fidelity prototype** — core customer/provider flows fully interactive. 
- **M4 Design** — architecture, schema, API outline. 
- **M5 Backend API** — key endpoints + tests. 
- **M6 Increment** — ≥2 use cases end‑to‑end. 
- **M7 Final** — complete system & documentation. 

---

## 6. Change Management
- Stories are living artifacts; changes are tracked via repository issues and linked pull requests.  
- Major changes should update this SRS.
