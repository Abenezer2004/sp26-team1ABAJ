<#import "_nav.ftlh" as layout>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Dashboard - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<@layout.providerNav active="dashboard" />

<div class="container page">
  <div class="row-between" style="margin-bottom: 28px;">
    <div>
      <h1>Provider Dashboard</h1>
      <p>Manage customer appointment requests and booking statuses.</p>
    </div>
    <a class="btn btn-primary" href="/provider/listings">Browse Open Listings</a>
  </div>

  <#if successMsg??><div class="alert alert-success">${successMsg}</div></#if>
  <#if errorMsg??><div class="alert alert-error">${errorMsg}</div></#if>

  <div class="stat-grid" style="margin-bottom: 28px;">
    <div class="stat-box"><div class="stat-value">${pendingAppts?size}</div><div>Pending</div></div>
    <div class="stat-box"><div class="stat-value">${confirmedAppts?size}</div><div>Confirmed</div></div>
    <div class="stat-box"><div class="stat-value">${completedAppts?size}</div><div>Completed</div></div>
    <div class="stat-box"><div class="stat-value">${declinedAppts?size}</div><div>Declined</div></div>
  </div>

  <h2>Pending Requests</h2>
  <#if pendingAppts?size == 0>
    <div class="card"><p>No pending appointment requests right now.</p></div>
  <#else>
    <#list pendingAppts as appt>
      <div class="card">
        <div class="row-between">
          <div>
            <h3>${appt.customer.firstName} ${appt.customer.lastName}</h3>
            <p><strong>Date:</strong> ${appt.dateTime}</p>
            <p><strong>Care Details:</strong> ${appt.careDetails!"No details provided."}</p>
            <#if appt.listing??>
              <p><strong>Listing:</strong> ${appt.listing.specialtyNeeded} · $${appt.listing.hourlyBudget!0}/hr</p>
            </#if>
          </div>
          <span class="badge badge-warning">Pending</span>
        </div>
        <div style="display:flex; gap:12px; margin-top:18px; flex-wrap:wrap;">
          <form method="post" action="/provider/appointments/${appt.appointmentId}/status">
            <input type="hidden" name="status" value="CONFIRMED">
            <button class="btn btn-primary btn-small" type="submit">Confirm</button>
          </form>
          <form method="post" action="/provider/appointments/${appt.appointmentId}/status">
            <input type="hidden" name="status" value="DECLINED">
            <button class="btn btn-secondary btn-small" type="submit">Decline</button>
          </form>
        </div>
      </div>
    </#list>
  </#if>

  <h2>Confirmed Appointments</h2>
  <#if confirmedAppts?size == 0>
    <div class="card"><p>No confirmed appointments.</p></div>
  <#else>
    <#list confirmedAppts as appt>
      <div class="card">
        <div class="row-between">
          <div>
            <h3>${appt.customer.firstName} ${appt.customer.lastName}</h3>
            <p><strong>Date:</strong> ${appt.dateTime}</p>
            <p>${appt.careDetails!"No care details provided."}</p>
          </div>
          <span class="badge badge-success">Confirmed</span>
        </div>
        <form method="post" action="/provider/appointments/${appt.appointmentId}/status" style="margin-top:18px;">
          <input type="hidden" name="status" value="COMPLETED">
          <button class="btn btn-primary btn-small" type="submit">Mark Completed</button>
        </form>
      </div>
    </#list>
  </#if>

  <h2>Completed Appointments</h2>
  <#if completedAppts?size == 0>
    <div class="card"><p>No completed appointments.</p></div>
  <#else>
    <#list completedAppts as appt>
      <div class="card">
        <div class="row-between">
          <div>
            <h3>${appt.customer.firstName} ${appt.customer.lastName}</h3>
            <p><strong>Date:</strong> ${appt.dateTime}</p>
          </div>
          <span class="badge badge-info">Completed</span>
        </div>
      </div>
    </#list>
  </#if>
</div>
</body>
</html>
