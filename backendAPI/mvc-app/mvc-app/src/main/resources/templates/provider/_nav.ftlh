<#macro providerNav active="">
<nav class="navbar">
  <div class="container navbar-content">
    <a class="logo" href="/">Nurse Search</a>
    <ul class="nav-links">
      <li><a href="/" >Home</a></li>
      <li><a href="/provider/listings" class="${active == 'listings'?then('active','')}">Find Customers</a></li>
      <li><a href="/provider/dashboard" class="${active == 'dashboard'?then('active','')}">Appointments</a></li>
      <li><a href="/provider/reviews" class="${active == 'reviews'?then('active','')}">Reviews</a></li>
      <li><a href="/provider/profile" class="${active == 'profile'?then('active','')}">Profile</a></li>
      <li><a href="/provider/logout">Logout</a></li>
    </ul>
  </div>
</nav>
</#macro>
