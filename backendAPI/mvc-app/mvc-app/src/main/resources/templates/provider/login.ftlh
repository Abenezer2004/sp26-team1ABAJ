<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Login - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<nav class="navbar">
  <div class="container navbar-content">
    <a class="logo" href="/">Nurse Search</a>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/provider/signup">Provider Sign Up</a></li>
      <li><a href="/customer/login">Customer Login</a></li>
    </ul>
  </div>
</nav>

<div class="container page" style="max-width: 460px;">
  <div class="card">
    <h1 class="text-center">Provider Login</h1>
    <p class="text-center text-muted" style="margin-bottom: 28px;">Log in to manage listings, appointments, and your provider profile.</p>

    <#if error??>
      <div class="alert alert-error">${error}</div>
    </#if>

    <form method="post" action="/provider/login">
      <div class="form-group">
        <label>Email Address</label>
        <input type="email" name="email" required placeholder="nurse@example.com">
      </div>

      <div class="form-group">
        <label>Password</label>
        <input type="password" name="password" required placeholder="Enter your password">
      </div>

      <button type="submit" class="btn btn-primary btn-full btn-large">Log In</button>
    </form>

    <p class="text-center" style="margin-top: 24px;">
      Need a provider account? <a href="/provider/signup">Sign up here</a>
    </p>
  </div>
</div>
</body>
</html>
