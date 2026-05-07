<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Book Appointment - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
  <style>
    .booking-layout {
      display: grid;
      grid-template-columns: 1fr 340px;
      gap: 24px;
      margin: 32px 0;
    }
    @media (max-width: 968px) { .booking-layout { grid-template-columns: 1fr; } }
    .form-row { display: grid; grid-template-columns: 1fr 1fr; gap: 16px; }
    @media (max-width: 600px) { .form-row { grid-template-columns: 1fr; } }
    .star-rating { display: flex; gap: 4px; margin-bottom: 8px; }
  </style>
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/customer/search">Find Nurses</a></li>
      <li><a href="/customer/dashboard">My Bookings</a></li>
      <li><a href="/customer/profile">${customerName!''}</a></li>
      <li><a href="/customer/logout">Log Out</a></li>
    </ul>
  </div>
</nav>

<div class="container">
  <a href="/customer/nurses/${nurse.userId}" style="display:inline-flex;align-items:center;gap:8px;margin:24px 0;color:var(--text-secondary);">
    ← Back to ${nurse.firstName}'s Profile
  </a>

  <div class="booking-layout">
    <main>
      <div class="card">
        <h1 style="margin-bottom: 8px;">Request Appointment</h1>
        <p style="color:var(--text-secondary);margin-bottom:28px;">Fill in your care details and preferred time below.</p>

        <#if error??>
          <div class="alert alert-error" style="margin-bottom:20px;">${error}</div>
        </#if>

        <form method="post" action="/customer/nurses/${nurse.userId}/book">

          <!-- Date & Time -->
          <div class="form-group">
            <label class="form-label">Preferred Date & Time *</label>
            <input type="datetime-local" name="dateTime" class="form-control" required
                   style="font-family:var(--font-body);"
                   min="2026-01-01T00:00">
            <small style="color:var(--text-muted);font-size:13px;">Select a date and time that works for you. The nurse must confirm.</small>
          </div>

          <!-- Care Details -->
          <div class="form-group">
            <label class="form-label">Describe Your Care Needs *</label>
            <textarea name="careDetails" class="form-control" rows="5" required
                      placeholder="e.g. Post-surgery wound care, medication reminders, mobility assistance..."></textarea>
          </div>

          <!-- Link to Listing (optional) -->
          <#if listings?? && listings?size gt 0>
          <div class="form-group">
            <label class="form-label">Link to a Job Listing (optional)</label>
            <select name="listingId" class="form-control">
              <option value="">-- None --</option>
              <#list listings as listing>
                <option value="${listing.listingId}">${listing.specialtyNeeded} – posted ${listing.startDate!''}</option>
              </#list>
            </select>
          </div>
          </#if>

          <div style="display:flex;gap:12px;margin-top:8px;">
            <button type="submit" class="btn btn-primary btn-large">Send Booking Request</button>
            <a href="/customer/nurses/${nurse.userId}" class="btn btn-secondary btn-large">Cancel</a>
          </div>
        </form>
      </div>
    </main>

    <!-- Sidebar nurse summary -->
    <aside>
      <div class="card" style="position:sticky;top:90px;">
        <div style="display:flex;gap:16px;align-items:center;margin-bottom:20px;">
          <div class="nurse-avatar" style="width:64px;height:64px;font-size:24px;">
            ${nurse.firstName?substring(0,1)}${nurse.lastName?substring(0,1)}
          </div>
          <div>
            <h3 style="margin:0 0 4px;">${nurse.firstName} ${nurse.lastName}</h3>
            <div style="color:var(--text-secondary);font-size:14px;">${nurse.specialty!''}</div>
          </div>
        </div>

        <#if nurse.hourlyRate??>
          <div style="text-align:center;margin-bottom:16px;">
            <span class="nurse-rate">$${nurse.hourlyRate}/hour</span>
          </div>
        </#if>

        <div style="background:var(--bg-subtle);border-radius:var(--radius-md);padding:16px;font-size:14px;">
          <div style="display:flex;justify-content:space-between;padding:6px 0;">
            <span style="color:var(--text-muted);">Specialty</span>
            <span style="font-weight:600;">${nurse.specialty!'-'}</span>
          </div>
          <div style="display:flex;justify-content:space-between;padding:6px 0;">
            <span style="color:var(--text-muted);">Experience</span>
            <span style="font-weight:600;">${nurse.experienceLevel!'-'}</span>
          </div>
          <#if nurse.city??>
          <div style="display:flex;justify-content:space-between;padding:6px 0;">
            <span style="color:var(--text-muted);">City</span>
            <span style="font-weight:600;">${nurse.city}</span>
          </div>
          </#if>
          <#if nurse.reviewCount?? && nurse.reviewCount gt 0>
          <div style="display:flex;justify-content:space-between;padding:6px 0;">
            <span style="color:var(--text-muted);">Rating</span>
            <span style="font-weight:600;">⭐ ${nurse.averageRating} (${nurse.reviewCount})</span>
          </div>
          </#if>
        </div>

        <p style="font-size:13px;color:var(--text-muted);margin-top:16px;text-align:center;">
          Your request will be <strong>Pending</strong> until the nurse confirms.
        </p>
      </div>
    </aside>
  </div>
</div>
</body>
</html>
