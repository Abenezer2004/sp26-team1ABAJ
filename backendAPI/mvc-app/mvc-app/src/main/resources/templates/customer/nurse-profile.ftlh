<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${nurse.firstName} ${nurse.lastName} - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
  <style>
    .profile-layout { display:grid; grid-template-columns:1fr 350px; gap:24px; margin:32px 0; }
    @media (max-width:968px) { .profile-layout { grid-template-columns:1fr; } }
    .review-card { background:var(--bg-subtle); padding:16px; border-radius:var(--radius-md); margin-bottom:16px; }
    .review-header { display:flex; justify-content:space-between; align-items:center; margin-bottom:8px; }
    .stars { color:#f59e0b; font-size:18px; }
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
      <#if customerName??><li><a href="/customer/profile">${customerName}</a></li></#if>
      <#if customerId??><li><a href="/customer/logout">Log Out</a></li></#if>
    </ul>
  </div>
</nav>

<div class="container">
  <a href="/customer/search" style="display:inline-flex;align-items:center;gap:8px;margin:24px 0;color:var(--text-secondary);">
    ← Back to Search
  </a>

  <div class="profile-layout">
    <main>

      <!-- Profile Header -->
      <div class="card">
        <div style="display:flex;gap:24px;align-items:flex-start;margin-bottom:24px;">
          <div class="nurse-avatar" style="width:100px;height:100px;font-size:40px;">
            ${nurse.firstName?substring(0,1)}${nurse.lastName?substring(0,1)}
          </div>
          <div style="flex:1;">
            <h1 style="margin-bottom:8px;">${nurse.firstName} ${nurse.lastName}</h1>
            <p style="font-size:18px;color:var(--text-secondary);margin-bottom:16px;">
              <#if nurse.specialty??>${nurse.specialty}</#if>
              <#if nurse.experienceLevel??> · ${nurse.experienceLevel} Level</#if>
            </p>
            <div style="display:flex;gap:16px;flex-wrap:wrap;">
              <#if nurse.reviewCount?? && nurse.reviewCount gt 0>
                <span style="font-weight:600;">⭐ ${nurse.averageRating} (${nurse.reviewCount} review<#if nurse.reviewCount != 1>s</#if>)</span>
              </#if>
              <span class="badge badge-success">Verified</span>
              <span class="badge badge-info">Available</span>
            </div>
          </div>
        </div>

        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(150px,1fr));gap:16px;padding:24px;background:var(--bg-subtle);border-radius:var(--radius-md);">
          <#if nurse.city??>
          <div>
            <div style="font-size:20px;font-weight:700;color:var(--primary);">${nurse.city}</div>
            <div style="color:var(--text-muted);font-size:14px;">Location</div>
          </div>
          </#if>
          <#if nurse.experienceLevel??>
          <div>
            <div style="font-size:20px;font-weight:700;color:var(--primary);">${nurse.experienceLevel}</div>
            <div style="color:var(--text-muted);font-size:14px;">Experience Level</div>
          </div>
          </#if>
          <#if nurse.licenseNumber??>
          <div>
            <div style="font-size:16px;font-weight:700;color:var(--primary);">${nurse.licenseNumber}</div>
            <div style="color:var(--text-muted);font-size:14px;">License #</div>
          </div>
          </#if>
        </div>
      </div>

      <!-- About -->
      <#if nurse.bio??>
      <div class="card">
        <h2>About ${nurse.firstName}</h2>
        <p>${nurse.bio}</p>
      </div>
      </#if>

      <!-- Specialties & Skills -->
      <div class="card">
        <h2>Specialties & Skills</h2>
        <div class="nurse-tags">
          <#if nurse.specialty??><span class="tag">${nurse.specialty}</span></#if>
          <#if nurse.experienceLevel??><span class="tag">${nurse.experienceLevel} Level</span></#if>
          <#if nurse.internshipAvailable?? && nurse.internshipAvailable>
            <span class="tag">Internship Available</span>
          </#if>
        </div>
      </div>

      <!-- Languages -->
      <#if nurse.languagesSpoken??>
      <div class="card">
        <h2>Languages</h2>
        <div style="display:flex;gap:12px;flex-wrap:wrap;">
          <#list nurse.languagesSpoken?split(",") as lang>
            <span class="badge badge-info">${lang?trim}</span>
          </#list>
        </div>
      </div>
      </#if>

      <!-- Hours of Operation -->
      <#if nurse.hoursOfOperation??>
      <div class="card">
        <h2>Hours of Operation</h2>
        <p>${nurse.hoursOfOperation}</p>
      </div>
      </#if>

      <!-- Reviews -->
      <div class="card">
        <h2>Patient Reviews <#if nurse.reviewCount?? && nurse.reviewCount gt 0>(${nurse.reviewCount})</#if></h2>
        <#if reviews?? && reviews?size gt 0>
          <p style="color:var(--text-secondary);margin-bottom:20px;">What patients are saying about ${nurse.firstName}.</p>
          <#list reviews as review>
          <div class="review-card">
            <div class="review-header">
              <span style="font-weight:600;">Patient</span>
              <#if review.rating??>
                <span class="stars">
                  <#list 1..5 as i><#if i <= review.rating>★<#else>☆</#if></#list>
                  <span style="font-size:14px;color:var(--text-muted);margin-left:4px;">${review.rating}/5</span>
                </span>
              </#if>
            </div>
            <#if review.comment??>
              <p style="margin:0;color:var(--text-primary);">${review.comment}</p>
            </#if>
            <#if review.replyText??>
              <div style="margin-top:12px;padding:10px;background:var(--bg-card);border-radius:var(--radius-sm);border-left:3px solid var(--primary);">
                <span style="font-weight:600;font-size:13px;color:var(--primary);">Nurse's Reply:</span>
                <p style="margin:4px 0 0;font-size:14px;">${review.replyText}</p>
              </div>
            </#if>
          </div>
          </#list>
        <#else>
          <p style="color:var(--text-muted);">No reviews yet. Be the first to review ${nurse.firstName} after your appointment!</p>
        </#if>
      </div>

    </main>

    <!-- Sidebar -->
    <aside>
      <div class="card" style="position:sticky;top:90px;">
        <#if nurse.hourlyRate??>
          <div class="nurse-rate" style="text-align:center;margin-bottom:24px;">
            $${nurse.hourlyRate}/hour
          </div>
        </#if>

        <#if customerId??>
          <a href="/customer/nurses/${nurse.userId}/book" class="btn btn-primary btn-full btn-large">
            Book Appointment
          </a>
        <#else>
          <a href="/customer/login" class="btn btn-primary btn-full btn-large">
            Log In to Book
          </a>
        </#if>

        <div style="margin:24px 0;text-align:center;color:var(--text-muted);">
          ─────  or  ─────
        </div>
        <button class="btn btn-secondary btn-full">Send Message</button>
      </div>
    </aside>
  </div>
</div>
</body>
</html>
