<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Bookings - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
  <style>
    .booking-card {
      background: var(--bg-card); border-radius: var(--radius-lg); padding: 24px;
      border: 1px solid var(--border); margin-bottom: 16px; transition: all 0.2s ease;
    }
    .booking-card:hover { box-shadow: var(--shadow-md); }
    .booking-header { display:flex; justify-content:space-between; align-items:flex-start; margin-bottom:16px; }
    .tab-content { display:none; }
    .tab-content.active { display:block; }
    .flash-success {
      padding: 14px 20px; border-radius: var(--radius-md); margin-bottom: 24px;
      background: #d1fae5; color: #065f46; border: 1px solid #6ee7b7; font-weight: 500;
    }
    .flash-error {
      padding: 14px 20px; border-radius: var(--radius-md); margin-bottom: 24px;
      background: #fee2e2; color: #991b1b; border: 1px solid #fca5a5; font-weight: 500;
    }
    .btn-cancel {
      display: inline-block; padding: 6px 14px; font-size: .82rem; font-weight: 600;
      border-radius: var(--radius-full); border: 1.5px solid var(--danger);
      color: var(--danger); background: transparent; cursor: pointer; transition: all .2s;
    }
    .btn-cancel:hover { background: var(--danger); color: #fff; }
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

<div class="container" style="margin:32px auto;">

  <#if successMsg??>
    <div class="flash-success">✓ ${successMsg}</div>
  </#if>
  <#if errorMsg??>
    <div class="flash-error">✗ ${errorMsg}</div>
  </#if>

  <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:32px;">
    <h1>My Bookings</h1>
    <a href="/customer/listing/new" class="btn btn-primary">+ Create Job Listing</a>
  </div>

  <div class="tabs">
    <button class="tab active" onclick="showTab('listings', this)">
      My Listings
      <#if listings?? && listings?size gt 0>
        <span class="badge badge-info" style="margin-left:6px;">${listings?size}</span>
      </#if>
    </button>
    <button class="tab" onclick="showTab('pending', this)">
      Pending
      <#if pendingAppts?? && pendingAppts?size gt 0>
        <span class="badge badge-warning" style="margin-left:6px;">${pendingAppts?size}</span>
      </#if>
    </button>
    <button class="tab" onclick="showTab('upcoming', this)">
      Upcoming
      <#if confirmedAppts?? && confirmedAppts?size gt 0>
        <span class="badge badge-success" style="margin-left:6px;">${confirmedAppts?size}</span>
      </#if>
    </button>
    <button class="tab" onclick="showTab('completed', this)">Completed</button>
    <button class="tab" onclick="showTab('cancelled', this)">Cancelled</button>
  </div>

  <!-- MY LISTINGS TAB -->
  <div id="listings-tab" class="tab-content active">
    <#if listings?? && listings?size gt 0>
      <#list listings as listing>
      <div class="booking-card">
        <div class="booking-header">
          <div>
            <h3 style="margin-bottom:4px;">${listing.specialtyNeeded!''}</h3>
            <p style="color:var(--text-secondary);margin:0;font-size:14px;">Posted listing</p>
          </div>
          <#if listing.status?string == "OPEN">
            <span class="badge badge-success">Open</span>
          <#elseif listing.status?string == "FILLED">
            <span class="badge badge-info">Filled</span>
          <#else>
            <span class="badge badge-warning">Closed</span>
          </#if>
        </div>
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(180px,1fr));gap:16px;
                    padding:16px;background:var(--bg-subtle);border-radius:var(--radius-md);margin-bottom:16px;">
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Start Date</div>
            <div style="font-weight:600;">
              <#if listing.startDate??>${listing.startDate}<#else>Not set</#if>
            </div>
          </div>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Duration</div>
            <div style="font-weight:600;">
              <#if listing.durationDays??>${listing.durationDays} days<#else>Not set</#if>
            </div>
          </div>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Budget</div>
            <div style="font-weight:600;color:var(--primary);">
              <#if listing.hourlyBudget??>$${listing.hourlyBudget}/hr<#else>Not set</#if>
            </div>
          </div>
        </div>
        <#if listing.additionalRequirements??>
          <p style="color:var(--text-secondary);margin:0;">${listing.additionalRequirements}</p>
        </#if>
      </div>
      </#list>
    <#else>
      <div class="empty-state">
        <div class="empty-state-icon">📋</div>
        <h3>No Listings Yet</h3>
        <p>Create your first job listing to find a nurse.</p>
        <a href="/customer/listing/new" class="btn btn-primary">Create Job Listing</a>
      </div>
    </#if>
  </div>

  <!-- PENDING TAB -->
  <div id="pending-tab" class="tab-content">
    <#if pendingAppts?? && pendingAppts?size gt 0>
      <#list pendingAppts as appt>
      <div class="booking-card">
        <div class="booking-header">
          <div style="display:flex;gap:16px;align-items:center;">
            <div class="nurse-avatar">
              ${appt.nurse.firstName?substring(0,1)}${appt.nurse.lastName?substring(0,1)}
            </div>
            <div>
              <h3 style="margin-bottom:4px;">${appt.nurse.firstName} ${appt.nurse.lastName}</h3>
              <p style="color:var(--text-secondary);margin:0;font-size:14px;">${appt.nurse.specialty!''}</p>
            </div>
          </div>
          <span class="badge badge-warning">Pending</span>
        </div>
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:16px;
                    padding:16px;background:var(--bg-subtle);border-radius:var(--radius-md);margin-bottom:16px;">
          <#if appt.dateTime??>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Requested Time</div>
            <div style="font-weight:600;">${appt.dateTime}</div>
          </div>
          </#if>
          <#if appt.careDetails??>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Care Details</div>
            <div>${appt.careDetails}</div>
          </div>
          </#if>
        </div>
        <div style="display:flex;gap:12px;">
          <a href="/customer/nurses/${appt.nurse.userId}" class="btn btn-secondary btn-small">View Profile</a>
          <form method="post" action="/customer/appointments/${appt.appointmentId}/cancel"
                onsubmit="return confirm('Cancel this appointment request?');" style="display:inline;">
            <button type="submit" class="btn-cancel">Cancel Request</button>
          </form>
        </div>
      </div>
      </#list>
    <#else>
      <div class="empty-state">
        <div class="empty-state-icon">⏳</div>
        <h3>No Pending Requests</h3>
        <p>When you request an appointment it will appear here.</p>
        <a href="/customer/search" class="btn btn-primary">Find a Nurse</a>
      </div>
    </#if>
  </div>

  <!-- UPCOMING (CONFIRMED) TAB -->
  <div id="upcoming-tab" class="tab-content">
    <#if confirmedAppts?? && confirmedAppts?size gt 0>
      <#list confirmedAppts as appt>
      <div class="booking-card">
        <div class="booking-header">
          <div style="display:flex;gap:16px;align-items:center;">
            <div class="nurse-avatar">
              ${appt.nurse.firstName?substring(0,1)}${appt.nurse.lastName?substring(0,1)}
            </div>
            <div>
              <h3 style="margin-bottom:4px;">${appt.nurse.firstName} ${appt.nurse.lastName}</h3>
              <p style="color:var(--text-secondary);margin:0;font-size:14px;">${appt.nurse.specialty!''}</p>
            </div>
          </div>
          <span class="badge badge-success">Confirmed</span>
        </div>
        <div style="display:grid;grid-template-columns:repeat(auto-fit,minmax(200px,1fr));gap:16px;
                    padding:16px;background:var(--bg-subtle);border-radius:var(--radius-md);margin-bottom:16px;">
          <#if appt.dateTime??>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Date & Time</div>
            <div style="font-weight:600;">${appt.dateTime}</div>
          </div>
          </#if>
          <#if appt.careDetails??>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Care Details</div>
            <div>${appt.careDetails}</div>
          </div>
          </#if>
          <#if appt.nurse.hourlyRate??>
          <div>
            <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Rate</div>
            <div style="font-weight:600;color:var(--primary);">$${appt.nurse.hourlyRate}/hr</div>
          </div>
          </#if>
        </div>
        <div style="display:flex;gap:12px;">
          <a href="/customer/nurses/${appt.nurse.userId}" class="btn btn-primary btn-small">View Profile</a>
          <form method="post" action="/customer/appointments/${appt.appointmentId}/cancel"
                onsubmit="return confirm('Cancel this confirmed appointment?');" style="display:inline;">
            <button type="submit" class="btn-cancel">Cancel Appointment</button>
          </form>
        </div>
      </div>
      </#list>
    <#else>
      <div class="empty-state">
        <div class="empty-state-icon">📅</div>
        <h3>No Upcoming Appointments</h3>
        <p>Confirmed appointments will appear here.</p>
        <a href="/customer/search" class="btn btn-primary">Find a Nurse</a>
      </div>
    </#if>
  </div>

  <!-- COMPLETED TAB -->
  <div id="completed-tab" class="tab-content">
    <#if completedAppts?? && completedAppts?size gt 0>
      <#list completedAppts as appt>
      <div class="booking-card">
        <div class="booking-header">
          <div style="display:flex;gap:16px;align-items:center;">
            <div class="nurse-avatar">
              ${appt.nurse.firstName?substring(0,1)}${appt.nurse.lastName?substring(0,1)}
            </div>
            <div>
              <h3 style="margin-bottom:4px;">${appt.nurse.firstName} ${appt.nurse.lastName}</h3>
              <p style="color:var(--text-secondary);margin:0;font-size:14px;">${appt.nurse.specialty!''}</p>
            </div>
          </div>
          <span class="badge badge-info">Completed</span>
        </div>
        <#if appt.dateTime??>
        <div style="padding:16px;background:var(--bg-subtle);border-radius:var(--radius-md);margin-bottom:16px;">
          <div style="color:var(--text-muted);font-size:14px;margin-bottom:4px;">Appointment Date</div>
          <div style="font-weight:600;">${appt.dateTime}</div>
        </div>
        </#if>
        <div style="display:flex;gap:12px;">
          <#if !appt.reviewedByCustomer>
            <a href="/customer/appointments/${appt.appointmentId}/review" class="btn btn-primary btn-small">⭐ Write Review</a>
          <#else>
            <span class="badge badge-success" style="padding:8px 16px;">Review Submitted ✓</span>
          </#if>
          <a href="/customer/nurses/${appt.nurse.userId}" class="btn btn-secondary btn-small">View Profile</a>
        </div>
      </div>
      </#list>
    <#else>
      <div class="empty-state">
        <div class="empty-state-icon">✅</div>
        <h3>No Completed Appointments</h3>
        <p>Completed appointments will appear here.</p>
      </div>
    </#if>
  </div>

  <!-- CANCELLED TAB -->
  <div id="cancelled-tab" class="tab-content">
    <#assign allCancelled = (cancelledAppts![]) + (declinedAppts![])>
    <#if allCancelled?size gt 0>
      <#list allCancelled as appt>
      <div class="booking-card">
        <div class="booking-header">
          <div style="display:flex;gap:16px;align-items:center;">
            <div class="nurse-avatar">
              ${appt.nurse.firstName?substring(0,1)}${appt.nurse.lastName?substring(0,1)}
            </div>
            <div>
              <h3 style="margin-bottom:4px;">${appt.nurse.firstName} ${appt.nurse.lastName}</h3>
              <p style="color:var(--text-secondary);margin:0;font-size:14px;">${appt.nurse.specialty!''}</p>
            </div>
          </div>
          <#if appt.status?string == "CANCELLED">
            <span class="badge badge-warning">Cancelled</span>
          <#else>
            <span class="badge badge-warning">Declined</span>
          </#if>
        </div>
        <div style="display:flex;gap:12px;">
          <a href="/customer/nurses/${appt.nurse.userId}/book" class="btn btn-primary btn-small">Book Again</a>
          <a href="/customer/nurses/${appt.nurse.userId}" class="btn btn-secondary btn-small">View Profile</a>
        </div>
      </div>
      </#list>
    <#else>
      <div class="empty-state">
        <div class="empty-state-icon">✓</div>
        <h3>No Cancelled Bookings</h3>
        <p>You haven't cancelled any appointments.</p>
      </div>
    </#if>
  </div>

</div>

<script>
  function showTab(tabName, btn) {
    document.querySelectorAll('.tab-content').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.getElementById(tabName + '-tab').classList.add('active');
    btn.classList.add('active');
  }
</script>
</body>
</html>
