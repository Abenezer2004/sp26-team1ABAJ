
# Requirements – Starter Template

**Project Name:** Nurse serarch \
**Team:** Abenezer Shiferaw and Allen Holthz\
**Course:** CSC 340\
**Version:** 1.0\
**Date:** 2026-02-12

---

## 1. Overview
**Vision.** One or two sentences: who this is for, the core problem, and the outcome.
Nurse search will connect elderly and individuals/ patients requiring in-home care with qualifed nurses, making it easy to find the right caregiver for you and your needs. The customer can filter based on specialtity, experience,languge spoken and rate. And conferm and shedule a booking  

**Glossary** Terms used in the project
- **Term 1:** Customer:indiviual seaking a in home caregiver 
- **Term 2:** provider: in home care giver/ nurse
- **Term 3:** bookings: A scheduled appointment between customer and provider 
- **Term 4:** Specialty: Specific area of nursing (wound care, rehabe. medication managment)
- **Term 5:** Listings: A job posting by the customers with discribitin of the work.


**Primary Users / Roles.**
- **Customer (Patient: )** —  Find and book nurses who match the requirments they need.
- **Provider ( Nurse:  )** — Discover job and accept or decline if it alligns with their skills and rate.

**Scope (this semester).**
- <Account creation and authentication for both customer and provider>
- <Nurse profile creation with specialty, experience, and rate information>
- <Search and filter functionality for customers to find nurses>
- <Booking system>
- <Appointment acceptance or decline> 
- <Calendar view showing upcoming appointments>
- <Customer creating listing >
- <Rating and review system> 

**Out of scope (deferred).**
- <Payment processing and transaction handling>
- <Background check verification system>
- <Real-time chat between customers and nurses>

> This document is **requirements‑level** and solution‑neutral; design decisions (UI layouts, API endpoints, schemas) are documented separately.

---

## 2. Functional Requirements (User Stories)
Write each story as: **As a `<role>`, I want `<capability>`, so that `<benefit>`.** Each story includes at least one **Given/When/Then** scenario.

### 2.1 Customer Stories
- **US‑CUST‑001 — <Account creation and login>**  
  _Story:_ As a customer, I want create and account and log in, so that so that I can access the app to search for nurse.  
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑CUST‑002 — <Search and filter Nurse>**  
  _Story:_ As a customer, I want search and filter nurses by specialty, experience level, etc, so that I can find a nurse who meets my specific care needs and budget.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```
  - **US‑CUST‑003 — <View Nurse profile>**  
  _Story:_ As a customer, I want to view detailed nurse profiles including qualifications, experience, ratings, and reviews, so that I can make an informed decision about which nurse to book. 
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑CUST‑004 — <Request Appointment Booking>**  
  _Story:_ As a customer, I want to request an appointment with a nurse for a specific date and time, so that I can schedule the care I need.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```
  - **US‑CUST‑005 — <View Booking Status>**  
  _Story:_ As a customer, I want to view the status of my booking requests and confirmed appointments, so that I can track my scheduled care and pending requests.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑CUST‑006 — <Create Job Listing>**  
  _Story:_  As a customer, I want to create a job listing describing my care needs, so that nurses can find and apply to opportunities that match their skills.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```
  - **US‑CUST‑007 — <Rate and Review Nurse>**  
  _Story:_ As a customer, I want to rate and review a nurse after a completed appointment, so that I can share my experience and help other customers make informed decisions.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
  ```

- **US‑CUST‑008 — <Cancel Appointment>**  
  _Story:_  As a customer, I want to cancel a confirmed appointment, so that I can adjust my schedule when plans change.
  _Acceptance:_
  ```gherkin
  Scenario: <happy path>
    Given <preconditions>
    When  <action>
    Then  <observable outcome>
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
- **Performance:** description 
- **Availability/Reliability:** description
- **Security/Privacy:** description
- **Usability:** description

---

## 4. Assumptions, Constraints, and Policies
- list any rules, policies, assumptions, etc.
- Assumptioons -Nurses are licensed and certified, both the customer provide accurate information
- Policies- users must be 18 or above, nurse must accept booking in 48 hours, users can only review nurse after a booking is complete
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
