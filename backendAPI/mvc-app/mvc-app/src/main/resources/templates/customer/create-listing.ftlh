<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Create Job Listing - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
  <nav class="navbar">
    <div class="container navbar-content">
      <div class="logo">Nurse Search</div>
      <ul class="nav-links">
        <li><a href="/">Home</a></li>
        <li><a href="/customer/search">Find Nurses</a></li>
        <li><a href="/customer/listing/new" class="active">Post Listing</a></li>
      </ul>
    </div>
  </nav>

  <div class="container" style="max-width: 800px; margin: 32px auto;">

    <a href="/" style="display: inline-flex; align-items: center; gap: 8px;
                       margin-bottom: 24px; color: var(--text-secondary);">
      ← Back to Home
    </a>

    <div class="card">
      <h1>Create a Job Listing</h1>
      <p style="color: var(--text-secondary); margin-bottom: 32px;">
        Post your care needs and let qualified nurses apply to your listing.
      </p>

      <#if error??>
        <div style="background: rgba(214,64,69,.08); border: 1.5px solid rgba(214,64,69,.3);
                    color: #D64045; padding: 14px 18px; border-radius: 12px;
                    margin-bottom: 20px; font-weight: 600;">
          ⚠ ${error}
        </div>
      </#if>

      <form action="/customer/listing/new" method="post">

        <div class="form-group">
          <label for="specialtyNeeded">Required Specialty *</label>
          <select id="specialtyNeeded" name="specialtyNeeded" required>
            <option value="">Select specialty</option>
            <option>Geriatric Care</option>
            <option>Wound Care</option>
            <option>Medication Management</option>
            <option>Rehabilitation</option>
            <option>Palliative Care</option>
            <option>Dementia/Alzheimer's Care</option>
            <option>Post-Surgery Care</option>
            <option>General Home Care</option>
          </select>
        </div>

        <div class="form-group">
          <label for="additionalRequirements">Job Description *</label>
          <textarea id="additionalRequirements" name="additionalRequirements"
                    required placeholder="Describe the care needed, daily tasks, and any special requirements"></textarea>
        </div>

        <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
          <div class="form-group">
            <label for="startDate">Start Date *</label>
            <input type="date" id="startDate" name="startDate" required>
          </div>
          <div class="form-group">
            <label for="durationDays">Duration (days) *</label>
            <input type="number" id="durationDays" name="durationDays"
                   required placeholder="e.g. 30" min="1">
          </div>
        </div>

        <div class="form-group">
          <label for="hourlyBudget">Hourly Budget ($/hr) *</label>
          <input type="number" id="hourlyBudget" name="hourlyBudget"
                 required placeholder="e.g. 45" min="20" step="0.01">
        </div>

        <div class="form-group">
          <label for="languageRequired">Preferred Language</label>
          <div style="display: flex; flex-wrap: wrap; gap: 8px; margin-top: 8px;">
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value="English"> <span>English</span>
            </label>
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value="Spanish"> <span>Spanish</span>
            </label>
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value="French"> <span>French</span>
            </label>
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value="Amharic"> <span>Amharic</span>
            </label>
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value="Mandarin"> <span>Mandarin</span>
            </label>
            <label class="checkbox-label" style="background: var(--bg-subtle); padding: 8px 16px; border-radius: var(--radius-full);">
              <input type="radio" name="languageRequired" value=""> <span>No preference</span>
            </label>
          </div>
        </div>

        <!-- Status set automatically to OPEN by @PrePersist -->
        <input type="hidden" name="status" value="OPEN">

        <div style="display: flex; gap: 12px; justify-content: flex-end; margin-top: 8px;">
          <a href="/" class="btn btn-secondary">Cancel</a>
          <button type="submit" class="btn btn-primary btn-large">Publish Listing</button>
        </div>

      </form>
    </div>
  </div>

</body>
</html>
