<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Edit Provider Profile - Nurse Search</title>
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

  <div style="margin-bottom:28px;">
    <h1>Edit Nurse Profile</h1>
    <p>Update your provider information.</p>
  </div>

  <div class="card">

    <form method="post" action="/provider/profile/edit">

      <div class="grid grid-2">

        <div class="form-group">
          <label>First Name</label>
          <input type="text"
                 name="firstName"
                 value="${nurse.firstName!""}">
        </div>

        <div class="form-group">
          <label>Last Name</label>
          <input type="text"
                 name="lastName"
                 value="${nurse.lastName!""}">
        </div>

      </div>

      <div class="form-group">
        <label>Phone</label>
        <input type="text"
               name="phone"
               value="${nurse.phone!""}">
      </div>

      <div class="form-group">
        <label>Specialty</label>
        <input type="text"
               name="specialty"
               value="${nurse.specialty!""}">
      </div>

      <div class="form-group">
        <label>Experience Level</label>
        <input type="text"
               name="experienceLevel"
               value="${nurse.experienceLevel!""}">
      </div>

      <div class="form-group">
        <label>Hourly Rate</label>
        <input type="number"
               step="0.01"
               name="hourlyRate"
               value="${nurse.hourlyRate!0}">
      </div>

      <div class="form-group">
        <label>Languages Spoken</label>
        <input type="text"
               name="languagesSpoken"
               value="${nurse.languagesSpoken!""}">
      </div>

      <div class="form-group">
        <label>Hours of Operation</label>
        <input type="text"
               name="hoursOfOperation"
               value="${nurse.hoursOfOperation!""}">
      </div>

      <div class="form-group">
        <label>Bio</label>

        <textarea name="bio"
                  rows="6">${nurse.bio!""}</textarea>
      </div>

      <div style="display:flex; gap:12px; flex-wrap:wrap;">

        <button type="submit"
                class="btn btn-primary">

          Save Changes
        </button>

        <a href="/provider/profile"
           class="btn btn-secondary">

          Cancel
        </a>

      </div>

    </form>

  </div>

</div>

</body>
</html>