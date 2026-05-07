<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Find Nurses - Nurse Search</title>
    <link rel="stylesheet" href="/styles.css">
    <style>
        .layout {
            display: grid;
            grid-template-columns: 280px 1fr;
            gap: 24px;
            margin: 32px 0;
        }
        @media (max-width: 968px) { .layout { grid-template-columns: 1fr; } }
        .filters-sidebar {
            background: var(--bg-card);
            border: 1px solid var(--border);
            border-radius: var(--radius-lg);
            padding: 24px;
            height: fit-content;
            position: sticky;
            top: 88px;
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <div class="container navbar-content">
            <div class="logo">Nurse Search</div>
            <ul class="nav-links">
                <li><a href="/">Home</a></li>
                <li><a href="/customer/search" class="active">Find Nurses</a></li>
                <li><a href="/customer/dashboard">My Bookings</a></li>
                <li><a href="/customer/profile">${customerName!''}</a></li>
                <li><a href="/customer/logout">Log Out</a></li>
            </ul>
        </div>
    </nav>

    <div class="container">

        <#-- Top search bar — specialty + city only -->
        <div class="search-container">
            <form method="get" action="/customer/search">
                <div class="search-bar">
                    <div class="search-field" style="flex: 2;">
                        <label>Search by specialty</label>
                        <input type="text" name="specialty" value="${specialty!''}" placeholder="e.g. Wound Care">
                    </div>
                    <div class="search-field">
                        <label>City</label>
                        <input type="text" name="city" value="${city!''}" placeholder="e.g. Greensboro">
                    </div>
                    <button type="submit" class="btn btn-primary">Search</button>
                </div>
            </form>
        </div>

        <div class="layout">

            <#-- Sidebar filters — single form, no hidden duplicates -->
            <aside class="filters-sidebar">
                <h3 style="margin-bottom: 24px;">Filters</h3>

                <form method="get" action="/customer/search">

                    <div class="filter-section">
                        <h4>Specialty</h4>
                        <div class="checkbox-group">
                            <#list ["Wound Care","Geriatric Care","Medication Management","Rehabilitation","Palliative Care","Post-Surgery Care"] as spec>
                            <label class="checkbox-label">
                                <input type="radio" name="specialty" value="${spec}"
                                       <#if (specialty!'') == spec>checked</#if>>
                                <span>${spec}</span>
                            </label>
                            </#list>
                        </div>
                    </div>

                    <div class="filter-section">
                        <h4>City</h4>
                        <input type="text" name="city" value="${city!''}"
                               placeholder="e.g. Greensboro"
                               style="width:100%; padding:8px 12px; border:1.5px solid var(--border);
                                      border-radius:var(--radius-md); font-family:var(--font-body);">
                    </div>

                    <div class="filter-section">
                        <h4>Language</h4>
                        <div class="checkbox-group">
                            <#list ["English","Spanish","French","Mandarin","Amharic"] as lang>
                            <label class="checkbox-label">
                                <input type="radio" name="language" value="${lang}"
                                       <#if (language!'') == lang>checked</#if>>
                                <span>${lang}</span>
                            </label>
                            </#list>
                        </div>
                    </div>

                    <div class="filter-section">
                        <h4>Max Hourly Rate ($/hr)</h4>
                        <input type="number" name="maxRate" value="${maxRate!''}"
                               placeholder="e.g. 50" min="1" max="300"
                               style="width:100%; padding:8px 12px; border:1.5px solid var(--border);
                                      border-radius:var(--radius-md); font-family:var(--font-body);">
                    </div>

                    <div class="filter-section">
                        <h4>Experience Level</h4>
                        <div class="checkbox-group">
                            <#list ["Entry","Mid","Senior"] as lvl>
                            <label class="checkbox-label">
                                <input type="radio" name="experienceLevel" value="${lvl}"
                                       <#if (experienceLevel!'') == lvl>checked</#if>>
                                <span>${lvl}</span>
                            </label>
                            </#list>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary btn-full">Apply Filters</button>
                    <a href="/customer/search" class="btn btn-secondary btn-full" style="margin-top:8px;">
                        Clear All Filters
                    </a>
                </form>
            </aside>

            <#-- Results -->
            <main>
                <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:24px;">
                    <h2>
                        <#if resultCount == 0>No Nurses Found
                        <#elseif resultCount == 1>1 Nurse Available
                        <#else>${resultCount} Nurses Available
                        </#if>
                    </h2>
                </div>

                <#if nurses?? && nurses?size gt 0>
                    <div class="grid grid-2" id="nurseResults">
                        <#list nurses as nurse>
                        <div class="nurse-card" onclick="window.location.href='/customer/nurses/${nurse.userId}'">
                            <div class="nurse-card-header">
                                <div class="nurse-avatar">
                                    ${nurse.firstName?substring(0,1)}${nurse.lastName?substring(0,1)}
                                </div>
                                <div class="nurse-info">
                                    <h3>${nurse.firstName} ${nurse.lastName}</h3>
                                    <div class="nurse-specialty">
                                        <#if nurse.specialty??>${nurse.specialty}</#if>
                                        <#if nurse.experienceLevel??> · ${nurse.experienceLevel}</#if>
                                    </div>
                                </div>
                            </div>
                            <div class="nurse-card-body">
                                <div class="nurse-tags">
                                    <#if nurse.languagesSpoken??><span class="tag">${nurse.languagesSpoken}</span></#if>
                                    <#if nurse.city??><span class="tag">📍 ${nurse.city}</span></#if>
                                </div>
                                <#if nurse.hourlyRate??>
                                    <div class="nurse-rate">$${nurse.hourlyRate}/hour</div>
                                </#if>
                                <#if nurse.bio??>
                                    <p style="font-size:.85rem; color:var(--text-muted); margin:8px 0;">
                                        <#if nurse.bio?length gt 100>${nurse.bio?substring(0,100)}...<#else>${nurse.bio}</#if>
                                    </p>
                                </#if>
                                <button class="btn btn-primary btn-full">View Profile</button>
                            </div>
                        </div>
                        </#list>
                    </div>
                <#else>
                    <div class="empty-state">
                        <div class="empty-state-icon">🔍</div>
                        <h3>No Nurses Found</h3>
                        <p>Try adjusting your filters or <a href="/customer/search">show all nurses</a>.</p>
                    </div>
                </#if>
            </main>

        </div>
    </div>

</body>
</html>
