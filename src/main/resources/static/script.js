function clearDropdown() {
    document.querySelector('.search-dropdown').innerHTML = '';
}

document.querySelector('#search-form').addEventListener('submit', () => {
    setTimeout(clearDropdown, 400);
});
document.querySelector('#search-form').addEventListener('focusout', clearDropdown);

function removeRatingForm() {
    document.querySelector('.rating-form-container').remove();
}

function removeRatingFormDelayed() {
    setTimeout(removeRatingForm, 200);
}