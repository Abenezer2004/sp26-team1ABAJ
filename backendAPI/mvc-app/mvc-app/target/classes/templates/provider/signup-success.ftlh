<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Signup Success - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<div class="container page" style="max-width: 620px;">
  <div class="card text-center">
    <h1>Welcome, ${name}!</h1>
    <p>Your provider account was created successfully.</p>
    <p class="text-muted">Provider ID: ${nurseId}</p>
    <div style="margin-top: 24px;">
      <a class="btn btn-primary" href="/provider/dashboard">Go to Dashboard</a>
      <a class="btn btn-secondary" href="/provider/profile">View Profile</a>
    </div>
  </div>
</div>
</body>
</html>
