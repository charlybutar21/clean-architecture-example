document.addEventListener('DOMContentLoaded', function() {
    // Modal form handling
    const modal = document.querySelector('#contactModal');
    const closeButton = document.querySelector('.close-button');

    document.querySelector('#showModal').addEventListener('click', function() {
        clearForm();
        modal.style.display = 'flex';
    });

    closeButton.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    }

    // Fetch country list on load
    fetch('https://restcountries.com/v3.1/all')
        .then(response => response.json())
        .then(data => populateCountrySelect(data))
        .catch(error => console.error('Error fetching country data:', error));

    function populateCountrySelect(countries) {
        const countrySelect = document.getElementById('modal-country');
        countries.forEach(country => {
            const option = document.createElement('option');
            option.value = country.name.common;
            option.textContent = country.name.common;
            countrySelect.appendChild(option);
        });
    }

    // Fetch and display contact details
    document.querySelectorAll('.contact-link').forEach(link => {
        link.addEventListener('click', function() {
            const contactId = this.getAttribute('data-id');
            fetchContactDetails(contactId);
        });
    });

    function fetchContactDetails(contactId) {
        const url = `/contacts/${contactId}`;

        fetch(url)
            .then(response => response.json())
            .then(contact => {
                document.getElementById('contactId').value = contact.id;
                document.getElementById('modal-name').value = contact.name;
                document.getElementById('modal-email').value = contact.email;
                document.getElementById('modal-country').value = contact.country;
                document.getElementById('modal-phone').value = contact.phone;

                // Set form to update contact
                const contactForm = document.getElementById('contactForm');
                contactForm.action = "/contacts";
                contactForm.method = "post";
                createHiddenMethodField(contactForm, "PUT");

                modal.style.display = 'flex';
            })
            .catch(error => console.error('Error fetching contact details:', error));
    }

    function createHiddenMethodField(form, method) {
        const methodField = document.createElement("input");
        methodField.type = "hidden";
        methodField.name = "_method";
        methodField.value = method;
        form.appendChild(methodField);
    }

    function clearForm() {
        document.getElementById('contactForm').reset();
        document.getElementById('contactId').value = '';
        removeMethodField();
    }

    function removeMethodField() {
        const methodField = document.querySelector('input[name="_method"]');
        if (methodField) {
            methodField.remove();
        }
    }

    // Auto-hide success/error messages after 3 seconds
    setTimeout(function() {
        document.querySelectorAll('.alert').forEach(alert => alert.style.display = 'none');
    }, 3000);
});
