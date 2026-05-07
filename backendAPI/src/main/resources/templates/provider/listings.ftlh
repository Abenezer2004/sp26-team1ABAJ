<#import "_nav.ftlh" as layout>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Open Customer Listings - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<@layout.providerNav active="listings" />

<div class="container page">
  <h1>Open Customer Listings</h1>
  <p>Browse customer-posted care needs and filter by specialty or budget.</p>

  <div class="card">
    <form method="get" action="/provider/listings" class="grid grid-3">
      <div class="form-group">
        <label>Specialty Needed</label>
        <input type="text" name="specialty" value="${specialty!}" placeholder="Wound Care">
      </div>
      <div class="form-group">
        <label>Max Hourly Budget</label>
        <input type="number" step="0.01" name="maxBudget" value="${maxBudget!}" placeholder="40">
      </div>
      <div class="form-group" style="display:flex; align-items:end;">
        <button class="btn btn-primary btn-full" type="submit">Filter Listings</button>
      </div>
    </form>
  </div>

  <div class="row-between" style="margin-bottom: 20px;">
    <h2>${resultCount} Listing(s) Found</h2>
    <a class="btn btn-secondary" href="/provider/listings">Clear Filters</a>
  </div>

  <#if listings?size == 0>
    <div class="card">
      <p>No open listings match your filters.</p>
    </div>
  <#else>
    <div class="grid grid-2">
      <#list listings as listing>
        <div class="card">
          <div class="row-between">
            <h3>${listing.specialtyNeeded}</h3>
            <span class="badge badge-primary">${listing.status}</span>
          </div>
          <p><strong>Customer:</strong> ${listing.customer.firstName} ${listing.customer.lastName}</p>
          <p><strong>Start Date:</strong> ${listing.startDate!"Not specified"}</p>
          <p><strong>Duration:</strong> ${listing.durationDays!"Not specified"} day(s)</p>
          <p><strong>Budget:</strong> $${listing.hourlyBudget!0}/hr</p>
          <p><strong>Language:</strong> ${listing.languageRequired!"No requirement"}</p>
          <p>${listing.additionalRequirements!"No additional requirements."}</p>
        </div>
      </#list>
    </div>
  </#if>
</div>
</body>
</html>
