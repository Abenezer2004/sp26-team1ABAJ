<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Sign Up - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<nav class="navbar">
  <div class="container navbar-content">
    <a class="logo" href="/">Nurse Search</a>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/provider/login">Provider Login</a></li>
      <li><a href="/customer/signup">Customer Sign Up</a></li>
    </ul>
  </div>
</nav>

<div class="container page" style="max-width: 820px;">
  <div class="card">
    <h1>Provider Account Creation</h1>
    <p class="text-muted" style="margin-bottom: 28px;">Create your nurse/provider profile. These fields connect directly to nurse search, customer bookings, listings, internship availability, and hours of operation.</p>

    <#if error??>
      <div class="alert alert-error">${error}</div>
    </#if>

    <form method="post" action="/provider/signup">
      <h2>Account Information</h2>

      <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
        <div class="form-group">
          <label>First Name *</label>
          <input type="text" name="firstName" required>
        </div>
        <div class="form-group">
          <label>Last Name *</label>
          <input type="text" name="lastName" required>
        </div>
      </div>

      <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
        <div class="form-group">
          <label>Email *</label>
          <input type="email" name="email" required>
        </div>
        <div class="form-group">
          <label>Phone *</label>
          <input type="tel" name="phone" required>
        </div>
      </div>

      <div class="form-group">
        <label>Password *</label>
        <input type="password" name="password" required>
      </div>

      <h2>Professional Details</h2>

      <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
        <div class="form-group">
          <label>Specialty *</label>
          <input type="text" name="specialty" required placeholder="Geriatric Care, Wound Care, etc.">
        </div>
        <div class="form-group">
          <label>Experience Level *</label>
          <input type="text" name="experienceLevel" required placeholder="Entry, Mid, Senior, 5+ years, etc.">
        </div>
      </div>

      <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
        <div class="form-group">
          <label>Hourly Rate *</label>
          <input type="number" step="0.01" name="hourlyRate" required>
        </div>
        <div class="form-group">
          <label>License Number</label>
          <input type="text" name="licenseNumber">
        </div>
      </div>

      <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
        <div class="form-group">
          <label>City</label>
          <input type="text" name="city" placeholder="Greensboro">
        </div>
        <div class="form-group">
          <label>Zip Code</label>
          <input type="text" name="zipCode" placeholder="27401">
        </div>
      </div>

      <div class="form-group">
        <label>Languages Spoken</label>
        <input type="text" name="languagesSpoken" placeholder="English, Spanish">
      </div>

      <div class="form-group">
        <label>Professional History / Bio</label>
        <textarea name="bio" placeholder="Describe your nursing history, skills, and care experience."></textarea>
      </div>

      <h2>Internship & Hours</h2>

      <div class="form-group">
        <label>Internships Available?</label>
        <select name="internshipAvailable">
          <option value="false">No</option>
          <option value="true">Yes</option>
        </select>
      </div>

      <div class="form-group">
        <label>Hours of Operation</label>
        <textarea name="hoursOfOperation" placeholder="Mon-Fri 8am-6pm, Saturday by appointment"></textarea>
      </div>

      <button type="submit" class="btn btn-primary btn-large">Create Provider Account</button>
    </form>
  </div>
</div>
</body>
</html>
