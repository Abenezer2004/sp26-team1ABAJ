<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Log In - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>
    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/customer/search">Find Nurses</a></li>
      <li><a href="/customer/signup">Sign Up</a></li>
    </ul>
  </div>
</nav>

<div class="container" style="max-width: 450px; margin-top: 80px; margin-bottom: 48px;">
  <div class="card">
    <h1 class="text-center">Welcome Back</h1>
    <p class="text-center" style="color: var(--text-secondary); margin-bottom: 32px;">
      Log in to access your account
    </p>

    <#if error??>
      <div style="background: rgba(214,64,69,.08); border: 1.5px solid rgba(214,64,69,.3);
                  color: #D64045; padding: 14px 18px; border-radius: 12px;
                  margin-bottom: 20px; font-weight: 600;">
        ⚠ ${error}
      </div>
    </#if>

    <form action="/customer/login" method="post">
      <div class="form-group">
        <label for="email">Email Address</label>
        <input type="email" id="email" name="email" required placeholder="your.email@example.com">
      </div>

      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" id="password" name="password" required placeholder="Enter your password">
      </div>

      <button type="submit" class="btn btn-primary btn-full btn-large">Log In</button>
    </form>

    <p class="text-center" style="margin-top: 24px; color: var(--text-secondary);">
      Don't have an account? <a href="/customer/signup" style="font-weight: 600;">Sign Up</a>
    </p>
  </div>
</div>

</body>
</html>
