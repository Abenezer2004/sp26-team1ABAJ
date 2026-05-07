<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Register as Nurse - Nurse Search</title>
    <link rel="stylesheet" href="/styles.css">
</head>
<body>

<nav class="navbar">
    <div class="container navbar-content">
        <div class="logo">Nurse Search</div>
        <ul class="nav-links">
            <li><a href="/">Home</a></li>
            <li><a href="/provider/login">Nurse Login</a></li>
            <li><a href="/customer/signup">Customer Signup</a></li>
        </ul>
    </div>
</nav>

<div class="container" style="max-width: 760px; margin-top: 48px; margin-bottom: 48px;">
    <div class="card">
        <h1 class="text-center">Register as a Nurse</h1>
        <p class="text-center text-muted mb-16">Create your provider account and nursing profile.</p>

        <#if error??>
            <div class="card" style="border-color: var(--danger);">
                <p style="color: var(--danger); margin: 0;">${error}</p>
            </div>
        </#if>

        <form method="post" action="/provider/signup">

            <h2>Account Information</h2>

            <div class="grid grid-2">
                <div class="form-group">
                    <label for="firstName">First Name *</label>
                    <input type="text" id="firstName" name="firstName" required>
                </div>

                <div class="form-group">
                    <label for="lastName">Last Name *</label>
                    <input type="text" id="lastName" name="lastName" required>
                </div>
            </div>

            <div class="form-group">
                <label for="email">Email Address *</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="phone">Phone Number *</label>
                <input type="tel" id="phone" name="phone" required>
            </div>

            <div class="form-group">
                <label for="password">Password *</label>
                <input type="password" id="password" name="password" required>
            </div>

            <h2>Professional Information</h2>

            <div class="form-group">
                <label for="licenseNumber">Nursing License Number</label>
                <input type="text" id="licenseNumber" name="licenseNumber">
            </div>

            <div class="grid grid-2">
                <div class="form-group">
                    <label for="specialty">Specialty</label>
                    <input type="text" id="specialty" name="specialty" placeholder="Geriatric Care, Wound Care, etc.">
                </div>

                <div class="form-group">
                    <label for="experienceLevel">Experience Level</label>
                    <input type="text" id="experienceLevel" name="experienceLevel" placeholder="1-3 years, Senior, etc.">
                </div>
            </div>

            <div class="grid grid-2">
                <div class="form-group">
                    <label for="hourlyRate">Hourly Rate</label>
                    <input type="number" step="0.01" id="hourlyRate" name="hourlyRate">
                </div>

                <div class="form-group">
                    <label for="city">City</label>
                    <input type="text" id="city" name="city">
                </div>
            </div>

            <div class="form-group">
                <label for="zipCode">Zip Code</label>
                <input type="text" id="zipCode" name="zipCode">
            </div>

            <div class="form-group">
                <label for="languagesSpoken">Languages Spoken</label>
                <input type="text" id="languagesSpoken" name="languagesSpoken" placeholder="English, Spanish">
            </div>

            <div class="form-group">
                <label for="hoursOfOperation">Hours of Operation</label>
                <textarea id="hoursOfOperation" name="hoursOfOperation" placeholder="Mon-Fri 8am-6pm"></textarea>
            </div>

            <div class="form-group">
                <label for="bio">Professional Bio</label>
                <textarea id="bio" name="bio" placeholder="Tell customers about your nursing background."></textarea>
            </div>

            <div class="form-group">
                <label class="checkbox-label">
                    <input type="checkbox" name="internshipAvailable" value="true">
                    <span>Internship available</span>
                </label>
            </div>

            <button type="submit" class="btn btn-primary btn-full btn-large">Create Nurse Account</button>
        </form>

        <p class="text-center" style="margin-top: 24px;">
            Already registered? <a href="/provider/login">Log in as Nurse</a>
        </p>
    </div>
</div>

</body>
</html>