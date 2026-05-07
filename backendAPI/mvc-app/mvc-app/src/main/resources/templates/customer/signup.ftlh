<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Sign Up - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
  <nav class="navbar">
    <div class="container navbar-content">
      <div class="logo">Nurse Search</div>
      <ul class="nav-links">
        <li><a href="/">Home</a></li>
        <li><a href="/customer/search">Find Nurses</a></li>
        <li><a href="/customer/signup" class="active">Sign Up</a></li>
      </ul>
    </div>
  </nav>

  <div class="container" style="max-width: 500px; margin-top: 48px; margin-bottom: 48px;">
    <div class="card">
      <h1 class="text-center">Create Your Account</h1>
      <p class="text-center" style="color: var(--text-secondary); margin-bottom: 32px;">Join Nurse Search</p>

      <#if error??>
        <div style="background: rgba(214,64,69,.08); border: 1.5px solid rgba(214,64,69,.3);
                    color: #D64045; padding: 14px 18px; border-radius: 12px;
                    margin-bottom: 20px; font-weight: 600;">
          ⚠ ${error}
        </div>
      </#if>

      <form action="/customer/signup" method="post">

        <div class="form-group">
          <label for="firstName">First Name *</label>
          <input type="text" id="firstName" name="firstName"
                 value="${(customer.firstName)!''}" required placeholder="Ab">
        </div>

        <div class="form-group">
          <label for="lastName">Last Name *</label>
          <input type="text" id="lastName" name="lastName"
                 value="${(customer.lastName)!''}" required placeholder="John">
        </div>

        <div class="form-group">
          <label for="email">Email Address *</label>
          <input type="email" id="email" name="email"
                 value="${(customer.email)!''}" required placeholder="ab.john@example.com">
        </div>

        <div class="form-group">
          <label for="phone">Phone Number *</label>
          <input type="tel" id="phone" name="phone"
                 value="${(customer.phone)!''}" required placeholder="(111) 123-4567">
        </div>

        <div class="form-group">
          <label for="passwordHash">Password *</label>
          <input type="password" id="passwordHash" name="passwordHash"
                 required placeholder="strong password">
        </div>

        <div class="form-group">
          <label for="preferredLanguage">Preferred Language</label>
          <select id="preferredLanguage" name="preferredLanguage">
            <option value="English">English</option>
            <option value="Spanish">Spanish</option>
            <option value="French">French</option>
            <option value="Mandarin">Mandarin</option>
            <option value="Amharic">Amharic</option>
            <option value="Other">Other</option>
          </select>
        </div>

        <!-- Hidden fields — set automatically -->
        <input type="hidden" name="role" value="CUSTOMER">
        <input type="hidden" name="status" value="ACTIVE">

        <button type="submit" class="btn btn-primary btn-full btn-large">Create Account</button>
      </form>

      <p class="text-center" style="margin-top: 24px; color: var(--text-secondary);">
        Already have an account? <a href="/" style="font-weight: 600;">Find a Nurse</a>
      </p>
    </div>
  </div>

</body>
</html>
