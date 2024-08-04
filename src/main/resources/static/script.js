function clearDropdown() {
    document.querySelector('.search-dropdown').innerHTML = '';
}

document.querySelector('#search-form').addEventListener('submit', clearDropdown);
document.querySelector('#search-form').addEventListener('focusout', clearDropdown);

function removeRatingForm() {
    document.querySelector('.rating-form-container').remove();
}