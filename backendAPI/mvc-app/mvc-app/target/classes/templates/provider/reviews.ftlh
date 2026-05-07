<#import "_nav.ftlh" as layout>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Provider Reviews - Nurse Search</title>
  <link rel="stylesheet" href="/styles.css">
</head>
<body>
<@layout.providerNav active="reviews" />

<div class="container page">
  <h1>My Reviews</h1>
  <p>View reviews customers left for you and reply when needed.</p>

  <#if successMsg??><div class="alert alert-success">${successMsg}</div></#if>
  <#if errorMsg??><div class="alert alert-error">${errorMsg}</div></#if>

  <#if reviews?size == 0>
    <div class="card"><p>No reviews yet.</p></div>
  <#else>
    <#list reviews as review>
      <div class="card">
        <div class="row-between">
          <h3>
            <#if review.appointment?? && review.appointment.customer??>
              ${review.appointment.customer.firstName} ${review.appointment.customer.lastName}
            <#else>
              Customer Review
            </#if>
          </h3>
          <#if review.rating??><span class="badge badge-info">${review.rating}/5</span></#if>
        </div>

        <p>${review.comment!"No comment provided."}</p>

        <#if review.replyText?? && review.replyText?has_content>
          <div class="stat-box">
            <strong>Your Reply:</strong>
            <p>${review.replyText}</p>
          </div>
        <#else>
          <form method="post" action="/provider/reviews/${review.reviewId}/reply" style="margin-top: 18px;">
            <div class="form-group">
              <label>Reply</label>
              <textarea name="replyText" required placeholder="Thank the customer or respond professionally."></textarea>
            </div>
            <button class="btn btn-primary btn-small" type="submit">Add Reply</button>
          </form>
        </#if>
      </div>
    </#list>
  </#if>
</div>
</body>
</html>
