<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Open Customer Listings - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>

<nav class="navbar">
  <div class="container navbar-content">
    <div class="logo">Nurse Search</div>

    <ul class="nav-links">
      <li><a href="/">Home</a></li>
      <li><a href="/provider/dashboard">Dashboard</a></li>
      <li><a href="/provider/listings" class="active">Open Listings</a></li>
      <li><a href="/provider/profile">Profile</a></li>
      <li><a href="/provider/logout">Logout</a></li>
    </ul>
  </div>
</nav>

<div class="container page">

  <div style="display:flex; justify-content:space-between; align-items:center; gap:16px; flex-wrap:wrap; margin-bottom:28px;">
    <div>
      <h1>Open Customer Listings</h1>
      <p>Browse customer care requests and filter by specialty or budget.</p>
    </div>

    <a class="btn btn-secondary" href="/provider/dashboard">
      Back to Dashboard
    </a>
  </div>

  <div class="search-container">

    <form method="get"
          action="/provider/listings"
          class="search-bar">

      <div class="search-field">
        <label for="specialty">Specialty</label>

        <input type="text"
               id="specialty"
               name="specialty"
               value="${specialty!}"
               placeholder="Geriatric Care, Wound Care">
      </div>

      <div class="search-field">
        <label for="maxBudget">Max Budget</label>

        <input type="number"
               step="0.01"
               id="maxBudget"
               name="maxBudget"
               value="${maxBudget!}"
               placeholder="40">
      </div>

      <button class="btn btn-primary" type="submit">
        Apply Filters
      </button>

      <a class="btn btn-secondary" href="/provider/listings">
        Clear
      </a>

    </form>
  </div>

  <#if listings?? && listings?size gt 0>

    <div class="grid grid-2">

      <#list listings as listing>

        <div class="card">

          <div style="display:flex; justify-content:space-between; align-items:flex-start; gap:12px;">

            <div>

              <h3>${listing.specialtyNeeded!"Care Needed"}</h3>

              <p>
                <strong>Customer:</strong>

                <#if listing.customer??>
                  ${listing.customer.firstName!"Unknown"}
                  ${listing.customer.lastName!""}
                <#else>
                  Unknown Customer
                </#if>
              </p>

            </div>

            <span class="badge badge-success">
              ${listing.status!"OPEN"}
            </span>

          </div>

          <p>
            <strong>Start Date:</strong>
            ${listing.startDate!"Not specified"}
          </p>

          <p>
            <strong>Duration:</strong>
            ${listing.durationDays!"Not specified"} days
          </p>

          <p>
            <strong>Budget:</strong>
            $${listing.hourlyBudget!0}/hr
          </p>

          <p>
            <strong>Language:</strong>
            ${listing.languageRequired!"No preference"}
          </p>

          <p>
            <strong>Requirements:</strong>
            ${listing.additionalRequirements!"No additional requirements."}
          </p>

        </div>

      </#list>

    </div>

  <#else>

    <div class="card empty-state">
      <h2>No open listings found.</h2>
      <p>Try clearing filters or checking back later.</p>
    </div>

  </#if>

</div>

</body>
</html>