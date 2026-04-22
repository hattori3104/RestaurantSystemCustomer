/**
 * 
 */
//タブの切替表示＆カテゴリー絞り込み
document.addEventListener('DOMContentLoaded', () => {

	//全てのラジオボタンを取得
	const tabs = document.querySelectorAll('input[name="tab"]');

	//ラベル要素を取得
	const tabLabels = document.querySelectorAll('.tab-labels label');

	//全ての注文一覧が入っている親要素を取得
	const orderBox = document.querySelector('.tab-contents');
	if (!orderBox) return;

	const orders = orderBox.querySelectorAll('.order_row');

	// ✅ 絞り込み処理：カテゴリに一致する注文だけ表示
	const applyFilter = (selectedCategory) => {
		orders.forEach(order => {
			const category = order.getAttribute('data-category');
			order.style.display = category === selectedCategory ? '' : 'none';
		});

		// ラベルスタイル切り替え
		tabLabels.forEach((label, idx) => {
			label.style.borderBottom = idx === categoryList.indexOf(selectedCategory) ? '2px solid rgb(0,128,0)' : 'none';
			label.style.color = idx === categoryList.indexOf(selectedCategory) ? 'rgb(0,128,0)' : '#999';
		});

		window.scrollTo({ top: 0 });
	};

	//タブ変更時のイベントを登録
	tabs.forEach((tab, index) => {
		tab.addEventListener('change', () => {
			if (tab.checked) {
				const selectedCategory = categoryList[index];
				applyFilter(selectedCategory);
			}
		});
	});

	// 初期表示でチェックされているタブに対応した絞り込みを実行
	const checkedTab = Array.from(tabs).find(tab => tab.checked);
	if (checkedTab) {
		applyFilter(categoryList[Array.from(tabs).indexOf(checkedTab)]);
	}
	
});
