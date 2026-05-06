<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Listing Published - Nurse Search</title>
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

  <div class="container" style="max-width: 560px; margin-top: 64px; margin-bottom: 64px;">
    <div class="card" style="text-align:center;">

      <div style="width: 72px; height: 72px;
                  background: linear-gradient(135deg, #2D9E6B, #3dbf82);
                  color: #fff; border-radius: 50%;
                  display: flex; align-items: center; justify-content: center;
                  font-size: 2rem; font-weight: 800; margin: 0 auto 24px;">
        ✓
      </div>

      <h1>Listing Published!</h1>
      <p style="font-size: 1.05rem; margin: 12px 0 32px; color: var(--text-secondary);">
        Your job listing is now live. Qualified nurses will be able to see and apply for it.
      </p>

      <a href="/customer/dashboard" class="btn btn-primary btn-large btn-full">
        Go to My Bookings
      </a>

      <a href="/customer/listing/new" class="btn btn-secondary btn-full" style="margin-top: 12px;">
        Post Another Listing
      </a>

    </div>
  </div>

</body>
</html>
