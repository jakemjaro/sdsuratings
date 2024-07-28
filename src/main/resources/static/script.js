function clearDropdown() {
    document.querySelector('.search-dropdown').innerHTML = '';
}

document.querySelector('#search-form').addEventListener('submit', clearDropdown);
//document.querySelector('#search-form').addEventListener('focusout', clearDropdown);

//const ratingFormContainer = document.querySelector('.rating-form-container');
//const ratingForm = document.querySelector('.rating-form');
//ratingFormContainer.addEventListener("click", () => {
//    ratingFormContainer.style.display = "hidden";
//    ratingForm.style.display = "hidden";
//});
