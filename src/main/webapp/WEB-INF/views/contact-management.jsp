<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Management</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<div class="container">
    <!-- Success/Error Messages -->
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success" id="successMessage">${successMessage}</div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" id="errorMessage">${errorMessage}</div>
    </c:if>

    <h2>Contact Management</h2>

    <!-- Search and Add Contact Form -->
    <div class="search-form-container">
        <form class="search-form" action="${pageContext.request.contextPath}/contacts" method="get">
            <input type="text" name="keyword" placeholder="Search by name" value="${param.keyword}" />
            <button type="submit" class="button">Search</button>
            <a href="/contacts" class="button clear-button">Clear Search</a>
        </form>
        <a href="#" class="button" id="showModal">Add Contact</a>
    </div>

    <!-- Contact List Table -->
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Country</th>
            <th>Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="contact" items="${data}">
            <tr>
                <td><a href="#" class="contact-link" data-id="${contact.id}">${contact.name}</a></td>
                <td>${contact.email}</td>
                <td>${contact.country}</td>
                <td>${contact.phone}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- Pagination Controls -->
    <div class="pagination">
        <c:choose>
            <c:when test="${totalPages > 0}">
                <c:if test="${currentPage > 0}">
                    <a href="?page=${currentPage - 1}&size=${pageSize}&keyword=${param.keyword}&sortBy=${sortBy}&sortOrientation=${sortOrientation}">Previous</a>
                </c:if>
                <c:forEach begin="0" end="${totalPages - 1}" var="i">
                    <a href="?page=${i}&size=${pageSize}&keyword=${param.keyword}&sortBy=${sortBy}&sortOrientation=${sortOrientation}">${i + 1}</a>
                </c:forEach>
                <c:if test="${currentPage < totalPages - 1}">
                    <a href="?page=${currentPage + 1}&size=${pageSize}&keyword=${param.keyword}&sortBy=${sortBy}&sortOrientation=${sortOrientation}">Next</a>
                </c:if>
            </c:when>
            <c:otherwise>
                <p>No pages available.</p>
            </c:otherwise>
        </c:choose>
    </div>

</div>

<!-- Modal for Adding/Editing Contact -->
<div class="modal" id="contactModal">
    <div class="modal-content">
        <span class="close-button">&times;</span>
        <h2 id="modal-title">Contact Form</h2>
        <form id="contactForm" action="/contacts" method="post">
            <input type="hidden" id="contactId" name="id">

            <label for="modal-name">Name:</label>
            <input type="text" id="modal-name" name="name" required>

            <label for="modal-email">Email:</label>
            <input type="email" id="modal-email" name="email" required>

            <label for="modal-country">Country:</label>
            <select id="modal-country" name="country" required>
                <option value="">Select a country</option>
            </select>

            <label for="modal-phone">Phone:</label>
            <div style="display: flex; align-items: center;">
                <span style="margin-right: 5px;">+62</span>
                <input type="tel" id="modal-phone" name="phone" required pattern="[0-9]{8,}" title="Phone number must be at least 8 digits long" style="flex-grow: 1;">
            </div>

            <button type="submit" class="button" id="modal-submit-button">Save</button>
        </form>
    </div>
</div>

<!-- JavaScript for Modal and Fetch Operations -->
<script src="${pageContext.request.contextPath}/js/contact-management.js"></script>

</body>
</html>
