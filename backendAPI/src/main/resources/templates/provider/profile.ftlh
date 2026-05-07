<#import "_nav.ftlh" as layout>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Profile - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<@layout.providerNav active="profile" />

<div class="container page" style="max-width: 900px;">
  <div class="row-between" style="margin-bottom: 28px;">
    <h1>My Provider Profile</h1>
    <a class="btn btn-primary" href="/provider/profile/edit">Edit Profile</a>
  </div>

  <#if successMsg??><div class="alert alert-success">${successMsg}</div></#if>
  <#if errorMsg??><div class="alert alert-error">${errorMsg}</div></#if>

  <#if nurse??>
    <div class="card">
      <h2>${nurse.firstName} ${nurse.lastName}</h2>
      <p>${nurse.specialty!"No specialty listed"} · ${nurse.experienceLevel!"No experience listed"}</p>
      <div class="stat-grid">
        <div class="stat-box"><div class="stat-value">$${nurse.hourlyRate!0}</div><div>Hourly Rate</div></div>
        <div class="stat-box"><div class="stat-value">${nurse.averageRating!0}</div><div>Average Rating</div></div>
        <div class="stat-box"><div class="stat-value">${nurse.reviewCount!0}</div><div>Reviews</div></div>
      </div>
    </div>

    <div class="card">
      <h2>Contact & Location</h2>
      <p><strong>Email:</strong> ${nurse.email}</p>
      <p><strong>Phone:</strong> ${nurse.phone}</p>
      <p><strong>City:</strong> ${nurse.city!"Not listed"}</p>
      <p><strong>Zip Code:</strong> ${nurse.zipCode!"Not listed"}</p>
    </div>

    <div class="card">
      <h2>Professional History</h2>
      <p>${nurse.bio!"No professional history added yet."}</p>
      <p><strong>License Number:</strong> ${nurse.licenseNumber!"Not listed"}</p>
      <p><strong>Languages Spoken:</strong> ${nurse.languagesSpoken!"Not listed"}</p>
    </div>

    <div class="card">
      <h2>Availability</h2>
      <p><strong>Internships Available:</strong> ${(nurse.internshipAvailable!false)?then("Yes", "No")}</p>
      <p><strong>Hours of Operation:</strong> ${nurse.hoursOfOperation!"Not listed"}</p>
    </div>
  <#else>
    <div class="card"><p>Provider profile could not be loaded.</p></div>
  </#if>
</div>
</body>
</html>
