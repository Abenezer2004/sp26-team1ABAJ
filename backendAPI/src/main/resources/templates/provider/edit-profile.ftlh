<#import "_nav.ftlh" as layout>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Edit Provider Profile - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<@layout.providerNav active="profile" />

<div class="container page" style="max-width: 860px;">
  <div class="card">
    <h1>Edit Provider Profile</h1>
    <p class="text-muted" style="margin-bottom: 28px;">Update the fields customers use to find and evaluate your profile.</p>

    <#if nurse??>
      <form method="post" action="/provider/profile/edit">
        <h2>Personal Information</h2>
        <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
          <div class="form-group">
            <label>First Name</label>
            <input type="text" name="firstName" value="${nurse.firstName!}">
          </div>
          <div class="form-group">
            <label>Last Name</label>
            <input type="text" name="lastName" value="${nurse.lastName!}">
          </div>
        </div>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
          <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" value="${nurse.email!}">
          </div>
          <div class="form-group">
            <label>Phone</label>
            <input type="tel" name="phone" value="${nurse.phone!}">
          </div>
        </div>

        <h2>Professional Details</h2>
        <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
          <div class="form-group">
            <label>Specialty</label>
            <input type="text" name="specialty" value="${nurse.specialty!}">
          </div>
          <div class="form-group">
            <label>Experience Level</label>
            <input type="text" name="experienceLevel" value="${nurse.experienceLevel!}">
          </div>
        </div>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
          <div class="form-group">
            <label>Hourly Rate</label>
            <input type="number" step="0.01" name="hourlyRate" value="${nurse.hourlyRate!}">
          </div>
          <div class="form-group">
            <label>License Number</label>
            <input type="text" name="licenseNumber" value="${nurse.licenseNumber!}">
          </div>
        </div>

        <div style="display:grid; grid-template-columns: 1fr 1fr; gap:16px;">
          <div class="form-group">
            <label>City</label>
            <input type="text" name="city" value="${nurse.city!}">
          </div>
          <div class="form-group">
            <label>Zip Code</label>
            <input type="text" name="zipCode" value="${nurse.zipCode!}">
          </div>
        </div>

        <div class="form-group">
          <label>Languages Spoken</label>
          <input type="text" name="languagesSpoken" value="${nurse.languagesSpoken!}">
        </div>

        <div class="form-group">
          <label>Bio / Professional History</label>
          <textarea name="bio">${nurse.bio!}</textarea>
        </div>

        <h2>Provider Use Cases</h2>
        <div class="form-group">
          <label>Internships Available?</label>
          <select name="internshipAvailable">
            <option value="false" <#if !(nurse.internshipAvailable!false)>selected</#if>>No</option>
            <option value="true" <#if nurse.internshipAvailable!false>selected</#if>>Yes</option>
          </select>
        </div>

        <div class="form-group">
          <label>Hours of Operation</label>
          <textarea name="hoursOfOperation">${nurse.hoursOfOperation!}</textarea>
        </div>

        <div style="display:flex; gap:12px;">
          <button class="btn btn-primary" type="submit">Save Changes</button>
          <a class="btn btn-secondary" href="/provider/profile">Cancel</a>
        </div>
      </form>
    <#else>
      <p>Profile could not be loaded.</p>
    </#if>
  </div>
</div>
</body>
</html>
