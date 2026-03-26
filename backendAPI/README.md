# Nurse Search - Backend API Documentation

**Version:** 1.0
**Team*:** Abenezer Shiferaw & Allen Holthz
**Class:** CSC 340 
**Last Updated:** March 20, 2026
**Base URL:** `http://localhost:8080/api`

---

## Table of Contents

1. [Overview](#1-overview)
2. [User Roles](#2-user-roles)
3. [UML Class Diagram](#3-uml-class-diagram)
4. [API Endpoints](#4-api-endpoints)
   - [Customer Management](#customer-management)
   - [Appointment Management](#farm-management)
   - [Listing Management](#produce-box-management)
   - [Review Management](#review-management)
5. [Use Case Mapping](#5-use-case-mapping)

---
## 1. Overview
Nurse Search connects patients and elderly individuals seeking in-home care with qualified nurses. Customers can search nurses by specialty, experience, language, and rate, then book appointments and leave reviews.

- **User Accounts**: Customers and Nurses both extend a shared User base with email, password, role, and status
- **Nurse Profiles**: Specialty, experience, hourly rate, languages spoken, internship availability, hours of operation, and average rating
- **Listings**: Customers post job listings describing their care needs — nurses can browse and filter them
- **Appointments**: Tracks the full lifecycle from PENDING → CONFIRMED → COMPLETED (or CANCELLED/DECLINED)
- **Reviews**: both the customer and nurse can optionally review each other after a completed appointment. Rating and comment are both optional.
---
## 2. User Roles
The API supports two primary user roles:

| Role | Description | Primary Actions |
|------|-------------|-----------------|
| **CUSTOMER** | Individual seeking in-home care | Search nurses, create listings, book appointments, write reviews, cancel appointments |
| **NURSE** | Licensed in-home caregiver | Update profile, browse listings, confirm/decline appointments, reply to reviews |

---
## 3. UML Class Diagram
![UML Class Diagram](../doc/UMLNursesearch%20(2).pdf)

## 4. API Endpoints
**Note:** Users are created through role-specific endpoints (`/customers`, `/Nurse`), not through a generic `/users` endpoint. This ensures proper role assignment and role-specific attributes.

### Customer Management


#### Create Customer
**Endpoint:** `POST /api/customers`
**Use Case:** US-CUST-001
**Description:** Register a new customer account.

**Request Body:**
```json
{
  "email": "maria@example.com",
  "passwordHash": "secret123",
  "firstName": "Maria",
  "lastName": "Santos",
  "phone": "555-1001",
  "role": "CUSTOMER",
  "status": "ACTIVE",
  "address": "123 Main St",
  "city": "Greensboro",
  "zipCode": "27401"
}
```


**Response:** `201 Created`
```json
{
  "userId": 1,
  "email": "maria@example.com",
  "firstName": "Maria",
  "lastName": "Santos",
  "phone": "555-1001",
  "role": "CUSTOMER",
  "status": "ACTIVE",
  "address": "123 Main St",
  "city": "Greensboro",
  "zipCode": "27401",
  "createdAt": "2026-03-23T10:00:00"
}
```
---

#### Get All Customers
**Endpoint:** `GET /api/customers`
**Description:** Retrieve all customer accounts.

**Response:** `200 OK` — array of customer objects

---

#### Get Customer by ID
**Endpoint:** `GET /api/customers/{id}`
**Description:** Retrieve a specific customer by their ID.

**Response:** `200 OK` or `404 Not Found`

---

#### Get Customer by Email
**Endpoint:** `GET /api/customers/email/{email}`
**Description:** Retrieve a customer by their email address.

**Response:** `200 OK` or `404 Not Found`

---


#### Update Customer
**Endpoint:** `PUT /api/customers/{id}`
**Use Case:** US-CUST-001
**Description:** Update customer profile. Only include fields you want to change.

**Request Body (partial update supported):**
```json
{
  "address": "456 New St",
  "city": "Durham",
  "notes": "Prefers morning visits"
}
```

**Response:** `200 OK` — updated customer object, or `404 Not Found`

---

#### Delete Customer
**Endpoint:** `DELETE /api/customers/{id}`
**Description:** Delete a customer account.

**Response:** `204 No Content` or `404 Not Found`

---


### Nurse Management
*Implemented by: Allen Holtz*

#### Create Nurse
**Endpoint:** `POST /api/nurses`
**Use Case:** US-PROV-001
**Description:** Register a new nurse account.

**Request Body:**
```json
{
  "email": "james@example.com",
  "passwordHash": "secret123",
  "firstName": "James",
  "lastName": "Walker",
  "phone": "555-2001",
  "role": "NURSE",
  "status": "ACTIVE",
  "specialty": "Geriatric Care",
  "experienceLevel": "Senior",
  "hourlyRate": 35.00,
  "city": "Greensboro",
  "languagesSpoken": "English, Spanish",
  "internshipAvailable": true,
  "hoursOfOperation": "Mon-Fri 8am-6pm"
}
```

**Response:** `201 Created` — nurse object with `averageRating: 0.0`

---

#### Get All Nurses
**Endpoint:** `GET /api/nurses`
**Use Case:** US-CUST-002
**Description:** Retrieve all nurse profiles.

**Response:** `200 OK` — array of nurse objects

---

#### Get Nurse by ID
**Endpoint:** `GET /api/nurses/{id}`
**Use Case:** US-CUST-003
**Description:** View a full nurse profile including average rating and review count.

**Response:** `200 OK` or `404 Not Found`

---

#### Get Nurse by Email
**Endpoint:** `GET /api/nurses/email/{email}`
**Description:** Retrieve a nurse by their email address.

**Response:** `200 OK` or `404 Not Found`

---

#### Search and Filter Nurses
**Endpoint:** `GET /api/nurses/search`
**Use Case:** US-CUST-002
**Description:** Search nurses using one or more optional filters.

**Query Parameters (all optional):**

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `specialty` | String | Filter by specialty | `Geriatric Care` |
| `minRate` | Double | Minimum hourly rate | `20` |
| `maxRate` | Double | Maximum hourly rate | `40` |
| `city` | String | Filter by city | `Greensboro` |
| `language` | String | Filter by language spoken | `Spanish` |

**Example requests:**
```
GET /api/nurses/search?specialty=Geriatric Care
GET /api/nurses/search?minRate=20&maxRate=40
GET /api/nurses/search?city=Greensboro&language=Spanish
GET /api/nurses/search?specialty=Wound Care&maxRate=35
```

**Response:** `200 OK` — array of matching nurse objects

---

#### Update Nurse
**Endpoint:** `PUT /api/nurses/{id}`
**Use Case:** US-PROV-001, US-PROV-003, US-PROV-004
**Description:** Update nurse profile, availability, or hours. Only include fields to change.

**Request Body (partial update supported):**
```json
{
  "bio": "10 years experience in geriatric home care",
  "licenseNumber": "RN-29384",
  "hourlyRate": 38.00,
  "internshipAvailable": false,
  "hoursOfOperation": "Mon-Sat 7am-8pm"
}
```

**Response:** `200 OK` — updated nurse object, or `404 Not Found`

---

#### Delete Nurse
**Endpoint:** `DELETE /api/nurses/{id}`
**Description:** Delete a nurse account.

**Response:** `204 No Content` or `404 Not Found`

---

### Listing Management
*Implemented by: Abenezer Shiferaw*

#### Create Listing
**Endpoint:** `POST /api/listings/customer/{customerId}`
**Use Case:** US-CUST-006
**Description:** Customer posts a job listing describing their care needs.

**Request Body:**
```json
{
  "specialtyNeeded": "Geriatric Care",
  "startDate": "2026-04-15",
  "durationDays": 30,
  "hourlyBudget": 40.00,
  "languageRequired": "Spanish",
  "additionalRequirements": "Must speak Spanish, morning availability preferred"
}
```

**Response:** `201 Created` — listing object with `status: "OPEN"`

---

#### Get All Listings
**Endpoint:** `GET /api/listings`
**Description:** Retrieve all listings.

**Response:** `200 OK` — array of listing objects

---

#### Get Listing by ID
**Endpoint:** `GET /api/listings/{id}`
**Description:** Retrieve a specific listing by ID.

**Response:** `200 OK` or `404 Not Found`

---

#### Get Open Listings
**Endpoint:** `GET /api/listings/open`
**Use Case:** US-PROV-002
**Description:** Retrieve all open listings. Nurses use this to browse available jobs. Supports optional filters.

**Query Parameters (all optional):**

| Parameter | Type | Description | Example |
|-----------|------|-------------|---------|
| `specialty` | String | Filter by specialty needed | `Wound Care` |
| `maxBudget` | Double | Filter by maximum hourly budget | `35` |

**Example requests:**
```
GET /api/listings/open
GET /api/listings/open?specialty=Geriatric Care
GET /api/listings/open?maxBudget=35
GET /api/listings/open?specialty=Wound Care&maxBudget=40
```

**Response:** `200 OK` — array of open listing objects

---

#### Get Listings by Customer
**Endpoint:** `GET /api/listings/customer/{customerId}`
**Description:** Retrieve all listings posted by a specific customer.

**Response:** `200 OK` — array of listing objects

---

#### Search Listings by Specialty
**Endpoint:** `GET /api/listings/search?specialty={specialty}`
**Description:** Search all listings by specialty needed.

**Response:** `200 OK` — array of matching listing objects

---

#### Update Listing
**Endpoint:** `PUT /api/listings/{id}`
**Description:** Update a listing. Supports partial updates.

**Request Body:**
```json
{
  "hourlyBudget": 45.00,
  "additionalRequirements": "Updated requirements",
  "status": "FILLED"
}
```

**Response:** `200 OK` — updated listing object, or `404 Not Found`

---

#### Delete Listing
**Endpoint:** `DELETE /api/listings/{id}`
**Description:** Delete a listing.

**Response:** `204 No Content` or `404 Not Found`

---

### Appointment Management
*Implemented by: Abenezer Shiferaw & Allen Holtz*

#### Create Appointment
**Endpoint:** `POST /api/appointments`
**Use Case:** US-CUST-004
**Description:** Customer requests a booking with a nurse. Status starts as PENDING. `listingId` is optional.

**Request Body:**
```json
{
  "customerId": 1,
  "nurseId": 2,
  "listingId": 1,
  "dateTime": "2026-04-15T09:00:00",
  "careDetails": "Daily medication management and mobility assistance"
}
```

**Response:** `201 Created`
```json
{
  "appointmentId": 1,
  "customer": { "userId": 1, "firstName": "Maria" },
  "nurse": { "userId": 2, "firstName": "James" },
  "dateTime": "2026-04-15T09:00:00",
  "careDetails": "Daily medication management and mobility assistance",
  "status": "PENDING",
  "reviewedByCustomer": false,
  "reviewedByNurse": false,
  "createdAt": "2026-03-23T10:00:00"
}
```

---

#### Get All Appointments
**Endpoint:** `GET /api/appointments`
**Description:** Retrieve all appointments in the system.

**Response:** `200 OK` — array of appointment objects

---

#### Get Appointment by ID
**Endpoint:** `GET /api/appointments/{id}`
**Description:** Retrieve a specific appointment by ID.

**Response:** `200 OK` or `404 Not Found`

---

#### Get Appointments by Customer
**Endpoint:** `GET /api/appointments/customer/{customerId}`
**Use Case:** US-CUST-005
**Description:** Retrieve all bookings for a customer.

**Response:** `200 OK` — array of appointments

---

#### Get Appointments by Customer and Status
**Endpoint:** `GET /api/appointments/customer/{customerId}/status/{status}`
**Use Case:** US-CUST-005
**Description:** Retrieve customer bookings filtered by status.

**Valid status values:** `PENDING`, `CONFIRMED`, `COMPLETED`, `CANCELLED`, `DECLINED`

**Example requests:**
```
GET /api/appointments/customer/1/status/PENDING
GET /api/appointments/customer/1/status/COMPLETED
GET /api/appointments/customer/1/status/CANCELLED
```

**Response:** `200 OK` — filtered array of appointments

---

#### Get Appointments by Nurse
**Endpoint:** `GET /api/appointments/nurse/{nurseId}`
**Description:** Retrieve all appointments for a nurse.

**Response:** `200 OK` — array of appointments

---

#### Cancel Appointment
**Endpoint:** `PUT /api/appointments/{id}/cancel`
**Use Case:** US-CUST-008
**Description:** Customer cancels an appointment. Sets status to CANCELLED.

**Response:** `200 OK` — updated appointment with `status: "CANCELLED"`, or `404 Not Found`

---

#### Update Appointment Status
**Endpoint:** `PUT /api/appointments/{id}/status`
**Description:** Update appointment status. Used by nurse to confirm, decline, or complete.

**Request Body:**
```json
{ "status": "CONFIRMED" }
```

**Valid transitions:**
- Nurse confirms: `PENDING` → `CONFIRMED`
- Nurse declines: `PENDING` → `DECLINED`
- Nurse completes: `CONFIRMED` → `COMPLETED`

**Response:** `200 OK` — updated appointment object, or `400 Bad Request` for invalid status value

---

#### Delete Appointment
**Endpoint:** `DELETE /api/appointments/{id}`
**Description:** Delete an appointment record.

**Response:** `204 No Content`

---

### Review Management
*Implemented by: Abenezer Shiferaw & Allen Holtz*

#### Create Review
**Endpoint:** `POST /api/reviews`
**Use Case:** US-CUST-007
**Description:** Submit a review after a completed appointment. Both customer and nurse can review each other. Rating and comment are both optional — but at least one must be provided.

**Validation rules enforced:**
- Appointment must have status `COMPLETED`
- `submitterId` must match the customer or nurse on that appointment
- Each party can only submit one review per appointment
- Rating must be between 1 and 5 if provided

**Request Body:**
```json
{
  "submitterId": 1,
  "appointmentId": 1,
  "rating": 5,
  "comment": "James was incredibly professional and caring!"
}
```

**Rating only (no comment):**
```json
{
  "submitterId": 1,
  "appointmentId": 1,
  "rating": 5
}
```

**Comment only (no rating):**
```json
{
  "submitterId": 1,
  "appointmentId": 1,
  "comment": "Great experience overall"
}
```

**Response:** `201 Created` — review object. If a rating was included, the nurse's `averageRating` and `reviewCount` update automatically.

**Error responses:**
- `400 Bad Request` — appointment not completed, submitter not part of appointment, or already reviewed

---

#### Get Reviews for a Nurse
**Endpoint:** `GET /api/reviews/nurse/{nurseId}`
**Use Case:** US-CUST-003
**Description:** Retrieve all customer reviews written about a nurse. Used to display ratings on a nurse's profile.

**Response:** `200 OK` — array of review objects

---

#### Get Reviews about a Customer
**Endpoint:** `GET /api/reviews/customer/{customerId}`
**Description:** Retrieve all nurse reviews written about a customer.

**Response:** `200 OK` — array of review objects

---

#### Get Reviews by Appointment
**Endpoint:** `GET /api/reviews/appointment/{appointmentId}`
**Description:** Retrieve both reviews (customer and nurse) for a single appointment. Returns up to 2 reviews.

**Response:** `200 OK` — array of up to 2 review objects

---

#### Get Review by ID
**Endpoint:** `GET /api/reviews/{id}`
**Description:** Retrieve a specific review by ID.

**Response:** `200 OK` or `404 Not Found`

---

#### Add Nurse Reply
**Endpoint:** `PUT /api/reviews/{id}/reply`
**Description:** Nurse replies to a customer review. Can only reply to reviews where `reviewedBy` is `CUSTOMER`.

**Request Body:**
```json
{
  "replyText": "Thank you for the kind words! It was a pleasure caring for you."
}
```

**Response:** `200 OK` — updated review with `replyText` set, or `400 Bad Request`

---

#### Delete Review
**Endpoint:** `DELETE /api/reviews/{id}`
**Description:** Delete a review. Used for admin moderation.

**Response:** `204 No Content` or `404 Not Found`

---

## 5. Use Case Mapping

### Customer Use Cases

| Use Case | Description | Endpoints |
|----------|-------------|-----------|
| **US-CUST-001** | Register and update customer profile | `POST /api/customers` · `PUT /api/customers/{id}` |
| **US-CUST-002** | Search and filter nurses by specialty, rate, city, language | `GET /api/nurses/search` · `GET /api/nurses` |
| **US-CUST-003** | View nurse profile with ratings and reviews | `GET /api/nurses/{id}` · `GET /api/reviews/nurse/{nurseId}` |
| **US-CUST-004** | Request an appointment booking | `POST /api/appointments` |
| **US-CUST-005** | View booking status (all and filtered) | `GET /api/appointments/customer/{id}` · `GET /api/appointments/customer/{id}/status/{status}` |
| **US-CUST-006** | Create a job listing | `POST /api/listings/customer/{customerId}` |
| **US-CUST-007** | Rate and review nurse after completed appointment | `POST /api/reviews` |
| **US-CUST-008** | Cancel a confirmed appointment | `PUT /api/appointments/{id}/cancel` |

### Provider (Nurse) Use Cases

| Use Case | Description | Endpoints |
|----------|-------------|-----------|
| **US-PROV-001** | Register and update nursing profile and history | `POST /api/nurses` · `PUT /api/nurses/{id}` |
| **US-PROV-002** | Browse and filter open customer listings | `GET /api/listings/open` · `GET /api/listings/open?specialty=X&maxBudget=Y` |
| **US-PROV-003** | Set internship availability | `PUT /api/nurses/{id}` with `internshipAvailable` field |
| **US-PROV-004** | Update hours of operation | `PUT /api/nurses/{id}` with `hoursOfOperation` field |
