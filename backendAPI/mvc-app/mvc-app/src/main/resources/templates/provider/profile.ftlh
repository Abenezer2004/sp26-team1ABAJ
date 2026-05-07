<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Profile - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>

    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/provider/dashboard">Dashboard</a></li>
      <li><a href="/provider/listings">Open Listings</a></li>
      <li><a href="/provider/profile" class="active">Profile</a></li>
      <li><a href="/provider/logout">Logout</a></li>
    </ul>
  </div>
</nav>

<div class="container page">

  <div style="display:flex; justify-content:space-between; align-items:center; gap:16px; flex-wrap:wrap; margin-bottom:28px;">

    <div>
      <h1>${nurse.firstName} ${nurse.lastName}</h1>

      <p>
        ${nurse.specialty!"General Nursing"}
      </p>
    </div>

    <a class="btn btn-primary"
       href="/provider/profile/edit">

      Update Profile
    </a>

  </div>

  <div class="card">

    <h2>Professional Information</h2>

    <p><strong>Email:</strong> ${nurse.email!""}</p>
    <p><strong>Phone:</strong> ${nurse.phone!""}</p>
    <p><strong>Specialty:</strong> ${nurse.specialty!"Not specified"}</p>
    <p><strong>Experience:</strong> ${nurse.experienceLevel!"Not specified"}</p>
    <p><strong>Hourly Rate:</strong> $${nurse.hourlyRate!0}</p>
    <p><strong>Languages:</strong> ${nurse.languagesSpoken!"Not specified"}</p>
    <p><strong>Hours:</strong> ${nurse.hoursOfOperation!"Not specified"}</p>

    <p>
      <strong>Internship Available:</strong>

      <#if nurse.internshipAvailable?? && nurse.internshipAvailable>
        Yes
      <#else>
        No
      </#if>
    </p>

    <p><strong>Bio:</strong></p>

    <p>${nurse.bio!"No bio provided."}</p>

  </div>

</div>

</body>
</html>