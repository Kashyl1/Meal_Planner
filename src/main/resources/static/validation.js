document.addEventListener('DOMContentLoaded', function () {
  const form = document.querySelector('form');
  const submitButton = document.querySelector('.form-submit-button');
  const mealSelections = document.querySelectorAll('select');

  submitButton.addEventListener('click', function (event) {
    for (const mealSelection of mealSelections) {
      if (mealSelection.value === '') {
        event.preventDefault();
        alert('Please fill out all meal selections before submitting the plan.');
        return;
      }
    }
  });
});