document.addEventListener("DOMContentLoaded", function () {
    // Get all the anchor tags in the sidebar nav
    const links = document.querySelectorAll(".nav-link");
  
    // Loop through each link and add a click event listener
    links.forEach((link) => {
      link.addEventListener("click", () => {
        // Remove the active class from all nav-items
        links.forEach((lnk) => {
          lnk.parentElement.classList.remove("active");
        });
  
        // Add the active class to the parent nav-item of the clicked link
        link.parentElement.classList.add("active");
  
        // Store the active link href in local storage
        localStorage.setItem("activeLink", link.getAttribute("href"));
      });
    });
  
    // Check if there is an active link stored in local storage
    const activeLink = localStorage.getItem("activeLink");
    if (activeLink) {
      const activeElement = document.querySelector(`a[href="${activeLink}"]`);
      if (activeElement) {
        activeElement.parentElement.classList.add("active");
  
        // Open the parent collapse if it's part of a nested menu
        const parentCollapse = activeElement.closest('.collapse');
        if (parentCollapse) {
          const collapseInstance = new bootstrap.Collapse(parentCollapse, { toggle: false });
          collapseInstance.show();
        }
      }
    }
  });

  $(document).ready(function(){
    $('.datepicker-input').datepicker({
        format: 'dd/mm/yyyy'
    });
});


  document.addEventListener("DOMContentLoaded", function() {
    const forms = document.querySelectorAll('form');
    forms.forEach(function(form) {
        form.setAttribute('autocomplete', 'off');
    });
});

  