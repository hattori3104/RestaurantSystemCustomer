/**
 * 
 */
// 合計金額を表示する要素
const totalElem = document.getElementById('total');
const inputTotalElem = document.getElementById('input-total');

// 合計を更新する関数
function updateTotal() {
    let toppingTotal = 0;
    document.querySelectorAll('.counter-input').forEach(input => {
        const id = input.dataset.id;
        const count = parseInt(input.value, 10) || 0;
        toppingTotal += count * (toppingPrices[id] || 0);

        // ボタンの有効/無効状態を更新
        const parent = input.parentElement;
        const minusBtn = parent.querySelector('.minus');
        const plusBtn = parent.querySelector('.plus');
        const max = parseInt(plusBtn?.getAttribute('data-max'), 10) || 20;
        const min = parseInt(minusBtn?.getAttribute('data-min'), 10) || 0;

        if (minusBtn) {
            if (count <= min) minusBtn.classList.add('disabled');
            else minusBtn.classList.remove('disabled');
        }

        if (plusBtn) {
            if (count >= max) plusBtn.classList.add('disabled');
            else plusBtn.classList.remove('disabled');
        }
    });

    const total = basePrice + toppingTotal;
    if (totalElem) totalElem.textContent = total;
    if (inputTotalElem) inputTotalElem.value = total;
}

// カウンターボタン押下処理
function handleCounterClick(event) {
    const button = event.currentTarget;
    if (button.classList.contains('disabled')) return; // 無効なら無視

    const isMinusButton = button.classList.contains('minus');
    const isPlusButton = button.classList.contains('plus');
    const max = parseInt(button.getAttribute('data-max'), 10) || 20;
    const min = parseInt(button.getAttribute('data-min'), 10) || 0;

    const input = button.parentElement.querySelector('.counter-input');
    let currentValue = parseInt(input.value, 10) || 0;

    if (isMinusButton && currentValue > min) {
        input.value = currentValue - 1;
    }
    if (isPlusButton && currentValue < max) {
        input.value = currentValue + 1;
    }

    // hidden input に反映
    const hiddenInput = document.getElementById('topping-' + button.dataset.id);
    if (hiddenInput) hiddenInput.value = input.value;

    updateTotal();
}

// ページ読み込み時の初期化処理
document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('.counter-button').forEach(button => {
        button.addEventListener('click', handleCounterClick);
    });
    updateTotal(); // 初期表示で正しい小計を計算
})
