function clearSearch() {
    document.querySelector('.search-dropdown').innerHTML = '';
}

document.querySelector('#search-form').addEventListener('submit', clearSearch);
//document.querySelector('#search-form').addEventListener('focusout', clearSeach);

//const ratingFormContainer = document.querySelector('.rating-form-container');
//const ratingForm = document.querySelector('.rating-form');
//ratingFormContainer.addEventListener("click", () => {
//    ratingFormContainer.style.display = "hidden";
//    ratingForm.style.display = "hidden";
//});