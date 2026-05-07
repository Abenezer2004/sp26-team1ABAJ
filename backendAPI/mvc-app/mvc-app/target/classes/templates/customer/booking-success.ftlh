<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Booking Request Sent - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/customer/search">Find Nurses</a></li>
      <li><a href="/customer/dashboard">My Bookings</a></li>
      <li><a href="/customer/logout">Log Out</a></li>
    </ul>
  </div>
</nav>

<div class="container" style="max-width:600px; margin:80px auto;">
  <div class="card" style="text-align:center;">

    <div style="font-size:72px; margin-bottom:16px;">📅</div>
    <h1 style="color:var(--success); margin-bottom:12px;">Booking Request Sent!</h1>
    <p style="font-size:18px; margin-bottom:28px;">
      Your appointment is now <strong>Pending</strong>.<br>
      The nurse will confirm within 48 hours.
    </p>

    <#if appointment??>
    <div style="background:var(--bg-subtle); border-radius:var(--radius-md); padding:20px;
                margin-bottom:28px; text-align:left;">

      <div style="margin-bottom:12px;">
        <div style="color:var(--text-muted); font-size:13px; margin-bottom:4px;">Nurse</div>
        <div style="font-weight:600; font-size:16px;">
          <#if appointment.nurse??>${appointment.nurse.firstName!''} ${appointment.nurse.lastName!''}</#if>
        </div>
      </div>

      <div style="margin-bottom:12px;">
        <div style="color:var(--text-muted); font-size:13px; margin-bottom:4px;">Status</div>
        <span class="badge badge-warning">Pending — awaiting nurse confirmation</span>
      </div>

      <#if appointment.careDetails?? && appointment.careDetails != "">
      <div style="margin-bottom:12px;">
        <div style="color:var(--text-muted); font-size:13px; margin-bottom:4px;">Care Details</div>
        <div>${appointment.careDetails}</div>
      </div>
      </#if>

    </div>
    </#if>

    <div style="display:flex; gap:12px; justify-content:center; flex-wrap:wrap;">
      <a href="/customer/dashboard" class="btn btn-primary">View My Bookings</a>
      <a href="/customer/search" class="btn btn-secondary">Find More Nurses</a>
    </div>

  </div>
</div>
</body>
</html>
