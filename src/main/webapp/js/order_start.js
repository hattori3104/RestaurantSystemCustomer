/**
 * order_start.js
 */
document.addEventListener("DOMContentLoaded", () => {
    const decrementBtn = document.getElementById("decrement-btn");
    const incrementBtn = document.getElementById("increment-btn");
    const guestCountElem = document.getElementById("guest-count");
    const guestValue = document.getElementById("guest-input");

    let count = 1;   // 初期値
    const minCount = 1;
    const maxCount = 10;

    function updateDisplay() {
        guestCountElem.textContent = count;
        guestValue.value = count;
    }

    decrementBtn.addEventListener("click", () => {
        if (count > minCount) {
            count--;
            updateDisplay();
        }
    });

    incrementBtn.addEventListener("click", () => {
        if (count < maxCount) {
            count++;
            updateDisplay();
        }
    });

    // 初期表示
    updateDisplay();
});