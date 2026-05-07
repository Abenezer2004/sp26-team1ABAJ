<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Write a Review - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
  <style>
    .star-row { display:flex; gap:8px; margin:12px 0; }
    .star-btn {
      font-size:36px; background:none; border:none; cursor:pointer;
      color: var(--border); transition:color 0.15s;
    }
    .star-btn.selected, .star-btn:hover { color: #f59e0b; }
  </style>
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/customer/search">Find Nurses</a></li>
      <li><a href="/customer/dashboard" class="active">My Bookings</a></li>
      <li><a href="/customer/profile">${customerName!''}</a></li>
      <li><a href="/customer/logout">Log Out</a></li>
    </ul>
  </div>
</nav>

<div class="container" style="max-width:640px;margin:40px auto;">
  <a href="/customer/dashboard" style="display:inline-flex;align-items:center;gap:8px;margin-bottom:24px;color:var(--text-secondary);">
    ← Back to My Bookings
  </a>

  <div class="card">
    <h1 style="margin-bottom:8px;">Write a Review</h1>

    <#if appointment??>
    <div style="display:flex;gap:16px;align-items:center;padding:16px;background:var(--bg-subtle);border-radius:var(--radius-md);margin-bottom:24px;">
      <div class="nurse-avatar">
        ${appointment.nurse.firstName?substring(0,1)}${appointment.nurse.lastName?substring(0,1)}
      </div>
      <div>
        <div style="font-weight:700;font-size:17px;">${appointment.nurse.firstName} ${appointment.nurse.lastName}</div>
        <div style="color:var(--text-secondary);font-size:14px;">${appointment.nurse.specialty!''}</div>
        <#if appointment.dateTime??>
          <div style="color:var(--text-muted);font-size:13px;">Appointment: ${appointment.dateTime}</div>
        </#if>
      </div>
    </div>
    </#if>

    <form method="post" action="/customer/appointments/${appointment.appointmentId}/review">

      <!-- Star Rating -->
      <div class="form-group">
        <label class="form-label">Your Rating *</label>
        <div class="star-row" id="starRow">
          <button type="button" class="star-btn" data-val="1" onclick="setRating(1)">★</button>
          <button type="button" class="star-btn" data-val="2" onclick="setRating(2)">★</button>
          <button type="button" class="star-btn" data-val="3" onclick="setRating(3)">★</button>
          <button type="button" class="star-btn" data-val="4" onclick="setRating(4)">★</button>
          <button type="button" class="star-btn" data-val="5" onclick="setRating(5)">★</button>
        </div>
        <input type="hidden" name="rating" id="ratingInput" required>
        <div id="ratingLabel" style="color:var(--text-muted);font-size:14px;">Click to select a rating</div>
      </div>

      <!-- Review Text -->
      <div class="form-group">
        <label class="form-label">Your Review</label>
        <textarea name="comment" class="form-control" rows="5"
                  placeholder="Share your experience — how was the care, professionalism, and communication?"></textarea>
      </div>

      <div style="display:flex;gap:12px;">
        <button type="submit" class="btn btn-primary btn-large" id="submitBtn" disabled>Submit Review</button>
        <a href="/customer/dashboard" class="btn btn-secondary btn-large">Cancel</a>
      </div>
    </form>
  </div>
</div>

<script>
  const labels = ['', 'Poor', 'Fair', 'Good', 'Very Good', 'Excellent'];
  function setRating(val) {
    document.getElementById('ratingInput').value = val;
    document.getElementById('ratingLabel').textContent = val + ' star' + (val > 1 ? 's' : '') + ' — ' + labels[val];
    document.getElementById('submitBtn').disabled = false;
    document.querySelectorAll('.star-btn').forEach(btn => {
      btn.classList.toggle('selected', parseInt(btn.dataset.val) <= val);
    });
  }
</script>
</body>
</html>
